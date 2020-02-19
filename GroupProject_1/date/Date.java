/**
 *  This is a group project. 2 Students can be in one group.
 *  You can do the project alone.  
 *  Each student has to submit. 
 *  *  Deadline: 4:00pm     Late deadline: 11:59pm  Late penalty: 50% 
 *  Write your group members here:
 *  	Student 1:Jiawen Lei
 *  	Student 2: Sarah Bang
 */

package date;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class Date implements Comparable<Date>{

	private static final int[] DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	protected final int month;
	protected final int day;
	protected final int year;

	/*
	 * Initialized a new date from the month, day and year.
	 * @param month the month (between 1 and 12)
	 * @param day the day (between 1 and 28-31, depending on the month)
	 * @param year the year

	 */

	public Date(int month, int day, int year){
		if(!isValid(month, day, year)){
			System.out.println("Invalid Date");
			throw new IllegalArgumentException();
		}
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/*
	 * @return month of that month
	 */
	public int getMonth(){
		return this.month;
	}

	/*
	 * @return day of that day
	 */
	public int getDay(){
		return this.day;
	}

	/*
	 * @return year of that year
	 */
	public int getYear(){
		return this.year;
	}

	/**
	 * This method checks if a given date is a valid calendar date
	 * 
	 * @param m  month
	 * @param d  day
	 * @param y  year. (A year is no less than 1900, and no greater than 2100)
	 * @return  true if the given date is a valid calendar date. false otherwise
	 */
	public static boolean isValid(int m, int d, int y){
		if(isLeapYear(y)) {
			if(m > 12 || m < 1) {
				return false;
			}
			if(d > DAYS[m] || (y < 1900 || y > 2100)) {
				return false;
			}

			if(m == 2 && d > 0 && d <30) {
				return true;
			}
			return true;
		}else {
			if(m > 12 || m < 1) {
				return false;
			}
			if(d >= DAYS[m] || (y < 1900 || y > 2100)) {
				return false;
			}

			if(m == 2 && d > 0 && d < 29) {
				return true;
			}
			
			return true;
		}
	}
	/** 
	 * @param year
	 * @return true if the given year is a leap year. false otherwise.
	 */
	public static boolean isLeapYear(int year){
		if(((year % 4 == 0) && (year % 100 !=0)) || (year % 400 ==0)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Compare this date to that day.
	 * @return {a negative integer or zero or a positive integer}, depending on whether this date is {before, equal to, after} that date
	 */

	public int compareTo(Date that) {
		if(this.day ==(that.day) && this.month == that.month && this.year == that.year){
			return 0;
		}else if(this.year > that.year) {
			return 1;
		}else {
			return -1;
		}
	}

	/**
	 * Return a string representation of this date.
	 * @return the string representation in the format MM/DD/YYYY
	 */
	public String toString(){

		return month + "/" + day + "/" + year;
	}


	/**
	 * 
	 * @return the word representation of the date.
	 * Example: (new Date(12,1,2017)).dateToWords() returns "December One Two Thousand Seventeen" 
	 */
	public String dateToWords() {
		String[] monthWords = {"January", "February", "March", "April", "May", "June", "July", "August", "September","October", "November", "December"};
		String[] numbersLessThanTen = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
		String[] numbersBetweenTenAndTwenty = {"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
		String[] multiplesOfTen = {"Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
		String[] yearWords = {"Hundred", "Thousand"};
		int year_a = year % 10;
		int year_b = year / 100;
		int year_c = year / 1000;
		String dayCount = "";
		String yearCount = "";
		//year
		yearCount += numbersLessThanTen[year_c-1]+" "+ yearWords[1] + " "; 
		if(year % 1000 != 0){
			if(year_b < 20) {
				yearCount += numbersLessThanTen[year_b %10 -1] +" "+ yearWords[0] + " ";
			}
			if(year % 100 <=100 && year % 100 >= 20) {
				yearCount += multiplesOfTen[year%100/10-1] +" "+ numbersLessThanTen[year_a-1] + " " ;
			}else if(year % 100 <20 && year % 100 > 10) {
				yearCount += numbersBetweenTenAndTwenty[year_a-1] + " ";
			}else if(year %100 <=10 && year % 100 >=1) {
				yearCount += numbersLessThanTen[year_a -1] + " ";
			}
		}else {
			if(year == 2000) {
				yearCount = numbersLessThanTen[1] +" "+ yearWords[1] ;
			}else if(year == 1900) {
				yearCount += numbersLessThanTen[year_b % 10-1] + " ";
			}
		}
		//day
		if(day <= 10 && day > 0) {
			dayCount += numbersLessThanTen[day -1]+ " ";
		}else if( day > 10 && day < 20) {
			dayCount += numbersBetweenTenAndTwenty[day%10-1] + " ";
		}else if(day >=20 && day <= 31) {
			dayCount += multiplesOfTen[day/10-1] +" "+ numbersLessThanTen[day%10-1]+ " ";
		}
		return monthWords[month-1] +" "+ dayCount + yearCount;

	}
	public int age(){
		Calendar cal = Calendar.getInstance();
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int m = cal.get(Calendar.MONTH);		//starts from 0 to 11
		int y = cal.get(Calendar.YEAR);

		int age = 0;
		for(int i =this.year; i < y; i++) {
			age++;
		}
		if(month > m) {
			age--;
		}else if(month == m) {
			if(day > d) {
				age--;
			}
		}
		return age;
	}
}
