package LWeb.Engine;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sopl;
import LWeb.Common.Counter;
import LWeb.Common.DynArray.DynArray;
import LWeb.Engine.Instr.Root;
import LWeb.Engine.Instr.RootP.ResourceP.Resource;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;



public class Core {

    public static DynArray<Resource> resources = new DynArray<>();
    public static DynArray<BufferedImage> buffers = new DynArray<>();
    public static DynArray<Exception> errors = new DynArray<>();
    public static long newError=-1;
    
    public static Runnable[] byteToDraw(byte o[], Counter i) throws Exception{
        ArrayList<Runnable> rn=new ArrayList<Runnable>();        
        while(!i.ended){
            rn.add(Root.getInst(o, i));
            if(newError!=-1){
                System.out.println(errors.get((int)newError));
                newError=-1;
            }
        }
        return rn.toArray(new Runnable[rn.size()]);
    }

}
