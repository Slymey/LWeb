package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.PaintP.*;
import LWeb.Engine.Instr.RootP.PaintP.None;

import java.util.function.Supplier;

public class Paint {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->Fill.getInst(i, c),      //1
                ()->DrawString.getInst(i, c),      //2
                ()->DrawString.getInst(i, c),      //3
                ()->Line.getInst(i, c),      //4
                ()->Bezier.getInst(i, c),      //5
                ()->DrawCurve.getInst(i, c),      //6
            }, inst);
        
        
    }
}
