package LWeb.Common;


import static LWeb.Common.Common.*;
import LWeb.Engine.CoreLO;
//import Common.*;
//import org.openide.windows.IOColorPrint;
import static LWeb.Common.Color.Color;
import static LWeb.Common.FileOperations.loadAsString;
import static LWeb.Common.Pair.Pair;
import static LWeb.Common.Triple.Triple;
import LWeb.Compiler.Components.TypeProvider.*;
import static LWeb.Compiler.Parser.group;
import static LWeb.Compiler.Parser.tokenize;
import LWeb.Common.Range.Range.*;
import LWeb.Engine.Instr.RootP.Filter;
import LWeb.Engine.Instr.RootP.Header;
import LWeb.Engine.Instr.RootP.Paint;
import LWeb.Engine.Instr.RootP.ResourceP.Position.IntPos;
import static LWeb.Engine.Util.GLEU.Common.*;
import static LWeb.Engine.Util.GLEU.Shader.*;
import LWeb.Engine.Util.GLEU.*;
import LWeb.Engine.Util.TTFFontRenderer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.sessions.Session;
import static org.joml.Math.toRadians;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import static org.lwjgl.stb.STBTruetype.stbtt_GetFontVMetrics;
import static org.lwjgl.stb.STBTruetype.stbtt_InitFont;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FreeType;
import static org.lwjgl.util.freetype.FreeType.*;


