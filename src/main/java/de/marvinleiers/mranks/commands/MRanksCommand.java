package de.marvinleiers.mranks.commands;

import de.marvinleiers.mranks.commands.subcommands.SetRank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MRanksCommand implements CommandExecutor
{
    private final List<Subcommand> subcommands = new ArrayList<>();

    public MRanksCommand()
    {
        subcommands.add(new SetRank());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cOnly for players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0)
        {
            sendHelp(sender);
            return true;
        }

        for (Subcommand subcommand : subcommands)
        {
            if (subcommand.getName().equalsIgnoreCase(args[0]))
            {
                subcommand.execute(player, args);
                return true;
            }
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender)
    {
        sender.sendMessage(" ");
        sender.sendMessage(" ");

        for (Subcommand subcommand : subcommands)
            sender.sendMessage(subcommand.getUsage());

        sender.sendMessage(" ");
        sender.sendMessage(" ");
    }
}
