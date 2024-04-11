package Engine.Instr.RootP;

import Common.Color;
import static Common.Color.color;
import Common.Common;
import static Common.Common.byteToInt;
import static Common.Common.clamp;
import Common.Counter;
import Engine.Core;
import Engine.Util.ColorMixers;
import Common.Range.Range;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class Stack {
    public static Runnable getInst(byte o[], Counter i){
        int source = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int target = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            BufferedImage bis = Core.buffers.get(source);
            BufferedImage bit = Core.buffers.get(target);
            int[] rgbArray=null;
            
            rgbArray = bis.getRGB(0, 0, bis.getWidth(), bis.getHeight(), rgbArray, 0, bis.getWidth());
            BiFunction<Color,Color,Color> mixer =ColorMixers.NORMAL.func;
            //BiFunction<Color,Color,Color> mixer = ColorMixers.MULTIPLY.func;
            for(int w:new Range(0,bis.getWidth()-1)){
                for(int h:new Range(0,bis.getHeight()-1)){
                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
                    int xt=clamp(w+x,0,bit.getWidth());
                    int yt=clamp(h+y,0,bit.getWidth());
                    bit.setRGB( xt, 
                                yt, 
                               mixer.apply(
                                      color(rgbArray[w+h*bis.getWidth()]),
                                      color(bit.getRGB(xt,yt))).color());
                }
            }
        }; 
    }
}
