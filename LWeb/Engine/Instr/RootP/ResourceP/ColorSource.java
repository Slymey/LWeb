package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.awt.image.BufferedImage;
import java.net.URI;

public class ColorSource {
    public static Object getRsc(byte[] o, Counter i){
        LWeb.Common.ColorSource cs=null;
        switch(o[i.inc()]){
            case 0://flat color
                cs=new LWeb.Common.ColorSource(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
                break;
            case 4:// image from URI
                URI uri = Core.getResource(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}), URI.class);
                cs = new LWeb.Common.ColorSource(uri);
                break;
            case 5://existing buffer
                cs = new LWeb.Common.ColorSource(Core.getResource(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}), BufferedImage.class));
                break;
        }
        return cs;
    }
}
