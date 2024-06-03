package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.*;
import LWeb.Common.Triple;
import static LWeb.Common.Triple.Triple;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import static org.lwjgl.opengl.GL43.*;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memSlice;


public class Common {
    public enum BufferUsage{ 
        STREAM_DRAW(GL_STREAM_DRAW),
        STREAM_READ(GL_STREAM_READ),
        STREAM_COPY(GL_STREAM_COPY),
        STATIC_DRAW(GL_STATIC_DRAW),
        STATIC_READ(GL_STATIC_READ),
        STATIC_COPY(GL_STATIC_COPY),
        DYNAMIC_DRAW(GL_DYNAMIC_DRAW),
        DYNAMIC_READ(GL_DYNAMIC_READ),
        DYNAMIC_COPY(GL_DYNAMIC_COPY);
        public final int gl_code;BufferUsage(int code){gl_code=code;}
    }
    
    public enum DataType{
        BYTE(GL_BYTE, 1),
        UNSIGNED_BYTE(GL_UNSIGNED_BYTE, 1),
        SHORT(GL_SHORT,2 ),
        UNSIGNED_SHORT(GL_UNSIGNED_SHORT, 2),
        INT(GL_INT, 4),
        UNSIGNED_INT(GL_UNSIGNED_INT, 4),
        HALF_FLOAT(GL_HALF_FLOAT, 2),
        FLOAT(GL_FLOAT, 4),
        DOUBLE(GL_DOUBLE, 8),
        UNSIGNED_INT_2_10_10_10_REV(GL_UNSIGNED_INT_2_10_10_10_REV, 4),
        INT_2_10_10_10_REV(GL_INT_2_10_10_10_REV, 4),
        FIXED(GL_FIXED, 4);
        public final int gl_code;public final int b_size;DataType(int code, int size){gl_code=code;b_size=size;}
        public static DataType byFirstPar(int v){
            int ind = inList(v, values(), (DataType dt)->{
                return dt.gl_code;
            });
            return ind==-1?null:values()[ind];
        }
    }
    
    public static boolean checkError(int target, Function<Integer,String> type){
        String error = type.apply(target);
        if(!"".equals(error)){System.out.println(lognm(2)+""+error);return true;}
        return false;
    }
    public static int glCheckError(){
        return glCheckError(0);
    }
    public static int glCheckError(int d){
        int errorCode;
        while ((errorCode = glGetError()) != GL_NO_ERROR){
            String error="";
            switch (errorCode){
                case GL_INVALID_ENUM:                  error = "INVALID_ENUM"; break;
                case GL_INVALID_VALUE:                 error = "INVALID_VALUE"; break;
                case GL_INVALID_OPERATION:             error = "INVALID_OPERATION"; break;
                case GL_STACK_OVERFLOW:                error = "STACK_OVERFLOW"; break;
                case GL_STACK_UNDERFLOW:               error = "STACK_UNDERFLOW"; break;
                case GL_OUT_OF_MEMORY:                 error = "OUT_OF_MEMORY"; break;
                case GL_INVALID_FRAMEBUFFER_OPERATION: error = "INVALID_FRAMEBUFFER_OPERATION"; break;
                case GL_FRAMEBUFFER_COMPLETE: error = "FRAMEBUFFER_COMPLETE";break;                      
                case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT: error = "FRAMEBUFFER_INCOMPLETE_ATTACHMENT";break;         
                case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: error = "FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT";break; 
                case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER: error = "FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER";break;        
                case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER: error = "FRAMEBUFFER_INCOMPLETE_READ_BUFFER";break;        
                case GL_FRAMEBUFFER_UNSUPPORTED: error = "FRAMEBUFFER_UNSUPPORTED";break;                   
                case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE: error = "FRAMEBUFFER_INCOMPLETE_MULTISAMPLE";break;        
                case GL_FRAMEBUFFER_UNDEFINED: error = "FRAMEBUFFER_UNDEFINED";break;                    
            }
            System.out.println(lognm(2+d)+ error);
        }
        return errorCode;
    }
    
