package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Range.Range;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.Texture;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class BufferPtr {
    private static int baseColor= 0xffffffff;
    //------------
    
    public static Runnable getInst(byte o[], Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int pos = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        return () -> {//add cashing (check if buffer exists)
            IntPos wh=c.getResource(pos, IntPos.class);
            FrameBuffer fb = c.getResource(id, FrameBuffer.class);
            if(fb==null){fb = new FrameBuffer()
                    .attachTargetTexture(GL_COLOR_ATTACHMENT0, new Texture()
                        .setImage(wh)
                        .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                        .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR))
                    .attachVertexArray(FrameBuffer.deafaultLayout)
                    .attachShader(new Shader()
                        .addVertShader("basic.vert")
                        .addFragShader("basic.frag")
                        .initialize());
            }
            
//            BufferedImage bi=new BufferedImage(wh.x,wh.y,TYPE_INT_ARGB);
//            for(int bw:new Range(0,bi.getWidth()-1)){
//                for(int bh:new Range(0,bi.getHeight()-1)){
//                    bi.setRGB(bw, bh, baseColor);
//                }
//            }
            c.putResource(id, fb);
        };
    }
}
