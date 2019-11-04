package com.kuehne.task.decathlon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.kuehne.task.decathlon.enums.DecathlonEnum;
import com.kuehne.task.decathlon.util.Util;

@Entity
public class DecathlonModel {

	@Id
	@GeneratedValue
	private Long id;
	private String athelteName;
	private double _100m;
	private double longJump;
	private double shotPut;
	private double highJump;
	private double _400m;
	private double _110mHurdles;
	private double discusThrow;
	private double poleVault;
	private double javelinThrow;
	private double _1500m;
	
	@Transient
	private double totalPoints;
	
	public DecathlonModel() {
		super();
	}
	
	public DecathlonModel(String athelteName) {
		super();
		this.athelteName = athelteName;
	}
	
	public DecathlonModel(double _100m, double longJump, double shotPut, double highJump, double _400m,
			double _110mHurdles, double discusThrow, double poleVault, double javelinThrow, double _1500m) {
		super();
		this._100m = _100m;
		this.longJump = longJump;
		this.shotPut = shotPut;
		this.highJump = highJump;
		this._400m = _400m;
		this._110mHurdles = _110mHurdles;
		this.discusThrow = discusThrow;
		this.poleVault = poleVault;
		this.javelinThrow = javelinThrow;
		this._1500m = _1500m;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAthelteName() {
		return athelteName;
	}
	public void setAthelteName(String athelteName) {
		this.athelteName = athelteName;
	}
	public double get_100m() {
		return _100m;
	}
	public void set_100m(double _100m) {
		this._100m = _100m;
	}
	public double getLongJump() {
		return longJump;
	}
	public void setLongJump(double longJump) {
		this.longJump = longJump;
	}
	public double getShotPut() {
		return shotPut;
	}
	public void setShotPut(double shotPut) {
		this.shotPut = shotPut;
	}
	public double getHighJump() {
		return highJump;
	}
	public void setHighJump(double highJump) {
		this.highJump = highJump;
	}
	public double get_400m() {
		return _400m;
	}
	public void set_400m(double _400m) {
		this._400m = _400m;
	}
	public double get_110mHurdles() {
		return _110mHurdles;
	}
	public void set_110mHurdles(double _110mHurdles) {
		this._110mHurdles = _110mHurdles;
	}
	public double getDiscusThrow() {
		return discusThrow;
	}
	public void setDiscusThrow(double discusThrow) {
		this.discusThrow = discusThrow;
	}
	public double getPoleVault() {
		return poleVault;
	}
	public void setPoleVault(double poleVault) {
		this.poleVault = poleVault;
	}
	public double getJavelinThrow() {
		return javelinThrow;
	}
	public void setJavelinThrow(double javelinThrow) {
		this.javelinThrow = javelinThrow;
	}
	public double get_1500m() {
		return _1500m;
	}
	public void set_1500m(double _1500m) {
		this._1500m = _1500m;
	}
	public double getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	// Set field value based on DecathlonEnum
	public void setByPointCategory(DecathlonEnum decathlonEnum, String value)
	{
		if(decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME))
			this.setAthelteName(value);
		else if (decathlonEnum.equals(DecathlonEnum._100M))
			this.set_100m(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.LONG_JUMP))
			this.setLongJump(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.SHOT_PUT))
			this.setShotPut(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.HIGH_JUMP))
			this.setHighJump(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum._400M))
			this.set_400m(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum._110M_HURDLES))
			this.set_110mHurdles(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.DISCUS_THROW))
			this.setDiscusThrow(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.POLE_VAULT))
			this.setPoleVault(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum.JAVELIN_THROW))
			this.setJavelinThrow(Util.getDoubleValue(value));
		else if (decathlonEnum.equals(DecathlonEnum._1500M))
		{
			this.set_1500m(Util.getDoubleFromTime(value));
		}
	}
	
	// get field value as String based on DecathlonEnum
	public String getByPointCategory(DecathlonEnum decathlonEnum)
	{
		if(decathlonEnum.equals(DecathlonEnum.ATHLETE_NAME))
			return this.getAthelteName();
		else if (decathlonEnum.equals(DecathlonEnum._100M))
			return String.valueOf(this.get_100m());
		else if (decathlonEnum.equals(DecathlonEnum.LONG_JUMP))
			return String.valueOf(this.getLongJump());
		else if (decathlonEnum.equals(DecathlonEnum.SHOT_PUT))
			return String.valueOf(this.getShotPut());
		else if (decathlonEnum.equals(DecathlonEnum.HIGH_JUMP))
			return String.valueOf(this.getHighJump());
		else if (decathlonEnum.equals(DecathlonEnum._400M))
			return String.valueOf(this.get_400m());
		else if (decathlonEnum.equals(DecathlonEnum._110M_HURDLES))
			return String.valueOf(this.get_110mHurdles());
		else if (decathlonEnum.equals(DecathlonEnum.DISCUS_THROW))
			return String.valueOf(this.getDiscusThrow());
		else if (decathlonEnum.equals(DecathlonEnum.POLE_VAULT))
			return String.valueOf(this.getPoleVault());
		else if (decathlonEnum.equals(DecathlonEnum.JAVELIN_THROW))
			return String.valueOf(this.getJavelinThrow());
		else if (decathlonEnum.equals(DecathlonEnum._1500M))
			return String.valueOf(this.get_1500m());
		else if(decathlonEnum.equals(DecathlonEnum.TOTAL_POINTS))
			return String.valueOf(this.getTotalPoints());
		else return "";
	}
	
	// get field value as double based on DecathlonEnum
	public double getByPointCategoryDouble(DecathlonEnum decathlonEnum)
	{
		
		if (decathlonEnum.equals(DecathlonEnum._100M))
			return this.get_100m();
		else if (decathlonEnum.equals(DecathlonEnum.LONG_JUMP))
			return this.getLongJump();
		else if (decathlonEnum.equals(DecathlonEnum.SHOT_PUT))
			return this.getShotPut();
		else if (decathlonEnum.equals(DecathlonEnum.HIGH_JUMP))
			return this.getHighJump();
		else if (decathlonEnum.equals(DecathlonEnum._400M))
			return this.get_400m();
		else if (decathlonEnum.equals(DecathlonEnum._110M_HURDLES))
			return this.get_110mHurdles();
		else if (decathlonEnum.equals(DecathlonEnum.DISCUS_THROW))
			return this.getDiscusThrow();
		else if (decathlonEnum.equals(DecathlonEnum.POLE_VAULT))
			return this.getPoleVault();
		else if (decathlonEnum.equals(DecathlonEnum.JAVELIN_THROW))
			return this.getJavelinThrow();
		else if (decathlonEnum.equals(DecathlonEnum._1500M))
			return this.get_1500m();
		else if(decathlonEnum.equals(DecathlonEnum.TOTAL_POINTS))
			return this.getTotalPoints();
		
		else return 0.0;
	}
	
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer("DecathlonModel[\n");
		
		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
			string.append(decathlonEnum.getDisplayName() + ": " + this.getByPointCategory(decathlonEnum));
			string.append("\n");
		}
		
		string.append("TotalPoints:" + totalPoints + "\n");
		
		string.append("]");
		return string.toString();
	}
}
