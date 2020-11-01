package de.marvinleiers.mranks.commands.subcommands;

import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.mranks.commands.Subcommand;
import de.marvinleiers.mranks.menus.SetRankMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRank extends Subcommand
{
    @Override
    public String getName()
    {
        return "setrank";
    }

    @Override
    public String getDescription()
    {
        return "Set a players rank.";
    }

    @Override
    public String getSyntax()
    {
        return "/mranks setrank <player>";
    }

    @Override
    public String getUsage()
    {
        return "§a§l‣ §7" + getSyntax() + " §6• §7" + getDescription();
    }

    @Override
    public void execute(Player player, String[] args)
    {
        if (args.length < 2)
        {
            player.sendMessage("§cUsage: " + getSyntax());
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null)
        {
            player.sendMessage("§c" + args[1] + " is not online!");
            return;
        }

        new SetRankMenu(target, MenuAPI.getMenuUserInformation(player)).open();
    }
}
