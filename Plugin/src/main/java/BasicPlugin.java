public class BasicPlugin implements Plugin {
    private final String publicName = "BasicPlugin";
    private final int version = 2;
    @Override
    public void doUseful() {
        System.out.printf ("I'm %s v%d loaded from %s \n", this.publicName, this.version, this.getClass().getClassLoader().getName());
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
