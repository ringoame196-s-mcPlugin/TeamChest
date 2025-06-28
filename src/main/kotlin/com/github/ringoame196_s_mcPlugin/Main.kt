package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.events.TeamChestEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this

    override fun onEnable() {
        super.onEnable()
        saveResource("data.db", false)
        Data.dataFolderPath = plugin.dataFolder.path
        server.pluginManager.registerEvents(TeamChestEvent(), plugin)
        val command = getCommand("teamchest")
        command!!.setExecutor(Command())
    }
}
