package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class End {
    public static Runnable getInst(byte[] o, Counter i, Core c){
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
