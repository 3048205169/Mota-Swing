package gamePanel;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

public class ClassUtils {
    // getClassName("top.lingkang.demohibernate.entity")
    public static Class[] getClassByPackage(String packageName) {
        try {
            Enumeration<URL> resources = ClassUtils.class.getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String[] file = new File(url.getFile()).list();
                Class[] classList = new Class[file.length];
                for (int i = 0; i < file.length; i++) {
                    classList[i] = Class.forName(packageName + "." + file[i].replaceAll("\\.class", ""));
                }
                return classList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Class[] classByPackage = getClassByPackage("GameObject.Characters.Enemy");
        for (Class aClass : classByPackage) {
            System.out.println(aClass.getName());
        }
    }
}

