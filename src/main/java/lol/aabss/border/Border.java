package lol.aabss.border;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Border extends JavaPlugin {

    public static LivingEntity borderMob = null;

    @Override
    public void onEnable() {
        getLogger().info("Enabling Border v1.0 by aabss (big.abs) on discord");
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("border")).setExecutor(new lol.aabss.border.Command());
        Objects.requireNonNull(getCommand("border")).setTabCompleter(this);
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntityTeam(borderMob);
        assert team != null;
        team.removeEntity(borderMob);
        borderMob.remove();
        getLogger().info("Disabled Border v1.0 by aabss (big.abs) on discord");
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        completions.add("reload");
        completions.add("spawn");
        return completions;
    }

}
