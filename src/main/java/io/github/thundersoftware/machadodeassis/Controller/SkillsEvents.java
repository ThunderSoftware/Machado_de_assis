/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.io.github.thundersoftware.machadodeassis.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.java.io.github.thundersoftware.machadodeassis.MachadoDeAssis;
import main.java.io.github.thundersoftware.machadodeassis.Model.Skills;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
/**
 *
 * @author g4bri
 */
public class SkillsEvents implements Listener{
    
    
    @EventHandler
    public void OnCraft (PrepareItemCraftEvent evento){
        if(evento.getRecipe() == null)
                 return;
        if(evento.getView().getPlayer() instanceof  Player){
            
             if(evento.getRecipe() == null)
                 return;
        
                if(!evento.isRepair())
                { 
                    
                    
                    
                    
                    Player jogador = (Player)evento.getView().getPlayer();
                    Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(0);
                    ItemStack is =   evento.getInventory().getResult();
                    ItemMeta im = is.getItemMeta();
                    int lvl = sk.getNvl()[0];
                    int lvlitem = GerenciadorDeSkills.NvlDoItemFerreiro(is);
                    
                    if(lvlitem == -1){
                        return;
                    }
                    
                    
                   
                    if(lvlitem>lvl)
                    {
                        jogador.sendMessage(ChatColor.RED+"Nível necessario "+ChatColor.WHITE+GerenciadorDeSkills.NvlDoItemFerreiro(is)+ChatColor.RED+" o seu : "+ChatColor.WHITE+ lvl);
                        evento.getInventory().setResult(null);
                        
                        return;
                    }
                       
                    
                    
                    if(im != null){
                        

                        List<String> lore = is.getItemMeta().getLore();
                        if(!im.hasLore())
                        {
                            lore = new ArrayList<>();
                        }
                            
                            String txt = ChatColor.GREEN+"Feito por: ";
                            String nome = "";



                            if(lvl >= GerenciadorDeSkills.NIVELMESTRE){
                                nome += ChatColor.BLUE;
                                
                            }else if(lvl >= GerenciadorDeSkills.NIVELAVANCADO){
                                nome += ChatColor.DARK_GREEN;
                            }else if(lvl >= GerenciadorDeSkills.NIVELEXPERIENTE){
                                nome += ChatColor.YELLOW;
                                
                            }else if(lvl >= GerenciadorDeSkills.NIVELINTERMEDIARIO){
                                nome += ChatColor.GOLD;
                            }else{
                                //Iniciante
                                nome += ChatColor.RED;
                            }
                            
                            
                            txt += nome;
                            if(im.hasLocalizedName()){
                                nome+=im.getLocalizedName();
                            }else{
                             nome+=is.getType().name().replace("_", " ");
                            }
                            
                            
                            
                            
                            txt+=jogador.getDisplayName();
                            lore.add(txt);

                            im.setLore(lore);
                            im.setDisplayName(nome);



                            is.setItemMeta(im);
                  }
                    if((int)(Math.random()*100) <= lvl){
                    
                        boolean encantavel = false;
                        for(Enchantment enc : Enchantment.values()){
                                encantavel = encantavel ||enc.canEnchantItem(is); 
                        }
                        if(encantavel){
                            
                        jogador.sendMessage(ChatColor.YELLOW+"Você pode criar um item "+ChatColor.WHITE+"excepcional"+ChatColor.YELLOW+"!");
                        is = GerenciadorDeSkills.EncantarItemAleatoriamente(is,lvl);
                        im.setDisplayName(ChatColor.DARK_RED+"[Excepcional]"+im.getDisplayName());
                        }
                    }

                        if(is.getAmount() <= 0)
                            return;

                        evento.getInventory().setResult(is);

                 }
             
            
            
            
            
        }
   
    
}
    
