package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import LWeb.Engine.Util.GLEU.FrameBuffer;
import LWeb.Engine.Util.GLEU.Texture;
import java.net.URI;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;

public class ImageBuffer {
    public static ResourceInst.RByteCol getBytes(int Bbuffer){
        return new ResourceInst.RByteCol(14, itb(Bbuffer));
    }
    
    int id;
    Texture tex;
    Core c;
    
    public static Object getRsc(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return new ImageBuffer(c, id);
    }
    
    public ImageBuffer(Core co, int id){
        this.id=id;
        c=co;
    }
    public Texture getImage(){
        if(tex==null){
            tex = c.getResource(id, FrameBuffer.class).getTex();
        }
        return tex;
    }
}
