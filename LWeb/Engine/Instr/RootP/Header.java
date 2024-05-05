package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.HeaderP.*;

public class Header {
    public static Runnable getInst(byte o[], Counter i, Core c){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i, c),     //0
                ()->Window.getInst(o, i, c),      //1
                ()->Windowf.getInst(o, i, c),     //2
                ()->Screen.getInst(o, i, c)     //2
            }, inst);
        
    }
}
