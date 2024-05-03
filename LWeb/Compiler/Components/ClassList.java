package LWeb.Compiler.Components;

import java.util.HashMap;



public class ClassList{
    public static enum Activity{STATIC, ENABLED, DISABLED}
    private static final HashMap<String, ClassList> classes=new HashMap<>();
    final String name;
    private ClassList(String name){
        this.name=name;
    }
    public static ClassList makeClass(String name){
        ClassList get = classes.get(name);
        if(get!=null)return get;
        ClassList cl= new ClassList(name);
        classes.put(name, cl);
        return cl;
    }
    public static ClassList getClass(String name){
        return classes.get(name);
    }
    public int compareTo(ClassList o) {
        return name.compareTo(o.name);
    }
    public int hashCode(){
        return name.hashCode();
    }
}