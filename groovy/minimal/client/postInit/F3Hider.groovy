// side: client
package minimal.client.postInit

import config.minimal.F3HiderConfig
import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import utils.CommonUtils
fwefew
@Field static String modpackName = "GC Minimal " + getPackVersion() + " on Minecraft 1.12.2"

event_manager.listen(EventPriority.LOWEST) { RenderGameOverlayEvent.Text event ->
    if (CommonUtils.notSurvivalSP()) return

    Minecraft mc = Minecraft.getMinecraft()

    if (!mc.gameSettings.showDebugInfo) return

    ArrayList<String> left = event.getLeft()

    ArrayList<String> right = event.getRight()

    left.add(0, modpackName)
    left.retainAll { line ->
        F3HiderConfig.leftToShow.any { line.contains(it) }
    }

    right.retainAll { line ->
        F3HiderConfig.rightToShow.any { line.contains(it) }
    }


}