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
import static org.lwjgl.opengl.GL41.*;

public class BlendMode {
    public static enum BlendModes{ZERO(1),ONE(2),SRC_COLOR(3),ONE_MINUS_SRC_COLOR(4),SRC_ALPHA(5),ONE_MINUS_SRC_ALPHA(6),DST_ALPHA(7),ONE_MINUS_DST_ALPHA(8),DST_COLOR(9),ONE_MINUS_DST_COLOR(10),SRC_ALPHA_SATURATE(11),CONSTANT_COLOR(12),ONE_MINUS_CONSTANT_COLOR(13),CONSTANT_ALPHA(14),ONE_MINUS_CONSTANT_ALPHA(15);int b;private BlendModes(int b){this.b = b;}}
    public static ResourceInst.RByteCol noBlending(){
        return new ResourceInst.RByteCol(20, ib(0,0,0,0,0));
    }
    //<editor-fold defaultstate="collapsed" desc="uniform blending">
    public static ResourceInst.RByteCol uniformBlending(int srcBM, int dstBM){
        if((srcBM&0xf)>=12||(dstBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(int srcBM, int dstBM, int color){
        if((srcBM&0xf)>=12||(dstBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlendingRefColor(int srcBM, int dstBM, int Rcolor){
        if((srcBM&0xf)>=12||(dstBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(int srcBM, int dstBM, IntColor color){
        if((srcBM&0xf)>=12||(dstBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(int srcBM, int dstBM, FloatColor color){
        if((srcBM&0xf)>=12||(dstBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),srcBM<<4|(dstBM&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes srcBM, BlendModes dstBM){
        if((srcBM.b&0xf)>=12||(dstBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes srcBM, BlendModes dstBM, int color){
        if((srcBM.b&0xf)>=12||(dstBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlendingRefColor(BlendModes srcBM, BlendModes dstBM, int Rcolor){
        if((srcBM.b&0xf)>=12||(dstBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes srcBM, BlendModes dstBM, IntColor color){
        if((srcBM.b&0xf)>=12||(dstBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0));
    }
    public static ResourceInst.RByteCol uniformBlending(BlendModes srcBM, BlendModes dstBM, FloatColor color){
        if((srcBM.b&0xf)>=12||(dstBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib(srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),srcBM.b<<4|(dstBM.b&0xf),0));
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="rgb-alpha blending">
    public static ResourceInst.RByteCol custumBlending(int srcRgbBM, int dstRgbBM, int srcAlphaBM, int dstAlphaBM){
        if((srcRgbBM&0xf)>=12||(dstRgbBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(int srcRgbBM, int dstRgbBM, int srcAlphaBM, int dstAlphaBM, int color){
        if((srcRgbBM&0xf)>=12||(dstRgbBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlendingRefColor(int srcRgbBM, int dstRgbBM, int srcAlphaBM, int dstAlphaBM, int Rcolor){
        if((srcRgbBM&0xf)>=12||(dstRgbBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(int srcRgbBM, int dstRgbBM, int srcAlphaBM, int dstAlphaBM, IntColor color){
        if((srcRgbBM&0xf)>=12||(dstRgbBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(int srcRgbBM, int dstRgbBM, int srcAlphaBM, int dstAlphaBM, FloatColor color){
        if((srcRgbBM&0xf)>=12||(dstRgbBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcRgbBM<<4|(dstRgbBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes srcRgbBM, BlendModes dstRgbBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM){
        if((srcRgbBM.b&0xf)>=12||(dstRgbBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(0));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes srcRgbBM, BlendModes dstRgbBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, int color){
        if((srcRgbBM.b&0xf)>=12||(dstRgbBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(color));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlendingRefColor(BlendModes srcRgbBM, BlendModes dstRgbBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, int Rcolor){
        if((srcRgbBM.b&0xf)>=12||(dstRgbBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(Rcolor));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes srcRgbBM, BlendModes dstRgbBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, IntColor color){
        if((srcRgbBM.b&0xf)>=12||(dstRgbBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
    }
    public static ResourceInst.RByteCol custumBlending(BlendModes srcRgbBM, BlendModes dstRgbBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, FloatColor color){
        if((srcRgbBM.b&0xf)>=12||(dstRgbBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
            return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
        }
        return new ResourceInst.RByteCol(20, ib((srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcRgbBM.b<<4|(dstRgbBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
    }
    //</editor-fold>
    
//    //<editor-fold defaultstate="collapsed" desc="custum blending">
//    public static ResourceInst.RByteCol custumBlending(int srcRedBM, int dstRedBM, int srcGreenBM, int dstGreenBM, int srcBlueBM, int dstBlueBM, int srcAlphaBM, int dstAlphaBM){
//        if((srcRedBM&0xf)>=12||(dstRedBM&0xf)>=12||(srcGreenBM&0xf)>=12||(dstGreenBM&0xf)>=12||(srcBlueBM&0xf)>=12||(dstBlueBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(0));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(int srcRedBM, int dstRedBM, int srcGreenBM, int dstGreenBM, int srcBlueBM, int dstBlueBM, int srcAlphaBM, int dstAlphaBM, int color){
//        if((srcRedBM&0xf)>=12||(dstRedBM&0xf)>=12||(srcGreenBM&0xf)>=12||(dstGreenBM&0xf)>=12||(srcBlueBM&0xf)>=12||(dstBlueBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(color));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlendingRefColor(int srcRedBM, int dstRedBM, int srcGreenBM, int dstGreenBM, int srcBlueBM, int dstBlueBM, int srcAlphaBM, int dstAlphaBM, int Rcolor){
//        if((srcRedBM&0xf)>=12||(dstRedBM&0xf)>=12||(srcGreenBM&0xf)>=12||(dstGreenBM&0xf)>=12||(srcBlueBM&0xf)>=12||(dstBlueBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), itb(Rcolor));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(int srcRedBM, int dstRedBM, int srcGreenBM, int dstGreenBM, int srcBlueBM, int dstBlueBM, int srcAlphaBM, int dstAlphaBM, IntColor color){
//        if((srcRedBM&0xf)>=12||(dstRedBM&0xf)>=12||(srcGreenBM&0xf)>=12||(dstGreenBM&0xf)>=12||(srcBlueBM&0xf)>=12||(dstBlueBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(int srcRedBM, int dstRedBM, int srcGreenBM, int dstGreenBM, int srcBlueBM, int dstBlueBM, int srcAlphaBM, int dstAlphaBM, FloatColor color){
//        if((srcRedBM&0xf)>=12||(dstRedBM&0xf)>=12||(srcGreenBM&0xf)>=12||(dstGreenBM&0xf)>=12||(srcBlueBM&0xf)>=12||(dstBlueBM&0xf)>=12||(srcAlphaBM&0xf)>=12||(dstAlphaBM&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM<<4|(dstRedBM&0xf)),(srcGreenBM<<4|(dstGreenBM&0xf)),(srcBlueBM<<4|(dstBlueBM&0xf)),(srcAlphaBM<<4|(dstAlphaBM&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(BlendModes srcRedBM, BlendModes dstRedBM, BlendModes srcGreenBM, BlendModes dstGreenBM, BlendModes srcBlueBM, BlendModes dstBlueBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM){
//        if((srcRedBM.b&0xf)>=12||(dstRedBM.b&0xf)>=12||(srcGreenBM.b&0xf)>=12||(dstGreenBM.b&0xf)>=12||(srcBlueBM.b&0xf)>=12||(dstBlueBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(0));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(BlendModes srcRedBM, BlendModes dstRedBM, BlendModes srcGreenBM, BlendModes dstGreenBM, BlendModes srcBlueBM, BlendModes dstBlueBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, int color){
//        if((srcRedBM.b&0xf)>=12||(dstRedBM.b&0xf)>=12||(srcGreenBM.b&0xf)>=12||(dstGreenBM.b&0xf)>=12||(srcBlueBM.b&0xf)>=12||(dstBlueBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(color));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlendingRefColor(BlendModes srcRedBM, BlendModes dstRedBM, BlendModes srcGreenBM, BlendModes dstGreenBM, BlendModes srcBlueBM, BlendModes dstBlueBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, int Rcolor){
//        if((srcRedBM.b&0xf)>=12||(dstRedBM.b&0xf)>=12||(srcGreenBM.b&0xf)>=12||(dstGreenBM.b&0xf)>=12||(srcBlueBM.b&0xf)>=12||(dstBlueBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), itb(Rcolor));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(BlendModes srcRedBM, BlendModes dstRedBM, BlendModes srcGreenBM, BlendModes dstGreenBM, BlendModes srcBlueBM, BlendModes dstBlueBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, IntColor color){
//        if((srcRedBM.b&0xf)>=12||(dstRedBM.b&0xf)>=12||(srcGreenBM.b&0xf)>=12||(dstGreenBM.b&0xf)>=12||(srcBlueBM.b&0xf)>=12||(dstBlueBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0), ib(color.ri(),color.gi(),color.bi(),color.ai()));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
//    }
//    public static ResourceInst.RByteCol custumBlending(BlendModes srcRedBM, BlendModes dstRedBM, BlendModes srcGreenBM, BlendModes dstGreenBM, BlendModes srcBlueBM, BlendModes dstBlueBM, BlendModes srcAlphaBM, BlendModes dstAlphaBM, FloatColor color){
//        if((srcRedBM.b&0xf)>=12||(dstRedBM.b&0xf)>=12||(srcGreenBM.b&0xf)>=12||(dstGreenBM.b&0xf)>=12||(srcBlueBM.b&0xf)>=12||(dstBlueBM.b&0xf)>=12||(srcAlphaBM.b&0xf)>=12||(dstAlphaBM.b&0xf)>=12){
//            return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),2), ftb(color.rf()),ftb(color.gf()),ftb(color.bf()),ftb(color.af()));
//        }
//        return new ResourceInst.RByteCol(20, ib((srcRedBM.b<<4|(dstRedBM.b&0xf)),(srcGreenBM.b<<4|(dstGreenBM.b&0xf)),(srcBlueBM.b<<4|(dstBlueBM.b&0xf)),(srcAlphaBM.b<<4|(dstAlphaBM.b&0xf)),0));
//    }
//    //</editor-fold>
//    
    
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
            glBlendFuncSeparate(sr, dr, sa, da);// fully sepera rgba not supported
//            glBlendFunc(sr, dr);
//            glBlendFunc(sg, dg);
//            glBlendFunc(sb, db);
//            glBlendFunc(sa, da);
        }else{
            glDisable(GL_BLEND);
        }
    }
}
