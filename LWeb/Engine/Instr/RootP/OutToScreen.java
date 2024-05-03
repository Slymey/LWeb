package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import static LWeb.Engine.Core.resources;
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
    public static Runnable getInst(byte[] o, Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int win = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            BufferedImage image = Core.getResource(id, BufferedImage.class);
            Window window = Core.getResource(win, Window.class);
            int width = image.getWidth(), height = image.getHeight();
            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            Texture texture = new Texture()
                .setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT)	
                .setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT)
                .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)	
                .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)	
                    .loadImage(image, false, true, true);
            
            window.screenBuffer().draw(texture);
            
            glfwSwapBuffers(window.winId());
        };
    }
}
