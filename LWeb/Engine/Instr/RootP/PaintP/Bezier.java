package LWeb.Engine.Instr.RootP.PaintP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Engine.Core;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Engine.Constants.ConstTypes.BEZIER3P;
import static LWeb.Engine.Constants.ConstTypes.LINE;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;

public class Bezier {
    public static byte[] getBytes(int Bid, int Rp1, int Rp2, int Rp3, int Rwidth, int Rcolor){
        return flatten(ib(2,5), itb(Bid), itb(Rp1),itb(Rp3), itb(Rp2), itb(Rwidth), itb(Rcolor));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pos1 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pos2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pos3 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int wid = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            FrameBuffer fb = c.getResource(id, FrameBuffer.class).use();
            Color cl = c.getResource(col, Color.class);
            Position p1 = c.getResource(pos1, Position.class);
            Position p2 = c.getResource(pos2, Position.class);
            Position p3 = c.getResource(pos3, Position.class);
            int w = c.getResource(wid, int.class);
            Position p = fb.getTex().p.resolve(Position.class);
            p1.p = p;
            p2.p = p;
            p3.p = p;
//            System.out.println(lognm()+""+cl);
            Shader shader = c.getConstant(BEZIER3P, Shader.class)
                    .use()
                    .setUniformF("p0", p1.xf(), p1.yf())
                    .setUniformF("p1", p2.xf(), p2.yf())
                    .setUniformF("p2", p3.xf(), p3.yf())
                    .setUniformF("color", cl.rf(), cl.gf(), cl.bf(), cl.af())
                    .setUniformF("width", 1.0f*w/p.xi());           
            fb.preferedLayout.draw();
            
        };
    }
}
