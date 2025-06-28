package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this

    companion object {
        lateinit var instance: Main
            private set
    }

    override fun onEnable() {
        super.onEnable()
        saveResource("data.db", false)
        server.pluginManager.registerEvents(Events(), plugin)
        val command = getCommand("teamchest")
        command!!.setExecutor(Command())
    }
}
