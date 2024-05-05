package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Texture;
import LWeb.Engine.Util.SimpleRemoteThread;
import LWeb.Engine.Util.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.system.MemoryUtil;


public class OutToScreen {
    public static Runnable getInst(byte[] o, Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int win = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int screen = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            FrameBuffer buff = c.getResource(id, FrameBuffer.class);
            FrameBuffer scrn = c.getResource(screen, FrameBuffer.class);
            long window = c.getResource(win, long.class);
            
            //buff.finnish(scrn, true);
            scrn.useWithClear();
            scrn.draw(buff.getTex());
            
            glfwSwapBuffers(window);
        };
    }
}
