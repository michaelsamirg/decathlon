package com.kuehne.task.decathlon.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.service.DecathlonService;

@RestController
@RequestMapping(value="/decathlon")
public class DecathlonRest {

	@Autowired
	private DecathlonService decathlonService;
	
	@RequestMapping(method = RequestMethod.GET, value="/list")
	@ResponseBody
	public List<DecathlonModel> getCalculatedResults()
	{
		return decathlonService.calcualtePoints();
	}
}
