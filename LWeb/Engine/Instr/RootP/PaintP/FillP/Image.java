package LWeb.Engine.Instr.RootP.PaintP.FillP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Constants;
import LWeb.Engine.Instr.RootP.ResourceP.Box;
import LWeb.Engine.Instr.RootP.ResourceP.ImageFile;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.Texture;
import LWeb.Engine.Util.GLEU.VertexArray;

public class Image {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int image = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int box = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        return () -> {
            FrameBuffer tfb = c.getResource(id, FrameBuffer.class);
            Texture img = c.getResource(image, ImageFile.class).getImage();
            Box bd = c.getResource(box, Box.class);
            
            //c.getResource(blm, BlendMode.class).setBlendMode();
            tfb.draw(img,
                    c.getConstant(Constants.ConstTypes.BOX_SHADER, Shader.class)
                            .use().setUniformF("box", bd.xf(), bd.yf(), bd.zf(), bd.wf()), 
                    c.getConstant(Constants.ConstTypes.BASIC_QUAD, VertexArray.class));
        };
    }
}
