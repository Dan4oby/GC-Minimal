// side: client

package minimal.client.postInit

import dev.redstudio.redcore.ticking.RedClientTickEvent
import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.GameSettings
import utils.CommonUtils

@Field static def lastGamemode = null
@Field static Minecraft mc = Minecraft.getMinecraft()

event_manager.listen { RedClientTickEvent.PentaTickEvent e ->
    if (mc.player == null) return
    def gm = mc.playerController.currentGameType
    if (gm != lastGamemode) {
        lastGamemode = gm
        GameSettings settings = mc.gameSettings
        if (CommonUtils.notSurvivalSP()) {
            settings.viewBobbing = false
        } else {
            settings.viewBobbing = true
        }

    }
}
