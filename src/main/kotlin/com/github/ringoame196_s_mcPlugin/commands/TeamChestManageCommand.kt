package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.team.TeamManager
import com.github.ringoame196_s_mcPlugin.teamchest.TeamChestManager
import com.github.ringoame196_s_mcPlugin.teamchest.TeamChestStorage
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TeamChestManageCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        val subCommand = args[0]

        return when (subCommand) {
            CommandConst.CLEAR_ALL_COMMAND -> clearAllCommand(sender)
            CommandConst.CLEAR_COMMAND -> clearCommand(sender, args)
            else -> false
        }
    }

    private fun clearCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 2) return false
        val teamName = args[1]
        TeamChestStorage.deleteTeamChest(teamName)
        TeamChestManager.clear(teamName)
        val message = "${ChatColor.RED}${teamName}のチームチェストをクリアしました"
        sender.sendMessage(message)

        return true
    }

    private fun clearAllCommand(sender: CommandSender): Boolean {
        TeamChestStorage.deleteAllTeamChest()
        TeamChestManager.clearAll()
        val message = "${ChatColor.DARK_RED}${sender.name}が全てのチームチェストをクリアしました"
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOp) continue
            player.sendMessage(message)
        }
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf<String>(
                CommandConst.CLEAR_COMMAND,
                CommandConst.CLEAR_ALL_COMMAND
            )
            2 -> TeamManager.getTeamNames()
            else -> mutableListOf()
        }
    }
}
