package LWeb.Engine.Instr.RootP.PaintP;

import LWeb.Common.Counter;
import LWeb.Engine.Core;


public class None {
    public static Runnable getInst(byte[] o, Counter i, Core c){
        return ()->{};
    }
}
