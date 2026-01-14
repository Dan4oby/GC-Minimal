// side: client
package minimal.client.postInit

import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiControls
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Field static String lastGui = null
@Field static int lastScale = -1

@Field static boolean guiModified = false

event_manager.listen { GuiScreenEvent.InitGuiEvent.Pre e ->
    if (e.getGui() != null) {
        try {
            GuiScreen gui = e.getGui()

            if (guiModified) {
                guiModified = false
                return
            }

            lastGui = name

            int scale = 3
            if (gui instanceof GuiControls) scale = 2

            if (lastScale == scale) return

            lastScale = scale

            guiModified = true
            Minecraft.getMinecraft().gameSettings.guiScale = scale
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft())
            int w = scaledResolution.getScaledWidth()
            int h = scaledResolution.getScaledHeight()
            e.setCanceled(true)
            e.getGui().setWorldAndResolution(Minecraft.getMinecraft(), w, h)
        } catch (Exception ex) {
            guiModified = false
        }
    } else {
        try {
            lastGui = null
            lastScale = -1

        } catch (Exception ex) {

        }
    }
}

event_manager.listen { TickEvent.ClientTickEvent e ->
    if (Minecraft.getMinecraft().currentScreen == null) {
        Minecraft minecraft = Minecraft.getMinecraft()
        minecraft.gameSettings.guiScale = 3
    }
}