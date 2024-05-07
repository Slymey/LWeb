package LWeb.Engine.Instr.RootP.PaintP.FillP;

import LWeb.Common.Color;
import LWeb.Common.ColorSource;
import LWeb.Common.Common;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
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
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

public class Solid {
    public static byte[] getBytes(int Bid, int Rcolor){
        return flatten(ib(2,1,1), itb(Bid), itb(Rcolor));
    }
    
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int color = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            FrameBuffer fb = c.getResource(id, FrameBuffer.class).use();
            Color cl = c.getResource(color, Color.class);
//            System.out.println(lognm()+""+cl);
            Shader shader = c.getConstant(LINEAR_GRADIENT_SHADER, Shader.class)
                    .use()
                    .setUniformF("offset", cl.rf(), cl.gf(), cl.bf(), cl.af());
           
            fb.preferedLayout.draw();
//            ByteBuffer bb = ByteBuffer.allocate(1000*1000);
//            glGetTexImage(fb.getTex().TEX, 0, GL_RGBA, GL_UNSIGNED_BYTE, bb);
//            System.out.println(lognm()+""+Common.ats(bb.array()));
//            BufferedImage bi = c.getResource(id, BufferedImage.class);
//            for(int x:new Range(0,bi.getWidth()-1)){
//                for(int y:new Range(0,bi.getHeight()-1)){
//                    bi.setRGB(x, y, ((Color)(c.getResource(color, ColorSource.class)).get(0,0)).color());
//                }
//            }
        };
    }
}
