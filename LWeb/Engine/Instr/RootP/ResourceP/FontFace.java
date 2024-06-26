package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.itb;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import LWeb.Engine.Util.GLEU.FontPainter;
import java.awt.Font;
import java.util.Arrays;

public class FontFace {
    FontPainter fnt;
    Font font;
    boolean aa;
    boolean fm;
    
    public static ResourceInst.RByteCol getBytes(int style, int size, String text){
        byte b[] = text.getBytes();
        return new ResourceInst.RByteCol(2,  itb(style), itb(size), itb(b.length), b);
    }
    public static Object getRsc(ByteCounter i, Core c){
        int style = byteToInt(Arrays.copyOfRange(i.o, i.c, i.c+4));
        int size = byteToInt(Arrays.copyOfRange(i.o, i.c+4, i.c+8));
        int fontnmlen = byteToInt(Arrays.copyOfRange(i.o, i.c+8, i.c+12));
        String font=new String(Arrays.copyOfRange(i.o, i.c+12, i.c+12+fontnmlen));
        i.c = i.c+12+fontnmlen;
//        System.out.println(lognm()+""+font);
//        System.out.println(lognm()+""+new Font(font, style ,size));
        
        return new FontFace(new Font(font, style ,size), true, true);
    }
    
    public FontFace(Font f, boolean aa, boolean fm){
        font=f;
        this.aa=aa;
        this.fm=fm;
    }
    public FontPainter getPainter(){
        if(fnt==null){
            fnt = new FontPainter(font)
                .initializeRenderer(aa, fm);
        }
        return fnt;
    }
    
}
