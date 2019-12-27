/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.io.github.thundersoftware.machadodeassis.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.io.github.thundersoftware.machadodeassis.MachadoDeAssis;
import main.java.io.github.thundersoftware.machadodeassis.Model.Skills;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author g4bri
 */
public class GerenciadorDeSkills {
    
    
    public final static int NIVELINICIANTE = 0;
    public final static int NIVELINTERMEDIARIO = 5;
    public final static int NIVELEXPERIENTE = 10;
    public final static int NIVELAVANCADO = 15;
    public final static int NIVELMESTRE = 20;
    public final static int SEMNIVEL = -1;
    
    
    public final static float XPGANHOINICIANTE = 0.5f;
    public final static float XPGANHOINTERMEDIARIO = 5f;
    public final static float XPGANHOEXPERIENTE = 10f;
    public final static float XPGANHOAVANCADO = 15f;
    public final static float XPGANHOMESTRE = 20f;
    
    public final static Material[] ItensEspadachim = {Material.WOODEN_SWORD,Material.STONE_SWORD,Material.GOLDEN_SWORD,Material.IRON_SWORD,Material.DIAMOND_SWORD};
    public final static Material[] ItensBarbaro = {Material.WOODEN_AXE,Material.STONE_AXE,Material.GOLDEN_AXE,Material.IRON_AXE,Material.DIAMOND_AXE,Material.TRIDENT};
    public final static Material[] ItensArqueiro = {Material.CROSSBOW,Material.BOW};
    public final static String[] NomesLoreDoMago = { ChatColor.MAGIC+""+ChatColor.AQUA+"Abc"+ChatColor.RESET+ChatColor.AQUA+"Magia"+ChatColor.MAGIC+"ABC"}; 
    public final static Material[] ItensDesarmado = {Material.AIR,null};
   
    
    public static final String Patch = "Skills/";
    
    
    public static boolean SalvarSkills(List<Skills> skills, String nomedojogador){
       MachadoDeAssis.plugin.getConfig().set(nomedojogador, skills);
        try {
            MachadoDeAssis.plugin.getConfig().save(Patch);
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorDeSkills.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       return true;
    }
    
    public static List<Skills> CarregarSkills(String nomeJogador){
        try {
            MachadoDeAssis.plugin.getConfig().load(Patch);
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(GerenciadorDeSkills.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
       return (List<Skills>)MachadoDeAssis.plugin.getConfig().get(nomeJogador);
    }
    
    public static boolean SalvarSkills(Skills[] skills, String nomedojogador){
        List<Skills> sk = new ArrayList<Skills>();
        sk.addAll(Arrays.asList(skills));
        
        
         return SalvarSkills(sk, nomedojogador);
    }
    
    public static Skills[] GerarSkillsIniciais(){
        Skills[] sk = new Skills[8];
        sk[0] = new Skills(0, "Ferreiro");
        sk[1] = new Skills(1, "Minerador",new float[]{0,15,0.5f},new int[]{1,25});
        sk[2] = new Skills(2, "Encantador");
        sk[3] = new Skills(3,"Espadachim");
        sk[4] = new Skills(4,"Bárbaro");
        sk[5] = new Skills(5,"Arqueiro");
        sk[6] = new Skills(6,"Mago");
        sk[7] = new Skills(7,"Artista marcial");
        return sk;
    }
    
    public static boolean ListaParaMetaData(List<Skills> sk, Player jogador){
       
        for(int i =0; i< sk.size();i++){
            
            jogador.setMetadata(sk.get(i).getNomeSkill(),new FixedMetadataValue(MachadoDeAssis.plugin, sk.get(i)));
                 
        }
        
       return true; 
    }
    
    public static boolean ListaParaMetaData(Skills[] sk, Player jogador){
       
        List<Skills> skl = new ArrayList<Skills>();
        skl.addAll(Arrays.asList(sk));
        
       return ListaParaMetaData(skl, jogador); 
    }
        
    public static List<Skills>  MetaDataParaLista(Player jogador){

        List<Skills> sk = null;
        Skills[] skl = GerarSkillsIniciais();
        List<MetadataValue> mt;
        if(jogador.hasMetadata(skl[0].getNomeSkill()))
        {
            
            sk = new ArrayList<>();
            for (Skills skl1 : skl) {
                mt = jogador.getMetadata(skl1.getNomeSkill());
                for (MetadataValue metadataValue : mt) {
                    if(metadataValue.getOwningPlugin().getName().equals(MachadoDeAssis.plugin.getName())){
                        if(metadataValue.value() instanceof Skills){
                            sk.add((Skills)metadataValue.value());
                            break;
                        }
                    }
                }
            }
            
            
        }
            
        return sk;
    }
        
    
    
    public static boolean AddXp(int codskill,float qnt,Player jogador) {
        
        boolean valor;
        
        
        List<Skills> skl = MetaDataParaLista(jogador);
        Skills[] sk = new Skills[skl.size()];
        
        for(int i = 0; i < skl.size(); i++){
            sk[i] = skl.get(i);
        }
        valor =  sk[codskill].AddXp(qnt);
        
        ListaParaMetaData(sk, jogador);
        
        return valor;
    }
    
    
    
    public static int NvlDoItemFerreiro(ItemStack is){
       
        switch(is.getType()){
            case WOODEN_AXE:
                      return NIVELINICIANTE;
                     
            case WOODEN_HOE:
                      return NIVELINICIANTE;
                     
            case WOODEN_SHOVEL:
                      return NIVELINICIANTE;
                     
            case WOODEN_PICKAXE:
                      return NIVELINICIANTE;
                     
            case WOODEN_SWORD:
                      return NIVELINICIANTE;
                      
            case LEATHER_HELMET:
                      return NIVELINICIANTE;
                      
            case LEATHER_CHESTPLATE:
                      return NIVELINICIANTE;
                      
            case LEATHER_LEGGINGS:
                      return NIVELINICIANTE;
            case LEATHER:
                      return NIVELINICIANTE;
                      
            case LEATHER_BOOTS:
                      return NIVELINICIANTE;
                     
            
                     
            
                     
            
                      
                      
           //INTERMEDIARIO  
            case STONE_AXE:
                      return NIVELINTERMEDIARIO;
                     
            case STONE_HOE:
                      return NIVELINTERMEDIARIO;
                     
            case STONE_SHOVEL:
                      return NIVELINTERMEDIARIO;
                     
            case STONE_PICKAXE:
                      return NIVELINTERMEDIARIO;
                     
            case STONE_SWORD:
                      return NIVELINTERMEDIARIO;
                      
            case CHAINMAIL_HELMET:
                       return NIVELINTERMEDIARIO;
                     
            case CHAINMAIL_CHESTPLATE:
                      return NIVELINTERMEDIARIO;
                     
            case CHAINMAIL_LEGGINGS:
                      return NIVELINTERMEDIARIO;
                     
            case CHAINMAIL_BOOTS:
                      return NIVELINTERMEDIARIO;
                      
            //EXPERIENTE  
            case IRON_AXE:
                      return NIVELEXPERIENTE;
                     
            case IRON_HOE:
                      return NIVELEXPERIENTE;
                     
            case IRON_SHOVEL:
                      return NIVELEXPERIENTE;
                     
            case IRON_PICKAXE:
                      return NIVELEXPERIENTE;
                     
            case IRON_SWORD:
                      return NIVELEXPERIENTE;
            
            case BOW:
                      return NIVELEXPERIENTE;
            case SHIELD:
                      return NIVELEXPERIENTE;
            case IRON_INGOT:
                return NIVELEXPERIENTE;
                      
            case IRON_HELMET:
                       return NIVELEXPERIENTE;
                     
            case IRON_CHESTPLATE:
                      return NIVELEXPERIENTE;
                     
            case IRON_LEGGINGS:
                      return NIVELEXPERIENTE;
                     
            case IRON_BOOTS:
                      return NIVELEXPERIENTE;
            //Avancado  
            case GOLDEN_AXE:
                      return NIVELAVANCADO;
                     
            case GOLDEN_HOE:
                      return NIVELAVANCADO;
                     
            case GOLDEN_SHOVEL:
                      return NIVELAVANCADO;
                     
            case GOLDEN_PICKAXE:
                      return NIVELAVANCADO;
                     
            case GOLDEN_SWORD:
                      return NIVELAVANCADO;
                      
            case CROSSBOW:
                      return NIVELAVANCADO;
            case GOLD_INGOT:
                return NIVELAVANCADO;
                      
            case GOLDEN_HELMET:
                      return NIVELAVANCADO;
            case GOLDEN_CHESTPLATE:
                      return NIVELAVANCADO;
            case GOLDEN_LEGGINGS:
                      return NIVELAVANCADO;
            case GOLDEN_BOOTS:
                      return NIVELAVANCADO;
                      
           //MESTRE  
            case DIAMOND_AXE:
                      return NIVELMESTRE;
                     
            case DIAMOND_HOE:
                      return NIVELMESTRE;
                     
            case DIAMOND_SHOVEL:
                      return NIVELMESTRE;
                     
            case DIAMOND_PICKAXE:
                      return NIVELMESTRE;
                     
            case DIAMOND_SWORD:
                      return NIVELMESTRE;
                      
            case TRIDENT:
                      return NIVELMESTRE;
                      
            case DIAMOND_HELMET:
                      return NIVELMESTRE;
            case DIAMOND_CHESTPLATE:
                      return NIVELMESTRE;
            case DIAMOND_LEGGINGS:
                      return NIVELMESTRE;
            case DIAMOND_BOOTS:
                      return NIVELMESTRE;
                    default:
                        return NIVELINICIANTE;
        }
    
    }
    
    
    
    public static float XpParaAdicionar(ItemStack is){
       
        return XpParaAdicionar(NvlDoItemFerreiro(is));
        
    }
    
    public static float XpParaAdicionar(int nvl){
        
        switch(nvl){
            case NIVELINICIANTE:
                return XPGANHOINICIANTE;
                
                case NIVELINTERMEDIARIO:
                return XPGANHOINTERMEDIARIO;
                
                case NIVELEXPERIENTE:
                return XPGANHOEXPERIENTE;
                
                case NIVELAVANCADO:
                return XPGANHOAVANCADO;
                
                case NIVELMESTRE:
                return XPGANHOMESTRE;
                    default:
                        return 0f;
        
        }
    }
    
    
    
     public static ItemStack EncantarItemAleatoriamente(ItemStack is,int lvl){
        int rep = (int)((lvl/10)+1);
        if(NvlDoItemFerreiro(is)==NIVELAVANCADO)
            rep*=3;
        
        
            
            
        List<Enchantment> enc = new ArrayList<>();

           for (Enchantment value : Enchantment.values()) {
               if(value.canEnchantItem(is))
               enc.add(value);
            }


          for(int i = 1; i <= rep; i++){
              
                    if (enc.size() > i) {
                       Collections.shuffle(enc);
                       Enchantment chosen = enc.get(i-1);
                       is.addEnchantment(chosen, 1 + (int) (Math.random() * ((chosen.getMaxLevel() - 1) + 1)));
                   }
           }
        
           return is;
    }
    
    
     public static int NvlDoBlocoMinerador(Block bloco)
     {
         switch(bloco.getType())
         {
           //Iniciante
             case STONE:
                     return NIVELINICIANTE;
            case DIORITE:
                        return NIVELINICIANTE;
            case ANDESITE:
                     return NIVELINICIANTE;
            case GRANITE:
                        return NIVELINICIANTE;
                        
                        
           //Intermediario
            case COAL_ORE:
                     return NIVELINTERMEDIARIO;
            case SANDSTONE:
                     return NIVELINTERMEDIARIO;
            case RED_SANDSTONE:
                        return NIVELINTERMEDIARIO;
           
                        
                        
                        
                        
                        
                        
           //Experiente
             case IRON_ORE:
                     return NIVELEXPERIENTE;
             case LAPIS_ORE:
                     return NIVELEXPERIENTE;
            case NETHER_QUARTZ_ORE:
                     return NIVELEXPERIENTE;
             case GLOWSTONE:
                     return NIVELEXPERIENTE;
         
                     
              //Avançado
             case GOLD_ORE:
                     return NIVELAVANCADO;
             case REDSTONE_ORE:
                     return NIVELAVANCADO;
            case PRISMARINE:
                     return NIVELAVANCADO;
             case PRISMARINE_BRICKS:
                     return NIVELAVANCADO;
            case DARK_PRISMARINE:
                     return NIVELAVANCADO;
            case SEA_LANTERN:
                        return NIVELAVANCADO;
                     
             //Mestre
             case DIAMOND_ORE:
                     return NIVELMESTRE;
             case EMERALD_ORE:
                     return NIVELMESTRE;
            case OBSIDIAN:
                     return NIVELMESTRE;
              
            
            default:
                     return SEMNIVEL;
                     
                        
                     
                     
         }
     
         
     }
     
    public static float XpParaAdicionarMinerador(Block bloco)
    {
        
        switch(NvlDoBlocoMinerador(bloco)){
            case NIVELINICIANTE:
                return XPGANHOINICIANTE;
                
                case NIVELINTERMEDIARIO:
                return XPGANHOINTERMEDIARIO;
                
                case NIVELEXPERIENTE:
                return XPGANHOEXPERIENTE;
                
                case NIVELAVANCADO:
                return XPGANHOAVANCADO;
                
                case NIVELMESTRE:
                return XPGANHOMESTRE;
                    default:
                        return 0f;
        
        }
    }
     
     
    public static ChatColor ObterCorPorNivel(int lvl)
    {
        
         if(lvl >= GerenciadorDeSkills.NIVELMESTRE){
                                return ChatColor.BLUE;
                            }else if(lvl >= GerenciadorDeSkills.NIVELAVANCADO){
                                return ChatColor.DARK_GREEN;
                            }else if(lvl >= GerenciadorDeSkills.NIVELEXPERIENTE){
                                return ChatColor.YELLOW;
                            }else if(lvl >= GerenciadorDeSkills.NIVELINTERMEDIARIO){
                                return ChatColor.GOLD;
                            }else{
                                //Iniciante
                                return ChatColor.RED;
                            }
    
    
    
    }
     
     
    
    public static Enchantment[] ObterEncantamentosminNvl(ItemStack is)
    {
        List<Enchantment> en = new ArrayList<>();
        for(Enchantment enc:Enchantment.values())
        {
            if(enc.canEnchantItem(is)){
                en.add(enc);
            }
        }
    
        
        
        
        
        
        
        if(en.size()> 0)
            return (Enchantment[])en.toArray();
        
        return null;
    }
    
    
    public static void MonstrarXpAdicionadoELvlUp(float xp, Skills sk,Player jogador,boolean lvlup){
        
        jogador.sendMessage(ChatColor.GREEN + "Adicionado " + ChatColor.WHITE + xp + ChatColor.GREEN + " XP a habilidade de " + ChatColor.YELLOW + sk.getNomeSkill());
        jogador.sendMessage(ChatColor.GREEN + "Total: " + ChatColor.WHITE + sk.getExp()[0] + ChatColor.GREEN + "/" + ChatColor.WHITE + sk.getExp()[1]);
        if(lvlup){

            jogador.sendMessage(ChatColor.GREEN+"Subiu de nível!");
            jogador.sendMessage(ChatColor.GREEN+"Novo nível: "+ChatColor.WHITE+sk.getNvl()[0]);

        }else
        {
           jogador.sendMessage(ChatColor.GREEN+"Nível: "+ChatColor.WHITE+sk.getNvl()[0]);
           
        }
        
        
    
    }
    
    
    public static void MonstrarXpAdicionadoELvlUp(float xp, int sk,Player jogador,boolean lvlup){
        
        
        
        MonstrarXpAdicionadoELvlUp(xp,MetaDataParaLista(jogador).get(sk),jogador,lvlup);
    
    }
    
    
    
    public static void GerarFerramentasFerreiro()
    {
        
        
        
        ShapedRecipe receita;
        ItemStack item;
        ItemMeta im;
        List<String> lore;
        
        item = new ItemStack(Material.WOODEN_HOE);
        im = item.getItemMeta();
        lore = new ArrayList<>();
        
        
        lore.add(ChatColor.BLUE+"Usada para trabalhar com ferramentas, armas e armaduras");
        im.setDisplayName(ObterCorPorNivel(1)+"Martelo de madeira");
        im.setLore(lore);
        
        item.setItemMeta(im);
        
        
        receita = new ShapedRecipe(new NamespacedKey(MachadoDeAssis.plugin, "martelodemadeira"), item);
        receita.shape("w","s","s");
        receita.setIngredient('w', Material.WOODEN_HOE);
        receita.setIngredient('s', Material.STICK);
     
        
        
        Bukkit.addRecipe(receita);
    }
    
    
    
    
     public static int NvlPorEntidadeCombate(Entity ent)
     {
         //Monstros
         if(ent instanceof Creature){
             Creature crea = (Creature)ent;
             switch(crea.getType()){
                 
                 //jogadores
                 case PLAYER:
                     
                     return NvlCombateJogador((Player)crea);
                 
                 //Sem nvl
                 case BAT:
                     return SEMNIVEL;
                 case COD:
                     return SEMNIVEL;
                 case SALMON:
                     return SEMNIVEL;  
                     case VILLAGER:
                     return SEMNIVEL;  
                     
                     
                     
                 //Iniciante    
                 case CAT:
                     return NIVELINICIANTE;
                case CHICKEN:
                     return NIVELINICIANTE;
                
                case COW:
                     return NIVELINICIANTE;
                case DONKEY:
                     return NIVELINICIANTE;
                case FOX:
                     return NIVELINICIANTE;
                case HORSE:
                     return NIVELINICIANTE;
                case MUSHROOM_COW:
                     return NIVELINICIANTE;
                case MULE:
                     return NIVELINICIANTE;
                case OCELOT:
                     return NIVELINICIANTE;
                case PANDA:
                     return NIVELINICIANTE;
                case PARROT:
                     return NIVELINICIANTE;
                case PIG:
                     return NIVELINICIANTE;
                case RABBIT:
                     return NIVELINICIANTE;
                   
                case SHEEP:
                     return NIVELINICIANTE;   
                case SKELETON_HORSE:
                     return NIVELINICIANTE;   
                case SNOWMAN:
                     return NIVELINICIANTE;
                case SQUID:
                     return NIVELINICIANTE;
                case TROPICAL_FISH:
                     return NIVELINICIANTE;
                case TURTLE:
                     return NIVELINICIANTE;    
                 
                case ZOMBIE_HORSE:
                     return NIVELINICIANTE;   
                
                     
                     
                     
                //Intermediario
                case DOLPHIN:
                        return NIVELINTERMEDIARIO;
                case LLAMA:
                        return NIVELINTERMEDIARIO;
                case PUFFERFISH:
                        return NIVELINTERMEDIARIO;
                        
                case TRADER_LLAMA:
                        return NIVELINTERMEDIARIO;
                case WOLF:
                        return NIVELINTERMEDIARIO;
                case WANDERING_TRADER:
                        return NIVELINTERMEDIARIO;
                        
                        
                        
                //EXPERIENTE
                case CREEPER:
                        return NIVELEXPERIENTE;
                case DROWNED:
                        return NIVELEXPERIENTE;
                case ENDERMITE:
                        return NIVELEXPERIENTE;
                case PHANTOM:
                        return NIVELEXPERIENTE;
                case PILLAGER:
                        return NIVELEXPERIENTE;
                case POLAR_BEAR:
                        return NIVELEXPERIENTE;
                case SILVERFISH:
                        return NIVELEXPERIENTE;
                case SHULKER:
                        return NIVELEXPERIENTE;
                case SKELETON:
                        return NIVELEXPERIENTE;    
                case SPIDER:
                        return NIVELEXPERIENTE;
                case VEX:
                        return NIVELEXPERIENTE;
                case VINDICATOR:
                        return NIVELEXPERIENTE;     
                case WITCH:
                        return NIVELEXPERIENTE;          
                case ZOMBIE:
                        return NIVELEXPERIENTE;    
                case ZOMBIE_VILLAGER:
                        return NIVELEXPERIENTE;    
                      
                //Avançado
                case BLAZE:
                        return NIVELAVANCADO; 
                case CAVE_SPIDER:
                        return NIVELAVANCADO;        
                case ENDERMAN:
                        return NIVELAVANCADO; 
                case EVOKER:
                        return NIVELAVANCADO;  
                case GHAST:
                        return NIVELAVANCADO;   
                case GUARDIAN:
                        return NIVELAVANCADO;
                case HUSK:
                        return NIVELAVANCADO;
                case ILLUSIONER:
                        return NIVELAVANCADO;
                case IRON_GOLEM:
                        return NIVELAVANCADO;
                case PIG_ZOMBIE:
                        return NIVELAVANCADO;  
                case RAVAGER:
                        return NIVELAVANCADO;   
                case STRAY:
                        return NIVELAVANCADO;       
                case WITHER_SKELETON:
                        return NIVELAVANCADO;        
                    
                
                        
                //Mestre
                case ELDER_GUARDIAN:
                            return  NIVELMESTRE;
                case ENDER_DRAGON:
                            return  NIVELMESTRE;
                case WITHER_SKULL:
                            return  NIVELMESTRE;
                case GIANT:
                                return NIVELMESTRE;
                        
                        
                //Slime  e magmacube
                        case SLIME:
                                Slime sl = (Slime) ent;
                                switch(sl.getSize()){
                                    case 0:
                                        return NIVELINICIANTE;
                                    case 1:
                                        return  NIVELINTERMEDIARIO;
                                        default:
                                            if(sl.getSize()>4){
                                                    if(sl.getSize()>=20){
                                                 
                                                        return NIVELMESTRE;
                                                    }
                                                     return NIVELAVANCADO;
                                            }
                                   return NIVELEXPERIENTE;
                                            
                                }            
                                            
                default:
                    return SEMNIVEL;
                                
               
                        
                        
                        
                case MAGMA_CUBE:
                                MagmaCube mc = (MagmaCube) ent;
                                switch(mc.getSize()){
                                    case 0:
                                        return NIVELINTERMEDIARIO;
                                    case 1:
                                        return  NIVELEXPERIENTE;
                                        default:
                                            if(mc.getSize()>4){
                                                    
                                                     return NIVELMESTRE;
                                            }
                                   return NIVELEXPERIENTE;
                                            
                                            
                                            
                                            
                                
                                }
                
                
                        
                        
                        
                        
                        
             }
         
         }
         return  SEMNIVEL;
         
         
         
         
         
         
     }
     
     
     
     public static int NvlCombateJogador(Player jogador){
         List<Skills> sk = GerenciadorDeSkills.MetaDataParaLista(jogador);
         int maionvl = 0;
     
         for(int i = 3; i < 8;i++){
            if(sk.get(i).getNvl()[0] > maionvl){
                maionvl = sk.get(i).getNvl()[0];
            }
            
            if(maionvl<NIVELINTERMEDIARIO){
                return NIVELINICIANTE;
            }else if(maionvl < NIVELEXPERIENTE){
                return NIVELINTERMEDIARIO;
            }else if(maionvl < NIVELAVANCADO){
                return NIVELEXPERIENTE;
            }else if(maionvl < NIVELMESTRE){
                return NIVELAVANCADO;
            }else{
                return NIVELMESTRE;
            }
            
         }
     
         return SEMNIVEL;
     }
     
     
     
     
     
     
     public static float AdicionarXpCombate(Player jogador,Entity alvo,int skill,boolean mostrarxp){
        
         float xp;
         boolean lvlup = false;
         xp = XpParaAdicionar(NvlPorEntidadeCombate(alvo));
         lvlup = GerenciadorDeSkills.AddXp(skill,xp, jogador);
         
         if(mostrarxp){
             MonstrarXpAdicionadoELvlUp(xp,skill,jogador,lvlup);
         }
         
         
         return xp;
     
     }
    
    
    
     
     
     public static int ObterCodSkillCombatePorItem(ItemStack is)
     {
         
         
         
        for(int i = 0; i< ItensEspadachim.length; i++){
            if(is.getType()==ItensEspadachim[i]){
                return 3;
            }
        } 
        for(int i = 0; i< ItensBarbaro.length; i++){
            if(is.getType()==ItensBarbaro[i]){
                return 4;
            }
        }
        for(int i = 0; i< ItensArqueiro.length; i++){
            if(is.getType()==ItensArqueiro[i]){
                return 5;
            }
        }
        for(int i = 0; i< ItensDesarmado.length; i++){
            if(is.getType()==ItensDesarmado[i]){
                return 7;
            }
        }
        
        
        if(is.getItemMeta().hasLore()){
            for(int i = 0; i< NomesLoreDoMago.length; i++){
                
                for(int i2 = 0; i2< is.getItemMeta().getLore().size(); i2++){
                    
                    if(is.getItemMeta().getLore().get(i)==NomesLoreDoMago[i]){
                        
                        return 6;
                        
                        
                    }
                
                }
                
            }
        
        
        }
        
        
         
        return -1;
     }
    
    
    
    
    
    
    
}
