package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import static LWeb.Engine.Core.buffers;
import static LWeb.Engine.Core.resources;
import java.net.URI;

public class ColorSource {
    public static Pair<Class, Object> getRsc(byte[] o, Counter i){
        Class cls = LWeb.Common.ColorSource.class;
        Object resource = null;
        LWeb.Common.ColorSource cs=null;
        switch(o[i.inc()]){
            case 0://flat color
                cs=new LWeb.Common.ColorSource(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
                break;
            case 4:// image from URI
                URI uri = (URI)resources.get(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]})).get();
                cs = new LWeb.Common.ColorSource(uri);
                break;
            case 5://existing buffer
                cs = new LWeb.Common.ColorSource(buffers.get(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]})));
                break;
        }
        resource = cs;
        return Pair(cls, resource);
    }
}
