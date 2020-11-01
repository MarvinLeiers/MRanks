package de.marvinleiers.mranks.ranks;

import org.bukkit.ChatColor;

public enum RankEnum
{
    OWNER("§4§lOwner • ", "Owner", ChatColor.DARK_RED, 0),
    ADMIN("§c§lAdmin • ", "Admin", ChatColor.RED, 1),
    DEVELOPER("§9§lDeveloper • ", "Developer", ChatColor.BLUE, 2),
    MODERATOR("§5§lMod • ", "Moderator", ChatColor.DARK_PURPLE, 3),
    VIP("§6VIP • ", "VIP", ChatColor.GOLD, 4),
    DEFAULT("§7", "Default", ChatColor.GRAY, 5);

    String prefix;
    String name;
    ChatColor color;
    int priority;

    RankEnum(String prefix, String name, ChatColor color, int priority)
    {
        this.prefix = prefix;
        this.priority = priority;
        this.color = color;
        this.name = name;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getName()
    {
        return name;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public int getPriority()
    {
        return priority;
    }

    public String getFormated()
    {
        return getColor() + getName();
    }

    @Override
    public String toString()
    {
        return name.toLowerCase();
    }
}
