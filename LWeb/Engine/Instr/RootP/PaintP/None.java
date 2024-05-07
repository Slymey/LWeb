package LWeb.Engine.Instr.RootP.PaintP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.vpb;
import LWeb.Engine.Core;


public class None {
    public static byte[] getBytes(){
        return ib(2, 0);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        return ()->{};
    }
}
