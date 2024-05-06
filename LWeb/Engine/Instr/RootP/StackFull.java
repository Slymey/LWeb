package LWeb.Engine.Instr.RootP;

import LWeb.Common.Color;
import static LWeb.Common.Color.color;
import LWeb.Common.Common;
import static LWeb.Common.Common.*;
import static LWeb.Common.Common.clamp;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.ColorMixers;
import LWeb.Common.Range.Range;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class StackFull {
    public static Runnable getInst(ByteCounter i, Core c){
        int source = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int target = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int mxr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});//color mixer
        int wdh = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});//cropping
        int hgh = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        float mx[][] = {
            {
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),//2d transfowm matrix ---wise
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),//origin is at top left of target (modifiable in buffer)
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()})//transforms pixel pos from final to original
            },{
                byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),//pixels computed at top left
                0,0
            },{
                0,0,1}
        };
        float ofx=byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
        float ofy=byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
        float stx[]=mxMuls(new float[]{1,0,0},mx);//stepsizes
        stx[0]/=stx[2];
        stx[1]/=stx[2];
        float sty[]=mxMuls(new float[]{0,1,0},mx);//stepsizes
        sty[0]/=sty[2];
        sty[1]/=sty[2];
        float sts[]=new float[]{ofx,ofy};//start pos
        //use mxMul tu cal new pos of pixel and area(1)*det(mx) for nex area(alpha) of pixel
        return () -> {
            BufferedImage bis = c.getResource(source, BufferedImage.class);
            BufferedImage bit = c.getResource(target, BufferedImage.class);
            int[] rgbArray=null;
            rgbArray = bis.getRGB(0, 0, bis.getWidth(), bis.getHeight(), rgbArray, 0, bis.getWidth());
            BiFunction<Color,Color,Color> mixer = ColorMixers.values()[mxr].func;
            //BiFunction<Color,Color,Color> mixer = ColorMixers.MULTIPLY.func;
            
//            for(int w:new Range(0,bis.getWidth()-1)){
//                for(int h:new Range(0,bis.getHeight()-1)){
//                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
//                    float xt=clamp(stx[0],0,bit.getWidth());
//                    float yt=clamp(sty[1],0,bit.getWidth());
//                    bit.setRGB( w, 
//                                h, 
//                               mixer.apply(
//                                      color(rgbArray[w+h*bis.getWidth()]),
//                                      color(bit.getRGB((int)xt,(int)yt))).color());
//                }
//            }
        }; 
    }
}
/*


*/