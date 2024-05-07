package LWeb.Engine.Instr.RootP;

import LWeb.Common.Color;
import static LWeb.Common.Color.color;
import LWeb.Common.Common;
import static LWeb.Common.Common.*;
import static LWeb.Common.Common.clamp;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.ColorMixers;
import LWeb.Common.Range.Range;
import LWeb.Engine.Constants;
import LWeb.Engine.Instr.RootP.ResourceP.BlendMode;
import LWeb.Engine.Instr.RootP.ResourceP.Box;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.VertexArray;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class StackViewBox {
    public static byte[] getBytes(int Bsource, int Btarget, int Rbox, int RviewBox, int RblendMode){
        return flatten(ib(7), itb(Bsource), itb(Btarget), itb(Rbox), itb(RviewBox), itb(RblendMode));
    }
    
    public static Runnable getInst(ByteCounter i, Core c){
        int source = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int target = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int viewBox = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int blm = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            FrameBuffer sfb = c.getResource(source, FrameBuffer.class);
            FrameBuffer tfb = c.getResource(target, FrameBuffer.class);
            Box locs = c.getResource(box, Box.class);
            Box vb = c.getResource(viewBox, Box.class);
            Position p = tfb.getTex().p.resolve(Position.class);
            Position sp = sfb.getTex().p.resolve(Position.class);
//            System.out.println(lognm()+""+locs.xf()+" "+ locs.yf()+" "+ locs.zf()+" "+ locs.wf()+" "+locs.xi()+" "+ locs.yi()+" "+ locs.zi()+" "+ locs.wi());
//            System.out.println(lognm()+""+p.xf()+" "+p.yf()+" "+p.xi()+" "+p.yi());
            locs.p=p;
            vb.p=sp;
//            System.out.println(lognm()+""+locs.xf()+" "+ locs.yf()+" "+ locs.zf()+" "+ locs.wf()+" "+locs.xi()+" "+ locs.yi()+" "+ locs.zi()+" "+ locs.wi());
            c.getResource(blm, BlendMode.class).setBlendMode();
            tfb.draw(sfb.getTex(),
                    c.getConstant(Constants.ConstTypes.BOX_SHADER, Shader.class)
                            .use().setUniformF("box", locs.xf(), locs.yf(), locs.zf(), locs.wf()) 
                            .setUniformF("viewBox", vb.xf(), vb.yf(), vb.zf(), vb.wf()), 
                    c.getConstant(Constants.ConstTypes.BASIC_QUAD, VertexArray.class));
            
            
            
            
        }; 
    }
}
/*


*/