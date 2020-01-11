#version 330 core

uniform sampler2D uTexture;

out vec4 fColor;
in vec2 vTexCoord;
in vec3 vNormal;

void main() {
//    fColor = texture(uTexture, vTexCoord);
    fColor = vec4(0f, 0f, 0f, 1f);
}