    public static void glDebugOutput(int source, int type, int id, int severity, int length, long message, long userParam){
        // ignore non-significant error/warning codes
        if(id == 131169 || id == 131185 || id == 131218 || id == 131204) return; 
        String msg = GLDebugMessageCallback.getMessage(length, message);

        System.out.println("---------------");
        System.out.println("Debug message (" + id + "): " +  msg);
        String s="";
        switch (source){
            case GL_DEBUG_SOURCE_API:             s="Source: API"; break;
            case GL_DEBUG_SOURCE_WINDOW_SYSTEM:   s="Source: Window System"; break;
            case GL_DEBUG_SOURCE_SHADER_COMPILER: s="Source: Shader Compiler"; break;
            case GL_DEBUG_SOURCE_THIRD_PARTY:     s="Source: Third Party"; break;
            case GL_DEBUG_SOURCE_APPLICATION:     s="Source: Application"; break;
            case GL_DEBUG_SOURCE_OTHER:           s="Source: Other"; break;
        } 
            System.out.println(s);
        switch (type){
            case GL_DEBUG_TYPE_ERROR:               s="Type: Error"; break;
            case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR: s="Type: Deprecated Behaviour"; break;
            case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:  s="Type: Undefined Behaviour"; break; 
            case GL_DEBUG_TYPE_PORTABILITY:         s="Type: Portability"; break;
            case GL_DEBUG_TYPE_PERFORMANCE:         s="Type: Performance"; break;
            case GL_DEBUG_TYPE_MARKER:              s="Type: Marker"; break;
            case GL_DEBUG_TYPE_PUSH_GROUP:          s="Type: Push Group"; break;
            case GL_DEBUG_TYPE_POP_GROUP:           s="Type: Pop Group"; break;
            case GL_DEBUG_TYPE_OTHER:               s="Type: Other"; break;
        }
            System.out.println(s);

        switch (severity){
            case GL_DEBUG_SEVERITY_HIGH:         s="Severity: high"; break;
            case GL_DEBUG_SEVERITY_MEDIUM:       s="Severity: medium"; break;
            case GL_DEBUG_SEVERITY_LOW:          s="Severity: low"; break;
            case GL_DEBUG_SEVERITY_NOTIFICATION: s="Severity: notification"; break;
        } 
        System.out.println(s);
    }
    
    
    public static ByteBuffer imageToBuffer(BufferedImage image, int [] mix) {
        return imageToBuffer(image, mix, false, false);
    }
    
    public static ByteBuffer imageToBuffer(BufferedImage image, int [] mix, boolean flHorz, boolean flVert) {
        int w = image.getWidth();
        int h = image.getHeight();
        int[] pixels = image.getRGB(0, 0, w, h, null, 0, image.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length * 4);
        for (int i = flVert?w*h-w:0; (!flVert&&i < w*h)||(flVert&&i >= 0); i+=flVert?-w:w) {
            for (int j = flHorz?w-1:0; (!flHorz&&j < w)||(flHorz&&j >= 0); j+=flHorz?-1:1) {
                int pixel = pixels[i+j];
                buffer.put((byte) ((pixel >> mix[0]*8) & 0xFF));
                buffer.put((byte) ((pixel >> mix[1]*8) & 0xFF));
                buffer.put((byte) ((pixel >> mix[2]*8) & 0xFF));
                buffer.put((byte) ((pixel >> mix[3]*8) & 0xFF));
            }
        }
//        int i=0;
//        for (int pixel : pixels) {
//                System.out.println(lognm()+""+i+++" ");
//            buffer.put((byte) ((pixel >> mix[0]*8) & 0xFF));
//            buffer.put((byte) ((pixel >> mix[1]*8) & 0xFF));
//            buffer.put((byte) ((pixel >> mix[2]*8) & 0xFF));
//            buffer.put((byte) ((pixel >> mix[3]*8) & 0xFF));
//        }
        buffer.flip();
        return buffer;
    }
    
    
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = resource.startsWith("http") ? null : Paths.get(resource);
        if (path != null && Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                InputStream source = resource.startsWith("http")
                    ? new URL(resource).openStream()
                    : Common.class.getClassLoader().getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return memSlice(buffer);
    }
    
    public static void glfwInvoke(
        long window,
         GLFWWindowSizeCallbackI windowSizeCB,
         GLFWFramebufferSizeCallbackI framebufferSizeCB
    ) {
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            if (windowSizeCB != null) {
                glfwGetWindowSize(window, w, h);
                windowSizeCB.invoke(window, w.get(0), h.get(0));
            }

            if (framebufferSizeCB != null) {
                glfwGetFramebufferSize(window, w, h);
                framebufferSizeCB.invoke(window, w.get(0), h.get(0));
            }
        }

    }
    
}
