package com.kuehne.task.decathlon;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kuehne.task.decathlon.model.DecathlonModel;
import com.kuehne.task.decathlon.service.DecathlonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DecathlonApplicationTests {

	@Autowired
	private DecathlonService decathlonService;
	
	@Before
    public void setup() throws Exception {
		decathlonService.saveData();
    }
	
	@Test
	public void testDecathlonList() {
		
		List<DecathlonModel> list = decathlonService.getDecathlonList();
		
		assertNotNull(list);
	}
	
	@Test
	public void testDecathlonRetreiveById()
	{
		DecathlonModel decathlonModel = decathlonService.getDecathlonById(1L);
		
		assertNotNull(decathlonModel);
	}
	
	@Test
	public void testDecathlonCalculations()
	{
		DecathlonModel decathlonModel = new DecathlonModel(12.61, 5.0, 9.22, 1.50, 
				60.39, 16.43, 21.60, 2.60, 35.81, 325.72);
		
		decathlonModel.setAthelteName("Test");
		
		DecathlonModel decathlonModelPoints = decathlonService.calcualtePoints(decathlonModel);
		
		Assert.assertEquals(4200.0, decathlonModelPoints.getTotalPoints(), 0);
	}

}
