package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scoreboard.Team
import java.util.UUID

object TeamChestManager {
    private val teamChests = mutableMapOf<String, Inventory>() // チームとチェスト連携
    private val playerOpenedTeamChest = mutableMapOf<UUID, String>() // プレイヤーとチェスト連携
    private val teamChestViewers = mutableMapOf<String, Int>()

    val TEAM_CHEST_NAME = "${ChatColor.DARK_GREEN}チームチェスト"
    const val GUI_SIZE = 27

    fun openTeamChest(player: Player, team: Team) {
        val teamName = team.name
        val teamChest = getTeamChest(teamName)
        playerOpenedTeamChest[player.uniqueId] = teamName
        player.openInventory(teamChest)
        additionView(teamName)
    }

    fun closeTeamChest(player: Player) {
        val teamName = playerOpenedTeamChest[player.uniqueId] ?: return
        playerOpenedTeamChest.remove(player.uniqueId)
        reduceView(teamName)
        if (teamChestViewers[teamName] == 0) {
            updateTeamChest(teamName)
        }
    }

    private fun getTeamChest(teamName: String): Inventory {
        // Dataにあれば それを返す なければ ロードする
        return teamChests[teamName] ?: loadTeamChest(teamName)
    }

    private fun loadTeamChest(teamName: String): Inventory {
        val inventory = Bukkit.createInventory(null, GUI_SIZE, TEAM_CHEST_NAME)
        teamChests[teamName] = inventory
        TeamChestStorage.load(inventory, teamName)
        return inventory
    }

    private fun updateTeamChest(teamName: String) {
        val inventory = teamChests[teamName] ?: return
        TeamChestStorage.update(inventory, teamName)
    }

    fun additionView(teamName: String) {
        val view = teamChestViewers[teamName] ?: 0
        teamChestViewers[teamName] = view + 1
    }

    fun reduceView(teamName: String) {
        val view = teamChestViewers[teamName] ?: 0
        teamChestViewers[teamName] = view - 1
        if (view > 1) teamChestViewers[teamName] = 0
    }
}
