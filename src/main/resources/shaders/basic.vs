#version 330

uniform mat4 uModel;
uniform mat4 uViewProjection;

in vec3 aPosition;
in vec2 aTexCoord;

out vec2 vTexCoord;

void main() {
    vTexCoord = aTexCoord;
    gl_Position = (vec4(aPosition, 1) * uModel) * uViewProjection;
}