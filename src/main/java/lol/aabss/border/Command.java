package lol.aabss.border;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static lol.aabss.border.Border.borderMob;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = Brdr.getConfig("prefix");
        if (args.length == 0){
            sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix + " <reset><red>/border <reload | spawn>"));
        }
        else{
            if (args[0].equals("reload")) {
                Border.getPlugin(Border.class).reloadConfig();
                sender.sendMessage(MiniMessage.miniMessage().deserialize(Brdr.getConfig("prefix") + " <reset><yellow>Reloaded!"));
            }
            else if (args[0].equals("spawn")){
                if (sender instanceof Player p){
                    if (borderMob != null){
                        borderMob.remove();
                        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team team = board.getTeam("mob");
                        if (team != null){
                            team.unregister();
                        }
                        borderMob = null;
                    }
                    EntityType mob = EntityType.valueOf(Brdr.getConfig("border-mob"));
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), mob);
                    borderMob = (LivingEntity) entity;
                    if (Brdr.configBoolean("mob-glow")){
                        borderMob.setGlowing(true);
                        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                        int color = Integer.parseInt(Brdr.getConfig("glow-color"));
                        Team team = board.getTeam("mob");
                        if (team != null && borderMob != null){
                            team.addEntity(borderMob);
                        }
                        else{
                            board.registerNewTeam("mob").color(NamedTextColor.nearestTo(TextColor.color(color)));
                            Team newteam = board.getTeam("mob");
                            assert newteam != null;
                            newteam.addEntity(borderMob);
                        }
                    }
                    Objects.requireNonNull(borderMob.
                                    getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).
                            setBaseValue((Double) Brdr.
                                    configNumber("mob-speed"));
                    Objects.requireNonNull(borderMob.
                                    getAttribute(Attribute.GENERIC_MAX_HEALTH)).
                            setBaseValue(2048);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix + " <reset><green>Border mob spawned!"));
                }
                else{
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix + " <reset><red>This command can only be executed by players!"));
                }
            }
            else{
                sender.sendMessage(MiniMessage.miniMessage().deserialize(prefix + " <reset><red>/border <reload | spawn>"));
            }
        }
        return true;
    }
}
