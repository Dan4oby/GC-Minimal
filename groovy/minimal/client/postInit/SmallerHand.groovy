
package minimal.client.postInit

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.util.EnumHandSide
import net.minecraftforge.client.event.RenderSpecificHandEvent

event_manager.listen { RenderSpecificHandEvent event ->
    Minecraft mc = Minecraft.getMinecraft();
    ItemRenderer ir = mc.getItemRenderer();

    GlStateManager.pushMatrix();
    try {
        GlStateManager.translate(0.0F, -0.05F, 0.1F);

        event.setCanceled(true)

        ir.renderItemInFirstPerson(
                (AbstractClientPlayer) mc.player,
                event.getPartialTicks(),
                event.getInterpolatedPitch(),
                event.getHand(),
                event.getSwingProgress(),
                event.getItemStack(),
                event.getEquipProgress()
        );

    } finally {
        GlStateManager.popMatrix();
    }
}