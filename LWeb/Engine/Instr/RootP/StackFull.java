package Engine.Instr.RootP;

import Common.Color;
import static Common.Color.color;
import Common.Common;
import static Common.Common.*;
import static Common.Common.clamp;
import Common.Counter;
import Engine.Core;
import Engine.Util.ColorMixers;
import Common.Range.Range;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class StackFull {
    public static Runnable getInst(byte o[], Counter i){
        int source = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int target = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int mxr = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});//color mixer
        int wdh = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});//cropping
        int hgh = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        float mx[][] = {
            {
                byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}),//2d transfowm matrix ---wise
                byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}),//origin is at top left of target (modifiable in buffer)
                byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]})//transforms pixel pos from final to original
            },{
                byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}),//pixels computed at top left
                0,0
            },{
                0,0,1}
        };
        float ofx=byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        float ofy=byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        float stx[]=mxMuls(new float[]{1,0,0},mx);//stepsizes
        stx[0]/=stx[2];
        stx[1]/=stx[2];
        float sty[]=mxMuls(new float[]{0,1,0},mx);//stepsizes
        sty[0]/=sty[2];
        sty[1]/=sty[2];
        float sts[]=new float[]{ofx,ofy};//start pos
        //use mxMul tu cal new pos of pixel and area(1)*det(mx) for nex area(alpha) of pixel
        return () -> {
            BufferedImage bis = Core.buffers.get(source);
            BufferedImage bit = Core.buffers.get(target);
            int[] rgbArray=null;
            rgbArray = bis.getRGB(0, 0, bis.getWidth(), bis.getHeight(), rgbArray, 0, bis.getWidth());
            BiFunction<Color,Color,Color> mixer = ColorMixers.values()[mxr].func;
            //BiFunction<Color,Color,Color> mixer = ColorMixers.MULTIPLY.func;
            
            for(int w:new Range(0,bis.getWidth()-1)){
                for(int h:new Range(0,bis.getHeight()-1)){
                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
                    float xt=clamp(stx[0],0,bit.getWidth());
                    float yt=clamp(sty[1],0,bit.getWidth());
                    bit.setRGB( w, 
                                h, 
                               mixer.apply(
                                      color(rgbArray[w+h*bis.getWidth()]),
                                      color(bit.getRGB((int)xt,(int)yt))).color());
                }
            }
        }; 
    }
}
/*


*/