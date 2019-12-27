/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.io.github.thundersoftware.machadodeassis.View;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import main.java.io.github.thundersoftware.machadodeassis.Controller.GerenciadorDeSkills;
import main.java.io.github.thundersoftware.machadodeassis.MachadoDeAssis;
import main.java.io.github.thundersoftware.machadodeassis.Model.Skills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author g4bri
 */
public class MenuSkills implements Listener{
    
    public final static String NOMEINVFERREIRO = ChatColor.GRAY+"Ferreiro";
    
    public final static Inventory FERREIRO = Bukkit.createInventory(null, 9, NOMEINVFERREIRO);
    static {
        
        
        FERREIRO.setItem(0, LimparMeta(Material.ANVIL, 1));
        FERREIRO.setItem(1, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        FERREIRO.setItem(2, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        FERREIRO.setItem(3, LimparMeta(Material.MINECART, 1));
        FERREIRO.setItem(4, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        FERREIRO.setItem(5, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        FERREIRO.setItem(6, LimparMeta(Material.WRITTEN_BOOK, 1));
        FERREIRO.setItem(7, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        FERREIRO.setItem(8, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        //[N,V,V,S,V,V,Q,V,V,]
        //The first parameter, is the slot that is assigned to. Starts counting at 0
    }
    
    public final static Inventory AdminPainel = Bukkit.createInventory(null, 27, ChatColor.MAGIC+"Admin");
    static {
        
        List<String> lore = new ArrayList<>();
        int valor = 0;
        lore.add(valor+"");
        lore.add(ChatColor.GREEN+"Clique subir ao nvl maximo!");
        valor++;
        
        AdminPainel.setItem(0, EscreverMeta(Material.ANVIL,"Ferreiro", lore));
        AdminPainel.setItem(1, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(2, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
       
        
        lore.set(0, valor+"");
        valor++;
        
        AdminPainel.setItem(3, EscreverMeta(Material.DIAMOND_PICKAXE,"Minerador", lore));
        AdminPainel.setItem(4, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(5, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        lore.set(0, valor+"");
        valor++;
        
        
        AdminPainel.setItem(6, EscreverMeta(Material.ENCHANTING_TABLE,"Encantador", lore));
        AdminPainel.setItem(7, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(8, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        
        lore.set(0, valor+"");
        valor++;
        
        
        AdminPainel.setItem(9, EscreverMeta(Material.DIAMOND_SWORD,"Espadachim", lore));
        AdminPainel.setItem(10, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(11, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        lore.set(0, valor+"");
        valor++;
        
        
        AdminPainel.setItem(12, EscreverMeta(Material.DIAMOND_AXE,"bárbaro", lore));
        AdminPainel.setItem(13, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(14, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        lore.set(0, valor+"");
        valor++;
        
        
        AdminPainel.setItem(15, EscreverMeta(Material.BOW,"Arqueiro", lore));
        AdminPainel.setItem(16, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(17, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        lore.set(0, valor+"");
        valor++;
        
       
        AdminPainel.setItem(18, EscreverMeta(Material.TOTEM_OF_UNDYING,"Artes marciais", lore));
        AdminPainel.setItem(19, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(20, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        lore.set(0, valor+"");
        valor++;
        
        
        AdminPainel.setItem(21, EscreverMeta(Material.STICK,"Mago", lore));
        AdminPainel.setItem(22, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(23, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
        AdminPainel.setItem(24, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        AdminPainel.setItem(25, LimparMeta(Material.BLACK_STAINED_GLASS_PANE, 1));
        
    }
    
    
    
    
    @EventHandler
    public void VerificarItemAtivado(PlayerInteractEvent evento){
        if(evento.hasBlock()){
            if(evento.getClickedBlock().getType() == Material.SMITHING_TABLE && evento.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK && evento.getPlayer().isSneaking())
                {
                    Inventory inv = FERREIRO;
                    List<Skills> sk = GerenciadorDeSkills.MetaDataParaLista(evento.getPlayer());
                    
                    
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GREEN+"Nível ["+GerenciadorDeSkills.ObterCorPorNivel(sk.get(0).getNvl()[0])+sk.get(0).getNvl()[0]+ChatColor.GREEN+"/"+GerenciadorDeSkills.ObterCorPorNivel(sk.get(0).getNvl()[1])+sk.get(0).getNvl()[1]+ChatColor.GREEN+"]");
                    lore.add(ChatColor.GREEN+"Xp ["+ChatColor.WHITE+ sk.get(0).getExp()[0] +ChatColor.GREEN+"/"+ChatColor.WHITE+ sk.get(0).getExp()[1]+ChatColor.GREEN+"]");
                    inv.setItem(0, EscreverMeta(inv.getItem(0),ChatColor.GREEN+"Skill "+GerenciadorDeSkills.ObterCorPorNivel(sk.get(0).getNvl()[0])+"Ferreiro",lore));
                    
                    evento.getPlayer().openInventory(FERREIRO);
                
                    
                
                }
            
            
           
        }
        
        
        
    }
    
    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        InventoryView inventory = event.getView(); // The inventory that was clicked in
        if (inventory.getTitle().equals(NOMEINVFERREIRO)) {
                    
                    
                    
                    event.setCancelled(true);
                    
                    
                    
                    if(clicked.getType() == Material.BLACK_STAINED_GLASS_PANE && player.isOp()){
                        
                        inventory.close();
                        player.openInventory(AdminPainel);
                    }
        }else if(inventory.getTitle().equals(ChatColor.MAGIC+"Admin")) {
             event.setCancelled(true);
              if(clicked.getType() != Material.BLACK_STAINED_GLASS_PANE  && player.isOp()){
                  try{
                    int cod = Integer.parseInt(clicked.getItemMeta().getLore().get(0));
                    List<Skills> sk = GerenciadorDeSkills.MetaDataParaLista(player);
                    int[] nvl = sk.get(cod).getNvl();
                    nvl[0] = nvl[1];
                    sk.get(cod).setNvl(nvl);
                    GerenciadorDeSkills.ListaParaMetaData(sk, player);
                    player.sendMessage(ChatColor.GREEN+"Feito!");
                  
                  }catch(NumberFormatException e){
                  
                    MachadoDeAssis.plugin.getLogger().log(Level.INFO, "Erro{0}", e);
                  
                  }
                  
              }
            
            
        }
        
        
        
    }
        
    
    
    
    
    
    public static ItemStack EscreverMeta(Material tipo,String titulo,List<String> lore){
        
        ItemStack is = new ItemStack(tipo);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(titulo);
        im.setLore(lore);
        is.setItemMeta(im);
        
        return is;
        
        
    }
    
    
    
    public static ItemStack EscreverMeta(ItemStack is,String titulo,List<String> lore){
        
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(titulo);
        im.setLore(lore);
        is.setItemMeta(im);
        
        return is;
        
        
    }
    
    public static ItemStack LimparMeta(Material ma,int qnt){
        ItemStack is = new ItemStack(ma, qnt);
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        im.setDisplayName(" ");
        im.setLore(lore);
        is.setItemMeta(im);
        
        return is;
    }
}
    
    

