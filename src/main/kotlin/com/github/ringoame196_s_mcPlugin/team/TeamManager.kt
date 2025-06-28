package com.github.ringoame196_s_mcPlugin.team

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

object TeamManager {
    // プレイヤーの所属チーム取得
    fun getPlayerTeam(player: Player): Team? {
        val scoreboard = player.scoreboard
        val teams = scoreboard.teams
        return teams.firstOrNull { team -> team.hasEntry(player.name) }
    }

    // teamName -> チーム取得
    fun getTeam(teamName: String): Team? {
        val scoreboard = Bukkit.getScoreboardManager()?.mainScoreboard ?: return null
        return scoreboard.getTeam(teamName)
    }

    fun getTeamNames(): MutableList<String> {
        val scoreboard = Bukkit.getScoreboardManager()?.mainScoreboard ?: return mutableListOf()
        return scoreboard.teams.mapNotNull { it.name }.toMutableList()
    }
}
