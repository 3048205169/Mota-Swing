import GameObject.Characters.Enemy.Monster;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Locale;

public class testing {
    public static void main(String[] args) {
        Boolean b=null;
        try{
            b = Monster.class.isAssignableFrom(Class.forName("GameObject.Characters.Enemy."+StringUtils.capitalize("skeleton")));
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(b);
    }
}
