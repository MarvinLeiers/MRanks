package de.marvinleiers.mranks.user;

import de.marvinleiers.mranks.MRanks;
import de.marvinleiers.mranks.ranks.RankEnum;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MRanksUser
{
    private final Player player;
    private final File configFile;
    private YamlConfiguration config;
    private final PermissionAttachment permissionAttachment;

    private RankEnum rank;

    public MRanksUser(Player player)
    {
        this.player = player;
        this.configFile = new File(MRanks.getInstance().getDataFolder().getPath(), "/user_infos/" + player.getUniqueId().toString() + ".yml");
        this.permissionAttachment = player.addAttachment(MRanks.getInstance());

        if (!configFile.exists())
            this.setUpFirstConfig();

        if (config == null)
            this.config = YamlConfiguration.loadConfiguration(configFile);

        this.setUpPermissions();
    }

    private void setUpPermissions()
    {
        this.setRank(RankEnum.valueOf(config.getString("rank").toUpperCase()));

        if (!config.isSet("permissions"))
            return;

        List<String> permissions = config.getStringList("permissions");

        for (String permission : permissions)
        {
            this.addPermission(permission);
            System.out.println("Added permission " + permission);
        }
    }

    private void setUpFirstConfig()
    {
        if (!configFile.getParentFile().exists())
            configFile.getParentFile().mkdirs();

        try
        {
            configFile.createNewFile();

            this.config = YamlConfiguration.loadConfiguration(configFile);

            config.set("created", System.currentTimeMillis());
            config.set("rank", RankEnum.DEFAULT.toString());
            saveConfig();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setRank(RankEnum rank)
    {
        this.rank = rank;

        config.set("rank", rank.toString());
        saveConfig();

        player.setDisplayName(rank.getPrefix() + player.getName() + "§f");
        player.setPlayerListName(getRank().getPrefix() + player.getName());
        MRanks.getInstance().scoreboard.getTeam(getRank().getPriority() + getRank().getName()).addEntry(player.getName());
    }

    public void addPermission(String permission)
    {
        permissionAttachment.setPermission(permission, true);
    }

    public void removePermission(String permission)
    {
        permissionAttachment.setPermission(permission, false);
    }

    private void saveConfig()
    {
        try
        {
            config.save(configFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig()
    {
        return config;
    }

    public boolean hasPermission(String str)
    {
        return permissionAttachment.getPermissible().hasPermission(str);
    }

    public RankEnum getRank()
    {
        return rank;
    }

    public Player getPlayer()
    {
        return player;
    }
}