public class test {
    public static void main(String args[]) throws Exception {
        
        //<editor-fold defaultstate="collapsed" desc="old">
        //String s="zuvbn<hh<ub/*nnh/*hfhf*/ghfiz*/niuonui>ubnim";
//        Triple<String,Integer,Integer> o=readTillTarget(s,0,">","<","/*","*/","|*","|+","|-");
//        sopl("\n"+(s.length()));
//        sopl(o);
//        sopl(o);
//        String s2="zuvbn<hh<u\"b/*nnh/*hfhf*/ghfiz*/n\\\"iuo\"nui>ubnim";
//        Object o2=readTillTarget(s2,0,"","","//","\n");
//        sopl((s2.length()));
//        sopl(o2);
//        String s3="ubnimbrA//BinmoomC\nDoinmom";
//        Object o3=readTillTarget(readTillTarget(s,0,"","","//","\n","","","\n").getKey(),0,"","","/*","*/");
//        sopl((s3.length()));
//        sopl(o3);
//
//        
//        Triple<String,Integer,?> ss1 = readTillTarget(s,0,"<","","","");
//        sopl(ss1);
//        String sr=ss1.getKey();
//        Triple<String,Integer,Integer> ss2 = readTillTarget(s,ss1.getValue(),">","<","","");
//        Range rangeA_Z = new Range('A','Z');
//        Range rangea_z = new Range('a','z');
//        Range range0_9 = new Range('0','9');
//        char ch='G';
//        sopl(rangeA_Z.equals(ch)||rangea_z.equals(ch)||range0_9.equals(ch));
//        for(Integer i:rangeA_Z){
//            char c = (char)(int)i;
//            sop(c);
//        }
//        
//        String s="-400hh";
//        sopl(countSpaces(s));
//        sopl(parseInt(s));
//        //sopl(Double.valueOf(s));
//        Scanner sc = new Scanner(s);
//        sopl(sc.nextDouble());// <-----
//        
//        String s ="ountbmims sirbn.rbs";
//        sopl(readWhileValidWithFirst(s,0));
//         readTillTarget(String source,int offset, IndexedPredicate<String> target, IndexedPredicate<String> restart, IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd)
//        char c[]={' '};
//        String o[] =splitWithCStrings("ubnirb;ibi'ub;i'nh;t\"nm;n\"om,;",';');
//        sopl(Arrays.toString(o));
//        if(true)return;
        int ftgzbhutvcrtvbznuuztvcrvtzbunzbtvcrtvzbuuzbtvcrtvzunbz=0;
        
        
//        String s="vvzuub(ni,gg)ibn,nonn(ib(kn,ff)),[tn,tn(no,kk)jj]";
//        char cso[]={'(','[','"','\''};
//        char csc[]={')',']','"','\''};
//        Stack<Character> sk= new Stack<>();
//        
//        String o2[] = splitWithComents(s,0,(String st,int ind)->{
//            if(st.charAt(ind)==',')
//                return Pair(true,1);
//            return Pair(false,0);
//        },(String st,int ind)->{
//            char cv = st.charAt(ind);
//            int b = inList(cv,cso);
//            sopl(cv);
//            if(b>=0)sk.push(cso[b]);
//            return Pair(b>=0,(b>=0)?1:0);
//        },(String st,int ind)->{
//            char cv = st.charAt(ind);
//            sopl(cv);
//            boolean b=false;
//            if(!sk.empty()){
//                b = csc[inList(sk.peek(),cso)]==cv;
//                if(b)sk.pop();
//            }
//            return Pair(b,(b)?1:0);
//        });
//        sopl(Arrays.toString(o2));
//        Collection c;
            ///
            //IOColorPrint.print(io, "Green text", Color.GREEN);
            //AbstractSessionLog.getLog().log(9, "ib") ;
        //throw new Error();
        //</editor-fold>
        
        Pair<Integer, Integer> viewBox= Pair(800, 600);
        
        GLFWErrorCallback.createPrint(System.err).set(); // Error handling
        
        //initialization
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);  
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);//mac-osx
        
        // Create a GLFW window and set context
        long window = glfwCreateWindow(viewBox.first, viewBox.second, "LearnOpenGL", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        glfwMakeContextCurrent(window);
        createCapabilities(); // Initialize OpenGL capabilities
        
        int flags[] = new int[1];
        glGetIntegerv(GL_CONTEXT_FLAGS, flags);
        if ((flags[0] & GL_CONTEXT_FLAG_DEBUG_BIT)!=0){
            glEnable(GL_DEBUG_OUTPUT);
            glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS); 
            glDebugMessageCallback(LWeb.Engine.Util.GLEU.Common::glDebugOutput, 0l);
            //glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DONT_CARE, 0, true);
        }
        

        FontPainter.viewBox=viewBox;
        glViewport(0, 0, viewBox.first, viewBox.second);
        glfwSetFramebufferSizeCallback(window, (long wd, int widt, int heigh)->{//for resizing window
            viewBox.first=widt;
            viewBox.second=heigh;
            FontPainter.viewBox=viewBox;
            glViewport(0, 0, widt, heigh);
        });  
        
        FrameBuffer screen = FrameBuffer.deafult
                .attachShader(FrameBuffer.defaultShader)
                .attachVertexArray(FrameBuffer.deafaultLayout)
                .setClearColor(1,1,1,1);
       
//        //<editor-fold defaultstate="collapsed" desc="ttf">
//        try (MemoryStack stack = MemoryStack.stackPush()) {
//            // Initialize FreeType library
//            PointerBuffer libBuffer = stack.mallocPointer(1);
//            int error = FreeType.FT_Init_FreeType(libBuffer);
//            checkFTerror(error);
//            if (error != 0) {
//                throw new RuntimeException("Failed to initialize FreeType");
//            }
//            long ftLibrary = libBuffer.get(0);
//
//            // Load a font into memory
//            String fontPath = "src/LWeb/Common/arial.ttf"; // Update with your font file path
//            ByteBuffer fontBuffer = ByteBuffer.wrap(Files.readAllBytes(Paths.get(fontPath)));
//
//            // Create a font face
//            PointerBuffer facePointer = stack.mallocPointer(1);
//            error = FreeType.FT_New_Face(ftLibrary, fontPath, 0, facePointer);
//            checkFTerror(error);
//            if (error != 0) {
//                throw new RuntimeException("Failed to create font face");
//            }
//
//            ByteBuffer faceBuffer = stack.malloc(1024);
//            FT_Face ftFace = new FT_Face( faceBuffer);
//
//            // Set pixel sizes for the font face
//            error = FreeType.FT_Set_Pixel_Sizes(ftFace, 0, 48); // Set height to 48 pixels
//            checkFTerror(error);
//            if (error != 0) {
//                throw new RuntimeException("Failed to set font size");
//            }
//
//            // Load a glyph for demonstration (e.g., 'A')
//            error = FreeType.FT_Load_Char(ftFace, 'A', FreeType.FT_LOAD_RENDER);
//            checkFTerror(error);
//            if (error != 0) {
//                throw new RuntimeException("Failed to load glyph for 'A'");
//            }
//
//            // Clean up resources when done
//            FreeType.FT_Done_Face(ftFace); // Release the face
//            FreeType.FT_Done_FreeType(ftLibrary); // Release the FreeType library
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//        //</editor-fold>
//        //
        
        //TTFFontRenderer tf = new TTFFontRenderer(new Font("src/LWeb/Common/arial.ttf", 0, 48));
        
        
        //end of initialization
        
        
        
