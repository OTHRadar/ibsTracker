package com.example.ibstracker;

public class FodmapItem {
	
	public Fodmap getFodmap() {
		return fodmap;
	}

	public void setFodmap(Fodmap fodmap) {
		this.fodmap = fodmap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Fodmap fodmap;
	@Override
	public String toString() {
		return name + ": " + fodmap.toString();
	}

	public String name;
	
	public FodmapItem(String name, Fodmap fodmap) {
		this.fodmap = fodmap;
		this.name = name;
	}
	public FodmapItem(String name, int fodmap) {
		switch(fodmap) {
		case 0:
			this.fodmap = Fodmap.ALL_CLEAR;
			break;
		case 1:
			this.fodmap = Fodmap.CAUTION;
			break;
		case 2:
			this.fodmap = Fodmap.WARNING;
			break;
		default:
			this.fodmap = Fodmap.ALL_CLEAR;
		}
		this.name = name;
	}
}
