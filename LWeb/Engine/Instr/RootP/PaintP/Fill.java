package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.PaintP.FillP.*;

public class Fill {
    public static Runnable getInst(byte o[], Counter i){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i),     //0
                ()->Solid.getInst(o, i),      //1
                ()->Linear.getInst(o, i),      //2
                ()->Image.getInst(o, i)      //3
            }, inst);
        
    }
}
