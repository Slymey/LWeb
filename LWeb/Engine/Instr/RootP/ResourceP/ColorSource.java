package Engine.Instr.RootP.ResourceP;

import static Common.Common.byteToInt;
import Common.Counter;
import Common.Pair;
import static Common.Pair.Pair;
import static Engine.Core.buffers;
import static Engine.Core.resources;
import java.net.URI;

public class ColorSource {
    public static Pair<Class, Object> getRsc(byte[] o, Counter i){
        Class cls = Common.ColorSource.class;
        Object resource = null;
        Common.ColorSource cs=null;
        switch(o[i.inc()]){
            case 0://flat color
                cs=new Common.ColorSource(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
                break;
            case 4:// image from URI
                URI uri = (URI)resources.get(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]})).get();
                cs = new Common.ColorSource(uri);
                break;
            case 5://existing buffer
                cs = new Common.ColorSource(buffers.get(byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]})));
                break;
        }
        resource = cs;
        return Pair(cls, resource);
    }
}
