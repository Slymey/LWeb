
package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.sg;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.vpb;
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
    public static void main(String[] args) {
        System.out.println(lognm()+"un");
    }
    public static class RByteCol{
        byte b[];
        int t;
        public RByteCol(int ty, byte []by){t=ty;b=by;}
        public RByteCol(int ty, byte[]... by){t=ty;b=flatten(by);}
        public byte[] at(int id){
            return flatten(ib(5), itb(t), itb(id), b);
        }
        public byte[] atOnce(int id){
            return flatten(ib(24), itb(t), itb(id), b);
        }
        public byte[] atCond(int id, int cond){
            return flatten(ib(25), itb(t), itb(id),itb(b.length), itb(cond), b);
        }
        public byte[] atNegCond(int id, int cond){
            return flatten(ib(26), itb(t), itb(id),itb(b.length), itb(cond), b);
        }
    }
    
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
            BlendMode::getRsc,       //20
            FlatFloatColor::getRsc       //21
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
