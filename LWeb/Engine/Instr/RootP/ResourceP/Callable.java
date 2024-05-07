package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.bytesToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.istb;
import static LWeb.Common.Common.itb;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import java.util.Arrays;

public class Callable {
    public static ResourceInst.RByteCol getBytes(String name, int... Rresources){
        byte b[] = name.getBytes();
        byte r[] = istb(Rresources);
        return new ResourceInst.RByteCol(7,  itb(b.length), b, itb(Rresources.length), r);
    }
    public static Object getRsc(ByteCounter i, Core c){
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        String name =  new String(Arrays.copyOfRange(i.o, i.c, i.incp(length)));
        int rscLen = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int rsc[] =  bytesToInt(Arrays.copyOfRange(i.o, i.c, i.incp(rscLen*4)));
        return new Callable(c, name, rsc);
    }
    int parms[];
    Object rscb[];
    String funcid;
    Core c;
    public Callable(Core c, String func, int parms[]){
        this.parms=parms;
        this.funcid=func;
        this.c=c;
        this.rscb = new Object[parms.length];
    }
    public Object call(){
        for (int i = 0; i < rscb.length; i++) {
            rscb[i]=c.getResource(parms[i]);
        }
        return c.getCallable(funcid).apply(rscb);
    }
}
