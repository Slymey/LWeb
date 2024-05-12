package LWeb.Engine.Util.GLEU;

import LWeb.Engine.Instr.RootP.ResourceP.ResourcePointer;

import static LWeb.Common.Common.lognm;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;


public class IntLineTexture {
    private static int idBase=0;
    private static IntLineTexture bound;

    public final int numId;
    public final int TEX;
    public ResourcePointer p;
    public int pd;
    public IntLineTexture(){
        numId=idBase++;
        TEX = glGenTextures();
    }
    
    public IntLineTexture setParameter(int pname, int value){
        bind();
        glTexParameteri(GL_TEXTURE_1D, pname, value);
        return this;
    }
    public IntLineTexture setImage(ResourcePointer p){
        bind();
        this.p=p;
        glTexImage1D(GL_TEXTURE_1D, 0, GL_RED_INTEGER, p.resolve(int.class), 0, GL_RED_INTEGER, GL_UNSIGNED_BYTE, (int[])null);
        return this;
    }
    public IntLineTexture setImage(int len){
        bind();
        glTexImage1D(GL_TEXTURE_1D, 0, GL_RED_INTEGER, len, 0, GL_RED_INTEGER, GL_UNSIGNED_BYTE, (int[])null);
        return this;
    }
    public IntLineTexture setImage(int len, int[] data){
        bind();
        glTexImage1D(GL_TEXTURE_1D, 0, GL_RED_INTEGER, len, 0, GL_RED_INTEGER, GL_UNSIGNED_BYTE, data);
        return this;
    }

    public IntLineTexture activateOn(int textureInd){
        glActiveTexture(GL_TEXTURE0+textureInd); // activate the texture unit first before binding texture
        bind();
        return this;
    }
    
    public void delete(){
        glDeleteTextures(TEX);
    }
    
    public IntLineTexture bind(){
        bind(this);
        return this;
    }
    public static void bind(IntLineTexture va){
        glBindTexture(GL_TEXTURE_1D, va.TEX);  
    }
}
