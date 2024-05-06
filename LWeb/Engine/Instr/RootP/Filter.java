package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.FilterP.*;

public class Filter {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->PaletteSwap.getInst(i, c)      //1
            }, inst);
        
    }
}
