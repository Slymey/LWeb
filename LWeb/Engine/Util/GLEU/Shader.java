package LWeb.Engine.Util.GLEU;

import static LWeb.Common.Common.lognm;
import static LWeb.Common.FileOperations.loadAsString;
import static LWeb.Engine.Util.GLEU.Common.checkError;
import static LWeb.Engine.Util.GLEU.Common.glCheckError;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.*;


public class Shader {
    public final int program;
    private String basePath;
    private boolean finalized = false;
    private boolean terminated = false;
    
    public Shader(){
        program = glCreateProgram();
        basePath="src/LWeb/Engine/Shaders/";
    }
    public Shader(String path){
        program = glCreateProgram();
        if(path.charAt(path.length()-1)!='/')path+='/';
        basePath=path;
    }
    
    public Shader addVertShader(String... files){
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(finalized){System.out.println(lognm(1)+"Shader program already linked");return null;}
        for(String i:files){
            int shader = glCreateShader(GL_VERTEX_SHADER);
            String str = loadAsString(basePath+i);
            glShaderSource(shader, str);
            glCompileShader(shader);
            checkError(shader, GL20::glGetShaderInfoLog);
            glAttachShader(program, shader);
        }
        return this;
    }
    public Shader addFragShader(String... files){
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(finalized){System.out.println(lognm(1)+"Shader program already linked");return null;}
        for(String i:files){
            int shader = glCreateShader(GL_FRAGMENT_SHADER);
            String str = loadAsString(basePath+i);
            glShaderSource(shader, str);
            glCompileShader(shader);
            checkError(shader, GL20::glGetShaderInfoLog);
            glAttachShader(program, shader);
        }
        return this;
    }
    public Shader addVertShaderByPath(String... files){
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(finalized){System.out.println(lognm(1)+"Shader program already linked");return null;}
        for(String i:files){
            int shader = glCreateShader(GL_VERTEX_SHADER);
            String str = loadAsString(i);
            glShaderSource(shader, str);
            glCompileShader(shader);
            checkError(shader, GL20::glGetShaderInfoLog);
            glAttachShader(program, shader);
        }
        return this;
    }
    public Shader addFragShaderByPath(String... files){
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(finalized){System.out.println(lognm(1)+"Shader program already linked");return null;}
        for(String i:files){
            int shader = glCreateShader(GL_FRAGMENT_SHADER);
            String str = loadAsString(i);
            glShaderSource(shader, str);
            glCompileShader(shader);
            checkError(shader, GL20::glGetShaderInfoLog);
            glAttachShader(program, shader);
        }
        return this;
    }
    
    public Shader initialize(){
        glLinkProgram(program);
        checkError(program, GL20::glGetProgramInfoLog);
        finalized=true;
        glCheckError();
        return this;
    }
    public Shader use(){ 
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(!finalized){System.out.println(lognm(1)+"Shader program not initialized");return null;}
        glUseProgram(program);
        return this;
    } 
    
    public Shader setUniformF(String name, float... value){  
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(!finalized){System.out.println(lognm(1)+"Shader program not initialized");return null;}
        int loc = glGetUniformLocation(program, name);
        switch (value.length) {
            case 1:{
                glUniform1fv(loc, value); 
                break;
            }
            case 2:{
                glUniform2fv(loc, value); 
                break;
            }
            case 3:{
                glUniform3fv(loc, value); 
                break;
            }
            case 4:{
                glUniform4fv(loc, value); 
                break;
            }
            default:
                glUniform4fv(loc, value); 
        }
        return this;
    }
    public Shader setUniformI(String name, int... value){  
        if(terminated){System.out.println(lognm(1)+"Shader program deleted");return null;}
        if(!finalized){System.out.println(lognm(1)+"Shader program not initialized");return null;}
        int loc = glGetUniformLocation(program, name);
        switch (value.length) {
            case 1:{
                glUniform1iv(loc, value); 
                break;
            }
            case 2:{
                glUniform2iv(loc, value); 
                break;
            }
            case 3:{
                glUniform3iv(loc, value); 
                break;
            }
            case 4:{
                glUniform4iv(loc, value); 
                break;
            }
            default:
                glUniform4iv(loc, value); 
        }
        return this;
    }
    
    
    
    
    public static int loadShader(String target, int type){
        int shader = glCreateShader(type);
        String str = loadAsString(target);
        glShaderSource(shader, str);
        glCompileShader(shader);
        checkError(shader, GL20::glGetShaderInfoLog);
        return shader;
    }
    public static int getShaderProgram(int... targets){
        int shaderProgram = glCreateProgram();
        for(int i:targets){
            glAttachShader(shaderProgram, i);
        }
        glLinkProgram(shaderProgram);
        checkError(shaderProgram, GL20::glGetProgramInfoLog);
        return shaderProgram;
    }
    public static int makeShaderProgram(int... targets){
        int shaderProgram = glCreateProgram();
        for(int i:targets){
            glAttachShader(shaderProgram, i);
        }
        glLinkProgram(shaderProgram);
        for(int i:targets){
            glDeleteShader(i);
        }
        checkError(shaderProgram, GL20::glGetProgramInfoLog);
        return shaderProgram;
    }
    
    public void delete(){
        glDeleteProgram(program);
        terminated=true;
    }
}
