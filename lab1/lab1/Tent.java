package lab1;

public class Tent extends WoodenThings implements Open {

	public @interface NewAnnotation{
		String word ();
		int TheNamberOfActions () default 0;
		boolean UseInProgramm () default true;
	}
    Tent(){

        super("Палатка");

    }

//реализация метода из интерфейса lab1.Open

    @Override
   @NewAnnotation (word = "это моя аннотация", TheNamberOfActions =3) public void open() {

        this.state = STATE.OPENED;

        System.out.println("Палатка открылась");

    }
    
}