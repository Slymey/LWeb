#version 330 core
out vec4 FragColor;
  
in vec2 tx;

uniform sampler2D text;
uniform vec4 fontColor;
uniform vec4 paintBox;

void main()
{
    vec2 bc2 = paintBox.xy + paintBox.zw;
    if(tx.x < paintBox.x || tx.y < paintBox.y || tx.x > bc2.x || tx.y > bc2.y){
        //FragColor = vec4(1.0, 0.0, 0.0, 1.0);
        discard;
    }else{
        FragColor = texture(text, clamp((tx-paintBox.xy)/paintBox.zw, 0.0, 1.0))*fontColor; 
    }
}