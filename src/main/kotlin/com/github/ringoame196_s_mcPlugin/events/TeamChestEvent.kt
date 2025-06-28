package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.TeamChestManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

class TeamChestEvent : Listener {
    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player as? Player ?: return
        val inventory = e.view

        if (inventory.title != TeamChestManager.TEAM_CHEST_NAME) return
        TeamChestManager.updateTeamChest(player)
    }
}
