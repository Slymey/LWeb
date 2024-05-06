package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;


public class None {
    public static Runnable getInst(ByteCounter i, Core c){
        return ()->{
            System.out.println(lognm()+"no-op");
        };
    }
}
