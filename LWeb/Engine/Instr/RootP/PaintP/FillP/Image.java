package Engine.Instr.RootP.PaintP.FillP;

import Common.ColorSource;
import static Common.Common.byteToInt;
import Common.Counter;
import Engine.Core;
import Common.Range.Range;
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
