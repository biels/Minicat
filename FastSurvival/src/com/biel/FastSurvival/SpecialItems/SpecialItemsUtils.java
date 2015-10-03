package com.biel.FastSurvival.SpecialItems;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Recall.RecallListener;
import com.biel.FastSurvival.SpecialItems.Items.*;
import com.biel.FastSurvival.Utils.Utils;

public class SpecialItemsUtils {
	public static ArrayList<SpecialItem> getRegisteredSpecialItems() {
		ArrayList<SpecialItem> l = new ArrayList<SpecialItem>();
		//REGISTER HERE
		l.add(new DiamondCoreItem()); //1
		l.add(new SteelCoreItem()); //2
		l.add(new KnowledgeFocuserItem()); //3
		l.add(new RegenEnchancerItem()); //4
		l.add(new KnowledgeMagnifierItem()); //5
		l.add(new SquishyDamageCollectorItem()); //6
		l.add(new RedstoneDamageCollectorItem()); //7
		l.add(new UltimateDamageCollectorItem()); //8
		l.add(new LowEndDamageDeflectorItem()); //9
		l.add(new PoweredDamageDeflectorItem()); //10
		l.add(new PlasmaDamageDeflectorItem()); //11
		l.add(new CalciumSourceItem()); //12
		l.add(new AssaultEquipmentItem()); //13
		l.add(new AdvancedAssaultEquipmentItem()); //14
		l.add(new SmallDamageBoosterItem()); //15
		l.add(new DamageBoosterItem()); //16
		l.add(new StolenBersekWeaponItem()); //17
		l.add(new GoldenBersekSwordItem()); //18
		l.add(new LegendaryBersekSwordItem()); //19
		l.add(new MastersBersekSwordItem()); //20
		l.add(new BersekEssenceItem()); //21
		l.add(new LifeStealingEssenceItem()); //22
		l.add(new VampiricScepterItem()); //23
		l.add(new UltimateLifeStealerItem()); //24
		l.add(new UndeadProofLifeStealerItem()); //25
		l.add(new InitiatedSniperGettingStartedItem()); //26
		l.add(new SnipersAdvancedGuideItem()); //27
		//-------------
		return l;
	}
	public static void registerItemListeners(){
		for (SpecialItem s : getRegisteredSpecialItems()){
			Bukkit.getServer().getPluginManager().registerEvents(s, FastSurvival.getPlugin());
		}
	}
	public static ItemStack getRandomSpecialItem(int maxtier){
		int tier = maxtier;
		if (Utils.Possibilitat(3)){tier++;}
		if (Utils.Possibilitat(1)){tier++;}
		if (Utils.Possibilitat(20)){tier--;}
		if (Utils.Possibilitat(20)){tier--;}
		if (tier < 1){tier = 1;}
		if (tier > 3){tier = 3;}
		return getRandomSpecificTierSpecialItem(tier).createNewItemStack();
	}
	private static SpecialItem getRandomSpecificTierSpecialItem(int tier){
		ArrayList<SpecialItem> allTierItems = getAllTierItems(tier);
		return allTierItems.get(Utils.NombreEntre(0, allTierItems.size() - 1));
	}
	public static ArrayList<SpecialItem> getAllTierItems(int tier) {
		ArrayList<SpecialItem> items = new ArrayList<SpecialItem>();
		for (SpecialItem s : getRegisteredSpecialItems()){
			if(s.getTier() == tier)items.add(s);
		}
		return items;
	}
}
