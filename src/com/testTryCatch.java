package com;

public class testTryCatch {

	public static void main(String[] args) {
		testTryCatch test = new testTryCatch();
		try {
			String ret = test.test();
			System.out.println("return String "+ ret);
		}catch(Exception e){
			e.printStackTrace();
		}catch (Throwable e){
			e.printStackTrace();
		}
	}
	
	public String test() throws Throwable {
		String retString = "start";
		try {
			//System.out.println("start String:" + retString);
			retString = "try inner ";
			throw new Exception("Exception inner");
			//return retString;
		} catch(Exception e) {
			retString = "catch inner";
			//System.out.println("catch String:" + retString);
			return retString;
		} finally {
			retString = "finally inner";
			//System.out.println("finally String :" + retString);
			return retString;
		}
	}
	

}
