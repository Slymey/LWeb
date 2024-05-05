package LWeb.Engine.Instr;

import static LWeb.Common.Common.lognm;
import LWeb.Engine.Instr.RootP.*;
import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.function.Supplier;


public class Root {
    public static Runnable getInst(byte o[], Counter i, Core c){
        byte inst = o[i.inc()];
//        System.out.println(lognm()+""+inst+" "+i.c);
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i, c),     //0
                ()->Buffer.getInst(o, i, c),      //1
                ()->Paint.getInst(o, i, c),      //2
                ()->Stack.getInst(o, i, c),      //3
                ()->Header.getInst(o, i, c),      //4
                ()->ResourceInst.getInst(o, i, c),      //5
                ()->Filter.getInst(o, i, c),      //6
                ()->StackFull.getInst(o, i, c),      //7
                ()->Wait.getInst(o, i, c),      //8
                ()->OutToFile.getInst(o, i, c),      //9
                ()->OutToScreen.getInst(o, i, c),      //10
                ()->BranchIf.getInst(o, i, c),      //11
                ()->BranchIf.getInst(o, i, c),      //12
                ()->BranchIf.getInst(o, i, c),      //13
                ()->BranchIf.getInst(o, i, c),      //14
                ()->BranchIf.getInst(o, i, c),      //15
                ()->NamedApi.getInst(o, i, c),      //16
                ()->End.getInst(o, i, c),      //17
                ()->BranchLoop.getInst(o, i, c),      //18
                ()->BufferPtr.getInst(o, i, c),      //19
                ()->ClearBuffer.getInst(o, i, c),      //20
                ()->ClearBufferColor.getInst(o, i, c),      //21
            }, inst);
        
        
    }
}
