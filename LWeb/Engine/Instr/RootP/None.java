package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Engine.Core;


public class None {
    public static Runnable getInst(byte[] o, Counter i, Core c){
        return ()->{
            System.out.println(lognm()+"no-op");
        };
    }
}
