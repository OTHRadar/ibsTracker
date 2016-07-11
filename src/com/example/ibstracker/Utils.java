package com.example.ibstracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.Log;

public class Utils {
	public static String toDisplayCase(String s) {

	    final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
	                                                 // to be capitalized

	    StringBuilder sb = new StringBuilder();
	    boolean capNext = true;

	    for (char c : s.toCharArray()) {
	        c = (capNext)
	                ? Character.toUpperCase(c)
	                : Character.toLowerCase(c);
	        sb.append(c);
	        capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
	    }
	    return sb.toString();
	}
	public static String getFileAsString(String file) {
	    BufferedReader reader = null;
	    StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file)), "UTF-8"), 8);
	    String line = null;
			while ((line = reader.readLine()) != null)
			{
			    sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return sb.toString();
	}
	
	public static List<FodmapItem> getFileAsFodmapItemList(InputStream inputStream) {
		List<FodmapItem> itemList = new ArrayList<FodmapItem>();
		JsonReader reader = null;
		try {
			 reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
			 reader.beginObject();
		     while (reader.hasNext()) {
		       String name = reader.nextName();
		       if (name.equals("fodmapItem")) {
		    	   reader.beginObject();
		    	   reader.nextName();
		    	   String fmItemName = reader.nextString();
		    	   reader.nextName();
		    	   int fmItemType = reader.nextInt();
		    	   FodmapItem fm = new FodmapItem(Utils.toDisplayCase(fmItemName), fmItemType);
		    	   itemList.add(fm);
		    	   reader.endObject();
		       }
		       else {
		    	   reader.skipValue();
		       }
		     }
		     reader.endObject();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return itemList;
	}
}
