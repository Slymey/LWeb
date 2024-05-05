package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.mixIntBytes;
import LWeb.Common.Triple;
import LWeb.Engine.Instr.RootP.ResourceP.Position;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import org.lwjgl.stb.STBImage;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;
import org.lwjgl.system.MemoryStack;


public class Texture {
    private static int idBase=0;
    private static Texture bound;
    
    public final int numId;
    public final int TEX;
    public Position p;
    public Texture(){
        numId=idBase++;
        TEX = glGenTextures();
    }
    
    public Texture setParameter(int pname, int value){
        bind();
        glTexParameteri(GL_TEXTURE_2D, pname, value);
        return this;
    }
    public Texture setImage(Position p){
        bind();
        this.p=p;
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, p.xi(), p.yi(), 0, GL_RGBA, GL_UNSIGNED_BYTE, (float[])null);
        return this;
    }
    public Texture loadImage(String imgFile, boolean shouldFlip, boolean alpha){
        bind();
        int width;
        int height;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            stbi_set_flip_vertically_on_load(shouldFlip);
            
            URL url = Texture.class.getResource(imgFile);
            File file = new File(imgFile);
            String filePath = file.getAbsolutePath();
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if(buffer ==null) {
                      throw new Exception("Can't load file "+imgFile+" "+STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get();
            p= new Position.IntPos(width, height);
            //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexImage2D(GL_TEXTURE_2D, 0, alpha?GL_RGBA:GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            glGenerateMipmap(GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    
    public Texture loadImage(URI imgFile, boolean shouldFlip, boolean alpha){
        bind();
        int width;
        int height;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            stbi_set_flip_vertically_on_load(shouldFlip);
            
            File file = new File(imgFile);
            String filePath = file.getAbsolutePath();
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if(buffer ==null) {
                      throw new Exception("Can't load file "+imgFile+" "+STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get();
            p = new Position.IntPos(width, height);
            //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexImage2D(GL_TEXTURE_2D, 0, alpha?GL_RGBA:GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            glGenerateMipmap(GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    
    public Texture loadImage(BufferedImage imgBuff, boolean shouldFlipH, boolean shouldFlipV, boolean alpha){
        bind();
        int width;
        int height;
        ByteBuffer buffer;
        try (MemoryStack stack = MemoryStack.stackPush()){
            buffer = Common.imageToBuffer(imgBuff, new int[]{2,1,0,3}, shouldFlipH, shouldFlipV);
            width =imgBuff.getWidth();
            height = imgBuff.getHeight();
            p= new Position.IntPos(width, height);
            //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            
            glTexImage2D(GL_TEXTURE_2D, 0, alpha?GL_RGBA:GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            glGenerateMipmap(GL_TEXTURE_2D);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return this;
    }
    
    public Texture activateOn(int textureInd){
        glActiveTexture(GL_TEXTURE0+textureInd); // activate the texture unit first before binding texture
        bind();
        return this;
    }
    
    
    
    public Texture bind(){
        bind(this);
        return this;
    }
    public static void bind(Texture va){
        glBindTexture(GL_TEXTURE_2D, va.TEX);  
    }
}
