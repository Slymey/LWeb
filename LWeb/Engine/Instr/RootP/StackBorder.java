package LWeb.Engine.Instr.RootP;

import LWeb.Common.Color;
import static LWeb.Common.Color.color;
import LWeb.Common.Common;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.clamp;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Util.ColorMixers;
import LWeb.Common.Range.Range;
import LWeb.Engine.Constants;
import LWeb.Engine.Instr.RootP.ResourceP.BlendMode;
import LWeb.Engine.Instr.RootP.ResourceP.Border;
import LWeb.Engine.Instr.RootP.ResourceP.Box;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.VertexArray;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class StackBorder {
    public static byte[] getBytes(int Bsource, int Btarget, int Rbox, int RblendMode, int Rborder){
        return flatten(ib(28), itb(Bsource), itb(Btarget), itb(Rbox), itb(RblendMode), itb(Rborder));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int source = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int target = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int blm = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int brd = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            FrameBuffer sfb = c.getResource(source, FrameBuffer.class);
            FrameBuffer tfb = c.getResource(target, FrameBuffer.class);
            //FrameBuffer tuogv = c.getResource(0xf0000000, FrameBuffer.class);//0x140000
            Box locs = c.getResource(box, Box.class);
            Position p = tfb.getTex().p.resolve(Position.class);
            Position sp = sfb.getTex().p.resolve(Position.class);
            Border b = c.getResource(brd, Border.class);
            //Position jno = tuogv.getTex().p.resolve(Position.class);
//            if(locs.wi()==20||true){
//            System.out.println(lognm()+" ----- ");
//            System.out.println(lognm()+"ptr: "+tfb.getTex().p.ptr+" "+sfb.getTex().p.ptr);
//            System.out.println(lognm()+""+locs.xf()+" "+ locs.yf()+" "+ locs.zf()+" "+ locs.wf()+" "+locs.xi()+" "+ locs.yi()+" "+ locs.zi()+" "+ locs.wi());
//            System.out.println(lognm()+""+p.xf()+" "+p.yf()+" "+p.xi()+" "+p.yi());
//            System.out.println(lognm()+""+sp.xf()+" "+sp.yf()+" "+sp.xi()+" "+sp.yi());
//            System.out.println(lognm()+""+jno.xf()+" "+jno.yf()+" "+jno.xi()+" "+jno.yi());
//            System.out.println(lognm()+""+c.creenSize.xf()+" "+c.creenSize.yf()+" "+c.creenSize.xi()+" "+c.creenSize.yi());
//            System.out.println(lognm()+"bef: "+source+" "+target);
//            System.out.println(lognm()+"feb: "+sfb.FBO+" "+tfb.FBO);
//            System.out.println(lognm()+""+1.0f*locs.zi()/(sp.xi())+" "+ 1.0f*locs.wi()/(sp.yi()));
//            }
            locs.p=c.creenSize;
            b.p=p;
//            if(locs.wi()==20||true){
//                System.out.println(lognm()+""+locs.xf()+" "+ locs.yf()+" "+ locs.zf()+" "+ locs.wf()+" "+locs.xi()+" "+ locs.yi()+" "+ locs.zi()+" "+ locs.wi());
//                //System.out.println(lognm()+""+locs.xf()*(p.xi())+" "+ locs.yf()*(p.yi())+" "+ locs.zf()*(p.xi())+" "+ locs.wf()*(p.yi()));
//                
//            }
            c.getResource(blm, BlendMode.class).setBlendMode();
            tfb.draw(sfb.getTex(),
                    c.getConstant(Constants.ConstTypes.BORDER_SHADER, Shader.class)
                            .use()
                            .setUniformF("box", locs.xf(), locs.yf(), locs.zf(), locs.wf())
                            .setUniformF("wt", b.wt())
                            .setUniformF("wr", b.wr())
                            .setUniformF("wb", b.wb())
                            .setUniformF("wl", b.wl())
                            .setUniformF("ct", b.ct().rf(), b.ct().gf(), b.ct().bf(), b.ct().af())
                            .setUniformF("cr", b.cr().rf(), b.cr().gf(), b.cr().bf(), b.cr().af())
                            .setUniformF("cb", b.cb().rf(), b.cb().gf(), b.cb().bf(), b.cb().af())
                            .setUniformF("cl", b.cl().rf(), b.cl().gf(), b.cl().bf(), b.cl().af())
                            .setUniformF("bss", b.ssx(), b.ssy())
                            .setUniformF("bse", b.sex(), b.sey())
                            .setUniformF("bes", b.esx(), b.esy())
                            .setUniformF("bee", b.eex(), b.eey()),
                            //.setUniformF("viewBox", 0, 0, 1.0f*locs.zi()/(sp.xi()), 1.0f*locs.wi()/(sp.yi())), 
                    c.getConstant(Constants.ConstTypes.BASIC_QUAD, VertexArray.class));
            
            
            
            
//            BufferedImage bis = c.getResource(source, BufferedImage.class);
//            BufferedImage bit = c.getResource(target, BufferedImage.class);
//            int[] rgbArray=null;
//            
//            rgbArray = bis.getRGB(0, 0, bis.getWidth(), bis.getHeight(), rgbArray, 0, bis.getWidth());
//            BiFunction<Color,Color,Color> mixer =ColorMixers.NORMAL.func;
//            //BiFunction<Color,Color,Color> mixer = ColorMixers.MULTIPLY.func;
//            for(int w:new Range(0,bis.getWidth()-1)){
//                for(int h:new Range(0,bis.getHeight()-1)){
//                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
//                    int xt=clamp(w+x,0,bit.getWidth());
//                    int yt=clamp(h+y,0,bit.getWidth());
//                    bit.setRGB( xt, 
//                                yt, 
//                               mixer.apply(
//                                      color(rgbArray[w+h*bis.getWidth()]),
//                                      color(bit.getRGB(xt,yt))).color());
//                }
//            }
        }; 
    }
}
