// side: client
package minimal.client.postInit.keybind_handlers

import config.minimal.OthersConfig
import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.world.GameType
import net.minecraftforge.fml.common.gameevent.InputEvent
import org.lwjgl.input.Keyboard
import utils.CommonUtils

@Field static boolean f3Pressed = false
@Field static boolean f4Pressed = false

if (!OthersConfig.packMode.getExclusiveCapabilities()) return

event_manager.listen { InputEvent.KeyInputEvent event ->
    if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
        f3Pressed = true
    } else {
        f3Pressed = false
    }

    if (f4Pressed) Minecraft.getMinecraft().gameSettings.showDebugInfo = true

    if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
        f4Pressed = true
    } else {
        f4Pressed = false
    }

    if (f3Pressed && f4Pressed) {
        Minecraft mc = Minecraft.getMinecraft()

        if (mc.player == null || mc.playerController == null) return

        switch (mc.playerController.getCurrentGameType()) {
            case (GameType.CREATIVE):
                CommonUtils.changeGamemodeSP(GameType.SPECTATOR)
                break
            default:
                CommonUtils.changeGamemodeSP(GameType.CREATIVE)
        }
    }
}

