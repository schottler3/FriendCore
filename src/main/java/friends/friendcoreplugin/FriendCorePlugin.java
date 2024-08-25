package friends.friendcoreplugin;

import friends.friendcoreplugin.commands.*;
import friends.friendcoreplugin.commands.utils.DeathsUtils;
import friends.friendcoreplugin.speedcart.CartStuff;
import friends.friendcoreplugin.speedcart.GUIStuff;
import friends.friendcoreplugin.speedcart.NPCInteract;
import friends.friendcoreplugin.speedcart.VillagerIntercept;
import friends.friendcoreplugin.tools.AnvilIntercept;
import friends.friendcoreplugin.tools.BlockBreak;
import friends.friendcoreplugin.tools.EntityDamage;
import friends.friendcoreplugin.utils.CommandList;
import friends.friendcoreplugin.utils.Msg;
import friends.friendcoreplugin.utils.VoteUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class FriendCorePlugin extends JavaPlugin {

    private static FriendCorePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        new AddCommand();
        new VillagerIntercept();
        new RemoveCommand();
        new Commands();
        new Deaths();
        new Vote();
        new CartStuff();
        new NPCInteract();
        new GUIStuff();
        new DeathsUtils();
        new VoteUtils();
        new Elevator();
        new CommandList();
        new EntityDamage();
        new AnvilIntercept();
        new BlockBreak();

        sudoBroadcast("FriendCorePlugin enabled");
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sudoBroadcast("FriendCorePlugin disabled");
    }

    public static FriendCorePlugin getInstance() {
        return instance;
    }

    public void restartSeq(int time){
        new BukkitRunnable() {
            int remainingTime = time;

            @Override
            public void run() {
                if (remainingTime == 0) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
                }
                else if(remainingTime <= 10){
                    sudoBroadcast("Server Restart in " + remainingTime + " seconds");
                }
                remainingTime--;
            }
        }.runTaskTimer(FriendCorePlugin.getInstance(), 0L, 20L);
    }

    public static void sudoBroadcast(String message){
        for (Player player : Bukkit.getOnlinePlayers()) {
            Msg.send(player, message);
        }
    }

    public static void throwError(String message){
        FriendCorePlugin.getInstance().getLogger().severe("Error: " + message);
        sudoBroadcast(message);
    }

}
