public class BasicPlugin implements Plugin {
    private final String publicName = "BasicPlugin";
    private final int version = 1;
    @Override
    public void doUseful() {
        System.out.printf ("%s version %d", this.publicName, this.version);
    }

    @Override
    public String publicName() {
        return this.publicName;
    }

    @Override
    public int version() {
        return this.version;
    }
}
