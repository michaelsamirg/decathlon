package com.kuehne.task.decathlon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{

	@Autowired
	private DecathlonService decathlonService;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		decathlonService.saveData();
		
		decathlonService.saveXMLFile();
	}

}
