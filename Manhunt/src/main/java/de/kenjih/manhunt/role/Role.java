package de.kenjih.manhunt.role;

import org.bukkit.ChatColor;

public enum Role {
    HUNTER("Hunter", ChatColor.DARK_GREEN),
    RUNNER("Runner", ChatColor.RED);

    private Role(String name, ChatColor chatColor){
        this.name = name;
        this.chatColor = chatColor;
    }

    public String getName(){
        return this.name;
    }

    public ChatColor getChatColor(){
        return this.chatColor;
    }


    private String name;
    private ChatColor chatColor;
}