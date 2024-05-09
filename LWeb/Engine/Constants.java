package LWeb.Engine;

import LWeb.Engine.Util.GLEU.Shader;
import LWeb.Engine.Util.GLEU.VertexArray;


public class Constants {
    public static enum ConstTypes{LINEAR_GRADIENT_SHADER, BASIC_QUAD, BOX_SHADER, LINE, BEZIER3P}
    
    private Object consts[] = new Object[ConstTypes.values().length];
    
    
    
    public Object getConstant(ConstTypes ct){
        int ord = ct.ordinal();
        switch (ct) {
            case LINEAR_GRADIENT_SHADER:{
                if(consts[ord]!=null)return consts[ord];
                consts[ord] = new Shader()
                    .addVertShader("color.vert")
                    .addFragShader("colorv.frag")
                    .initialize()
                    .use();
                return consts[ord];
            }
            case BASIC_QUAD:{
                if(consts[ord]!=null)return consts[ord];
                consts[ord] = VertexArray.basicQuad();
                return consts[ord];
            }
            case BOX_SHADER:{
                if(consts[ord]!=null)return consts[ord];
                consts[ord] = new Shader()
                    .addVertShader("basic.vert")
                    .addFragShader("bounding_box.frag")
                    .initialize()
                    .use();
                return consts[ord];
            }case LINE:{
                if(consts[ord]!=null)return consts[ord];
                consts[ord] = new Shader()
                    .addVertShader("basic.vert")
                    .addFragShader("line.frag")
                    .initialize()
                    .use();
                return consts[ord];
            }case BEZIER3P:{
                if(consts[ord]!=null)return consts[ord];
                consts[ord] = new Shader()
                    .addVertShader("basic.vert")
                    .addFragShader("bezier.frag")
                    .initialize()
                    .use();
                return consts[ord];
            }
        }
        return null;
    }
    
    
}
