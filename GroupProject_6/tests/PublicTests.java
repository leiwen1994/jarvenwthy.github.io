package tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import loopbag.Bag;
import loopbag.LoopBag;

import org.junit.Test;

public class PublicTests {

	@Test
	public void testInsert() {
		LoopBag<Integer> bag = new Bag(5);
		for(int i = 1; i < 8; i++){
			bag.insert(i);
		}
		String answer = "3,4,5,6,7";
		assertEquals(answer, bag.toString());
	}

	@Test
	public void testSize1() {
		LoopBag<Integer> bag = new Bag(5);
		for(int i = 1; i < 8; i++){
			bag.insert(i);
		}
		assertEquals(bag.size(),5);
	}
	@Test
	public void testSize2() {
		LoopBag<Integer> bag = new Bag(5);
		for(int i = 1; i < 4; i++){
			bag.insert(i);
		}
		assertEquals(bag.size(),3);
	}

	@Test
	public void testContains1() {
		LoopBag<Integer> bag = new Bag(5);
		for(int i = 1; i < 4; i++){
			bag.insert(i);
		}
		assertTrue(bag.contains(3));
	}

	@Test
	public void testContains2() {
		LoopBag<Integer> bag = new Bag(5);
		for(int i = 1; i < 10; i++){
			bag.insert(i);
		}
		assertFalse(bag.contains(3));
	}

	@Test
	public void testContains3() {
		LoopBag<Integer> bag = new Bag(5);
		for (int i = 1; i < 3; i++) {
			bag.insert(i);
		}
		assertFalse(bag.contains(3));
	}

	@Test
	public void testContains4() {
		LoopBag<Integer> bag = new Bag(5);
		for (int i = 1; i < 3; i++) {
			bag.insert(i);
		}

		assertFalse(bag.contains(4));
	}


	@Test
	public void testIsEmpty(){
		LoopBag<Integer> bag = new Bag(5);
		assertTrue(bag.isEmpty());
	}
	@Test
	public void testIsEmpty2(){
		LoopBag<Integer> bag = new Bag(5);
		bag.insert(10);
		assertFalse(bag.isEmpty());
	}

	@Test
	public void testUnion1(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(2);
		bag1.union(bag2);
		String answer="1,2";
		assertEquals(answer, bag1.toString());
	}
	@Test
	public void testUnion2(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(3);
		bag1.union(bag2);
		String answer="1,2";
		assertNotEquals(answer, bag1.toString());
	}
	@Test
	public void testUnion3(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(3);
		bag1.union(bag2);
		String answer="1,2,3";
		assertEquals(answer, bag1.toString());
	}

	@Test
	public void testUnion4(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag1.insert(3);
		bag2.insert(1);
		bag2.insert(4);
		bag2.insert(5);
		bag2.insert(6);
		bag1.union(bag2);
		String answer="2,3,4,5,6";
		assertEquals(answer, bag1.toString());
	}

	@Test
	public void testIntersect1(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(2);
		bag1.intersect(bag2);
		String answer="1,2";
		assertEquals(answer, bag1.toString());
	}
	@Test
	public void testIntersect2(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(3);
		bag1.intersect(bag2);
		String answer="1,2";
		assertNotEquals(answer, bag1.toString());
	}
	@Test
	public void testIntersect3(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag2.insert(1);
		bag2.insert(3);
		bag1.intersect(bag2);
		String answer="1";
		assertEquals(answer, bag1.toString());
	}

	@Test
	public void testIntersect4(){
		LoopBag<Integer> bag1 = new Bag(5);
		LoopBag<Integer> bag2 = new Bag(5);
		bag1.insert(1);
		bag1.insert(2);
		bag1.insert(4);
		bag2.insert(1);
		bag2.insert(2);
		bag2.insert(3);
		bag2.insert(4);
		bag1.intersect(bag2);
		String answer="1,2,4";
		assertEquals(answer, bag1.toString());
	}

	@Test
	public void testIterator1(){
		LoopBag<Integer> bag1 = new Bag(5);

		for(int i  = 1; i<=10; i++) bag1.insert(i);
		Iterator<Integer> iter = bag1.iterator();
		Integer answer = 6;
		assertEquals(answer, iter.next());
		answer = 7;
		assertEquals(answer, iter.next());
	}

	@Test
	public void testIteratorOldestFirst() {
		LoopBag<Integer> bag1 = new Bag(5);

		for (int i = 1; i <= 11; i++)
			bag1.insert(i);
		Iterator<Integer> iter = bag1.iterator();
		Integer answer = 7;
		assertEquals(answer, iter.next());
		answer = 8;
		assertEquals(answer, iter.next());
	}
}

