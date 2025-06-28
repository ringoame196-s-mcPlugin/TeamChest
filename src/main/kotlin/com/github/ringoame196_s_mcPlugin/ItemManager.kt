package com.github.ringoame196_s_mcPlugin

import org.bukkit.inventory.ItemStack
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Base64

object ItemManager {
    fun itemStackToBase64(item: ItemStack): String {
        val outputStream = ByteArrayOutputStream()
        ObjectOutputStream(outputStream).use { it.writeObject(item) }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    fun base64ToItemStack(base64: String): ItemStack {
        val bytes = Base64.getDecoder().decode(base64)
        return ObjectInputStream(ByteArrayInputStream(bytes)).use {
            it.readObject() as ItemStack
        }
    }
}
