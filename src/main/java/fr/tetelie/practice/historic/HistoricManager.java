package fr.tetelie.practice.historic;

import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

// original idea of ​​Noksio

public class HistoricManager {

    private List<HistoricElement> elements = new ArrayList<>();

    public HistoricManager() {}

    public Inventory getHistoricInventory(Player player, int page)
    {
        Inventory historic = Bukkit.createInventory(null, 9*6, "§6Historic");
        int i = page == 1 ? 0 : (page-1)*35;
        for(HistoricElement historicElement : elements)
        {
            historic.setItem(i, new ItemBuilder(historicElement.getIcon()).setLore(historicElement.getLines()).addLoreLine(" ").addLoreLine("§7at "+historicElement.getTime()).toItemStack());
            i++;
            if(i <= 35*page) break;
        }
        historic.setItem(45, new ItemBuilder(Material.LEVER).setName("§6Previous page").toItemStack());
        historic.setItem(49, new ItemBuilder(Material.SKULL_ITEM, page, (byte)0).setName("§ePage§7: §f"+page).toItemStack());
        historic.setItem(53, new ItemBuilder(Material.ARROW).setName("§6Next page").toItemStack());
        return historic;
    }

    public void addElement(HistoricElement historicElement)
    {
        elements.add(historicElement);
    }

    public void removeElement(HistoricElement historicElement)
    {
        elements.remove(historicElement);
    }

}
