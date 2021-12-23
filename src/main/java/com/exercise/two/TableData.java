// TableData.java
package com.exercise.two;

import java.io.Serializable;

public class TableData implements Serializable
{
	private String key;
	private String value;
	
	public String toString()
	{
		return key+value;
	}
	
	public String concatString() 
	{
		return key + " , " + value;
	}
	
	public String getKey() 
	{
		return key;
	}
	
	public String getValue() 
	{
		return value;
	}
	
	public void setKey(String keyData)
	{
		this.key = keyData;
	}
	
	public void setValue(String valueData)
	{
		this.value = valueData;
	}
	
	public String searchDataTable(String query)
	{
		int keyCount = 0;
		int valueCount = 0;
		String inKey = "";
		String inValue = "";
		String isFound = "false";
		
		if (key.contains(query)) {
			isFound = "";
			keyCount = countString(key, query);
			if (keyCount > 0) {
				inKey = "key";
				isFound += "true";				
			}
		} 
		
		if (value.contains(query)) {
			isFound = "";
			valueCount = countString(value, query);
			if (valueCount > 0) {
				inValue = "value";
				isFound += "true";
			}
		}
		
		return isFound + "," + inKey + "," + inValue + "," + keyCount + "," + valueCount;
	}
	
	private int countString(String mainStr, String query)
	{
		int count = 0;
		int index = 0;
		
		while (index <= mainStr.length() - query.length()) {
			String mainSubStr = mainStr.substring(index, index + query.length());
			
			if (query.equals(mainSubStr)) {
				count++;
			}
			
			index++;
		}
		
		return count;
	}
}