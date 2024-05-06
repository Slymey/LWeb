package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color;
import LWeb.Common.Color.FloatColor;
import LWeb.Common.Color.IntColor;
import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public class BlendMode {
    private static int map[] = {-1, GL_ZERO, GL_ONE, GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_DST_ALPHA, GL_ONE_MINUS_DST_ALPHA, GL_DST_COLOR, GL_ONE_MINUS_DST_COLOR, GL_SRC_ALPHA_SATURATE, GL_CONSTANT_COLOR, GL_ONE_MINUS_CONSTANT_COLOR, GL_CONSTANT_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA};
    boolean doBlend;
    Color cl;
    int sr;
    int sg;
    int sb;
    int sa;
    int dr;
    int dg;
    int db;
    int da;

    public static Object getRsc(ByteCounter i, Core c){//todo add blend equation support
        int r = i.next();
        int g = i.next();
        int b = i.next();
        int a = i.next();
        int f = i.next();
        //glBlendFunc(0, GL_CONSTANT_COLOR);//0,1 300-308, 8001-8004   ->  1,2, 3-10, 11-14
        int dr = map[(r&0xf)];
        int dg = map[(g&0xf)];
        int db = map[(b&0xf)];
        int da = map[(a&0xf)];
        int sr = map[(r>>>4)];
        int sg = map[(g>>>4)];
        int sb = map[(b>>>4)];
        int sa = map[(a>>>4)];
        Color color=null;
        if(sr>0x8000||sg>0x8000||sb>0x8000||sa>0x8000||dr>0x8000||dg>0x8000||db>0x8000||da>0x8000){
            if((f&0x1)==1){
                int colind = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                color=c.getResource(colind, Color.class);
            }else if((f>>>1&0x1)==1){
                color = new FloatColor(
                    byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                    byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                    byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                    byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()})
                );
            }else{
                color = new IntColor(i.next(),i.next(),i.next(),i.next());
            }
        }
        return new BlendMode(color, sr==-1||sg==-1||sb==-1||sa==-1||dr==-1||dg==-1||db==-1||da==-1, sr, sg, sb, sa, dr, dg, db, da);
    }
    
    public BlendMode(Color cl, boolean bl, int sr, int sg, int sb, int sa, int dr, int dg, int db, int da){
        doBlend=!bl;
        this.cl=cl;
        this.sr=sr;
        this.sg=sg;
        this.sb=sb;
        this.sa=sa;
        this.dr=dr;
        this.dg=dg;
        this.db=db;
        this.da=da;
    }
    
    public void setBlendMode(){
        if(doBlend){
            if(cl!=null){
                glBlendColor(cl.rf(), cl.gf(), cl.bf(), cl.af());
            }
            glEnable(GL_BLEND);
            glBlendFunc(sr, dr);
            glBlendFunc(sg, dg);
            glBlendFunc(sb, db);
            glBlendFunc(sa, da);
        }else{
            glDisable(GL_BLEND);
        }
    }
}
