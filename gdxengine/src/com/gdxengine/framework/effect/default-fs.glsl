#ifdef GL_ES
#define LOWP lowp
#define MEDP mediump
#define HIGP highp
precision lowp float;
#else
#define LOWP
#define MEDP
#define HIGP
#endif

uniform sampler2D u_diffuse;

varying LOWP float intensity;
varying MEDP vec2 texCoords;
varying MEDP vec3 lightColor;
varying MEDP vec3 ambientColor;

void main() {
	vec4 finalColor = vec4(lightColor,1.0) * intensity * texture2D(u_diffuse, texCoords); 
	finalColor.rbg += ambientColor;
	gl_FragColor = finalColor;
}