package LWeb.Engine.Instr.RootP.PaintP;

import LWeb.Common.ByteCounter;
import LWeb.Common.Color;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Curve;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;

import static LWeb.Common.Common.*;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Engine.Constants.ConstTypes.BEZIER3P;
import static LWeb.Engine.Constants.ConstTypes.CURVE;

public class DrawCurve {
    public static byte[] getBytes(int Bid, int Rcurve, int Rwidth, int Rcolor){
        return flatten(ib(2,6), itb(Bid), itb(Rcurve), itb(Rwidth), itb(Rcolor));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int cur = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int wid = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            FrameBuffer fb = c.getResource(id, FrameBuffer.class).use();
            Color cl = c.getResource(col, Color.class);
            Curve cr = c.getResource(cur, Curve.class);
            int w = c.getResource(wid, int.class);
            Position p = fb.getTex().p.resolve(Position.class);

//            System.out.println(lognm()+""+cl);
            Shader shader = c.getConstant(CURVE, Shader.class)
                    .use()
                    .setUniformF("p0", cr.getX(), cr.getY())
                    .setUniformF("scale", p.xi(), p.yi())
                    .setUniformI("length", cr.getLen())
                    .setUniformF("color", cl.rf(), cl.gf(), cl.bf(), cl.af())
                    .setUniformF("width", 1.0f*w/p.xi());
            cr.getTex().bind();
            fb.preferedLayout.draw();

        };
    }
}
