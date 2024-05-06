package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.Arrays;

public class PlainText {
    public static Object getRsc(ByteCounter i, Core c){
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return new String(Arrays.copyOfRange(i.o, i.c, i.inc(length)));
    }
}
