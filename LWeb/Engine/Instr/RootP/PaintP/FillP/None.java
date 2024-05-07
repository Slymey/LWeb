package LWeb.Engine.Instr.RootP.PaintP.FillP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;


public class None {
    public static byte[] getBytes(){
        return ib(2, 1,0);
    }
    public static Runnable getInst(ByteCounter i){
        return ()->{};
    }
}
