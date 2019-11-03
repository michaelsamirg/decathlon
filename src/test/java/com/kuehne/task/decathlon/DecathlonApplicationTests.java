package com.kuehne.task.decathlon;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.service.DecathlonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DecathlonApplicationTests {

	@Autowired
	private DecathlonService decathlonService;
	
	@Test
	public void testDecathlonList() {
		List<DecathlonModel> list = decathlonService.getDecathlonList();
		
		assertNotNull(list);
	}

}
