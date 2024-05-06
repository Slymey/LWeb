package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.HeaderP.*;

public class Header {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->Window.getInst(i, c),      //1
                ()->Windowf.getInst(i, c),     //2
                ()->Screen.getInst(i, c)     //2
            }, inst);
        
    }
}
