public class DeskAndChair extends WoodenThings implements Open{

    DeskAndChair(){

        super("Стол и стульчик");

    }

//реализация метода из интерфейса Open

    @Override

    public void open() {

        this.state = STATE.OPENED;

        System.out.println("Стол и стульчик открылись!");

    }

}