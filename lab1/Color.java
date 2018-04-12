import java.io.Serializable;

public class Color implements Serializable {
    private static final long serialVersionUID = 2;

    private int r, g, b;
    public Color (int red,int green,int blue){
        r = red;
        g = green;
        b = blue;
    }
    public Integer getR(){
        return this.r;
    }
    public Integer getG(){
        return this.g;
    }
    public Integer getB(){
        return this.b;
    }

    @Override
    public  int hashCode(){
        int hash = 11;
        hash = hash*13+r;
        hash = hash*13+g;
        hash = hash*13+b;
        return hash;
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Color))
            return false;
        Color other = (Color) obj;
        return (other.r == r) &&
                (other.g == g ) &&
                (other.b == b );
    }
    // переопределить getHashcode equels
    //как umbrella только для параметров color
}