//<editor-fold defaultstate="collapsed" desc="previous drawing">   
float vertices[] = {
     // positions          // colors           // texture coords
     0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
     0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
    -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
    -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left 
};
int indices[] = {  // note that we start from 0!
    0, 1, 3,   // first triangle
    1, 2, 3
};   



Texture texture = new Texture()
        .setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT)	
        .setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT)
        .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)	
        .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)	
        .loadImage("src/LWeb/Common/container.jpg",false, false);
Texture texture2 = new Texture()
        .setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT)	
        .setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT)
        .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)	
        .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)	
        .loadImage("src/LWeb/Common/awesomeface.png", true, false);



VertexArray VAO = new VertexArray()
        .addArrayBuffer(vertices, BufferUsage.STATIC_DRAW)
        .addEllementArrayBuffer(indices, BufferUsage.STATIC_DRAW)
        .setScanSize(8*sizeof(float.class))
        .setVertexAtribute(0, 3)
        .setVertexAtribute(1, 3)
        .setVertexAtribute(2, 2)
        .finnish();

FontPainter fp = new FontPainter("src/LWeb/Common/arial.ttf", 0 ,10)
        .initializeRenderer(true, true);


FrameBuffer main = new FrameBuffer()
        .attachShader( new Shader()
            .addVertShader("sh1.vert")
            .addFragShader("sh1.frag")
            .initialize()
            .use()
            .setUniformI("texture2", 1)
            .setUniformI("text", 2))
        .setClearColor(1,1,1,1)
        .attachTargetTexture(GL_COLOR_ATTACHMENT0, new Texture()
                    .setImage(new IntPos(viewBox.first, viewBox.second))
                    .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                    .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR));

Shader grad= new Shader()
                .addVertShader("color.vert")
                .addFragShader("box.frag")
                .addFragShader("colorc.frag")
                .initialize()
                .use()
                .setUniformF("red", 0.5f, 0.5f, 1, 0)
                .setUniformF("green", 0.5f, 0.5f, 1, 0.33333f)
                .setUniformF("blue", 0.5f, 0.5f, 1, 0.66666f)
                .setUniformF("alpha", 1, 0, 1, 0)
                .setUniformF("pos", 0.25f, 0.25f, 0.75f, 0.75f);
Shader grad2= new Shader()
                .addVertShader("color.vert")
                .addFragShader("square.frag")
                .addFragShader("colorv.frag")
                .initialize()
                .use()
                .setUniformF("offset", 0.5f, 0.5f, 0.5f, 0)
                .setUniformF("amp", 0.5f, 0.5f, 0.5f, 0)
                .setUniformF("freq", 1, 1, 1, 1)
                .setUniformF("phase", 0, 0.3333f, 0.6666f, 0)
                .setUniformF("pos", 0.25f, 0.25f, 0.75f, 0.75f);


