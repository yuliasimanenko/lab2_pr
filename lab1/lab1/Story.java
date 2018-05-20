package lab1;

public class Story {
    public static void story() {
        Smekajlo me = new Smekajlo();

        Shpunt sh = new Shpunt();

        me.action();

        WoodenThings obj = new DeskAndChair();

        WoodenThings o = new Tent();

        me.pushButton(obj);

        sh.action();

        //collection.consolOut();

        System.out.println(obj.toString());

        System.out.println("Хэш-код объекта 'Стол и стульчик': " + o.hashCode());

        System.out.println("Хэш-код объекта 'Тент':  " + o.hashCode());

        System.out.println("Сравнение объектов 'Стол и стульчик' и 'Палатка':  " + o.equals(obj));

        System.out.println("Метод toString объекта 'Тент':  " + o.toString());


    }
}
