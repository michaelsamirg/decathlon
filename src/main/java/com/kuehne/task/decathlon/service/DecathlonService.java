package com.kuehne.task.decathlon.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kuehne.task.decathlon.enums.DecathlonEnum;
import com.kuehne.task.decathlon.enums.DecathlonTypeEnum;
import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.model.DecathlonPointsModel;

@Service
public class DecathlonService {

	@Value("${results.file.path}")
	private String path;
	
	@Value("${results.file.seperator}")
	private String seperator;

	public List<DecathlonModel> calcualtePoints() {

		List<DecathlonModel> decathlonModelList = loadDataFromFile();
		List<DecathlonModel> decathlonModelPointsList = new ArrayList<>();
		DecathlonPointsModel decathlonPointsModel = new DecathlonPointsModel();
		double result, pointsA, pointsB, pointsC, totalPoints ;
		double points = 0.0;
		for (Iterator<DecathlonModel> iterator = decathlonModelList.iterator(); iterator.hasNext();) {
			DecathlonModel decathlonModel = (DecathlonModel) iterator.next();
			
			System.out.println(decathlonModel);
			
			DecathlonModel decathlonPoints = new DecathlonModel();
			decathlonPoints.setAthelteName(decathlonModel.getAthelteName());
			totalPoints = 0;
			
			for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
				
				if(!decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME))
				{
					result = decathlonModel.getByPointCategoryDouble(decathlonEnum);
					pointsA = decathlonPointsModel.getPointsA().getByPointCategoryDouble(decathlonEnum);
					pointsB = decathlonPointsModel.getPointsB().getByPointCategoryDouble(decathlonEnum);
					pointsC = decathlonPointsModel.getPointsC().getByPointCategoryDouble(decathlonEnum);
					//calculate time
					if(decathlonEnum.getType().equals(DecathlonTypeEnum.TIME))
					{
						points = (int)(pointsA * Math.pow(Math.abs(pointsB - result), pointsC));
						
					}
					//calculate distance
					else
					{
						points = (int)(pointsA * Math.pow(Math.abs(result - pointsB), pointsC));
					}
					
					decathlonPoints.setByPointCategory(decathlonEnum, String.valueOf(points));
					
					totalPoints += points;
				}
			}
			
			decathlonPoints.setTotalPoints(totalPoints);
			System.out.println(decathlonPoints);
			
			decathlonModelPointsList.add(decathlonPoints);
		}
		
		return decathlonModelPointsList;
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
