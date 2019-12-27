/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.io.github.thundersoftware.machadodeassis.Model;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author g4bri
 */
public class Skills {

    public int getCodSkill() {
        return CodSkill;
    }

    public void setCodSkill(int CodSkill) {
        this.CodSkill = CodSkill;
    }

    public String getNomeSkill() {
        return NomeSkill;
    }

    public void setNomeSkill(String NomeSkill) {
        this.NomeSkill = NomeSkill;
    }

    public float[] getExp() {
        return Exp;
    }

    public void setExp(float[] Exp) {
        this.Exp = Exp;
    }

    public int[] getNvl() {
        return Nvl;
    }

    public void setNvl(int[] Nvl) {
        this.Nvl = Nvl;
    }

    public Skills(int CodSkill, String NomeSkill) {
        this.CodSkill = CodSkill;
        this.NomeSkill = NomeSkill;
        this.Exp = new float[]{0,10,0.3f};
        this.Nvl = new int[]{1,25};
    }

    public Skills(int CodSkill, String NomeSkill, float[] Exp, int[] Nvl) {
        this.CodSkill = CodSkill;
        this.NomeSkill = NomeSkill;
        this.Exp = Exp;
        this.Nvl = Nvl;
    }
    
    private int CodSkill;
    private String NomeSkill;
    private float[] Exp;
    private int[] Nvl;
    
    
    //retorna verdadeiro caso a skill tenha subido de nível
    public boolean AddXp(float qnt){
        if(getNvl()[0] < getNvl()[1]){
            float[] xp = getExp();
            float restoxp;

            
            xp[0] += qnt;
            if(xp[0]>=xp[1]){
                    int[] nvl = getNvl();
                    nvl[0]++;
                    setNvl(nvl);
                    restoxp = xp[0]-xp[1];
                    xp[0]=0;
                    
                    xp[1] = xp[1]*(1+xp[2]);
                    if(restoxp>0){
                        AddXp(restoxp);
                    }
                    
                    setExp(xp);
                return true;
            }
        }
        
        return false;
    }
    
   
    
        
        
        
    
    
    
}
