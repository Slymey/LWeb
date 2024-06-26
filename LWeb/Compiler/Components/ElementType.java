package LWeb.Compiler.Components;

import static LWeb.Common.Common.sg;
import LWeb.Compiler.Components.StyleProperty.Property;
import java.util.HashMap;
import java.util.function.Consumer;

public class ElementType {
    boolean usable = true;
    String tag;
    //-- requirements[][];
    ElementType parents[];
    int closingType = 0;//0-/div, 1-reopening, 2-self closing
    ElementType autoClosingTags[];//must be of closing type 0 or 1
    boolean pseudo = false;
    Property style[];
    //String -> enum of events
    HashMap<String,Consumer> script = sg(()->{
        HashMap<String,Consumer> mp= new HashMap<String,Consumer>();
        mp.put("default", (Object o)->{});
        return mp;
    });
    
    
}
