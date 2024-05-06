package LWeb.Engine.Instr.RootP.HeaderP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.GLEU.FrameBuffer;


public class Screen {
    public static Runnable getInst(ByteCounter i, Core c){
        
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            FrameBuffer screen = FrameBuffer.deafult
                    .attachShader(FrameBuffer.defaultShader)
                    .attachVertexArray(FrameBuffer.deafaultLayout)
                    .setClearColor(1,1,1,1);
            c.putResource(id,  screen);//window resource
        };
    }
}
