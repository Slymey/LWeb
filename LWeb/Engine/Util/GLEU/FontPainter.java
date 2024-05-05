package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.sizeof;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Pair;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.util.LinkedHashMap;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL13.*;


public class FontPainter {
    public static Pair<Integer,Integer> viewBox=Pair(0, 0);
    public static Vector4f defaultColor=new Vector4f(0,0,0,1);
    public static Vector4f voidColor=new Vector4f(0,0,0,0);
    private static Shader fontShader = new Shader()
            .addVertShader("font.vert")
            .addFragShader("font.frag")
            .initialize();
    private static VertexArray fontBox = VertexArray.basicQuad();
    private static LinkedHashMap<Pair<String, Font>, RenderedString> cache = new LinkedHashMap<>();
    private static FontRenderContext deafultFrc = new FontRenderContext(new AffineTransform(1,0,0,1,0,0), true, true);
    
    private Font font;
    private FontRenderContext frc = deafultFrc;
    private Vector4f c = defaultColor;
    private RenderedString curent;
    public FontPainter(String font, int style, int size){
        this.font = new Font(font, style ,size); 
    }
    public FontPainter(String font, int style, int size, Vector4f c){
        this.c=c;
        this.font = new Font(font, style ,size); 
    }
    public FontPainter(Font font){
        this.font = font;
    }
    
    public FontPainter modifyFont(int style, int size){
        font = font.deriveFont(style, size);
        return this;
    }
    public FontPainter modifyFont(Vector4f c){
        this.c=c;
        return this;
    }
    public FontPainter modifyFont(int style, int size, Vector4f c){
        font = font.deriveFont(style, size);
        this.c=c;
        return this;
    }
    
    public FontPainter initializeRenderer(boolean antialiasing, boolean fracMetrics){
        AffineTransform at = new AffineTransform(1,0,0,1,0,0);
        frc = new FontRenderContext(at, antialiasing, fracMetrics);
        return this;
    }
    
    public FontPainter prepareText(RenderedString rstr){
        curent=rstr;
        return this;
    }
    public FontPainter prepareText(String text){
        curent = getTexture(text, font, frc);
        return this;
    }
    
    public RenderedString getString(){
        return curent;
    }
    public RenderedString getString(String text, Font font){
        return getTexture(text, font, frc);
    }
    
    public FontPainter draw(int x, int y, Vector4f c){
        return draw(curent, x, y, c);
    }
    public FontPainter draw(int x, int y){
        return draw(curent, x, y, c);
    }
    public FontPainter draw(String text,  int x, int y, Vector4f c){
        return prepareText(text).draw(curent, x, y, c);
    }
    public FontPainter draw(String text, int x, int y){
        return prepareText(text).draw(curent, x, y, c);
    }
    public FontPainter draw(RenderedString rstr, int x, int y){
        return draw(rstr, x, y, c);
    }
    public FontPainter draw(RenderedString rstr, int x, int y, Vector4f c){
        return draw(rstr, (x*1.0f/viewBox.first)*2-1, (y*1.0f/viewBox.second)*2-1, c);
    }
    public FontPainter draw(float x, float y, Vector4f c){
        return draw(curent, x, y, c);
    }
    public FontPainter draw(float x, float y){
        return draw(curent, x, y, c);
    }
    public FontPainter draw(String text,  float x, float y, Vector4f c){
        return prepareText(text).draw(curent, x, y, c);
    }
    public FontPainter draw(String text, float x, float y){
        return prepareText(text).draw(curent, x, y, c);
    }
    public FontPainter draw(RenderedString rstr, float x, float y){
        return draw(rstr, x, y, c);
    }
    public FontPainter draw(RenderedString rstr, float x, float y,  Vector4f c){
        if(rstr==null)return this;
        return draw(rstr, x, y, (float)rstr.box.getWidth()/viewBox.first, (float)rstr.box.getHeight()/viewBox.second, c);
    }
    public FontPainter draw(RenderedString rstr, float x, float y, float w, float h, Vector4f c){
        if(rstr==null)return this;
        
        fontShader.use();
        fontShader.setUniformI("text", 0);
        fontShader.setUniformF("drawBox", x, y, w, h);
        fontShader.setUniformF("aColor", c.x, c.y, c.z, c.w);
        fontBox.bind();
        
        rstr.tex.activateOn(0);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0*sizeof(int.class));

        return this;
    }
    
    
    
    private static RenderedString getTexture(String text, Font font, FontRenderContext frc){
        Pair<String, Font> unitId = Pair(text, font);
        RenderedString gt = cache.get(unitId);
        if(gt==null){
            gt = generateTexture(text, font, frc);
            cache.put(unitId, gt);
        }
        return gt;
    }
    
    private static RenderedString generateTexture(String text, Font font, FontRenderContext frc){
        Pair<BufferedImage, Rectangle2D> fsb;
        Texture fontTex = new Texture()
                .setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)	
                .setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)
                .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)	
                .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)	
                .loadImage((fsb = displayText(text, font, frc)).first, false,true, true);
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, new float[]{0.0f, 0.0f, 0.0f, 0.0f});  
        return new RenderedString(fontTex, fsb.second);
    }
    
    private static Pair<BufferedImage, Rectangle2D> displayText( String text, Font font, FontRenderContext frc){
        Rectangle2D bd = font.getStringBounds(text, frc);
        BufferedImage image=new BufferedImage((int)bd.getWidth()+2, (int)bd.getHeight()+2, TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setFont(font);
        g.drawString(text, 1, (int)bd.getMaxY()+(int)(bd.getHeight()+2)/2);
        g.dispose();
        return Pair(image, bd);
    }
    public String toString(){
        return ""+font;
    }
    
    public static class RenderedString{
        public final Texture tex;
        public final Rectangle2D box;
        public RenderedString(Texture tex, Rectangle2D box){
            this.tex=tex;
            this.box=box;
        }
    }
}
