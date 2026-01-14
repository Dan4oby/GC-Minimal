// side: client
// no_run
package minimal.client.init

import config.minimal.OthersConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.IRenderHandler
import net.minecraftforge.client.event.RenderWorldLastEvent

if (!OthersConfig.disableVanillaSky) return

event_manager.listen { RenderWorldLastEvent event ->
    Minecraft mc = Minecraft.getMinecraft()
    if (mc.world != null && mc.world.provider.getSkyRenderer() == null) {
        mc.world.provider.setSkyRenderer(new IRenderHandler() {
            @Override
            void render(float partialTicks, WorldClient world, Minecraft minecraft) {
                GlStateManager.disableTexture2D()
                GlStateManager.disableFog()
                GlStateManager.depthMask(false)

                GlStateManager.enableTexture2D()
                GlStateManager.enableFog()
                GlStateManager.depthMask(true)
            }
        })
    }
}