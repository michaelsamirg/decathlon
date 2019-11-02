package com.kuehne.task.decathlon.model;

public class DecathlonPointsModel {

	public DecathlonModel pointsA;
	public DecathlonModel pointsB;
	public DecathlonModel pointsC;
	
	public DecathlonPointsModel() {
		super();
		
		pointsA = new DecathlonModel(25.4347, 0.14354, 51.39, 0.8465, 
				1.53775, 5.74352, 12.91, 0.2797, 10.14, 0.03768);
		
		pointsB = new DecathlonModel(18, 220, 1.5, 75, 
				82, 28.5, 4, 100, 7, 480);
		
		pointsC = new DecathlonModel(1.81, 1.4, 1.05, 1.42, 
				1.81, 1.92, 1.1, 1.35, 1.08, 1.85);
	}

	public DecathlonModel getPointsA() {
		return pointsA;
	}

	public void setPointsA(DecathlonModel pointsA) {
		this.pointsA = pointsA;
	}

	public DecathlonModel getPointsB() {
		return pointsB;
	}

	public void setPointsB(DecathlonModel pointsB) {
		this.pointsB = pointsB;
	}

	public DecathlonModel getPointsC() {
		return pointsC;
	}

	public void setPointsC(DecathlonModel pointsC) {
		this.pointsC = pointsC;
	}
}
