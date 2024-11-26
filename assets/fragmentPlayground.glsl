#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture; // Текстура поляu_circleTexture
uniform sampler2D u_circleTexture; // Текстура поляu_circleTexture
uniform vec2 u_resolution;  // Размер экрана или текстуры
uniform vec2 u_circlePos;   // Позиция центра круга (0, 0 в данном случае)
uniform float u_radius;     // Радиус круга

varying vec2 v_texCoord;

void main() {
    // Получение цвета текстуры
    vec4 texColor = texture2D(u_texture, v_texCoord);

    // Преобразование координат в пиксели
    vec2 pixelCoord = v_texCoord * u_resolution;

    // Вычисление расстояния от текущей точки до центра круга
    float dist = distance(pixelCoord, u_circlePos);

    // Если точка внутри круга, рисуем круг, иначе оставляем текстуру
    if (dist < u_radius) {
        gl_FragColor = texture2D(u_circleTexture, v_texCoord); // Используем текстуру для круга
    } else {
        gl_FragColor = texColor; // Оставляем текстуру
    }
}
