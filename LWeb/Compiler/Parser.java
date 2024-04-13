package LWeb.Compiler;

import LWeb.Common.Common;
import static LWeb.Common.Common.*;
import LWeb.Common.ElementTag;
import LWeb.Common.EllClass;
import static LWeb.Common.EllClass.Activity.DISABLED;
import static LWeb.Common.EllClass.Activity.ENABLED;
import static LWeb.Common.EllClass.Activity.STATIC;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Tree;
import static LWeb.Compiler.Parser.TokenType.*;
import LWeb.Common.Range.Range;
import LWeb.Common.Tree.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;


public class Parser {
    public enum TokenType{WHITESPACE, TEXT, NUMBER, OTHER, SL_COMMENT, ML_COMMENT, SQ_STRING, DQ_STRING, 
        OPEN_ANGLE, CLOSE_ANGLE, EQUALS, PERCENT, AMPERSANT,PERIOD, FWDSLASH, BCKSLASH, SINGLEQUOTE, DOUBLEQUOTE, STAR, EKMARK, DASH}
    public static final String[] auto_closing={"p","br","hr","img","!--","input","meta","link"};
    public static final Range rangeA_Z = new Range('A','Z');
    public static final Range rangea_z = new Range('a','z');
    public static final Range range0_9 = new Range('0','9');
    public static boolean firstChar(char c){return rangeA_Z.equals(c)||rangea_z.equals(c)||c=='_';}
    public static boolean nextChar(char c){return firstChar(c)||range0_9.equals(c)||c=='-';}
    public static boolean firstNum(char c){return range0_9.equals(c)||c=='-'||c=='.';}
    public static boolean nextNum(char c){return range0_9.equals(c)||c=='.';}
    
