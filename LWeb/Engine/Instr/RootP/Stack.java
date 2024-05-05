package LWeb.Engine.Instr.RootP;

import LWeb.Common.Color;
import static LWeb.Common.Color.color;
import LWeb.Common.Common;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.clamp;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.ColorMixers;
import LWeb.Common.Range.Range;
import LWeb.Engine.Constants;
import LWeb.Engine.Instr.RootP.ResourceP.BlendMode;
import LWeb.Engine.Instr.RootP.ResourceP.Box;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.VertexArray;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class Stack {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int source = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int target = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int box = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int blm = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            FrameBuffer sfb = c.getResource(source, FrameBuffer.class);
            FrameBuffer tfb = c.getResource(target, FrameBuffer.class);
            Box locs = c.getResource(box, Box.class);
            locs.p=tfb.getTex().p;
            
            c.getResource(blm, BlendMode.class).setBlendMode();
            tfb.draw(sfb.getTex(),
                    c.getConstant(Constants.ConstTypes.BOX_SHADER, Shader.class)
                            .use().setUniformF("box", locs.xf(), locs.yf(), locs.zf(), locs.wf()), 
                    c.getConstant(Constants.ConstTypes.BASIC_QUAD, VertexArray.class));
            
            
            
            
//            BufferedImage bis = c.getResource(source, BufferedImage.class);
//            BufferedImage bit = c.getResource(target, BufferedImage.class);
//            int[] rgbArray=null;
//            
//            rgbArray = bis.getRGB(0, 0, bis.getWidth(), bis.getHeight(), rgbArray, 0, bis.getWidth());
//            BiFunction<Color,Color,Color> mixer =ColorMixers.NORMAL.func;
//            //BiFunction<Color,Color,Color> mixer = ColorMixers.MULTIPLY.func;
//            for(int w:new Range(0,bis.getWidth()-1)){
//                for(int h:new Range(0,bis.getHeight()-1)){
//                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
//                    int xt=clamp(w+x,0,bit.getWidth());
//                    int yt=clamp(h+y,0,bit.getWidth());
//                    bit.setRGB( xt, 
//                                yt, 
//                               mixer.apply(
//                                      color(rgbArray[w+h*bis.getWidth()]),
//                                      color(bit.getRGB(xt,yt))).color());
//                }
//            }
        }; 
    }
}
