package LWeb.Engine.Instr.RootP.FilterP;

import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;


public class None {
    public static Runnable getInst(ByteCounter i, Core c){
        return ()->{};
        
    }
}
