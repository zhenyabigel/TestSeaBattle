#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform sampler2D u_circleTexture;
uniform vec2 u_resolution;
uniform vec2 u_circlePos;
uniform float u_radius;

varying vec2 v_texCoord;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoord);

    vec2 pixelCoord = v_texCoord * u_resolution;

    float dist = distance(pixelCoord, u_circlePos);

    if (dist < u_radius) {
        gl_FragColor = texture2D(u_circleTexture, v_texCoord);
    } else {
        gl_FragColor = texColor; // Оставляем текстуру
    }
}
