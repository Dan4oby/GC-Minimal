// side: client
package minimal.client.postInit.fog

import config.minimal.FogConfig
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import utils.CommonUtils
import net.minecraft.world.biome.BiomeProvider

event_manager.listen(EventPriority.LOWEST) { RenderFogEvent event ->
    if (!(event.getEntity() instanceof EntityPlayer)) return

    EntityPlayer player = event.getEntity()

    double camX = player.posX;
    double camY = player.posY + player.getEyeHeight();
    double camZ = player.posZ;

    BlockPos pos = new BlockPos(camX, camY, camZ);
    IBlockState state = player.world.getBlockState(pos);

    if (state.getMaterial() == Material.WATER) return
    if (player.dimension != 0) return

    if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */

        String biomeName = player.world.getBiome(new BlockPos(player.posX, player.posY, player.posZ)).getRegistryName().toString()
        double height = player.posY




        // get fogStart, fogEnd from config for certain biome else take common parameters
        def (fogStart, fogEnd) = FogConfig.biomeFog.containsKey(biomeName)
                ? FogConfig.biomeFog[biomeName]["fog"]
                : [FogConfig.fogStart, FogConfig.fogEnd]

        FogSmoother.currFogStart = FogSmoother.adjustmentFog(FogSmoother.currFogStart, fogStart)
        FogSmoother.currFogEnd = FogSmoother.adjustmentFog(FogSmoother.currFogEnd, fogEnd)

        int visibleDistance = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16
        int fogStartInBlocks = (int) (visibleDistance * FogSmoother.currFogStart)
        int fogEndInBlocks = (int) (visibleDistance * FogSmoother.currFogEnd)

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR)


        GlStateManager.setFogStart(fogStartInBlocks)
        GlStateManager.setFogEnd(fogEndInBlocks)

    }
  //else {
  //    GlStateManager.setFogStart(0)
  //    GlStateManager.setFogEnd(0)
  //}
}


//event_manager.listen { FogColors event ->
//    Entity entity = event.getEntity()
//    String biomeName = entity.world.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString()
//
//    LinkedHashMap state = [
//            "isRaining"    : Minecraft.getMinecraft().world.getRainStrength(1.0F) >= 0.5F,
//            "isConfigBiome": FogConfig.biomeFog.containsKey(biomeName)
//    ]
//
//    float red, green, blue
//    (red, green, blue) = FogConfig.color
//
//
//    if (state["isConfigBiome"]) {
//        (red, green, blue) = FogConfig.biomeFog[biomeName]["color"]
//    }
//    if (!CommonUtils.notSurvivalSP()) {
//        red *= 0.5
//        green *= 0.5
//        blue *= 0.5
//    }
//
//    FogSmoother.currRed = FogSmoother.colorMixin(FogSmoother.currRed, red)
//    FogSmoother.currGreen = FogSmoother.colorMixin(FogSmoother.currGreen, green)
//    FogSmoother.currBlue = FogSmoother.colorMixin(FogSmoother.currBlue, blue)
//
//    float max_val = 255F
//    event.setRed((float) (FogSmoother.currRed / max_val))
//    event.setGreen((float) (FogSmoother.currGreen / max_val))
//    event.setBlue((float) (FogSmoother.currBlue / max_val))
//
//    state.each { key, value ->
//        FogSmoother.previousState[key] = value
//    }
//}

event_manager.listen { WorldEvent.Load event ->
    FogSmoother.currFogStart = 0.0
    FogSmoother.currFogEnd = 0.0
}