//</editor-fold>




        while(!glfwWindowShouldClose(window)){
            
            processInput(window);//input
            
            //glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
main.useWithClear();

            
Matrix4f mx = new Matrix4f()
        .identity()
        .rotate((float)glfwGetTime(), new Vector3f(0.0f, 0.0f, 1.0f))
        .translate(0.5f, -0.5f, 0.0f)
        .scale(0.5f);
int transformLoc = glGetUniformLocation(main.shader.program, "transform");
glUniformMatrix4fv(transformLoc, false, mx.get(new float[16]));


VAO.bind();
texture.activateOn(0);
texture2.activateOn(1);
VAO.draw(6);


fp.draw(0, 0, Color("#138848").asVec());
fp.draw("ubn", 300, 44);
fp.draw("nllm", 100, 44);

grad.use()
    .setUniformF("red", 0.5f, 0.5f, 1, 0)
    .setUniformF("green", 0.5f, 0.5f, 1, 0.33333f)
    .setUniformF("blue", 0.5f, 0.5f, 1, 0.66666f)
    .setUniformF("alpha", -1, -1000, 0.5f, 0.25f)
    .setUniformF("pos", 0.25f, 0.25f, 0.75f, 0.75f);
grad2.use()
    .setUniformF("offset", 0.5f, 0.5f, 0.5f, 0.5f)
    .setUniformF("amp", 0.5f, 0.5f, 0.5f, 0)
    .setUniformF("freq", 1, 1, 1, 1)
    .setUniformF("phase", 0, 0.3333f, 0.6666f, 0)
    .setUniformF("pos", 0.25f, 0.25f, 0.75f, 0.75f);


FrameBuffer.deafaultLayout.bind();//.enable(GL_BLEND).setGeneric2(GL11::glBlendFunc, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
grad2.use();

FrameBuffer.deafaultLayout.draw();


main.finnish(screen, true);
            
            
            // check and call events and swap the buffers
            glfwSwapBuffers(window);
            glfwPollEvents();    
        }
        
        
        
        //cleanup
        VAO.delete();
        //shaderProgram.delete();
        glfwTerminate();

        
        //<editor-fold defaultstate="collapsed" desc="other">
        if(3<5)return;
        //Object o = new Object[]{Paint,Filter,Header};
