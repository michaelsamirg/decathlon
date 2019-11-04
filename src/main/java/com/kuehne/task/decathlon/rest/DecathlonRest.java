package com.kuehne.task.decathlon.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.service.DecathlonService;
import com.kuehne.task.decathlon.util.Util;

@RestController
@RequestMapping(value="/decathlon")
public class DecathlonRest {

	@Autowired
	private DecathlonService decathlonService;
	
	// REST Api for decathlon list
	@RequestMapping(method = RequestMethod.GET, value="/list")
	@ResponseBody
	public List<DecathlonModel> getDecathlonList()
	{
		return decathlonService.getDecathlonList();
	}
	
	// REST Api for calculate decathlon by athlete
	@RequestMapping(method = RequestMethod.GET, value="/calculate")
	@ResponseBody
	public List<List<String>> getCalculatedResults(@RequestParam(name = "athleteId", required=true) Long athleteId)
	{
		DecathlonModel decathlonModel = decathlonService.getDecathlonById(athleteId);
		DecathlonModel decathlonlPoints = decathlonService.calcualtePoints(decathlonModel);
		
		List<List<String>> returnList = new ArrayList<List<String>>();
		
		returnList.add(Util.getDecathlonTitles());
		returnList.add(Util.fromDecathlonModelToList(decathlonModel, true));
		returnList.add(Util.fromDecathlonModelToList(decathlonlPoints, false));
		
		return returnList;
	}
}
