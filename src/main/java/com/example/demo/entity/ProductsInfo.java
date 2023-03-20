package com.example.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductsInfo  implements Serializable{
	
	private int timestamp;
	
	private int lastUpdate;
	
	private String url;

	private String asin;
	
	private String title;
	
	private int currentSell;
	
	private int avgSell;

	private int avgSell30;
	
	private int salesRankDrops30;
	
	private int salesRankDrops90;
	
	private int salesRankDrops180;
	
	private String manufacturer;
	
	private String brand;
	
	private String partNumber;
	
	private String upcList;
	
	private String eanList;
	
	private String model;
	
	private String productGroup;
	
	private String color;
	
	private int size;
	
	private int packageHeight;
	
	private int packageLength;
	
	private int packageWidth;
	
	private int packageWeight;

	
	
}
