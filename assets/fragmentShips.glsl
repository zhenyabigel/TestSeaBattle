
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture; // Текстура поляu_circleTexture
uniform sampler2D u_newShipTexture; // Текстура поляu_circleTexture
uniform vec2 u_resolution;  // Размер экрана или текстуры
uniform vec2 u_circlePos;   // Позиция центра круга (0, 0 в данном случае)
uniform vec4 u_shipBounds;   // Позиция центра круга (0, 0 в данном случае)
uniform float u_radius;     // Радиус круга

varying vec2 v_texCoord;

void main() {

    // Преобразование координат в пиксели
    vec2 pixelCoord = v_texCoord * u_resolution;
    bool isInCircle = true;

    vec2 circleCenter = u_circlePos * u_resolution;
    float radius = u_radius * min(u_resolution.x, u_resolution.y); // Радиус в пикселях

    vec2 shipMin = u_shipBounds.xy * u_resolution;
    vec2 shipMax = u_shipBounds.zw * u_resolution;
    // Вычисление расстояния от текущей точки до центра круга
    float dist = distance(pixelCoord, u_circlePos);
    vec2 corners[4];
    corners[0] = shipMin;                 // Левый нижний угол
    corners[1] = vec2(shipMax.x, shipMin.y); // Правый нижний угол
    corners[2] = vec2(shipMin.x, shipMax.y); // Левый верхний угол
    corners[3] = shipMax;                 // Правый верхний угол

    for (int i = 0; i < 4; i++) {
        float dist = distance(corners[i], circleCenter);
        if (dist > radius) {
            isInCircle = false; // Если хотя бы одна вершина за пределами круга, флаг сбрасывается
            break;
        }
    }

    // Выбираем текстуру в зависимости от того, находится ли корабль в круге
    vec4 color;
    if (isInCircle) {
        color = texture2D(u_texture, v_texCoord); // Текстура старого корабля внутри круга
    } else {
        color = texture2D(u_newShipTexture, v_texCoord); // Текстура нового корабля снаружи круга
    }

    gl_FragColor = color;

    gl_FragColor = color;
}


















//    #ifdef GL_ES
//    precision mediump float;
//    #endif
//
//    // Входные текстуры
//    uniform sampler2D u_oldShipTexture; // Текстура старого корабля
//    uniform sampler2D u_newShipTexture;  // Текстура нового корабля
//
//    // Параметры экрана/текстуры
//    uniform vec2 u_resolution;         // Размер экрана или текстуры
//    uniform vec2 u_circlePos;          // Позиция центра круга (нормализованная)
//    uniform float u_radius;            // Радиус круга (нормализованный)
//
//    // Границы корабля (x_min, y_min, x_max, y_max)
//    uniform vec4 u_shipBounds;
//
//    // Координаты текущего фрагмента
//    varying vec2 v_texCoord;
//
//    void main() {
//
//        // Нормализуем координаты текущего пикселя (от 0 до 1)
//        vec2 uv = v_texCoord;
//
//        // Преобразуем координаты центра круга и границ корабля в пиксельные значения
//        vec2 circleCenter = u_circlePos * u_resolution;
//        float radius = u_radius * min(u_resolution.x, u_resolution.y); // Радиус в пикселях
//
//        vec2 shipMin = u_shipBounds.xy * u_resolution;
//        vec2 shipMax = u_shipBounds.zw * u_resolution;
//
//        // Проверяем, находится ли корабль в пределах круга
//        bool isInCircle = true;
//
//        // Проверяем каждую вершину корабля
//        vec2 corners[4];
//        corners[0] = shipMin;                 // Левый нижний угол
//        corners[1] = vec2(shipMax.x, shipMin.y); // Правый нижний угол
//        corners[2] = vec2(shipMin.x, shipMax.y); // Левый верхний угол
//        corners[3] = shipMax;                 // Правый верхний угол
//
//        for (int i = 0; i < 4; i++) {
//            float dist = distance(corners[i], circleCenter);
//            if (dist > radius) {
//                isInCircle = false; // Если хотя бы одна вершина за пределами круга, флаг сбрасывается
//                break;
//            }
//        }
//
//        // Выбираем текстуру в зависимости от того, находится ли корабль в круге
//        vec4 color;
//        if (isInCircle) {
//            color = texture2D(u_oldShipTexture, uv); // Текстура старого корабля внутри круга
//        } else {
//            color = texture2D(u_newShipTexture, uv); // Текстура нового корабля снаружи круга
//        }
//
//        gl_FragColor = color;
//    }
