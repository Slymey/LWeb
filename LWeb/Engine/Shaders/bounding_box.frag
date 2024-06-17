#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform sampler2D tex;
uniform vec4 box;
uniform vec4 viewBox = vec4(0.0, 0.0, 1.0, 1.0);

vec2 Box(vec4 box, vec2 uv){
    vec2 c2 = box.xy+box.zw;
    if(uv.x < box.x || uv.y < box.y || uv.x > c2.x || uv.y > c2.y){
        discard;
    }
    return ((uv - box.xy)/box.zw - viewBox.xy)/viewBox.zw;
}

void main(){
    vec2 pos = Box(box, TexCoords);
    if(pos.x<0.1||pos.x>0.9||pos.y<0.1||pos.y>0.9){
        //FragColor =   vec4(0.5,0.5,0.5,1.0); 
        
    }else{
    
    }
    FragColor =   texture(tex, pos); 
}