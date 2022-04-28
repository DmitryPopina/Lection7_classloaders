import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class Browser {


    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Object> pluginList = new HashMap<>();
        Plugin def = new BasicPlugin();
        pluginList.put(def.publicName(), def);
        PluginManager manager = new PluginManager("Plugins");
        while (true) {
            for (Object o : pluginList.values()){
                try {
                    Method m = o.getClass().getDeclaredMethod("doUseful");
                    m.invoke(o);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (manager.loadAll()){
                for (Map.Entry<String, Object> e : manager.getPluginList().entrySet()){
                    pluginList.merge(e.getKey(), e.getValue(), (oldPlugin, newPlugin) ->(oldPlugin = newPlugin));
                }
            }
            Thread.sleep(3000);
            System.out.println("Next hop");
        }
    }
}