//        //<editor-fold defaultstate="collapsed" desc="prev">
//        GLFWErrorCallback.createPrint(System.err).set();
//
//        if (!GLFW.glfwInit()) {
//            throw new IllegalStateException("Failed to initialize GLFW");
//        }
//
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
//        
//        // Create GLFW window and OpenGL context
//        //long window = GLFW.glfwCreateWindow(w, h, "OpenGL BufferedImage", MemoryUtil.NULL, MemoryUtil.NULL);
//        if (window == MemoryUtil.NULL) {
//            throw new RuntimeException("Failed to create GLFW window");
//        }
//
//        GLFW.glfwMakeContextCurrent(window);
//        GL.createCapabilities(); // Initialize LWJGL's OpenGL bindings
//
//        if ((GL43.glGetInteger(GL43.GL_CONTEXT_FLAGS) & GL43.GL_CONTEXT_FLAG_DEBUG_BIT)!=0) {
//            GL43.glEnable(GL43.GL_DEBUG_OUTPUT);
//            GL43.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS); // For synchronous debug output
//
//            GLDebugMessageCallback callback = GLDebugMessageCallback.create((source, type, id, severity, length, message, userParam) -> {
//                String msg = GLDebugMessageCallback.getMessage(length, message);
//                System.err.printf("OpenGL Debug Message: source=%d, type=%d, id=%d, severity=%d, message=%s%n", source, type, id, severity, msg);
//            });
//
//            GL43.glDebugMessageCallback(callback, MemoryUtil.NULL); // Set debug callback
//        }
//        
//        // Set up viewport to match window size
//        GL11.glViewport(0, 0, w, h);
//
//        // Clear the buffer before rendering
//        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // White background
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // Clear the screen
//
//        // Load BufferedImage and convert to ByteBuffer
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File("test_1.png")); // Correct file path
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (image == null) {
//            throw new RuntimeException("Failed to load image");
//        }
//
////        int width = image.getWidth();
////        int height = image.getHeight();
//        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
//
//        // Fill ByteBuffer with pixel data from BufferedImage
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int argb = image.getRGB(x, y);
//                buffer.put((byte) ((argb >> 16) & 0xFF)); // Red
//                buffer.put((byte) ((argb >> 8) & 0xFF));  // Green
//                buffer.put((byte) (argb & 0xFF));         // Blue
//                buffer.put((byte) ((argb >> 24) & 0xFF)); // Alpha
//            }
//        }
//
//        buffer.flip(); // Prepare buffer for reading
//
//        // Create and bind OpenGL texture
//        int textureId = GL11.glGenTextures();
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
//
//        // Set texture parameters
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
//
//        // Load the texture data into OpenGL
//        GL11.glTexImage2D(
//            GL11.GL_TEXTURE_2D,
//            0,
//            GL11.GL_RGBA,
//            width,
//            height,
//            0,
//            GL11.GL_RGBA,
//            GL11.GL_UNSIGNED_BYTE,
//            buffer
//        );
//
//        // Drawing the texture on a quad
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // Clear before drawing
//
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId); // Bind the texture
//
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glTexCoord2f(0, 0);
//        GL11.glVertex2f(-1, -1); // Lower-left
//        GL11.glTexCoord2f(1, 0);
//        GL11.glVertex2f(1, -1); // Lower-right
//        GL11.glTexCoord2f(1, 1);
//        GL11.glVertex2f(1, 1); // Upper-right
//        GL11.glTexCoord2f(0, 1);
//        GL11.glVertex2f(-1, 1); // Upper-left
//        GL11.glEnd();
//        
//        // Main rendering loop
//        while (!GLFW.glfwWindowShouldClose(window)) {
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // Clear each frame
//
//            // Draw the textured quad
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
//            GL11.glBegin(GL11.GL_QUADS);
//            GL11.glTexCoord2f(0, 0);
//            GL11.glVertex2f(-1, -1);
//            GL11.glTexCoord2f(1, 0);
//            GL11.glVertex2f(1, -1);
//            GL11.glTexCoord2f(1, 1);
//            GL11.glVertex2f(1, 1);
//            GL11.glTexCoord2f(0, 1);
//            GL11.glVertex2f(-1, 1);
//            GL11.glEnd();
//
//            GLFW.glfwSwapBuffers(window); // Swap buffers for display
//            GLFW.glfwPollEvents(); // Handle window events
//        }
//
//        // Cleanup resources
//        MemoryUtil.memFree(buffer);
//        GL11.glDeleteTextures(textureId);
//
//        // Close GLFW
//        GLFW.glfwDestroyWindow(window);
//        GLFW.glfwTerminate();
//        GLFWErrorCallback.createPrint(System.err).free();
//        
//        //</editor-fold>
//        
        
        
        
//        String s = "oun://izbn.bopm/obunm?nim=bn&bnj=kn)nljm";
//        URI u = new URI(s);
//        URI a = u.normalize();
//        System.out.println(u);
//        System.out.println(a);
//        PropScalar ps = new PropScalar(35);
//        System.out.println(lognm()+ps);
//        
//        String sa[][]={{"ubrvn","ibnjm"},{"izvbun","tuvzbn"},{"tuvbnj"}};
//        String s2[] =flatten(sa);
//        System.out.println(lognm()+""+s2);
//        int b=0;
//        long i=0;
//        while (i!=100000000l) {
//            //System.out.print(" "+i);
//            //terminator(pi.i);
//            i++;
//        }
//        System.out.println(lognm()+""+i);
        
           
//        ByteBuffer pixelBuffer = MemoryUtil.memAlloc(width * height * 4); // RGBA, 4 bytes per pixel
//
//        // Example: Set all pixels to red
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                pixelBuffer.put((byte) 0); // Red
//                pixelBuffer.put((byte) 255);   // Green
//                pixelBuffer.put((byte) 0);   // Blue
//                pixelBuffer.put((byte) 255); // Alpha
//            }
//        }
//
//        pixelBuffer.flip();

        //GL11.glDrawPixels(width, height, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);//works

        //<editor-fold defaultstate="collapsed" desc="old2">
