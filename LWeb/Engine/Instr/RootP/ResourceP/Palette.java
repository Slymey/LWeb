package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.*;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import java.util.AbstractMap;
import java.util.Arrays;

public class Palette {
    public static ResourceInst.RByteCol getBytes(int... colors){//add more
        int np=colors.length>>>1;
        byte[] cpa = istb(Arrays.copyOfRange(colors, 0, np*2));
        return new ResourceInst.RByteCol(3,  itb(np), cpa);
    }
    public static Object getRsc(ByteCounter i, Core c){
        
        int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        Pair<Integer, Integer> pairo[]= new Pair[len];
        for(int j=0;j<len;j++){
            pairo[j]= Pair(
                    byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()}),
                    byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()}));
        }
        Arrays.sort(pairo, (Pair<Integer,Integer> s1, Pair<Integer,Integer> s2)->{
            return s1.getFirst()-s2.getFirst();
        });
        return pairo;
    }
}
