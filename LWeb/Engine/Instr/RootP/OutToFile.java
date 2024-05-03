package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;


public class OutToFile {
    public static Runnable getInst(byte[] o, Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int file = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return ()->{
            URI uri = Core.getResource(file, URI.class);
            File of = new File(uri);
            try {
                ImageIO.write(Core.getResource(id, BufferedImage.class), "png", of);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
