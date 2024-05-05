#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform vec4 pos = vec4(1.0, 1.0, 0.0, 0.0);
uniform vec4 offset = vec4(1.0, 1.0, 1.0, 1.0);
uniform vec4 amp = vec4(0.0, 0.0, 0.0, 0.0);
uniform vec4 freq = vec4(0.0, 0.0, 0.0, 0.0);
uniform vec4 phase = vec4(0.0, 0.0, 0.0, 0.0);
uniform vec4 box = vec4(0.0, 0.0, 1.0, 1.0);

const float TAU = 6.28318530718;

vec4 palette(float t, vec4 o, vec4 a, vec4 f, vec4 p){
    //interpolation, dc offset, amplitude, frequency, phase
    return o + a * cos(TAU*(f*t + p));
}
vec2 Box(in vec4 box, in vec2 texCoords){
    vec2 c2 = box.xy+box.zw;
    if(texCoords.x < box.x || texCoords.y < box.y || texCoords.x > c2.x || texCoords.y > c2.y){
        discard;
    }
    return (texCoords-box.xy)/box.zw;
}

void main()
{ 
    vec2 texCoords = Box(box, TexCoords);
    //if(TexCoords.x < box.x || TexCoords.x > box.z || TexCoords.y < box.y || TexCoords.y > box.w){discard;}
    
    float lenD = length( pos.zw - pos.xy);
    float lenP = length(pos.xy - texCoords );
    float t = clamp((lenP/lenD)*(dot(pos.zw-pos.xy, texCoords-pos.xy)/(lenP*lenD)), 0.0, 1.0);

    FragColor = palette(t, offset, amp, freq, phase);
}
