/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.io.github.thundersoftware.machadodeassis;

import static main.java.io.github.thundersoftware.machadodeassis.Controller.GerenciadorDeSkills.GerarFerramentasFerreiro;
import main.java.io.github.thundersoftware.machadodeassis.Controller.SkillsEvents;
import main.java.io.github.thundersoftware.machadodeassis.View.MenuSkills;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author Selrog
 */
public final class  MachadoDeAssis extends JavaPlugin{

    public static MachadoDeAssis plugin;
    @Override
    public void onEnable() {
        plugin = this;
        //GerarFerramentasFerreiro();
        getLogger().info("Plugin ativado!");
        
        getServer().getPluginManager().registerEvents(new SkillsEvents(), this);
        getServer().getPluginManager().registerEvents(new MenuSkills(), this);
        
        
        super.onEnable(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDisable() {
        
        getLogger().info("Desativado!");
        
        
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
