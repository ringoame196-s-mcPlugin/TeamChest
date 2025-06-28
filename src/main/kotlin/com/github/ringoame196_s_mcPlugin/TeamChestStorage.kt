package com.github.ringoame196_s_mcPlugin

import org.bukkit.inventory.Inventory

object TeamChestStorage {
    val dbFilePath = "${Data.dataFolderPath}/data.db"
    private const val TABLE_NAME = "team_chest"
    private const val TEAM_NAME_KEY = "team_name"
    private const val SLOT_KEY = "slot"
    private const val ITEM_KEY = "item"

    private val dbManager = DBManager(dbFilePath)

    fun update(inventory: Inventory, teamName: String) {
        for (slot in 0 until inventory.size) {
            val item = inventory.getItem(slot)
            if (item != null) {
                val sql = "INSERT INTO $TABLE_NAME ($TEAM_NAME_KEY, $SLOT_KEY, $ITEM_KEY) VALUES (?, ?, ?) ON CONFLICT($TEAM_NAME_KEY, $SLOT_KEY) DO UPDATE SET $ITEM_KEY = excluded.$ITEM_KEY;"
                val itemData = ItemManager.itemStackToBase64(item)
                dbManager.executeUpdate(sql, mutableListOf(teamName, slot, itemData))
            } else {
                delete(teamName, slot)
            }
        }
    }

    fun load(inventory: Inventory, teamName: String) {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $TEAM_NAME_KEY = ?;"
        val tableData = dbManager.acquisitionValuesList(sql, mutableListOf(teamName), mutableListOf(SLOT_KEY, ITEM_KEY))

        for (data in tableData) {
            val slot = data[SLOT_KEY].toString().toInt()
            val itemBase64 = data[ITEM_KEY].toString()
            val item = ItemManager.itemStackFromBase64(itemBase64)
            inventory.setItem(slot, item)
        }
    }

    fun deleteTeamChest(teamName: String) {
        val sql = "DELETE FROM $TABLE_NAME WHERE $TEAM_NAME_KEY = ?;"
        dbManager.executeUpdate(sql, mutableListOf(teamName))
    }

    fun deleteAllTeamChest() {
        val sql = "DELETE FROM $TABLE_NAME"
        dbManager.executeUpdate(sql)
    }

    private fun delete(teamName: String, slot: Int) {
        val sql = "DELETE FROM $TABLE_NAME WHERE $TEAM_NAME_KEY = ? AND $SLOT_KEY = ?;"
        dbManager.executeUpdate(sql, mutableListOf(teamName, slot))
    }
}
