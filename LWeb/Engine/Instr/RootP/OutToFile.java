package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;


public class OutToFile {
    public static byte[] getBytes(int Bid, int RfileUrl){
        return flatten(ib(10), itb(Bid), itb(RfileUrl));
    }
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
