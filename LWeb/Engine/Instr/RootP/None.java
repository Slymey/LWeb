package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;


public class None {
    public static byte[] getBytes(){
        return ib(0);
    }
    
    public static Runnable getInst(ByteCounter i, Core c){
        return ()->{
            System.out.println(lognm()+"no-op");
        };
    }

    private static byte[] ib(int i){
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
