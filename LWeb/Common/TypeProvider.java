
package Common;

import static Common.Color.Color;
import static Common.Common.*;
import java.util.HashMap;

public interface TypeProvider{

    boolean detirmanant();
    Object get();
    int order();
    void set(TypeProvider p);
    void set(Object value,int order);
    TypeProvider add(TypeProvider p);
    TypeProvider sub(TypeProvider p);
    TypeProvider negate();
    TypeProvider mul(TypeProvider p);
    TypeProvider div(TypeProvider p);
    TypeProvider inverse();
    @Override
    String toString();

    /**
     * get type provider from string eg. 35px, calc(20px + 30px), block, #ffffff
    */
    public static TypeProvider getType(String s, HashMap<String, Double> scales){
        TypeProvider t = null;
        if(s.isEmpty())return t;
        sopl(s);
        boolean nom = 0<=inList(s.charAt(0),new char[]{'-','.','0','1','2','3','4','5','6','7','8','9'});//check if number eg, 34px
        if(nom){// proces into numeroc TypeProvider
            Pair<Double, Integer> pd = parseDouble(s);
            String sfx=s.substring(pd.getSecond());
            sopl(" sfx:"+sfx);
            t= getFromSufix(sfx,pd.getFirst(),scales);//get typeProvider
            return t;
        }
        if(s.charAt(0)=='#'){//proccess colors eg. #ffffff
            t = new ColorType(s);
            return t;
        }
        int bckInd=s.indexOf("(");
        if(bckInd>=0){// proccess functions eg. calc(20px + 30px), var(--uhh)
            Triple<String, Integer, Integer> func=readTillTarget(s,0,"(","","","");
            switch(func.getFirst()){
                case "calc":


                    break;
                case "rgba":


                    break;
            }
        }
        return new TypeList(s);// generate list typeProvider eg. block, inline
    };
    
    //<editor-fold defaultstate="collapsed" desc="Scalar">
    class Scalar implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        private int order=0;
        public Scalar(double l){
            value=l;
        }
        private Scalar(double l,int ord){
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
        public int order() {
            return 0;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof Scalar))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
            value=(double) p.get();
        }
        @Override
        public void set(Object value,int order) {
            this.value=(double) value;
        }
        @Override
        public TypeProvider add(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Scalar){
                return new Scalar(this.value+(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Scalar){
                return new Scalar(this.value-(double)p.get());
            }
            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider negate() {
            return new Scalar(-this.value);
        }
        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Scalar){
                return new Scalar(this.value/(double)p.get());
            }
            return p.inverse().mul(this);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Scalar){
                return new Scalar(this.value*(double)p.get());
            }
            return p.mul(this);
        }
        @Override
        public TypeProvider inverse() {
            return new Scalar(1/this.value);
        }
        @Override
        public String toString(){
            return ""+value;
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Length">
    class Length implements TypeProvider{
        private boolean detirmanant=true;
        private double value=0;
        private int order=1;

        public Length(double l){
            value=l;
        }
        private Length(double l,int ord){
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
        public int order() {
            return order;
        }
        @Override
        public void set(TypeProvider p) {
            if(p==null)return;
            if(!(p instanceof Length))throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
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
            if(p instanceof Length){
                if(this.order!=p.order())throw new TypeMismatchException("Order "+p.order()+" does not match order "+this.order);
                return new Length(this.value+(double)p.get(),this.order);
            }

            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }
        @Override
        public TypeProvider sub(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Length){
                if(this.order!=p.order())throw new TypeMismatchException("Order "+p.order()+" does not match order "+this.order);
                return new Length(this.value-(double)p.get(),this.order);
            }

            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider negate() {
            return new Length(-this.value,this.order);
        }
        @Override
        public TypeProvider mul(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Length){
                int ord=this.order+p.order();
                if(ord==0)return new Scalar(this.value/(double)p.get(),0);
                return new Length(this.value*(double)p.get(),ord);
            }


            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider div(TypeProvider p) {
            if(p==null)return null;
            if(p instanceof Length){
                int ord=this.order-p.order();
                if(ord==0)return new Scalar(this.value/(double)p.get(),0);
                return new Length(this.value/(double)p.get(),ord);
            }


            throw new TypeMismatchException("Type "+p.getClass()+" does not match type "+this.getClass());
        }

        @Override
        public TypeProvider inverse() {
            return new Length(1/this.value,-this.order);
        }
        @Override
        public String toString(){
            if(order==1)
                return ""+value+"px";
            return ""+value+"px^"+order;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Color">
    class ColorType implements TypeProvider{
        private boolean detirmanant=true;
        private Color value;
        private int order=0;
        public ColorType(int l){
            value=Color(l);
        }
        public ColorType(String s){
            value=Color(s);
        }
        public ColorType(Color c){
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
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="List">
    class TypeList implements TypeProvider{
        private boolean detirmanant=true;
        private String value;
        private int order=0;
        public TypeList(String s){
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
                t = new Scalar(val);
                break;
            case 2:
                switch(sfx){
                    case "px":case"vh":
                        // change scales to call getType on TP with a scale and a calc eg. 
                        // 10vh -> calc(10/100 * var(viewport-height[in px])), 25px -> calc(25px * var(scale[numeric eg. 1, 1.2]))
                        t = new Length(val*scales.get(sfx));
                        break;
                }

                break;
        }
        return t;
    }
}