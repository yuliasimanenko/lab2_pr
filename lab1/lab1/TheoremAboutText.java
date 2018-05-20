package lab1;

import java.util.Scanner;

class NewException extends Exception{

	private static final long serialVersionUID = 1L;
	public NewException(String message){
        super(message);
    }
}


public class TheoremAboutText {
	static int m =0;
 public static void main () {
	System.out.println("����� �����, ������ �� �� ������ �������");
	@SuppressWarnings("resource")
	Scanner n = new Scanner(System.in);
	 m= n.nextInt();
	 Printer.printText();  //���������� ��� �����������
	 
	 NumberOfTheorem number = new NumberOfTheorem () {     //��� ��� ��������� �����
		 void randomNumber (int k) { 
			 if(k <= 0) throw new IllegalArgumentException("�������� ������������" + k);
			 k= (int)(Math.random()*k+1);
			 System.out.println(k);
			 
		 }
	 };
	 try { 
	 number.randomNumber(m);
	 }catch(IllegalArgumentException e) {
		m=Math.abs(m);
		System.out.println(m+ " (P.S �������� ����� ���� �������� �� ����������� ������ �������������)");
	 }
	 FinallyString end = new FinallyString();
	 end.choise(); 
	 
 }
 
 static class Printer {
	static void printText() {
		 System.out.println("������� �� ����� ���������");
		 class Local {
			  void method(){
			 System.out.print("�� ��� ����� ���� � ����,� ��� ��� ������ ����� �");
			 }
		 }
		 Local loc = new Local();
		 loc.method();
		 }
	 }
 }


 class NumberOfTheorem {
	  int k =0;
	 void  randomNumber  (int k) {
		 k= (int)(Math.random()*k);
		System.out.print(k);
		
	 }
	 void poem () throws NewException {
		 if (k<=0) throw new NewException("����� �� ������ ���� ���� ������ ����");
		 String str = "������������� �� �������,���� �� ��������";
		 System.out.println(str);
	 }	
}
 
 
 
  

