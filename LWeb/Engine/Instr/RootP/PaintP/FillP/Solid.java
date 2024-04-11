package Engine.Instr.RootP.PaintP.FillP;

import Common.Color;
import Common.ColorSource;
import static Common.Common.byteToInt;
import Common.Counter;
import Engine.Core;
import Common.Range.Range;
import java.awt.image.BufferedImage;

public class Solid {
    public static Runnable getInst(byte o[], Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int color = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            BufferedImage bi = Core.buffers.get(id);
            for(int x:new Range(0,bi.getWidth()-1)){
                for(int y:new Range(0,bi.getHeight()-1)){
                    bi.setRGB(x, y, ((Color)((ColorSource)Core.resources.get(color).get()).get(0,0)).color());
                }
            }
        };
    }
}
