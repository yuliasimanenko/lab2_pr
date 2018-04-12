public class Author extends Human{
    @Override
    void action() {
        throw new RuntimeException();
    }
    public Author(String name){
        super(name);
    }
    public String replica(Phrase phrase){
    	throw new RuntimeException();
    	}
}