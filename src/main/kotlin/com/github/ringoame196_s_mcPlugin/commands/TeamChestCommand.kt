package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.TeamChestManager
import com.github.ringoame196_s_mcPlugin.TeamManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeamChestCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            val message = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return true
        }
        val player = sender
        // 基本は所属チーム OP持ちのみどのチームでも
        var openTeam = TeamManager.getPlayerTeam(player)
        if (player.isOp && args.isNotEmpty()) {
            val teamName = args[0]
            openTeam = TeamManager.getTeam(teamName)
        }

        if (openTeam == null) {
            val message = "${ChatColor.RED}チームが見つかりませんでした"
            player.sendMessage(message)
            return true
        }
        TeamChestManager.openTeamChest(player, openTeam)

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> TeamManager.getTeamNames()
            else -> mutableListOf()
        }
    }
}
