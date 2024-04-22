package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import static LWeb.Engine.Core.resources;
import LWeb.Engine.Util.SimpleRemoteThread;


public class Wait {
    public static Runnable getInst(byte[] o, Counter i){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            System.out.println(" "+id);
            SimpleRemoteThread srt=(SimpleRemoteThread)resources.get(id).get();
            srt.pause();
        };
    }
}
