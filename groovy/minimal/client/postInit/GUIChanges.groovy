// side: client
package minimal.client.postInit

import config.minimal.OthersConfig
import groovy.transform.Field
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiOptions
import net.minecraft.client.gui.GuiScreenOptionsSounds
import net.minecraft.client.gui.GuiVideoSettings
import net.minecraft.client.resources.I18n
import net.minecraftforge.client.GuiIngameForge
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import utils.CommonUtils
import utils.PackMode

@Field static String lastGui = null
@Field static boolean guiModified = false

event_manager.listen { GuiScreenEvent.InitGuiEvent.Post event ->
    String gui = event.getGui().getClass().getName()
    if (lastGui != gui) {
        lastGui = gui
        guiModified = false

        if (OthersConfig.packMode.getDebugCapabilities()) {
            CommonUtils.sendMessageToPlayer("Current GUI: " + gui)
        }
    }

    List<GuiButton> buttons = event.getButtonList()

    if (OthersConfig.packMode.getDebugCapabilities()) {
        for (GuiButton button : event.getButtonList()) {
            CommonUtils.sendMessageToPlayer("Button: " + button.id)
        }
    }


    if (event.getGui() instanceof GuiOptions) {
        for (GuiButton button : buttons) {
            if (button.id == 104) {
                button.visible = false
                break
            }
        }
        int width = event.getGui().width
        int height = event.getGui().height

        buttons.add(new GuiButton(107, 5, 5, 150, 20,
                I18n.format("switch.packmode", new Object[0])))

    }

    if (event.getGui() instanceof GuiScreenOptionsSounds) {
        buttons.get(2).visible = false
        buttons.get(5).visible = false
        buttons.get(8).visible = false

        buttons.get(4).y -= 24
        buttons.get(6).y -= 24

        buttons.get(7).y += 24
        buttons.get(7).x += 160

    }

    guiModified = true
}

event_manager.listen { RenderGameOverlayEvent.Pre event ->
    GuiIngameForge.renderExperiance = false
    GuiIngameForge.renderHotbar = true
    GuiIngameForge.renderHealth = true
    GuiIngameForge.renderFood = true
    GuiIngameForge.renderCrosshairs = true
    GuiIngameForge.renderVignette = false
}

if (mezz.jei.config.Config.isBookmarkOverlayEnabled()) mezz.jei.config.Config.toggleBookmarkEnabled()
if (mezz.jei.config.Config.isOverlayEnabled()) mezz.jei.config.Config.toggleOverlayEnabled()


