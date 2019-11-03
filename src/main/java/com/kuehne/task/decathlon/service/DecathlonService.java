package com.kuehne.task.decathlon.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public DecathlonModel calcualtePoints(DecathlonModel decathlonModel) {

		DecathlonPointsModel decathlonPointsModel = new DecathlonPointsModel();
		double result, pointsA, pointsB, pointsC, totalPoints ;
		double points = 0.0;

		DecathlonModel decathlonPoints = new DecathlonModel();
		decathlonPoints.setAthelteName(decathlonModel.getAthelteName());
		totalPoints = 0;
		
		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {

			if (!decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME)) {
				result = decathlonModel.getByPointCategoryDouble(decathlonEnum);

				if (decathlonEnum.getDecathlonMeasureType() != null
						&& decathlonEnum.getDecathlonMeasureType().equals(DecathlonMeasureTypeEnum.CM))
					result = result * 100;

				pointsA = decathlonPointsModel.getPointsA().getByPointCategoryDouble(decathlonEnum);
				pointsB = decathlonPointsModel.getPointsB().getByPointCategoryDouble(decathlonEnum);
				pointsC = decathlonPointsModel.getPointsC().getByPointCategoryDouble(decathlonEnum);
				
				points = 0.0;
				if(decathlonEnum.getType() != null)
				{
					// calculate time
					if (decathlonEnum.getType().equals(DecathlonTypeEnum.TIME)) {
						points = (int) (pointsA * Math.pow(Math.abs(pointsB - result), pointsC));

					}
					// calculate distance
					else if (decathlonEnum.getType().equals(DecathlonTypeEnum.DISTANCE)){
						points = (int) (pointsA * Math.pow(Math.abs(result - pointsB), pointsC));
					}
				}

				decathlonPoints.setByPointCategory(decathlonEnum, String.valueOf(points));

				totalPoints += points;
			}
		}

		decathlonPoints.setTotalPoints(totalPoints);
		
		return decathlonPoints;
	}
	
	public List<DecathlonModel> calcualteAllPoints() {
		List<DecathlonModel> list = getDecathlonList();
		
		for (DecathlonModel decathlonModel : list) {
			DecathlonModel points = calcualtePoints(decathlonModel);
			decathlonModel.setTotalPoints(points.getTotalPoints());
		}
		
		list.sort((d1 , d2) -> (int)(d2.getTotalPoints() - d1.getTotalPoints()));
		
		return list;
	}
	
	
	public void saveXMLFile()
	{
		
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = Util.convertDecathlonListTOXML(calcualteAllPoints());
			
			File file = new File(xmlFilePath);
			
			StreamResult result = new StreamResult(file);
			
			transformer.transform(source, result);
			
		} catch (TransformerException e) {
			e.printStackTrace();
		} 
		
	}

	public void saveData()
	{
		List<DecathlonModel> decathlonModelList = loadDataFromFile();
		
		for (Iterator<?> iterator = decathlonModelList.iterator(); iterator.hasNext();) {
			DecathlonModel decathlonModel = (DecathlonModel) iterator.next();
			
			decathlonDao.save(decathlonModel);
		}
	}
	
	public List<DecathlonModel> getDecathlonList()
	{
		return decathlonDao.findAll();
	}
	
	public DecathlonModel getDecathlonById(Long athleteId)
	{
		return decathlonDao.getOne(athleteId);
	}
	
	private List<DecathlonModel> loadDataFromFile() {
		File file = getFile();
		List<DecathlonModel> decathlonModelList = new ArrayList<DecathlonModel>();
		
		if (file != null) {
			BufferedReader br = null;
			String line = "";
			
			try {
				br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					// use comma as separator
					String[] data = line.split(seperator);
					
					DecathlonModel decathlonModel = new DecathlonModel();
					
					int position = 0;
					
					for (String value : data) {
						
						DecathlonEnum decathlonEnum = DecathlonEnum.getByPosition(position);
						
						decathlonModel.setByPointCategory(decathlonEnum, value);
						position++;
					}
					
					decathlonModelList.add(decathlonModel);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return decathlonModelList;
	}

	private File getFile() {
		try {
			File file = new File(getClass().getClassLoader().getResource(path).getFile());

			return file;
		} catch (SecurityException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}

	}
}
