package LWeb.Compiler;

import static LWeb.Common.Common.byteToFile;
import static LWeb.Common.Common.fileToString;
import static LWeb.Compiler.Parser.fullyParse;
import static LWeb.Compiler.Parser.genBytes;
import java.io.File;


public class LWebc {
    
    
    
    
    
    public static byte[] compile(File html, File css){
        String s=fileToString(html);
        String sc=fileToString(css);
        return genBytes(fullyParse(s, sc));
    }
    public static byte[] compile(String html, String css){
        return genBytes(fullyParse(html, css));
    }
    public static void compileToFile(File html, File css, File output){
        String s=fileToString(html);
        String sc=fileToString(css);
        byteToFile(genBytes(fullyParse(s, sc)), output);
    }
    public static void compileToFile(String html, String css, File output){
        byteToFile(genBytes(fullyParse(html, css)), output);
    }
}
