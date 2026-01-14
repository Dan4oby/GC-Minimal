// side: client
package minimal.client.postInit
import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiVideoSettings
import net.minecraft.client.settings.GameSettings
import net.minecraft.init.Blocks
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager

@Field static Minecraft mc = Minecraft.getMinecraft()

static void setCommonOptions() {
    GameSettings settings = mc.gameSettings
    settings.gammaSetting = 1f


    boolean fabulous = settings.isFabulous()
    boolean fast = settings.isFast()
    boolean fancy = !fast


    if (fabulous) {

    } else if (fancy) {

    } else if (fast) {

    }

}

setCommonOptions()

event_manager.listen { GuiScreenEvent.MouseInputEvent event ->
    if (event.getGui() instanceof GuiVideoSettings) setCommonOptions()
}


