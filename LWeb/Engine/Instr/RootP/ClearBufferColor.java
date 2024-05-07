package LWeb.Engine.Instr.RootP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class ClearBufferColor {
    public static byte[] getBytes(int Bbuffer, int Rcolor){
        return flatten(ib(21),itb(Bbuffer), itb(Rcolor));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int color = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            FrameBuffer fb = c.getResource(id, FrameBuffer.class);
            Color cl = c.getResource(color, Color.class);
            fb.bind();
            glClearColor(cl.rf(), cl.gf(), cl.bf(), cl.af());
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        };
    }
}
