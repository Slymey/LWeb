
package Engine.Instr.RootP.ResourceP;


public class Resource {
    Class type = null;
    Object resource=null;
    public Resource(Class type, Object resource){
        this.type=type;
        this.resource=resource;
    }
    public Object get(){
        return resource;
    }
}
