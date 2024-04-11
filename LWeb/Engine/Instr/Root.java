package Engine.Instr;

import Engine.Instr.RootP.*;
import static Common.Common.sg;
import Common.Counter;
import java.util.function.Supplier;


public class Root {
    public static Runnable getInst(byte o[], Counter i){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->End.getInst(o, i),     //0
                ()->Buffer.getInst(o, i),      //1
                ()->Paint.getInst(o, i),      //2
                ()->Stack.getInst(o, i),      //3
                ()->Header.getInst(o, i),      //4
                ()->ResourceInst.getInst(o, i),      //5
                ()->Filter.getInst(o, i),      //6
                ()->StackFull.getInst(o, i)      //7
            }, inst);
        
        
    }
}
