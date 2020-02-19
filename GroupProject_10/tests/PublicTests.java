package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import expressiontree.ExpressionTree;
import expressiontree.ExpressionTree.Node;

public class PublicTests {

	@Test
	public void test1() {
		//2+3*4
		String postfix = "2 3 4 * +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(14, tree.evaluate(tree.getRoot()));
	}
	
	@Test
	public void test2() {
		//2+3*4
		String postfix = "2 3 4 * +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(3, tree.height(tree.getRoot()));
	}

	@Test
	public void test3() {
		//2+3*4
		String postfix = "2 3 4 * +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(7, tree.sumEvaluationsAtLevel(3));
	}
	
	@Test
	public void test4() {
		//2*3+5
		String postfix = "2 3 * 5 +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(11, tree.evaluate(tree.getRoot()));
	}
	
	@Test
	public void test5() {
		//2*3+5
		String postfix = "2 3 * 5 +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(3, tree.height(tree.getRoot()));
	}

	@Test
	public void test6() {
		//2*3+5
		String postfix = "2 3 * 5 +";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(5, tree.sumEvaluationsAtLevel(3));
	}
	
	@Test
	public void test7() {
		//7 / 2 - 5 + 1 * 17 - 3 * 2
		String postfix = "7 2 / 5 - 1 17 * + 3 2 * -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(9, tree.evaluate(tree.getRoot()));
	}
	
	@Test
	public void test8() {
		//7 / 2 - 5 + 1 * 17 - 3 * 2
		String postfix = "7 2 / 5 - 1 17 * + 3 2 * -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(5, tree.height(tree.getRoot()));
	}

	@Test
	public void test9() {
		//7 / 2 - 5 + 1 * 17 - 3 * 2
		String postfix = "7 2 / 5 - 1 17 * + 3 2 * -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(20, tree.sumEvaluationsAtLevel(3));
	}
	
	@Test
	public void test10() {
		//-1 - -2 * -12 / -4
		String postfix = "-1 -2 -12 * -4 / -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(5, tree.evaluate(tree.getRoot()));
	}
	
	@Test
	public void test11() {
		//-1 - -2 * -12 / -4
		String postfix = "-1 -2 -12 * -4 / -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(4, tree.height(tree.getRoot()));
	}

	@Test
	public void test12() {
		//-1 - -2 * -12 / -4
		String postfix = "-1 -2 -12 * -4 / -";
		ExpressionTree tree = new ExpressionTree(postfix);
		assertEquals(20, tree.sumEvaluationsAtLevel(3));
	}

}
