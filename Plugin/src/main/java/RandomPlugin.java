import java.util.Random;

public class RandomPlugin implements Plugin {
    @Override
    public void doUseful() {
        System.out.printf ("I'm %s v%d: %d loaded from %s \n", publicName(), version(), new Random().nextInt(), this.getClass().getClassLoader().getName());
    }

    @Override
    public String publicName() {
        return "RandomPlugin";
    }

    @Override
    public int version() {
        return 1;
    }
}
