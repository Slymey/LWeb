package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ats;
import static LWeb.Common.Common.bytesToInt;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.istb;
import static LWeb.Common.Common.itb;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
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
//                System.out.println(lognm()+"cac: "+len);
                int cds[] =  bytesToInt(Arrays.copyOfRange(i.o, i.c, i.incp(len*4)));
//                System.out.println(lognm()+"cacs: "+ats(cds));
                return new ChainAndCondition(c, cds);
            }
            case 5:{
                int len = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                int cds[] =  bytesToInt(Arrays.copyOfRange(i.o, i.c, i.incp(len*4)));
                return new ChainOrCondition(c, cds);
            }
            case 6:{
                int rval = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                int val =  byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
                return new EqualsCondition(c, rval, val);
            }
        }
        return null;
    }

    public static class NegativeCondition extends Condition{
        public static ResourceInst.RByteCol getBytes(int Rcondition){
            return new ResourceInst.RByteCol(11,  ib(1), itb(Rcondition));
        }
        
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

    public static class CallbackCondition extends Condition{
        public static ResourceInst.RByteCol getBytes(int Rcallable){
            return new ResourceInst.RByteCol(11,  ib(2), itb(Rcallable));
        }
        
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
    public static class BoxCondition extends Condition{
        public static ResourceInst.RByteCol getBytes(int Rbox, int Rpos){
            return new ResourceInst.RByteCol(11,  ib(3), itb(Rbox), itb(Rpos));
        }
        
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
//            System.out.println(lognm()+""+p.xi()+" "+p.yi()+" "+(p.xi()>b.xi()&&p.yi()>b.yi()&&p.xi()<(b.xi()+b.zi())&&p.yi()<(b.yi()+b.wi())));
//            System.out.println(lognm()+""+p.xf()+" "+p.yf()+" "+p.xi()+" "+p.yi());
//            System.out.println(lognm()+""+b.xf()+" "+ b.yf()+" "+ b.zf()+" "+ b.wf()+" "+b.xi()+" "+ b.yi()+" "+ b.zi()+" "+ b.wi());
            return p.xi()>b.xi()&&p.yi()>b.yi()&&p.xi()<(b.xi()+b.zi())&&p.yi()<(b.yi()+b.wi());
        }
    }

    public static class ChainAndCondition  extends Condition{
        public static ResourceInst.RByteCol getBytes(int... Rconditions){
            byte r[] = istb(Rconditions);
            return new ResourceInst.RByteCol(11,  ib(4), itb(Rconditions.length), r);
        }
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
    public static class ChainOrCondition  extends Condition{
        public static ResourceInst.RByteCol getBytes(int... Rconditions){
            byte r[] = istb(Rconditions);
            return new ResourceInst.RByteCol(11,  ib(5), itb(Rconditions.length), r);
        }
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
    public static class EqualsCondition  extends Condition{
        public static ResourceInst.RByteCol getBytes(int Rvalue, int value){
            return new ResourceInst.RByteCol(11,  ib(6), itb(Rvalue), itb(value));
        }
        int rval;
        int val;
        Core c;
        public EqualsCondition(Core co, int rval, int val){
            this.rval=rval;
            this.val=val;
            c=co;
        }
        
        public boolean evaluate(){
            //System.out.println("cond: "+c.getResource(rval, int.class)+" "+val);
            return c.getResource(rval, int.class)==val;
        }
        
    }
    
    
}
