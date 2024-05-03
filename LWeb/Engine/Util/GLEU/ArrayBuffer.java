package LWeb.Engine.Util.GLEU;

import LWeb.Engine.Util.GLEU.Common.DataType;
import java.nio.*;
import static org.lwjgl.opengl.GL15.*;


public class ArrayBuffer {
    private static ArrayBuffer bound;
    private static int ID=GL_ARRAY_BUFFER;
    
    private DataType type;
    public final int AB;
    public ArrayBuffer(){
        AB = glGenBuffers(); 
        type=null;
    }
    
    //<editor-fold defaultstate="collapsed" desc="initialization with data and usage">
    public ArrayBuffer(ByteBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_BYTE;
    }
    public ArrayBuffer(DoubleBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.DOUBLE;
    }
    public ArrayBuffer(FloatBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.FLOAT;
    }
    public ArrayBuffer(IntBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_INT;
    }
    public ArrayBuffer(LongBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_INT_2_10_10_10_REV;
    }
    public ArrayBuffer(ShortBuffer data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_SHORT;
    }
    public ArrayBuffer(double[] data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.DOUBLE;
    }
    public ArrayBuffer(float[] data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.FLOAT;
    }
    public ArrayBuffer(int[] data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_INT;
    }
    public ArrayBuffer(long size, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, size, usage);
        type=null;
    }
    public ArrayBuffer(long[] data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_INT_2_10_10_10_REV;
    }
    public ArrayBuffer(short[] data, int usage){
        AB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, AB);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        type=DataType.UNSIGNED_SHORT;
    }
    //</editor-fold>

    public ArrayBuffer bind(){
        bind(this);
        return this;
    }
    public static void bind(ArrayBuffer va){
        glBindBuffer(GL_ARRAY_BUFFER,va.AB);
    }
}
