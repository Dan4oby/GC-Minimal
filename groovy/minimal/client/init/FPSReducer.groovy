// side: client
package minimal.client.init

import dev.redstudio.redcore.ticking.RedClientTickEvent
import groovy.transform.Field
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.Display

@Field static int originalLimit = -1
@Field static boolean throttled = false


event_manager.listen(EventPriority.HIGHEST) { RedClientTickEvent.PentaTickEvent event ->
    Minecraft mc = Minecraft.getMinecraft()
    if (mc == null || mc.gameSettings == null) return

    boolean isActive = Display.isActive()

    if (!isActive && !throttled) {
        originalLimit = mc.gameSettings.limitFramerate
        mc.gameSettings.limitFramerate = 10
        throttled = true
    } else if (isActive && throttled) {
        if (originalLimit <= 0) originalLimit = 120
        mc.gameSettings.limitFramerate = originalLimit
        throttled = false
    }
}

