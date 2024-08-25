package friends.friendcoreplugin.commands;

import friends.friendcoreplugin.commands.utils.DeathsUtils;
import friends.friendcoreplugin.utils.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Deaths {

    public Deaths(){
        new CommandBase("deaths", true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                if(sender instanceof Player player){
                    player.openInventory(DeathsUtils.populateInven());
                }
                return true;
            }

            @Override
            public String getUsage() {
                return "/deaths";
            }
        }.enableDelay(5);


    }
}