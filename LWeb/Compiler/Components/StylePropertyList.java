package LWeb.Compiler.Components;

import LWeb.Common.Color;
import LWeb.Compiler.Components.StyleProperty;
import static LWeb.Common.Common.*;
import LWeb.Compiler.Components.LayoutProperty.Layout;
import LWeb.Compiler.Components.StyleProperty.Property;
import LWeb.Compiler.Components.TypeProvider.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiFunction;


public class StylePropertyList {
    static ArrayList<StyleProperty> list = sg(()->{
        ArrayList<StyleProperty> al= new ArrayList<>();
        list=al;
        al.add(new StyleProperty("margin-top", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
//            int top = getl(l, "o-top", int.class);
//            top += p.data.get(int.class);
//            l.put("o-top", top);
            int t = (int)(double)p.data.get(double.class);
            if(l.parent!=null)l.parent.r_height = nvl(l.parent.r_height)+t;
        }));
        al.add(new StyleProperty("margin-right", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            
            if(l.parent!=null)l.parent.r_width = nvl(l.parent.r_width)+t;
        }));
        al.add(new StyleProperty("margin-bottom", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            if(l.previous!=null){
                if(l.m_bottom==null){l.m_bottom = t;}else{l.m_bottom +=t;}
                l.m_bottom += l.previous.m_bottom + l.previous.height;
                
            }else{
                if(l.m_bottom==null){l.m_bottom = t;}else{l.m_bottom +=t;}
            }
            if(l.parent!=null)l.parent.r_height = nvl(l.parent.r_height)+t;
            
            //addlint(l, "o-bottom", p);
            
        }));
        al.add(new StyleProperty("margin-left", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            if(l.m_left==null){l.m_left = t;}else{l.m_left +=t;}
            if(l.parent!=null)l.parent.r_width = nvl(l.parent.r_width)+t;
            //addlint(l, "o-left", p);
        }));
        al.add(new StyleProperty("margin", new PropLength(0,"px"), sg(()->{
            //<editor-fold defaultstate="collapsed" desc="cast">
            BiFunction<TypeProvider[],Integer,Property[]> cast = (TypeProvider[] t, Integer p) -> {
//                        System.out.println(lognm()+"?? "+t);
//                        System.out.println(lognm()+"??2 "+byi(t));
                if(t.length==0)
                    return null;
                if(t.length==1)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t),p),
                        getByName("margin-right",byi(t),p),
                        getByName("margin-bottom",byi(t),p),
                        getByName("margin-left",byi(t),p)
                    });
                if(t.length==2)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,0),p),
                        getByName("margin-left",byi(t,1),p)
                    });
                if(t.length==3)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,0),p),
                        getByName("margin-left",byi(t,2),p)
                    });
                return flatten(new Property[][]{
                    getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,2),p),
                        getByName("margin-left",byi(t,3),p)
                });
            };
            return cast;
            //</editor-fold>
        }), null));
        al.add(new StyleProperty("padding", new PropLength(0,"px"), sg(()->{
            //<editor-fold defaultstate="collapsed" desc="cast">
            BiFunction<TypeProvider[],Integer,Property[]> cast = (TypeProvider[] t, Integer p) -> {
//                        System.out.println(lognm()+"?? "+t);
//                        System.out.println(lognm()+"??2 "+byi(t));
                if(t.length==0)
                    return null;
                if(t.length==1)
                    return flatten(new Property[][]{
                        getByName("padding-top",byi(t),p),
                        getByName("padding-right",byi(t),p),
                        getByName("padding-bottom",byi(t),p),
                        getByName("padding-left",byi(t),p)
                    });
                if(t.length==2)
                    return flatten(new Property[][]{
                        getByName("padding-top",byi(t,0),p),
                        getByName("padding-right",byi(t,1),p),
                        getByName("padding-bottom",byi(t,0),p),
                        getByName("padding-left",byi(t,1),p)
                    });
                if(t.length==3)
                    return flatten(new Property[][]{
                        getByName("padding-top",byi(t,0),p),
                        getByName("padding-right",byi(t,1),p),
                        getByName("padding-bottom",byi(t,0),p),
                        getByName("padding-left",byi(t,2),p)
                    });
                return flatten(new Property[][]{
                    getByName("padding-top",byi(t,0),p),
                        getByName("padding-right",byi(t,1),p),
                        getByName("padding-bottom",byi(t,2),p),
                        getByName("padding-left",byi(t,3),p)
                });
            };
            return cast;
            //</editor-fold>
        }
    ), null));
        al.add(new StyleProperty("width", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            //if(l.d_width==null){l.d_width = t;}else{l.d_width +=t;}
            l.d_width = nvl(l.d_width)+t;
            if(l.parent!=null)l.parent.r_width = nvl(l.parent.r_width)+t;
            
            l.width = (int)(double)p.data.get(double.class);
            //addlint(l, "o-width", p);
            //l.put("c-width", (int)(double)p.data.get(double.class));
        }));
        al.add(new StyleProperty("height", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            if(l.d_height==null){l.d_height = t;}else{l.d_height +=t;}
            
            if(l.parent!=null)l.parent.r_height = nvl(l.parent.r_height)+t;
            l.height = (int)(double)p.data.get(double.class);
            //addlint(l, "o-height", p);
            //l.put("c-height", (int)(double)p.data.get(double.class));
        }));
        al.add(new StyleProperty("background-color", new PropColor(0), null, (Property p, ElementTag l)->{
            l.background_color = p.data.get(Color.class);
            System.out.println(lognm()+""+p);
            //l.put("background-color", p.data.get(Color.class));
        }));
        al.add(new StyleProperty("color", new PropColor(0), null, (Property p, ElementTag l)->{
            l.text_color = p.data.get(Color.class);
            System.out.println(lognm()+""+p);
            //l.put("background-color", p.data.get(Color.class));
        }));
        al.add(new StyleProperty("font-size", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            l.font_size = (int)(double)p.data.get(double.class);
            System.out.println(lognm()+""+p);
            //l.put("background-color", p.data.get(Color.class));
        }));
        al.add(new StyleProperty("padding-left", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            if(l.p_left==null){l.p_left = t;}else{l.p_left +=t;}
            System.out.println(lognm()+""+p);
            //l.put("background-color", p.data.get(Color.class));
        }));
        al.add(new StyleProperty("padding-bottom", new PropLength(0,"px"), null, (Property p, ElementTag l)->{
            int t = (int)(double)p.data.get(double.class);
            if(l.p_bottom==null){l.p_bottom = t;}else{l.p_bottom +=t;}
            System.out.println(lognm()+""+p);
            //l.put("background-color", p.data.get(Color.class));
        }));
        
        return al;
    });
    
    public static <T> T getl(HashMap<String, Object> l, String k, Class<T> c){
        return castp(c,l.get(k));
    }
    public static void addl(HashMap<String, Object> l, String k, Property p){
        System.out.println(lognm()+""+k+" "+p);
        int i = (int)(double)p.data.get(double.class);
        addlint(l, k, i);
    }
    public static void addlint(HashMap<String, Object> l, String k, int i){
        int left = getl(l, k, int.class);
        left += i;
        l.put(k, left);
    }
    
    public static StyleProperty getByName(String name){
        int ind = inList(name, list,(StyleProperty s)->{
            return s.name;
        },(String s, String s2)->{
            return s.compareTo(s2);
        });
        if(ind<0)return null;
        return list.get(ind);
    }
    public static Property[] getByName(String name, TypeProvider[] tp, int priority){
        int ind = inList(name, list,(StyleProperty s)->{
            return s.name;
        },(String s, String s2)->{
            return s.compareTo(s2);
        });
        if(ind<0){
            StyleProperty sc = new StyleProperty(name, tp[0], null, null);
            list.add(sc);
            return sc.getProperties(tp, priority);
        }
        return list.get(ind).getProperties(tp, priority);
    }
}
