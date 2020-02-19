package tests;
import static org.junit.Assert.*;
import infixtoposix.InToPost;

import org.junit.Test;

public class PublicTests {

	@Test
	public void test1() {
		String input = "2+3*4";
		InToPost output = new InToPost(input);
		assertEquals(14, output.evaluatePostfix());
	}

	@Test
	public void test2() {
		String input = "2*3+5";
		InToPost output = new InToPost(input);
		assertEquals(11, output.evaluatePostfix());
	}
	
	@Test
	public void test3() {
		String input = "7 / 2 - 5 + 1 * 17 - 3 * 2";
		InToPost output = new InToPost(input);
		assertEquals(9, output.evaluatePostfix());
	}
	
	@Test
	public void test4() {
		String input = "-1 - -2 * -12 / -4";
		InToPost output = new InToPost(input);
		assertEquals(5, output.evaluatePostfix());
	}
	
	@Test
	public void test5() {
		String input = "(1 + 2) * 7";
		InToPost output = new InToPost(input);
		assertEquals(21, output.evaluatePostfix());
	}
	
	@Test
	public void test6() {
		String input = "(27 / (7 - 2 + 4)) * (3 - 1) * -2";
		InToPost output = new InToPost(input);
		assertEquals(-12, output.evaluatePostfix());
	}
	
	@Test
	public void test7() {
		String input = "((27 / 9) - (7 - 2)) * ((-3 - -1) * -2)";
		InToPost output = new InToPost(input);
		assertEquals(-8, output.evaluatePostfix());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test8() {
		String input = "(((27 / 9) - (7 - 2)) * ((-3 - -1) * -2)";
		InToPost output = new InToPost(input);
		assertEquals(-8, output.evaluatePostfix());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test9() {
		String input = "((27 / 9) - (7 - 2)) * ((-3 - -1) * -2";
		InToPost output = new InToPost(input);
		assertEquals(-8, output.evaluatePostfix());
	}
	

}
