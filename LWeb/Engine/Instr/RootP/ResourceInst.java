
package Engine.Instr.RootP;

import static Common.Common.byteToInt;
import static Common.Common.sg;
import Common.Counter;
import Common.Pair;
import Engine.Core;
import Engine.Instr.RootP.ResourceP.*;
import java.util.function.Supplier;

public class ResourceInst {
    public static Runnable getInst(byte o[], Counter i){
        int type = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int ind = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        Pair<Class, Object> rsc = sg((Supplier<Pair<Class, Object>>[])new Supplier[]{
                ()->None.getRsc(o, i),              //0
                ()->PlainText.getRsc(o, i),         //1
                ()->FontFace.getRsc(o, i),          //2
                ()->Palette.getRsc(o, i),           //3
                ()->URLAbsolute.getRsc(o, i),       //4
                ()->URLRelative.getRsc(o, i),       //5
                ()->ColorSource.getRsc(o, i)        //6
            }, type);
        Core.resources.set(ind, new Resource(rsc.getFirst(), rsc.getSecond()));
        return ()->{};
        
        
    }
}
