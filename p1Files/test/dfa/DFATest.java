package test.dfa;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import fa.dfa.DFA;

public class DFATest {

	// ------------------- dfa1 tests ----------------------//
	private DFA dfa1() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("a"));
		assertTrue(dfa.addState("b"));
		assertTrue(dfa.setStart("a"));
		assertTrue(dfa.setFinal("b"));

		assertFalse(dfa.addState("a"));
		assertFalse(dfa.setStart("c"));
		assertFalse(dfa.setFinal("c"));

		assertTrue(dfa.addTransition("a", "a", '0'));
		assertTrue(dfa.addTransition("a", "b", '1'));
		assertTrue(dfa.addTransition("b", "a", '0'));
		assertTrue(dfa.addTransition("b", "b", '1'));

		assertFalse(dfa.addTransition("c", "b", '1'));
		assertFalse(dfa.addTransition("a", "c", '1'));
		assertFalse(dfa.addTransition("a", "b", '2'));

		return dfa;
	}

	@Test
	public void test1_1() {
		DFA dfa = dfa1();
		System.out.println("dfa1 instantiation pass");
	}

	@Test
	public void test1_2() {
		DFA dfa = dfa1();
		assertNotNull(dfa.getState("a"));
		assertEquals(dfa.getState("a").getName(), "a");
		assertTrue(dfa.isStart("a"));
		assertNotNull(dfa.getState("b"));
		assertEquals(dfa.getState("b").getName(), "b");
		assertTrue(dfa.isFinal("b"));
		assertEquals(dfa.getSigma(), Set.of('0', '1'));

		System.out.println("dfa1 correctness pass");
	}

	@Test
	public void test1_3() {
		DFA dfa = dfa1();

		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa1 accept pass");
	}

	@Test
	public void test1_4() {

		DFA dfa = dfa1();

		String dfaStr = dfa.toString();
		String expStr = " Q = { a b }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "		0	1\n"
				+ "	a	a	b\n"
				+ "	b	a	b\n"
				+ "q0 = a\n"
				+ "F = { b }";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));

		System.out.println("dfa1 toString pass");
	}

	@Test
	public void test1_5() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');

		// different DFA objects
		assertTrue(dfa != dfaSwap);

		// different state objects
		assertTrue(dfa.getState("a") != dfaSwap.getState("a"));
		assertTrue(dfa.getState("b") != dfaSwap.getState("b"));
		assertEquals(dfa.isStart("a"), dfaSwap.isStart("a"));

		// the transitions of the original dfa should not change
		assertFalse(dfa.accepts("0"));
		assertTrue(dfa.accepts("1"));
		assertFalse(dfa.accepts("00"));
		assertTrue(dfa.accepts("101"));
		assertFalse(dfa.accepts("e"));

		System.out.println("dfa1Swap instantiation pass");
	}

	@Test
	public void test1_6() {
		DFA dfa = dfa1();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("1"));
		assertTrue(dfaSwap.accepts("0"));
		assertFalse(dfaSwap.accepts("11"));
		assertTrue(dfaSwap.accepts("010"));
		assertFalse(dfaSwap.accepts("e"));

		System.out.println("dfa1Swap accept pass");
	}

	// ------------------- dfaI tests ----------------------//
	private DFA dfa2() {
		DFA dfa = new DFA();
		dfa.addSigma('0');
		dfa.addSigma('1');

		assertTrue(dfa.addState("3"));
		assertTrue(dfa.setFinal("3"));

		assertTrue(dfa.addState("0"));
		assertTrue(dfa.setStart("0"));

		assertTrue(dfa.addState("1"));
		assertTrue(dfa.addState("2"));

		assertFalse(dfa.setFinal("c"));
		assertFalse(dfa.setStart("a"));
		assertFalse(dfa.addState("2"));

		assertTrue(dfa.addTransition("0", "1", '0'));
		assertTrue(dfa.addTransition("0", "0", '1'));
		assertTrue(dfa.addTransition("1", "3", '0'));
		assertTrue(dfa.addTransition("1", "2", '1'));
		assertTrue(dfa.addTransition("2", "1", '0'));
		assertTrue(dfa.addTransition("2", "1", '1'));
		assertTrue(dfa.addTransition("3", "3", '0'));
		assertTrue(dfa.addTransition("3", "3", '1'));

		assertFalse(dfa.addTransition("3", "a", '1'));
		assertFalse(dfa.addTransition("c", "a", '1'));
		assertFalse(dfa.addTransition("3", "a", '2'));

		return dfa;
	}

	@Test
	public void test2_1() {
		DFA dfa = dfa2();
		System.out.println("dfa2 instantiation pass");
	}

	@Test
	public void test2_2() {
		DFA dfa = dfa2();
		assertNotNull(dfa.getState("0"));
		assertEquals(dfa.getState("1").getName(), "1");
		assertTrue(dfa.isStart("0"));
		assertNotNull(dfa.getState("3"));
		assertEquals(dfa.getState("3").getName(), "3");
		assertTrue(dfa.isFinal("3"));
		assertEquals(dfa.getSigma(), Set.of('0', '1'));

		System.out.println("dfa2 correctness pass");
	}

	@Test
	public void test2_3() {
		DFA dfa = dfa2();
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));

		System.out.println("dfa2 accept pass");
	}

	@Test
	public void test2_4() {
		DFA dfa = dfa2();
		String dfaStr = dfa.toString();
		String expStr = "Q = { 3 0 1 2 }\n"
				+ "Sigma = { 0 1 }\n"
				+ "delta =\n"
				+ "	0	1\n"
				+ "3	3	3\n"
				+ "0	1	0\n"
				+ "1	3	2\n"
				+ "2	1	1\n"
				+ "q0 = 0\n"
				+ "F = { 3 }\n";
		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa2 toString pass");
	}

	@Test
	public void test2_5() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		// different DFA objects
		assertTrue(dfa != dfaSwap);
		// different DFA states
		assertTrue(dfa.getState("0") != dfaSwap.getState("0"));
		assertTrue(dfa.getState("1") != dfaSwap.getState("1"));
		assertTrue(dfa.getState("3") != dfaSwap.getState("3"));
		assertEquals(dfa.isStart("0"), dfaSwap.isStart("0"));
		assertEquals(dfa.isFinal("3"), dfaSwap.isFinal("3"));

		// ensure that the transitions of the original DFA don't change
		assertFalse(dfa.accepts("010"));
		assertTrue(dfa.accepts("00"));
		assertFalse(dfa.accepts("101"));
		assertTrue(dfa.accepts("111011111111110"));
		assertFalse(dfa.accepts("1110111111111010"));

		System.out.println("dfa2Swap instantiation pass");
	}

	@Test
	public void test2_6() {
		DFA dfa = dfa2();
		DFA dfaSwap = dfa.swap('1', '0');
		assertFalse(dfaSwap.accepts("101"));
		assertTrue(dfaSwap.accepts("11"));
		assertFalse(dfaSwap.accepts("010"));
		assertTrue(dfaSwap.accepts("000100000000001"));
		assertFalse(dfaSwap.accepts("0001000000000101"));
		System.out.println("dfa2Swap accept pass");
	}

	// ------------------- dfa3 tests ----------------------//
	private DFA dfa3() {
		DFA dfa = new DFA();
		dfa.addSigma('2');
		dfa.addSigma('1');

		assertTrue(dfa.addState("G"));
		assertTrue(dfa.addState("D"));

		assertTrue(dfa.setFinal("G"));
		assertTrue(dfa.setFinal("D"));

		assertTrue(dfa.addState("A"));
		assertTrue(dfa.setStart("D"));
		assertTrue(dfa.setStart("A"));

		assertTrue(dfa.addState("B"));
		assertTrue(dfa.addState("C"));
		assertTrue(dfa.addState("E"));
		assertTrue(dfa.addState("F"));

		assertFalse(dfa.addState("A"));
		assertFalse(dfa.setFinal("K"));
		assertFalse(dfa.setStart("BK"));

		assertTrue(dfa.addTransition("A", "B", '1'));
		assertTrue(dfa.addTransition("A", "C", '2'));

		assertTrue(dfa.addTransition("B", "D", '1'));
		assertTrue(dfa.addTransition("B", "E", '2'));

		assertTrue(dfa.addTransition("C", "F", '1'));
		assertTrue(dfa.addTransition("C", "G", '2'));

		assertTrue(dfa.addTransition("C", "F", '1'));
		assertTrue(dfa.addTransition("C", "G", '2'));

		assertTrue(dfa.addTransition("D", "D", '1'));
		assertTrue(dfa.addTransition("D", "E", '2'));

		assertTrue(dfa.addTransition("E", "D", '1'));
		assertTrue(dfa.addTransition("E", "E", '2'));

		assertTrue(dfa.addTransition("F", "F", '1'));
		assertTrue(dfa.addTransition("F", "G", '2'));

		assertTrue(dfa.addTransition("G", "F", '1'));
		assertTrue(dfa.addTransition("G", "G", '2'));

		assertFalse(dfa.addTransition("FF", "F", '1'));
		assertFalse(dfa.addTransition("F", "GG", '2'));

		assertFalse(dfa.addTransition("G", "F", 'K'));
		assertFalse(dfa.addTransition("A", "K", '7'));

		return dfa;
	}

	@Test
	public void test3_1() {
		DFA dfa = dfa3();

		System.out.println("dfa3 instantiation pass");
	}

	@Test
	public void test3_2() {
		DFA dfa = dfa3();
		assertNotNull(dfa.getState("A"));
		assertNull(dfa.getState("K"));
		assertEquals(dfa.getState("C").getName(), "C");
		assertTrue(dfa.isStart("A"));
		assertFalse(dfa.isStart("D"));
		assertNotNull(dfa.getState("G"));
		assertEquals(dfa.getState("E").getName(), "E");
		assertTrue(dfa.isFinal("G"));
		assertFalse(dfa.isFinal("B"));
		assertEquals(dfa.getSigma(), Set.of('2', '1'));

		System.out.println("dfa3 correctness pass");
	}

	@Test
	public void test3_3() {
		DFA dfa = dfa3();
		assertTrue(dfa.accepts("121212121"));
		assertTrue(dfa.accepts("12221212121"));
		assertFalse(dfa.accepts("12"));
		assertFalse(dfa.accepts("2"));
		assertFalse(dfa.accepts("1212"));

		System.out.println("dfa3 accept pass");
	}

	@Test
	public void test3_4() {
		DFA dfa = dfa3();
		String dfaStr = dfa.toString();
		String expStr = "Q={GDABCEF}\n"
				+ "Sigma = {2 1}\n"
				+ "delta =\n"
				+ "	2	1\n"
				+ "G	G	F\n"
				+ "D	E	D\n"
				+ "A	C	B\n"
				+ "B	E	D\n"
				+ "C	G	F\n"
				+ "E	E	D\n"
				+ "F	G	F\n"
				+ "q0 = A\n"
				+ "F = {G D}\n";

		assertTrue(dfaStr.replaceAll("\\s", "").equals(expStr.replaceAll("\\s", "")));
		System.out.println("dfa3 toString pass");
	}

	@Test
	public void test3_5() {
		DFA dfa = dfa3();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfa != dfaSwap);
		assertTrue(dfa.getState("A") != dfaSwap.getState("A"));
		assertTrue(dfa.getState("G") != dfaSwap.getState("G"));
		assertTrue(dfa.getState("E") != dfaSwap.getState("E"));
		assertEquals(dfa.isStart("D"), dfaSwap.isStart("D"));
		assertEquals(dfa.isFinal("A"), dfaSwap.isFinal("A"));

		// transitions of the original dfa should not change
		assertTrue(dfa.accepts("121212121"));
		assertTrue(dfa.accepts("12221212121"));
		assertFalse(dfa.accepts("12"));
		assertFalse(dfa.accepts("2"));
		assertFalse(dfa.accepts("1212"));

		System.out.println("df31Swap instantiation pass");
	}

	@Test
	public void test3_6() {
		DFA dfa = dfa3();
		DFA dfaSwap = dfa.swap('2', '1');
		assertTrue(dfaSwap.accepts("212121212"));
		assertTrue(dfaSwap.accepts("21112121212"));
		assertFalse(dfaSwap.accepts("21"));
		assertFalse(dfaSwap.accepts("1"));
		assertFalse(dfaSwap.accepts("2121"));

		System.out.println("dfa3Swap accept pass");
	}


	private DFA stress() {
		DFA dfa = new DFA();
		dfa.addSigma('a');
		dfa.addSigma('b');
		dfa.addSigma('c');

		assertTrue(dfa.addState("s0"));
		assertTrue(dfa.addState("q0"));
		assertTrue(dfa.addState("q1"));
		assertTrue(dfa.addState("q2"));
		assertTrue(dfa.addState("q3"));
		assertTrue(dfa.addState("r0"));
		assertTrue(dfa.addState("r1"));
		assertTrue(dfa.addState("r2"));
		assertTrue(dfa.addState("r3"));
		assertFalse(dfa.addState("r1"));

		assertTrue(dfa.setFinal("q2"));
		assertTrue(dfa.setFinal("r2"));
		assertTrue(dfa.setFinal("s0"));

		assertTrue(dfa.setStart("s0"));
		assertTrue(dfa.setStart("r1"));
		assertTrue(dfa.setStart("s0"));

		assertTrue(dfa.addState("B"));
		assertTrue(dfa.addState("C"));
		assertTrue(dfa.addState("E"));
		assertTrue(dfa.addState("F"));

		assertFalse(dfa.addState("q0"));
		assertFalse(dfa.setFinal("w0"));
		assertFalse(dfa.setStart("DQ"));

		assertTrue(dfa.addTransition("s0", "q0", 'c'));
		assertTrue(dfa.addTransition("q0", "q1", 'a'));
		assertTrue(dfa.addTransition("q1", "q2", 'b'));
		assertTrue(dfa.addTransition("q2", "q3", 'a'));
		assertTrue(dfa.addTransition("q3", "q2", 'b'));
		assertTrue(dfa.addTransition("q2", "s0", 'c'));

		assertTrue(dfa.addTransition("s0", "r0", 'a'));
		assertTrue(dfa.addTransition("r0", "r1", 'a'));
		assertTrue(dfa.addTransition("r1", "r2", 'b'));
		assertTrue(dfa.addTransition("r2", "r3", 'a'));
		assertTrue(dfa.addTransition("r3", "r2", 'b'));
		assertTrue(dfa.addTransition("r2", "s0", 'c'));

		assertFalse(dfa.addTransition("FF", "F", 'a'));
		assertFalse(dfa.addTransition("F", "GG", 'b'));

		assertFalse(dfa.addTransition("G", "F", 'K'));
		assertFalse(dfa.addTransition("A", "K", '7'));

		return dfa;
	}

	@Test
	public void testStress_1() {
		DFA dfa = stress();

		System.out.println("dfa3 instantiation pass");
	}

	@Test
	public void testStress_2() {
		DFA dfa = stress();
		assertNotNull(dfa.getState("q0"));
		assertNull(dfa.getState("n0"));
		assertEquals(dfa.getState("r2").getName(), "r2");
		assertTrue(dfa.isStart("s0"));
		assertFalse(dfa.isStart("q3"));
		assertNotNull(dfa.getState("q1"));
		assertEquals(dfa.getState("q1").getName(), "q1");
		assertTrue(dfa.isFinal("s0"));
		assertTrue(dfa.isFinal("q2"));
		assertTrue(dfa.isFinal("r2"));
		assertFalse(dfa.isFinal("q3"));
		assertEquals(dfa.getSigma(), Set.of('a', 'b', 'c'));

		System.out.println("dfa3 correctness pass");
	}
	
	@Test
	public void testStress_3() {
		DFA dfa = stress();
		assertTrue(dfa.accepts("cab")); // reach q2 as final
		assertTrue(dfa.accepts("aababab")); // reach r2 as final
		assertTrue(dfa.accepts("aabababc")); // reach s0 as final
		assertTrue(dfa.accepts("aabababababccabababc")); // full loop. Reach q2
		assertFalse(dfa.accepts("b"));
		assertFalse(dfa.accepts("12"));

		System.out.println("dfa3 accept pass");
	}


	
	private DFA stressSwappable() {
		DFA dfa = new DFA();
		dfa.addSigma('a');
		dfa.addSigma('b');

		assertTrue(dfa.addState("s0"));
		assertTrue(dfa.addState("q0"));
		assertTrue(dfa.addState("q1"));
		assertTrue(dfa.addState("q2"));
		assertTrue(dfa.addState("q3"));
		assertTrue(dfa.addState("r0"));
		assertTrue(dfa.addState("r1"));
		assertTrue(dfa.addState("r2"));
		assertTrue(dfa.addState("r3"));
		assertFalse(dfa.addState("r1"));

		assertTrue(dfa.setFinal("q2"));
		assertTrue(dfa.setFinal("r2"));
		assertTrue(dfa.setFinal("s0"));

		assertTrue(dfa.setStart("s0"));
		assertTrue(dfa.setStart("r1"));
		assertTrue(dfa.setStart("s0"));

		assertTrue(dfa.addState("B"));
		assertTrue(dfa.addState("C"));
		assertTrue(dfa.addState("E"));
		assertTrue(dfa.addState("F"));

		assertFalse(dfa.addState("q0"));
		assertFalse(dfa.setFinal("w0"));
		assertFalse(dfa.setStart("DQ"));

		assertTrue(dfa.addTransition("s0", "q0", 'b'));
		assertTrue(dfa.addTransition("q0", "q1", 'a'));
		assertTrue(dfa.addTransition("q1", "q2", 'b'));
		assertTrue(dfa.addTransition("q2", "q3", 'a'));
		assertTrue(dfa.addTransition("q3", "q2", 'b'));
		assertTrue(dfa.addTransition("q2", "s0", 'b'));

		assertTrue(dfa.addTransition("s0", "r0", 'a'));
		assertTrue(dfa.addTransition("r0", "r1", 'a'));
		assertTrue(dfa.addTransition("r1", "r2", 'b'));
		assertTrue(dfa.addTransition("r2", "r3", 'a'));
		assertTrue(dfa.addTransition("r3", "r2", 'b'));
		assertTrue(dfa.addTransition("r2", "s0", 'b'));

		assertFalse(dfa.addTransition("FF", "F", 'a'));
		assertFalse(dfa.addTransition("F", "GG", 'b'));

		assertFalse(dfa.addTransition("G", "F", 'K'));
		assertFalse(dfa.addTransition("A", "K", '7'));

		return dfa;
	}

	@Test
	public void testStress2_1() {
		DFA dfa = stressSwappable();

		System.out.println("dfa3 instantiation pass");
	}

	@Test
	public void testStress2_2() {
		DFA dfa = stressSwappable();
		assertNotNull(dfa.getState("q0"));
		assertNull(dfa.getState("n0"));
		assertEquals(dfa.getState("r2").getName(), "r2");
		assertTrue(dfa.isStart("s0"));
		assertFalse(dfa.isStart("q3"));
		assertNotNull(dfa.getState("q1"));
		assertEquals(dfa.getState("q1").getName(), "q1");
		assertTrue(dfa.isFinal("s0"));
		assertTrue(dfa.isFinal("q2"));
		assertTrue(dfa.isFinal("r2"));
		assertFalse(dfa.isFinal("q3"));
		assertEquals(dfa.getSigma(), Set.of('a', 'b'));

		System.out.println("dfa3 correctness pass");
	}

	@Test
	public void testStress2_5() {
		DFA dfa = stressSwappable();
		DFA dfaSwap = dfa.swap('a', 'b');
		assertTrue(dfa != dfaSwap);
		assertTrue(dfa.getState("q0") != dfaSwap.getState("q0"));
		assertTrue(dfa.getState("s0") != dfaSwap.getState("s0"));
		assertTrue(dfa.getState("r3") != dfaSwap.getState("r3"));
		assertEquals(dfa.isStart("s0"), dfaSwap.isStart("s0"));
		assertEquals(dfa.isFinal("s0"), dfaSwap.isFinal("s0"));
		assertEquals(dfa.isFinal("q2"), dfaSwap.isFinal("q2"));
		assertEquals(dfa.isFinal("r2"), dfaSwap.isFinal("r2"));

		// transitions of the original dfa should not change
		assertTrue(dfa.accepts("aab"));
		assertTrue(dfaSwap.accepts("bba"));
		assertTrue(dfaSwap.accepts("bbababa")); // reach r2 as final
		assertTrue(dfaSwap.accepts("bbababaa")); // reach s0 as final
		assertTrue(dfaSwap.accepts("bbababababaaabababa")); // full loop. Reach r2
		assertFalse(dfa.accepts("bbababababaaabababa")); // full loop. Reach r2
		assertFalse(dfa.accepts("b"));
		assertFalse(dfa.accepts("12"));

		System.out.println("df31Swap instantiation pass");
	}
}
