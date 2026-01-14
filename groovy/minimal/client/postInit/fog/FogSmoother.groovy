// side: client
package minimal.client.postInit.fog

import config.minimal.FogConfig


class FogSmoother {
    static LinkedHashMap previousState = [
            "isRaining"    : false,
            "isConfigBiome": false
    ]
    static double currFogStart = 0.0
    static double currFogEnd = 0.0
    static float currRed = FogConfig.color[0]
    static float currGreen = FogConfig.color[1]
    static float currBlue = FogConfig.color[2]
    final static double commonIncr = 0.5
    final static float colorIncr = 0.005

    static double adjustmentFog(currFog, fogTo) {
        double diff = Math.abs(fogTo - currFog)
        if (diff < 0.001D) return fogTo

        double adj = commonIncr * diff
        return currFog > fogTo
                ? Math.max(fogTo, currFog - adj)
                : Math.min(fogTo, currFog + 0.1F * adj)
    }

    static float colorMixin(color1, color2) {
        float diff = Math.abs(color1 - color2)

        return Math.abs(color1 - color2) > 1F
                ? (color1 + colorIncr * color2) / (1F + colorIncr)
                : color2
    }
}