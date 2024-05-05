package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color.IntColor;
import LWeb.Common.Counter;
import LWeb.Engine.Core;

public class FlatColor {
    public static Object getRsc(byte[] o, Counter i, Core c){
        return new IntColor(o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]);
    }
}
