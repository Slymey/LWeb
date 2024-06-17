package LWeb.Engine.Instr;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.lognm;
import LWeb.Engine.Instr.RootP.*;
import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import java.util.function.Supplier;


public class Root {
    public static Runnable getInst(ByteCounter i, Core c){
        byte inst = i.next();
//        System.out.println(lognm()+""+inst+" "+i.c);
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(i, c),     //0
                ()->Buffer.getInst(i, c),      //1
                ()->Paint.getInst(i, c),      //2
                ()->Stack.getInst(i, c),      //3
                ()->Header.getInst(i, c),      //4
                ()->ResourceInst.Resource.getInst(i, c),      //5
                ()->Filter.getInst(i, c),      //6
                ()->StackViewBox.getInst(i, c),      //7
                ()->Wait.getInst(i, c),      //8
                ()->OutToFile.getInst(i, c),      //9
                ()->OutToScreen.getInst(i, c),      //10
                ()->BranchIf.getInst(i, c),      //11
                ()->BranchIfPtr.getInst(i, c),      //12
                ()->CallIf.getInst(i, c),      //13
                ()->CallIfPtr.getInst(i, c),      //14
                ()->CallCallableIf.getInst(i, c),      //15
                ()->NamedApi.getInst(i, c),      //16
                ()->End.getInst(i, c),      //17
                ()->BranchLoop.getInst(i, c),      //18
                ()->BufferPtr.getInst(i, c),      //19
                ()->ClearBuffer.getInst(i, c),      //20
                ()->ClearBufferColor.getInst(i, c),      //21
                ()->CondIntruction.getInst(i, c),      //22
                ()->NegCondIntruction.getInst(i, c),      //23
                ()->ResourceInst.OneTimeResource.getInst(i, c),      //24
                ()->ResourceInst.CondResource.getInst(i, c),      //25
                ()->ResourceInst.NegCondResource.getInst(i, c),      //26
                ()->CopyResource.getInst(i, c),      //27
                ()->StackBorder.getInst(i, c),      //28
            }, inst);
        
        
    }
}
