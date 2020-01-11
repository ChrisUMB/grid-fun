#version 330 core

uniform mat4 uModel;
uniform mat4 uViewProjection;

in vec3 aPosition;
in vec2 aTexCoord;
in vec3 aNormal;

out vec2 vTexCoord;
out vec3 vNormal;

void main() {
    vTexCoord = aTexCoord;
    vNormal = aNormal;
    gl_Position = (vec4(aPosition, 1) * uModel) * uViewProjection;
}