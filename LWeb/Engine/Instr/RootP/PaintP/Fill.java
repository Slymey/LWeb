package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.PaintP.FillP.*;

public class Fill {
    public static Runnable getInst(byte o[], Counter i, Core c){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i, c),     //0
                ()->Solid.getInst(o, i, c),      //1
                ()->Linear.getInst(o, i, c),      //2
                ()->Image.getInst(o, i, c),      //3
            }, inst);
        
    }
}
