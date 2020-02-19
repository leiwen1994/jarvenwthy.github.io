package mediaRentalManager;

import java.util.ArrayList;

public class Customer implements Comparable<Customer>{
	//Declare variables
	private String name;
	private String address;
	private String plan;
	private ArrayList<String>rentList;
	private ArrayList<String>queueList; 
	//constructor
	public Customer(String name, String address,String plan){
		rentList= new ArrayList<String>();
		queueList= new ArrayList<String>();
		this.name = name;
		this.address = address;
		this.plan = plan;
	}
	// get name
	public String getName() {
		return name;
	}
	// get address
	public String getAddress() {
		return address;
	}
	//get plan
	public String getPlan() {
		return plan;
	}
	//get rentList
	public ArrayList<String> getRentList() {
		return rentList;
	}
	//get queueList
	public ArrayList<String> getQueueList() {
		return queueList;
	}
	//setter for name
	public void setName(String name) {
		this.name = name;
	}
	//setter for address
	public void setAddress(String address) {
		this.address = address;
	}
	//setter for plan
	public void setPlan(String plan) {
		this.plan = plan;
	}
	//print string
	public String toString() {
		return "Name: "+this.name + ", Address: " + this.address + ", Plan: " + this.plan;
	}
	//method to compare sort
	@Override
	public int compareTo(Customer o) {
		return name.compareTo(o.name);
	}
	//equal method
	@Override
	public boolean equals(Object o) {
		if( o == null) {
			return false;
		}
		if( o == this) {
			return true;
		}
		if(!(o instanceof Customer)) {
			return false;
		}
		Customer c1 = (Customer) o;
		return this.name.equals(c1.name) && this.address.equals(c1.address) && this.plan.equals(c1.plan);
		
	}
}
