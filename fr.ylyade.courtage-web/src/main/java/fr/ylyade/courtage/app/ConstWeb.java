package fr.ylyade.courtage.app;

import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.TaTFranchise;
import fr.ylyade.courtage.model.TaTReglement;
import fr.ylyade.courtage.model.TaTSousTraitance;

public class ConstWeb {

	public static boolean maintenance = false;
	
	public static boolean DEBUG = false;
	
	public static final String codeTReglementDefaut = TaTReglement.CB;
	public static final String codeTSousTraitanceDefaut = TaTSousTraitance.ST_0_30;
	public static final String codeFranchiseDefaut = TaTFranchise.FR3000;
	public static final String codeTEcheanceDefaut = TaTEcheance.ANNUEL;
	
}
