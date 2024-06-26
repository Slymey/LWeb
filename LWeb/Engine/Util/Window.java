package LWeb.Engine.Util;

import LWeb.Common.Common;
import static LWeb.Common.Common.ats;
import static LWeb.Common.Common.getCachedFilePath;
import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.rangeA_Z;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import LWeb.Engine.Util.GLEU.FontPainter;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import java.io.File;
import java.io.IOException;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            File fl = new File("src/LWeb/Engine/Util/Native");
            System.load(fl.getAbsolutePath()+"\\opengl32.dll");
        }
    }
    
    public Window(Runnable[] inst, Core c){
        this.c=c;
        this.work = inst;
    }
    public void run() {
            System.out.println("Hello LWJGL " + Version.getVersion() + "!");

            loop(true);
    }

    private void init() {
            
    }

    private void loop(boolean first) {
        try{
            do{
                work[c.progCounter++].run();
            }while(c.progCounter<work.length);
        }catch(Exception e){
            if(!first){
                throw e;
            }
            System.out.println(lognm()+"c.progCounter: "+c.progCounter);
            e.printStackTrace();
            try{
                Path sogl = null;
                if(System.getProperty("sun.arch.data.model").equals("32")){
                    sogl = getCachedFilePath("LWeb/Engine/Util/Native/x86/opengl32.dll");
                }else{
                    sogl = getCachedFilePath("LWeb/Engine/Util/Native/x64/opengl32.dll");
                }
                //System.out.println(lognm()+""+sogl);
                System.load(sogl.toString());
                System.out.println(lognm()+"Switching to software renderer");
                loop(false);
            }catch(IOException ex){
                System.out.println(lognm()+"c.progCounter: "+c.progCounter);
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
        }
    }
    
    
    
    
    //for (int i = 0; i < 20&&c.progCounter<work.length; i++) {
//                System.out.println(lognm()+""+c.progCounter);
            //}
            //long window = c.getNamedApi("w", long.class);

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
    public boolean isReady(){
        return ready;
    }
    
    static boolean keyFired=false;
    
    public static void processInput(long window, Core c){
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);
//        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS)
//            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS)
//            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        glfwSetScrollCallback(window, (long wd, double xoffset, double yoffset)->{
//            System.out.println(lognm()+""+xoffset+" "+yoffset);
        });
        glfwSetCharCallback(window, (windw, ch) -> {
            String s = c.getNamedApi("$s", String.class);
            if(s!=null){
                s += (char)ch;
                c.putNamedApi("$s", s);
            }
            //System.out.println(lognm()+""+ch);
        });
        glfwSetKeyCallback(window, (windw, key, scancode, action, mods) -> {
            String s = c.getNamedApi("$s", String.class);
            if(s!=null&&(action==1||action==2)){//backsoace 259, del 261, key 
                if(key==259&&s.length()>0){
                    s=s.substring(0, s.length()-1);
                }else if(key==261&&s.length()>0){
                    s=s.substring(1);
                }
                c.putNamedApi("$s", s);
            }
            //System.out.println(lognm()+""+key+" "+scancode+" "+action+" "+mods+" "+(char)(key+((mods==1||!rangeA_Z.equals(key))?0:32)));
        });
        glfwSetMouseButtonCallback(window, (windw, button, action, mods) -> {
            c.putNamedApi("$b", action);
//            System.out.println(lognm()+""+button+" "+action+" "+mods);
        });
        glfwSetCursorPosCallback(window, (windw, xpos, ypos) -> {
            int w[] = new int[1];
            int h[] = new int[1];
            glfwGetWindowSize(windw, w, h);
            c.putNamedApi("$c", new IntPos((int)xpos, h[0]-(int)ypos));
//            System.out.println(lognm()+""+xpos+" "+ypos);
        });
        
    }
    
    
    
}
