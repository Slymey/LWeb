package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;

public abstract class Box {
    public abstract float xf();
    public abstract float yf();
    public abstract float zf();
    public abstract float wf();
    public abstract int xi();
    public abstract int yi();
    public abstract int zi();
    public abstract int wi();
    public Position p=null;
    public static class FloatBox extends Box{
        float x;
        float y;
        float z;
        float w;
        public static Object getRsc(byte[] o, Counter i, Core c){
            float x = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            float y = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            float z = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            float w = byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            return new FloatBox(x, y, z, w);
        }
        public FloatBox(float x, float y, float z, float w){
            this.x=x;
            this.y=y;
            this.z=z;
            this.w=w;
        }

        public float xf(){return x;}
        public float yf(){return y;}
        public float zf(){return z;}
        public float wf(){return w;}
        public int xi(){if(p==null)return (int)(x*100); return (int)(p.xi()*x);}
        public int yi(){if(p==null)return (int)(y*100); return (int)(p.yi()*y);}
        public int zi(){if(p==null)return (int)(z*100); return (int)(p.xi()*z);}
        public int wi(){if(p==null)return (int)(w*100); return (int)(p.yi()*w);}
    }
    
    
    
    public static class IntBox extends Box{
        int x;
        int y;
        int z;
        int w;

        public static Object getRsc(byte[] o, Counter i, Core c){
            int x = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            int y = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            int z = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            int w = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
            return new IntBox(x, y, z, w);
        }

        public IntBox(int x, int y, int z, int w){
            this.x=x;
            this.y=y;
            this.z=z;
            this.w=w;
        }
        
        public int xi(){return x;}
        public int yi(){return y;}
        public int zi(){return z;}
        public int wi(){return w;}
        public float xf(){if(p==null)return x*100.0f; return x*1.0f/p.xi();}
        public float yf(){if(p==null)return y*100.0f; return y*1.0f/p.yi();}
        public float zf(){if(p==null)return z*100.0f; return z*1.0f/p.xi();}
        public float wf(){if(p==null)return w*100.0f; return w*1.0f/p.yi();}
    }
}
