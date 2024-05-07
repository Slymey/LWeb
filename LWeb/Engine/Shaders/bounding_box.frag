#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform sampler2D tex;
uniform vec4 box;
uniform vec4 viewBox = vec4(0.0, 0.0, 1.0, 1.0);

vec2 Box(vec4 box, vec2 texCoords){
    vec2 c2 = box.xy+box.zw;
    if(texCoords.x < box.x || texCoords.y < box.y || texCoords.x > c2.x || texCoords.y > c2.y){
        discard;
    }
    return ((texCoords - box.xy)/box.zw - viewBox.xy)/viewBox.zw;
}

void main(){
    vec2 pos = Box(box, TexCoords);
    FragColor =   texture(tex, pos); 
}