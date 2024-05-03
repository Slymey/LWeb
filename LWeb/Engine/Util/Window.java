package LWeb.Engine.Util;

import static LWeb.Common.Pair.Pair;
import LWeb.Common.Pair;
import LWeb.Engine.Core;
import static LWeb.Engine.Core.resources;
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
    private long window;
    private FrameBuffer screen;
    private boolean ready=false;
    private static boolean sw_render=false;
    public SimpleRemoteThread tr=new SimpleRemoteThread();
    private Iterator<Runnable> work;
    
    static{
        if(sw_render){
            File fl = new File("LWeb/Engine/Util/Native");
            System.load(fl.getAbsolutePath()+"\\opengl32.dll");
        }
    }
    
    public Window(Runnable[] inst){
        this.work = Arrays.asList(inst).iterator();
    }
    public long winId(){return window;}
    public FrameBuffer screenBuffer(){return screen;}
    public void run() {
            System.out.println("Hello LWJGL " + Version.getVersion() + "!");

            init();
            loop();

            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
    }

    private void init() {
        Pair<Integer, Integer> viewBox= Pair(800, 600);
        
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
        
        // Create a GLFW window and set context
        window = glfwCreateWindow(viewBox.first, viewBox.second, "LearnOpenGL", NULL, NULL);
        if(window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        Core.putResource(0xff8000,  this);//window resource
        
        try ( MemoryStack stack = stackPush() ) {//center the window
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        
        
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GLCapabilities gc = createCapabilities(); // Initialize OpenGL capabilities
        
        int flags[] = new int[1];
        glGetIntegerv(GL_CONTEXT_FLAGS, flags);
        if ((flags[0] & GL_CONTEXT_FLAG_DEBUG_BIT)!=0){
            glEnable(GL_DEBUG_OUTPUT);
            glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS); 
            glDebugMessageCallback(LWeb.Engine.Util.GLEU.Common::glDebugOutput, 0l);
            //glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DONT_CARE, 0, true);
        }
        

        FontPainter.viewBox=viewBox;
        glViewport(0, 0, viewBox.first, viewBox.second);
        glfwSetFramebufferSizeCallback(window, (long wd, int widt, int heigh)->{//for resizing window
            viewBox.first=widt;
            viewBox.second=heigh;
            FontPainter.viewBox=viewBox;
            glViewport(0, 0, widt, heigh);
        });  
        
        screen = FrameBuffer.deafult
                .attachShader(FrameBuffer.defaultShader)
                .attachVertexArray(FrameBuffer.deafaultLayout)
                .setClearColor(1,1,1,1);
        
        
//        //<editor-fold defaultstate="collapsed" desc="old">
//            // Setup an error callback. The default implementation
//            // will print the error message in System.err.
//            GLFWErrorCallback.createPrint(System.err).set();
//
//            // Initialize GLFW. Most GLFW functions will not work before doing this.
//            if ( !glfwInit() )
//                    throw new IllegalStateException("Unable to initialize GLFW");
//
//            // Configure GLFW
//            glfwDefaultWindowHints(); // optional, the current window hints are already the default
//            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
//            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//
//            // Create the window
//            window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
//            if ( window == NULL )
//                    throw new RuntimeException("Failed to create the GLFW window");
//            
//            Core.putResource(0xff8000, Long.class, window);
//            // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//
//            // Get the thread stack and push a new frame
//            try ( MemoryStack stack = stackPush() ) {
//                    IntBuffer pWidth = stack.mallocInt(1); // int*
//                    IntBuffer pHeight = stack.mallocInt(1); // int*
//
//                    // Get the window size passed to glfwCreateWindow
//                    glfwGetWindowSize(window, pWidth, pHeight);
//
//                    // Get the resolution of the primary monitor
//                    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//
//                    // Center the window
//                    glfwSetWindowPos(
//                            window,
//                            (vidmode.width() - pWidth.get(0)) / 2,
//                            (vidmode.height() - pHeight.get(0)) / 2
//                    );
//            } // the stack frame is popped automatically
//
//            // Make the OpenGL context current
//            glfwMakeContextCurrent(window);
//            // Enable v-sync
//            glfwSwapInterval(1);
//
//            // Make the window visible
//            glfwShowWindow(window);
//            //</editor-fold>
//            
            
    }

    private void loop() {
            // This line is critical for LWJGL's interoperation with GLFW's
            // OpenGL context, or any context that is managed externally.
            // LWJGL detects the context that is current in the current thread,
            // creates the GLCapabilities instance and makes the OpenGL
            // bindings available for use.
            
            //resources.add();
            
            
            
            // Set the clear color
//            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//            ready=true;
            // Run the rendering loop until the user has attempted to close
            // the window or has pressed the ESCAPE key.
//            boolean fin=false;
            while ( !glfwWindowShouldClose(window) ) {
                    processInput(window);//input

                    while (!tr.paused&&work.hasNext()){
                        work.next().run();
                    }
                    
                    
                    // Poll for window events. The key callback above will only be
                    // invoked during this call.
                    glfwPollEvents();
            }
    }
    public boolean isReady(){
        return ready;
    }
    
    public static void processInput(long window){
        
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);
        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
//        glfwSetKeyCallback(window, (windw, key, scancode, action, mods) -> {
//            System.out.println(lognm()+""+key+" "+scancode+" "+action+" "+mods);
//        });
//        glfwSetMouseButtonCallback(window, (windw, button, action, mods) -> {
//            System.out.println(lognm()+""+button+" "+action+" "+mods);
//        });
//        glfwSetCursorPosCallback(window, (windw, xpos, ypos) -> {
//            System.out.println(lognm()+""+xpos+" "+ypos);
//        });
    }
    
    
    
}
