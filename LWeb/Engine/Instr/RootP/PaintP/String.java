package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sopl;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
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
            BufferedImage bi=Core.getResource(id, BufferedImage.class);
            Graphics g = bi.getGraphics();
            g.setPaintMode();
            g.setColor(new java.awt.Color(0xff000000));
            g.setFont(Core.getResource(font, Font.class));
            g.drawString(Core.getResource(text, java.lang.String.class), x, y);
        };
    }
}