/*
        sopl(parseDouble("\n45px"));
        TypeProvider tp1=new Length(0);
        TypeProvider tp2=new Scalar(0);
        if(inList(tp2.getClass(),TypeProvider.class.getDeclaredClasses())>=0){
            sopl("aaaaaaaaaa");
        }
        System.out.println(org.lwjgl.Version.getVersion());
        
        
        // use this to disolve the style strings an then apply it with style property list
        String s=" 0 45px calc(25px + 7px)g";
        s=reduceChars(s,' ');
        Scanner sc = new Scanner(s);
        int brcDepth[]={0};
        String arr[] = splitWithBrackets(s,0,(String st,int ind)->{
            if(brcDepth[0]==0&&st.charAt(ind)==' ')
                return Pair(true,1);
            return Pair(false,0);
        },(String st,int ind)->{
            char cv = st.charAt(ind);
            boolean b = cv=='(';
            if(b)brcDepth[0]++;
            return Pair(b,b?1:0);
        },(String st,int ind)->{
            char cv = st.charAt(ind);
            boolean b = cv==')';
            if(b)brcDepth[0]--;
            return Pair(b,b?1:0);
        });
        sopl(Arrays.toString(arr));
        HashMap<String, Double> scales = new HashMap<>();
        scales.put("px", 1.0);
        scales.put("vh", 10.8);
        TypeProvider rt[] =
                (TypeProvider[])Arrays
                .stream(arr)
                .map((String sr)->{
                    return TypeProvider.getType(sr,scales);
                })
                .toArray((int o)->{
                    return new TypeProvider[arr.length];
                });
        sopl(Arrays.toString(rt));
        */
        
        //return readTillTarget(sr,0,"(","","","");
        //sopl(parseDouble(s,rt.getValue()+1));
        //</editor-fold>
        
        //</editor-fold>
        
    }
    /*storage*/
