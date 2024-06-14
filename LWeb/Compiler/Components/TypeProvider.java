
package LWeb.Compiler.Components;

import LWeb.Common.Color;
import LWeb.Common.Pair;
import LWeb.Compiler.Components.Calculate;
import static LWeb.Common.Color.Color;
import static LWeb.Common.Common.*;
import LWeb.Compiler.Parser.TokenType;
import java.net.URI;
import java.util.HashMap;

public interface TypeProvider{
    //public static int e=12;
    public static float ppmm=19.5f/1080;
    public static float bpxs=1.0f;//virtual pixels per real pixel
    boolean detirmanant();
    Object get();
    <T>T get(Class<T> c);
    int order();
    void set(TypeProvider p);
    void set(Object value,int order);
    TypeProvider add(TypeProvider p);
    TypeProvider sub(TypeProvider p);
    TypeProvider negate();
    TypeProvider mul(TypeProvider p);
    TypeProvider div(TypeProvider p);
    TypeProvider inverse();
    TypeProvider copy();
    @Override
    String toString();

    //used with <length>, <frequency>, <angle>, <time>, <percentage>, <number>, or <integer> values.
    //<editor-fold defaultstate="collapsed" desc="Calc">
    public static class PropCalc implements TypeProvider{
        private boolean detirmanant=true;
        private Calculate value;
        public PropCalc(Calculate c){
            value=c;
        }
        @Override
        public boolean detirmanant() {
            return detirmanant;
        }
        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof PropCalc))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value = (Calculate) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value= (Calculate) value;
        }
        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropCalc){
                return this.value.get().add((TypeProvider) p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropCalc){
                return this.value.get().sub((TypeProvider) p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider negate() {
            return this.value.get().negate();
        }
        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return this.value.get().div((TypeProvider) p.get());
            }
            return p.inverse().mul(this);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return this.value.get().mul((TypeProvider) p.get());
            }
            return p.mul(this);
        }
        @Override
        public TypeProvider inverse() {
            return this.value.get().inverse();
        }
        @Override
        public String toString(){
            return ""+value;
        }

        @Override
        public TypeProvider copy() {
            return new PropCalc(value);
        }

        
        
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Scalar">
    public static class PropScalar implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        public PropScalar(double l){
            value=l;
        }
        @Override
        public boolean detirmanant() {
            return detirmanant;
        }
        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof PropScalar))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value=(double) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value=(double) value;
        }
        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return new PropScalar(this.value+(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return new PropScalar(this.value-(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider negate() {
            return new PropScalar(-this.value);
        }
        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return new PropScalar(this.value/(double)p.get());
            }
            return p.inverse().mul(this);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropScalar){
                return new PropScalar(this.value*(double)p.get());
            }
            return p.mul(this);
        }
        @Override
        public TypeProvider inverse() {
            return new PropScalar(1/this.value);
        }
        @Override
        public String toString(){
            return ""+value;
        }

        @Override
        public TypeProvider copy() {
            return new PropScalar(value);
        }
        
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Percent">
    public static class PropPercent implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        public PropPercent(double l){
            value=l;
        }
        @Override
        public boolean detirmanant() {
            return detirmanant;
        }
        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof PropPercent))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value=(double) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value=(double) value;
        }
        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropPercent){
                return new PropPercent(this.value+(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropPercent){
                return new PropPercent(this.value-(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider negate() {
            return new PropPercent(-this.value);
        }
        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropPercent){
                return new PropPercent(this.value/(double)p.get());
            }
            return p.inverse().mul(this);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropPercent){
                return new PropPercent(this.value*(double)p.get());
            }
            return p.mul(this);
        }
        @Override
        public TypeProvider inverse() {
            return new PropPercent(1/this.value);
        }
        @Override
        public String toString(){
            return ""+value;
        }

        @Override
        public TypeProvider copy() {
            return new PropPercent(value);
        }
        
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PropTime">
    public static class PropTime implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        private int order=0;
        public PropTime(double l){
            value=l;
        }
        @Override
        public boolean detirmanant() {
            return detirmanant;
        }
        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof PropTime))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value=(double) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value=(double) value;
        }
        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropTime){
                return new PropTime(this.value+(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropTime){
                return new PropTime(this.value-(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider negate() {
            return new PropTime(-this.value);
        }
        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropTime){
                return new PropTime(this.value/(double)p.get());
            }
            return p.inverse().mul(this);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropTime){
                return new PropTime(this.value*(double)p.get());
            }
            return p.mul(this);
        }
        @Override
        public TypeProvider inverse() {
            return new PropTime(1/this.value);
        }
        @Override
        public String toString(){
            if(order==1)
                return ""+value+"s";
            return ""+value+"s^"+order;
        }
        @Override
        public TypeProvider copy() {
            return new PropTime(value);
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Length">
    public static class PropLength implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        private String type;
        private int order=1;

        public PropLength(double l, String t){
            value=l;
            type=t;
        }
        private PropLength(double l,int ord){
            value=l;
            order=ord;
        }
        @Override
        public boolean detirmanant() {
            return detirmanant;
        }
        @Override
        public Double get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return order;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof PropLength))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value=(double) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value = (double) value;
            this.order = order;
        }


        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropLength){
                if(this.order!=p.order())throw new TypeMismatchException("Order "+p.order()+" does not match order "+this.order);
                return new PropLength(this.value+(double)p.get(),this.order);
            }

            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropLength){
                if(this.order!=p.order())throw new TypeMismatchException("Order "+p.order()+" does not match order "+this.order);
                return new PropLength(this.value-(double)p.get(),this.order);
            }

            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider negate() {
            return new PropLength(-this.value,this.order);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropLength){
                int ord=this.order+p.order();
                if(ord==0)return new PropScalar(this.value/(double)p.get());
                return new PropLength(this.value*(double)p.get(),ord);
            }


            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof PropLength){
                int ord=this.order-p.order();
                if(ord==0)return new PropScalar(this.value/(double)p.get());
                return new PropLength(this.value/(double)p.get(),ord);
            }


            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider inverse() {
            return new PropLength(1/this.value,-this.order);
        }
        @Override
        public String toString(){
            if(order==1)
                return ""+value+type;
            return ""+value+type+"^"+order;
        }
        @Override
        public TypeProvider copy() {
            return new PropLength(value, order);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Color">
    public static class PropColor implements TypeProvider{
        private boolean detirmanant=true;
        private Color value;
        private int order=0;
        public PropColor(int l){
            value=Color(l);
        }
        public PropColor(String s){
            value=Color(s);
        }
        public PropColor(Color c){
            value=c;
        }
        @Override
        public boolean detirmanant() {
            return true;
        }

        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }

        @Override
        public void set(TypeProvider p) {
            value=(Color) p.get();
        }

        @Override
        public void set(Object value, int order) {
            this.value=(Color) value;
        }

        @Override
        public TypeProvider add(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider sub(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider negate() {
            return null;
        }

        @Override
        public TypeProvider mul(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider inverse() {
            return null;
        }
        @Override
        public TypeProvider copy() {
            return new PropColor(value);
        }
        public String toString(){
            return ""+value;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Uri">
    public static class PropUri implements TypeProvider{
        private boolean detirmanant=true;
        private URI value;
        private int order=0;
        public PropUri(URI s){
            value=s;
        }
        @Override
        public boolean detirmanant() {
            return true;
        }

        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }

        @Override
        public void set(TypeProvider p) {
            value=(URI) p.get();
        }

        @Override
        public void set(Object value, int order) {
            this.value=(URI) value;
        }

        @Override
        public TypeProvider add(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider sub(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider negate() {
            return null;
        }

        @Override
        public TypeProvider mul(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider inverse() {
            return null;
        }
        @Override
        public TypeProvider copy() {
            return new PropUri(value);
        }
        public String toString(){
            return ""+value;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="List">
    public static class PropList implements TypeProvider{
        private boolean detirmanant=true;
        private String value;
        private int order=0;
        public PropList(String s){
            value=s;
        }
        @Override
        public boolean detirmanant() {
            return true;
        }

        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }

        @Override
        public void set(TypeProvider p) {
            value=(String) p.get();
        }

        @Override
        public void set(Object value, int order) {
            this.value=(String) value;
        }

        @Override
        public TypeProvider add(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider sub(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider negate() {
            return null;
        }

        @Override
        public TypeProvider mul(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider inverse() {
            return null;
        }
        @Override
        public TypeProvider copy() {
            return new PropList(value);
        }
        public String toString(){
            return value;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Unknown">
    public static class PropUnknown implements TypeProvider{
        private boolean detirmanant=true;
        private Pair<TokenType[], String[]> value;
        private int order=0;
        public PropUnknown(Pair<TokenType[], String[]> s){
            value=s;
        }
        @Override
        public boolean detirmanant() {
            return true;
        }

        @Override
        public Object get() {
            return value;
        }
        @Override
        public <T> T get(Class<T> c){
            return cast(c, value);
        }
        @Override
        public int order() {
            return 0;
        }

        @Override
        public void set(TypeProvider p) {
            value=(Pair<TokenType[], String[]>) p.get();
        }

        @Override
        public void set(Object value, int order) {
            this.value=(Pair<TokenType[], String[]>) value;
        }

        @Override
        public TypeProvider add(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider sub(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider negate() {
            return null;
        }

        @Override
        public TypeProvider mul(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            return null;
        }

        @Override
        public TypeProvider inverse() {
            return null;
        }
        @Override
        public TypeProvider copy() {
            return new PropUnknown(value);
        }
        public String toString(){
            return ""+value;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Expception">
    public class TypeMismatchException extends IllegalArgumentException{
        public TypeMismatchException() {
            super();
        }
        public TypeMismatchException(String s) {
            super(s);
        }
        public TypeMismatchException(String message, Throwable cause) {
            super(message, cause);
        }
        public TypeMismatchException(Throwable cause) {
            super(cause);
        }
    }
    //</editor-fold>


    public static TypeProvider getFromSufix(String sfx, Double val, HashMap<String, Double> scales){
        TypeProvider t = null;
        switch(sfx.length()){
            case 0:
                t = new PropScalar(val);
                break;
            case 2:
                switch(sfx){
                    case "px":case"vh":
                        // change scales to call getType on TP with a scale and a calc eg. 
                        // 10vh -> calc(10/100 * var(viewport-height[in px])), 25px -> calc(25px * var(scale[numeric eg. 1, 1.2]))
                        t = new PropLength(val*scales.get(sfx),sfx);
                        break;
                }

                break;
        }
        return t;
    }
}