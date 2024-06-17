package LWeb.Engine;

import static LWeb.Common.Common.byteToLong;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.fileToByte;
import static LWeb.Common.Common.getTopClass;
import static LWeb.Common.Common.readFileAsBytes;
import LWeb.Common.UbFunction;
import LWeb.Engine.Util.Window;
import static LWeb.Engine.Util.Window.processInput;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try{
            this.setup(readFileAsBytes(file));
        }catch(IOException ex){
            Logger.getLogger(LWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    
    
    
    public LWeb setup(byte [] bytes){
        getTopClass();
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
    
    public void addCallable(String name, UbFunction<?,?> runnable){
        if(name==null||runnable==null||name.length()==0||name.charAt(0)=='$')return;
        progCore.putCallable(name, runnable);
    }
    public <T> T getNamedAPI(String name, Class<T> type){
        return progCore.getNamedApi(name, type);
    }
    public void putNamedApi(String name, Object data){
        progCore.putNamedApi(name, data);
    }
    public void putCheckedNamedApi(String name, Object data){
        Object o = progCore.getNamedApi(name, Object.class);
        if(!data.getClass().isInstance(o))return;
        progCore.putNamedApi(name, data);
    }
    
    private void start1(){
        progCore.putCallable("$e", (Object... o)->{
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
