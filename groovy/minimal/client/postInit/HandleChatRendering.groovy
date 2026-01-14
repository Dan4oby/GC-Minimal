
package minimal.client.postInit

import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ChatLine
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiNewChat
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.client.event.RenderGameOverlayEvent

@Field Minecraft mc = Minecraft.getMinecraft()

event_manager.listen { RenderGameOverlayEvent.Chat event ->
    if (Minecraft.getMinecraft().gameSettings.chatVisibility == EntityPlayer.EnumChatVisibility.HIDDEN) return

    GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.chatGUI
    FontRenderer fr   = Minecraft.getMinecraft().fontRenderer

    event.setCanceled(true)

    boolean chatOpen = chat.getChatOpen()
    int update       = Minecraft.getMinecraft().ingameGUI.getUpdateCounter()

    int maxLines = chat.getLineCount()

    int x = 2
    int y = event.getPosY() + 5

    int shown = 0
    List<ChatLine> lines = chat.drawnChatLines

    for (int i = 0; i < lines.size() && shown < maxLines; i++) {
        ChatLine cl = lines.get(i)
        ITextComponent comp = cl.getChatComponent()
        if (comp == null) continue

        int age = update - cl.getUpdatedCounter()

        if (!chatOpen && age >= 250) continue

        double fade = 1.0D
        if (!chatOpen && age >= 50) {
            fade -= age / 100D
            fade = Math.max(fade, 0D)
        }

        int alpha = (int) (255.0D * fade * Minecraft.getMinecraft().gameSettings.chatOpacity)
        if (alpha <= 3) continue

        int color = (alpha << 24) | 0xFFFFFF

        String s = comp.getFormattedText()
        fr.drawString(s, x as float, (y - shown * 9) as float, color, true)

        shown++
    }
}