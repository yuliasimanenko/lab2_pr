public class MyBean {

    private String name;
    private String size;
    private String description;

    public MyBean(String name, String size, String description) {
        this.setName(name);
        this.setSize(size);
        this.setDescription(description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
