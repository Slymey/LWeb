package LWeb.Engine.Instr.RootP.PaintP.FillP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Common.Range.Range;
import static LWeb.Engine.Constants.ConstTypes.LINEAR_GRADIENT_SHADER;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import java.awt.image.BufferedImage;

public class Linear {
    public static byte[] getBytes(int Bid, int Rgrad){
        return flatten(ib(2,1,2), itb(Bid), itb(Rgrad));
    }
    
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int color = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int color2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});//angle from two points
        int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int x2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int y2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        //int angle = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            
            FrameBuffer fb = c.getResource(id, FrameBuffer.class);
            Color cl = c.getResource(color, Color.class);
            Shader shader = c.getConstant(LINEAR_GRADIENT_SHADER, Shader.class)
                    .use()
                    .setUniformF("offset", cl.rf(), cl.gf(), cl.bf(), cl.af());
           
            fb.preferedLayout.draw();
            
            
//            BufferedImage bi = c.getResource(id, BufferedImage.class);
//            for(int xp:new Range(0,bi.getWidth()-1)){
//                for(int yp:new Range(0,bi.getHeight()-1)){
//                    bi.setRGB(xp, yp, color);
//                }
//            }
        };
    }
}
