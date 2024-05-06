package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.bytesToInt;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import java.util.Arrays;

public abstract class Condition {
    public abstract boolean evaluate();
    
    public static Object getRsc(ByteCounter i, Core c){
        byte subCond = i.next();
        Condition cond = null;
        switch (subCond) {
            case 1:{
                return new NegativeCondition(c, byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()}));
            }
            case 2:{
                return new CallbackCondition(c, byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()}));
            }
            case 3:{
                int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                int pos = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                return new BoxCondition(c, box, pos);
            }
            case 4:{
                int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                int cds[] =  bytesToInt(Arrays.copyOfRange(i.o, i.c, i.incp(len*4)));
                return new ChainAndCondition(c, cds);
            }
            case 5:{
                int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                int cds[] =  bytesToInt(Arrays.copyOfRange(i.o, i.c, i.incp(len*4)));
                return new ChainOrCondition(c, cds);
            }
        }
        return null;
    }

    private static class NegativeCondition extends Condition{
        int cond;
        Core c;
        private NegativeCondition(Core co, int subc){
            cond = subc;
            c=co;
        }
        public boolean evaluate(){
            return !c.getResource(cond, Condition.class).evaluate();
        }
        
    }

    private static class CallbackCondition extends Condition{
        int calb;
        Core c;
        public CallbackCondition(Core co, int calb){
            this.calb=calb;
            c=co;
        }

        @Override
        public boolean evaluate(){
//            System.out.println(lognm()+"c.c e1");
            return (boolean)c.getResource(calb, Callable.class).call();
        }
    }
    private static class BoxCondition extends Condition{
        int box;
        int pos;
        Core c;
        public BoxCondition(Core c,int box, int pos){
            this.box=box;
            this.pos=pos;
            this.c=c;
        }

        @Override
        public boolean evaluate(){
            Box b = c.getResource(box, Box.class);
            Position p = c.getResource(pos, Position.class);
            System.out.println(lognm()+""+p.xi()+" "+p.yi()+" "+(p.xi()>b.xi()&&p.yi()>b.yi()&&p.xi()<(b.xi()+b.zi())&&p.yi()<(b.yi()+b.wi())));
            System.out.println(lognm()+""+p.xf()+" "+p.yf()+" "+p.xi()+" "+p.yi());
            System.out.println(lognm()+""+b.xf()+" "+ b.yf()+" "+ b.zf()+" "+ b.wf()+" "+b.xi()+" "+ b.yi()+" "+ b.zi()+" "+ b.wi());
            return p.xi()>b.xi()&&p.yi()>b.yi()&&p.xi()<(b.xi()+b.zi())&&p.yi()<(b.yi()+b.wi());
        }
    }

    private static class ChainAndCondition  extends Condition{
        int[] cdss;
        Core c;
        public ChainAndCondition(Core co, int[] cds){
            cdss=cds;
            c=co;
        }
        
        public boolean evaluate(){
            for (int i = 0; i < cdss.length; i++) {
                if(!c.getResource(cdss[i], Condition.class).evaluate())return false;
            }
            return true;
        }
    }
    private static class ChainOrCondition  extends Condition{
        int[] cdss;
        Core c;
        public ChainOrCondition(Core co, int[] cds){
            cdss=cds;
            c=co;
        }
        
        public boolean evaluate(){
            for (int i = 0; i < cdss.length; i++) {
                if(c.getResource(cdss[i], Condition.class).evaluate())return false;
            }
            return false;
        }
    }
    
    
    
}
