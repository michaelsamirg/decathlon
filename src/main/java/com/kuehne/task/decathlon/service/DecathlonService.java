package com.kuehne.task.decathlon.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kuehne.task.decathlon.dao.DecathlonDao;
import com.kuehne.task.decathlon.enums.DecathlonEnum;
import com.kuehne.task.decathlon.enums.DecathlonMeasureTypeEnum;
import com.kuehne.task.decathlon.enums.DecathlonTypeEnum;
import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.model.DecathlonPointsModel;
import com.kuehne.task.decathlon.util.Util;

@Service
public class DecathlonService {

	@Value("${results.file.path}")
	private String path;
	
	@Value("${results.file.seperator}")
	private String seperator;
	
	@Value("${results.file.xml.path}")
	private String xmlFilePath;
	
	@Autowired
	private DecathlonDao decathlonDao;

	private Logger LOG = Logger.getLogger(DecathlonService.class.getName());
	
	// Calculate points for specific athlete
	/**
	 * @param decathlonModel
	 * @return
	 */
	public DecathlonModel calcualtePoints(DecathlonModel decathlonModel) 
	{
		// Initial points for calculation
		DecathlonPointsModel decathlonPointsModel = new DecathlonPointsModel();
		double result, pointsA, pointsB, pointsC, totalPoints ;
		double points = 0.0;

		// Object to hold points after calculation
		DecathlonModel decathlonPoints = new DecathlonModel();
		decathlonPoints.setAthelteName(decathlonModel.getAthelteName());
		totalPoints = 0;
		
		// iterate DecathlonEnum to retrieve data
		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {

			// if type is not Athlete name --> continue
			if (!decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME)) {
				result = decathlonModel.getByPointCategoryDouble(decathlonEnum);

				// if measure = CM --> multiply value * 100.0
				if (decathlonEnum.getDecathlonMeasureType() != null
						&& decathlonEnum.getDecathlonMeasureType().equals(DecathlonMeasureTypeEnum.CM))
					result = result * 100;

				// retrieve A,B,C points to replace them in the equation
				pointsA = decathlonPointsModel.getPointsA().getByPointCategoryDouble(decathlonEnum);
				pointsB = decathlonPointsModel.getPointsB().getByPointCategoryDouble(decathlonEnum);
				pointsC = decathlonPointsModel.getPointsC().getByPointCategoryDouble(decathlonEnum);
				
				points = 0.0;
				if(decathlonEnum.getType() != null)
				{
					// calculate time using equation ( INT(A * ((B — P)^C)) )
					if (decathlonEnum.getType().equals(DecathlonTypeEnum.TIME)) {
						points = (int) (pointsA * Math.pow(Math.abs(pointsB - result), pointsC));

					}
					// calculate distance using equation ( INT(A * ((P — B)^C)) )
					else if (decathlonEnum.getType().equals(DecathlonTypeEnum.DISTANCE)){
						points = (int) (pointsA * Math.pow(Math.abs(result - pointsB), pointsC));
					}
				}

				// set points by decathlonEnum in decathlonPoints object
				decathlonPoints.setByPointCategory(decathlonEnum, String.valueOf(points));

				totalPoints += points;
			}
		}
		
		// set total points
		decathlonPoints.setTotalPoints(totalPoints);
		
		return decathlonPoints;
	}
	
	// Calculate all points
	public List<DecathlonModel> calcualteAllPoints() {
		// retrieve athletes list
		List<DecathlonModel> list = getDecathlonList();
		
		// iterate athletes list and calculate each recored
		for (DecathlonModel decathlonModel : list) {
			DecathlonModel points = calcualtePoints(decathlonModel);
			decathlonModel.setTotalPoints(points.getTotalPoints());
		}
		
		// sort list based on total points
		list.sort((d1 , d2) -> (int)(d2.getTotalPoints() - d1.getTotalPoints()));
		
		return list;
	}
	
	// Generate XML file
	public void saveXMLFile()
	{
		
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			// Generate DOM source from calculated athletes list
			DOMSource source = Util.convertDecathlonListTOXML(calcualteAllPoints());
			
			File file = new File(xmlFilePath);
			
			StreamResult result = new StreamResult(file);
			
			// save output to XML file
			transformer.transform(source, result);
			
		} catch (TransformerException e) {
			LOG.severe(e.getMessage());
		} 
		
	}

	// Save data in H2 database
	public void saveData()
	{
		List<DecathlonModel> decathlonModelList = loadDataFromFile();
		
		for (Iterator<?> iterator = decathlonModelList.iterator(); iterator.hasNext();) {
			DecathlonModel decathlonModel = (DecathlonModel) iterator.next();
			
			decathlonDao.save(decathlonModel);
		}
	}
	
	// retrieve all athletes list
	public List<DecathlonModel> getDecathlonList()
	{
		return decathlonDao.findAll();
	}
	
	// retrieve athlete by ID
	public DecathlonModel getDecathlonById(Long athleteId)
	{
		return decathlonDao.getOne(athleteId);
	}
	
	// load data from csv file
	private List<DecathlonModel> loadDataFromFile() {
		File file = getFile();
		List<DecathlonModel> decathlonModelList = new ArrayList<DecathlonModel>();
		
		if (file != null) {
			BufferedReader br = null;
			String line = "";
			
			try {
				br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					// use ; as separator (value defined in application.properites)
					String[] data = line.split(seperator);
					
					// create new DecathlonModel
					DecathlonModel decathlonModel = new DecathlonModel();
					
					int position = 0;
					
					for (String value : data) {
						
						DecathlonEnum decathlonEnum = DecathlonEnum.getByPosition(position);
						
						// Set value in decathlonModel by decathlonEnum
						decathlonModel.setByPointCategory(decathlonEnum, value);
						position++;
					}
					
					decathlonModelList.add(decathlonModel);
				}

			} catch (FileNotFoundException e) {
				LOG.severe(e.getMessage());
			} catch (IOException e) {
				LOG.severe(e.getMessage());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						LOG.severe(e.getMessage());
					}
				}
			}
		}
		
		return decathlonModelList;
	}

	// return file
	private File getFile() {
		try {
			File file = new File(getClass().getClassLoader().getResource(path).getFile());

			return file;
		} catch (SecurityException e) {
			LOG.severe(e.getMessage());
			return null;
		} catch (NullPointerException e) {
			LOG.severe(e.getMessage());
			return null;
		}

	}
}
