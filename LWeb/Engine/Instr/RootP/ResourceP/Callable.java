package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.bytesToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import java.util.Arrays;

public class Callable {
    public static Object getRsc(byte[] o, Counter i, Core c){
        int length = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        String name =  new String(Arrays.copyOfRange(o, i.c, i.incp(length)));
        int rscLen = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int rsc[] =  bytesToInt(Arrays.copyOfRange(o, i.c, i.incp(rscLen*4)));
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
