package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import java.util.AbstractMap;
import java.util.Arrays;

public class Palette {
    public static Pair<Class, Object> getRsc(byte[] o, Counter i){
        
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
        Object resource = pairo;
        Class cls = pairo.getClass();
        return Pair(cls, resource);
    }
}
