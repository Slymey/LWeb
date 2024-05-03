package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import static LWeb.Engine.Core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class URLRelative {
    public static Object getRsc(byte[] o, Counter i){
        int len = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int pos = i.c;
        URI url;
        try {
            String data = new String(Arrays.copyOfRange(o, i.c, i.incp(len)));
            url = new URI(FileSystems.getDefault().getPath("").toUri().toString()+data);
        } catch (URISyntaxException ex) {
            errors.set(pos, ex);
            newError=pos;
            url=null;
        }
        return url;
    }
}
