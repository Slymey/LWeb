
package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.*;
import LWeb.Engine.Instr.RootP.ResourceP.None;
import LWeb.Engine.Instr.RootP.ResourceP.Number;
import java.util.function.Supplier;

public class ResourceInst {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int type = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int ind = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        Object rsc = sg((Supplier<Pair<Class, Object>>[])new Supplier[]{
                ()->None.getRsc(o, i, c),              //0
                ()->PlainText.getRsc(o, i, c),         //1
                ()->FontFace.getRsc(o, i, c),          //2
                ()->Palette.getRsc(o, i, c),           //3
                ()->URLAbsolute.getRsc(o, i, c),       //4
                ()->URLRelative.getRsc(o, i, c),       //5
                ()->FlatColor.getRsc(o, i, c),        //6
                ()->Callable.getRsc(o, i, c),       //7
                ()->Loop.getRsc(o, i, c),       //8
                ()->Number.getRsc(o, i, c),       //9
                ()->WholeNumber.getRsc(o, i, c),       //10
                ()->Condition.getRsc(o, i, c),       //11
                ()->ResourcePointer.getRsc(o, i, c),       //12
                ()->ImageFile.getRsc(o, i, c),       //13
                ()->ImageBuffer.getRsc(o, i, c),       //14
                ()->LinearGradient.getRsc(o, i, c),       //15
                ()->Position.FloatPos.getRsc(o, i, c),       //16
                ()->Position.IntPos.getRsc(o, i, c),       //17
                ()->Box.FloatBox.getRsc(o, i, c),       //18
                ()->Box.IntBox.getRsc(o, i, c),       //19
                ()->BlendMode.getRsc(o, i, c),       //20
            }, type);
        c.putResource(ind, rsc);
        return ()->{};
        
        
    }
}
