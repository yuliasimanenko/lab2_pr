public class Shpunt extends Human {

    Shpunt(){

        super("Шпунтик");

    }

    Shpunt(String name) {

        super(name);

// TODO Auto-generated constructor stub

    }

    @Override

    void action() {

        System.out.println("Шпунтик сел за столик, скрючив ноги неестественным образом");

    }

}