    @EventHandler
    public void PosCraft(InventoryClickEvent  evento){
     
            
            
            
            if(evento.getRawSlot() == 0 && evento.getInventory() instanceof CraftingInventory){
            

                Player jogador = (Player)evento.getWhoClicked();
                CraftingInventory ci = (CraftingInventory)evento.getInventory();

                if(evento.getClick()!= ClickType.NUMBER_KEY && evento.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD))
                {
                    jogador.sendMessage(ChatColor.RED+"função temporariamente removida!");
                    ci.setResult(null);
                    return;
                }
            
                
                if(evento.getClick() == ClickType.SHIFT_RIGHT || evento.getClick() == ClickType.SHIFT_LEFT ){
                    jogador.sendMessage(ChatColor.DARK_RED+"Seu xp pode não estar sendo calculado corretamente! ");
                    
                }
                
                
                
                ItemStack is =   ci.getResult();
                    
                    
                    if(is.getAmount()<=0)
                    {
                        evento.setCancelled(true);
                        return;
                    
                    }
                        
                    
                    float xp = 0;
                    
                    xp = GerenciadorDeSkills.XpParaAdicionar(is);
                    boolean lvl = GerenciadorDeSkills.AddXp(0, xp, jogador);

                    GerenciadorDeSkills.MonstrarXpAdicionadoELvlUp(xp,0, jogador, lvl);

                    jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);
               
            }
            
            
            
            
            
        
    }
    
    @EventHandler
    public void OnLogin(PlayerJoinEvent evento){
      Player jogador = evento.getPlayer();
      Skills[] sk = GerenciadorDeSkills.GerarSkillsIniciais();
      GerenciadorDeSkills.ListaParaMetaData(sk, jogador);
      
}
    
    @EventHandler
    public void ClicarNaFornalha(InventoryClickEvent evento)
    {
        Player jogador = (Player)evento.getWhoClicked();
        if(evento.getInventory() instanceof FurnaceInventory && evento.getCurrentItem()!= null && evento.getRawSlot() == 2)
        {

            
            
            FurnaceInventory fri = (FurnaceInventory)evento.getInventory();
            
            
            Furnace fr = (Furnace)fri.getHolder();
            
            
            Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(0);

            
            
            
            
            if(GerenciadorDeSkills.NvlDoItemFerreiro(fri.getResult())<=sk.getNvl()[0])
            {
                if(Math.random()*100 < sk.getNvl()[0])
                {

                    if(Math.random()*100 < sk.getNvl()[0]/10)
                    {
                        jogador.sendMessage(ChatColor.YELLOW+"Drop TRIPLO");
                        ItemStack is = fri.getResult();
                        is.setAmount(is.getAmount()*3);
                        fri.setResult(is);
                        
                    }else{
                        jogador.sendMessage(ChatColor.YELLOW+"Drop DUPLO");
                        ItemStack is = fri.getResult();
                        is.setAmount(is.getAmount()*2);
                        fri.setResult(is);

                    }
                        jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_ANVIL_USE, 10, 1);
               


                }



            }else{

                jogador.sendMessage(ChatColor.RED+"Nível necessario "+ChatColor.WHITE+GerenciadorDeSkills.NvlDoItemFerreiro(fri.getResult()));
                evento.setCancelled(true);
               
            }

        
        
        }
    }
    
    
    @EventHandler
    public void QuebrarFornalha(BlockBreakEvent evento)
    {
       if(evento.getBlock().getType() == Material.FURNACE || evento.getBlock().getType() == Material.BLAST_FURNACE){
           Furnace fr = (Furnace)evento.getBlock();
           fr.getInventory().setResult(null);
           evento.setDropItems(false);
       }
    
    }
    
    @EventHandler
    public void QuebrarItensMinerador(BlockBreakEvent evento){
        
        Block bloco = evento.getBlock();
        if(bloco.hasMetadata("Jogador"))
        {
           if(bloco.getMetadata("Jogador").get(0).asBoolean())
               return;
        }
        if(GerenciadorDeSkills.NvlDoBlocoMinerador(bloco)!=-1)
        {
        
            Player jogador = evento.getPlayer();
            Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(1);
            float xp;
            Collection<ItemStack> drops;
            if(GerenciadorDeSkills.NvlDoBlocoMinerador(bloco) <= sk.getNvl()[0])
            {
               
                
                xp = GerenciadorDeSkills.XpParaAdicionarMinerador(bloco);
                if(xp > 0)
                {
                     boolean lvl = GerenciadorDeSkills.AddXp(1, xp, jogador);
                     GerenciadorDeSkills.MonstrarXpAdicionadoELvlUp(xp,sk, jogador, lvl);
                     drops = bloco.getDrops(jogador.getInventory().getItemInMainHand());
                     
                     if(sk.getNvl()[0]>= GerenciadorDeSkills.NIVELEXPERIENTE&& (bloco.getType()== Material.IRON_ORE || bloco.getType() == Material.GOLD_ORE) && Math.random()*75<= sk.getNvl()[0])
                     {
                         int qnt = 2+(sk.getNvl()[0]/5);
                         ItemStack is;
                         ItemMeta im;
                         jogador.sendMessage(ChatColor.DARK_RED+"A sua pancada forjou alguns metais");
                         jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 10, 1);
                         bloco.getWorld().spawnParticle(Particle.LAVA,bloco.getLocation(),10);
                         
                         if(bloco.getType()== Material.IRON_ORE){
                             is = new ItemStack(Material.IRON_INGOT,qnt);
                             
                         }else{
                             is = new ItemStack(Material.GOLD_INGOT,qnt);
                         }
                            im = is.getItemMeta();
                         im.setDisplayName(ChatColor.DARK_RED+im.getDisplayName());
                         bloco.getWorld().dropItemNaturally(bloco.getLocation(),is);
                         is.setItemMeta(im);
                         
                     }
                     
                     
                     if(Math.random()*100<=sk.getNvl()[0]){
                         
                         if(Math.random()*100<=sk.getNvl()[0]/10)
                         {
                             jogador.sendMessage(ChatColor.GOLD+"Você conseguiu recuperar MUITO mais itens do que o normal");
                             for(ItemStack is:drops)
                             {
                             
                                bloco.getWorld().dropItemNaturally(bloco.getLocation(), is);
                             
                             }
                             
                
                         }else
                         {
                             
                             for(ItemStack is:drops)
                             {
                                is.setAmount(1);
                                bloco.getWorld().dropItemNaturally(bloco.getLocation(), is);
                             
                             }
                             jogador.sendMessage(ChatColor.GOLD+"Você conseguiu recuperar mais itens do que o normal");
                         }
                     
                     }
                }
            }else
            {
                jogador.sendMessage(ChatColor.RED+"Nível necessario "+ChatColor.WHITE+ GerenciadorDeSkills.NvlDoBlocoMinerador(bloco));
                evento.setCancelled(true);
            }
            
        
        }
    }
    
    
    
    
    
    @EventHandler
    public void EncantarItem(EnchantItemEvent evento)
    {
        Skills sk = GerenciadorDeSkills.MetaDataParaLista(evento.getEnchanter()).get(2);
        Player jogador = evento.getEnchanter();
        
        float xp = ((float)(Math.random()*10))+GerenciadorDeSkills.NvlDoItemFerreiro(evento.getItem());
        boolean lvl = false;
        if(Math.random()*100<= sk.getNvl()[0])
        {
            
            GerenciadorDeSkills.EncantarItemAleatoriamente(evento.getItem(), sk.getNvl()[0]); 
            jogador.sendMessage(ChatColor.GOLD+"Encantamento extra adicionado");
            
            
            if(sk.getNvl()[0]>=GerenciadorDeSkills.NIVELEXPERIENTE)
            {
                GerenciadorDeSkills.EncantarItemAleatoriamente(evento.getItem(), sk.getNvl()[0]); 
                    if(sk.getNvl()[0]>=GerenciadorDeSkills.NIVELMESTRE)
                {
                    GerenciadorDeSkills.EncantarItemAleatoriamente(evento.getItem(), sk.getNvl()[0]); 
                }
            }
        }
         
        int valor = evento.getExpLevelCost() - (sk.getNvl()[0]*100);
        if(valor < 100)
        {
            valor = 100;
            
        }
        evento.setExpLevelCost(valor);
        
        lvl = GerenciadorDeSkills.AddXp(2, xp, jogador);
        
        GerenciadorDeSkills.MonstrarXpAdicionadoELvlUp(xp, 2, jogador, lvl);
        
        
        
    }
    
    
    
    @EventHandler
    public void MarcarBloco(BlockPlaceEvent evento)
    {
        if(GerenciadorDeSkills.NvlDoBlocoMinerador(evento.getBlockPlaced())!= -1)
        {
            Block bloco = evento.getBlockPlaced();
            bloco.setMetadata("Jogador", new FixedMetadataValue(MachadoDeAssis.plugin, true));
            
        }
    
    }
    
    
    
    private boolean AtaqueDoJogador(EntityDamageByEntityEvent evento){
    
           return evento.getDamager() instanceof Player && evento.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK;
    
    }
    
    
    @EventHandler
    public void AdicionarXp(EntityDamageByEntityEvent evento){
         
            if(AtaqueDoJogador(evento)){
                Player jogador = (Player) evento.getDamager();
                int  CodItem;
                
                CodItem = GerenciadorDeSkills.ObterCodSkillCombatePorItem(jogador.getInventory().getItemInMainHand());
                if(CodItem >= 0){
                    
                GerenciadorDeSkills.AdicionarXpCombate(jogador,evento.getEntity(),CodItem,true);

                }
               
                
                
                
            }
        
        
        
    }
    
    
    
    
    
    @EventHandler
    public void HabilidadesEspadachim(EntityDamageByEntityEvent evento){
    
        
        if(AtaqueDoJogador(evento)){
            Player jogador = (Player)evento.getDamager();
            if(GerenciadorDeSkills.ObterCodSkillCombatePorItem(jogador.getInventory().getItemInMainHand())!=3){
                return;
            }
            
            boolean tecnica = false;
            
            
            Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(4);
            int nvl = sk.getNvl()[0];
            if(nvl> GerenciadorDeSkills.NIVELINICIANTE){
                
                double danoextra = 0;
                double danobase = evento.getDamage();
                double danofinal = danobase;
                
                //Fundamentos da arte mais 20% de dano a cada 5 níveis
                {
                    
                    int danofundamentos = ((int)nvl/5)+1;
                   
                    danoextra += danobase *(((danofundamentos*20)/100)+1);
                    danofinal += danoextra;
                    danoextra = 0;
                }    
                
                
                if(nvl>=GerenciadorDeSkills.NIVELINTERMEDIARIO){
                   
                    //Ponto vital = a cada 1 nvl +% chance de acerto critico que da + (nvl*10)% de dano
                    double valor = nvl/100.000f ;
                    if(Math.random()<= valor){
                        
                        int dano = ((int)nvl*10);
                        danoextra += danobase *(dano/100);
                        
                        evento.getEntity().getWorld().spawnParticle(Particle.CRIT, evento.getEntity().getLocation(), 20);
                        jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 10, 1);
               
                        danofinal += danoextra;
                        danoextra = 0;
                        jogador.sendMessage(ChatColor.GREEN+"Dano crítico!");
                        
                        
                    }
                    
                    
                    
                    

                    if(nvl>=GerenciadorDeSkills.NIVELEXPERIENTE){
                            
                        valor = (nvl/50.000f);
                        //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor && !tecnica){
                                
                                
                                
                               ((Creature)(evento.getEntity())).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10,2));
                               jogador.sendMessage(ChatColor.GREEN+"Usou o golpe ["+ChatColor.WHITE+"Lâmina gravitacional"+ChatColor.GREEN+"]!");
                               (evento.getEntity()).getWorld().playSound(jogador.getLocation(), Sound.ENTITY_PLAYER_SMALL_FALL, 10, 1);
               
                               
                               ((Creature)(evento.getEntity())).setFallDistance(6+(nvl/5)); 
                                int dano = ((int)nvl/10);
                                danoextra += (danobase * (1.5))+ dano;
                                danofinal += danoextra;
                                danoextra = 0;
                                (evento.getEntity()).getWorld().spawnParticle(Particle.CLOUD,(evento.getEntity()).getLocation(), 250);
                                
                               
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Lâmina gravitacional"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                                
                                
                            }
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        if(nvl>=GerenciadorDeSkills.NIVELAVANCADO){
                        
                            valor = (nvl/75.000f);
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                ((Creature)(evento.getEntity())).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 2,5));
                                jogador.sendMessage(ChatColor.GREEN+"Usou o golpe ["+ChatColor.WHITE+"Lâmina venenosa"+ChatColor.GREEN+"]!");
                                
                                
                                
                                
                                int dano = ((int)nvl/10);
                                danoextra += (danobase * (1.3))+dano;
                                danofinal += danoextra;
                                danoextra = 0;
                                
                                (evento.getEntity()).getWorld().spawnParticle(Particle.DRAGON_BREATH,(evento.getEntity()).getLocation(), 250);
                                (evento.getEntity()).getWorld().playSound(jogador.getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 10, 1);
               
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Lâmina venenosa"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }    
                            

                            
                            
                            
                            if(nvl>=GerenciadorDeSkills.NIVELMESTRE){

                                valor = (nvl/150.000f);
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                int dano = ((int)nvl*10);
                                danoextra += danobase *(dano/250);

                                evento.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, evento.getEntity().getLocation(), 20);
                                danofinal += danoextra;
                                danoextra = 0;
                                
                                
                                ((Creature)(evento.getEntity())).setFireTicks(10);
                                
                                jogador.sendMessage(ChatColor.GREEN+"Usou o golpe ["+ChatColor.WHITE+"Lâmina do dragão"+ChatColor.GREEN+"]!");
                                
                                (evento.getEntity()).getWorld().spawnParticle(Particle.LAVA,(evento.getEntity()).getLocation(), 25);
                                (evento.getEntity()).getWorld().playSound(jogador.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
               
                                
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Lâmina venenosa"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }
                                





                            }    
                        }  
                    }
                }
                
                
                danofinal+=danoextra;
                
                evento.setDamage(danofinal);
            }
            
            
        
        
        }
        
    
    
    }
    
    
    @EventHandler
    public void HabilidadesBarbaro(EntityDamageByEntityEvent evento){
         
            
        
        
        if(AtaqueDoJogador(evento)){
            Player jogador = (Player)evento.getDamager();
            if(GerenciadorDeSkills.ObterCodSkillCombatePorItem(jogador.getInventory().getItemInMainHand())!=4){
                return;
            }
            
            boolean tecnica = false;
            
            
            Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(3);
            int nvl = sk.getNvl()[0];
            if(nvl> GerenciadorDeSkills.NIVELINICIANTE){
                
                double danoextra = 0;
                double danobase = evento.getDamage();
                double danofinal = danobase;
                
                //Brutaliidade mais 30% de dano a cada 5 níveis
                {
                    
                    int danofundamentos = ((int)nvl/5)+1;
                   
                    danoextra += danobase *(((danofundamentos*30)/100)+1);
                    danofinal += danoextra;
                    danoextra = 0;
                }    
                
                
                if(nvl>=GerenciadorDeSkills.NIVELINTERMEDIARIO){
                   
                    //Ponto vital = a cada 1 nvl +% chance de acerto critico que da + (nvl*10)% de dano
                    double valor = nvl/100.000f ;
                    if(Math.random()<= valor){
                        
                        int dano = ((int)nvl);
                        danoextra += (danobase * (1.25+(nvl/100))) + dano;
                        
                        jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10, 1);
                        evento.getEntity().getWorld().spawnParticle(Particle.WATER_SPLASH, evento.getEntity().getLocation(), 20);
                        danofinal += danoextra;
                        
                        danoextra = 0;
                        jogador.sendMessage(ChatColor.GREEN+"Golpe brutal!");
                        
                        
                    }
                    
                    
                    
                    

                    if(nvl>=GerenciadorDeSkills.NIVELEXPERIENTE){
                            
                        valor = (nvl/120.000f);
                        //explosão esmagadora
                            if(Math.random()<=valor){
                                
                                
                                
                               jogador.sendMessage(ChatColor.GREEN+"Usou o golpe ["+ChatColor.WHITE+"explosão esmagadora"+ChatColor.GREEN+"]!");
                               jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
               
                               ((Creature)(evento.getEntity())).setFallDistance(6+(nvl/5)); 
                                int dano = ((int)nvl/10);
                                danoextra += (danobase * (1.85))+dano;
                                danofinal += danoextra;
                                danoextra = 0;
                                (evento.getEntity()).getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,(evento.getEntity()).getLocation(), 250);
                                
                               
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"explosão esmagadora"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                                
                                
                            }
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        if(nvl>=GerenciadorDeSkills.NIVELAVANCADO){
                        
                            valor = (nvl/140.000f);
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                jogador.sendMessage(ChatColor.GREEN+"Usou o golpe ["+ChatColor.WHITE+"Terremoto caótico"+ChatColor.GREEN+"]!");
                                jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_PLAYER_BIG_FALL, 10, 1);
               
                                
                                
                                
                                int dano = ((int)nvl/9);
                                danoextra += danobase * (1.3) + dano;
                                danofinal += danoextra;
                                danoextra = 0;
                                (evento.getEntity()).getWorld().spawnParticle(Particle.BLOCK_DUST,(evento.getEntity()).getLocation(), 500);
                                
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"terremoto caótico"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }    
                            

                            
                            
                            
                            if(nvl>=GerenciadorDeSkills.NIVELMESTRE){

                                valor = (nvl/150.000f);
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                int dano = ((int)nvl/5);
                                danoextra += danobase*2.50f *(dano*(1.50f+nvl/100)) + dano;

                                evento.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, evento.getEntity().getLocation(), 20);
                                 jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 15, 1);
               
                                danofinal += danoextra;
                                danoextra = 0;
                                
                                
                                
                                jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
               
                                jogador.sendMessage(ChatColor.GOLD+"Usou o golpe ["+ChatColor.WHITE+"Explosão final"+ChatColor.GOLD+"]!");
                                
                                (evento.getEntity()).getWorld().spawnParticle(Particle.EXPLOSION_LARGE,(evento.getEntity()).getLocation(), 25);
                                
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Explosão final"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }
                                





                            }    
                        }  
                    }
                }
                
                
                danofinal+=danoextra;
                
                evento.setDamage(danofinal);
            }
            
            
        
        
        
        
        
        
        
        }
        
        
        
    }
    
 
    @EventHandler
    public void HabilidadesDesarmado(EntityDamageByEntityEvent evento){
         
        if(AtaqueDoJogador(evento)){
            
                
        
        if(AtaqueDoJogador(evento)){
            Player jogador = (Player)evento.getDamager();
            if(GerenciadorDeSkills.ObterCodSkillCombatePorItem(jogador.getInventory().getItemInMainHand())!=7){
                return;
            }
            
            boolean tecnica = false;
            
            
            Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(7);
            int nvl = sk.getNvl()[0];
            if(nvl> GerenciadorDeSkills.NIVELINICIANTE){
                
                double danoextra = 0;
                double danobase = evento.getDamage();
                double danofinal = danobase;
                
                //Fundamentos mais 50% de dano a cada 5 níveis
                {
                    
                    int danofundamentos = ((int)nvl/5)+1;
                   
                    danoextra += danobase *(((danofundamentos*50)/100)+(nvl/2));
                    danofinal += danoextra;
                    danoextra = 0;
                }    
                
                
                if(nvl>=GerenciadorDeSkills.NIVELINTERMEDIARIO){
                   
                    //Ponto vital = a cada 1 nvl +% chance de acerto critico que da + (nvl*10)% de dano
                    double valor = (nvl/(120.000f - nvl*2));
                    if(Math.random()<= valor){
                        
                        int dano = ((int)nvl-(nvl/10+nvl));
                        danoextra += (danobase * (1.25+(nvl/100-nvl))) + dano;
                        
                        jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 10, 1);
                        evento.getEntity().getWorld().spawnParticle(Particle.LAVA, evento.getEntity().getLocation(), 30);
                        evento.getEntity().setFireTicks(50+nvl*2);
                        danofinal += danoextra;
                        
                        danoextra = 0;
                        jogador.sendMessage(ChatColor.GOLD+"Punho flamejante!");
                        
                        if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe punho flamejante!"));
                                   
                                    
                        }
                        
                        
                    }
                    
                    
                    
                    

                    if(nvl>=GerenciadorDeSkills.NIVELEXPERIENTE){
                            
                        valor = (nvl/(130.000f - nvl*2));
                        //explosão esmagadora
                            if(Math.random()<=valor){
                                
                                
                                
                               
                               jogador.getWorld().playSound(jogador.getLocation(), Sound.WEATHER_RAIN, 10, 1);
                               (evento.getEntity()).getWorld().spawnParticle(Particle.WATER_WAKE,(evento.getEntity()).getLocation(), 250);
                                
                                jogador.sendMessage(ChatColor.BLUE+"Punho da chuva!");
                               
                               
                               ((Creature)(evento.getEntity())).setFallDistance(6+(nvl/5)); 
                                int dano = ((int)nvl/10);
                                danoextra += (danobase * (1.25+(nvl/100-nvl))) + dano;
                                
                                if(jogador.getWorld().hasStorm() || jogador.isSwimming()){
                                    
                                    danoextra += (danobase * (1.10f+(nvl/100-nvl))) + dano;
                                    
                                }else if(!jogador.getWorld().hasStorm())
                                {
                                    boolean resultado = Math.random() <= nvl/100;
                                    
                                    if(resultado){
                                        jogador.getWorld().setStorm(true);
                                        jogador.sendMessage(ChatColor.BLUE+"Seu golpe iniciou uma tempestade");
                                    }
                                   
                                }
                                
                               
                                
                                danofinal += danoextra;
                                danoextra = 0;
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe !"));
                                   
                                    
                               }
                                
                                
                            }
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        if(nvl>=GerenciadorDeSkills.NIVELAVANCADO){
                        
                            valor = (nvl/(140.000f - nvl*3));
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                jogador.sendMessage(ChatColor.DARK_PURPLE+"Punho vampirico!");
                                jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 10, 1);
               
                                
                                
                                
                                int dano = ((int)nvl/5);
                                danoextra += danobase * (3.5f) + dano;
                                
                                if(jogador.getHealth()+(danoextra/5) > 20){
                                    jogador.setHealth(20);
                                }else{
                                     jogador.setHealth(jogador.getHealth()+(danoextra/10));
                                }
                               
                                danofinal += danoextra;
                                danoextra = 0;
                                (evento.getEntity()).getWorld().spawnParticle(Particle.SMOKE_LARGE,(evento.getEntity()).getLocation(), 200);
                                jogador.getWorld().spawnParticle(Particle.SMOKE_NORMAL, jogador.getLocation(), 20);
                                
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Punho vampirico"+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }    
                            

                            
                            
                            
                            if(nvl>=GerenciadorDeSkills.NIVELMESTRE){

                                valor = (nvl/(150.000f - nvl*3));
                            //Lamina gravitacional - chance de causar pararisia nos inimigos 
                            if(Math.random()<=valor){
                               
                                
                                int dano = ((int)nvl/5);
                                danoextra += danobase*4.50f *(dano*(1.50f+nvl/100)) + dano;

                                evento.getEntity().getWorld().spawnParticle(Particle.FLASH, evento.getEntity().getLocation(), 50);
                                jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_METAL_HIT, 15, 1);
               
                                danofinal += danoextra;
                                danoextra = 0;
                                evento.getEntity().getWorld().strikeLightning(evento.getEntity().getLocation());
                                
                                
                                jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
               
                                jogador.sendMessage(ChatColor.AQUA+"Punho do "+ChatColor.MAGIC+"Trovão"+ChatColor.RESET);
                                
                                (evento.getEntity()).getWorld().spawnParticle(Particle.EXPLOSION_LARGE,(evento.getEntity()).getLocation(), 25);
                                
                               
                               if(evento.getEntity() instanceof Player){
                               
                                   ((Player)(evento.getEntity())).sendMessage((ChatColor.RED+jogador.getName()+" usou o golpe ["+ChatColor.WHITE+"Punho do "+ChatColor.MAGIC+"Trovão"+ChatColor.RESET+ChatColor.RED+"]!"));
                                   
                                    
                               }
                            }
                                





                            }    
                        }  
                    }
                }
                
                
                danofinal+=danoextra;
                
                evento.setDamage(danofinal);
            }
            
            
        
        
        
        
        
        
        
        }
        
        
        
        }
    }
    
    
       @EventHandler
    public void HabilidadesArqueiro(EntityDamageByEntityEvent evento){
    
      
        
        if (evento.getEntity() instanceof Creature){
            Creature alvo = (Creature) evento.getEntity();
            if (evento.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) evento.getDamager();
                if (arrow.getShooter() instanceof Player){

                    Player jogador = (Player)arrow.getShooter();
                    if(GerenciadorDeSkills.ObterCodSkillCombatePorItem(jogador.getInventory().getItemInMainHand())!=5){
                        return;
                    }

                    boolean tecnica = false;


                    Skills sk = GerenciadorDeSkills.MetaDataParaLista(jogador).get(7);
                    int nvl = sk.getNvl()[0];
                    if(nvl> GerenciadorDeSkills.NIVELINICIANTE){

                        double danoextra = 0;
                        double danobase = evento.getDamage();
                        double danofinal = danobase;

                        //Fundamentos mais 50% de dano a cada 5 níveis
                        {

                            int danofundamentos = ((int)nvl/10)+1;

                            danoextra += danobase *(((danofundamentos*25)/100)+(nvl/2));
                            danofinal += danoextra;
                            danoextra = 0;
                        }    


                        if(nvl>=GerenciadorDeSkills.NIVELINTERMEDIARIO){

                            //Ponto vital = a cada 1 nvl +% chance de acerto critico que da + (nvl*10)% de dano
                            double valor = (nvl/(100.000f));
                            if(Math.random()<= valor){

                                int dano = (nvl/10);
                                danoextra += (danobase * (1.25)) + dano;

                                jogador.getWorld().spawnParticle(Particle.BUBBLE_COLUMN_UP, evento.getEntity().getLocation(), 30);
                                ((Creature)(evento.getEntity())).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10,2));
                                danofinal += danoextra;

                                danoextra = 0;
                                
                                
                                jogador.sendMessage(ChatColor.DARK_GREEN+"Flexa venenosa!");



                            }





                            if(nvl>=GerenciadorDeSkills.NIVELEXPERIENTE){

                                valor = (nvl/(100.000f));
                                //explosão esmagadora
                                    if(Math.random()<=valor){




                                       jogador.getWorld().playSound(jogador.getLocation(), Sound.AMBIENT_CAVE, 10, 1);
                                       (evento.getEntity()).getWorld().spawnParticle(Particle.FLASH,(evento.getEntity()).getLocation(), 250);

                                        jogador.sendMessage(ChatColor.BLACK+"Flexa da confusão");

                                        
                                        ((Creature)(evento.getEntity())).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10,2));

                                        ((Creature)(evento.getEntity())).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10,2));

                                        int dano = ((int)nvl/10);
                                        danoextra += (danobase * (1.15)) + dano;

                                        danofinal += danoextra;
                                        danoextra = 0;


                                    }









                                if(nvl>=GerenciadorDeSkills.NIVELAVANCADO){

                                    valor = (nvl/(100.000f));
                                    //Lamina gravitacional - chance de causar pararisia nos inimigos 
                                    if(Math.random()<=valor){


                                        jogador.sendMessage(ChatColor.GREEN+"Flexa dupla");
                                        jogador.getWorld().playSound(jogador.getLocation(), Sound.ENTITY_ARROW_SHOOT, 10, 1);

                                        Arrow bulletArrow = (evento.getEntity()).getWorld().spawnArrow((evento.getEntity()).getLocation(),new Vector(0,0,0), 1, 0);
                                        bulletArrow.setVelocity(jogador.getLocation().getDirection().multiply(10));
                                        bulletArrow.setShooter(jogador);

                                        int dano = ((int)nvl/5);
                                        danoextra += danobase * (3.5f) + dano;

                                        

                                        danofinal += danoextra;
                                        danoextra = 0;
                                        jogador.getWorld().spawnParticle(Particle.SMOKE_NORMAL, jogador.getLocation(), 20);

                                    }    





                                    if(nvl>=GerenciadorDeSkills.NIVELMESTRE){

                                        valor = (nvl/(100.000f));
                                    //Lamina gravitacional - chance de causar pararisia nos inimigos 
                                    if(Math.random()<=valor){


                                        int dano = ((int)nvl/5);
                                        danoextra += danobase*4.50f *(dano*(1.50f+nvl/100)) + dano;

                                        evento.getEntity().getWorld().spawnParticle(Particle.FLASH, evento.getEntity().getLocation(), 50);
                                        jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_METAL_HIT, 15, 1);

                                        danofinal += danoextra;
                                        danoextra = 0;
                                        evento.getEntity().getWorld().strikeLightning(evento.getEntity().getLocation());

                                        
                                        for(int i = 0; i< nvl/5;i++){
                                            
                                            
                                            Arrow bulletArrow = (evento.getEntity()).getWorld().spawnArrow((evento.getEntity()).getLocation(),new Vector(0,0,0), 1, 0);
                                            bulletArrow.setVelocity(jogador.getLocation().getDirection().multiply(10));
                                            bulletArrow.setShooter(jogador);

                                            
                                        }

                                        jogador.getWorld().playSound(jogador.getLocation(), Sound.BLOCK_ANVIL_HIT, 10, 1);

                                        jogador.sendMessage(ChatColor.AQUA+"Flexas de "+ChatColor.MAGIC+"Ártemis"+ChatColor.RESET);

                                        (evento.getEntity()).getWorld().spawnParticle(Particle.SPELL_MOB,(evento.getEntity()).getLocation(), 25);

                                    }






                                    }    
                                }  
                            }
                        }


                        danofinal+=danoextra;

                        evento.setDamage(danofinal);
                    }





















                    
                    
                }
            }
        }
        
        
        
    
    
    }
    
    
    @EventHandler
    public void HabilidadesMago(EntityDamageByEntityEvent evento){
         
            
        if(AtaqueDoJogador(evento)){
            
        
        
        
        
        }
        
        
        
    }
    
    
    
    
}
