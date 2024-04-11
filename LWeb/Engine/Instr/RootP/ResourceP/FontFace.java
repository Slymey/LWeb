package Engine.Instr.RootP.ResourceP;

import static Common.Common.byteToInt;
import Common.Counter;
import Common.Pair;
import static Common.Pair.Pair;
import java.awt.Font;
import java.util.Arrays;

public class FontFace {
    public static Pair<Class, Object> getRsc(byte[] o, Counter i){
        Class cls = null;
        int fontnmlen = byteToInt(Arrays.copyOfRange(o, i.c+8, i.c+12));
        String font=new String(Arrays.copyOfRange(o, i.c+12, i.c+12+fontnmlen));
        int style = byteToInt(Arrays.copyOfRange(o, i.c, i.c+4));
        int size = byteToInt(Arrays.copyOfRange(o, i.c+4, i.c+8));
        i.c = i.c+12+fontnmlen;
        Object resource = new Font(font, style, size);//size
        return Pair(cls, resource);
    }
}
