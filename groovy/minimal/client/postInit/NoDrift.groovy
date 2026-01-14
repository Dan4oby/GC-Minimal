// side: client

package minimal.client.postInit

import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.GameSettings
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Field static boolean disableCreativeDrift = false

event_manager.listen { TickEvent.PlayerTickEvent e ->
    if (disableCreativeDrift) return

    EntityPlayer player = e.player
    if (player.capabilities.isFlying) {
        GameSettings gs = Minecraft.getMinecraft().gameSettings

        boolean forward = GameSettings.isKeyDown(gs.keyBindForward)
        boolean back = GameSettings.isKeyDown(gs.keyBindBack)
        boolean left = GameSettings.isKeyDown(gs.keyBindLeft)
        boolean right = GameSettings.isKeyDown(gs.keyBindRight)
        boolean up = GameSettings.isKeyDown(gs.keyBindJump)
        boolean down = GameSettings.isKeyDown(gs.keyBindSneak)

        if (!forward && !back && !left && !right) {
            player.motionX = 0.00
            player.motionZ = 0.0D
        }
        if (!up && !down) {
            player.motionY = 0.00
        }
    }
}