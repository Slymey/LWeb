package LWeb.Engine.Instr.RootP.PaintP.FillP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Constants;
import LWeb.Engine.Instr.RootP.ResourceP.Box;
import LWeb.Engine.Instr.RootP.ResourceP.ImageFile;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.Texture;
import LWeb.Engine.Util.GLEU.VertexArray;

public class Image {
    public static byte[] getBytes(int Bid, int Rimage, int Rbox){
        return flatten(ib(2,1,3), itb(Bid), itb(Rimage), itb(Rbox));
    }
    
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int image = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return () -> {
            FrameBuffer tfb = c.getResource(id, FrameBuffer.class);
            Texture img = c.getResource(image, ImageFile.class).getImage();
            Box bd = c.getResource(box, Box.class);
            Position p = tfb.getTex().p.resolve(Position.class);
            bd.p=p;
            //c.getResource(blm, BlendMode.class).setBlendMode();
            tfb.draw(img,
                    c.getConstant(Constants.ConstTypes.BOX_SHADER, Shader.class)
                            .use().setUniformF("box", bd.xf(), bd.yf(), bd.zf(), bd.wf()), 
                    c.getConstant(Constants.ConstTypes.BASIC_QUAD, VertexArray.class));
        };
    }
}
