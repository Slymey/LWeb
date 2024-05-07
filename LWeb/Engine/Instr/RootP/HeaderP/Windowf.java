package LWeb.Engine.Instr.RootP.HeaderP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Common.vpb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import java.util.Arrays;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
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
import org.lwjgl.opengl.GLCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Windowf {
    public static byte[] getBytes(int win, float x, float y, int Rposf, byte gSinc, String name){
        byte b[] = name.getBytes();
        return flatten(ib(4,2),itb(win), ftb(x), ftb(y), itb(Rposf ), vpb(gSinc), itb(b.length), b);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        
        int win = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        float x = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
        float y = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
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
            GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
            pos.p=new IntPos(vm.width(), vm.height());
            // Create a GLFW window and set context
            window = glfwCreateWindow(pos.xi(), pos.yi(), name, NULL, NULL);
            if(window == NULL) {
                throw new RuntimeException("Failed to create GLFW window");
            }
            c.putResource(win,  window);//window resource
            
            //for fractional window pos/size
            //glfwSetWindowSize(window, w, h);
            if(x<0||y<0){
                glfwSetWindowPos(window, 
                        (int)(vm.width()*(1 - pos.xf())/2),
                        (int)(vm.height()*(1 - pos.yf())/2)
                );
            }else{
                glfwSetWindowPos(window, (int)x*vm.width(), (int)y*vm.height());
            }


            //open and begin
            glfwMakeContextCurrent(window);
            if(gs!=0)glfwSwapInterval(1);
            GLCapabilities gc = createCapabilities();
            
        };
    }
}
