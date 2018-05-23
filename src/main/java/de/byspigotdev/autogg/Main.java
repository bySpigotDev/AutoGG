package de.byspigotdev.autogg;

import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Von bySpigotDev
 * https://www.byspigotdev.de
 * LabyMod
 * Erstellt: 23.05.2018 / 10:43
 * Durch das decompilen dieses
 * Codes machst du dich
 * strafbar!
 */

public class Main extends LabyModAddon {

    private boolean enabled;
    private String ggmessage;
    private ArrayList<String> endmessages = new ArrayList<String>();

    @Override
    public void onEnable() {
        initalizeEndMessages();
        this.getApi().getEventManager().register(new MessageReceiveEvent() {
            @Override
            public boolean onReceive(String s, String s1) {
                for (String all : endmessages){
                    if (enabled){
                        if (s.contains(all)){
                            Minecraft.getMinecraft().thePlayer.sendChatMessage(ggmessage);
                        }
                    }
                }
                return false;
            }
        });
    }

    private void initalizeEndMessages(){
        endmessages.add("§7You earned §b");
        endmessages.add("§7» §c§lRUNDENSTATISTIKEN");
        endmessages.add("§cDer Server stoppt in");
        endmessages.add("§cDer Server startet in");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void loadConfig() {
        this.enabled = getConfig().has("enabled") ? getConfig().get("enabled").getAsBoolean() : true;
        this.ggmessage = getConfig().has("message") ? getConfig().get("message").getAsString() : "gg";
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        list.add( new BooleanElement( "Enabled", new ControlElement.IconData(Material.LEVER), new Consumer<Boolean>() {
            @Override
            public void accept(Boolean accepted) {
                enabled = accepted;
                getConfig().addProperty("enabled", accepted);
            }
        }, true));
        StringElement message = new StringElement("Message", new ControlElement.IconData(Material.PAPER),
                "gg", new Consumer<String>() {
            @Override
            public void accept(String accepted) {
                ggmessage = accepted;
                getConfig().addProperty("message", accepted);
            }
        });
        list.add(message);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getGGMessage() {
        return ggmessage;
    }
}
