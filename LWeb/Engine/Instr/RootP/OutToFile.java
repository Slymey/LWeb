package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import static LWeb.Engine.Core.buffers;
import static LWeb.Engine.Core.resources;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;


public class OutToFile {
    public static Runnable getInst(byte[] o, Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return ()->{
            URI uri = (URI)resources.get(id).get();
            File of = new File(uri);
            try {
                ImageIO.write(buffers.get(0), "png", of);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
}
