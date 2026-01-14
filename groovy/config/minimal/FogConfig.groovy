// side: client
package config.minimal

class FogConfig {
    static float fogStart = 0.33F
    static float fogEnd = 1.5F

    /*
    [RED, GREEN, BLUE]
    vanilla is:
    185F, 210F, 255F
    clear sky preset is
    150F, 180F, 255F*/
    final static float[] color = [65F, 95F, 110F]

    // f - float
    // "biome": ["color": [f, f, f], "fog": [f, f], "speed": f]
    final static LinkedHashMap biomeFog = [
            "minecraft:jungle": ["color": [100F, 130F, 150F], "fog": [0F, .5F], "speed": 0.001F],
    ]

    // isRaining
    // isConfigBiome
    // height
    final static Map<String, Closure<Integer>> priorities = [
            rainingPriority       : { int strength -> Math.min(10, strength) },
            configBiomeFogPriority: { int strength -> Math.min(10, strength) }
    ]
}