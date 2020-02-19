package loopbag;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

/**
 *	CMSC132 Summer 2017
 *	Group Project 2
 */
public class Main {

    public static void main(String[] args) {
    		LoopBag<Integer> bag = new Bag(5);  
    		LoopBag<Integer> bag2 = new Bag(5);  
    		for(int i = 1; i <= 7; i++){
    			bag.insert(i);
    		}
    		System.out.println(bag);
    		bag2.insert(5);
    		bag2.insert(6);
    		bag2.insert(7);
    		bag2.insert(8);
    		bag2.insert(1);
    		bag.union(bag2);
    		System.out.println(bag);
    }
}
