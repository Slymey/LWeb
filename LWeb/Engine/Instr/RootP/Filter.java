package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.FilterP.*;

public class Filter {
    public static Runnable getInst(byte o[], Counter i){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i),     //0
                ()->PaletteSwap.getInst(o, i)      //1
            }, inst);
        
    }
}
