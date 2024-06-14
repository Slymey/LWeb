package LWeb.Compiler;

import LWeb.Compiler.Components.Attribute;
import LWeb.Compiler.Components.Attribute.AttributeExists;
import LWeb.Compiler.Components.Attribute.AttributeText;
import LWeb.Compiler.Components.Attribute.AttributeTextMatch;
import LWeb.Compiler.Components.Attribute.CheckType;
import static LWeb.Compiler.Components.Attribute.CheckType.*;
import static LWeb.Compiler.Components.Attribute.attr;
import static LWeb.Compiler.Components.Attribute.newAttribute;
import LWeb.Compiler.Components.Calculate;
import LWeb.Compiler.Components.Calculate.Operation;
import static LWeb.Compiler.Components.Calculate.Operation.ADD;
import static LWeb.Compiler.Components.Calculate.Operation.DIV;
import static LWeb.Compiler.Components.Calculate.Operation.MUL;
import static LWeb.Compiler.Components.Calculate.Operation.SUB;
import LWeb.Common.Color;
import static LWeb.Common.Color.*;
import LWeb.Common.Common;
import static LWeb.Common.Common.*;
import static LWeb.Common.Common.Troolean.False;
import static LWeb.Common.Common.Troolean.None;
import static LWeb.Common.Common.Troolean.True;
import LWeb.Compiler.Components.ElementTag;
import static LWeb.Compiler.Components.ClassList.Activity.*;
import LWeb.Common.Pair;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Tree;
import static LWeb.Compiler.Parser.TokenType.*;
import LWeb.Common.Range.Range;
import LWeb.Compiler.Components.Selector;
import LWeb.Compiler.Components.Selector.Combinators;
import static LWeb.Compiler.Components.Selector.Combinators.*;
import LWeb.Compiler.Components.Selector.*;
import LWeb.Compiler.Components.Selector.CondTrue;
import LWeb.Compiler.Components.Selector.Conditions;
import LWeb.Compiler.Components.StyleProperty.Property;
import static LWeb.Compiler.Components.StylePropertyList.getByName;
import LWeb.Common.Tree.Node;
import LWeb.Common.Triple;
import LWeb.Compiler.Components.AsmGroup;
import LWeb.Compiler.Components.AsmGroup.Instr;
import LWeb.Compiler.Components.ClassCondition;
import LWeb.Compiler.Components.TypeProvider;
import LWeb.Compiler.Components.TypeProvider.*;
import LWeb.Compiler.Parser.TokenType;
import static LWeb.Compiler.ParseTools.*;
import LWeb.Engine.Instr.RootP.HeaderP.*;
import LWeb.Engine.Instr.RootP.PaintP.FillP.Solid;
import LWeb.Engine.Instr.RootP.ResourceP.*;
import LWeb.Engine.Instr.RootP.*;
import LWeb.Engine.LWeb;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class Parser {
    public enum TokenType{ERR, WHITESPACE, TEXT, NUMBER, OTHER, SL_COMMENT, ML_COMMENT, SQ_STRING, DQ_STRING, 
        OPEN_ANGLE, CLOSE_ANGLE, EQUALS, PERCENT, AMPERSANT,PERIOD, FWDSLASH, BCKSLASH, SINGLEQUOTE, 
        DOUBLEQUOTE, STAR, EKMARK, DASH, SEMICOLON, COMMA, OPEN_CURLY, CLOSE_CURLY, OPEN_BRACKET, CLOSE_BRACKET, 
        OPEN_SQUARE, CLOSE_SQUARE, COLON, LINE, HAT, TILDAE, DOLAR, HASH, RX_STRING, PLUS}
    public static final String[] auto_closing={"p","br","hr","img","!--","input","meta","link"};
    public static final Range rangeA_Z = new Range('A','Z');
    public static final Range rangea_z = new Range('a','z');
    public static final Range range0_9 = new Range('0','9');
    public static boolean firstChar(char c){return rangeA_Z.equals(c)||rangea_z.equals(c)||c=='_';}
    public static boolean nextChar(char c){return firstChar(c)||range0_9.equals(c)||c=='-';}
    public static boolean firstNum(char c){return range0_9.equals(c)||c=='-'||c=='.';}
    public static boolean nextNum(char c){return range0_9.equals(c)||c=='.';}
    
    public static void main(String[] args) {//test
        String s="<body>\n" +
                "    <div id=\"title\" ><h1 class=\"trs\" trtxt=\"$title$\">The Bricks</h1></div>\n" +
                "    <div id=\"main\">\n" +
                "        \n" +
                "        <div id=\"left\">\n" +
                "            <div id=\"tocke-box\">\n" +
                "                <p>Toèke: </p>\n" +
                "                <mark id=\"tocke\" ib=\"uno\">0</mark>\n" +
                "            </div>\n" +
                "            <div id=\"brick-box\">\n" +
                "                <canvas id=\"canvas\" width=\"800\" height=\"800\"></canvas>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div id=\"right\">\n" +
                "                <button id=\"play\" class=\"trs\" trtxt=\"$play$\">Play</button>\n" +
                "               \n" +
                "                \n" +
                "        </div>\n" +
                "    </div>\n" +
                "    \n" +
                "    \n" +
                "</body>";
        //s="ubn &ion %oh %uohj oh ouhjm &onm &onmkl uonmkk";
        //s="ubn &ion %oh %uohj oh ouhjm &onm &onmkl & uonmkk";
        //s=" uiun=oino ounm ljn= \"ibu\" onm = 'tz tzm' nim ==imk";
        //s="<zib  j.ubnm ibn.ob=oubn.in.khb.ib. ubn>";
//        Pair<String[], TokenType[]> t = tokenize(s);
//        sopl(ats(t.getFirst()));
//        sopl(ats(t.getSecond()));
//        t=group(t,DQ_STRING,SQ_STRING, SL_COMMENT, ML_COMMENT);
//        sopl(ats(t.getFirst()));
//        sopl(ats(t.getSecond()));
//        ArrayList<ElementTag> doc = ell(t);
        //sopl(doc);
//        Tree<ElementTag> tree = buildTree(doc);
        //sopl(tree);
        //sopl(tree.root.findFirstNode((ElementTag e1)->{return "right".equals(e1.id)?0:-1;}, 0, 0));
        s=        "<div>"
                + "    "
                + "</div>";
        
        
        String sc=" #title{height:12px !priority:-2;"
                + "width: 23em !priority:-12;}"
                + "div:not(:has(#tocke)){"
                + " --var:izbn;"
                + " adf:rgba(24, 24, 24 / 50%);"
                + "}"
                + "root{"
                + " width:800px;"
                + " height:600px;"
                + " margin:256px;"
                + "}";
        
        sc =      "root{"
                + " width:800px;"
                + " height:600px;"
                + " margin:256px;"
                + "}"
                + "div{"
                + " width:100px;"
                + " height:80px;"
                + " background-color:#ff00ff;"
                + "}";
//        
//        setTMax(10);
//        Pair<String[], TokenType[]> tc = tokenize(sc);
//        sopl(ats(t.getFirst()));
//        sopl(ats(t.getSecond()));
//        tc=group(tc,DQ_STRING,SQ_STRING, /*RX_STRING,  handle later*/SL_COMMENT, ML_COMMENT);
//        sopl(ats(tc.getFirst()));
//        sopl(ats(tc.getSecond()));
//        ArrayList<Pair<ArrayList<Selector>, LinkedHashSet<Property>>> css = css(tc);
//        sopl(css);
        
        AsmGroup tree = fullyParse(s, sc);
        sopl(tree);
        //conver group to be with Pi
        
//        String s="<no<f> - . <!-- \"  gg\"\n" +
//"<div ibn.in class='urnv %ioerbm &ierpbm nirm' uniin-o  = >  /* ///* \"z\\\"hh\" d onono='-3.2e-4hb\t\t\u3554ouerbn\"bmrbm3' >\n" +
//"<*// */  --> < / uib>  */div> /kbn\\/u\\nv/i uobn";
////        String s="zbi \"onj ljn\\\" izb \" nomkp 'kb \' onm' nb'ub /vubknl\\/nomm/ zub//in\n"
////                + " uohn";
//        sopl(s);
//        sopl(s.length());
//        Pair<String[], TokenType[]> t = tokenize(s);
//        sopl(ats(t.getFirst()));
//        sopl(flatten(t.first).length());
//        sopl(ats(t.getSecond()));
//        //t=group(t);
//        t=group(t,DQ_STRING,SQ_STRING, SL_COMMENT, ML_COMMENT);
//        sopl(ats(t.getFirst()));
//        sopl(flatten(t.first).length());
//        sopl(ats(t.getSecond()));
        
        //Main.main(null);
        
        
        
    }
    
    
    public static AsmGroup fullyParse(String html, String css){
        return packAsm(genAsmTree(genLayout(mergeTreeCss(fullyParseHtml(html), fullyParseCss(css)))).root);
    }
    public static ArrayList<Pair<ArrayList<Selector>, LinkedHashSet<Property>>> fullyParseCss(String s){
        return css(group(tokenize(s),DQ_STRING,SQ_STRING, /*RX_STRING,  handle later*/SL_COMMENT, ML_COMMENT));
    }
    public static Tree<ElementTag> fullyParseHtml(String s){
        return buildTree(ell(group(tokenize(s),DQ_STRING,SQ_STRING, SL_COMMENT, ML_COMMENT)));
    }
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="generic">
    //<editor-fold defaultstate="collapsed" desc="tokenize">
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
                TokenType tp = map(new Character[]{'<','>','=','%','&','.','/','\\','\'','"','*','!','-',
                                                    ';',',','{','}','(',')','[',']',':','|','^','~','$',
                                                    '#','+'},
                                   new TokenType[]{OPEN_ANGLE, CLOSE_ANGLE, EQUALS, PERCENT, AMPERSANT, PERIOD,
                                       FWDSLASH, BCKSLASH, SINGLEQUOTE, DOUBLEQUOTE, STAR, EKMARK, DASH,
                                        SEMICOLON, COMMA, OPEN_CURLY, CLOSE_CURLY, OPEN_BRACKET, CLOSE_BRACKET, 
                                        OPEN_SQUARE, CLOSE_SQUARE, COLON, LINE, HAT, TILDAE, DOLAR, HASH, PLUS},
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
    //</editor-fold>
    //group 23.4, e-4
    public static Pair<String[], TokenType[]> group(Pair<String[], TokenType[]> n, TokenType... flags){//ugly
        ArrayList<String> gr = new ArrayList<String>();
        ArrayList<TokenType> gt = new ArrayList<TokenType>();
        boolean alSq=inList(SQ_STRING,flags)!=-1;
        boolean alDq=inList(DQ_STRING,flags)!=-1;
        boolean alSl=inList(SL_COMMENT,flags)!=-1;
        boolean alMl=inList(ML_COMMENT,flags)!=-1;
        boolean alRx=inList(RX_STRING,flags)!=-1;
        boolean dqStr=false;
        boolean sqStr=false;
        boolean rxStr=false;
        boolean slComm=false;
        int mlComm=0;
        boolean firstmlc=false;
        boolean jsclml=false;
        String sp="";
        String st="";
        PairedIter pi= new PairedIter(n.first,n.second,0,n.first.length);
        while(pi.has()){
            if(alDq&&   !(sqStr||slComm||mlComm!=0||rxStr)&&   pi.ifT(DOUBLEQUOTE)){
                if(pi.lastT(2)==BCKSLASH){
                    if(dqStr){st+="\"";}else{
                        gr.add("\\");
                        gt.add(BCKSLASH);
                        gr.add("\"");
                        gt.add(DOUBLEQUOTE);
                    }
                }else{if(dqStr){
                        dqStr=false;
                        st+="\"";
                        gr.add(st);
                        gt.add(DQ_STRING);
                        st="";
                    }else{dqStr=true;st="\"";}
                }continue;
            }
            if(alSq&&   !(dqStr||slComm||mlComm!=0||rxStr)&&   pi.ifT(SINGLEQUOTE)){
                if(pi.lastT(2)==BCKSLASH){
                    if(sqStr){st+="'";}else{
                        gr.add("\\");
                        gt.add(BCKSLASH);
                        gr.add("'");
                        gt.add(SINGLEQUOTE);
                    }
                }else{if(sqStr){
                        sqStr=false;
                        st+="'";
                        gr.add(st);
                        gt.add(SQ_STRING);
                        st="";
                    }else{sqStr=true;st="'";}
                }continue;
            }
            if(alRx&&   !(sqStr||dqStr||slComm||mlComm!=0)  ){
                if(pi.peekT()==FWDSLASH&&pi.peekT(1)!=FWDSLASH&&pi.peekT(1)!=STAR){
                    pi.inc();
                    if(pi.lastT(2)==BCKSLASH){
                        if(rxStr){st+="/";}else{
                            gr.add("/");
                            gt.add(FWDSLASH);
                        }
                    }else{if(rxStr){
                            rxStr=false;
                            st+="/";
                            gr.add(st);
                            gt.add(RX_STRING);
                            st="";
                        }else{rxStr=true;st="/";}
                    }continue;
                }
            }
            if(slComm&&pi.peekT()==WHITESPACE&&pi.peekS().contains("\n")){
                String ss=pi.nextS();
                int si=ss.indexOf("\n");
                st+=ss.substring(0,si+1);
                gr.add(st);
                gt.add(SL_COMMENT);
                ss=ss.substring(si+1, ss.length());
                if(!"".equals(ss)){
                    gr.add(ss);
                    gt.add(WHITESPACE);
                    st="";
                }
                slComm=false;
            }
            if(mlComm!=0&&pi.peekT()==STAR&&pi.peekT(1)==FWDSLASH){
                pi.inc();pi.inc();
                mlComm--;
                st+="*/";
                if(mlComm==0){
                    gr.add(st);
                    gt.add(ML_COMMENT);
                    st="";
                }
            }
            if(alSl&&   !(sqStr||dqStr||rxStr||mlComm!=0)&&    pi.peekT()==FWDSLASH&&pi.peekT(1)==FWDSLASH){
                pi.inc();pi.inc();
                if(slComm){
                    st+="//";
                }else{
                    slComm=true;
                    st="//";
                }
                continue;
            }
            if(alMl&&   !(sqStr||dqStr||rxStr||slComm)&&    pi.peekT()==FWDSLASH&&pi.peekT(1)==STAR){
                pi.inc();pi.inc();
                mlComm++;
                if(mlComm!=0){
                    st+="/*";
                }else{
                    st="/*";
                }
                continue;
            }
            if(dqStr||sqStr||slComm||mlComm!=0||rxStr){
                st+=pi.nextS();
            }else{
                gr.add(pi.peekS());
                gt.add(pi.nextT());
            }
        }
        if(!"".equals(st))gr.add(st);
        if(dqStr) gt.add(DQ_STRING);
        if(sqStr) gt.add(SQ_STRING);
        if(rxStr) gt.add(RX_STRING);
        if(slComm)gt.add(SL_COMMENT);
        if(mlComm!=0)gt.add(ML_COMMENT);
        return Pair(gr.toArray(new String[0]), gt.toArray(new TokenType[0]));
    }
    
    public static Pair<String[], TokenType[]> filter(Pair<String[], TokenType[]> p, TokenType... skips){
        ArrayList<String> sa = new ArrayList<>();
        ArrayList<TokenType> ta = new ArrayList<>();
        String[] s= p.first; TokenType[] t = p.second;
        int mx = Math.min(s.length, t.length);
        for (int i = 0; i < mx; i++) {
            if(inList(t[i], skips)!=-1){
                sa.add(s[i]);
                ta.add(t[i]);
            }
        }
        return Pair(sa.toArray(new String[0]), ta.toArray(new TokenType[0]));
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
                    et.addAtribute("comment", newAttribute("comment", flatten(Arrays.copyOfRange(sa, i,tal))));
                    doc.add(et);
                    break;
                }
                et = new ElementTag(flatten(Arrays.copyOfRange(sa, i,tal)),true);
                if(!onlyWhitespace(et.tag)){
                    doc.add(et);
                }
                break;
            }
            int coa=inList(OPEN_ANGLE, ta, i,fca);
            if(coa+3<tal&&ta[coa+1]==EKMARK&&ta[coa+2]==DASH&&ta[coa+3]==DASH){
                int eci=findSubList(ta, new TokenType[]{DASH,DASH,CLOSE_ANGLE},coa+3,tal);
//                System.out.println(eci);
                et = new ElementTag("!--",false);
                et.addAtribute("comment", newAttribute("comment", flatten(Arrays.copyOfRange(sa, coa+3,eci==-1?tal:eci))));
                doc.add(et);
                i=eci==-1?tal:eci+2;
                continue;
            }
            
            
            et = new ElementTag(flatten(Arrays.copyOfRange(sa, i,foa==-1?tal:foa)),true);
            if(!onlyWhitespace(et.tag)){
                doc.add(et);
            }
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
                            et.addAtribute(Pair(nm,newAttribute(nm, dt.length()==0?null:dt.substring(1, dt.length()-1))));
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
                            et.addAtribute(Pair(nm,newAttribute(nm, dt.length()==0?null:dt.substring(1, dt.length()-1))));
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
                if(nm.length()!=0)et.addAtribute(Pair(nm,newAttribute(nm, dt.length()==0?null:dt.substring(1, dt.length()-1))));
                //</editor-fold>

                AttributeText cs=(AttributeText) et.getAtribute("class");
                if(cs!=null){
                    //System.out.println("c: "+cs);
                    et.addClass(cs.data);
                    et.removeAtribute("class");
                }
                AttributeText id=(AttributeText)et.getAtribute("id");
                if(id!=null){ 
                    //System.out.println("i: "+id);
                    et.id=id.data;
                    et.removeAtribute("id");
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
        Tree<ElementTag> tree = new Tree<>(new ElementTag("root",false),true);
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
    
    //insert comment skips --------------------------------------------------------------------------------
    
    //<editor-fold defaultstate="collapsed" desc="style sheet">
    /*
        list of selectors with properties
            pair of selectors and properties
                list of direct selectors 
                ordered set of preperties with valuea
                    name of property
                    pair of property value and priority
                        value
                        priority
    */
    public static ArrayList<Pair<ArrayList<Selector>,LinkedHashSet<Property>>> css(Pair<String[], TokenType[]> n){
        ArrayList<Pair<ArrayList<Selector>,LinkedHashSet<Property>>> out = new ArrayList<>();
        String sa[] = n.getFirst();
        TokenType ta[] = n.getSecond();
        int tal = n.getSecond().length;
        PairedIter pi= new PairedIter(sa,ta,0,tal);
        while(pi.has()){terminator(pi.i);
            Pair<ArrayList<Selector>, LinkedHashSet<Property>> par = readSelAttrPair(pi);
            if(par!=null)
                out.add(par);
        }
//        System.out.println(lognm()+"huh");
        
        return out;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="merging">
    public static Tree<ElementTag> mergeTreeCss(Tree<ElementTag> tree, ArrayList<Pair<ArrayList<Selector>,LinkedHashSet<Property>>> css){
        for(Node<ElementTag> el:tree){
            if(el.data.textOnly||el.data.atributes.containsKey("comment"))continue;
            //System.out.println(lognm()+""+el);
            for(Pair<ArrayList<Selector>, LinkedHashSet<Property>> i:css){
                ArrayList<Selector> sel = i.first;
                boolean b=false;
                for(Selector sl:sel){
//                    if(!el.data.textOnly)System.out.println(lognm()+""+sl+"  "+b+ " "+el);
                    if(sl.validate(el,true)){b=true;break;}
                }
                if(b){
                    //if(!el.data.textOnly)System.out.println(lognm()+"  "+b);
                    el.data.style.add(Pair(null, i.second));
                }
            }
            //if(!el.data.textOnly)System.out.println(lognm()+""+el);
        }
        return tree;
    }
//</editor-fold>
    
    public static Tree<ElementTag> genLayout(Tree<ElementTag> tree){
        for(Node<ElementTag> el:tree){
            el.data.layout.put("parent", el.getParent()!=null?el.getParent().data:null);
            HashMap<String, Object> l = el.data.layout;
            for(Pair<ArrayList<ClassCondition>, LinkedHashSet<Property>> i:el.data.style){
                for(Property p:i.second){
                    if(p.from.layouts==null)continue;
                    p.from.layouts.accept(p, l);
                }
                break;//TODO
                
                
                
            }
        }
        return tree;
    }
    
      
    
    public static Tree<ElementTag> genAsmTree(Tree<ElementTag> tree){
        for(Node<ElementTag> nd:tree){
            ElementTag el = nd.data;
            HashMap<String, Object> l = el.layout;
            el.asm=new AsmGroup();
            if(el.tag.equals("root")){
                genAsmRoot(el);
                continue;
            }
            System.out.println(lognm()+""+el.tag+": "+l);
            
            int h = castpr(int.class, l.get("o-height"));
            int w = castpr(int.class, l.get("o-width"));
            Instr in = new Instr(Buffer.getBytes(1, w, h, 2),vpi(1,2), vpi(1,13));
            el.asm.inits.add(in);

            Color col = castpr(Color.class, l.get("background-color"));
            if(col!=null){
                in = new Instr(FlatColor.getBytes(col).at(3),vpi(3),vpi(5));
                el.asm.inits.add(in);
                in = new Instr(Solid.getBytes(1, 3),vpi(1,3), vpi(3, 7));
                el.asm.runs.add(in);
            }
            int x = castpr(int.class, l.get("o-left"));
            int y = castpr(int.class, l.get("o-top"));
            in = new Instr(Box.IntBox.getBytes(x, y, w, h).at(4),vpi(4),vpi(5));
            el.asm.inits.add(in);

            in = new Instr(BlendMode.noBlending().at(5),vpi(5),vpi(5));
            el.asm.inits.add(in);

            in = new Instr(Stack.getBytes(1, -1, 4, 5),vpi(1,-1,4,5),vpi(1,5,9,13));
            el.asm.enders.add(in);
            
            el.asm.inind = vpi();//
            el.asm.outind = vpi();
            
            /*
            Buffer.getBytes(0xf0000001, 75, 50, 0x0c000001),
            Solid.getBytes(0xf0000001, 0x000003),
            Box.IntBox.getBytes(25, 25, 75, 50).at(0x160000),
            Stack.getBytes(0xf0000001, 0xf0000000, 0x160000, 0x2b0000),
            */
        }
        
        
        return tree;
    }
    
    public static AsmGroup packAsm(Node<ElementTag> root){
        AsmGroup ag = new AsmGroup();
        int index=1;
        int cmax=0;
        for(Instr is:root.data.asm.inits){
            for(int id:is.ioints){
                if(id==1){
                    ag.parBuff = id;
                }
                if(id<0)continue;
                cmax=Math.max(cmax, id);
            }

        }
        ag.inits.addAll(root.data.asm.inits);

        for(Instr is:root.data.asm.runs){
            for(int id:is.ioints){
                if(id==1){
                    ag.parBuff = id;
                }
                if(id<0)continue;
                cmax=Math.max(cmax, id);
            }

        }
        ag.runs.addAll(root.data.asm.runs);

        for(Instr is:root.data.asm.enders){
            for(int id:is.ioints){
                if(id<0)continue;
                cmax=Math.max(cmax, id);
            }

        }
        ag.enders.addAll(0, root.data.asm.enders);
        index += cmax;
        cmax=0;
        
        for(Node<ElementTag> nd:root.children){
            
            AsmGroup ce = packAsm(nd);
            for(Instr is:ce.inits){
                for(int id:is.ioints){
                    if(id<0)continue;
                    cmax=Math.max(cmax, id);
                    is.replace(id, index+id);
                }
                
            }
            ag.inits.addAll(ce.inits);
            
            for(Instr is:ce.runs){
                for(int id:is.ioints){
                    if(id<0)continue;
                    cmax=Math.max(cmax, id);
                    is.replace(id, index+id);
                }
                
            }
            ag.runs.addAll(ce.runs);
            
            for(Instr is:ce.enders){
                for(int id:is.ioints){
                    if(id==-1){
                        is.replace(id, ag.parBuff);
                        continue;
                    }
                    if(id<0)continue;
                    cmax=Math.max(cmax, id);
                    is.replace(id, index+id);
                }
                
            }
            ag.enders.addAll(0, ce.enders);
            System.out.println(lognm()+""+cmax);
            index += cmax;
            cmax=0;
        }
        
        
        return ag;
    }
    
    public static byte[] genBytes(AsmGroup ag){
        ArrayList<Byte> al = new ArrayList<>();
        al.addAll(po(ltb(LWeb.MAGIC_BYTES)));
        
        
        
        return op(al);
    }
    
    //<editor-fold defaultstate="collapsed" desc="genAsmRoot">
    /*
    root - screen buf, window, resize callback, close callback, draw loop, 
    
    Position.IntPos.getBytes(800, 556).at(0x140000),//window dimentions
    Callable.getBytes("E", 0xff8000).at(0xe00000),//window event processor
    Window.getBytes(0xff8000, 256, 256, 0x140000, true, "A"),//window itself
    Condition.CallbackCondition.getBytes(0xe00000).at(0xd00000),//loop condition
    Screen.getBytes(0xff8001),//screen
    
    Loop.ConditionalLoop.getBytes(false, true, true, 17, 0, 0xd00000).at(0x0a0001),//draw loop dcl
    BufferPtr.getBytes(0xf0000000, 0x140000),//draw buffer
    
    //other draw stuff
    
    OutToScreen.getBytes(0xf0000000, 0xff8000, 0xff8001),//output to screen
    BranchLoop.getBytes(0x0a0001),//actual draw loop call
    End.getBytes()//end the runtime
    
    */
    
    public static void genAsmRoot(ElementTag el){
        HashMap<String, Object> l = el.layout;
        int h = castpr(int.class, l.get("o-height"));
        int w = castpr(int.class, l.get("o-width"));
        Instr in = new Instr(Position.IntPos.getBytes(w, h).at(7),vpi(7), vpi(5));
        el.asm.inits.add(in);
        in = new Instr(Callable.getBytes("E", 3).at(2),vpi(3, 2), vpi(18, 5));
        el.asm.inits.add(in);
        int x = castpr(int.class, l.get("o-left"));
        int y = castpr(int.class, l.get("o-top"));
        in = new Instr(Window.getBytes(3, x, y, 7, true, "A"),vpi(3, 7), vpi(2, 14));
        el.asm.inits.add(in);
        in = new Instr(Condition.CallbackCondition.getBytes(2).at(4),vpi(2, 4), vpi(10, 5));
        el.asm.inits.add(in);
        in = new Instr(Screen.getBytes(5),vpi(5), vpi(2));
        el.asm.inits.add(in);
        
        
        in = new Instr(Loop.ConditionalLoop.getBytes(false, true, true, -2, 0, 4).at(6),
                        vpi(-2,4, 6), vpi(10, 19, 5));
        el.asm.runs.add(in);
        in = new Instr(BufferPtr.getBytes(1, 7), vpi(1, 7), vpi(1, 5));
        el.asm.runs.add(in);

        
        in = new Instr(OutToScreen.getBytes(1, 3, 5),vpi(1, 3, 5),vpi(1, 5, 9));
        el.asm.enders.add(in);
        in = new Instr(BranchLoop.getBytes(6),vpi(6),vpi(1));
        el.asm.enders.add(in);
        in = new Instr(End.getBytes(3),vpi(3),vpi(1));
        el.asm.enders.add(in);
    }
    //</editor-fold>

}

//<editor-fold defaultstate="collapsed" desc="pi">
class PairedIter{
    String s[];
    TokenType t[];
    static TokenType skips[] = {SL_COMMENT, ML_COMMENT};
    int i=0;
    int max;
    public PairedIter(String s[], TokenType t[],int i, int max){
        this.s=s;
        this.t=t;
        this.i=i<0?0:i;
        this.max=max;
    }
    public boolean has(){
        return i<max;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters">
    public TokenType nextT(){
        if(i>=max)return ERR;
        return t[i++];
    }
    public String nextS(){
        if(i>=max)return null;
        return s[i++];
    }
    public TokenType lastT(){
        if(i-1<0)return ERR;
        return t[i-1];
    }
    public TokenType lastT(int n){
        if(i-n<0)return ERR;
        return t[i-n];
    }
    public TokenType peekT(){
        if(i>=max)return ERR;
        return t[i];
    }
    public TokenType peekT(int n){
        if(i+n>=max)return ERR;
        return t[i+n];
    }
    public String lastS(){
        if(i-1<0)return "";
        return s[i-1];
    }
    public String lastS(int n){
        if(i-n<0)return "";
        return s[i-n];
    }
    public String peekS(){
        if(i>=max)return null;
        return s[i];
    }
    public String peekS(int n){
        if(i+n>=max)return null;
        return s[i+n];
    }
    public boolean ifT(TokenType... t){
        if(inList(peekT(), t)!=-1){
            i++;
            return true;
        }
        return false;
    }
    public boolean ifS(String... s){
        if(inList(peekT(), s)!=-1){
            i++;
            return true;
        }
        return false;
    }
    //</editor-fold>
    
    public void inc(){
        i++;
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ParseTools">
class ParseTools{
    
    public static Pair<ArrayList<Selector>,LinkedHashSet<Property>> readSelAttrPair(PairedIter pi){
        ArrayList<Selector> primarySel=new ArrayList<>();
        primarySel=readGroup(pi);
        LinkedHashSet<Property> attr=new LinkedHashSet<>();
        attr = readAttributes(pi);
        if(attr.isEmpty()||primarySel.isEmpty()){return null;}
        return Pair(primarySel,attr);
    }
    
    //<editor-fold defaultstate="collapsed" desc="attributes">
    public static LinkedHashSet<Property> readAttributes(PairedIter pi){
        LinkedHashSet<Property> out = new LinkedHashSet<>();
        pi.ifT(OPEN_CURLY);
        while(pi.has()){terminator(pi.i);
            pi.ifT(WHITESPACE);
//            System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            Property[] atrr = readAttribute(pi);
            if(atrr!=null){
                for(Property atr:atrr){
                    if(atr!=null){
                        out.add(atr);
                    }
                }
            }
//            System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
//            System.out.println(pi.i);
            pi.ifT(WHITESPACE);
            if(pi.peekT()==CLOSE_CURLY)break;
        }
        
        return out;
    }
    public static Property[] readAttribute(PairedIter pi){
        boolean custom=false;
        int priority=0;
        Property[] pr = null;
        String s="";
        pi.ifT(SEMICOLON);
        if(pi.peekT()==DASH&&pi.peekT(1)==DASH){pi.inc();pi.inc();custom=true;}
        if(pi.peekT()==TEXT){
//            System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            s = pi.nextS();
            pi.ifT(WHITESPACE);
            if(pi.ifT(COLON)){
                pi.ifT(WHITESPACE);
            }
            int smc= inList(SEMICOLON, pi.t, pi.i, pi.max);
            int pri= inList(EKMARK, pi.t, pi.i, smc, 0);
//            System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS()+" "+smc+" "+pri);
            if(pri!=-1){
                pri=pri-pi.i;
                if(pi.peekT(pri)==EKMARK&&pi.peekT(pri+1)==TEXT&&"priority".equals(pi.peekS(pri+1))&&pi.peekT(pri+2)==COLON&&pi.peekT(pri+3)==NUMBER){
                    try{    priority = Integer.parseInt(pi.peekS(pri+3));        }catch(NumberFormatException e){
                        try{priority = (int)Double.parseDouble(pi.peekS(pri+3)); }catch(NumberFormatException e2){}
                    }
                }
            }
            pr = readProperty(pi, (custom?"--":"")+s, priority, smc);
            pi.ifT(WHITESPACE);
            if(smc!=-1)pi.i=smc;
//            if(pi.ifT(EKMARK)&&pi.peekT()==TEXT&&"priority".equals(pi.nextS())&&pi.ifT(COLON)&&pi.peekT()==NUMBER){
//                try{    priority = Integer.parseInt(pi.nextS());        }catch(NumberFormatException e){
//                    try{priority = (int)Double.parseDouble(pi.nextS()); }catch(NumberFormatException e2){}
//                }
//            }
        }
        return pr;
    }
    /*
    35                              number
    65.6                            number
    466px                           number, text
    #4fe                            hash, (number, text).len=3
    #26ea                           hash, (number, text).len=4
    #247fab                         hash, (number, text).len=6
    #247a7fab                       hash, (number, text).len=8
    none                            text
    inline                          text
    var(--dgb)                      text, brac, dash, dash, text, brac
    url(file://tbtebtb)
    url(/ftbtn/trb)
    url(ftbtn/etb)
    calc(23px + 4px)                text, brac, number, text, plus, number, text, brac
    rgba(35,36,53 / 30%)            text, brac, number, number, number, fwdslash, number, percent, text, brac
    rgba(0.3,0.5,0.8,0.4)           text, brac, number, number, number, number, text, brac
    */
    public static Property[] readProperty(PairedIter pi, String name, int p, int smc){
        Property[] out = null;
        ArrayList<TypeProvider> al = new ArrayList<>();
        while(pi.has()&&pi.peekT()!=EKMARK&&pi.peekT()!=SEMICOLON){terminator(pi.i);
            TypeProvider tp = readValue(pi, smc);
            if(tp!=null)al.add(tp);
            
            
            if(!pi.ifT(WHITESPACE))break;
        }
        pi.ifT(CLOSE_BRACKET);
        //stop at ! or ;
//            System.out.println(lognm()+"Ie: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        return getByName(name, al.toArray(new TypeProvider[0]),p);
    }
    
    public static TypeProvider readBracFunc(PairedIter pi, int smc){
        TypeProvider tp = null;
        ArrayList<TypeProvider> al = new ArrayList<>();
        String s = pi.nextS(); pi.inc();
        int cbc = inList(CLOSE_BRACKET, pi.t, smc, pi.max);
//        System.out.println(lognm()+"I-b: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        switch (s) {
            case "url":{
                try {tp=new PropUri(new URI(flatten(byi(pi.s, pi.i, cbc))));
                } catch (URISyntaxException ex) {}
                break;
            }
            case "rgba":case "rgb":{
                int a=0,r=0,g=0,b=0;
//                System.out.println(lognm()+"I-ro: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                while(pi.has()&&pi.peekT()!=CLOSE_BRACKET){terminator(pi.i);
//                    System.out.println(lognm()+"I-ri: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    pi.ifT(WHITESPACE);
//                    System.out.println(lognm()+"I-r1: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    if(pi.peekT()==NUMBER)
                        r = parseIntOr(pi.nextS(),0); 
//                    System.out.println(lognm()+"I-r2: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    pi.ifT(WHITESPACE, COMMA);
                    pi.ifT(WHITESPACE, COMMA);
//                    System.out.println(lognm()+"I-r3: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    if(pi.peekT()==NUMBER)
                        g = parseIntOr(pi.nextS(),0); 
//                    System.out.println(lognm()+"I-r4: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    pi.ifT(WHITESPACE, COMMA);
                    pi.ifT(WHITESPACE, COMMA);
//                    System.out.println(lognm()+"I-r5: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    if(pi.peekT()==NUMBER)
                        b = parseIntOr(pi.nextS(),0); 
//                    System.out.println(lognm()+"I-r6: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    pi.ifT(WHITESPACE);
//                    System.out.println(lognm()+"I-r7: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    if(pi.ifT(COMMA)){
                        pi.ifT(WHITESPACE);
                        if(pi.peekT()==NUMBER){
//                    System.out.println(lognm()+"I-r8: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                            a = (int)(parseFloatOr(pi.nextS(),0)*255); 
                        }
                    }else if(pi.ifT(FWDSLASH)){
//                    System.out.println(lognm()+"I-r9: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                        pi.ifT(WHITESPACE);
//                    System.out.println(lognm()+"I-r10: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                        if(pi.peekT()==NUMBER&&pi.peekT(1)==PERCENT){
                            a = (int)(parseFloatOr(pi.nextS(),0)*2.55);
                            pi.inc();
                        }
//                    System.out.println(lognm()+"I-r11: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    }
                    pi.inc();
                }
//                    System.out.println(lognm()+"I-r11: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                tp = new PropColor(new IntColor(a,r,g,b));
                break;
            }
            case "calc":{//read on loop -> calc(50% - 24px/2 - 4px/2);   olso handle RX_STRING
                tp = new PropCalc(readCalc(pi, smc));
                break;
            }
            default:{
                int bcc=0;
                ArrayList<TokenType> tt = new ArrayList<>();
                ArrayList<String> ss = new ArrayList<>();
                while(pi.has()){terminator(pi.i);
//                    System.out.println(lognm()+"I-u: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    if(pi.peekT()==OPEN_BRACKET)bcc++;
                    if(pi.peekT()==CLOSE_BRACKET)bcc--;
                    if(bcc<0)break;
                    tt.add(pi.peekT());
                    ss.add(pi.nextS());
                }
                tp = new PropUnknown(Pair(tt.toArray(na(pi.t,0)), ss.toArray(na(pi.s,0))));
                //throw new AssertionError(s);
                break;
            }
        }
        
        return tp;
    }
    
    public static TypeProvider readValue(PairedIter pi, int smc){
        TypeProvider tp = null;
        pi.ifT(WHITESPACE);
//        System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        if(pi.peekT()==TEXT&&pi.peekT(1)==OPEN_BRACKET){
//        System.out.println(lognm()+"Ib: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            tp = readBracFunc(pi, smc);
            //continue;
        }else if(pi.peekT()==NUMBER){
            tp = readNumber(pi);
//        System.out.println(lognm()+"In: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        }else if(pi.ifT(HASH)){
            String s="#";
            while(pi.has()&&(pi.peekT()==NUMBER||pi.peekT()==TEXT)&&s.length()<10){terminator(pi.i);
                s+=pi.nextS();
            }
            tp = new PropColor(Color(s));
        }else if(pi.peekT()==TEXT){
            tp = new PropList(pi.nextS());
        }
        return tp;
    }
    
    public static TypeProvider readNumber(PairedIter pi){
        TypeProvider tp;
        float a = parseFloatOr(pi.nextS(),0);
        if(pi.peekT()==TEXT){
            tp = new PropLength(a,pi.nextS());
        }else if(pi.ifT(PERCENT)){
            tp = new PropPercent(a/100);
        }else{
            tp = new PropScalar(a);
        }
        return tp;
    }
    
    public static Calculate readCalc(PairedIter pi, int smc){
//        System.out.println(lognm()+"I-c1: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        pi.ifT(WHITESPACE);
        Calculate out=null;
        Calculate tmp1=null;
        Calculate tmp2=null;
        if(pi.ifT(OPEN_BRACKET)){
            out = readCalc(pi, smc);
        }else{
            out = new Calculate(readValue(pi,smc));
        }
        while(pi.has()&&!pi.ifT(CLOSE_BRACKET)){terminator(pi.i);
            Operation op = readOp(pi, smc);
            if(pi.ifT(OPEN_BRACKET)){
                tmp2 = readCalc(pi, smc);
            }else{
                tmp2 = new Calculate(readValue(pi,smc));
            }
            if(op==MUL||op==DIV){
                if(tmp1==null){
                    out = new Calculate(out, op, tmp2);
                    tmp2 = null;
                }else{
                    tmp1 = new Calculate(tmp1, op, tmp2);
                    tmp2 = null;
                }
            }else if(op==ADD||op==SUB){
                if(tmp1==null){
                    tmp1 = tmp2;
                    tmp2 = null;
                }else{
                    out = new Calculate(out, op, tmp1);
                    tmp1 = tmp2;
                    tmp2 = null;
                }
            }
        }
        return out;
    }
    public static Operation readOp(PairedIter pi, int smc){
        Operation op;
        if(pi.peekT()==WHITESPACE&&(pi.peekT(1)==PLUS||pi.peekT(1)==DASH)&&pi.peekT(2)==WHITESPACE){
            pi.inc();
            if(pi.ifT(PLUS)){
                op=ADD;
            }else{
                op=SUB;
            }
            pi.inc();
        }else{
            pi.ifT(WHITESPACE);
            if(pi.ifT(FWDSLASH)){
                op=DIV;
            }else if(pi.ifT(STAR)){
                op=MUL;
            }else{
                op=Operation.NONE;
            }
        }
        pi.ifT(WHITESPACE);
        return op;
    }
    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Selector stuff">
    public static ArrayList<Selector> readGroup(PairedIter pi){
        ArrayList<Selector> out=new ArrayList<>();
        while(pi.has()){terminator(pi.i);
            out.add(readChain(pi, true));
//            System.out.println(lognm()+"I--: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
//            System.out.println(pi.i);
            pi.ifT(WHITESPACE);
            if(!pi.ifT(COMMA))break;
            pi.ifT(WHITESPACE);
        }
        while(pi.has()&&pi.peekT()!=OPEN_CURLY)pi.inc();
        return out;
    }
    
    public static Selector readChain(PairedIter pi, boolean fi){
        Selector sel = new Selector();
        ArrayList<ArrayList<Conditions>> tg=new ArrayList<>();
        pi.ifT(CLOSE_CURLY);
        boolean first=true;
        while(pi.has()){terminator(pi.i);
//            System.out.println(lognm()+"I-1: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            pi.ifT(WHITESPACE);
//            if(pi.peekT()==OPEN_BRACKET){
//                sel.addCondition(Pair(DESCENDANT, readBracketTarget(pi)));
//            }
            TokenType tt= pi.peekT();
            if(inList(tt, new TokenType[]{STAR,HAT,PLUS,TILDAE,TEXT,COLON,PERIOD,HASH,CLOSE_ANGLE})==-1)break;
            Combinators ct=map(new TokenType[]{CLOSE_ANGLE, PLUS, TILDAE},new Combinators[]{CHILD, NEXT, SUBSEQUENT}, tt, DESCENDANT);
            if(ct!=DESCENDANT)pi.inc();
            if(first&&fi)ct=SELF;
            first=false;
            pi.ifT(WHITESPACE);
            sel.addCondition(Pair( ct, readTarget(pi)));
        }
        return sel;
    }
    
    public static ArrayList<ArrayList<Conditions>> readBracketTarget(PairedIter pi){//:is, :nth
        ArrayList<ArrayList<Conditions>> out=new ArrayList<>();
        main:while(pi.has()){terminator(pi.i);
//            System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            out.add(readTarget(pi));
            while(pi.has()&&pi.peekT()!=COMMA){terminator(pi.i);
                if(pi.ifT(CLOSE_BRACKET)){
                    break main;
                }
                pi.inc();
            }
            pi.inc();
        }
        return out;
    }
    
    public static ArrayList<Conditions> readTarget(PairedIter pi){
        ArrayList<Conditions> out = new ArrayList<>();
        boolean txt=false;
        while(pi.has()){terminator(pi.i);
//            System.out.println(lognm()+"I-3: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            if(!txt&&pi.peekT()==TEXT){
                txt=true;
                out.add(new CondTag(pi.nextS()));
                continue;
            }
            if(pi.ifT(COLON)){
                if(pi.has()&&pi.ifT(COLON)){
                    if(pi.has()&&pi.peekT()==TEXT){
                        out.add(new CondPEll(pi.nextS()));
                    }
                }else{
//                    System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                    out.add(readColonCond(pi));
                    pi.ifT(CLOSE_BRACKET);
//            System.out.println(lognm()+"I-3i1: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                }
                continue;
            }
            if(pi.ifT(STAR)){
                out.add(new CondTrue());
                continue;
            }
            if(pi.ifT(PERIOD)){
                if(pi.has()&&pi.peekT()==TEXT){
                    out.add(new CondClass(pi.nextS()));
                }
                continue;
            }
            if(pi.ifT(HASH)){
                if(pi.has()&&pi.peekT()==TEXT){
                    out.add(new CondId(pi.nextS()));
                }
                continue;
            }
            if(pi.ifT(OPEN_SQUARE)){
//                System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                out.add(readAttrCond(pi));
                continue;
            }
//            System.out.println(lognm()+"I-3i: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            break;
        }
//            System.out.println(lognm()+"I-3e: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
        
        
        return out;
    }
    public static Conditions readAttrCond(PairedIter pi){
        Conditions out=Selector.CondTrue;
        String s="";
        String d="";
        Troolean r=None;
        CheckType t=NONE;
        boolean eq=false;
        boolean ck=false;
        boolean fi=false;
        while(pi.has()){
            pi.ifT(WHITESPACE);
//                System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            if(pi.ifT(CLOSE_SQUARE)){
                return new CondAttribute(newAttribute(s,d,t,r));
            }
            if(fi){pi.inc();continue;}
            if(!fi&&pi.peekT()==TEXT){
                s=pi.nextS();
                pi.ifT(WHITESPACE);
                t = map(new TokenType[]{EQUALS, TILDAE, LINE, HAT, DOLAR, STAR},
                    new CheckType[]{FULL, WORD, HYPHEN, PREF, SUFF, CONTAIN},
                    pi.peekT(), NONE);
                if(t==NONE){
                    fi=true;
                    continue;
                    //return new CondAttribute(newAttribute(s,d,t,r));
                }else{
                    pi.inc();
                }
//                System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS()+" "+s);
                pi.ifT(EQUALS);
                if(pi.peekT()==SQ_STRING||pi.peekT()==DQ_STRING||pi.peekT()==RX_STRING){
                    if(pi.peekT()==RX_STRING)t=REGEX;
                    d=pi.nextS();
                    d=d.substring(1, d.length()-1);
//                    System.out.println(lognm()+"I: "+pi.i+" "+pi.peekT()+" "+pi.peekS()+" "+d);
                    pi.ifT(WHITESPACE);
                    if(pi.peekT()==TEXT){
                        if(pi.peekS().equalsIgnoreCase("i")){
                            r=False;
                        }else if(pi.peekS().equalsIgnoreCase("s")){
                            r=True;
                        }
                    }
                    fi=true;
                    continue;
                    //return new CondAttribute(newAttribute(s,d,t,r));
                }else{
                    if(pi.peekT()==TEXT||pi.peekT()==NUMBER){
                        d=pi.nextS();
                        t=FULL;
                    }else{
                        t=NONE;
                    }
//                    System.err.println(lognm()+"--"+pi.i);
                    fi=true;
                    continue;
                    //return new CondAttribute(newAttribute(s,d,t,r));
                }
            }
        }
        return out;
    }
    public static Conditions readColonCond(PairedIter pi){
        Conditions out=Selector.CondTrue;
        while(pi.has()){terminator(pi.i);
//            System.out.println(lognm()+"I-c1: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
            if(pi.peekT()==TEXT){
                String s = pi.nextS();
                if(pi.has()&&pi.ifT(OPEN_BRACKET)){
                    pi.ifT(WHITESPACE);
                    //<editor-fold defaultstate="collapsed" desc="switch">
                    switch (s) {
                        case "nth-child":{
                            Pair<Integer, Integer> nm = readNums(pi);
                            ArrayList<ArrayList<Conditions>> tgs = readBracketTarget(pi);
                            return new CondNth(nm.first,nm.second,tgs);
                        }
                        case "nth-of-type":{
                            Pair<Integer, Integer> nm = readNums(pi);
                            ArrayList<ArrayList<Conditions>> tgs = readBracketTarget(pi);
                            return new CondNthType(nm.first,nm.second,tgs);
                        }
                        case "has":{
                            ArrayList<Selector> sel=new ArrayList<>();
                            while(pi.has()){terminator(pi.i);
                                sel.add(readChain(pi, false));
                                if(!pi.ifT(COMMA))break;
                                //while(pi.has()&&!pi.ifT(COMMA))pi.inc();
                            }
                            return new CondHas(sel);
                        }
                        case "qualify":{
                            ArrayList<Selector> sel=new ArrayList<>();
                            while(pi.has()){terminator(pi.i);
                                sel.add(readChain(pi, false));
                                if(!pi.ifT(COMMA))break;
                                //while(pi.has()&&!pi.ifT(COMMA))pi.inc();
                            }
                            return new CondQualify(sel);
                        }
                        case "is":{
                            return new CondIs(readBracketTarget(pi));
                        }
                        case "not":{
                            ArrayList<Selector> sel=new ArrayList<>();
                            while(pi.has()){terminator(pi.i);
                                sel.add(readChain(pi, true));
                                pi.ifT(WHITESPACE);
                                if(!pi.ifT(COMMA))break;
                                //while(pi.has()&&!pi.ifT(COMMA))pi.inc();
                            }
//            System.out.println(lognm()+"I-ce: "+pi.i+" "+pi.peekT()+" "+pi.peekS());
                            return new CondNot(sel);
                        }
                        case "contains":{
                            TokenType tt=pi.peekT();
                            String sl=pi.nextS();
                            boolean cs=true;
                            if((tt==SQ_STRING||tt==DQ_STRING)){
                                sl = sl.substring(1, sl.length()-1);
                                sl = sl.replaceAll("\\\\E", "\\\\\\\\E");
                                sl = "\\Q"+sl+"\\E";
                                pi.ifT(WHITESPACE);
                                if(pi.has()&&pi.peekT()==TEXT){
                                    cs=pi.nextS().equals("s");
                                }
                            }
                            if(tt==RX_STRING){
                                sl=sl.substring(1, sl.length()-1);
                                pi.ifT(WHITESPACE);
                                if(pi.has()&&pi.peekT()==TEXT){
                                    cs=pi.nextS().equals("s");
                                }
                            }
                            return new CondContains(sl,cs);
                        }
                    }
                    //</editor-fold>
                }else{
                    return new CondClass(":"+s);
                }
            }
            break;
        }
        
        
        return out;
    }
    public static Pair<Integer, Integer> readNums(PairedIter pi){
        int a=0;
        int b=0;
        boolean nums=false;
        while(pi.has()){terminator(pi.i);
            pi.ifT(WHITESPACE);
            //<editor-fold defaultstate="collapsed" desc="nums">
            if(pi.has()&&pi.peekT()==TEXT){
                if(pi.ifS("n")){
                    a=1;
                    pi.ifT(WHITESPACE);
                    TokenType tt=pi.nextT();
                    if(tt==PLUS||tt==DASH){
                        pi.ifT(WHITESPACE);
                        if(pi.has()&&pi.peekT()==NUMBER){
                            try{
                                int i=Integer.parseInt(pi.nextS());
                                b=(tt==PLUS)?i:-i;
                            }catch(Exception e){}
                        }else{
                            pi.inc();
                        }
                    }else{
                        pi.inc();
                    }
                }else{
                    pi.inc();
                }
            }
            if(pi.has()&&pi.ifT(DASH)){
                if(pi.has()&&pi.peekT()==TEXT){
                    if(pi.ifS("n")){
                        a=-1;
                        pi.ifT(WHITESPACE);
                        TokenType tt=pi.nextT();
                        if(tt==PLUS||tt==DASH){
                            pi.ifT(WHITESPACE);
                            if(pi.has()&&pi.peekT()==NUMBER){
                                try{
                                    int i=Integer.parseInt(pi.nextS());
                                    b=(tt==PLUS)?i:-i;
                                }catch(Exception e){}
                            }else{
                                pi.inc();
                            }
                        }else{
                            pi.inc();
                        }
                    }else{
                        pi.inc();
                    }
                }else{
                    pi.inc();
                }
            }
            if(pi.has()&&pi.peekT()==NUMBER){
                int i=0;
                try{
                    i=Integer.parseInt(pi.nextS());
                }catch(Exception e){}
                if(pi.has()&&pi.peekT()==TEXT){
                    if(pi.ifS("n")){
                        a=i;
                        pi.ifT(WHITESPACE);
                        TokenType tt=pi.nextT();
                        if(tt==PLUS||tt==DASH){
                            pi.ifT(WHITESPACE);
                            if(pi.has()&&pi.peekT()==NUMBER){
                                try{
                                    int i2=Integer.parseInt(pi.nextS());
                                    b=(tt==PLUS)?i2:-i2;
                                }catch(Exception e){}
                            }else{
                                pi.inc();
                            }
                        }else{
                            pi.inc();
                        }
                    }else{
                        b=i;
                        pi.inc();
                    }
                }else{
                    pi.inc();
                }
            }
            //</editor-fold>
            break;
        }
        return Pair(a,b);
    }
    //</editor-fold>
    
    
    
}
//</editor-fold>

