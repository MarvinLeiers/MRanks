package de.marvinleiers.mranks.menus;

import de.marvinleiers.menuapi.Menu;
import de.marvinleiers.menuapi.MenuUserInformation;
import de.marvinleiers.mranks.MRanks;
import de.marvinleiers.mranks.ranks.RankEnum;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetRankMenu extends Menu
{
    private Player target;

    public SetRankMenu(Player target, MenuUserInformation menuUserInformation)
    {
        super(menuUserInformation);
        this.target = target;
    }

    @Override
    public String getTitle()
    {
        return "Change rank";
    }

    @Override
    public int getSlots()
    {
        return 9;
    }

    @Override
    public void setItems()
    {
        for (RankEnum rank : RankEnum.values())
            inventory.addItem(makeItem(Material.PAPER, rank.getFormated()));
    }

    @Override
    public void handleClickActions(InventoryClickEvent inventoryClickEvent)
    {
        ItemStack item = inventoryClickEvent.getCurrentItem();

        RankEnum rank = RankEnum.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName().toUpperCase()));

        MRanks.getInstance().getMRanksUser(target).setRank(rank);
    }
}
