package fr.ylyade.courtage.controller;

import java.text.Normalizer;
import org.apache.commons.lang3.StringUtils;

import fr.ylyade.courtage.app.AbstractController;

public abstract class AbstractControllerGed extends AbstractController {
	
	public static String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
//		s = toUrlRewriting(s);
//		return s;

	}
	
	public static String clearString(String chaine, char[] unauthorizedChar, char replacingChar) {
		for (int i = 0; i < unauthorizedChar.length; i++) {
			chaine = chaine.replace(unauthorizedChar[i],replacingChar);
		}
		return chaine;
	}
	public static String toUrlRewriting(String chaine){
		chaine = clearString(chaine, new char[] {'.','*','$',';',',','?',':','/','|','!','@','&','#',
				'(',')','[',']','{','}','"','\'','+','°','€','}','<','>','=','\\'},'-'); //remplace les caractères non autorisés par des "-"
		chaine = StringUtils.lowerCase(chaine); //tout en minuscule
		chaine = StringUtils.stripEnd(chaine,"0123456789"); //supprime les chiffres à la fin
		chaine = StringUtils.replace(chaine," ","_"); //remplace les espaces par des "_"
		chaine = StringUtils.replace(chaine,"é","e");
		chaine = StringUtils.replace(chaine,"è","e");
		chaine = StringUtils.replace(chaine,"ê","e");
		chaine = StringUtils.replace(chaine,"à","a");
		chaine = StringUtils.replace(chaine,"ç","c");
		chaine = StringUtils.replace(chaine,"ù","u");
		chaine = StringUtils.replace(chaine,"û","u");
		chaine = StringUtils.replace(chaine,"î","i");
		chaine = StringUtils.replace(chaine,"â","a");
		chaine = StringUtils.replace(chaine,"ô","o");
		chaine = StringUtils.replace(chaine,"ü","u");
		chaine = StringUtils.replace(chaine,"ë","e");
		chaine = StringUtils.replace(chaine,"ï","i");
		chaine = StringUtils.replace(chaine,"ä","a");
		chaine = StringUtils.replace(chaine,"ö","o");
		chaine = StringUtils.deleteWhitespace(chaine); //supprime les espaces
		chaine = StringUtils.stripEnd(chaine,"-"); //supprime le "-" final s'il existe
		return chaine;
	}
	


}
