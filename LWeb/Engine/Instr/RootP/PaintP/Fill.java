package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.PaintP.FillP.*;

public class Fill {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->Solid.getInst(i, c),      //1
                ()->Linear.getInst(i, c),      //2
                ()->Image.getInst(i, c),      //3
            }, inst);
        
    }
}
