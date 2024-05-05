package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Engine.Core;
import java.util.AbstractMap;
import java.util.Arrays;

public class Palette {
    public static Object getRsc(byte[] o, Counter i, Core c){
        
        int len = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        Pair<Integer, Integer> pairo[]= new Pair[len];
        for(int j=0;j<len;j++){
            pairo[j]= Pair(
                    byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}),
                    byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]}));
        }
        Arrays.sort(pairo, (Pair<Integer,Integer> s1, Pair<Integer,Integer> s2)->{
            return s1.getFirst()-s2.getFirst();
        });
        return pairo;
    }
}
