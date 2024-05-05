package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sopl;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.FontFace;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FontPainter;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class String {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int text = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int font = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int pos = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return ()->{
//            sopl(" t:"+id+" tx:"+text+" f:"+font+" x:"+x+" y:"+y);
            FrameBuffer sfb = c.getResource(id, FrameBuffer.class);
            java.lang.String txt = c.getResource(text, java.lang.String.class);
            FontPainter fp = c.getResource(font, FontFace.class).getPainter();
            Position p = c.getResource(pos, Position.class);
            sfb.use();
            fp.draw(txt, p.xi(), p.yi());
        };
    }
}
