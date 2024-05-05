package LWeb.Common;


import LWeb.Common.Color.IntColor;
import static LWeb.Common.Pair.Pair;
import static LWeb.Common.Triple.*;
import LWeb.Compiler.Main.*;
import LWeb.Common.Range.Range;
import LWeb.Engine.Constants;
import LWeb.Engine.LWeb;
import LWeb.Engine.Util.GLEU.Shader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Common {
    public static final Range rangeA_Z = new Range('A','Z');
    public static final Range rangea_z = new Range('a','z');
    public static final Range range0_9 = new Range('0','9');
    private static final int OBJECT_HEADER_SIZE = 16; // Typical for 64-bit JVM
    private static final int OBJECT_REF_SIZE = 8;
    private static Object[] terminatorState=null;
    private static ScheduledExecutorService safetyNet=null;
    private static Thread safetyNetSource=null;
    private static int terminatorCount=0;
    private static int terminatorMax=1000;
    public enum Troolean{False,None,True}
    
    //+ColorMixersNoAlpha +ColorMixersAlphaMultiply +ColorMixersComposite
    public static String lognm(){
        return lognm(1);
    }
    public static String lognm(int off){
        StackTraceElement stea[] = Thread.currentThread().getStackTrace();
        StackTraceElement ste = stea[Math.min(2+off, stea.length-1)];
        return ""+ste;
    }
    public static String lognm(String dc, String fl, String mn, int ln){
        StackTraceElement ste = new StackTraceElement(dc,fl,mn,ln);
        return ""+ste;
    }
    
    
    public static class LoopTerminationException extends RuntimeException{
        public LoopTerminationException() {
            super();
        }
        public LoopTerminationException(String s) {
            super(s);
        }
        public LoopTerminationException(String message, Throwable cause) {
            super(message, cause);
        }
        public LoopTerminationException(Throwable cause) {
            super(cause);
        }
    }
    public static void setTMax(int tmx){terminatorMax=tmx;}
    public static void terminator(Object... o){
        if(terminatorState==null){
            terminatorState=o;
            return;
        }
        if(arrEq(terminatorState,o)){
            terminatorCount++;
        }else{
            terminatorState=o;
            terminatorCount=0;
        }
        if(terminatorMax<terminatorCount){
            terminatorCount=0;
            throw new LoopTerminationException("State remained identical for too long ("+terminatorMax+"): "+ats(o));
        }
    }
    public static void terminator(String msg, Object... o){
        if(terminatorState==null){
            terminatorState=o;
            return;
        }
        if(arrEq(terminatorState,o)){
            terminatorCount++;
        }else{
            terminatorState=o;
            terminatorCount=0;
        }
        if(terminatorMax<terminatorCount){
            terminatorCount=0;
            throw new LoopTerminationException("State remained identical for too long ("+terminatorMax+"): "+ats(o)+"\n Message: "+msg);
        }
    }
    
        
    public static int clamp(int a, int min, int max){
        return Math.min(max, Math.max(min, a));
    }
    public static float clamp(float a, float min, float max){
        return Math.min(max, Math.max(min, a));
    }
    public static void sop(String s){System.out.print(s);}
    public static void sop(Object s){System.out.print(s);}
    public static void sopl(String s){System.out.println(s);}
    public static void sopl(Object s){System.out.println(s);}
    public static void sopl(){System.out.println();}
    public static float byteToFloat(byte[] ba){
        return Float.intBitsToFloat(byteToInt(ba));
    }
    public static int byteToInt(byte[] ba){
        return  (ba[0]& 0xff)<<(3*8)|
                (ba[1]& 0xff)<<(2*8)|
                (ba[2]& 0xff)<<(1*8)|
                (ba[3]& 0xff)<<(0*8);
    }
    public static int[] bytesToInt(byte[] ba){
        int len = ba.length/4;
        int out[] = new int[len];
        for (int i = 0; i < len; i++) {
            out[i]=(ba[i*4+0]& 0xff)<<(3*8)|
                (ba[i*4+1]& 0xff)<<(2*8)|
                (ba[i*4+2]& 0xff)<<(1*8)|
                (ba[i*4+3]& 0xff)<<(0*8);
        }
        return out;
    }
    public static int[] mixIntBytes(int[] ba, int [] mix){
        int len= ba.length;
        int out[] = new int[len];
        for (int i = 0; i < len; i++) {
            out[i]=((ba[i]>>>(3*8)&0xff)<<((mix[3])*8))|
                   ((ba[i]>>>(2*8)&0xff)<<((mix[2])*8))|
                   ((ba[i]>>>(1*8)&0xff)<<((mix[1])*8))|
                   ((ba[i]>>>(0*8)&0xff)<<((mix[0])*8));
        }
        return out;
    }
    public static long byteToLong(byte[] ba){
        return  ((long)ba[0]& 0xff)<<(7*8)|
                ((long)ba[1]& 0xff)<<(6*8)|
                ((long)ba[2]& 0xff)<<(5*8)|
                ((long)ba[3]& 0xff)<<(4*8)|
                ((long)ba[4]& 0xff)<<(3*8)|
                ((long)ba[5]& 0xff)<<(2*8)|
                ((long)ba[6]& 0xff)<<(1*8)|
                ((long)ba[7]& 0xff)<<(0*8);
    }
    public static byte[] longToByte(long in){
        return new byte[]{
                    (byte)(in>>>(7*8)&0xff),
                    (byte)(in>>>(6*8)&0xff),
                    (byte)(in>>>(5*8)&0xff),
                    (byte)(in>>>(4*8)&0xff),
                    (byte)(in>>>(3*8)&0xff),
                    (byte)(in>>>(2*8)&0xff),
                    (byte)(in>>>(1*8)&0xff),
                    (byte)(in>>>(0*8)&0xff)
                };
    }
    public static byte[] intToByte(int in){
        return new byte[]{
                    (byte)(in>>>(3*8)&0xff),
                    (byte)(in>>>(2*8)&0xff),
                    (byte)(in>>>(1*8)&0xff),
                    (byte)(in>>>(0*8)&0xff)
                };
    }
    public static byte[] intToByte(int in, byte ba[]){
        ba[0]=(byte)(in>>>(3*8)&0xff);
        ba[1]=(byte)(in>>>(2*8)&0xff);
        ba[2]=(byte)(in>>>(1*8)&0xff);
        ba[3]=(byte)(in>>>(0*8)&0xff);
        return ba;
    }
    public static BufferedImage mipmap(BufferedImage inp, int lvl){
        if(lvl<0)return null;
        BufferedImage out = new BufferedImage(inp.getWidth()/2,inp.getHeight()/2,inp.getType());
        for(int w:new Range(0,out.getWidth()-1)){
            for(int h:new Range(0,out.getHeight()-1)){
                int c1=inp.getRGB(w*2, h*2);
                int c2=inp.getRGB(w*2+1, h*2);
                int c3=inp.getRGB(w*2, h*2+1);
                int c4=inp.getRGB(w*2+1, h*2+1);
                out.setRGB(w,h,((IntColor)Color.avg(c1,c2,c3,c4)).color());
            }
        }
        if(lvl==0){
            return out;
        }
        return mipmap(out,lvl-1);
    }
    
    public static BufferedImage anisotropic(BufferedImage inp, int lvlx, int lvly){
        if(lvlx<0||lvly<0)return null;
        if(lvlx==0){
            BufferedImage out = new BufferedImage(inp.getWidth(),inp.getHeight()/2,inp.getType());
            for(int w:new Range(0,out.getWidth()-1)){
                for(int h:new Range(0,out.getHeight()-1)){
                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
                    int c1=inp.getRGB(w*2, h*2);
                    int c2=inp.getRGB(w*2, h*2+1);
                    out.setRGB(w,h,((IntColor)Color.avg(c1,c2)).color());
                }
            }
            if(lvly==0)return out;
            return anisotropic(out,lvlx,lvly-1);
        }else{
            BufferedImage out = new BufferedImage(inp.getWidth()/2,inp.getHeight(),inp.getType());
            for(int w:new Range(0,out.getWidth()-1)){
                for(int h:new Range(0,out.getHeight()-1)){
                    //sopl("  w:"+w+"  h:"+h+"  c:"+rgbArray[w+h*bis.getWidth()]);
                    byte c1[]=intToByte(inp.getRGB(w*2, h*2));
                    byte c2[]=intToByte(inp.getRGB(w*2+1, h*2));
                    out.setRGB(w,h,
                            byteToInt(new byte[]{
                                    (byte)(((int)c1[0]+c2[0])/2),
                                    (byte)(((int)c1[1]+c2[1])/2),
                                    (byte)(((int)c1[2]+c2[2])/2),
                                    (byte)(((int)c1[3]+c2[3])/2)
                                })
                    );
                }
            }
            return anisotropic(out,lvlx-1,lvly);
        }
    }
    
    public static int sizeof(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            return sizeofArray(obj);
        } else if (clazz.isPrimitive()) {
            return sizeofPrimitive(clazz);
        } else {
            return sizeofObject(clazz, obj);
        }
    }
    public static int sizeof(Class<?> clazz, int... size){
        return sizeof(clazz,0, size);
    }
    private static int sizeof(Class<?> clazz, int offs, int... size){
        if (clazz == null||size.length<=offs) {
            return 0;
        }
        if (clazz.isArray()) {
            return sizeof(clazz.getComponentType(), offs+1, size)*size[offs];
        } else if (clazz.isPrimitive()) {
            return sizeofPrimitive(clazz);
        } else {
            return sizeofObject(clazz);
        }
    }
    public static int sizeof(Class<?> clazz){
        if (clazz == null) {
            return 0;
        }
        if (clazz.isArray()) {
            return sizeofPrimitive(clazz.getComponentType());
        } else if (clazz.isPrimitive()) {
            return sizeofPrimitive(clazz);
        } else {
            return sizeofObject(clazz);
        }
    }
    private static int sizeofPrimitive(Class<?> clazz) {
        if (clazz == byte.class || clazz == boolean.class) {
            return 1;
        } else if (clazz == short.class || clazz == char.class) {
            return 2;
        } else if (clazz == int.class || clazz == float.class) {
            return 4;
        } else if (clazz == long.class || clazz == double.class) {
            return 8;
        }
        return 0; // Default if not a recognized primitive
    }
    private static int sizeofArray(Object array) {
        int totalSize = 0;//OBJECT_HEADER_SIZE; // Base object overhead
        int length = Array.getLength(array);
        Class<?> componentType = array.getClass().getComponentType();

        if (componentType.isPrimitive()) {
            totalSize += length * sizeofPrimitive(componentType);
        } else {
            for (int i = 0; i < length; i++) {
                totalSize += 0;//OBJECT_REF_SIZE; // Add reference size
                Object element = Array.get(array, i);
                totalSize += sizeof(element); // Recursively calculate element sizes
            }
        }
        return totalSize;
    }
    private static int sizeofObject(Class<?> clazz) {
        int size = 0;
        for (Field field : clazz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (fieldType.isPrimitive()) {
                sizeofPrimitive(fieldType);
            } else {
                size += 8; // this can vary based on JVM and system architecture
            }
        }
        return size;
    }
    private static int sizeofObject(Class<?> clazz, Object obj) {
        int totalSize = 0;//OBJECT_HEADER_SIZE;

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType().isPrimitive()){
                totalSize += sizeof(field.getType());
            }else{
                try {
                    Object fieldValue = field.get(obj);
                    totalSize += sizeof(fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return totalSize;
    }
    
    public static Class[] getClassTree(Class c){
        ArrayList<Class> al = new ArrayList<>();
        while(c!=null){
            al.add(c);
            c = c.getEnclosingClass();
        }
        Class ba[]=new Class[al.size()];
        final int bInd[]={al.size()};
        al.forEach((Class b)->{
            ba[--bInd[0]]=b;
        });
        return ba;
    }
    /*public static Runnable[] coreToDraw(Object oa[]) throws Exception{
        Method m;
        ArrayList<Runnable> rn=new ArrayList<>();        
        for(int i=0;i<oa.length||oa[i]==CoreOps.END;i++){
            m = ((CoreLO)oa[i]).label();
            int len = ++i+m.getParameterCount();
            rn.add((Runnable) m.invoke(null, Arrays.copyOfRange(oa, i, len)));
            i=len-1;
        }
        return (Runnable[]) rn.toArray();
    }*/
    
    
    public static boolean isSubStringThere(String source, int index, String target){
        if(source.length()<index||source.length()<index+target.length())return false;
        boolean out = true;
        for(int i=index,j=0;i<source.length()&&j<target.length();i++,j++){
            out=out&&(source.charAt(i)==target.charAt(j));
        }
        return out;
    }
    
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, String target, String restart, String comStart, String comEnd, String restartReplace, String comStartReplace, String comEndReplace){
         return readTillTarget(source, offset, target, restart, comStart, comEnd,restartReplace,comStartReplace,comEndReplace,true);
    }
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, String target, String restart, String comStart, String comEnd, String restartReplace, String comStartReplace, String comEndReplace,boolean cStrings){
        StringBuilder sb=new StringBuilder();
        boolean isComStart=comStart.length()!=0;
        boolean isComEnd=comEnd.length()!=0;
        boolean isTarget=target.length()!=0;
        boolean isRestart=restart.length()!=0;
        boolean canCString = cStrings;
        boolean inCString=false;
        int lastRestart = offset;
        int comDepth=0;
        Character c=null;
        int i=offset;
        for(;i<source.length();i++){
            c= source.charAt(i);
            if(canCString&&comDepth==0&&inCString){
                if(c=='"'&&source.charAt(i-1)!='\\'){
                    inCString=false;
                }
                sb.append(c);
                continue;
            }
            if(canCString&&comDepth==0&&c=='"'){
                inCString=true;
                sb.append(c);
                continue;
            }
            if(isComEnd&&comDepth>0&&c==comEnd.charAt(0)
                    &&isSubStringThere(source, i, comEnd)){
                i+=comEnd.length()-1;
                comDepth--;
                sb.append(comEndReplace);
                continue;
            }
            if(isComStart&&c==comStart.charAt(0)
                    &&isSubStringThere(source, i, comStart)){
                i+=comStart.length()-1;
                comDepth++;
                sb.append(comStartReplace);
                continue;
            }
            if(comDepth!=0)continue;
            if(isRestart&&c==restart.charAt(0)
                    &&isSubStringThere(source, i, restart)){
                i+=restart.length()-1;
                sb=new StringBuilder(source.length()-i);
                sb.append(restartReplace);
                lastRestart=i;
                continue;
            }
            if(isTarget&&c==target.charAt(0)
                    &&isSubStringThere(source, i, target)){
                i+=target.length()-1;
                return Triple(sb.toString(),i-offset, lastRestart-offset);
            }
            sb.append(c);
        }
        
        if(comDepth!=0)return Triple("",i-offset, lastRestart-offset);
        return Triple(sb.toString(),i-offset, lastRestart-offset);
    }
    
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, IndexedPredicate<String> target, IndexedPredicate<String> restart, IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd){
        return readTillTarget(source, offset, target, restart, comStart, comEnd,"","","");
    }
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, IndexedPredicate<String> target, IndexedPredicate<String> restart, IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd, String restartReplace, String comStartReplace, String comEndReplace){
        StringBuilder sb=new StringBuilder();
        boolean isComStart=comStart!=null;
        boolean isComEnd=comEnd!=null;
        boolean isTarget=target!=null;
        boolean isRestart=restart!=null;
        boolean inCString=false;
        int lastRestart = offset;
        Pair<Boolean,Integer> p;
        int comDepth=0;
        Character c=null;
        int i=offset;
        for(;i<source.length();i++){
            c= source.charAt(i);
            if(comDepth==0&&inCString){
                if(c=='"'&&source.charAt(i-1)!='\\'){
                    inCString=false;
                }
                sb.append(c);
                continue;
            }
            if(comDepth==0&&c=='"'){
                inCString=true;
                sb.append(c);
                continue;
            }
            if(isComEnd&&comDepth>0){
                p=comEnd.test(source, i);
                if(p.getFirst()){
                    i+=p.getSecond()-1;
                    comDepth--;
                    sb.append(comEndReplace);
                    continue;
                }
            }
            if(isComStart){
                p=comStart.test(source, i);
                if(p.getFirst()){
                    i+=p.getSecond()-1;
                    comDepth++;
                    sb.append(comStartReplace);
                    continue;
                }
            }
            if(comDepth!=0)continue;
            if(isRestart){
                p=restart.test(source, i);
                if(p.getFirst()){
                    i+=p.getSecond()-1;
                    sb=new StringBuilder(source.length()-i);
                    sb.append(restartReplace);
                    lastRestart=i;
                    continue;
                }
            }
            if(isTarget){
                p=target.test(source, i);
                if(p.getFirst()){
                    i+=p.getSecond()-1;
                    return Triple(sb.toString(),i-offset, lastRestart-offset);
                }
            }
            sb.append(c);
        }
        
        if(comDepth!=0)return Triple("",i-offset, lastRestart-offset);
        return Triple(sb.toString(),i-offset, lastRestart-offset);
    }
    
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, String target, String restart, String comStart, String comEnd){
        return readTillTarget(source, offset, target, restart, comStart, comEnd,"","","",true);
    }
    public static Triple<String, Integer, Integer> readTillTarget(String source,int offset, String target, String restart, String comStart, String comEnd,boolean cStrings){
        return readTillTarget(source, offset, target, restart, comStart, comEnd,"","","",cStrings);
    }
    
    public static int skipComment(String source, int offset, String comStart, String comEnd){
        int c=readTillTarget(source,++offset,comEnd,"",comStart,comEnd).getSecond();
        if(c+offset==source.length())return c+1;
        return c+comEnd.length();
    }
    
    
    public static int countSpaces(String s){
        int num=0;
        for(;num<s.length();num++){
            if(s.charAt(num)!=' ')break;
        }
        return num;
    }
    public static int countChars(String s, char c){
        int num=0;
        for(;num<s.length();num++){
            if(s.charAt(num)!=c)break;
        }
        return num;
    }
    public static int countChars(String s, char c, int offset){
        int num=offset;
        for(;num<s.length();num++){
            if(s.charAt(num)!=c)break;
        }
        return num-offset;
    }
    public static int countInvalids(String s, int offset, Predicate<Character> c){
        int num=offset;
        for(;num<s.length();num++){
            if(!c.test(s.charAt(num)))break;
        }
        return num-offset;
    }
    
    public static Pair<String, Integer> readWhileValidWithFirst(String s, int offset){
        if(s.length()-1<offset)return Pair("",0);
        char ch= s.charAt(offset);
        if(!(rangeA_Z.equals(ch)||rangea_z.equals(ch))){
            return Pair("",0);
        }
        return readWhileValid(s,offset);
    }
    public static Pair<String, Integer> readWhileValidWithFirst(String s, int offset, Predicate<Character> cf,Predicate<Character> c){
        if(s.length()-1<offset)return Pair("",0);
        char ch= s.charAt(offset);
        if(!cf.test(ch)){
            return Pair("",0);
        }
        Pair<String, Integer> p=readWhileValid(s,offset+1,c);
        p.setFirst(ch+p.getFirst());
        p.setSecond(p.getSecond()+1);
        return p;
    }
    public static Pair<String, Integer> readWhileValid(String s, int offset){
        return readWhileValid(s,offset, (Character ch)->{
            return rangeA_Z.equals((char)ch)||rangea_z.equals((char)ch)||range0_9.equals((char)ch)||ch=='-';
        });
    }
    public static Pair<String, Integer> readWhileValid(String s, int offset, Predicate<Character> c){
        StringBuilder sb = new StringBuilder();
        int i=offset;
        for(;i<s.length()&&c.test(s.charAt(i));i++){
            sb.append(s.charAt(i));
        }
        return new Pair(sb.toString(),i-offset);
    }
    
    public static String reduceChars(String s,char ch){
        StringBuilder sb=new StringBuilder();
        boolean space=false;
        char quote=' ';
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(quote!=' '){
                if((c==quote)&&s.charAt(i-1)!='\\'){
                    quote=' ';
                }
                sb.append(c);
                continue;
            }
            if(c=='"'||c=='\''){
                quote=c;
                sb.append(c);
                continue;
            }
            if(space&&c==ch)continue;
            space=false;
            if(c==ch)space=true;
            sb.append(c);
        }
        return sb.toString();
    }
    public static String removeChars(String s,char ch){
        StringBuilder sb=new StringBuilder();
        char quote=' ';
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(quote!=' '){
                if((c==quote)&&s.charAt(i-1)!='\\'){
                    quote=' ';
                }
                sb.append(c);
                continue;
            }
            if(c=='"'||c=='\''){
                quote=c;
                sb.append(c);
                continue;
            }
            if(c==ch)continue;
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String[] splitWithCStrings(String s, char ch){
        char c[]={' '};
        return splitWithComents(s,0,(String st, int ind)->{
            if(st.charAt(ind)==ch){
                return Pair(true,1);
            }
            return Pair(false,0);
        },(String st, int ind)->{
            if(st.charAt(ind)=='"'||st.charAt(ind)=='\''){
                c[0]=st.charAt(ind);
                return Pair(true,1);
            }
            return Pair(false,0);
        },(String st, int ind)->{
            if(c[0]!=' '){
                if((st.charAt(ind)==c[0])&&ind>=1&&st.charAt(ind-1)!='\\'){
                    c[0]=' ';
                    return Pair(true,1);
                }
            }
            return Pair(false,0);
        });
    }
    
    public static String reverse(String s){
        return new StringBuilder(s).reverse().toString();
    }
    
    public static Pair<Integer, Integer> parseInt(String s){
        if (s == null) {
            throw new NumberFormatException("null");
        }

        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;
        boolean first=true;

        if (len > 0) {
            multmin = limit / 10;
            while (i < len) {
                if(s.charAt(i)==' '){i++;continue;}
                if(first){
                    first=false;
                    char firstChar = s.charAt(i);
                    if (firstChar < '0') { // Possible leading "+" or "-"
                        if (firstChar == '-') {
                            negative = true;
                            limit = Integer.MIN_VALUE;
                        } else if (firstChar != '+')
                            return null;

                        if (len == 1) // Cannot have lone "+" or "-"
                            return null;
                        i++;
                    }
                }
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),10);
                if (digit < 0) {
                    break;
                }
                if (result < multmin) {
                    return null;
                }
                result *= 10;
                if (result < limit + digit) {
                    return null;
                }
                result -= digit;
            }
        } else {
            return null;
        }
        return new Pair(negative ? result : -result,i);
    }
    
    public static Pair<Double, Integer> parseDouble(String s){
        return parseDouble(s,0);
    }
    
    public static Pair<Double, Integer> parseDouble(String s, int offset){
        if (s == null) {
            throw new NumberFormatException("null");
        }
        String sa="";
        char cl[]={' ','-','.','0','1','2','3','4','5','6','7','8','9'};
        int i=offset;
        int minusFirst=0;
        int oneDot=0;
        for(;i<s.length()&&inList(s.charAt(i),cl)!=-1&&minusFirst<2&&oneDot<2;i++){
            char c= s.charAt(i);
            if(minusFirst>0&&c==' ')minusFirst++;
            if(c=='-')minusFirst++;
            if(oneDot>0&&c==' ')oneDot++;
            if(c=='.')oneDot++;
            sa+=c;
        }
        if(sa.length()==0)
            return Pair(0.0,0);
        return Pair(Double.valueOf(sa),i-offset);
    }
    
    public static int parseIntOr(String s, int i){try{return Integer.parseInt(s);}catch(NumberFormatException e){return i;}}
    public static float parseFloatOr(String s, float i){try{return Float.parseFloat(s);}catch(NumberFormatException e){return i;}}
    
    //<editor-fold defaultstate="collapsed" desc="inList">
    public static <T,V> int inList(V e, List<T> l, Function<T,V> getr, Comparator<V> comp){
        int c=0;
        for(T i:l){
            if(comp.compare(getr.apply(i),e)==0){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T,V> int inList(V e, List<T> l, Function<T,V> comp){
        int c=0;
        for(T i:l){
            if(comp.apply(i).equals(e)){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l, Comparator<T> comp){
        int c=0;
        for(T i:l){
            if(comp.compare(i, e)==0){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l){
        int c=0;
        for(T i:l){
            if(i.equals(e)){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(char e, List<Character> l){
        int c=0;
        for(char i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(boolean e, List<Boolean> l){
        int c=0;
        for(boolean i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(int e, List<Integer> l){
        int c=0;
        for(int i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(long e, List<Long> l){
        int c=0;
        for(long i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(short e, List<Short> l){
        int c=0;
        for(short i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(byte e, List<Byte> l){
        int c=0;
        for(byte i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(float e, List<Float> l){
        int c=0;
        for(float i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(double e, List<Double> l){
        int c=0;
        for(double i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    ////
    public static <T,V> int inList(V e, T[] l, Function<T,V> getr, Comparator<V> comp){
        int c=0;
        for(T i:l){
            if(comp.compare(getr.apply(i),e)==0){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T,V> int inList(V e, T[] l, Function<T,V> comp){
        int c=0;
        for(T i:l){
            if(comp.apply(i).equals(e)){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l, Comparator<T> comp){
        int c=0;
        for(T i:l){
            if(comp.compare(i, e)==0){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l){
        int c=0;
        for(T i:l){
            if(i.equals(e)){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(char e, char[] l){
        int c=0;
        for(char i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(boolean e, boolean[] l){
        int c=0;
        for(boolean i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(int e, int[] l){
        int c=0;
        for(int i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(long e, long[] l){
        int c=0;
        for(long i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(short e, short[] l){
        int c=0;
        for(short i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(byte e, byte[] l){
        int c=0;
        for(byte i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(float e, float[] l){
        int c=0;
        for(float i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    public static int inList(double e, double[] l){
        int c=0;
        for(double i:l){
            if(i==e){
                return c;
            }
            c++;
        }
        return -1;
    }
    ////
    public static <T,V> int inList(V e, List<T> l, Function<T,V> getr, Comparator<V> comp, int s, int f){
        for(int i=s;i<Math.min(l.size(),f);i++) {
            if(comp.compare(getr.apply(l.get(i)),e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T,V> int inList(V e, List<T> l, Function<T,V> comp, int s, int f){
        for(int i=s;i<Math.min(l.size(),f);i++) {
            if(comp.apply(l.get(i)).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l, Comparator<T> comp, int s, int f){
        for(int i=s;i<Math.min(l.size(),f);i++) {
            if(comp.compare(l.get(i), e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static int inList(char e, List<Character> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(boolean e, List<Boolean> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(int e, List<Integer> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(long e, List<Long> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(short e, List<Short> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(byte e, List<Byte> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(float e, List<Float> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(double e, List<Double> l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.size(),f);i++) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    ////
    public static <T,V> int inList(V e, T[] l, Function<T,V> getr, Comparator<V> comp, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(comp.compare(getr.apply(l[i]),e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T,V> int inList(V e, T[] l, Function<T,V> comp, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(comp.apply(l[i]).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l, Comparator<T> comp, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(comp.compare(l[i], e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i].equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static int inList(char e, char[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(boolean e, boolean[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(int e, int[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(long e, long[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(short e, short[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(byte e, byte[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(float e, float[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(double e, double[] l, int s, int f){
        for(int i=s;i>=0&&i<Math.min(l.length,f);i++) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    ////
    public static <T,V> int inList(V e, List<T> l, Function<T,V> getr, Comparator<V> comp, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(comp.compare(getr.apply(l.get(i)),e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T,V> int inList(V e, List<T> l, Function<T,V> comp, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(comp.apply(l.get(i)).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l, Comparator<T> comp, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(comp.compare(l.get(i), e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, List<T> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static int inList(char e, List<Character> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(boolean e, List<Boolean> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(int e, List<Integer> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(long e, List<Long> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(short e, List<Short> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(byte e, List<Byte> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(float e, List<Float> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(double e, List<Double> l, int s, int f, int reverse){
        for(int i=Math.min(l.size(),f)-1;i>=0&&i>=s;i--) {
            if(l.get(i)==e){
                return i;
            }
        }
        return -1;
    }
    ////
    public static <T,V> int inList(V e, T[] l, Function<T,V> getr, Comparator<V> comp, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(comp.compare(getr.apply(l[i]),e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T,V> int inList(V e, T[] l, Function<T,V> comp, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(comp.apply(l[i]).equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l, Comparator<T> comp, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(comp.compare(l[i], e)==0){
                return i;
            }
        }
        return -1;
    }
    public static <T> int inList(T e, T[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i].equals(e)){
                return i;
            }
        }
        return -1;
    }
    public static int inList(char e, char[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(boolean e, boolean[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(int e, int[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(long e, long[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(short e, short[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(byte e, byte[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(float e, float[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    public static int inList(double e, double[] l, int s, int f, int reverse){
        for(int i=Math.min(l.length,f)-1;i>=0&&i>=s;i--) {
            if(l[i]==e){
                return i;
            }
        }
        return -1;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="findSubList">
    public static<T> int findSubList(List<T> l, List<T> sl, int s, int e){
        outer: for (int i = s; i < Math.min(e, l.size())-sl.size(); i++) {
            if(l.get(i).equals(sl.get(0))){
                for (int j = 1; j < sl.size(); j++) {
                    if(!sl.get(j).equals(l.get(i+j))){
                        continue outer;
                    }
                }
                return i;
            }
        }
        return -1;
    }
    public static<T> int findSubList(T[] l, T[] sl, int s, int e){
        outer: for (int i = s; i < Math.min(e, l.length)-sl.length; i++) {
            if(l[i].equals(sl[0])){
                for (int j = 1; j < sl.length; j++) {
                    if(!sl[j].equals(l[i+j])){
                        continue outer;
                    }
                }
                return i;
            }
        }
        return -1;
    }
    ////
    public static<T> int findSubList(List<T> l, List<T> sl){
        return findSubList(l,sl,0,l.size());
    }
    public static<T> int findSubList(T[] l, T[] sl){
        return findSubList(l,sl,0,l.length);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="arrEq">
    public static boolean arrEq(Object[] a, Object[] b){
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) return false;
        }
        return true;
    }
    //</editor-fold>
    
    
    public static String[] splitWithComents(String s, int offset, IndexedPredicate<String> target,IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd){
        return splitWithComents(s,offset,target,comStart,comEnd,(Triple<Boolean,String,Integer> p)->{
            if(!p.getFirst()&&p.getThird()<p.getSecond().length()){
                return ""+p.getSecond().charAt(p.getThird());
            }
            return "";
        },(Triple<Boolean,String,Integer> p)->{
            if(!p.getFirst()&&p.getThird()<p.getSecond().length()){
                return ""+p.getSecond().charAt(p.getThird());
            }
            return "";
        });
    }
    public static String[] splitWithComents(String s, int offset, IndexedPredicate<String> target,IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd, Function<Triple<Boolean,String,Integer>,String> comStartReplace,Function<Triple<Boolean,String,Integer>,String> comEndReplace){
        StringBuilder sb=new StringBuilder();
        ArrayList<String> al=new ArrayList<>();
        boolean quote=false;
        Pair<Boolean,Integer> p;
        for(int i=offset;i<s.length();){
            if(quote){
                p = comEnd.test(s, i);
                i+=p.getSecond();
                if(p.getFirst()){
                    quote=false;
                }else{
                    i++;
                }
                if(comEndReplace!=null){
                    sb.append(comEndReplace.apply(Triple(p.getFirst(),s,i)));
                } 
                continue;
            }
            p = comStart.test(s, i);
            i+=p.getSecond();
            if(p.getFirst()){
                quote=true;
                if(comStartReplace!=null){
                    sb.append(comStartReplace.apply(Triple(false,s,i-1)));
                    sb.append(comStartReplace.apply(Triple(false,s,i)));
                }
                continue;
            }
            
            p = target.test(s, i);
            i+=p.getSecond();
            if(p.getFirst()){
                al.add(sb.toString());
                sb=new StringBuilder();
                continue;
            }
            sb.append(s.charAt(i));
            i++;
        }
        al.add(sb.toString());
        return al.toArray(new String[0]);
    }
    
    public static String[] splitWithBrackets(String s, int offset, IndexedPredicate<String> target,IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd){
        return splitWithBrackets(s,offset,target,comStart,comEnd,(Triple<Boolean,String,Integer> p)->{
            if(!p.getFirst()&&p.getThird()<p.getSecond().length()){
                return ""+p.getSecond().charAt(p.getThird());
            }
            return "";
        },(Triple<Boolean,String,Integer> p)->{
            if(!p.getFirst()&&p.getThird()<p.getSecond().length()){
                return ""+p.getSecond().charAt(p.getThird());
            }
            return "";
        });
    }
    public static String[] splitWithBrackets(String s, int offset, IndexedPredicate<String> target,IndexedPredicate<String> comStart, IndexedPredicate<String> comEnd, Function<Triple<Boolean,String,Integer>,String> comStartReplace,Function<Triple<Boolean,String,Integer>,String> comEndReplace){
        StringBuilder sb=new StringBuilder();
        ArrayList<String> al=new ArrayList<>();
        Pair<Boolean,Integer> p;
        for(int i=offset;i<s.length();){
            p = comEnd.test(s, i);
            i+=p.getSecond();
            if(p.getFirst()){
                if(comEndReplace!=null){
                    sb.append(comEndReplace.apply(Triple(false,s,i-1)));
                }
                continue;
            }
            p = comStart.test(s, i);
            i+=p.getSecond();
            if(p.getFirst()){
                if(comStartReplace!=null){
                    sb.append(comStartReplace.apply(Triple(false,s,i-1)));
                }
                continue;
            }
            
            p = target.test(s, i);
            i+=p.getSecond();
            if(p.getFirst()){
                al.add(sb.toString());
                sb=new StringBuilder();
                continue;
            }
            sb.append(s.charAt(i));
            i++;
        }
        al.add(sb.toString());
        return al.toArray(new String[0]);
    }
    
    public static <T> String ats(T[] arr){return Arrays.toString(arr);}
    public static String ats(char[] arr){return Arrays.toString(arr);}
    public static String ats(boolean[] arr){return Arrays.toString(arr);}
    public static String ats(int[] arr){return Arrays.toString(arr);}
    public static String ats(long[] arr){return Arrays.toString(arr);}
    public static String ats(short[] arr){return Arrays.toString(arr);}
    public static String ats(byte[] arr){return Arrays.toString(arr);}
    public static String ats(float[] arr){return Arrays.toString(arr);}
    public static String ats(double[] arr){return Arrays.toString(arr);}
    
    public static boolean onlyWhitespace(String s){
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!(c==' '||c=='\t'||c=='\n'||c=='\r'))return false;
        }
        return true;
    }
    
    //<editor-fold defaultstate="collapsed" desc="veriadic to array">
    public static <T> T[] vp(T... t){
        return t;
    }
    public static char[] vp(char... t){
        return t;
    }
    public static boolean[] vp(boolean... t){
        return t;
    }
    public static int[] vp(int... t){
        return t;
    }
    public static long[] vp(long... t){
        return t;
    }
    public static short[] vp(short... t){
        return t;
    }
    public static byte[] vp(byte... t){
        return t;
    }
    public static float[] vp(float... t){
        return t;
    }
    public static double[] vp(double... t){
        return t;
    }
    //</editor-fold>
    
    public static <T> T sg(Supplier<T> func){
        return func.get();//static generator 
    }
    public static <T> T[] sg(Supplier<T>[] func){
        T[] out = (T[])new Object[func.length];
        for (int i = 0; i < func.length; i++) {
            out[i] = func[i].get();
        }
        return out;//static generator 
    }
    public static <T> T sg(Supplier<T>[] func, int ind){
        return func[ind].get();//static generator 
    }

    //<editor-fold defaultstate="collapsed" desc="byi">
    public static <T> T[] byi(T[] t){
        T [] a =na(t, 1);
        a[0]=t[0];
        return a;
    }
    public static <T> T[] byi(T[] t, int i){
        T [] a =na(t,1);
        a[0]=t[Math.min(t.length-1, i<0?0:i)];
        return a;
    }
    public static <T> T[] byi(T[] t, int s, int e){
        return Arrays.copyOfRange(t, s, e);
    }
    //</editor-fold>
    
    public static String fileToString(String f){
        BufferedReader br1 = null;
        String s1="";
        try {
            br1 = new BufferedReader(new FileReader(f));
            while(br1.ready()){
                s1+=br1.readLine();
                if(br1.ready())s1+="\n";
            }
            return s1;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br1.close();
            } catch (IOException ex) {
                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s1;
    }
    public static String fileToString(File f){
        BufferedReader br1 = null;
        String s1="";
        try {
            br1 = new BufferedReader(new FileReader(f));
            while(br1.ready()){
                s1+=br1.readLine();
                if(br1.ready())s1+="\n";
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                br1.close();
            } catch (IOException ex) {
                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s1;
    }
    
    public static <T> T[] na(T[][][] t, int size){
        return (T[]) java.lang.reflect.Array.newInstance(t.getClass().getComponentType().getComponentType().getComponentType(), size);
    }
    public static <T> T[] na(T[][] t, int size){
        return (T[]) java.lang.reflect.Array.newInstance(t.getClass().getComponentType().getComponentType(), size);
    }
    public static <T> T[] na(T[] t, int size){
        return (T[]) java.lang.reflect.Array.newInstance(t.getClass().getComponentType(), size);
    }
    public static <T> T[] na(T t, int size){
        return (T[]) java.lang.reflect.Array.newInstance(t.getClass(), size);
    }
    
    public static <T,R> R map(T[] t, R[] r, T in, R n){
        if(t.length!=r.length)return n;
        int i=inList(in,t);
        if(i==-1)return n;
        return r[i];
    }
    
    public static <T> boolean basicEq(T t1, T t2){return t1.equals(t2);}
    
    //<editor-fold defaultstate="collapsed" desc="flatten">
    public static <T> T[] flatten(T[][] a){
        int len=0;
        for(T[] b:a){
            len+=b.length;
        }
        T[] r = na(a,len);
        int i=0;
        for(T[] b:a){
            for(T c:b){
                r[i++]=c;
            }
        }
        return r;
    }
    
    public static char[] flatten(char[][] a){
        int len=0;
        for(char[] b:a){
            len+=b.length;
        }
        char[] r =  new char[len];
        int i=0;
        for(char[] b:a){
            for(char c:b){
                r[i++]=c;
            }
        }
        return r;
    }
    public static boolean[] flatten(boolean[][] a){
    int len=0;
    for(boolean[] b:a){
        len+=b.length;
    }
    boolean[] r =  new boolean[len];
    int i=0;
    for(boolean[] b:a){
        for(boolean c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static int[] flatten(int[][] a){
    int len=0;
    for(int[] b:a){
        len+=b.length;
    }
    int[] r =  new int[len];
    int i=0;
    for(int[] b:a){
        for(int c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static long[] flatten(long[][] a){
    int len=0;
    for(long[] b:a){
        len+=b.length;
    }
    long[] r =  new long[len];
    int i=0;
    for(long[] b:a){
        for(long c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static short[] flatten(short[][] a){
    int len=0;
    for(short[] b:a){
        len+=b.length;
    }
    short[] r =  new short[len];
    int i=0;
    for(short[] b:a){
        for(short c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static byte[] flatten(byte[][] a){
    int len=0;
    for(byte[] b:a){
        len+=b.length;
    }
    byte[] r =  new byte[len];
    int i=0;
    for(byte[] b:a){
        for(byte c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static float[] flatten(float[][] a){
    int len=0;
    for(float[] b:a){
        len+=b.length;
    }
    float[] r =  new float[len];
    int i=0;
    for(float[] b:a){
        for(float c:b){
            r[i++]=c;
        }
    }
    return r;
}
    public static double[] flatten(double[][] a){
        int len=0;
        for(double[] b:a){
            len+=b.length;
        }
        double[] r =  new double[len];
        int i=0;
        for(double[] b:a){
            for(double c:b){
                r[i++]=c;
            }
        }
        return r;
    }
    
    public static String flatten(String[] a){
        String s="";
        for(String b:a){
            s+=b;
        }
        return s;
    }
    


//</editor-fold>
    
    public static float[] mxMuls(float[] vk, float[][] mx){
        if(mx.length!=vk.length)throw new RuntimeException("Matrix and vektor sizes dont match");
        for (int i = 0; i < vk.length; i++) {
            float f=0;
            float ms[] = mx[i];
            for(float v:ms){
                f+=vk[i]*v;
            }
            vk[i]=f;
        }
        return vk;
    }
    public static float[] mxMul(float[] vk, float[][] mx){
        float vs[] = Arrays.copyOf(vk, vk.length);
        return mxMuls(vs,mx);
    }
    private static float _mxDet(float[][] mx){
        float f=0;
        float fn=0;
        int ln=mx.length;
        for (int i=0; i < ln; i++) {
            float t=1;
            for (int j = 0; j < ln; j++) {
                t*=mx[j][(i+j<ln)?i+j:i+j-ln];
            }
            f+=t;
        }
        for (int i=0; i < ln; i++) {
            float t=1;
            for (int j = 0; j < ln; j++) {
                t*=mx[ln-j-1][ln-1-((i+j<ln)?i+j:i+j-ln)];
            }
            fn+=t;
        }
        return f-fn;
    }

    public static<T> T cast(Class<T> c, Object o ){
        return (T)o;
    } 
    
    
    
    //testing ground
    
    public static void main(String[] args) {
        
        
        Pair<Integer, Integer> i = cast(Pair.class, Pair(42,24));
        System.out.println(lognm()+""+byteToInt(new byte[]{-16, 0,0,3}));//268435453
        
        
        
    }


}
