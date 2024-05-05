package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;

public abstract class Condition {
    public abstract boolean evaluate();
    
    public static Object getRsc(byte[] o, Counter i, Core c){
        byte subCond = o[i.inc()];
        Condition cond = null;
        switch (subCond) {
            case 1:{
                Condition subc = c.getResource(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}), Condition.class);
                return new NegativeCondition(subc);
            }
            case 2:{
                Callable calb = c.getResource(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}), Callable.class);
                return new CallbackCondition(calb);
            }
        }
        return null;
    }

    private static class NegativeCondition extends Condition{
        Condition cond;
        private NegativeCondition(Condition subc){
            cond = subc;
        }
        public boolean evaluate(){
            return !cond.evaluate();
        }
        
    }

    private static class CallbackCondition extends Condition{
        Callable calb;
        public CallbackCondition(Callable calb){
            this.calb=calb;
        }

        @Override
        public boolean evaluate(){
//            System.out.println(lognm()+"c.c e1");
            return (boolean)calb.call();
        }
    }
    
    
    
}
