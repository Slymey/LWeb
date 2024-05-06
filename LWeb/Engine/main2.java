package LWeb.Engine;


import static LWeb.Common.Common.*;
import LWeb.Common.*;
import LWeb.Common.DynArray.DynArray;
import LWeb.Common.Range.Range;


public class main2 {

    
    
    
    
    
    
    
    static byte f[]={(byte)0x70, (byte)0x72, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x00, (byte)0x00, //magic bytes
                     5, 0,0,0,17, 0,20,0,0, 0,0,3,32, 0,0,2,44,
                     16, 0,-1,-128,0, 0,0,0,1, (byte)'w', 
                     16, 0,22,0,11, 0,0,0,1, (byte)'c', 
                     5,  0,0,0,7,  0,-32,0,0,  0,0,0,1, (byte)'E',  0,0,0,1, 0,-1,-128,0,
                     4, 1,  0,-1,-128,0, 0,0,1,0, 0,0,1,0, 0,20,0,0,  1,  0,0,0,1, (byte)'A', 
                     5,  0,0,0,11, 0,-48,0,0,  2,   0,-32,0,0,
                     4, 3,  0,-1,-128,1,
                     5,  0,0,0,1, 0,0,0,9, 0,0,0,12, (byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f,  (byte)0x20, (byte)0x77, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21,
                     5,  0,0,0,2, 0,0,0,10, 0,0,0,0, 0,0,0,20, 0,0,0,25, (byte)0x73, (byte)0x72, (byte)0x63, (byte)0x2f, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x2f, (byte)0x43, (byte)0x6f, (byte)0x6d, (byte)0x6d, (byte)0x6f, (byte)0x6e, (byte)0x2f, (byte)0x61, (byte)0x72, (byte)0x69, (byte)0x61, (byte)0x6c, (byte)0x2e, (byte)0x74, (byte)0x74, (byte)0x66, // type, index, |font| style, point size, font-face-len, ff[]
                     5, 0,0,0,19, 0,22,0,10,  0,0,0,50,  0,0,0,50,  0,0,0,75,  0,0,0,50,
                     5, 0,0,0,17, 0,22,0,11,  0,0,0,10, 0,0,0,20,
                     5,  0,0,0,11, 0,13,0,0,  3,   0,22,0,10,  0,22,0,11,
                     5,  0,0,0,8,  0,10,0,1,  6,  0,0,0,12, 0,0,0,0,  1,  0,-48,0,0,  //start is absolute, endi is relative and condition is inverted
                     19,  -16,0,0,0,  0,20,0,0, //--------redo resounces
                     20,  -16,0,0,0,
                     1,  -16,0,0,3,  0,0,1,75,  0,0,0,50,  12,0,0,0,
                     5, 0,0,0,17, 0,20,0,1, 0,0,0,10, 0,0,0,20,
                     2, 2, -16,0,0,3, 0,0,0,9, 0,0,0,10, 0,20,0,1,  // string-draw target, resource, font resource, x, y, 
                     //5, 0,0,0,3, 0,0,0,2, 0,0,0,2, -1,0,0,0, -1,0,-1,0, -1,-1,-1,-1, 0,0,0,0,// palette
                     //6, 1, -16,0,0,3, 0,0,0,2, //deal wit pallete swap later
                     5, 0,0,0,6, 0,0,0,3,  -1,-1,-1,0,
                     5, 0,0,0,6, 0,0,0,4,  100,0,-1,0,
                     5, 0,0,0,6, 0,0,0,5,  100,0,0,-1,
                     25, 0,0,0,6, 0,0,0,5, 0,0,0,4, 0,13,0,0,  0,-1,-1,-1,
                     5, 0,0,0,20, 0,43,0,0, 0x56,0x56,0x56,0x56,0, //blend mode
                     5, 0,0,0,19, 0,22,0,0,  0,0,0,0,  0,0,0,20,  0,0,1,75,  0,0,0,50,
                     3,  -16,0,0,3,  -16,0,0,0,  0,22,0,0, 0,43,0,0,
                     1,  -16,0,0,1,  0,0,0,75,  0,0,0,50,  12,0,0,0,
                       2,  1,  1,  -16,0,0,1,  0,0,0,3,
                     5, 0,0,0,19, 0,22,0,0,  0,0,0,25,  0,0,0,25,  0,0,0,75,  0,0,0,50,
                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,//1,  0,0,0,2,  0,0,0,75,  0,0,0,50,
                       2,  1,  1,  -16,0,0,1,  0,0,0,4,
                     5, 0,0,0,19, 0,22,0,0,  0,0,0,75,  0,0,0,25,  0,0,0,75,  0,0,0,50,
                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,//1,  0,0,0,3,  0,0,0,75,  0,0,0,50,
                       2,  1,  1,  -16,0,0,1,  0,0,0,5,
                     5, 0,0,0,19, 0,22,0,0,  0,0,0,50,  0,0,0,50,  0,0,0,75,  0,0,0,50,
                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,
                     5, 0,0,0,5, 0,0,0,6, 0,0,0,10, (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x5f, (byte)0x31, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67,
                     5, 0,0,0,13, 0,0,0,7, 0,0,0,6,
                     5, 0,0,0,5, 0,0,0,8, 0,0,0,9, (byte)0x69, (byte)0x6d, (byte)0x61, (byte)0x67, (byte)0x65, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67, 
                     5, 0,0,0,20, 0,43,0,1, 0,0,0,0,0, //blend mode
                     5, 0,0,0,19, 0,22,0,1,  0,0,0,100,  0,0,0,80, 0,0,0,100,  0,0,0,100,
                       2,  1,  3,  -16,0,0,0,  0,0,0,7,  0,22,0,1,
                     //9,  -16,0,0,0,  0,0,0,8,
                     10, -16,0,0,0, 0,-1,-128,0, 0,-1,-128,1,
                     //8,  0,(byte)0xff,0,0,
                     18, 0,10,0,1,
                     17};//end
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
    static byte r[]={
    };
    public static void main(String args[]) throws Exception {
        
        
        
        
        
        
        
        r = new byte[]{(byte)0x70, (byte)0x72, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x00, (byte)0x00, //magic bytes
                     5, 0,0,0,17, 0,20,0,0, 0,0,3,32, 0,0,2,88, //rsc pos id:0x200000 800 556
                     16, 0,-1,-128,0, 0,0,0,1, (byte)'w', //api window "w"
                     5,  0,0,0,7,  0,-32,0,0,  0,0,0,1, (byte)'E',  0,0,0,1, 0,-1,-128,0, //rsc callable id:0xd10000 "E" window
                     4, 1,  0,-1,-128,0, 0,0,1,0, 0,0,1,0, 0,20,0,0,  1,  0,0,0,1, (byte)'A', //window x:256 y:256 pos "A"
                     5, 0,0,0,11, 0,-48,0,0,  2,   0,-32,0,0, //rsc cond id:0xc10000 calback id:0xd10000
                     5, 0,0,0,6, 0,0,0,3,  -1,0,-1,-1, //rsc color 255 0 255 255
                     4, 3,  0,-1,-128,1, // screen buffer 
                     19,  -16,0,0,0,  0,20,0,0,  //buffrPtr id:  id:0x200000
                     5, 0,0,0,8,  0,10,0,1,  6,  0,0,0,8, 0,0,0,0,  1,  0,-48,0,0,  //start is absolute, endi is relative and condition is inverted
                     2,  1,  1,  -16,0,0,0,  0,0,0,3,
                     10, -16,0,0,0, 0,-1,-128,0, 0,-1,-128,1,
                     //8,  0,(byte)0xff,0,0,
                     18, 0,10,0,1,
                     17};//end
        
        
        
        
        LWeb lw = new LWeb(f);
        lw.start();
        //System.out.println(Core.resources.toString());
        
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
    static byte[] a(int... i){
        byte [] out = new byte[i.length];
        for (int j = 0; j < i.length; j++) {
            out[j] = (byte)(i[j]&0xff);
        }
        return out;
    }
    
    /*
    vec3 palette(float t, vec3 o, vec3 a, vec3 f, vec3 p){
        //interpolation, dc offset, amplitude, frequency, phase
	return o + a * cos(TAU*(f*t + p));
    
}
    
    */
    
    
}
