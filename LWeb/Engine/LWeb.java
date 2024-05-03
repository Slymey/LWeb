package LWeb.Engine;

import static LWeb.Common.Common.byteToLong;
import LWeb.Common.Counter;
import static LWeb.Engine.Core.byteToDraw;
import static LWeb.Engine.Core.resources;
import LWeb.Engine.Util.Window;
import java.util.concurrent.Executors;


public class LWeb {
    
    private static final long MAGIC_BYTES=0x70724c5765620000l;// magic bytes + mayor/minor version (prLWebMm)
    
    private Exception readError=null;
    private Runnable r[];
    
    public LWeb(byte [] bytes){
        
        Counter i = new Counter(0);
        long mb = byteToLong(bytes);
        i.inc(8);
        if(MAGIC_BYTES>>8==mb>>8   &&   (MAGIC_BYTES&0xff)<1){
            r = byteToDraw(bytes, i);
        }else{
            readError=new VersionMismatch("Data identifier or version unsupported");
        }
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
        Window w=new Window(r);
        resources.set(0xff0000,  w.tr);
        Executors.newSingleThreadExecutor().execute(w);
        
    }
    
    
    
    
    
    
    
    private static class VersionMismatch extends Exception {
        public VersionMismatch(String message) {
            super(message);
        }
    }
    
    
    
}
