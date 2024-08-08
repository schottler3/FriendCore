package friends.friendcoreplugin;

import friends.friendcoreplugin.commands.AddCommand;
import friends.friendcoreplugin.commands.Commands;
import friends.friendcoreplugin.commands.RemoveCommand;
import friends.friendcoreplugin.commands.Vote;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class FriendCorePlugin extends JavaPlugin {

    private static FriendCorePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        System.out.println("FriendCore enabled");
        new AddCommand();
        new VillagerIntercept();
        new RemoveCommand();
        new Commands();
        new Vote();
        new CartStuff();
        new NPCInteract();
        new GUIStuff();
        new VoteStuff();
        new Elevator();
        new CommandList();
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("FriendCore disabled");
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

    public void sudoBroadcast(String message){
        for (Player player : Bukkit.getOnlinePlayers()) {
            Msg.send(player, message);
        }
    }

}
