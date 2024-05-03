package LWeb.Compiler.Components;

import LWeb.Common.Triple;
import LWeb.Compiler.Components.ClassList;
import LWeb.Compiler.Components.ClassList.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;


public class ClassCondition {   
    private boolean sign=true;//positive or negative condition
    //may be better if map<Ell, map<class, acc>>
    public final HashSet<Triple<ElementTag,ClassList,Activity>> classes = new HashSet<>();
    public final ArrayList<ClassCondition> subConditions = new ArrayList<>();
    
    
    public boolean validate(){
        for(Triple<ElementTag,ClassList,Activity> ec:classes){
            if(ec.first.type(ec.second)!=ec.third)return !sign;
        }
        if(subConditions.isEmpty())return sign;
        for(ClassCondition ec:subConditions){
            if(ec.validate())return sign;
        }
        return !sign;
    }
}
