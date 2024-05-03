#version 330 core
out vec4 FragColor;
  
in vec2 TexCoord;
in vec4 fontColor;
in vec4 paintBox;
in vec2 bPos;

uniform sampler2D text;

void main()
{
    vec2 tst = bPos - paintBox.zw*2.0;
    if(tst.x > 0.0 || tst.y > 0.0 || bPos.x <0.0 || bPos.y < 0.0){
        discard;
    }
    FragColor =   texture(text, clamp((TexCoord-paintBox.xy/2.0-0.5)/paintBox.zw, 0.0, 1.0))*fontColor; 
}