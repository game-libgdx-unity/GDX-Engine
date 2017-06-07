#ifdef GL_ES
#define LOWP lowp
#define MEDP mediump
#define HIGP highp
#else
#define LOWP
#define MEDP
#define HIGP
#endif

attribute vec4 a_position;
attribute vec3 a_normal;
attribute MEDP vec2 a_texCoord0;

uniform mat3 u_normal;
uniform mat4 u_projView;
uniform vec3 u_light;
uniform vec3 u_light_color;
uniform vec3 u_light_ambient_color;

varying LOWP float intensity;
varying MEDP vec2 texCoords;
varying MEDP vec3 lightColor;
varying MEDP vec3 ambientColor;

void main() {
	vec3 n = normalize(u_normal * a_normal);
	vec3 l = normalize(u_light);
	intensity = max(dot(n, l), 0.0);
	texCoords = a_texCoord0;
	lightColor = u_light_color;
	ambientColor = u_light_ambient_color;
	gl_Position = u_projView * a_position;
}