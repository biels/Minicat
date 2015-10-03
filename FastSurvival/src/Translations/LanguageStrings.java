package Translations;

import java.io.File;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Utils.GestorPropietats;

public class LanguageStrings {
	public static String getString(String code){
		GestorPropietats c = FastSurvival.Config();
		String LanguageFileName = c.ObtenirPropietat("LanguageFileName");
		//FastSurvival.getPlugin()
		File lngFile = new File(FastSurvival.getPlugin().getDataFolder().getAbsolutePath() + "/Lang");
		if(!lngFile.exists()){lngFile.mkdir();}
		GestorPropietats g = new GestorPropietats(FastSurvival.getPlugin().getDataFolder().getAbsolutePath() + "/Lang/" + LanguageFileName + ".txt");
		if (g.ExisteixPripietat(code)){
			if (g.ObtenirPropietat(code).equals("") || g.ObtenirPropietat(code).equals("0")){
				return code;
			}
			return g.ObtenirPropietat(code);
		}else{
			return code;
		}
	}
}
