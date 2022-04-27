import java.io.File;
import java.net.MalformedURLException;

public class Browser {


    public static void main(String[] args) throws MalformedURLException {
        Plugin def = new BasicPlugin();
        PluginManager manager;// = new PluginManager("");
        manager = new PluginManager("plugins");
        manager.loadAll();
    }
}
