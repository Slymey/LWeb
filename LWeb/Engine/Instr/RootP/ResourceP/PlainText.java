package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.*;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst.RByteCol;
import java.util.Arrays;

public class PlainText {
    public static RByteCol getBytes(String text){
        byte b[] = text.getBytes();
        return new RByteCol(1,  itb(b.length), b);
    }
    public static Object getRsc(ByteCounter i, Core c){
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return new String(Arrays.copyOfRange(i.o, i.c, i.incp(length)));
    }
}
