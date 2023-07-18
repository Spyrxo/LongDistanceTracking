package xyz.superswolemmobs.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import xyz.superswolemmobs.LongDistanceTracking;

import java.util.ArrayList;
import java.util.List;

public class CommandChangeRenderValue extends CommandBase {

    @Override
    public String getCommandName() {
        return "changerendervalue";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "changerendervalue";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("crv");
        aliases.add("rendervalue");
        aliases.add("trackingdist");
        aliases.add("viewentitydist");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {

        // player needs to have typed in a value
        if (strings.length < 1) {
            LongDistanceTracking.getClientPlayer().addChatMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "You did not type a value"));
            return;
        }

        // attempt to handle the value change
        try {

            // get the value they typed, make sure it's not too big
            int value = Integer.parseInt(strings[0]);
            if (value > LongDistanceTracking.FIXED_MAX_DISTANCE_ALLOWED) {
                value = LongDistanceTracking.FIXED_MAX_DISTANCE_ALLOWED;
            }

            // modify the render distance weight and inform the player
            LongDistanceTracking.customRenderDistanceWeight = value;
            LongDistanceTracking.getClientPlayer().addChatMessage(
                    new ChatComponentText(EnumChatFormatting.GREEN + "Changed the tracking distance value to " + value));

        } catch (Exception ex) {
            LongDistanceTracking.getClientPlayer().addChatMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "An error has occurred when trying to change the value"));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
