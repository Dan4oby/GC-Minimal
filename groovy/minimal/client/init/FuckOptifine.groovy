// side: client
package minimal.client.init

import dev.redstudio.redcore.utils.OptiNotFine
import net.minecraft.client.Minecraft
import net.minecraft.crash.CrashReport


if (OptiNotFine.isOptiFineInstalled()) {
    Minecraft.getMinecraft().crashed(
            new CrashReport("PLEASE, DON'T USE OPTIFINE", new Throwable("Modpack doesn't support it"))
    )
}