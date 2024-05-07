package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public abstract class Position {
    public abstract float xf();
    public abstract float yf();
    public abstract int xi();
    public abstract int yi();
    public Position p=null;
    
    public static class FloatPos extends Position{
        public static ResourceInst.RByteCol getBytes(float x, float y){
            return new ResourceInst.RByteCol(16, ftb(x), ftb(y));
        }
        
        public float x;
        public float y;

        public static Object getRsc(ByteCounter i, Core c){
            float x = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float y = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            return new FloatPos(x, y);
        }
        public FloatPos(float x, float y){
            this.x=x;
            this.y=y;
        }
        
        public float xf(){return x;}
        public float yf(){return y;}
        public int xi(){if(p==null)return (int)(x*100); return (int)(p.xi()*x);}
        public int yi(){if(p==null)return (int)(y*100); return (int)(p.yi()*y);}
    }
    
    public static class IntPos extends Position{
        public static ResourceInst.RByteCol getBytes(int x, int y){
            return new ResourceInst.RByteCol(17, itb(x), itb(y));
        }
        
        public int x;
        public int y;

        public static Object getRsc(ByteCounter i, Core c){
            int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});

            return new IntPos(x, y);
        }

        public IntPos(int x, int y){
            this.x=x;
            this.y=y;
        }
        public int xi(){return x;}
        public int yi(){return y;}
        public float xf(){if(p==null)return x/100.0f; return x*1.0f/p.xi();}
        public float yf(){if(p==null)return y/100.0f; return y*1.0f/p.yi();}
    }
    
}
