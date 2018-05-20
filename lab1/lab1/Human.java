package lab1;

public abstract class Human {

    protected String name;

    Human(String name){

        this.name = name;

    }

    abstract void action();

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Human)) return false;
        Human other  = (Human)obj;
        return name == other.name;
    }
}