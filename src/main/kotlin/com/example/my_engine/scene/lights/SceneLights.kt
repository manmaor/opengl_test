package com.example.my_engine.scene.lights

// When modifying also modify fragment shader structs
data class SceneLights (
    val ambientLight: AmbientLight,
    val dirLight: DirLight,
    val pointLight: List<PointLight>,
    val spotLight: List<SpotLight>
)