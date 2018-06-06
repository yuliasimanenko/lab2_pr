package lab1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Umbrella extends WoodenThings implements Open, Comparable<Umbrella>, Serializable {
    private static final long serialVersionUID = 1;

    @ORMField(fieldName = "color")
    private Color color;
    @ORMField(fieldName = "manufacturer")
    private String manufacturer;
    private GregorianCalendar gr;

    public int getYear() {
        return this.getGr().get(Calendar.YEAR);
    }

    public void setYear(int value) {
        gr.set(Calendar.YEAR, value);
    }

    public GregorianCalendar getGr(){
        return gr;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public Color getColor(){
        return this.color;
    }

    public Umbrella(Color color, String manufacturer,GregorianCalendar gr){
        super("Зонтик");
        this.color = color;
        this.manufacturer = manufacturer;
        this.gr= gr;
    }

//переопределенеи интерфейса lab1.Open

    @Override

    public void open() {

        this.state = STATE.OPENED;

        System.out.println("Зонтик открылся");

    }


    @Override
    public int hashCode() {
        int hash = 11;
        hash = hash * 13 + this.getYear();
        hash = hash * 13 + color.hashCode();
        hash = hash * 13 + manufacturer.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Umbrella))
            return false;
        Umbrella other = (Umbrella) obj;
        return (other.getYear() == this.getYear() &&
                color.equals(other.color) &&
                manufacturer.equals(other.manufacturer));
    }

    @Override
    public String toString(){
        return  "The country "+manufacturer+" date: "+gr.get(Calendar.YEAR) ;
    }

    @Override
    public int compareTo(Umbrella o) {
        Integer year = o.getGr().get(Calendar.YEAR);
        Integer thisyear = this.getGr().get(Calendar.YEAR);
        return thisyear.compareTo(year);
        //return ((Integer)id).compareTo(o.id);
    }
}