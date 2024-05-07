package LWeb.Engine.Instr.RootP.FilterP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ib;
import LWeb.Engine.Core;


public class None {
    public static byte[] getBytes(){
        return ib(6, 0);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        return ()->{};
        
    }
}
