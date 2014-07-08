package common;

public class ParseException extends Exception{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		try{
			throw new ParseException();
			//System.out.println("no Exception");
		}catch(ParseException e){
			System.out.println("no ParseException");
		}catch(Exception e){
			System.out.println("no Exception");
		}
	}
}
