package ch.toothwit.anticrashclient.main; 

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent; 

public class AntiCrashClient extends JavaPlugin {
     
    private List<PacketType> packets = new ArrayList<>(); 
    
    @Override 
    public void onEnable() 
    {
        packets.add(PacketType.Play.Client.SET_CREATIVE_SLOT); 
        packets.add(PacketType.Play.Client.CUSTOM_PAYLOAD); 
        
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, packets, ListenerOptions.INTERCEPT_INPUT_BUFFER) {  
            
            @Override
           public void onPacketReceiving(PacketEvent event)
           {
               int remaining = event.getNetworkMarker().getInputBuffer().remaining(); 
               
               if(remaining > 10000) { 
                   if(event.getPlayer() != null) { 
                       event.getPlayer().sendMessage("Tja... Funktioniert nicht, wa ? "); 
                       event.getPlayer().setGameMode(GameMode.SURVIVAL); 
                       System.out.println("Player "+event.getPlayer().getName()+" tried to crash the server using CrashTestClient"); 
                   }
                   event.setCancelled(true); 
               }
               
           }
            
        }); 
         
    } 
    
}
