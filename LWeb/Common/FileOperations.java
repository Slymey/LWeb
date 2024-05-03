package LWeb.Common;

import static LWeb.Common.Common.lognm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileOperations {
    private String path="";
    
    public static void main(String[] args) {
        
        
        System.out.println(lognm()+""+loadAsString("src/LWeb/Engine/Shaders/sh1.glsl"));
    }
    public FileOperations(String path){
        if(path.charAt(path.length()-1)!='/')path+='/';
        this.path=path;
    }
    
    public static String loadAsString(String file){
        String out="";
        try{
            File fl=new File(file);
            if(fl.exists()){
                FileReader br = new FileReader(fl);
                char cbuf[] = new char[(int)fl.length()];
                int r=br.read(cbuf);
                if(r!=-1){
                    out = new String(cbuf);
                }
            }else
                System.out.println(lognm()+" no file: "+file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out;
    }
    
    public String load(String file){
        String out="";
        try{
            File fl=new File(path+file);
            if(fl.exists()){
                FileReader br = new FileReader(fl);
                char cbuf[] = new char[(int)fl.length()];
                int r=br.read(cbuf);
                if(r!=-1){
                    out = new String(cbuf);
                }
            }else
                System.out.println(lognm()+" no file: "+path+file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out;
    }
    
}
