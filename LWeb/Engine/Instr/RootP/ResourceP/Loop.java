package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;

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
        Loop loop = null;
        boolean sd=(isDelta&0x1)==1;
        boolean ed=(isDelta>>>1&0x1)==1;
        boolean inv=(isDelta>>>2&0x1)==1;
        switch (subLoop) {
            case 0:{
                float start = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
                float end = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
                float amount = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
                return new BoundedLoop(c, sd, ed, inv, startPtr, endPtr, start, end, amount);
            }
            case 1:{
                int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                return new ConditionalLoop(c, sd, ed, inv, startPtr, endPtr, c.getResource(cond, Condition.class));
            }
        }
        return null;
    }

    private static class BoundedLoop extends Loop{
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

    private static class ConditionalLoop extends Loop{
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
