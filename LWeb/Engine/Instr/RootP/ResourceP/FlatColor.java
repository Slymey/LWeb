package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color.IntColor;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;

public class FlatColor {
    public static Object getRsc(ByteCounter i, Core c){
        return new IntColor(i.next(),i.next(),i.next(),i.next());
    }
}
