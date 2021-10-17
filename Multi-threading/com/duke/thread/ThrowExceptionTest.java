package com.duke.thread;

class Test extends Exception
{
	
}

public class ThrowExceptionTest {

	public static void main(String[] args) {
		
		try {
			throw new Test();
		} catch (Test e) {
			System.out.println("catched test exception");
		}
		finally {
			System.out.println("finally block");
		}
		
	}
}