//int vertexColorLocation = glGetUniformLocation(shaderProgram2, "ourColor");
//glUniform4f(vertexColorLocation, 0.0f, greenValue, 0.0f, 1.0f);
    
    
    public static void processInput(long window){
        
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
            glfwSetWindowShouldClose(window, true);
        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS)
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
//        glfwSetKeyCallback(window, (windw, key, scancode, action, mods) -> {
//            System.out.println(lognm()+""+key+" "+scancode+" "+action+" "+mods);
//        });
//        glfwSetMouseButtonCallback(window, (windw, button, action, mods) -> {
//            System.out.println(lognm()+""+button+" "+action+" "+mods);
//        });
//        glfwSetCursorPosCallback(window, (windw, xpos, ypos) -> {
//            System.out.println(lognm()+""+xpos+" "+ypos);
//        });
    }
    
    public static void checkFTerror(int errorCode) {
        if(errorCode==0)return;
        switch (errorCode) {
            case FreeType.FT_Err_Unknown_File_Format:
                System.out.println(lognm(1)+ "Unknown file format");return ;
            case FreeType.FT_Err_Cannot_Open_Resource:
                System.out.println(lognm(1)+ "Cannot open resource");return ;
            case FreeType.FT_Err_Invalid_Face_Handle:
                System.out.println(lognm(1)+ "Invalid face handle");return ;
            case FreeType.FT_Err_Invalid_File_Format:
                System.out.println(lognm(1)+ "Invalid file format");return ;
            case FreeType.FT_Err_Invalid_Version:
                System.out.println(lognm(1)+ "Invalid version");return ;
            case FreeType.FT_Err_Lower_Module_Version:
                System.out.println(lognm(1)+ "Lower module version");return ;
            case FreeType.FT_Err_Invalid_Argument:
                System.out.println(lognm(1)+ "Invalid argument");return ;
            case FreeType.FT_Err_Out_Of_Memory:
                System.out.println(lognm(1)+ "Out of memory");return ;
            case FreeType.FT_Err_Invalid_Glyph_Index:
                System.out.println(lognm(1)+ "Invalid glyph index");return ;
            default:
                System.out.println(lognm(1)+ "Unknown FreeType error (" + errorCode + ")");return ;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    //<editor-fold defaultstate="collapsed" desc="other">
    
    private static String formatInput(String input) {
        StringBuilder formatted = new StringBuilder();
        int indentLevel = 0;

        int inBrackets = 0;
        boolean isNewLine = true;

        for (char c : input.toCharArray()) {
            switch (c) {
                case '[':
                        indentLevel++;
                        inBrackets++;
                    if (inBrackets==0) {
                    }
                    formatted.append(c);
                    break;

                case ']':
                    formatted.append(c);
                    indentLevel--;
                    if (inBrackets!=0) {
                        inBrackets--;
                        formatted.append("\n");
                        isNewLine = true;
                    }
                    break;

                case ',':
                    formatted.append(c);
                    formatted.append("\n");
                    isNewLine = true;
                    break;

                default:
                    if (isNewLine) {
                        for (int i = 0; i < indentLevel; i++) {
                            formatted.append("  "); // 4 spaces for indentation
                        }
                        isNewLine = false;
                    }
                    formatted.append(c);
                    break;
            }
        }

        return formatted.toString();
    }
    
    
    
    
    
    
    public static String formatText(String input) {
        // To build the formatted string
        StringBuilder formattedText = new StringBuilder();
        // To keep track of the current indentation level
        int indentLevel = 0;
        // To control the indentation step (number of spaces)
        int indentStep = 2;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            switch (currentChar) {
                case '[':
                    // New line and indent after opening bracket
                    indentLevel++;
                    formattedText.append(currentChar);
                    formattedText.append("\n");
                    formattedText.append(repeat(" ", indentLevel * indentStep));
                    // Increase indent level
                    break;

                case ']':
                    // Decrease indent level and new line before closing bracket
                    indentLevel--;
                    formattedText.append(currentChar);
                    formattedText.append("\n");
                    formattedText.append(repeat(" ", (indentLevel-1) * indentStep));
                    break;

                default:
                    // Regular characters
                    formattedText.append(currentChar);
                    break;
            }
        }

        return formattedText.toString();
    }
    
    public static String repeat(String str, int times) {
        StringBuilder repeated = new StringBuilder();
        for (int i = 0; i < times; i++) {
            repeated.append(str);
        }
        return repeated.toString();
    }

    
    
    
    
    
    
    
    
    
    
    
    public static <T> T[] a(T... o){
        ArrayList<T> al =new ArrayList<>();
        if(o==null||o.length==0)return (T[])new Object[0];
        Class c = o[0].getClass();
        for(T ob:o){
            if(c.isInstance(ob)&&ob!=null){
                sopl(ob);
                al.add(ob);
            }else if(ob!=null&&ob.getClass().isArray()){
                T or[] = (T[]) ob;
                if(or.length>0&&c.isInstance(or[0])){
                    for(T oc:or){
                        if(oc!=null){
                            al.add(ob);
                        }
                    }
                }
            }
        }
        T[] t = (T[]) new Object[1]; 
        return al.toArray(t);
    }
    
    
    static void sop(String s){
        System.out.print(s);
    }
    static void sop(Object s){
        System.out.print(s);
    }
    static void sopl(String s){
        System.out.println(s);
    }
    static void sopl(Object s){
        System.out.println(s);
    }
    static void sopl(){
        System.out.println();
    }
    //</editor-fold>

}
