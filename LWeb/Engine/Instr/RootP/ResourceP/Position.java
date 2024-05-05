package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;

public abstract class Position {
    public abstract float xf();
    public abstract float yf();
    public abstract int xi();
    public abstract int yi();
    public Position p=null;
    
    public static class FloatPos extends Position{
        public float x;
        public float y;

        public static Object getRsc(byte[] o, Counter i, Core c){
            float x = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            float y = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
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
        public int x;
        public int y;

        public static Object getRsc(byte[] o, Counter i, Core c){
            int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});

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
