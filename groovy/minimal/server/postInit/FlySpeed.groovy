package minimal.server.postInit

import groovy.transform.Field
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.IAttributeInstance
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.gameevent.PlayerEvent

@Field private static final float speed = 0.11f

event_manager.listen { PlayerEvent.PlayerLoggedInEvent event ->
    EntityPlayer p = event.player

    IAttributeInstance inst = p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
    inst.setBaseValue(speed)

    p.capabilities.flySpeed = speed
    p.sendPlayerAbilities()
}

