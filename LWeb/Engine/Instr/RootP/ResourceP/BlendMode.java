package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color;
import LWeb.Common.Color.FloatColor;
import LWeb.Common.Color.IntColor;
import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public class BlendMode {
    public static enum BlendModes{ZERO(1),ONE(2),SRC_COLOR(3),ONE_MINUS_SRC_COLOR(4),SRC_ALPHA(5),ONE_MINUS_SRC_ALPHA(6),DST_ALPHA(7),ONE_MINUS_DST_ALPHA(8),DST_COLOR(9),ONE_MINUS_DST_COLOR(10),SRC_ALPHA_SATURATE(11),CONSTANT_COLOR(12),ONE_MINUS_CONSTANT_COLOR(13),CONSTANT_ALPHA(14),ONE_MINUS_CONSTANT_ALPHA(15);int b;private BlendModes(int b){this.b = b;}}
    public static ResourceInst.RByteCol noBlending(){
        return new ResourceInst.RByteCol(20, ib(0,0,0,0,0));
    }
    //<editor-fold defaultstate="collapsed" desc="uniform blending">
    public static ResourceInst.RByteCol uniformBlending(int blendModes){
        if((blendModes>>>4)>=12||(blendModes&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0));
    }
    public static ResourceInst.RByteCol uniformBlending(int blendModes, int color){
        if((blendModes>>>4)>=12||(blendModes&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0));
    }
    public static ResourceInst.RByteCol uniformBlendingRefColor(int blendModes, int Rcolor){
        if((blendModes>>>4)>=12||(blendModes&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0));
    }
    public static ResourceInst.RByteCol uniformBlending(int blendModes, IntColor color){
        if((blendModes>>>4)>=12||(blendModes&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0));
    }
    public static ResourceInst.RByteCol uniformBlending(int blendModes, FloatColor color){
        if((blendModes>>>4)>=12||(blendModes&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes,blendModes,blendModes,blendModes,0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes blendModes){
        if((blendModes.b>>>4)>=12||(blendModes.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes blendModes, int color){
        if((blendModes.b>>>4)>=12||(blendModes.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0));
    }
    public static ResourceInst.RByteCol uniformBlendingRefColor(BlendModes blendModes, int Rcolor){
        if((blendModes.b>>>4)>=12||(blendModes.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes blendModes, IntColor color){
        if((blendModes.b>>>4)>=12||(blendModes.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes blendModes, FloatColor color){
        if((blendModes.b>>>4)>=12||(blendModes.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(blendModes.b,blendModes.b,blendModes.b,blendModes.b,0));
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="custum blending">
    public static ResourceInst.RByteCol custumBlending(int redBM, int greenBM, int blueBM, int alphaBM){
        if((redBM>>>4)>=12||(redBM&0xf)>=12||(greenBM>>>4)>=12||(greenBM&0xf)>=12||(blueBM>>>4)>=12||(blueBM&0xf)>=12||(alphaBM>>>4)>=12||(alphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0));
    }
    public static ResourceInst.RByteCol custumBlending(int redBM, int greenBM, int blueBM, int alphaBM, int color){
        if((redBM>>>4)>=12||(redBM&0xf)>=12||(greenBM>>>4)>=12||(greenBM&0xf)>=12||(blueBM>>>4)>=12||(blueBM&0xf)>=12||(alphaBM>>>4)>=12||(alphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0));
    }
    public static ResourceInst.RByteCol custumBlendingRefColor(int redBM, int greenBM, int blueBM, int alphaBM, int Rcolor){
        if((redBM>>>4)>=12||(redBM&0xf)>=12||(greenBM>>>4)>=12||(greenBM&0xf)>=12||(blueBM>>>4)>=12||(blueBM&0xf)>=12||(alphaBM>>>4)>=12||(alphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0));
    }
    public static ResourceInst.RByteCol custumBlending(int redBM, int greenBM, int blueBM, int alphaBM, IntColor color){
        if((redBM>>>4)>=12||(redBM&0xf)>=12||(greenBM>>>4)>=12||(greenBM&0xf)>=12||(blueBM>>>4)>=12||(blueBM&0xf)>=12||(alphaBM>>>4)>=12||(alphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0));
    }
    public static ResourceInst.RByteCol custumBlending(int redBM, int greenBM, int blueBM, int alphaBM, FloatColor color){
        if((redBM>>>4)>=12||(redBM&0xf)>=12||(greenBM>>>4)>=12||(greenBM&0xf)>=12||(blueBM>>>4)>=12||(blueBM&0xf)>=12||(alphaBM>>>4)>=12||(alphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(redBM,greenBM,blueBM,alphaBM,0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes redBM, BlendModes greenBM, BlendModes blueBM, BlendModes alphaBM){
        if((redBM.b>>>4)>=12||(redBM.b&0xf)>=12||(greenBM.b>>>4)>=12||(greenBM.b&0xf)>=12||(blueBM.b>>>4)>=12||(blueBM.b&0xf)>=12||(alphaBM.b>>>4)>=12||(alphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes redBM, BlendModes greenBM, BlendModes blueBM, BlendModes alphaBM, int color){
        if((redBM.b>>>4)>=12||(redBM.b&0xf)>=12||(greenBM.b>>>4)>=12||(greenBM.b&0xf)>=12||(blueBM.b>>>4)>=12||(blueBM.b&0xf)>=12||(alphaBM.b>>>4)>=12||(alphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0));
    }
    public static ResourceInst.RByteCol custumBlendingRefColor(BlendModes redBM, BlendModes greenBM, BlendModes blueBM, BlendModes alphaBM, int Rcolor){
        if((redBM.b>>>4)>=12||(redBM.b&0xf)>=12||(greenBM.b>>>4)>=12||(greenBM.b&0xf)>=12||(blueBM.b>>>4)>=12||(blueBM.b&0xf)>=12||(alphaBM.b>>>4)>=12||(alphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes redBM, BlendModes greenBM, BlendModes blueBM, BlendModes alphaBM, IntColor color){
        if((redBM.b>>>4)>=12||(redBM.b&0xf)>=12||(greenBM.b>>>4)>=12||(greenBM.b&0xf)>=12||(blueBM.b>>>4)>=12||(blueBM.b&0xf)>=12||(alphaBM.b>>>4)>=12||(alphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes redBM, BlendModes greenBM, BlendModes blueBM, BlendModes alphaBM, FloatColor color){
        if((redBM.b>>>4)>=12||(redBM.b&0xf)>=12||(greenBM.b>>>4)>=12||(greenBM.b&0xf)>=12||(blueBM.b>>>4)>=12||(blueBM.b&0xf)>=12||(alphaBM.b>>>4)>=12||(alphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(redBM.b,greenBM.b,blueBM.b,alphaBM.b,0));
    }
    //</editor-fold>
    
    
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
