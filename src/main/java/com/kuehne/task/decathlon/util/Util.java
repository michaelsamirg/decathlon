package com.kuehne.task.decathlon.util;

public class Util {

	public static String getStringValue(Object value) {
		try {
			String s = String.valueOf(value);
			return s;
		} catch (NullPointerException e) {
			return null;
		}

	}

	public static Double getDoubleValue(String value) {
		try {
			Double d = Double.parseDouble(value);
			return d.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	public static Double getDoubleValue(Object value) {
		try {
			Double d = Double.parseDouble(String.valueOf(value));
			return d.doubleValue();
		} catch (NullPointerException e) {
			return 0.0;
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
	
	public static Double getDoubleFromTime(String value)
	{
		String[] time = value.split("\\.");
		double doubleValue = 0;
		double min = 0;
		double sec = 0;
		double mSec = 0;
		
		if(time.length == 3)
		{
			min = getDoubleValue(time[0]);
			sec = getDoubleValue(time[1]);
			mSec = getDoubleValue(time[2]);
		}
		else if(time.length == 2)
		{
			sec = getDoubleValue(time[0]);
			mSec = getDoubleValue(time[1]);
		}
		else if(time.length == 1)
		{
			mSec = getDoubleValue(time[0]);
		}
		
		doubleValue = (min * 60.0) + sec + (mSec / 100.0);
		
		return doubleValue;
	}
	
}
