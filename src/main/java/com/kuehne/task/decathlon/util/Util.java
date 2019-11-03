package com.kuehne.task.decathlon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kuehne.task.decathlon.enums.DecathlonEnum;
import com.kuehne.task.decathlon.enums.DecathlonTypeEnum;
import com.kuehne.task.decathlon.model.DecathlonModel;

public class Util {

	private static Logger LOG = Logger.getLogger(Util.class.getName());
	
	public static String getStringValue(Object value) {
		try {
			String s = String.valueOf(value);
			return s;
		} catch (NullPointerException e) {
			LOG.severe(e.getMessage());
			return null;
		}

	}

	public static Double getDoubleValue(String value) {
		try {
			Double d = Double.parseDouble(value);
			return d.doubleValue();
		} catch (NullPointerException e) {
			LOG.severe(e.getMessage());
			return 0.0;
		} catch (NumberFormatException e) {
			LOG.severe(e.getMessage());
			return 0.0;
		}
	}

	public static Double getDoubleValue(Object value) {
		try {
			Double d = Double.parseDouble(String.valueOf(value));
			return d.doubleValue();
		} catch (NullPointerException e) {
			LOG.severe(e.getMessage());
			return 0.0;
		} catch (NumberFormatException e) {
			LOG.severe(e.getMessage());
			return 0.0;
		}
	}

	public static Double getDoubleFromTime(String value) {
		String[] time = value.split("\\.");
		double doubleValue = 0;
		double min = 0;
		double sec = 0;
		double mSec = 0;

		if (time.length == 3) {
			min = getDoubleValue(time[0]);
			sec = getDoubleValue(time[1]);
			mSec = getDoubleValue(time[2]);
		} else if (time.length == 2) {
			sec = getDoubleValue(time[0]);
			mSec = getDoubleValue(time[1]);
		} else if (time.length == 1) {
			mSec = getDoubleValue(time[0]);
		}

		doubleValue = (min * 60.0) + sec + (mSec / 100.0);

		return doubleValue;
	}

	public static List<String> getDecathlonTitles() {
		List<String> list = new ArrayList<>();

		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
			if (!decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME)) {

				list.add(decathlonEnum.getDisplayName());
			}
		}

		return list;
	}

	public static List<String> fromDecathlonModelToList(DecathlonModel decathlonModel, boolean withUnit) {
		double points;
		String unit = "";
		List<String> list = new ArrayList<>();

		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
			if (!decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME)) {
				points = decathlonModel.getByPointCategoryDouble(decathlonEnum);

				unit = "";
				if (withUnit && decathlonEnum.getType() != null) {
					if (decathlonEnum.getType().equals(DecathlonTypeEnum.TIME)) {
						unit = " s";
					} else if (decathlonEnum.getType().equals(DecathlonTypeEnum.DISTANCE)) {
						unit = " m";
					}
				}

				list.add(points == 0 ? "--" : String.valueOf(points) + unit);
			}
		}

		return list;

	}

	public static DOMSource convertDecathlonListTOXML(List<DecathlonModel> list) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Decathlon");
			doc.appendChild(rootElement);
			
			HashMap<Long, String> rankMap = calculateRank(list);
			
			for (DecathlonModel decathlonModel : list) {
				Element athlete = doc.createElement("Athlete");
				rootElement.appendChild(athlete);
				
				Attr attr = doc.createAttribute("id");
				attr.setValue(decathlonModel.getId() + "");
				athlete.setAttributeNode(attr);
				
				attr = doc.createAttribute("Rank");
				attr.setValue(rankMap.get(decathlonModel.getId()));
				athlete.setAttributeNode(attr);
				
				for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
					Element element = doc.createElement(decathlonEnum.getFieldName());
					element.appendChild(doc.createTextNode(decathlonModel.getByPointCategory(decathlonEnum)));
					athlete.appendChild(element);
				}
			}
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
			
			return source;
			
		} catch (ParserConfigurationException e) {
			LOG.severe(e.getMessage());
		} catch (TransformerException e) {
			LOG.severe(e.getMessage());
		}
		
		return null;
	}
	
	private static HashMap<Long, String> calculateRank(List<DecathlonModel> list)
	{
		String rankStr = "";
		int rank = 1;
		double points = 0.0;
		
		List<Long> ids = new ArrayList<>();
		
		HashMap<Long, String> map = new HashMap<>();
		
		for (DecathlonModel decathlonModel : list) {
			if(points != 0.0 && points != decathlonModel.getTotalPoints())
			{
				for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext();) {
					Long id = (Long) iterator.next();
					
					map.put(id,  rankStr);
				}
				
				ids = new ArrayList<>();
				rankStr = "";
			}
			
			points = decathlonModel.getTotalPoints();
			
			if(rankStr.length() > 0) rankStr += "-";
			
			rankStr += rank++ + "";
			
			ids.add(decathlonModel.getId());
		}
		
		for (Iterator<Long> iterator = ids.iterator(); iterator.hasNext();) {
			Long id = (Long) iterator.next();
			
			map.put(id,  rankStr);
		}
		
		return map;
	}
}
