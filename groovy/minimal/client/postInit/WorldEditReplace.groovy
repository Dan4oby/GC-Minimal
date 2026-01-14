// no_run
package minimal.client.postInit

import groovy.transform.Field
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fml.common.gameevent.InputEvent
import org.lwjgl.input.Keyboard
import utils.CommonUtils

@Field private static final Minecraft MC = Minecraft.getMinecraft();

event_manager.listen { InputEvent.KeyInputEvent e ->

    if (MC.player == null) return;
    if (!Keyboard.isKeyDown(Keyboard.KEY_M)) return

    String cmd = buildReplaceCommandFromHotbar();
    if (cmd == null) {
        return;
    }

    // ВАЖНО: именно две косые черты для WorldEdit
    CommonUtils.executeCommandSP(cmd);
}

/**
 * Собирает строку вида:
 * //replace <from> 10%<to2>,10%<to3>,...
 * Возвращает null, если в первом слоте нет блока.
 */
private String buildReplaceCommandFromHotbar() {
    ItemStack[] hotbar = new ItemStack[9];
    for (int i = 0; i < 9; i++) {
        hotbar[i] = MC.player.inventory.getStackInSlot(i);
    }

    // FROM — первый слот хотбара
    String from = tokenForStack(hotbar[0]);
    if (from == null) {
        notifyClient("В первом слоте должен быть БЛОК для //replace <from>.");
        return null;
    }

    // TO — слоты 2..9
    StringBuilder targets = new StringBuilder();
    for (int i = 1; i < 9; i++) {
        String t = tokenForStack(hotbar[i]);
        if (t == null) continue;
        // не дублируем исходный блок

        if (targets.length() > 0) targets.append(',');
        targets.append("10%").append(t);
    }

    if (targets.length() == 0) {
        notifyClient("Во 2–9 слотах нет целевых блоков. Нечего чем заменять.");
        return null;
    }

    // Итоговая команда (WorldEdit/FAWE сам нормализует «веса»)
    return "//replace " + from + " " + targets;
}

/**
 * Преобразует ItemStack блока в токен для WorldEdit.
 * Формат: namespace:id[:meta]
 * Примеры: minecraft:stone, minecraft:stone:3, minecraft:wool:14
 *
 * Если предмет не блок — возвращает null.
 */
private String tokenForStack(ItemStack stack) {
    if (stack == null || stack.isEmpty()) return null;
    Item item = stack.getItem();
    if (!(item instanceof ItemBlock)) return null;

    ItemBlock ib = (ItemBlock) item;
    ResourceLocation rl = ib.getBlock().getRegistryName();
    if (rl == null) return null;

    int meta = stack.getMetadata(); // для 1.12.2 — «цвет/вариант» в damage/meta
    if (meta <= 0) {
        return rl.toString(); // minecraft:stone
    } else {
        return rl.toString() + ":" + meta; // minecraft:stone:3
    }
}

private void notifyClient(String msg) {
    // Можешь заменить на свой overlay/hud; тут — чат
    if (MC.player != null) {
        MC.player.sendMessage(new TextComponentString("[YourMod] " + msg));
    }
}