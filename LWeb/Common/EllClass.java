package Common;

import static Common.EllClass.Activity.*;


public class EllClass {
    public enum Activity{STATIC, ENABLED, DISABLED}
    final String name;
    private Activity type;
    public EllClass(String name, Activity type){
        this.name=name;
        this.type=type;
    }
    public Activity type(){
        return type;
    }
    public void activate(){
        type=ENABLED;
    }
    public void deactivate(){
        type=DISABLED;
    }
    public void toggleActive(){
        if(type==ENABLED)
            type=DISABLED;
        if(type==DISABLED)
            type=ENABLED;
    }
    public String toString(){
        return name+"+"+type;
    }
}
