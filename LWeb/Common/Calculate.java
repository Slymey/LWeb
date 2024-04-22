package LWeb.Common;


public class Calculate {
    public enum Operation{NONE, ADD, SUB, NEGATE, MUL, DIV, INVERSE}
    TypeProvider value;
    Calculate val1;
    Calculate val2;
    Calculate par;
    Operation op;
    private boolean cache=false;
    
    Calculate(Calculate c1,Operation op,Calculate c2){c1.par=this;c2.par=this;val1=c1;this.op=op;val2=c2;}
    Calculate(TypeProvider tv){value=tv;this.op=Operation.NONE;}
    public TypeProvider get(){
        if(cache)return value;
        value = get1();
        return value;
    }
    private TypeProvider get1(){
        switch(op){
            case NONE:
                return value;
            case ADD:
                return val1.get().add(val2.get());
            case SUB:
                return val1.get().sub(val2.get());
            case NEGATE:
                return val1.get().negate();
            case MUL:
                return val1.get().mul(val2.get());
            case DIV:
                return val1.get().div(val2.get());
            case INVERSE:
                return val1.get().inverse();
            default:return null;
        }
    }
    public void invalidateCache(){
        cache=false;
        Calculate pr=par;
        while(pr!=null){
            pr.cache=false;
            pr=pr.par;
        }
    }
    
    
    Calculate create(String s){
        
        
        return null;
    }
}
