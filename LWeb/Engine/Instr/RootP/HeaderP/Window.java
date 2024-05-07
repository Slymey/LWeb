package LWeb.Engine.Instr.RootP.HeaderP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Common.vp;
import static LWeb.Common.Common.vpb;
import static LWeb.Common.Common.vpi;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import java.util.Arrays;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.opengl.GLCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Window {
    public static byte[] getBytes(int win, int x, int y, int Rpos, byte gSinc, String name){
        byte b[] = name.getBytes();
        return flatten(ib(4,1), itb(win), itb(x), itb(y), itb(Rpos ), vpb(gSinc), itb(b.length), b);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        
        int win = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int p = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        byte gs = i.next();
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        String name = new String(Arrays.copyOfRange(i.o, i.c, i.incp(length)));
        return ()->{
            long window;
            GLFWErrorCallback.createPrint(System.err).set(); // Error handling
        
            //initialization
            if (!GLFW.glfwInit()) {
                throw new IllegalStateException("Failed to initialize GLFW");
            }
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);  
            //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);//mac-osx
            Position pos = c.getResource(p, Position.class);
            // Create a GLFW window and set context
            System.out.println(lognm()+""+pos.xi()+" "+ pos.yi());
            window = glfwCreateWindow(pos.xi(), pos.yi(), name, NULL, NULL);
            if(window == NULL) {
                throw new RuntimeException("Failed to create GLFW window");
            }
            c.putResource(win,  window);//window resource
            
            //for fractional window pos/size
            //glfwSetWindowSize(window, w, h);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if(x<0||y<0){
                glfwSetWindowPos(window, 
                        (vidmode.width() - pos.xi()) / 2,
                        (vidmode.height() - pos.yi()) / 2
                );
            }else{
                glfwSetWindowPos(window,x, y);
            }
            glfwSetWindowSizeCallback(window, (long wd, int width, int height)->{
                c.putResource(p, new IntPos(width,height));
                glViewport(0, 0, width, height);
            });

            //open and begin
            glfwMakeContextCurrent(window);
            if(gs!=0)glfwSwapInterval(1);
            GLCapabilities gc = createCapabilities();
            
        };
    }
}
