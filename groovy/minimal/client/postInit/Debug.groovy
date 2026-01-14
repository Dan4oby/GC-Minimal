// no_run
package minimal.client.postInit

import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos

long lastClientPrint = 0L
long lastServerPrint = 0L
fewfwe
event_manager.listen { Event event ->

    // ===== КЛИЕНТ =====
    if (event instanceof TickEvent.ClientTickEvent) {
        def e = (TickEvent.ClientTickEvent) event

        def mc = Minecraft.getMinecraft()
        if (mc == null || mc.world == null || mc.player == null) return

        def world = mc.world
        def player = mc.player

        if (world.totalWorldTime - lastClientPrint < 40) return
        lastClientPrint = world.totalWorldTime

        int rd = mc.gameSettings.renderDistanceChunks
        int cx = (int)(player.posX / 16.0D)
        int cz = (int)(player.posZ / 16.0D)

        int r8 = Math.min(8, rd)
        int r10 = rd  // в твоём кейсе 10

        // --- 1) world: считаем отдельно радиус 8 и 10 ---
        def worldR8  = new HashSet<String>()
        def worldR10 = new HashSet<String>()

        for (int dx = -r10; dx <= r10; dx++) {
            for (int dz = -r10; dz <= r10; dz++) {
                int x = cx + dx
                int z = cz + dz
                if (world.isChunkGeneratedAt(x, z)) {
                    int cheb = Math.max(Math.abs(dx), Math.abs(dz)) // "радиус по чанкам"
                    worldR10.add("${x},${z}")
                    if (cheb <= r8) {
                        worldR8.add("${x},${z}")
                    }
                }
            }
        }

        // --- 2) render: тоже радиус 8 и 10 ---
        def renderR8  = new HashSet<String>()
        def renderR10 = new HashSet<String>()

        def frustum = mc.renderGlobal?.viewFrustum
        int rcArrayLen = -1
        if (frustum != null && frustum.renderChunks != null) {
            def arr = frustum.renderChunks
            rcArrayLen = arr.length
            arr.each { rc ->
                if (rc == null) return
                BlockPos pos = rc.position
                int x = pos.x >> 4
                int z = pos.z >> 4
                int dx = x - cx
                int dz = z - cz
                int cheb = Math.max(Math.abs(dx), Math.abs(dz))
                if (cheb <= r10) {
                    renderR10.add("${x},${z}")
                    if (cheb <= r8) {
                        renderR8.add("${x},${z}")
                    }
                }
            }
        }

        // --- 3) missing по радиусам ---
        def missingR8  = new HashSet<>(worldR8);  missingR8.removeAll(renderR8)
        def missingR10 = new HashSet<>(worldR10); missingR10.removeAll(renderR10)

        log.info("[CLIENT] rd=${rd}, playerChunk=(${cx},${cz}), rcArrayLen=${rcArrayLen}")
        log.info("[CLIENT] worldR8=${worldR8.size()}, renderR8=${renderR8.size()}, missingR8=${missingR8.size()}")
        log.info("[CLIENT] worldR10=${worldR10.size()}, renderR10=${renderR10.size()}, missingR10=${missingR10.size()}")

        if (!missingR10.isEmpty()) {
            log.info("[CLIENT] missing in R10 (max 10):")
            int printed = 0
            for (String s : missingR10) {
                log.info("  " + s)
                printed++
                if (printed >= 10) {
                    if (missingR10.size() > 10) {
                        log.info("  ... +${missingR10.size() - 10} more")
                    }
                    break
                }
            }
        }

        return
    }

    // ===== СЕРВЕР =====
    if (event instanceof TickEvent.PlayerTickEvent) {
        def e = (TickEvent.PlayerTickEvent) event

        def player = e.player
        def world = player.world
        if (world.isRemote) return

        if (world.totalWorldTime - lastServerPrint < 40) return
        lastServerPrint = world.totalWorldTime

        def server = player.server
        if (server == null) return

        int vd = server.playerList.viewDistance
        int cx = (int)(player.posX / 16.0D)
        int cz = (int)(player.posZ / 16.0D)

        int generated = 0
        int loaded = 0

        for (int dx = -vd; dx <= vd; dx++) {
            for (int dz = -vd; dz <= vd; dz++) {
                int x = cx + dx
                int z = cz + dz

                if (world.isChunkGeneratedAt(x, z)) {
                    generated++
                }
                def chunk = world.getChunkProvider().getLoadedChunk(x, z)
                if (chunk != null) {
                    loaded++
                }
            }
        }

        log.info("[SRV] vd=${vd}, playerChunk=(${cx},${cz}), generated=${generated}, loaded=${loaded}")
        return
    }
}
