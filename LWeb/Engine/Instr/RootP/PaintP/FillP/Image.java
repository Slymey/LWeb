package LWeb.Engine.Instr.RootP.PaintP.FillP;

import LWeb.Common.ColorSource;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Common.Range.Range;
import java.awt.image.BufferedImage;

public class Image {
    public static Runnable getInst(byte o[], Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int image = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            BufferedImage bi = Core.buffers.get(id);
            BufferedImage img = ((ColorSource)Core.resources.get(image).get()).getBuffer();
            for(int xp:new Range(0,bi.getWidth()-1)){
                for(int yp:new Range(0,bi.getHeight()-1)){
                    bi.setRGB(xp, yp, img.getRGB(xp, yp));
                }
            }
        };
    }
}
