package de.marvinleiers.mranks.commands;

import org.bukkit.entity.Player;

public abstract class Subcommand
{
    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getUsage();

    public abstract void execute(Player player, String[] args);
}
