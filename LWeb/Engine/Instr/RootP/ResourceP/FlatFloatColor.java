package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color.IntColor;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Color.Color;
import LWeb.Common.Color.FloatColor;
import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Color.Color;
import LWeb.Common.Color;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class FlatFloatColor {
    public static ResourceInst.RByteCol getBytes(float r, float g, float b, float a){
        return new ResourceInst.RByteCol(21, ftb(r), ftb(g), ftb(b), ftb(a));
    }
    public static ResourceInst.RByteCol getBytes(FloatColor color){
        return new ResourceInst.RByteCol(21, ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
    }
    public static ResourceInst.RByteCol getBytes(String color){
        Color cl = Color(color);
        return new ResourceInst.RByteCol(21, ftb(cl.rf()),ftb(cl.gf()),ftb(cl.bf()),ftb(cl.af()));
    }
    
    public static Object getRsc(ByteCounter i, Core c){
        return new FloatColor(
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}));
    }
}
