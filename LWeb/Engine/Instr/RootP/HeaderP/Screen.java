package LWeb.Engine.Instr.RootP.HeaderP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Util.GLEU.FrameBuffer;


public class Screen {
    public static byte[] getBytes(int id){
        return flatten(ib(4,3), itb(id));
    }
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
