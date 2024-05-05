package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import static LWeb.Engine.Core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class URLAbsolute {
    public static Object getRsc(byte[] o, Counter i, Core c){
        int len = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int pos = i.c;
        URI url;
        try {
            url = new URI(new String(Arrays.copyOfRange(o, i.c, i.inc(len))));
        } catch (URISyntaxException ex) {
            c.reportError(pos, ex);
            url=null;
        }
        return url;
    }
}
