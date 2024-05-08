package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ib;
import LWeb.Engine.Core;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class End {
    public static byte[] getBytes(){
        return ib(17);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        i.ended=true;
        return ()->{
            System.out.println(lognm()+"end");
            long window = c.getResource((int)c.namedApi.get("w"), long.class);
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            
            c.progThread.interrupt();
            
            c.terminationCallback.run();
            /*
            File outputfile = new File("image.png");
            try {
                ImageIO.write(buffers.get(0), "png", outputfile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            */
        };
    }
}
