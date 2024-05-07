package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.*;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst.RByteCol;


public class None {
    public static RByteCol getBytes(){
        return new RByteCol(0, vpb());
    }
    public static Object getRsc(ByteCounter i, Core c){
        return null;
    }
}
