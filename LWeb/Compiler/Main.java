package LWeb.Compiler;


import LWeb.Compiler.Components.ElementTag;
import LWeb.Compiler.Components.Selector;
import static LWeb.Common.Common.*;
import LWeb.Common.*;
import static LWeb.Compiler.Components.Attribute.newAttribute;
import static LWeb.Common.Pair.Pair;
import LWeb.Compiler.Components.StyleProperty.Property;
import LWeb.Compiler.Parser.TokenType;
import static LWeb.Compiler.Parser.TokenType.DQ_STRING;
import static LWeb.Compiler.Parser.TokenType.ML_COMMENT;
import static LWeb.Compiler.Parser.TokenType.RX_STRING;
import static LWeb.Compiler.Parser.TokenType.SL_COMMENT;
import static LWeb.Compiler.Parser.TokenType.SQ_STRING;
import static LWeb.Compiler.Parser.buildTree;
import static LWeb.Compiler.Parser.css;
import static LWeb.Compiler.Parser.ell;
import static LWeb.Compiler.Parser.fullyParse;
import static LWeb.Compiler.Parser.group;
import static LWeb.Compiler.Parser.tokenize;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String args[]){
        System.out.println("Start");
        
        File f1 = new File("e2.html");
        File f2 = new File("e.css");
        
        String s1=fileToString(f1);
        String s2=fileToString(f2);
        String ste=fileToString("large_test.html");
        String stc=fileToString("large_test.css");

//        Pair<String[], Parser.TokenType[]> ttk = group(tokenize(st),DQ_STRING,SQ_STRING, SL_COMMENT, ML_COMMENT);
//        ArrayList<ElementTag> doc = ell(ttk);
//        //System.out.println(doc);
//        Tree<ElementTag> tree = buildTree(doc);
//        System.out.println(tree.root.getSize());
//        String stree= ""+tree;
//        System.out.println(stree.length());
//        System.out.println(stree);
        //System.out.println(ats(ttk.getFirst()));
        //if(3<5)throw new Exception("etb");
//        Pair<String[], TokenType[]> ttk = group(tokenize(st),DQ_STRING,SQ_STRING, /*RX_STRING, handle later*/SL_COMMENT, ML_COMMENT);
//        System.out.println(ats(ttk.getFirst())+"\n\n"+ats(ttk.getSecond()));
//        //TODO: sorted list using specificity
//        ArrayList<Pair<ArrayList<Selector>, LinkedHashSet<Property>>> css = css(ttk);
//        System.out.println(css);
        //
        //System.out.println(Parser.fullyParseCss(stc));
        //System.out.println(Parser.fullyParseHtml(ste));
        //System.out.println(fullyParse(s1, s2));
        
        
        
        Tree<ElementTag> finl = fullyParse(s1, s2);
        System.out.println(lognm()+"Finished parsing");
        System.out.println(finl);

            
    }
    
    
    
    
    
    
    
    
    
    
    
}
