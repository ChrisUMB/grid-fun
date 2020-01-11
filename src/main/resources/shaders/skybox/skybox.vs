#version 330 core

in vec3 aPos;
out vec3 vPos;

uniform mat4 uProjection;
uniform mat4 uViewRot;

void main() {
    vPos = aPos;
    gl_Position = (vec4(aPos, 1) * uViewRot) * uProjection;
}