    public static void main(String[] args) {//test
        String s="<no<f> - . <!-- \"  gg\"\n" +
"<div ibn.in class='urnv %ioerbm &ierpbm nirm' uniin-o  = >  /* ///* \"z\\\"hh\" d onono='-3.2e-4hb\t\t\u3554ouerbn\"bmrbm3' >\n" +
"<*// */  --> < / uib>  // */div>";
        
        //s="ubn &ion %oh %uohj oh ouhjm &onm &onmkl uonmkk";
        //s="ubn &ion %oh %uohj oh ouhjm &onm &onmkl & uonmkk";
        //s=" uiun=oino ounm ljn= \"ibu\" onm = 'tz tzm' nim ==imk";
        //s="<zib  j.ubnm ibn.ob=oubn.in.khb.ib. ubn>";
        
        Pair<String[], TokenType[]> t = tokenize(s);
        sopl(ats(t.getFirst()));
        sopl(ats(t.getSecond()));
        t=group(t);
        sopl(ats(t.getFirst()));
        sopl(ats(t.getSecond()));
        ArrayList<ElementTag> doc = ell(t);
        sopl(doc);
        Tree<ElementTag> tree = buildTree(doc);
        sopl(tree);
        //sopl(Double.parseDouble("4.2e-4"));
        //System.out.println(findSubList());
        
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="generic">
    public static Pair<String[], TokenType[]> tokenize(String s){
        ArrayList<String> tk = new ArrayList<String>();
        ArrayList<TokenType> tt = new ArrayList<TokenType>();
        char c=0;
        boolean inPr=false;
        boolean inNm=false;
        boolean whSpc=false;
        boolean inNmf=false;
        String ac="";
        for (int i = 0; i < s.length(); i++) {
            c=s.charAt(i);
            if(!inPr&&firstChar(c)){
                if(whSpc)tt.add(WHITESPACE);
                if(inNm&&!".".equals(ac)&&!"-".equals(ac))tt.add(NUMBER);
                if(inNmf&&!".".equals(ac)&&!"-".equals(ac))tt.add(OTHER);
                if(".".equals(ac))tt.add(PERIOD);
                if("-".equals(ac))tt.add(DASH);
                if(whSpc||inNm){tk.add(ac);whSpc=false;inNm=false;ac="";inNmf=false;}
                inPr=true;
                ac+=c;
            }else if(inPr&&nextChar(c)){
                ac+=c;
            }else if(c==' '||c=='\t'||c=='\n'||c=='\r'){
                if(inNm&&!inNmf&&!".".equals(ac)&&!"-".equals(ac))tt.add(NUMBER);
                if(inPr)tt.add(TEXT);
                if(inNmf&&!".".equals(ac)&&!"-".equals(ac))tt.add(OTHER);
                if(".".equals(ac))tt.add(PERIOD);
                if("-".equals(ac))tt.add(DASH);
                if(inPr||inNm){tk.add(ac);inPr=false;inNm=false;ac="";inNmf=false;}
                whSpc=true;
                ac+=c;
            }else if(!inNm&&firstNum(c)){
                if(whSpc)tt.add(WHITESPACE);if(inPr)tt.add(TEXT);
                if(inPr||whSpc){tk.add(ac);inPr=false;whSpc=false;ac="";}
                inNm=true;
                inNmf=!range0_9.equals(c);
                ac+=c;
            }else if(inNm&&nextNum(c)){
                inNmf=false;
                ac+=c;
            }else{
                if(whSpc)tt.add(WHITESPACE);
                if(inNm&&!inNmf&&!".".equals(ac)&&!"-".equals(ac))tt.add(NUMBER);
                if(inPr)tt.add(TEXT);
                if(inNmf&&!".".equals(ac)&&!"-".equals(ac))tt.add(OTHER);
                if(".".equals(ac))tt.add(PERIOD);
                if("-".equals(ac))tt.add(DASH);
                if(!"".equals(ac))tk.add(ac);
                tk.add(""+c);
                TokenType tp = map(new Character[]{'<','>','=','%','&','.','/','\\','\'','"','*','!','-'},
                                   new TokenType[]{OPEN_ANGLE, CLOSE_ANGLE, EQUALS, PERCENT, AMPERSANT, PERIOD,
                                       FWDSLASH, BCKSLASH, SINGLEQUOTE, DOUBLEQUOTE, STAR, EKMARK, DASH},
                                               c, OTHER);
                tt.add(tp);
                whSpc=false;
                inNm=false;
                inNmf=false;
                inPr=false;
                ac="";
            }
        }
        if(!"".equals(ac)){
            tk.add(ac);
            if(whSpc)tt.add(WHITESPACE);if(inNm&&!inNmf)tt.add(NUMBER);if(inPr)tt.add(TEXT);if(inNmf)tt.add(OTHER);
        }
        return Pair(tk.toArray(new String[0]), tt.toArray(new TokenType[0]));
    }
    //group 23.4, e-4
    public static Pair<String[], TokenType[]> group(Pair<String[], TokenType[]> n){//ugly
        ArrayList<String> gr = new ArrayList<String>();
        ArrayList<TokenType> gt = new ArrayList<TokenType>();
        boolean dqStr=false;
        boolean sqStr=false;
        boolean slComm=false;
        int mlComm=0;
        boolean firstmlc=false;
        boolean jsclml=false;
        String sp="";
        String st="";
        for (int i = 0; i < n.getFirst().length; i++) {
            String sr = n.getFirst()[i];
            TokenType tr = n.getSecond()[i];
            //double quote strings
            if("\"".equals(sr)&&!"\\".equals(sp)
                    &&!sqStr&&!dqStr&&!slComm&&mlComm==0){
                if(!dqStr){if(!"".equals(st))gr.add(st);st="";}
                dqStr=true;
                st+=sr;
            }else if(dqStr&&!("\"".equals(sr)&&!"\\".equals(sp))){
                st+=sr;
            }else if(dqStr&&"\"".equals(sr)&&!"\\".equals(sp)){
                st+=sr;
                dqStr=false;
                if(!"".equals(st)){gr.add(st);gt.add(DQ_STRING);}
                st="";
            }
            //single qoute strings
            else if("'".equals(sr)&&!"\\".equals(sp)
                    &&!dqStr&&!sqStr&&!slComm&&mlComm==0){
                if(!sqStr){if(!"".equals(st))gr.add(st);st="";}
                sqStr=true;
                st+=sr;
            }else if(sqStr&&!("'".equals(sr)&&!"\\".equals(sp))){
                st+=sr;
            }else if(sqStr&&"'".equals(sr)&&!"\\".equals(sp)){
                st+=sr;
                sqStr=false;
                if(!"".equals(st)){gr.add(st);gt.add(SQ_STRING);}
                st="";
            }
            //single line comments
            else if("/".equals(sr)&&"/".equals(sp)
                    &&!dqStr&&!sqStr&&!slComm&&mlComm==0&&!jsclml){
                gr.remove(gr.size()-1);
                gt.remove(gt.size()-1);
                if(!slComm){if(!"".equals(st))gr.add(st);st="";}
                slComm=true;
                st+=sr+sp;
            }else if(slComm&&!("\n".equals(sr)||"\r".equals(sr))){
                st+=sr;
            }else if(slComm&&("\n".equals(sr)||"\r".equals(sr))){
                st+=sr;
                slComm=false;
                if(!"".equals(st)){gr.add(st);gt.add(SL_COMMENT);}
                st="";
            }
            //multi line comments
            else if("*".equals(sr)&&"/".equals(sp)
                    &&!dqStr&&!sqStr&&!slComm&&!jsclml){
                if(mlComm==0)gr.remove(gr.size()-1);
                if(mlComm==0)gt.remove(gt.size()-1);
                if(!"".equals(st)&&mlComm==0){gr.add(st);st="";}
                if(mlComm==0)st+=sp;
                mlComm+=1;
                firstmlc=true;
                st+=sr;
            }else if(mlComm!=0&&!("/".equals(sr)&&"*".equals(sp))){
                firstmlc=false;
                jsclml=false;
                st+=sr;
            }else if(!firstmlc&&mlComm!=0&&"/".equals(sr)&&"*".equals(sp)){
                st+=sr;
                jsclml=true;
                mlComm-=1;
                if(!"".equals(st)&&mlComm==0){gr.add(st);gt.add(ML_COMMENT);st="";}
            }else if(mlComm!=0){
                st+=sr;
            }else{
                jsclml=false;
                gr.add(sr);
                gt.add(tr);
            }
            
            sp=sr;
        }
        if(!"".equals(st))gr.add(st);
        if(dqStr) gt.add(DQ_STRING);
        if(sqStr) gt.add(SQ_STRING);
        if(slComm)gt.add(SL_COMMENT);
        if(mlComm!=0)gt.add(ML_COMMENT);
        return Pair(gr.toArray(new String[0]), gt.toArray(new TokenType[0]));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="document">
    public static ArrayList<ElementTag> ell(Pair<String[], TokenType[]> n){
        ArrayList<ElementTag> doc = new ArrayList<>();
        String sa[] = n.getFirst();
        TokenType ta[] = n.getSecond();
        int tal = n.getSecond().length;
        ElementTag et;
        for (int i = 0; i < tal; i++) {
            String sr = sa[i];
            TokenType tr = ta[i];
            //Comparator<TokenType> c = (TokenType t1, TokenType t2)->{return t1.equals(t2)?0:};
            int fca=inList(CLOSE_ANGLE, ta, i,tal);
            int foa=inList(OPEN_ANGLE, ta, i,fca, 0);
            //text before tag
            if(fca==-1||foa==-1){
                if(foa!=-1&&foa+3<tal&&ta[foa+1]==EKMARK&&ta[foa+2]==DASH&&ta[foa+3]==DASH){
                    et = new ElementTag("!--",false);
                    et.addAtribute("comment", flatten(Arrays.copyOfRange(sa, i,tal)));
                    doc.add(et);
                    break;
                }
                et = new ElementTag(flatten(Arrays.copyOfRange(sa, i,tal)),true);
                doc.add(et);
                break;
            }
            int coa=inList(OPEN_ANGLE, ta, i,fca);
            if(coa+3<tal&&ta[coa+1]==EKMARK&&ta[coa+2]==DASH&&ta[coa+3]==DASH){
                int eci=findSubList(ta, new TokenType[]{DASH,DASH,CLOSE_ANGLE},coa+3,tal);
                System.out.println(eci);
                et = new ElementTag("!--",false);
                et.addAtribute("comment", flatten(Arrays.copyOfRange(sa, coa+3,eci==-1?tal:eci)));
                doc.add(et);
                i=eci==-1?tal:eci+2;
                continue;
            }
            
            
            et = new ElementTag(flatten(Arrays.copyOfRange(sa, i,foa==-1?tal:foa)),true);
            doc.add(et);
            
            int tni=inList(TEXT, ta,foa,fca);
            et = new ElementTag(sa[tni],false);
            boolean cl=inList(FWDSLASH, ta,foa+1,tni)!=-1;
            if(!cl){
                //<editor-fold defaultstate="collapsed" desc="atributes">
                boolean t1=false;
                boolean t2=false;
                boolean eq=false;
                boolean ws=false;
                boolean pd=false;
                boolean da=false;
                String nm="";
                String dt="";
                for (int j = foa+2; j < fca; j++) {//<zb |unm=jnm nimk=jnkm>   <zb>|  
                    String ss = sa[j];
                    TokenType ts = ta[j];
                    //if(ta[j+1]==CLOSE_ANGLE)break;
                    if(ts==WHITESPACE){
                        if(da){
                            et.addAtribute(Pair(nm, dt.length()==0?null:dt));
                            nm="";
                            dt="";
                            pd=false;
                        }
                        da=false;
                        t2=false;
                        ws=true;
                    }
                    if(!eq&&!t2&&(ws||pd)&&ts==TEXT){
                        if(!eq&&t1){
                            et.addAtribute(Pair(nm, dt.length()==0?null:dt));
                            nm="";
                            dt="";
                            pd=false;
                        }
                        nm+=(pd?".":"")+ss;
                        pd=false;
                        t1=true;
                        ws=false;
                    }
                    if(!eq&&!t2&&t1&&ts==PERIOD){
                        pd=true;
                        t1=false;
                        ws=false;
                    }
                    if((pd||t1)&&ts==EQUALS){
                        eq=true;
                    }
                    if(eq&&(ts==SQ_STRING||ts==DQ_STRING||ts==TEXT||ts==NUMBER)){
                        dt+=ss;
                        t1=false;
                        eq=false;
                        pd=false;
                        if(ts==TEXT)t2=true;
                        da=true;
                    }
                    if(t2&&ts==PERIOD){
                        pd=true;
                    }
                    if(pd&&t2&&ts==TEXT){
                        dt+="."+ss;
                    }
                }
                if(nm.length()!=0)et.addAtribute(Pair(nm, dt.length()==0?null:dt));
                //</editor-fold>

                String cs=et.getAtribute("class");
                if(cs!=null){
                    System.out.println(cs);
                    et.addClass(cs);
                    et.removeAtribute("class");
                }
            }else{
                et.closingType=-1;
            }
            doc.add(et);
            
            
            //System.out.println(foa);
            //System.out.println(fca);
            //Arrays.binarySearch(ta, 0, inList(OPEN_ANGLE, ta), CLOSE_ANGLE, c);
            //if(tr==OPEN_ANGLE)lastOpenAngle=i;
            i=fca;
        }
        
    
        return doc;
    }
    
    public static Tree<ElementTag> buildTree(ArrayList<ElementTag> ell){
        Tree<ElementTag> tree = new Tree<>(new ElementTag(":root",false),true);
        Node<ElementTag> lnd=tree.root;
        for (int i = 0; i < ell.size(); i++) {
            ElementTag el = ell.get(i);
            if(el.textOnly){
                lnd.addNode(el);
                continue;
            }
            if(el.closingType!=-1){
                if(inList(el.tag,auto_closing)!=-1){
                    lnd.addNode(el);
                    Node<ElementTag> fsn=lnd;
                    while(fsn.getParent()!=null&&!fsn.data.tag.equals(el.tag)){
                        fsn=fsn.getParent();
                    }
                    if(fsn.getParent()==null){continue;}
                    lnd=fsn.getParent();
                }else{
                    lnd = lnd.addNode(el);
                }
                continue;
            }
            Node<ElementTag> fsn=lnd;
            while(fsn.getParent()!=null&&!fsn.data.tag.equals(el.tag)){
                fsn=fsn.getParent();
            }
            if(fsn.getParent()==null){continue;}
            lnd=fsn.getParent();
        }
        
        
        return tree;
    }
    
    //</editor-fold>
    
    
}
