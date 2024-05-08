package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.istb;
import static LWeb.Common.Common.itb;
import static LWeb.Common.Common.sg;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.HeaderP.Screen;
import LWeb.Engine.Instr.RootP.HeaderP.Window;
import LWeb.Engine.Instr.RootP.HeaderP.Windowf;
import LWeb.Engine.Instr.RootP.ResourceInst;
import java.util.function.Supplier;

public abstract class Loop {
    boolean isSDelta=false;
    boolean isEDelta=false;
    boolean invr=false;
    int startPtr=0;
    int endPtr=0;
    Core c;
    private Loop(Core co, boolean sd, boolean ed, boolean inv, int s, int e){
        isSDelta=sd;
        isEDelta=ed;
        startPtr=s;
        invr=inv;
        endPtr=e;
        c=co;
    }
    public abstract int proccessLoop();
    public static Object getRsc(ByteCounter i, Core c){
        byte isDelta = i.next();
        int startPtr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int endPtr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        byte subLoop = i.next();
        boolean sd=(isDelta&0x1)==1;
        boolean ed=(isDelta>>>1&0x1)==1;
        boolean inv=(isDelta>>>2&0x1)==1;
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->BoundedLoop.getRsc(i, c, sd, ed, inv, startPtr, endPtr),      //1
                ()->ConditionalLoop.getRsc(i, c, sd, ed, inv, startPtr, endPtr),     //2
            }, subLoop);
    }

    public static class BoundedLoop extends Loop{
        public static ResourceInst.RByteCol getBytes(boolean isStartRelative, boolean isEndRelative, boolean condIsInverted, int startPointer, int endPointer,
                float startNumber, float endNumber, float stride){
            int fl = (isStartRelative?1:0)|(isEndRelative?1:0)<<1|(condIsInverted?1:0)<<2;
            return new ResourceInst.RByteCol(8,   ib(fl), itb(startPointer), itb(endPointer), ib(0),ftb(startNumber), ftb(endNumber), ftb(stride));
        }
        public static Object getRsc(ByteCounter i, Core c, boolean sb, boolean eb, boolean inv, int startPtr, int endPtr){
            float start = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float end = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float amount = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            return new BoundedLoop(c, sb, eb, inv, startPtr, endPtr, start, end, amount);
        }
        
        float start;
        float end;
        float amount;
        float current;
        public BoundedLoop(Core c, boolean sb, boolean eb, boolean inv, int startPtr, int endPtr, float start, float end, float amount){
            super(c, sb, eb, inv, startPtr, endPtr);
            this.start=start;
            this.end=end;
            this.amount=amount;
            current=start;
        }
        public int proccessLoop(){
            current+=amount;
            return (amount<end^invr)?((isSDelta?c.progCounter:0)+startPtr):((isEDelta?c.progCounter:0)+endPtr);
        }
    }

    public static class ConditionalLoop extends Loop{
        public static ResourceInst.RByteCol getBytes(boolean isStartRelative, boolean isEndRelative, boolean condIsInverted, int startPointer, int endPointer, int Rcondition){
            int fl = (isStartRelative?1:0)|(isEndRelative?1:0)<<1|(condIsInverted?1:0)<<2;
            return new ResourceInst.RByteCol(8,   ib(fl), itb(startPointer), itb(endPointer), ib(1),itb(Rcondition));
        }
        
        public static Object getRsc(ByteCounter i, Core c, boolean sb, boolean eb, boolean inv, int startPtr, int endPtr){
            int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            return new ConditionalLoop(c, sb, eb, inv, startPtr, endPtr, c.getResource(cond, Condition.class));
        }
        
        
        Condition cond;
        public ConditionalLoop(Core c, boolean sb, boolean eb, boolean inv, int startPtr, int endPtr, Condition cond){
            super(c, sb, eb, inv, startPtr, endPtr);
            this.cond=cond;
        }

        @Override
        public int proccessLoop(){
            return (cond.evaluate()^invr)?((isSDelta?c.progCounter:0)+startPtr):((isEDelta?c.progCounter:0)+endPtr);
        }
    }
    
    
}
