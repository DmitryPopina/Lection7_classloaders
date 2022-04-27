import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;

public class PluginManager {
    private final String dir;
    private final HashMap<String, Integer> pluginVersions;
    private final HashMap<String, Plugin> pluginList;
    URLClassLoader loader;

    public PluginManager(String dir) {
        this.dir = dir;
        pluginList = new HashMap<>();
        pluginVersions = new HashMap<>();
        try {
            loader = new URLClassLoader(new URL[]{new URL(new File(dir).toURI().toURL().toExternalForm())}, null);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean processClassLoad(Class<Plugin> clazz){
        Plugin newPlugin;
        String className;
        int classVersion;
        try {
            newPlugin = clazz.getConstructor().newInstance();
            className = newPlugin.publicName();
            classVersion = newPlugin.version();
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (Plugin.class.isAssignableFrom(aClass)) {
            return processClassLoad((Class<Plugin>) aClass);
        }
        return false;
    }

    public void loadAll(){
        File file = new File(this.dir);
        String[] files = file.list();
        if (files != null) {
            for (String fileName : files) {
                if (fileName.endsWith(".class")) {
                    loadPlugin(fileName);
                }
            }
        }
    }

    public HashMap<String, Plugin> getPluginList() {
        return pluginList;
    }
}
