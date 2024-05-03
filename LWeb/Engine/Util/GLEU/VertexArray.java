package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.sizeof;
import LWeb.Common.TriConsumer;
import LWeb.Engine.Util.GLEU.Common.BufferUsage;
import LWeb.Engine.Util.GLEU.Common.DataType;
import static LWeb.Engine.Util.GLEU.Common.DataType.FLOAT;
import java.nio.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.*;


public class VertexArray {
    private static VertexArray bound=null;
    
    public final int VAO;
    private int attrScanSize=0;
    private int currOffset=0;
    private int vertexCount=0;
    private int vertexOffset=0;
    public VertexArray(){
        VAO = glGenVertexArrays();
    }
    
    //<editor-fold defaultstate="collapsed" desc="adding ArrayBuffers with data">
    public VertexArray addArrayBuffer(ByteBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(DoubleBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(FloatBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(IntBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(LongBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(ShortBuffer data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(double[] data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(float[] data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(int[] data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(long[] data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addArrayBuffer(short[] data, BufferUsage usage){
        bind();
        int AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="adding EllementArrayBuffers with data">
    public VertexArray addEllementArrayBuffer(ByteBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(DoubleBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(FloatBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(IntBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(LongBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(ShortBuffer data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(double[] data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(float[] data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(int[] data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(long[] data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    public VertexArray addEllementArrayBuffer(short[] data, BufferUsage usage){
        bind();
        int EAB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EAB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage.gl_code);
        return this;
    }
    //</editor-fold>

    public VertexArray setVertecesToRender(int count){
        vertexCount=count;
        return this;
    }
    public VertexArray setVertecesToRenderOffset(int amount){
        vertexOffset=amount;
        return this;
    }
    
    public VertexArray draw(){
        bind();
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, vertexOffset*sizeof(int.class));
        return this;
    }
    public VertexArray draw(int count){
        bind();
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, vertexOffset*sizeof(int.class));
        return this;
    }
    public VertexArray draw(int count, int offset){
        bind();
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, offset*sizeof(int.class));
        return this;
    }
    
    
    public<T>  VertexArray setGeneric1(Consumer<T> func, T parm1){
        func.accept(parm1);
        return this;
    }
    public<T, U>  VertexArray setGeneric2(BiConsumer<T,U> func, T parm1, U parm2){
        func.accept(parm1, parm2);
        return this;
    }
    public<T, U, P>  VertexArray setGeneric3(TriConsumer<T,U,P> func, T parm1, U parm2, P parm3){
        func.accept(parm1, parm2, parm3);
        return this;
    }
    
    public VertexArray enable(int parm){
        glEnable(parm);
        return this;
    }
    public VertexArray disable(int parm){
        glDisable(parm);
        return this;
    }
    
    public VertexArray setScanSize(int sizeInBytes){
        attrScanSize=sizeInBytes;
        return this;
    }
    
    public VertexArray setVertexAtribute(int pos, int amount){
        return setVertexAtribute(pos, amount, FLOAT);
    }
    public VertexArray setVertexAtribute(int pos, int amount, DataType type){
        return setVertexAtribute(pos, amount, type,currOffset);
    }
    public VertexArray setVertexAtribute(int pos, int amount, DataType type, int offset){
        return setVertexAtribute(pos, amount, type, false, offset);
    }
    public VertexArray setVertexAtribute(int pos, int amount, DataType type, boolean normalized){
        return setVertexAtribute(pos, amount, type, normalized, currOffset);
    }
    public VertexArray setVertexAtribute(int pos, int amount, DataType type, boolean normalized, int offset){
        return setVertexAtribute(pos, amount, type, normalized, attrScanSize, offset);
    }
    public VertexArray setVertexAtribute(int pos, int amount, DataType type, boolean normalized, int scansize,  int offset){
        bind();
        currOffset+=amount*type.b_size;
        glVertexAttribPointer(pos, amount, type.gl_code, normalized, scansize, offset);
        glEnableVertexAttribArray(pos); 
        return this;
    }
    public VertexArray finnish(){
        bound=null;
        glBindVertexArray(0);
        return this;
    }
    
    
    public static VertexArray basicQuad(){
        return dinamicQuad(-1, -1, 2, 2);
    }
    public static VertexArray dinamicQuad(float x, float y, float w, float h){
        return new VertexArray()
            .addArrayBuffer(new float[]{
                    // positions     // texture coords
                    x+w,  y+h,       1.0f, 1.0f,   // top right
                    x+w,  y,         1.0f, 0.0f,   // bottom right
                    x,    y,         0.0f, 0.0f,   // bottom left
                    x,    y+h,       0.0f, 1.0f    // top left 
               }, Common.BufferUsage.STATIC_DRAW)
            .addEllementArrayBuffer(new int[]{ 
                    0, 1, 3, 
                    1, 2, 3
                } , Common.BufferUsage.STATIC_DRAW)
            .setScanSize(4*sizeof(float.class))
            .setVertexAtribute(0, 2)
            .setVertexAtribute(1, 2)
            .setVertecesToRender(6)
            .enable(GL_BLEND)
            .setGeneric2(GL11::glBlendFunc, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            .finnish();
    }
    
    
    
    
    public void delete(){
        glDeleteVertexArrays(VAO);
    }
    public VertexArray bind(){
        bind(this);
        return this;
    }
    public static void bind(VertexArray va){
        glBindVertexArray(va.VAO);
    }
            
            
}
