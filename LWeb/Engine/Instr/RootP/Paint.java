package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.PaintP.Fill;
import LWeb.Engine.Instr.RootP.PaintP.None;
import LWeb.Engine.Instr.RootP.PaintP.String;
import java.util.function.Supplier;

public class Paint {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->Fill.getInst(i, c),      //1
                ()->String.getInst(i, c),      //2
            }, inst);
        
        
    }
}
