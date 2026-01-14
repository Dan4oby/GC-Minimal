// side: client
package minimal.client.init

import com.feed_the_beast.ftbquests.client.ClientQuestFile
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.advancements.GuiScreenAdvancements
import net.minecraftforge.client.event.GuiOpenEvent

event_manager.listen { GuiOpenEvent event ->
    if (event.getGui() instanceof GuiScreenAdvancements) {
        try {
            ClientQuestFile.INSTANCE.openQuestGui(Minecraft.getMinecraft().player)
            event.setCanceled(true)
        } catch (Exception ex) {

        }
    }
}
