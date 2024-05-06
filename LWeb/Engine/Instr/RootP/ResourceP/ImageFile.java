package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.GLEU.Texture;
import java.io.File;
import java.net.URI;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;

public class ImageFile {
    URI file;
    Texture tex;
    
    public static Object getRsc(ByteCounter i, Core c){
        URI uri = c.getResource(byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()}), URI.class);
        File fl = new File(uri);
//        System.out.println(lognm()+""+uri.toString()+" "+fl+" "+fl.getAbsolutePath()+" "+fl.exists());
        return new ImageFile(uri);
    }
    
    public ImageFile(URI uri){
        file=uri;
    }
    public Texture getImage(){
        if(tex==null){
            tex = new Texture()
                .setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT)	
                .setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT)
                .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)	
                .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)	
                .loadImage(file, true, true);
        }
        return tex;
    }
}
