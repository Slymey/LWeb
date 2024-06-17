package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sopl;
import LWeb.Common.ByteCounter;
import LWeb.Common.Color;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.sizeof;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import LWeb.Engine.Instr.RootP.ResourceP.FontFace;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FontPainter;
import LWeb.Engine.Util.GLEU.FontPainter.RenderedString;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;

public class DrawString {
    public static byte[] getBytes(int Bid, int Rtext, int Rfont, int Rpos, int Rcolor){
        return flatten(ib(2,2), itb(Bid), itb(Rtext), itb(Rfont), itb(Rpos), itb(Rcolor));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int text = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int font = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pos = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            FrameBuffer sfb = c.getResource(id, FrameBuffer.class);
            String txt = c.getResource(text, String.class);
            FontPainter fp = c.getResource(font, FontFace.class).getPainter();
            Position p = c.getResource(pos, Position.class);
            Color cl = c.getResource(col, Color.class);
            Position vb = c.creenSize;//sfb.getTex().p.resolve(Position.class);
            Position vp = sfb.getTex().p.resolve(Position.class);
//            System.out.println(lognm()+""+text);
            sfb.use();
            p.p=vb;
            FontPainter.viewBox=Pair(vb.xi(), vb.yi());
            RenderedString str = fp.prepareText(txt).getString();
            
            float w = ((float)str.box.getWidth())/vb.xi();
            float h = ((float)str.box.getHeight())/vb.yi();
//            sopl(lognm()+" x:"+p.xi()+" y:"+p.yi()+" w:"+vb.xi()+" h:"+vb.yi()+" "+vp.xi()+" "+vp.yi()+" "+str.box.getWidth()+" "+str.box.getHeight());
            glEnable(GL_BLEND);
            glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            
            //GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA
            fp.fontShader.use();
            fp.fontShader.setUniformI("text", 0);
            fp.fontShader.setUniformF("paintBox", p.xf(), p.yf(), w, h);
            fp.fontShader.setUniformF("fontColor", cl.rf(),cl.gf(),cl.bf(),cl.af());
            fp.fontBox.bind();
            str.tex.activateOn(0);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            
            //fp.draw(txt, p.xi(), p.yi(), FontPainter.defaultColor);
        };
    }
}
