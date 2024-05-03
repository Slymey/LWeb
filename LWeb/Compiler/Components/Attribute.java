package LWeb.Compiler.Components;

import static LWeb.Compiler.Components.Attribute.CheckType.*;
import LWeb.Common.Common.Troolean;
import static LWeb.Common.Common.Troolean.False;
import static LWeb.Common.Common.Troolean.None;
import java.util.function.Predicate;
import java.util.regex.Pattern;


public abstract class Attribute {
    public static void main(String[] args) {
        System.out.println("txrczktvuglbhčnjoćm"
                + "čkjnhbigtzuvfrd");
    }
    public final String name;
    //                           =     ~=     |=      ^=    $=    *=      =/
    public enum CheckType{NONE, FULL, WORD, HYPHEN, PREF, SUFF, CONTAIN, REGEX};
    public static final Attribute attr = new Attribute(""){};
    public static AttributeEl newAttribute(String name, String data){//element parsing
        AttributeEl out = null;
        
        
        out = new AttributeText(name, data);
        return out;
    }
    public static AttributeStyle newAttribute(String name, String data, CheckType ct, Troolean caseSens){//css parsing
        AttributeStyle out = null;
        if(!"".equals(name)){
            if(ct==NONE){
                out = new AttributeExists(name);
            }else{
                out = new AttributeTextMatch(name, data, ct, caseSens);
            }
        }else{
            out = new AttributeTrue();
        }
        return out;
    }
    public Attribute(String name){this.name = name;}
    public static class AttributeEl extends Attribute{
        AttributeEl(String name){
            super(name);
        }
    }
    public static abstract class AttributeStyle extends Attribute{
        public final Troolean caseSensitivity;
        public final CheckType ct;
        AttributeStyle(String name, CheckType ct, Troolean caseSensitivity){super(name);this.ct=ct;this.caseSensitivity=caseSensitivity;}
        public abstract boolean equals(AttributeEl o);
    }
    
    
    public static class AttributeText extends AttributeEl{
        public final String data;
        public AttributeText(String name, String data){
            super(name);
            this.data=data;
        }
        @Override
        public String toString(){
            return name+":"+data;
        }
    }
    public static class AttributeTrue extends AttributeStyle{
        public AttributeTrue(){
            super("", NONE, None);
        }
        @Override
        public boolean equals(AttributeEl o) {
            return true;
        }
        public String toString(){return "["+name+"]";}
    }
    public static class AttributeExists extends AttributeStyle{
        public AttributeExists(String name) {
            super(name, NONE, None);
        }
        @Override
        public boolean equals(AttributeEl o) {
            return name.equals(o.name);
        }
        public String toString(){return "["+name+"]";}
    }
    public static class AttributeTextMatch extends AttributeStyle{
        private final Predicate<String> pattern;
        private final Pattern ppt;
        public AttributeTextMatch(String name, String data, CheckType ct, Troolean caseSens){
            super(name, ct, caseSens);
            int flg=caseSens==None
                    ?Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE 
                    :(caseSens==False
                        ?Pattern.CASE_INSENSITIVE  | Pattern.UNICODE_CASE
                        :0);
            if(ct!=REGEX){
                data= data.replaceAll("\\\\E", "\\E\\Q");
            }
            switch(ct){
                case FULL:
                    this.ppt = Pattern.compile( "^\\Q"+data+"\\E$",flg);
                break;case WORD:
                    this.ppt = Pattern.compile( "(^\\Q"+data+"\\E$)|(^\\Q"+data+"\\E\\s)|(\\s\\Q"+data+"\\E$)|(\\s\\Q"+data+"\\E\\s)",flg);
                break;case HYPHEN:
                    this.ppt = Pattern.compile( "^\\s*\\Q"+data+"\\E-",flg);
                break;case PREF:
                    this.ppt = Pattern.compile( "^\\Q"+data, flg);
                break;case SUFF:
                    this.ppt = Pattern.compile( data+"\\E$",flg);
                break;case CONTAIN:
                    this.ppt = Pattern.compile( "\\Q"+data+"\\E",flg);
                break;case REGEX:
                    this.ppt = Pattern.compile( data,flg);
                break;default: this.ppt = Pattern.compile( "",flg);
            }
            this.pattern=ppt.asPredicate();
        }

        public boolean equals(AttributeEl o){
            if(ct==NONE)return true;
            if(!(o instanceof AttributeText))return false;
            return pattern.test(((AttributeText)o).data);//do smart comparison
        }
        @Override
        public String toString(){
            return name+":"+ct+"="+ppt;
        }
    }

}
