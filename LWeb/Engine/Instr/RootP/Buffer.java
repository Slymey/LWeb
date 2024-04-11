package Engine.Instr.RootP;

import static Common.Common.byteToInt;
import Common.Counter;
import Engine.Core;
import Common.Range.Range;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;


public class Buffer {
    private static int baseColor= 0xffffffff;
    //------------
    
    public static Runnable getInst(byte[] o, Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int w = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int h = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        return () -> {//add cashing (check if buffer exists)
            BufferedImage bi=new BufferedImage(w,h,TYPE_INT_ARGB);
            for(int bw:new Range(0,bi.getWidth()-1)){
                for(int bh:new Range(0,bi.getHeight()-1)){
                    bi.setRGB(bw, bh, baseColor);
                }
            }
            Core.buffers.set(id, bi);
        };
    }
}
