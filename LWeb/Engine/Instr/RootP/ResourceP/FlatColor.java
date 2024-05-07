package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color.IntColor;
import LWeb.Common.ByteCounter;
import LWeb.Common.Color;
import static LWeb.Common.Color.Color;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class FlatColor {
    public static ResourceInst.RByteCol getBytes(int color){
        return new ResourceInst.RByteCol(6,  itb(color));
    }
    public static ResourceInst.RByteCol getBytes(Color color){
        return new ResourceInst.RByteCol(6,  ib(color.ri()),ib(color.gi()),ib(color.bi()),ib(color.ai()));
    }
    public static ResourceInst.RByteCol getBytes(IntColor color){
        return new ResourceInst.RByteCol(6,  ib(color.ri()),ib(color.gi()),ib(color.bi()),ib(color.ai()));
    }
    public static ResourceInst.RByteCol getBytes(String color){
        Color cl = Color(color);
        return new ResourceInst.RByteCol(6,  ib(cl.ri()),ib(cl.gi()),ib(cl.bi()),ib(cl.ai()));
    }
    public static Object getRsc(ByteCounter i, Core c){
        return new IntColor(i.next(),i.next(),i.next(),i.next());
    }
}
