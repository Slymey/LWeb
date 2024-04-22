package LWeb.Engine.Instr.RootP;

import LWeb.Common.Counter;
import static LWeb.Engine.Core.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class End {
    public static Runnable getInst(byte[] o, Counter i){
        i.ended=true;
        return ()->{
            Thread.currentThread().interrupt();
            /*
            File outputfile = new File("image.png");
            try {
                ImageIO.write(buffers.get(0), "png", outputfile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            */
        };
    }
}
