package minimal.client.postInit


import net.minecraftforge.client.event.FOVUpdateEvent
import utils.CommonUtils

event_manager.listen { FOVUpdateEvent event ->
    if (CommonUtils.notSurvivalSP()) {
        float fov = 1.1f
        if (event.getNewfov() >= fov) event.setNewfov(fov)
    }
}
