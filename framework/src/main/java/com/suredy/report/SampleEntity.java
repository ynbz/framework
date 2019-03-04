package com.suredy.report;

import java.io.Serializable;

public class SampleEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4972790369117645903L;
	
	private String id = null;
	
	private String name = null;
	
	private String city = null;
	
	private String address = null;


	public SampleEntity(String id, String name, String city, String address) {
		this.address = address;
		this.city = city;
		this.id = id;
		this.name = name;
	}


	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}


	
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}


	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
