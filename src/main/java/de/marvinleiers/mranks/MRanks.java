package de.marvinleiers.mranks;

import de.marvinleiers.menuapi.MenuAPI;
import de.marvinleiers.mranks.commands.MRanksCommand;
import de.marvinleiers.mranks.ranks.RankEnum;
import de.marvinleiers.mranks.user.MRanksUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public final class MRanks extends JavaPlugin implements Listener
{
    private static final HashMap<Player, MRanksUser> mRanksUserHashMap = new HashMap<>();

    public Scoreboard scoreboard;

    @Override
    public void onEnable()
    {
        MenuAPI.setUp(this);

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("mranks").setExecutor(new MRanksCommand());

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        for (RankEnum rank : RankEnum.values())
        {
            Team team = this.scoreboard.registerNewTeam(rank.getPriority() + rank.getName());

            team.setColor(rank.getColor());
            team.setPrefix(rank.getPrefix());
        }

        Bukkit.getOnlinePlayers().stream().forEach(player -> register(player));
    }

    private void register(Player player)
    {
        mRanksUserHashMap.put(player, new MRanksUser(player));

        MRanksUser mRanksUser = getMRanksUser(player);

        player.setScoreboard(this.scoreboard);

        player.setPlayerListName(mRanksUser.getRank().getPrefix() + player.getName());
        Team team = this.scoreboard.getTeam(mRanksUser.getRank().getPriority() + mRanksUser.getRank().getName());
        team.addEntry(player.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        register(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        event.setFormat(event.getPlayer().getDisplayName() + " §7» §o" + event.getMessage());
    }

    public MRanksUser getMRanksUser(Player player)
    {
        if (!mRanksUserHashMap.containsKey(player))
            return null;

        return mRanksUserHashMap.get(player);
    }

    public static MRanks getInstance()
    {
        return getPlugin(MRanks.class);
    }
}
