package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import java.util.Arrays;

public class NamedApi {
    public static byte[] getBytes(int Rid, String text){
        byte b[] = text.getBytes();
        return flatten(ib(16), itb(Rid), itb(b.length), b);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        String name =  new String(Arrays.copyOfRange(i.o, i.c, i.incp(length)));
        return ()->{
            c.namedApi.put(name, id);
        };
    }
}
