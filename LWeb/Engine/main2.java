package LWeb.Engine;


import static LWeb.Common.Common.*;
import LWeb.Common.*;
import LWeb.Common.DynArray.DynArray;
import static LWeb.Engine.Core.byteToDraw;
import LWeb.Common.Range.Range;


public class main2 {
//    private int t=0;
    static byte f[]={(byte)0x70, (byte)0x72, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x00, (byte)0x00, //magic bytes
                     1,  0,0,0,0,  0,0,7,-128,  0,0,4,56,//--------redo resounces
                     5,  0,0,0,1, 0,0,0,0, 0,0,0,12, (byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f,  (byte)0x20, (byte)0x77, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21,
                     5,  0,0,0,2, 0,0,0,1, 0,0,0,0, 0,0,0,20, 0,0,0,0,
                     // type, index, |font| style, point size, font-face-len, ff[]
                     1,  0,0,0,3,  0,0,1,75,  0,0,0,50,
                     2, 2, 0,0,0,3, 0,0,0,0, 0,0,0,1, 0,0,0,10, 0,0,0,20,  
                     // string-draw target, resource, font resource, x, y, 
                     5, 0,0,0,3, 0,0,0,2, 0,0,0,2, -1,0,0,0, -1,0,-1,0, -1,-1,-1,-1, 0,0,0,0,
                     // palette
                     6, 1, 0,0,0,3, 0,0,0,2,
                     5, 0,0,0,6, 0,0,0,3, 0, -1,-1,-1,0,
                     5, 0,0,0,6, 0,0,0,4, 0, 100,0,-1,0,
                     5, 0,0,0,6, 0,0,0,5, 0, 100,0,0,-1,
                     3,  0,0,0,3,  0,0,0,0,  0,0,0,0,  0,0,0,20,
                     1,  0,0,0,1,  0,0,0,75,  0,0,0,50,
                       2,  1,  1,  0,0,0,1,  0,0,0,3,
                     3,  0,0,0,1,  0,0,0,0,  0,0,0,25,  0,0,0,25,
                     //1,  0,0,0,2,  0,0,0,75,  0,0,0,50,
                       2,  1,  1,  0,0,0,1,  0,0,0,4,
                     3,  0,0,0,1,  0,0,0,0,  0,0,0,75,  0,0,0,25,
                     //1,  0,0,0,3,  0,0,0,75,  0,0,0,50,
                       2,  1,  1,  0,0,0,1,  0,0,0,5,
                     3,  0,0,0,1,  0,0,0,0,  0,0,0,50,  0,0,0,50,
                     5, 0,0,0,5, 0,0,0,6, 0,0,0,10, (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x5f, (byte)0x31, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67,
                     5, 0,0,0,6, 0,0,0,7, 4, 0,0,0,6,
                     5, 0,0,0,5, 0,0,0,8, 0,0,0,9, (byte)0x69, (byte)0x6d, (byte)0x61, (byte)0x67, (byte)0x65, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67, 
                     1,  0,0,0,4,  0,0,0,100,  0,0,0,100,
                       2,  1,  3,  0,0,0,4,  0,0,0,7,
                     3,  0,0,0,4,  0,0,0,0,  0,0,0,100,  0,0,0,80,
                     9,  0,0,0,8,
                     10, 0,0,0,0,
                     8,  0,(byte)0xff,0,0,
                     0};//end
    /*
    static Object q[]={CoreOps.BUFFER, 0, 1920, 1080,
                       CoreOps.RESOURCE, "String", 17, "Hello world!",
                       CoreOps.RESOURCE, "Font-face", 0, "",
                       CoreOps.BUFFER, 3, 331,  50,
                       CoreOps.Paint.STRING, 3, 0,  1, 10, 20,
                       CoreOps.BUFFER, 1, 150,  100,
                       CoreOps.Paint.Fill.SOLID, 1, 0xff000000,
                       CoreOps.STACK, 1, 0, 50, 50,
                       CoreOps.BUFFER, 2, 150,  100,
                       CoreOps.Paint.Fill.SOLID, 1, 0x00ff0000,
                       CoreOps.STACK, 2, 0, 100, 50,
                       CoreOps.END
    };*/
    static Runnable r[]={
    };
    public static void main(String args[]) throws Exception {
        
        LWeb lw = new LWeb(f);
        lw.start();
        System.out.println(Core.resources.toString());
        
        /*
        Canvas c= new Canvas();
        Graphics g = c.getGraphics();
        
        
        for(int i:new Range(0,0)){
            ptr=0;  
            while(f[ptr]!=0){
                top();
            }
        }
        */
        
        
        
        
        
        
        //<editor-fold defaultstate="collapsed" desc="file stuf">
        /*File f2 = new File("a.html");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f2));
            
            
            
            br.close();
        } catch (IOException ex) {
            //ex.printStackTrace();
        } */
        
        //</editor-fold>
        
        /*
        https://en.wikipedia.org/wiki/Alpha_compositing
        https://en.wikipedia.org/wiki/Blend_modes
        https://css-tricks.com/almanac/properties/m/mix-blend-mode/
        https://developer.mozilla.org/en-US/docs/Web/CSS/mix-blend-mode
        
        https://bsouthga.dev/posts/color-gradients-with-python
        */
        
    }
    static byte b(int i){
        return (byte)(i&0xff);
    }
}
