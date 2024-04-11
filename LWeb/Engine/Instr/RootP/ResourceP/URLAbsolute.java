package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import static LWeb.Engine.Core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class URLAbsolute {
    public static Pair<Class, Object> getRsc(byte[] o, Counter i){
        Class cls = URI.class;
        int len = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int pos = i.c;
        URI url;
        try {
            url = new URI(new String(Arrays.copyOfRange(o, i.c, i.inc(len))));
        } catch (URISyntaxException ex) {
            errors.set(pos, ex);
            newError=pos;
            url=null;
        }
        Object resource = url;
        return Pair(cls, resource);
    }
}
