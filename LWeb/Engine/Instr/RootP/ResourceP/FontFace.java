package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import java.awt.Font;
import java.util.Arrays;

public class FontFace {
    public static Object getRsc(byte[] o, Counter i){
        int style = byteToInt(Arrays.copyOfRange(o, i.c, i.c+4));
        int size = byteToInt(Arrays.copyOfRange(o, i.c+4, i.c+8));
        int fontnmlen = byteToInt(Arrays.copyOfRange(o, i.c+8, i.c+12));
        String font=new String(Arrays.copyOfRange(o, i.c+12, i.c+12+fontnmlen));
        i.c = i.c+12+fontnmlen;
        return new Font(font, style, size);//size
    }
}
