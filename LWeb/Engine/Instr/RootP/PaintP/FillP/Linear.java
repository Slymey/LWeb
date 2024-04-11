package Engine.Instr.RootP.PaintP.FillP;

import static Common.Common.byteToInt;
import Common.Counter;
import Engine.Core;
import Common.Range.Range;
import java.awt.image.BufferedImage;

public class Linear {
    public static Runnable getInst(byte o[], Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int color = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int color2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int y2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int angle = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            BufferedImage bi = Core.buffers.get(id);
            for(int xp:new Range(0,bi.getWidth()-1)){
                for(int yp:new Range(0,bi.getHeight()-1)){
                    bi.setRGB(xp, yp, color);
                }
            }
        };
    }
}
