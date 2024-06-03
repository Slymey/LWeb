
package LWeb.Engine.Instr.RootP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.Root;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class CopyResource {
    public static byte[] getBytes(int Rfrom, int Rto){
        return flatten(ib(27),itb(Rfrom), itb(Rto));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int from = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int to = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            //System.out.println("copy");
            c.putResource(to, c.getResource(from));
        };
    }
}
