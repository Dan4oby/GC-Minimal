package utils


import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.GameType
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class CommonUtils {
    @SideOnly(Side.CLIENT)
    public static Minecraft mc = Minecraft.getMinecraft()

    static boolean notSurvivalMP(EntityPlayerMP player) {
        return player.interactionManager.getGameType() != GameType.SURVIVAL
    }

    @SideOnly(Side.CLIENT)
    static boolean notSurvivalSP() {
        Minecraft mc = Minecraft.getMinecraft()
        return !mc || !mc.player || mc.playerController.currentGameType != GameType.SURVIVAL
    }

    @SideOnly(Side.CLIENT)
    static GameType getGameTypeSP() {
        Minecraft mc = Minecraft.getMinecraft()
        if (mc == null || mc.player == null) return
        return mc.playerController.currentGameType
    }

    static void executeCommand(String command) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance()
        if (server == null) return

        server.getCommandManager().executeCommand(server, command)
    }

    static void executeCommands(String[] commands) {
        commands.each { command ->
            executeCommand(command)
        }
    }

    static void changeGamemodeMP(String playerName, GameType gameType) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance()
        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerName)
        if (player != null) player.setGameType(gameType)
    }

    @SideOnly(Side.CLIENT)
    static void sendMessageToPlayer(String message) {
        Minecraft mc = Minecraft.getMinecraft()
        if (mc.player == null) return
        mc.player.sendMessage(new TextComponentString(message))
    }

    @SideOnly(Side.CLIENT)
    static void changeGamemodeSP(GameType gameType) {
        def mc = Minecraft.getMinecraft()
        if (mc.player == null) return

        mc.player.sendChatMessage("/gamemode " + gameType.name)
    }

    @SideOnly(Side.CLIENT)
    static void teleportPlayerSP(double x, double y, double z) {
        def mc = Minecraft.getMinecraft()
        if (mc.player == null) return

        mc.player.sendChatMessage("/tp " + (int)x + " " + (int)y + " " + (int)z)
    }

    @SideOnly(Side.CLIENT)
    static void executeCommandSP(String command) {
        def mc = Minecraft.getMinecraft()
        if (mc.player == null) return

        mc.player.sendChatMessage(command)
    }
}