#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_mask;
uniform vec2 u_circleCenter;
uniform float u_circleRadius;
uniform vec2 u_textureSize;

varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
    vec2 flippedTexCoord = vec2(v_texCoord0.x,1.0-v_texCoord0.y);

vec4 mask = texture2D(u_mask, flippedTexCoord);
 vec2 normalCircleCenter = u_circleCenter / u_textureSize;
 float normalCircleRadius = u_circleRadius / u_textureSize.x;

     float aspectRatio = u_textureSize.x / u_textureSize.y;

 vec2 distanceVec = (v_texCoord0 - normalCircleCenter) * vec2(aspectRatio, 1.0);
    float distance = length(distanceVec);
     if (distance < normalCircleRadius) {
         gl_FragColor = vec4(1.0, 1.0, 0.0, 0.0);
     } else if (mask.w == 0.0){
              gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
     }
     else{
         gl_FragColor = mask;
}
}
