import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Umbrella extends WoodenThings implements Open, Comparable<Umbrella>, Serializable {
    private static final long serialVersionUID = 1;

    private int id;
    private Color color;
    private String materialName;
    private String manufacturer;
    private GregorianCalendar gr;

    public int getId() {
        return id;
    }
    public GregorianCalendar getGr(){
        return gr;
    }
    public String getMaterialName() {
        return materialName;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public Color getColor(){
        return this.color;
    }

    public Umbrella(int id, Color color, String materialName, String manufacturer,GregorianCalendar gr){
        super("Зонтик");
        this.id = id;
        this.color = color;
        this.materialName = materialName;
        this.manufacturer = manufacturer;
        this.gr= gr;
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
        return "ID: "+id +" Material: "+ materialName + "The country "+manufacturer+" date: "+gr.get(Calendar.YEAR) ;
    }

    @Override
    public int compareTo(Umbrella o) {
        return ((Integer)id).compareTo(o.id);
    }
}