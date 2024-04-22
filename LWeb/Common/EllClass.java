package LWeb.Common;

import static LWeb.Common.EllClass.Activity.*;
import LWeb.Compiler.Parser;
import static LWeb.Compiler.Parser.TokenType.AMPERSANT;
import static LWeb.Compiler.Parser.TokenType.PERCENT;
import static LWeb.Compiler.Parser.TokenType.TEXT;
import static LWeb.Compiler.Parser.TokenType.WHITESPACE;
import static LWeb.Compiler.Parser.tokenize;
import java.util.HashMap;
import java.util.HashSet;


public class EllClass implements Comparable<EllClass> {

    //public static final HashMap<String,EllClass> listing = new HashMap<>();
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
    /*
    public void addClass(String classes){
        Pair<String[], Parser.TokenType[]> tk = tokenize(classes);
        String sa[] = tk.getFirst();
        Parser.TokenType ta[] = tk.getSecond();
        boolean pmk = false;
        Parser.TokenType ptr = null;
        for(int i=0;i < sa.length;i++){
            String sr = sa[i];
            Parser.TokenType tr = ta[i];
            if(i==0&&(!"\"".equals(sr)||!"'".equals(sr))){continue;}
            if(tr==AMPERSANT || tr==PERCENT){
                pmk = true;
            }else if(tr==TEXT){
                if(!pmk || ptr==WHITESPACE) {
                    addClass(new EllClass(sr, STATIC));
                }else if(pmk && ptr==AMPERSANT) {
                    addClass(new EllClass(sr, ENABLED));
                }else if(pmk && ptr==PERCENT) {
                    addClass(new EllClass(sr, DISABLED));
                }
                pmk = false;
            }
            ptr = tr;
        }
    }
    public static EllClass addClass(EllClass cs){
        listing.add(cs);
        return cs;
    }
    
    public int hashCode(){
        return name.hashCode();
    }*/
    public String toString(){
        return name+"+"+type;
    }
    public int compareTo(EllClass o) {
        return name.compareTo(o.name);
    }
}
