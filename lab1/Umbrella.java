import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class Umbrella extends WoodenThings implements Open, Comparable<Umbrella>, Serializable {
    private static final long serialVersionUID = 2;

    private int id;
    private Color color;
    private String materialName;
    private String manufacturer;
    private GregorianCalendar date;


    public int getId() {
        return id;
    }
    public GregorianCalendar getDate(){return date;}
    public String getMaterialName() {
        return materialName;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public Color getColor(){
        return this.color;
    }
    public Integer getYear(){return date.get(Calendar.YEAR);}
    public Integer getMONTH(){return date.get(Calendar.MONTH);}
    public Integer getDay(){return date.get(Calendar.DAY_OF_MONTH);}

    public Umbrella(int id, Color color, String materialName, String manufacturer,GregorianCalendar date){
        super("Зонтик");
        this.id = id;
        this.color = color;
        this.materialName = materialName;
        this.manufacturer = manufacturer;
        this.date = date;
    }

//переопределенеи интерфейса Open

    @Override

    public void open() {

        this.state = STATE.OPENED;

        System.out.println("Зонтик открылся");

    }


    @Override
    public int hashCode() {
        int hash = 11;
        hash = hash * 13 + id;
        hash = hash * 13 + color.hashCode();
        hash = hash * 13 + manufacturer.hashCode();
        hash = hash * 13 + materialName.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Umbrella))
            return false;
        Umbrella other = (Umbrella) obj;
        return (other.id == id &&
                color.equals(other.color) &&
                manufacturer.equals(other.manufacturer) &&
                materialName.equals(other.materialName));
    }

    @Override
    public String toString(){
        return "ID: "+id +" Material: "+ materialName+ " Date ";
    }

    @Override
    public int compareTo(Umbrella o) {
        return ((Integer)id).compareTo(o.id);
    }
}