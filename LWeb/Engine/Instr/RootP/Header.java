package Engine.Instr.RootP;

import static Common.Common.sg;
import Common.Counter;
import java.util.function.Supplier;
import Engine.Instr.RootP.HeaderP.*;

public class Header {
    public static Runnable getInst(byte o[], Counter i){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i),     //0
                ()->None.getInst(o, i)      //1
            }, inst);
        
    }
}
