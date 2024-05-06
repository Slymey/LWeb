package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Common.Range.Range;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import LWeb.Engine.Instr.RootP.ResourceP.ResourcePointer;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.Texture;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;


public class Buffer {
    private static int baseColor= 0xffffffff;
    //------------
    
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int w = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int h = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int sizeRe = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return () -> {//add cashing (check if buffer exists)
            FrameBuffer fb = c.getResource(id, FrameBuffer.class);
            IntPos ip = c.getResource(sizeRe, IntPos.class);
            if(fb!=null&&ip!=null&&ip.xi()==w&&ip.yi()==h){
                fb.useWithClear();
                return;
            }
            c.putResource(sizeRe, new Position.IntPos(w, h));
            Shader sh;
            if(fb!=null){
                sh=fb.shader;
                fb.delete();
                fb.getTex().delete();
            }else{
                sh=new Shader()
                    .addVertShader("basic.vert")
                    .addFragShader("basic.frag")
                    .initialize();
            }
            fb = new FrameBuffer()
                .attachTargetTexture(GL_COLOR_ATTACHMENT0, new Texture()
                        .setImage(new ResourcePointer(sizeRe, c))
                    .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                    .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR))
                .attachVertexArray(FrameBuffer.deafaultLayout)
                .setClearColor(1,1,1,0)
                .attachShader(sh).useWithClear();
            
            
//            BufferedImage bi=new BufferedImage(w,h,TYPE_INT_ARGB);
//            for(int bw:new Range(0,bi.getWidth()-1)){
//                for(int bh:new Range(0,bi.getHeight()-1)){
//                    bi.setRGB(bw, bh, baseColor);
//                }
//            }
            c.putResource(id, fb);
        };
    }
}
