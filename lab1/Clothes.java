public class Clothes extends Fabric implements Look {
    @Override
    public void look() {
        throw new RuntimeException();
    }

    @Override
    public void putOn() {
        throw new RuntimeException();
    }
}
