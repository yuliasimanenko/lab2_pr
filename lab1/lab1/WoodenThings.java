package lab1;

import java.io.Serializable;

public abstract class WoodenThings implements Serializable {
    private static final long serialVersionUID = 2;


    protected String name; //название объекта

    protected STATE state; //состояние объекта

    WoodenThings(String name){ //конструктор с параметрами

        this.name = name;

        this.state = STATE.CLOSED;

    }

    public enum STATE {

        OPENED, CLOSED;

    }

//переопределяем и реализуем методы, указанные в задании

    @Override

    public String toString() {

        String s = new String();

        if (this.state == STATE.OPENED) {

            s = "opened";

        }

        else if (this.state == STATE.CLOSED) {

            s = "closed";

        }

        return "Название сооружения:" + this.name + "  Состояние:" + s;

    }

    @Override

    public int hashCode() {

        int sum = 0;

        for (int i = 0;i < this.name.length();i++) {

            sum += this.name.charAt(i);

        }

        if (this.state == STATE.OPENED)

            sum += 999;

        else

            sum += 777;

        return sum;

    }

    @Override

    public boolean equals(Object obj) {

        if (this == obj)

            return true;

        if (obj == null)

            return false;

        if (getClass() != obj.getClass())

            return false;

        WoodenThings other = (WoodenThings) obj;

        if (this.name != other.name)

            return false;

        if (this.state != other.state)

            return false;

        return true;

    }

    abstract public void open();
}
