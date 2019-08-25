package fr.tetelie.practice.historic;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

public @Getter class HistoricElement {

    final SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss");

    private ItemStack icon;
    private String[] lines;
    private ElementType elementType;
    private String time;

    public HistoricElement(ItemStack icon, ElementType elementType, String... lines)
    {
        this.icon = icon;
        this.elementType = elementType;
        this.lines = lines;
        this.time = dt.format(new Date());
    }

    public HistoricElement(ItemStack icon, String... lines)
    {
        this(icon, ElementType.NONE, lines);
    }

}
