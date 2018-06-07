package lab1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Umbrella extends WoodenThings implements Open, Comparable<Umbrella>, Serializable {
    private static final long serialVersionUID = 1;

    @ORMField(fieldName = "color")
    private Color color;
    @ORMField(fieldName = "manufacturer")
    private String manufacturer;
    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public static void setYear(Integer i,Umbrella u){
        LocalDateTime time=u.getDateTime();
        time.withDayOfYear(i);

    }

    public String getManufacturer(){
        return manufacturer;
    }
    public Color getColor(){
        return this.color;
    }

    public Umbrella(Color color, String manufacturer,LocalDateTime dateTime){
        super("Зонтик");
        this.color = color;
        this.manufacturer = manufacturer;
        this.dateTime = dateTime;
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
        hash = hash * 13 + this.getDateTime().getYear();
        hash = hash * 13 + color.hashCode();
        hash = hash * 13 + manufacturer.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Umbrella))
            return false;
        Umbrella other = (Umbrella) obj;
        return (other.getDateTime().getYear() == this.getDateTime().getYear() &&
                color.equals(other.color) &&
                manufacturer.equals(other.manufacturer));
    }

    @Override
    public String toString(){
        return  "The country "+manufacturer+" date: "+dateTime.toString() ;
    }

    @Override
    public int compareTo(Umbrella o) {
        Integer year = o.getDateTime().getYear();
        Integer thisyear = this.getDateTime().getYear();
        return thisyear.compareTo(year);
        //return ((Integer)id).compareTo(o.id);
    }
}