public class FinallyPhrase {
	String s;
 void finallyPhrase () { 
	 String s;
	 if (TheoremAboutText.m >100) {
		 s = "Странная демонстрация";
	 } else {
		 s= null;
	 }
	 if(s==null) throw new NumberlException("  Число не может быть меньше");
	 
	 
	 try{System.out.println(s);
	 s.length();
	 }catch(NumberlException e){
		
	 }
 }
}

class NumberlException extends NullPointerException{

	private static final long serialVersionUID = 1L;
    public NumberlException(String message){
      super(message);
    }
}

