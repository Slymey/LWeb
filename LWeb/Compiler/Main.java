package Compiler;


import static Common.Common.*;
import Common.*;
import static Common.Pair.Pair;
import static Compiler.Parser.ell;
import static Compiler.Parser.group;
import static Compiler.Parser.tokenize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String args[]) {
        System.out.println("Start");
        
        File f1 = new File("e.html");
        File f2 = new File("e.css");
        
        String s1=fileToString(f1);
        String s2=fileToString(f2);

//        String st=fileToString("large_test.html");
//        Pair<String[], Parser.TokenType[]> ttk = group(tokenize(st));
//        ArrayList<ElementTag> doc = ell(ttk);
//        System.out.println(doc);
        //System.out.println(ats(ttk.getFirst()));



        Pair<String[], Parser.TokenType[]> etk = group(tokenize(s1));
        System.out.println(ats(etk.getFirst()));
        Pair<String[], Parser.TokenType[]> ctk = group(tokenize(s2));
        System.out.println(ats(ctk.getFirst()));
        //System.out.println(ats(ctk.getSecond()));


        //
        s1=readTillTarget(readTillTarget(s1,0,"","","//","\n","","","\n").getFirst(),0,"","","/*","*/").getFirst();
        s2=readTillTarget(readTillTarget(s2,0,"","","//","\n","","","\n").getFirst(),0,"","","/*","*/").getFirst();

        //new ElementNode("::root",false)
        ArrayList<ElementTag> document = new ArrayList<>();
        createElList(document,s1,0);
        sopl(document.toString());

        ArrayList<Pair<ArrayList<String>,HashMap<String,Pair<String,Integer>>>> styleSheet = new ArrayList<>();
        String scss=removeChars(removeChars(reduceChars(s2,' '),'\n'),'\t');
        sopl("\n"+scss);
        createCssList(styleSheet,scss,0);
        sopl(styleSheet.toString());
            
    }
    //fix split with coments to proprely treat nested eg "ff(buvr"gh) -> ??
    public static int createCssList(ArrayList<Pair<ArrayList<String>,HashMap<String,Pair<String,Integer>>>> list, String s, int offset){
        int i = offset;
        for(;i<s.length();i++){//implement nested selectors
            Triple<String, Integer, Integer> sS=readTillTarget(s,i,"{","","","");
            i+=sS.getSecond()+1;
            Triple<String, Integer, Integer> sP=readTillTarget(s,i,"}","","","");
            i+=sP.getSecond();
            char cso[]={'(','[','"','\''};
            char csc[]={')',']','"','\''};
            Stack<Character> stack= new Stack<>();
            String selectorsArray[] = splitWithComents(sS.getFirst(),0,(String st,int ind)->{
                if(st.charAt(ind)==',')
                    return Pair(true,1);
                return Pair(false,0);
            },(String st,int ind)->{
                char cv = st.charAt(ind);
                int b = inList(cv,cso);
                //sopl(cv);
                if(b>=0)stack.push(cso[b]);
                return Pair(b>=0,(b>=0)?1:0);
            },(String st,int ind)->{
                char cv = st.charAt(ind);
                //sopl(cv);
                boolean b=false;
                if(!stack.empty()){
                    b = csc[inList(stack.peek(),cso)]==cv;
                    if(b)stack.pop();
                }
                return Pair(b,(b)?1:0);
            });
            ArrayList<String> selectors=new ArrayList<>();
            selectors.addAll(Arrays.asList(selectorsArray));
            
            HashMap<String,Pair<String,Integer>> properties=new HashMap<>();
            String pa[]=splitWithCStrings(sP.getFirst(),';');
            for(String sp:pa){
                if(sp.length()==0)continue;
                int j=0;
                if(sp.charAt(0)==' ')j=1;
                
                Triple<String, Integer, Integer> sPn=readTillTarget(sp,j,":","","","");
                j+=sPn.getSecond();
                String pn=readWhileValid(sPn.getFirst(),0, Parser::nextChar).getFirst(); 
                
                int eksIndex=sp.lastIndexOf("!");
                Integer sPi=0;
                String sPv="";
                if(eksIndex>=0){
                    String sPp=sp.substring(eksIndex, sp.length());
                    boolean prThere = isSubStringThere(sp,eksIndex,"!priority:");
                    if(prThere){
                        sPi=parseInt(sPp.substring(10)).getFirst();
                    }
                    sPv=sp.substring(j+1, eksIndex);
                }else{
                    sPv=sp.substring(j+1);
                }
                
                properties.put(pn, Pair(sPv,sPi));
            }
            list.add(Pair(selectors,properties));
        }
        return i;
    }
    
    //reimplement with reduceChars
    public static int createElList(ArrayList<ElementTag> list, String s, int offset){
        Predicate<Character> validFirst = Parser::firstChar;
        boolean [] cFlag = {false};
        Pair<String,Integer> temp;
        ElementTag node;
        Pair<String,Integer> st;
        int i=offset;
        for(;i<s.length();i++){
            Triple<String,Integer,?> s1 = readTillTarget(s,i,"<","","","");
            Triple<String,Integer,Integer> s2 = readTillTarget(s,i,">","<","","",false);
            if(s2.getThird()>0)
                list.add(new ElementTag(s.substring(i, i+s2.getThird()),true));
            i+=s2.getSecond();
            
            //use rtt with predicates to seperate atributes before adding node
            String se=s2.getFirst();
            
            if(se.charAt(0)=='/'){
                st=readWhileValidWithFirst(se,1);
                node=new ElementTag(st.getFirst(),false);
                node.closingType=-1;
                list.add(node);
                int j=st.getSecond();
                continue;
            }else{
                st=readWhileValidWithFirst(se,0);
                node=new ElementTag(st.getFirst(),false);
            }
            int j=st.getSecond();
            for(;j<se.length();j++){
                cFlag[0]=false;
                int sk = countInvalids(se,j,validFirst.negate());
                //sopl(" se:"+se);
                j+=sk;
                temp=readWhileValidWithFirst(se,j,validFirst,(Character ch)->{
                    return rangeA_Z.equals((char)ch)||rangea_z.equals((char)ch)||range0_9.equals((char)ch)||ch=='-'||ch=='_';
                });
                //sopl(" tm:"+temp);
                String at=temp.getFirst();
                //sopl(" at:"+at);
                j+=temp.getSecond();
                sk = countInvalids(se,j,(Character ch)->{
                    if(ch=='=')cFlag[0]=true;
                    //sop(ch);
                    return ch==' '||ch=='=';
                });
                //sopl(" ls:"+sk+"  "+j+"   "+cFlag[0]+"   "+se.length());
                j+=sk-1;
                if(!cFlag[0]){
                    if(at.length()!=0)
                        node.addAtribute(at, null);
                    continue;
                }
                Triple<String,Integer,?> av;
                if(se.charAt(j)=='"'){
                    av = readTillTarget(se,j,"\"","","","", false);
                }else if(se.charAt(j)=='\''){
                    av = readTillTarget(se,j,"'","","","", false);
                }else{
                    av = readTillTarget(se,j+1," ","","","", false);
                }
                //sopl("  a:"+j+"  "+av);
                j+=av.getSecond();
                node.addAtribute(at, av.getFirst());
            }
            //return with closing tag
            list.add(node);
            //i=createTree(node,s,i);
            
            //sopl(""+i+" "+node);
        }
        return i;
    }
    
    
    
    
    
    
    public static ArrayList d1(ArrayList<String> in){
        ArrayList<Pair<String,HashMap<String,String>>> data=new ArrayList<>();
        for(int i=0;i<in.size();i++){
            String it=in.get(i);
            char ty=it.charAt(0);
            //it=it.substring(1);
            /*int a=it.indexOf(" ",1);
            String sn=it.substring(1, (a==-1)?it.length():a);
            it=it.substring(sn.length());
            */
            
            switch(ty){
                case ' ':
                    //System.out.println(it);
                    data.add(new Pair(it.substring(1),null));
                    break;
                case '<':
                    int a=it.indexOf(" ",1);
                    String sn=it.substring(1, (a==-1)?it.length():a);
                    it=it.substring(sn.length()+1);
                    //System.out.println(it);
                    String ts="";
                    boolean sps=false;
                    for(int j=0;j<it.length();j++){
                        char c=it.charAt(j);
                        if(c<='z'&&c>='a'||c<='Z'&&c>='A'||c<='9'&&c>='0'){
                            if(sps)ts="";
                            sps=false;
                        }else{sps=true;}
                        if(!sps){
                            ts+=c;
                        }//                     ************ =   ***********
                        
                        
                    }
                    break;
                case '"':
                    
                    
                    break;
            }
            
            
            
            //System.out.println(it+"    "+a+"    "+((a==-1)?it.length():a));
        }
        
        
        
        System.out.println(in);
        System.out.println(data);
        return data;
    }
    
    // <editor-fold defaultstate="collapsed" desc="prev stuf">
    public static void t1(BufferedReader br) throws IOException{
        String inLine="";
        ArrayList<Pair<String,HashMap<String,String>>> data=new ArrayList<>();
        int ln=0;
        boolean read[]={false,false,false,false,false,false,false,false,true,false,false};
        String s="";
        while(br.ready()){
            inLine=br.readLine();
            for(int i=0;i<inLine.length();i++){
                boolean st[] = Arrays.copyOf(read, read.length);
                char c= inLine.charAt(i);
                //data.add(new Pair("ff",new HashMap()));
                if(read[8]){
                    if(c==' '||c=='\t'||c=='\n'){
                        st[10]=true;
                    }else{
                        st[8]=false;
                    }
                }
                if(!(c==' '||c=='\t'||c=='\n')){
                    st[10]=false;
                }
                if(c=='<'){
                    st[9]=true;
                    st[0]=false;
                    if(s!=""){
                        data.add(new Pair(s,null));
                        s="";
                    }
                }
                if(read[8]&&c=='"'){
                    String stmp[] = {inLine};
                    int indtmp[] = {i};
                    s=fromJsonStringWithBr(br,stmp,indtmp);
                    i=indtmp[0];
                    inLine=stmp[0];
                    data.add(new Pair(s,null));
                    s="";
                }
                if(read[9]&&!read[1]&&(c>='a'&&c<='z'||c>='A'&&c<='Z')){
                    st[0]=true;
                    st[1]=true;
                    st[9]=false;
                    s+=c;
                }
                if(read[9]&&!read[5]&&c=='!'){
                    st[0]=true;
                    st[5]=true;
                    st[9]=false;
                    s+=c;
                }

                if(read[5]&&"!--".equals(s)){
                    st[5]=false;
                    st[7]=true;
                }
                if(!read[7]&&(c==' '||c=='\t'||c=='\n')&&!read[10]){
                    st[10]=true;
                    st[0]=false;
                    if(s=="!DOCTYPE"){
                        st[6]=true;
                    }
                    data.add(new Pair(s,null));
                    s="";
                }
                if(c=='>'&&read[7]&&s.charAt(s.length()-1)=='-'&&s.charAt(s.length()-2)=='-'){
                    st[0]=false;
                    read[0]=false;
                    st[7]=false;
                    data.add(new Pair(s.substring(0, s.length()-2),null));
                    s="";
                }
                if(read[0]&&!st[9]){
                    s+=c;
                }
                System.out.println(Arrays.toString(st)+"\n"+Arrays.toString(st)+"\n  "+i+"  "+ln+"    "+s);
                read=st;
            }


            ln++;
        }



        System.out.println(data);
        
    }
    
    private static String fromJsonStringWithBr(BufferedReader br,String s[], int ind[]){
        String data="";//****************************************************************************************
        ind[0]++;
        boolean bsl=false;
        while(true){
            for(;ind[0]<s[0].length();ind[0]++){  
                //System.out.println("utvzbn   "+s[0].charAt(ind[0])+"    "+ind[0]+"    "+data);
                if(s[0].charAt(ind[0])=='\\'&&!bsl){
                    bsl=true;
                    continue;
                }
                if(s[0].charAt(ind[0])=='"'&&!bsl){/*System.out.println("tndndtn");*/bsl=false;return data;}
                if(bsl){
                    bsl=false;
                    switch(s[0].charAt(ind[0])){
                        case 'n':
                            data+="\n";break;
                        case 't':
                            data+="\t";break;
                        case 'r':
                            data+="\r";break;
                        case '\\':
                            data+="\\";break;
                        case '/':
                            data+="/";break;
                        case 'b':
                            data+="\b";break;
                        case 'f':
                            data+="\f";break;
                        case '"':
                            data+="\"";break;
                    }
                }else{
                    if(s[0].charAt(ind[0])!='\\'){
                        data+=s[0].charAt(ind[0]);
                    }
                }
            }
            try {
                if(!br.ready()){break;}
                s[0]=br.readLine();
                ind[0]=0;
            } catch (IOException ex) {break;}
        }
        //System.out.println("js e "+ind[0]+"  "+data);
        return data;
    }

    public static void t2(String s) {
        ArrayList<Pair<String,HashMap<String,String>>> data=new ArrayList<>();
        HashMap<String,String> hm=null;
        String sp[] = s.split("<");
        System.out.println(Arrays.toString(sp));
        
        for(int ind = 0;ind<sp.length;ind++){
            if(sp[ind].startsWith("!DOCTYPE")){
                String dt="";//sp[ind].substring(8);
                for(;ind<sp.length;ind++){
                    String sl=sp[ind]+"a";
                    String sp2[]=sl.split(">");
                    System.out.println(Arrays.toString(sp2)+"    "+ind);
                    if(sp2.length>1){
                        String st1="";
                        for(int i=1;i<sp2.length-1;i++){
                            st1+=sp2[i];
                        }
                        dt+=sp2[0];
                        sp[ind]=st1;
                        break;
                    }else{
                        dt+=sp2[0].substring(0, sp2[0].length()-1);
                    }
                }
                hm = new HashMap<>();
                hm.put("DOCTYPE",dt.substring(8));
                data.add(new Pair("!DOCTYPE",hm));
                hm=null;
            }else if(sp[ind].startsWith("!--")){
                String dt="";//sp[ind].substring(8);
                for(;ind<sp.length;ind++){
                    String sl=sp[ind]+"a";
                    String sp2[]=sl.split("-->");
                    System.out.println(Arrays.toString(sp2)+"    "+ind);
                    if(sp2.length>1){
                        String st1="";
                        for(int i=1;i<sp2.length-1;i++){
                            st1+=sp2[i];
                        }
                        dt+=sp2[0];
                        sp[ind]=st1;
                        break;
                    }else{
                        dt+=sp2[0].substring(0, sp2[0].length()-1);
                    }
                }
                hm = new HashMap<>();
                hm.put("!--",dt.substring(3,dt.length()-2));
                data.add(new Pair("!--",hm));
                hm=null;
            }else{
                String dt="";//sp[ind].substring(8);
                for(;ind<sp.length;ind++){
                    String sl=sp[ind]+"a";
                    String sp2[]=sl.split(">");
                    //System.out.println(Arrays.toString(sp2)+"    "+ind);
                    if(sp2.length>1){
                        String st1="";
                        for(int i=1;i<sp2.length-1;i++){
                            st1+=sp2[i];
                        }
                        dt+=sp2[0];
                        sp[ind]=st1;
                        break;
                    }else{
                        dt+=sp2[0].substring(0, sp2[0].length()-1);
                    }
                }
                System.out.println(dt);
                
                
                data.add(new Pair(sp[ind],null));//split by space save first, ignore empty, save val,=,"->merge save, repete
            }
            
            
            
        }
        
        
        System.out.println(data+"   ");
        System.out.println(Arrays.toString(sp));
    }
    
    public static ArrayList t3(BufferedReader br) throws IOException{
        boolean quo=false;
        boolean tag=false;
        boolean com=false;
        ArrayList<String> str = new ArrayList<>();
        String s="";
        char c=(char)0;
        char cb=(char)0;
        int d=0;
        while(br.ready()){
            String in=br.readLine()+" ";
            for(int i=0;i<in.length();i++){
                c = in.charAt(i);
                if(c=='"'&&!tag){
                    str.add(" "+s);
                    String sa[]={in};int ind[]={i};
                    s=getJsonStringWithBr(br,sa,ind);
                    in=sa[0];i=ind[0];
                    //System.out.println("bver   "+s+"   "+i+"   "+c+"   "+quo+"   "+tag+"   "+com+"   "+d);
                    str.add("\""+s);
                    s="";
                }else{
                    if(quo&&c!='<'){
                        tag=true;
                        quo=false;
                        str.add(" "+s.substring(0,s.length()-1));
                        s="";
                    }else{
                        if(!tag)s+=c;
                    }
                    if(c=='<'&&!quo&&!tag){
                        quo=true;
                    }
                    if(tag){
                        if(d==3&&"!--".equals(s)){
                            com=true;
                            d=0;
                        }
                        d++;
                        if(!com){
                            if(c=='>'){
                                tag=false;
                                d=0;
                                str.add("<"+s);
                                s="";
                                cb=c;
                            }else{
                                if(c=='"'){
                                    String sa[]={in};int ind[]={i};
                                    //System.out.println("bver   "+s+"   "+i+"   "+c+"   "+quo+"   "+tag+"   "+in);
                                    s+='"'+getJsonStringWithBr(br,sa,ind)+'"';

                                    in=sa[0];i=ind[0];
                                    //System.out.println("bver   "+s+"   "+i+"   "+c+"   "+quo+"   "+tag+"   "+in);
                                }else{
                                    s+=c;
                                }
                            }
                        }else{
                            try{
                                if(c=='>'&&s.charAt(s.length()-1)=='-'&&s.charAt(s.length()-2)=='-'){
                                    tag=false;
                                    com=false;
                                    d=0;
                                    str.add("!"+s.substring(3,s.length()-2));
                                    s="";
                                    cb=c;
                                }else{
                                    s+=c;
                                }
                            }catch(IndexOutOfBoundsException e){s+=c;}
                        }
                    }
                }
                
                //System.out.println(s+"   "+i+"   "+c+"   "+quo+"   "+tag+"   "+com+"   "+d);
                cb=c;
            }
            
        }
        //str.set(str.size()-1, str.get(str.size()-1).substring(0, str.get(str.size()-1).length()-1));
        /*for(String sr:str){
            System.out.println(sr);
        }*/
        return str;
    }
    
    public static String getBracketContents(String sp[],int indr[]){
        int ind=indr[0];
        String dt="";//sp[ind].substring(8);
        for(;ind<sp.length;ind++){
            String sl=sp[ind]+"a";
            String sp2[]=sl.split(">");
            //System.out.println(Arrays.toString(sp2)+"    "+ind);
            if(sp2.length>1){
                String st1="";
                for(int i=1;i<sp2.length-1;i++){
                    st1+=sp2[i];
                }
                dt+=sp2[0];
                sp[ind]=st1;
                break;
            }else{
                dt+=sp2[0].substring(0, sp2[0].length()-1);
            }
        }
        indr[0]=ind;
        return dt;
    }
    
    private static String getJsonStringWithBr(BufferedReader br,String s[], int ind[]){
        String data="";//****************************************************************************************
        ind[0]++;
        boolean bsl=false;
        while(true){
            for(;ind[0]<s[0].length();ind[0]++){  
                //System.out.println("utvzbn   "+s[0].charAt(ind[0])+"    "+ind[0]+"    "+data);
                if(s[0].charAt(ind[0])=='\\'&&!bsl){
                    bsl=true;
                    continue;
                }
                if(s[0].charAt(ind[0])=='"'&&!bsl){/*System.out.println("tndndtn");*/bsl=false;return data;}
                if(bsl){
                    data+='\\';
                    bsl=false;
                }
                data+=s[0].charAt(ind[0]);
            }
            try {
                if(!br.ready()){break;}
                s[0]=br.readLine();
                ind[0]=0;
            } catch (IOException ex) {break;}
        }
        //System.out.println("js e "+ind[0]+"  "+data);
        return data;
    }
    
    
    private static String getJsonString(String s, int ind[]){
        String data="";//****************************************************************************************
        ind[0]++;
        boolean bsl=false;
        for(;ind[0]<s.length();ind[0]++){  
            //System.out.println("utvzbn   "+s[0].charAt(ind[0])+"    "+ind[0]+"    "+data);
            if(s.charAt(ind[0])=='\\'&&!bsl){
                bsl=true;
                continue;
            }
            if(s.charAt(ind[0])=='"'&&!bsl){/*System.out.println("tndndtn");*/bsl=false;return data;}
            if(bsl){
                data+='\\';
                bsl=false;
            }
            data+=s.charAt(ind[0]);
        }
        //System.out.println("js e "+ind[0]+"  "+data);
        return data;
    }

    private static String fromJsonString(String s, int ind[]){
        String data="";
        ind[0]++;
        int i =0;
        for(;ind[0]<s.length();ind[0]++){  
            if(s.charAt(ind[0])=='"'&&s.charAt(ind[0]-1)!='\\'){break;}
            i++;
            if(s.charAt(ind[0]-1)=='\\'){
                switch(s.charAt(ind[0])){
                    case 'n':
                        data+="\n";break;
                    case 't':
                        data+="\t";break;
                    case 'r':
                        data+="\r";break;
                    case '\\':
                        data+="\\";break;
                    case '/':
                        data+="/";break;
                    case 'b':
                        data+="\b";break;
                    case 'f':
                        data+="\f";break;
                    case '"':
                        data+="\"";break;
                }
            }else{
                if(s.charAt(ind[0])!='\\'){
                    data+=s.charAt(ind[0]);
                }
            }
        }
        
        //System.out.println("js e "+ind[0]+"  "+data);
        return data;
    }
    
    // </editor-fold>
    
    
    
    
}
