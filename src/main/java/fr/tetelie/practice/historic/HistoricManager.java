package fr.tetelie.practice.historic;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public @Getter @Setter class HistoricManager {

    public HistoricManager(String title, String author, String... pages)
    {

    }

    public HistoricManager(String title, String author) { this(title, author, new String[0]); }

    public void open(Player p) {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItem(slot, getHistoricBook());

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        p.getInventory().setItem(slot, old);
    }

    private ItemStack getHistoricBook() {
        try {
            Class CraftMetaBook = Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaBook");
            Method method = CraftMetaBook.getMethod("getPages");
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();
            //List<IChatBaseComponent> pages = (List<IChatBaseComponent>) CraftMetaBook.getDeclaredField("pages").get(meta);
            List<IChatBaseComponent> pages = (List<IChatBaseComponent>) method.invoke(meta);
            TextComponent text = new TextComponent("§l§cExample\n");
            TextComponent msg = new TextComponent("§l§bClick me to go to spigotmc.org!\n");
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.spigotmc.org"));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("HI").create()));
            TextComponent msg2 = new TextComponent("§l§aClick me to go to spawn");
            msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spawn"));
            IChatBaseComponent page = ChatSerializer.a(ComponentSerializer.toString(new BaseComponent[]{text, msg, msg2}));
            pages.add(page);
            meta.setTitle("Example");
            meta.setAuthor("Optics Server");
            book.setItemMeta(meta);
            return book;
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException ex) {
            System.out.println(ex);
        }
        return null;
    }

}
