package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;


public class OutToFile {
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int file = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            URI uri = c.getResource(file, URI.class);
            File of = new File(uri);
            try {
                ImageIO.write(c.getResource(id, BufferedImage.class), "png", of);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
