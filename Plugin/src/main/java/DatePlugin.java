import java.time.LocalDateTime;

public class DatePlugin implements Plugin {
    @Override
    public void doUseful() {
        System.out.printf("I'm %s v%d: %s loaded from %s \n", publicName(), version(), LocalDateTime.now(), this.getClass().getClassLoader().getName());
    }

    @Override
    public String publicName() {
        return "DateIPlugin";
    }

    @Override
    public int version() {
        return 1;
    }
}
