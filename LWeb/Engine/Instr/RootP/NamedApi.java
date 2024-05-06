package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.Arrays;

public class NamedApi {
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        String name =  new String(Arrays.copyOfRange(i.o, i.c, i.incp(length)));
        return ()->{
            c.namedApi.put(name, id);
        };
    }
}
