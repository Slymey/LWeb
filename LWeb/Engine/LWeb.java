package LWeb.Engine;

import static LWeb.Common.Common.byteToLong;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.fileToByte;
import LWeb.Engine.Util.Window;
import static LWeb.Engine.Util.Window.processInput;
import java.io.File;
import java.util.concurrent.Executors;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class LWeb {
    
    public static final long MAGIC_BYTES=0x724c576562000000l;// magic bytes + mayor/minor/revision version (rLWebMm)
    
    private Exception readError=null;
    private Core progCore;
    private Runnable r[];
    
    public LWeb(){
    }
    public LWeb(byte [] bytes){
        this.setup(bytes);
    }
    public LWeb(File file){
        this.setup(fileToByte(file));
    }
    public LWeb(String file){
        this.setup(fileToByte(file));
    }
    

    
    
    
    
    public LWeb setup(byte [] bytes){
        progCore = new Core();
        ByteCounter i = new ByteCounter(bytes, 0);
        
        long mb = byteToLong(bytes);
        i.inc(8);
        if(MAGIC_BYTES>>8==mb>>8   &&   (MAGIC_BYTES&0xff)<1){
            r = progCore.byteToDraw(i);
        }else{
            readError=new VersionMismatch("Data identifier or version unsupported");
        }
        return this;
    }
    
    public LWeb start() throws Exception{
        if(readError==null){
            start1();
        }else{
            throw readError;
        }
        return this;
    }
    
    private void start1(){
        progCore.putCallable("E", (Object... o)->{
            glfwPollEvents();
            boolean b = glfwWindowShouldClose((long)o[0]);
            processInput((long)o[0], progCore);
//            System.out.println(lognm()+"evnt "+b+" "+(long)o[0]);
            return b;
        });
        
        Window w=new Window(r, progCore);
        progCore.progCounter=0;
        Executors.newSingleThreadExecutor().execute(w);
    }
    
    
    
    
    
    
    
    private static class VersionMismatch extends Exception {
        public VersionMismatch(String message) {
            super(message);
        }
    }
    
    
    
}
