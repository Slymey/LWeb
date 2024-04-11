package Common;


public class Calculate {
    TypeProvider value;
    Calculate val1;
    Calculate val2;
    Operation op;
    
    Calculate(Calculate c1,Operation op,Calculate c2){val1=c1;this.op=op;val2=c2;}
    Calculate(TypeProvider tv){value=tv;this.op=Operation.NONE;}
    TypeProvider get(){
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
    TypeProvider cache(){
        value = get();
        return value;
    }
    
    
    enum Operation{
        NONE,
        ADD,
        SUB,
        NEGATE,
        MUL,
        DIV,
        INVERSE;
        
    }
    
    Calculate create(String s){
        
        
        return null;
    }
}
