package Engine.Instr.RootP;

import Common.Counter;
import static Engine.Core.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class End {
    public static Runnable getInst(byte[] o, Counter i){
        i.ended=true;
        return ()->{
            File outputfile = new File("image.png");
            try {
                ImageIO.write(buffers.get(0), "png", outputfile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
