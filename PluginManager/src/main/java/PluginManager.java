import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class PluginManager {
    private final String dir;
    private final HashMap<String, Integer> pluginVersions;
    private final HashMap<String, Object> pluginList;
    URLClassLoader loader;

    public PluginManager(String dir) {
        this.dir = dir;
        pluginList = new HashMap<>();
        pluginVersions = new HashMap<>();
        try {
            loader = new URLClassLoader("MySuperLoader", new URL[]{new URL(new File(dir).toURI().toURL().toExternalForm())}, null);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean processClassLoad(Class<?> clazz){
        Object newPlugin;
        String className;
        int classVersion;
        try {
            newPlugin = clazz.getConstructor().newInstance();
            className = (String) clazz.getDeclaredMethod("publicName").invoke(newPlugin);
            classVersion = (int) clazz.getDeclaredMethod("version").invoke(newPlugin);
            if (!pluginVersions.containsKey(className)){
                pluginVersions.put(className, classVersion);
                pluginList.put(className, newPlugin);
                return true;
            }else {
                int oldVersion = pluginVersions.get(className);
                if (classVersion > oldVersion){
                    pluginVersions.put(className, classVersion);
                    pluginList.put(className, newPlugin);
                    return true;
                }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return  false;
    }

    boolean loadPlugin(String className){
        Class<?> aClass;
        try {
            aClass = loader.loadClass(className);
            if (aClass.isInterface()) {
                return false;
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            System.out.println(e.getMessage());
            return false;
        }
        return processClassLoad(aClass);
    }

    public boolean loadAll(){
        boolean newRes = false;
        File file = new File(this.dir);
        String[] files = file.list();
        if (files != null) {
            for (String fileName : files) {
                if (fileName.endsWith(".class") && !fileName.startsWith("Plugin")) {
                    if (loadPlugin(fileName.replace(".class", ""))){
                        newRes = true;
                    }
                }
            }
        }
        return newRes;
    }

    public HashMap<String, Object> getPluginList() {
        return pluginList;
    }
}
