package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.itb;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import static LWeb.Engine.Core.*;
import LWeb.Engine.Instr.RootP.ResourceInst;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class URLRelative {
    public static ResourceInst.RByteCol getBytes(String rurl){
        byte b[] = rurl.getBytes();
        return new ResourceInst.RByteCol(5,  itb(b.length), b);
    }
    public static Object getRsc(ByteCounter i, Core c){
        int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pos = i.c;
        URI url;
        try {
            String data = new String(Arrays.copyOfRange(i.o, i.c, i.incp(len)));
            url = new URI(FileSystems.getDefault().getPath("").toUri().toString()+data);
        } catch (URISyntaxException ex) {
            c.reportError(pos, ex);
            url=null;
        }
        return url;
    }
}
