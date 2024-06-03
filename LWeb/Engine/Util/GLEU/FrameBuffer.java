package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.lognm;
import static LWeb.Engine.Util.GLEU.Common.glCheckError;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.*;


public class FrameBuffer {
    private static FrameBuffer bound=null;
    public final static Shader defaultShader = new Shader()
            .addVertShader("basic.vert")
            .addFragShader("basic.frag")
            .initialize();
    private static Vector4f deafultcc = new Vector4f(1,1,1,1);
    public static final FrameBuffer deafult = new FrameBuffer(0);
    public final static VertexArray deafaultLayout = VertexArray.basicQuad();
    
    public final int FBO;
    public VertexArray preferedLayout = null;
    private Vector4f cc = deafultcc;
    public Shader shader=null;
    private Texture tex;
    private int attrScanSize=0;
    private int currOffset=0;
    public FrameBuffer(){
        FBO = glGenFramebuffers();
    }
    private FrameBuffer(int fbo){
        FBO = fbo;
    }
    
    public FrameBuffer attachShader(Shader sh){
        shader = sh;
        return this;
    }
    public FrameBuffer attachVertexArray(VertexArray va){
        preferedLayout = va;
        return this;
    }
    public FrameBuffer setClearColor(float r,float g,float b,float a){
        cc = new Vector4f(r, g, b, a);
        return this;
    }
    public FrameBuffer setClearColor(Vector4f color){
        cc = color;
        return this;
    }
    public FrameBuffer attachTargetTexture(int type, Texture tex){
        bind();
        this.tex=tex;
        glFramebufferTexture2D(GL_FRAMEBUFFER, type, GL_TEXTURE_2D, tex.TEX, 0);  
        glCheckError(0);
        return this;
    }
    public Texture getTex(){
        return tex;
    }
    
    public FrameBuffer draw(Texture tex){
        bind();
        preferedLayout.bind();
        if(shader!=null)shader.use();
        if(tex!=null)tex.bind();
        preferedLayout.draw();
        return this;
    }
    public FrameBuffer draw(Texture tex, Shader shader){
        bind();
        preferedLayout.bind();
        if(shader!=null)shader.use();
        if(tex!=null)tex.bind();
        preferedLayout.draw();
        return this;
    }
    public FrameBuffer draw(Texture tex, Shader shader, VertexArray layout){
        bind();
        layout.bind();
        if(shader!=null)shader.use();
        if(tex!=null)tex.bind();
        layout.draw();
        return this;
    }
    public FrameBuffer draw(Texture tex, VertexArray layout){
        bind();
        layout.bind();
        if(shader!=null)shader.use();
        if(tex!=null)tex.bind();
        layout.draw();
        return this;
    }
    
    public FrameBuffer useWithClear(){ 
        bind();
        if(shader!=null)shader.use();
        glClearColor(cc.x,cc.y,cc.z,cc.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        return this;
    }
    public FrameBuffer use(){ 
        bind();
        if(shader!=null)shader.use();
        return this;
    }
    public FrameBuffer finnish(FrameBuffer target, boolean clearTarget){
        return finnish(target, target.preferedLayout, clearTarget);
    }
    public FrameBuffer finnish(FrameBuffer target, VertexArray panel, boolean clearTarget){
        if(panel==null)return this;
        if(clearTarget){target.useWithClear();}else{target.use();}
        panel.bind();
        if(tex!=null)tex.bind();
        panel.draw();
        
        return this;
    }
    public FrameBuffer finnish(FrameBuffer target, VertexArray panel, Shader shader,  boolean clearTarget){
        if(panel==null)return this;
        if(clearTarget){target.useWithClear();}else{target.use();}
        shader.use();
        panel.bind();
        if(tex!=null)tex.bind();
        panel.draw();
        
        return this;
    }
    
    public void delete(){
        glDeleteFramebuffers(FBO);
    }
    public FrameBuffer bind(){
        bind(this);
        return this;
    }
    public static void bind(FrameBuffer fb){
        glBindFramebuffer(GL_FRAMEBUFFER,fb.FBO);
    }
}
