package Engine.Instr.RootP.PaintP;

import static Common.Common.byteToInt;
import static Common.Common.sopl;
import Common.Counter;
import Engine.Core;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class String {
    public static Runnable getInst(byte o[], Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int text = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int font = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return ()->{
            sopl(" t:"+id+" tx:"+text+" f:"+font+" x:"+x+" y:"+y);
            BufferedImage bi=Core.buffers.get(id);
            Graphics g = bi.getGraphics();
            g.setPaintMode();
            g.setColor(new java.awt.Color(0xff000000));
            g.setFont((Font)Core.resources.get(font).get());
            g.drawString((java.lang.String)Core.resources.get(text).get(), x, y);
        };
    }
}
