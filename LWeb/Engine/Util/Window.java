package LWeb.Engine.Util;

import static LWeb.Common.Common.lognm;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import LWeb.Engine.Util.GLEU.FontPainter;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import java.io.File;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Arrays;
import java.util.Iterator;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_CONTEXT_FLAGS;
import static org.lwjgl.opengl.GL43.GL_CONTEXT_FLAG_DEBUG_BIT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements Runnable{

    // The window handle
    private boolean ready=false;
    private static boolean sw_render=false;
    private final Core c;
    private Runnable[] work;
    
    static{
        if(sw_render){
            File fl = new File("LWeb/Engine/Util/Native");
            System.load(fl.getAbsolutePath()+"\\opengl32.dll");
        }
    }
    
    public Window(Runnable[] inst, Core c){
        this.c=c;
        this.work = inst;
    }
    public void run() {
            System.out.println("Hello LWJGL " + Version.getVersion() + "!");

            loop();
    }

    private void init() {
            
    }

    private void loop() {
        do{
            for (int i = 0; i < 20&&c.progCounter<work.length; i++) {
                work[c.progCounter++].run();
            }

            long window = c.getNamedApi("w", long.class);

//            glfwPollEvents();
//            if(glfwWindowShouldClose(window))break;
//            processInput(window);

//            FontPainter.viewBox=viewBox;
//        glViewport(0, 0, viewBox.first, viewBox.second);
//        glfwSetFramebufferSizeCallback(window, (long wd, int widt, int heigh)->{//for resizing window
//            viewBox.first=widt;
//            viewBox.second=heigh;
//            FontPainter.viewBox=viewBox;
//            glViewport(0, 0, widt, heigh);
//        });
            
            
        }while(c.progCounter<work.length);
    }
    public boolean isReady(){
        return ready;
    }
    
    public static void processInput(long window, Core c){
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);
        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        glfwSetScrollCallback(window, (long wd, double xoffset, double yoffset)->{
//            System.out.println(lognm()+""+xoffset+" "+yoffset);
        });
        glfwSetKeyCallback(window, (windw, key, scancode, action, mods) -> {
//            System.out.println(lognm()+""+key+" "+scancode+" "+action+" "+mods);
        });
        glfwSetMouseButtonCallback(window, (windw, button, action, mods) -> {
//            System.out.println(lognm()+""+button+" "+action+" "+mods);
        });
        glfwSetCursorPosCallback(window, (windw, xpos, ypos) -> {
            int w[] = new int[1];
            int h[] = new int[1];
            glfwGetWindowSize(windw, w, h);
            c.putNamedApi("c", new IntPos((int)xpos, h[0]-(int)ypos));
//            System.out.println(lognm()+""+xpos+" "+ypos);
        });
    }
    
    
    
}
