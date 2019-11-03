package com.kuehne.task.decathlon.enums;

public enum DecathlonEnum {
	ATHLETE_NAME(0, DecathlonTypeEnum.ATHLETE_NAME, "Athlete Name", "athleteName", null),
	_100M(1, DecathlonTypeEnum.TIME, "100 m", "_100m", null),
	LONG_JUMP(2, DecathlonTypeEnum.DISTANCE, "Long Jump", "longJump", DecathlonMeasureTypeEnum.CM),
	SHOT_PUT(3, DecathlonTypeEnum.DISTANCE, "Shot Put", "shotPut", DecathlonMeasureTypeEnum.M),
	HIGH_JUMP(4, DecathlonTypeEnum.DISTANCE, "High Jump", "highJump", DecathlonMeasureTypeEnum.CM),
	_400M(5, DecathlonTypeEnum.TIME, "400 m", "_400m", null),
	_110M_HURDLES(6, DecathlonTypeEnum.TIME, "110 m Hurdles", "_110mHurdles", null),
	DISCUS_THROW(7, DecathlonTypeEnum.DISTANCE, "Discus Throw", "discusThrow", DecathlonMeasureTypeEnum.M),
	POLE_VAULT(8, DecathlonTypeEnum.DISTANCE, "Pole Vault", "poleVault", DecathlonMeasureTypeEnum.CM),
	JAVELIN_THROW(9, DecathlonTypeEnum.DISTANCE, "Javelin Throw",  "javelinThrow", DecathlonMeasureTypeEnum.M),
	_1500M(10, DecathlonTypeEnum.TIME, "1500 m", "_1500m", null),
	TOTAL_POINTS(11, null, "Total Points", "totalPoints", null);
	
	private int position;
	private DecathlonTypeEnum type;
	private DecathlonMeasureTypeEnum decathlonMeasureType;
	private String displayName;
	private String fieldName;
	
	private DecathlonEnum(int position, DecathlonTypeEnum type, String displayName, String fieldName, DecathlonMeasureTypeEnum decathlonMeasureType)
	{
		this.position = position;
		this.type = type;
		this.decathlonMeasureType = decathlonMeasureType;
		this.displayName = displayName;
		this.fieldName = fieldName;
	}

	public int getPosition() {
		return position;
	}

	public DecathlonTypeEnum getType() {
		return type;
	}
	
	public DecathlonMeasureTypeEnum getDecathlonMeasureType() {
		return decathlonMeasureType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public static DecathlonEnum getByPosition(int position)
	{
		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
			if(decathlonEnum.getPosition() == position)
				return decathlonEnum;
		}
		
		return null;
	}
	
	public static DecathlonEnum getByType(DecathlonTypeEnum type)
	{
		for (DecathlonEnum decathlonEnum : DecathlonEnum.values()) {
			if(decathlonEnum.getType().equals(type))
				return decathlonEnum;
		}
		
		return null;
	}
}
