package LWeb.Engine.Instr.RootP.PaintP.FillP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Common.Range.Range;
import static LWeb.Engine.Constants.ConstTypes.LINEAR_GRADIENT_SHADER;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import java.awt.image.BufferedImage;

public class Linear {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int color = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int color2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});//angle from two points
        int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int y2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        //int angle = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            
            FrameBuffer fb = c.getResource(id, FrameBuffer.class);
            Color cl = c.getResource(color, Color.class);
            Shader shader = c.getConstant(LINEAR_GRADIENT_SHADER, Shader.class)
                    .use()
                    .setUniformF("offset", cl.rf(), cl.gf(), cl.bf(), cl.af());
           
            fb.preferedLayout.draw();
            
            
            BufferedImage bi = c.getResource(id, BufferedImage.class);
            for(int xp:new Range(0,bi.getWidth()-1)){
                for(int yp:new Range(0,bi.getHeight()-1)){
                    bi.setRGB(xp, yp, color);
                }
            }
        };
    }
}
