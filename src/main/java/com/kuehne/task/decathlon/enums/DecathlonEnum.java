package com.kuehne.task.decathlon.enums;

public enum DecathlonEnum {
	ATHLETE_NAME(0, DecathlonTypeEnum.ATHLETE_NAME),
	_100M(1, DecathlonTypeEnum.TIME),
	LONG_JUMP(2, DecathlonTypeEnum.DISTANCE),
	SHOT_PUT(3, DecathlonTypeEnum.DISTANCE),
	HIGH_JUMP(4, DecathlonTypeEnum.DISTANCE),
	_400M(5, DecathlonTypeEnum.TIME),
	_110M_HURDLES(6, DecathlonTypeEnum.TIME),
	DISCUS_THROW(7, DecathlonTypeEnum.DISTANCE),
	POLE_VAULT(8, DecathlonTypeEnum.DISTANCE),
	JAVELIN_THROW(9, DecathlonTypeEnum.DISTANCE),
	_1500M(10, DecathlonTypeEnum.DISTANCE);
	
	private int position;
	private DecathlonTypeEnum type;
	
	private DecathlonEnum(int position, DecathlonTypeEnum type)
	{
		this.position = position;
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public DecathlonTypeEnum getType() {
		return type;
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
