package com.kuehne.task.decathlon.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{

	private Logger LOG = Logger.getLogger(CommandLineAppStartupRunner.class.getName());
	@Autowired
	private DecathlonService decathlonService;
	
	// Run at startup, save athlete data from cvs file, then generate XML file in
	// the specific path
	public void run(String... args) throws Exception {
		try {
			decathlonService.saveData();
			
			decathlonService.saveXMLFile();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}
	}

}
