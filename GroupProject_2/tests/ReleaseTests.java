package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cities.CityApp;

public class ReleaseTests {

	CityApp ca;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ca = new CityApp();
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void testCamelCase2() {
		String expected = "San Antonio";
		String result = ca.camelCase("San antonio");
		assertEquals(expected, result);
	}
	
	
	@Test
	public void testPopulation2() { 
		int expected = -1;
		int result = ca.population("fairfax");
		assertEquals(expected, result);
	}
	
	
	
	@Test
	public void testPopulation4() { 
		int expected = 622104;
		int result = ca.population("baltimore");
		assertEquals(expected, result);
	}
	
	
	
	
	
	@Test
	public void testState3() { 
		String expected = "Nebraska";
		String result = ca.state("Lincoln");
		assertEquals(expected, result);
	}
	

	
	@Test
	public void testCityRank3() { 
		String expected = "Baltimore";
		String result = ca.cityByRank(26);
		assertEquals(expected, result);
	}
	

	
	
	@Test
	public void testCitiesInState3() { 
		int expected = 212;
		int result = ca.CitiesInState("california");
		assertEquals(expected, result);
	}

	
	@Test
	public void testDistance3() { 
		int expected = 1903;
		int result = (int)ca.distance("buffalo","phoenix");	
		assertEquals(expected, result);
	}
	
	
	
}
