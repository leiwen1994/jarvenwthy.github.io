/**
 * This course project is copyright of CyberTek ©CyberTek[2017]. All rights reserved. 
 * Any redistribution or reproduction of part or all of the contents in any form is 
 * prohibited without the express consent of CyberTek.
 */
package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;

import date.Date;

public class PublicTests{
	@Test
	public void isLeapTest(){
		Date d1 = new Date(1,1,2016);
		Date d2 = new Date(1,1,2013);
		assertEquals(true, Date.isLeapYear(d1.getYear()));
		assertEquals(false, Date.isLeapYear(d2.getYear()));
	}
	
	@Test
	public void isValidTest(){
		assertEquals(false, Date.isValid(21, 1, 2011));
		assertEquals(true, Date.isValid(1,1,1999));
	}
	
	@Test
	public void toStringTest(){
		Date d1 = new Date(10, 15, 2001);
		Date d2 = new Date(7, 30, 1989);
		assertEquals("10/15/2001", d1.toString());
		assertEquals("7/30/1989", d2.toString());
	}
	
	@Test
	public void compareToTest(){
		Date d1 = new Date(10, 9, 1977);
		Date d2 = new Date(9, 10, 1977);
		Date d3 = new Date(10, 9, 1977);
		Date d4 = new Date(9, 10, 2007);
		assertEquals(-1, d2.compareTo(d1));
		assertEquals(0, d1.compareTo(d3));
		assertEquals(-1, d2.compareTo(d3));
		assertEquals(1, d4.compareTo(d3));
	}
	
	@Test
	public void toWordsTest(){
		Date d1 = new Date(10, 9, 1977);
		Date d2 = new Date(1, 1, 2000);
		Date d3 = new Date(4, 29, 1919);
		assertEquals("October Nine One Thousand Nine Hundred Seventy Seven", d1.dateToWords().trim());
		assertEquals("January One Two Thousand", d2.dateToWords().trim());
		assertEquals("April Twenty Nine One Thousand Nine Hundred Nineteen", d3.dateToWords().trim());
		
	}
	
	@Test
	public void testAge(){
		Date date = new Date(6, 29, 1978);
		assertEquals(39, date.age());
		date = new Date(1, 1, 2000);
		assertEquals(18, date.age());
		date = new Date(4, 29, 1988);
		assertEquals(30, date.age());
	}


}
