public class Smekajlo extends Human implements Push{

    Smekajlo(){
        super("Смекайло");
    }

    @Override
    public void action() {

        System.out.println("Смекайло показал посетителям какое-то неуклюжее сооружение");
        System.out.println("Похожее на необычный инструмент напоминающий зонтик");

    }

    @Override
    public void pushButton(WoodenThings obj) {

        System.out.println("Смекайло нажал кнопку на ручке");

        obj.open();

    }
    public void reaction(){throw new RuntimeException();}
}