package LWeb.Engine;


import static LWeb.Common.Common.*;
import LWeb.Common.*;
import LWeb.Common.DynArray.DynArray;
import LWeb.Common.Range.Range;
import LWeb.Engine.Instr.RootP.*;
import LWeb.Engine.Instr.RootP.HeaderP.*;
import LWeb.Engine.Instr.RootP.PaintP.*;
import LWeb.Engine.Instr.RootP.PaintP.FillP.*;
import LWeb.Engine.Instr.RootP.ResourceP.*;

import java.lang.String;

import static LWeb.Engine.Instr.RootP.ResourceP.BlendMode.BlendModes.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class main2 {
    
//    //<editor-fold defaultstate="collapsed" desc="old byte code input">
//    static byte f[]={(byte)0x70, (byte)0x72, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x00, (byte)0x00, //magic bytes
//                     5, 0,0,0,17, 0,20,0,0, 0,0,3,32, 0,0,2,44,
//                     16, 0,-1,-128,0, 0,0,0,1, (byte)'w', 
//                     16, 0,22,0,11, 0,0,0,1, (byte)'c', 
//                     5,  0,0,0,7,  0,-32,0,0,  0,0,0,1, (byte)'E',  0,0,0,1, 0,-1,-128,0,
//                     4, 1,  0,-1,-128,0, 0,0,1,0, 0,0,1,0, 0,20,0,0,  1,  0,0,0,1, (byte)'A', 
//                     5,  0,0,0,11, 0,-48,0,0,  2,   0,-32,0,0,
//                     4, 3,  0,-1,-128,1,
//                     5,  0,0,0,1, 0,0,0,9, 0,0,0,12, (byte)0x48, (byte)0x65, (byte)0x6c, (byte)0x6c, (byte)0x6f,  (byte)0x20, (byte)0x77, (byte)0x6f, (byte)0x72, (byte)0x6c, (byte)0x64, (byte)0x21,
//                     5,  0,0,0,2, 0,0,0,10, 0,0,0,0, 0,0,0,20, 0,0,0,25, (byte)0x73, (byte)0x72, (byte)0x63, (byte)0x2f, (byte)0x4c, (byte)0x57, (byte)0x65, (byte)0x62, (byte)0x2f, (byte)0x43, (byte)0x6f, (byte)0x6d, (byte)0x6d, (byte)0x6f, (byte)0x6e, (byte)0x2f, (byte)0x61, (byte)0x72, (byte)0x69, (byte)0x61, (byte)0x6c, (byte)0x2e, (byte)0x74, (byte)0x74, (byte)0x66, // type, index, |font| style, point size, font-face-len, ff[]
//                     5, 0,0,0,19, 0,22,0,10,  0,0,0,50,  0,0,0,50,  0,0,0,75,  0,0,0,50,
//                     5, 0,0,0,17, 0,22,0,11,  0,0,0,10, 0,0,0,20,
//                     5,  0,0,0,11, 0,13,0,0,  3,   0,22,0,10,  0,22,0,11,
//                     5,  0,0,0,8,  0,10,0,1,  6,  0,0,0,12, 0,0,0,0,  1,  0,-48,0,0,  //start is absolute, endi is relative and condition is inverted
//                     19,  -16,0,0,0,  0,20,0,0, //--------redo resounces
//                     20,  -16,0,0,0,
//                     1,  -16,0,0,3,  0,0,1,75,  0,0,0,50,  12,0,0,0,
//                     5, 0,0,0,17, 0,20,0,1, 0,0,0,10, 0,0,0,20,
//                     2, 2, -16,0,0,3, 0,0,0,9, 0,0,0,10, 0,20,0,1,  // string-draw target, resource, font resource, x, y, 
//                     //5, 0,0,0,3, 0,0,0,2, 0,0,0,2, -1,0,0,0, -1,0,-1,0, -1,-1,-1,-1, 0,0,0,0,// palette
//                     //6, 1, -16,0,0,3, 0,0,0,2, //deal wit pallete swap later
//                     5, 0,0,0,6, 0,0,0,3,  -1,-1,-1,0,
//                     5, 0,0,0,6, 0,0,0,4,  100,0,-1,0,
//                     5, 0,0,0,6, 0,0,0,5,  100,0,0,-1,
//                     25, 0,0,0,6, 0,0,0,5, 0,0,0,4, 0,13,0,0,  0,-1,-1,-1,
//                     5, 0,0,0,20, 0,43,0,0, 0x56,0x56,0x56,0x56,0, //blend mode
//                     5, 0,0,0,19, 0,22,0,0,  0,0,0,0,  0,0,0,20,  0,0,1,75,  0,0,0,50,
//                     3,  -16,0,0,3,  -16,0,0,0,  0,22,0,0, 0,43,0,0,
//                     1,  -16,0,0,1,  0,0,0,75,  0,0,0,50,  12,0,0,0,
//                       2,  1,  1,  -16,0,0,1,  0,0,0,3,
//                     5, 0,0,0,19, 0,22,0,0,  0,0,0,25,  0,0,0,25,  0,0,0,75,  0,0,0,50,
//                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,//1,  0,0,0,2,  0,0,0,75,  0,0,0,50,
//                       2,  1,  1,  -16,0,0,1,  0,0,0,4,
//                     5, 0,0,0,19, 0,22,0,0,  0,0,0,75,  0,0,0,25,  0,0,0,75,  0,0,0,50,
//                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,//1,  0,0,0,3,  0,0,0,75,  0,0,0,50,
//                       2,  1,  1,  -16,0,0,1,  0,0,0,5,
//                     5, 0,0,0,19, 0,22,0,0,  0,0,0,50,  0,0,0,50,  0,0,0,75,  0,0,0,50,
//                     3,  -16,0,0,1,  -16,0,0,0,  0,22,0,0, 0,43,0,0,
//                     5, 0,0,0,5, 0,0,0,6, 0,0,0,10, (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x5f, (byte)0x31, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67,
//                     5, 0,0,0,13, 0,0,0,7, 0,0,0,6,
//                     5, 0,0,0,5, 0,0,0,8, 0,0,0,9, (byte)0x69, (byte)0x6d, (byte)0x61, (byte)0x67, (byte)0x65, (byte)0x2e, (byte)0x70, (byte)0x6e, (byte)0x67, 
//                     5, 0,0,0,20, 0,43,0,1, 0,0,0,0,0, //blend mode
//                     5, 0,0,0,19, 0,22,0,1,  0,0,0,100,  0,0,0,80, 0,0,0,100,  0,0,0,100,
//                       2,  1,  3,  -16,0,0,0,  0,0,0,7,  0,22,0,1,
//                     //9,  -16,0,0,0,  0,0,0,8,
//                     10, -16,0,0,0, 0,-1,-128,0, 0,-1,-128,1,
//                     //8,  0,(byte)0xff,0,0,
//                     18, 0,10,0,1,
//                     17};//end
//    //</editor-fold>
//    
    
    
    
    static byte f[]= flatten(ltb(LWeb.MAGIC_BYTES),
            Position.IntPos.getBytes(800, 556).at(0x140000),//window dimentions
            NamedApi.getBytes(0xff8000, "$w"),
            NamedApi.getBytes(0x16000b, "$c"),//cursor position callback
            Callable.getBytes("$e", 0xff8000).at(0xe00000),//window event processor
            Window.getBytes(0xff8000, 256, 256, 0x140000, true, "A"),//window itself
            Condition.CallbackCondition.getBytes(0xe00000).at(0xd00000),//loop condition
            Screen.getBytes(0xff8001),//screen
            WholeNumber.getBytes(0).at(0x16001b),
            PlainText.getBytes("Hello world!").at(0x0f0009),
            CopyResource.getBytes(0x0f0009, 0x000009),
            NamedApi.getBytes(0x0f0009, "$s"),
            FontFace.getBytes(0, 40, "src/LWeb/Common/arial.ttf").at(0x00000a),
            NamedApi.getBytes(0x16001b, "$b"),
            Box.IntBox.getBytes(50, 50, 75, 50).at(0x16000a),
            Position.IntPos.getBytes(10, 20).at(0x16000b),
            Position.IntPos.getBytes(150,  200).at(0x230000),
            Position.IntPos.getBytes(230,  400).at(0x230001),
            Position.IntPos.getBytes(250,  180).at(0x230002),
            Box.IntBox.getBytes(300, 300, 300, 40).at(0x160008),
            Condition.BoxCondition.getBytes(0x16000a, 0x16000b).at(0x0d0000),//hover detection
            Condition.EqualsCondition.getBytes(0x16001b, 1).at(0x0d0001),//mouse detection
            Condition.BoxCondition.getBytes(0x160008, 0x16000b).at(0x0d0002),//text box hover
            Condition.ChainAndCondition.getBytes(0x0d0001,0x0d0002).at(0x0d0003),//click on box detection
            Condition.EqualsCondition.getBytes(0x16001c, 1).at(0x0d0004),//text selection condition
            Condition.NegativeCondition.getBytes(0x0d0002).at(0x0d0005),//not in box detection
            Condition.ChainAndCondition.getBytes(0x0d0001,0x0d0005).at(0x0d0006),//out of box click
            Condition.EqualsCondition.getBytes(0x16001c, 0).at(0x0d0007),//text non selection condition
            WholeNumber.getBytes(0).at(0x16001c),//is text box selected
            Buffer.getBytes(0xf0000003, 331, 50, 0x0c000003),//                                      V - add relative to declaration loop positions
            Loop.ConditionalLoop.getBytes(false, true, true, 29, 0, 0xd00000).at(0x0a0001),//draw loop dcl
            BufferPtr.getBytes(0xf0000000, 0x140000),//draw buffer
            ClearBuffer.getBytes(0xf0000000),
            ClearBuffer.getBytes(0xf0000003),
            
            WholeNumber.getBytes(1).atCond(0x16001c, 0x0d0003),//select text box
            WholeNumber.getBytes(0).atCond(0x16001c, 0x0d0006),//select text box
            CondIntruction.conditional(CopyResource.getBytes(0x0f0009, 0x000009), 0x0d0004),
            CondIntruction.conditional(CopyResource.getBytes(0x000009, 0x0f0009), 0x0d0007),
            
            FlatColor.getBytes(0x222222ff).at(0x000006),
            FlatColor.getBytes(0x000000ff).at(0x000007),
            Border.getBytes(1, 1, 1, 1, 0x000006, 0x000006, 0x000006, 0x000006, 0, 0, 0, 0, 0, 0, 0, 0).at(0x330000),
            Buffer.getBytes(0xf0000005, 300, 40, 0x0c000005),
            BlendMode.noBlending().at(0x2b0001),
            Position.IntPos.getBytes(0, -10).at(0x140001),
            DrawString.getBytes(0xf0000005, 0x000009, 0x00000a, 0x140001, 0x000007),
            StackBorder.getBytes(0xf0000005, 0xf0000000, 0x160008, 0x2b0001, 0x330000),
            
            FlatColor.getBytes(0xffff00ff).at(0x000003),
            FlatColor.getBytes(0x6400ffff).at(0x000004),
            FlatColor.getBytes(0x640000ff).at(0x000005),
            FlatColor.getBytes(0x00ffffff).atCond(0x000005, 0x0d0000),
            BlendMode.uniformBlending(SRC_ALPHA, ONE_MINUS_SRC_ALPHA).at(0x2b0000),
            Box.IntBox.getBytes(500, 400, 331, 50).at(0x160000),
            //Stack.getBytes(0xf0000003, 0xf0000000, 0x160000, 0x2b0001),
            Buffer.getBytes(0xf0000001, 75, 50, 0x0c000001),
            Solid.getBytes(0xf0000001, 0x000003),
            Box.IntBox.getBytes(25, 25, 75, 50).at(0x160000),
            Stack.getBytes(0xf0000001, 0xf0000000, 0x160000, 0x2b0000),
            Solid.getBytes(0xf0000001, 0x000004),
            Box.IntBox.getBytes(75, 25, 75, 50).at(0x160100),
            Stack.getBytes(0xf0000001, 0xf0000000, 0x160100, 0x2b0000),
            Solid.getBytes(0xf0000001, 0x000005),
            Box.IntBox.getBytes(50, 50, 75, 50).at(0x160200),
            
            Buffer.getBytes(0xf0000011, 25, 20, 0x0c000002),            
            Solid.getBytes(0xf0000011, 0x000003),
            Box.IntBox.getBytes(10, 10, 25, 20).at(0x160300),
            Stack.getBytes(0xf0000011, 0xf0000001, 0x160300, 0x2b0000),
            
            Stack.getBytes(0xf0000001, 0xf0000000, 0x160200, 0x2b0000),
            URLRelative.getBytes("test_1.png").at(0x000006),
            ImageFile.getBytes(0x000006).at(0x000007),
            URLRelative.getBytes("image.png").at(0x000008),
            Box.IntBox.getBytes(100, 80, 50, 50).at(0x160001),
            Image.getBytes(0xf0000000, 0x000007, 0x160001),
            WholeNumber.getBytes(1).at(0x230003),
            FloatNumber.getBytes(4f).at(0x230004),
            WholeNumber.getBytes(5).at(0x230005),
            Line.getBytes(0xf0000000, 0x230000, 0x230001, 0x230005, 0x000015),
            
            Condition.EqualsCondition.getBytes(0x16001b, 1).at(0x0d0001),
            CondIntruction.conditional(CopyResource.getBytes(0x16000b, 0x230002), 0x0d0001),
            
            FlatColor.getBytes(0x000000ff).at(0x000015),
            Bezier.getBytes(0xf0000000, 0x230000, 0x230001, 0x230002, 0x230003,0x230004, 0x000015),
//            Curve.getBytes(272, 281, 103, 367, 195, 503, 189, 349, 170, 456).at(0x270000),
//            DrawCurve.getBytes(0xf0000000, 0x270000, 0x230003,0x000015),
            Curve.getBytes(8, 223, 151, 226, 195, 31, 192, 22, 194, 9, 210, 9, 210, 9, 207, 26, 232, 107, 207, 281, 7, 235, 8, 223, 8, 223, 8, 223, 8, 223).at(0x270001),
            DrawCurve.getBytes(0xf0000000, 0x270001, 0x230003,0x000015),
            Curve.getBytes(8, 230, 196, 270, 204, 70).at(0x270002),
            DrawCurve.getBytes(0xf0000000, 0x270002, 0x230003,0x000015),
            //OutToFile.getBytes(0xf0000000, 0x000008),
            OutToScreen.getBytes(0xf0000000, 0xff8000, 0xff8001),//output to screen
            //Wait.getBytes(0xff0000),
            BranchLoop.getBytes(0x0a0001),//actual draw loop call
            End.getBytes(0xff8000)//end the runtime

        );
    
    static byte r[]={
    };
    public static void main(String args[]) throws Exception {
        
//        System.out.println(lognm()+""+ats(f));
        
        //org.lwjgl.system.Library.loadSystem();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Natives Directory = " + System.getProperty("java.library.path"));
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        for(URL url: urls){
            System.out.println(url.getFile());
        }
        
//        System.out.println(lognm()+""+getTopClass());
        
        
        LWeb lw = new LWeb(f);
        lw.start();
        
        
        
        
        
        //System.out.println(Core.resources.toString());
        
        
        
        
        
        
        
        
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
/*
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
*/