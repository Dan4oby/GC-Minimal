// no_run
package minimal.server.init

import net.minecraft.world.WorldType
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition
import utils.CommonUtils

event_manager.listen { CreateSpawnPosition event ->
    if (event.world.worldType == WorldType.FLAT) {
        CommonUtils.executeCommands(new String[]{
                "gamerule doWeatherCycle false",
                "gamerule doDaylightCycle false",
                "weather clear",
                "time set 12750",
                "difficulty peaceful",
                "gamerule doMobSpawning false"
        })
    } else {
        CommonUtils.executeCommands(new String[]{
                "gamerule doWeatherCycle false",
                "gamerule doDaylightCycle false",
                "weather rain",
                "time set 16000",
                "difficulty peaceful",
                "gamerule doMobSpawning false"
        })
    }
}