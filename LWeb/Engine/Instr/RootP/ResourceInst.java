
package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import LWeb.Common.Pair;
import LWeb.Common.TriFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.*;
import LWeb.Engine.Instr.RootP.ResourceP.None;
import LWeb.Engine.Instr.RootP.ResourceP.Number;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;


//.*>(.*)\.getRsc.*\/\/([0-9]*)\r\n
//$1::getRsc,       //$2\n
public class ResourceInst {
    static List<BiFunction<ByteCounter, Core, Object>> rList = Arrays.asList(
            None::getRsc,       //0
            PlainText::getRsc,       //1
            FontFace::getRsc,       //2
            Palette::getRsc,       //3
            URLAbsolute::getRsc,       //4
            URLRelative::getRsc,       //5
            FlatColor::getRsc,       //6
            Callable::getRsc,       //7
            Loop::getRsc,       //8
            Number::getRsc,       //9
            WholeNumber::getRsc,       //10
            Condition::getRsc,       //11
            ResourcePointer::getRsc,       //12
            ImageFile::getRsc,       //13
            ImageBuffer::getRsc,       //14
            LinearGradient::getRsc,       //15
            Position.FloatPos::getRsc,       //16
            Position.IntPos::getRsc,       //17
            Box.FloatBox::getRsc,       //18
            Box.IntBox::getRsc,       //19
            BlendMode::getRsc       //20
        );
    public static class Resource{
        public static Runnable getInst(ByteCounter i, Core c){
            int type = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int ind = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            Object rsc = rList.get(type).apply(i, c);
            c.putResource(ind, rsc);
            return ()->{
                c.putResource(ind, rsc);
            };
        }
    }
    public static class OneTimeResource{
        public static Runnable getInst(ByteCounter i, Core c){
            int type = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int ind = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            Object rsc = rList.get(type).apply(i, c);
            return ()->{
                if(c.getResource(ind, Object.class)==null)
                    c.putResource(ind, rsc);
            };
        }
    }
    public static class CondResource{
        public static Runnable getInst(ByteCounter i, Core c){
            int type = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int ind = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int cnd = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            ByteCounter i2 = new ByteCounter(i.o, i.c);
            i.c+=len;
            return  ()->{
                if(c.getResource(cnd, Condition.class).evaluate()){
                    i2.lock();
                    Object rsc = rList.get(type).apply(i2, c);
                    i2.unlock();
                    c.putResource(ind, rsc);
                }
            };
        }
    }
    public static class NegCondResource{
        public static Runnable getInst(ByteCounter i, Core c){
            int type = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int ind = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int cnd = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            ByteCounter i2 = new ByteCounter(i.o, i.c);
            i.c+=len;
            return ()->{
                if(!c.getResource(cnd, Condition.class).evaluate()){
                    Object rsc = rList.get(type).apply(i2, c);
                    c.putResource(ind, rsc);
                }
            };
        }
    }
    
}
