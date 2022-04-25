package fr.ylyade.courtage.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;


@Entity
@Table(name = "ta_sous_donnee")
public class TaSousDonnee implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4935738456796029302L;
	
	//CONSTANTE CLE/VALEURS POUR LE JSONDATA (fourre-tout)
	
	//FRAIS D'emission (14/04/2020)
	public static final String CLE_FRAIS_EMISSION_ASSUREUR ="frais_emission_assureur";
	public static final String VALEUR_FRAIS_EMISSION_ASSUREUR ="70";
	//JURILAW (14/04/2020)
	public static final String CLE_JURILAW ="jurilaw";
	public static final String VALEUR_JURILAW ="68";
	
	
	//FRAIS DE GESTION
	public static final String CLE_FRAIS_GESTION_YLYADE ="frais_gestion_ylyade";
	public static final String VALEUR_FRAIS_GESTION_YLYADE ="0";
	//public static final String VALEUR_FRAIS_GESTION_YLYADE ="70";
	//public static final String VALEUR_FRAIS_GESTION_YLYADE_PIB ="100";
	public static final String VALEUR_FRAIS_GESTION_YLYADE_PIB ="0";
	public static final String CLE_FRAIS_GESTION_ASSUREUR ="frais_gestion_assureur";
	public static final String VALEUR_FRAIS_GESTION_ASSUREUR ="165";
	public static final String VALEUR_FRAIS_GESTION_ASSUREUR_PIB ="200";
	//FRAIS D'AVENANT
	public static final String CLE_FRAIS_AVENANT_YLYADE ="frais_avenant_ylyade";
	public static final String VALEUR_FRAIS_AVENANT_YLYADE ="70";
	public static final String CLE_FRAIS_AVENANT_ASSUREUR ="frais_avenant_assureur";
	public static final String VALEUR_FRAIS_AVENANT_ASSUREUR ="80";
	
	//COMMISSION YLYADE
	public static final String CLE_TAUX_COMMISSION_YLYADE ="taux_commission_ylyade";
	public static final String VALEUR_TAUX_COMMISSION_YLYADE ="12";
	public static final String CLE_MONTANT_COMMISSION_YLYADE ="montant_commission_ylyade";
	public static final String CLE_MONTANT_SUR_ECHEANCE_COMMISSION_YLYADE ="montant_sur_echeance_commission_ylyade";
	
	//INTERRUPTION D'ASSURANCE
	public static final String CLE_INTERRUP_ASSUR_0_A_2_ANS ="interruption_assurance_0_a_2_ans";
	public static final String CLE_INTERRUP_ASSUR_2_A_5_ANS ="interruption_assurance_2_a_5_ans";
	public static final String CLE_INTERRUP_ASSUR_5_A_7_ANS ="interruption_assurance_5_a_7_ans";
	public static final String CLE_INTERRUP_ASSUR_PLUS_7_ANS ="interruption_assurance_plus_7_ans";
	
	//DEJA ASSURE MEME RISQUE
	public static final String CLE_DEJA_ASSUR_MEME_RISQUE ="deja_assure_meme_risque";
	
	//ANTECEDANT RESILIE NON PAIEMENT
	public static final String CLE_MONTANT_RESILIE_NON_PAIEMENT_ASSUREUR ="montant_rnp_assureur";
	public static final String CLE_MONTANT_RESILIE_NON_PAIEMENT_YLYADE ="montant_rnp_ylyade";
	
	
	//REPRISE PASSE
	public static final String CLE_MONTANT_REPRISE_PASSE_ASSUREUR ="montant_reprise_passe_assureur";
	public static final String CLE_MONTANT_REPRISE_PASSE_YLYADE ="montant_reprise_passe_ylyade";
	
	//PROTECTION JURIDIQUE
	public static final String CLE_PJ_CHOISIE = "pj_choisie";
	public static final String VALEUR_PJ_CHOISIE_PJPRO = "Pj";
	public static final String VALEUR_PJ_CHOISIE_PJPROETENDUE = "Pj_ETENDUE";
	public static final String VALEUR_PJ_CHOISIE_PJPROETENDUE_PIB = "Pj_ETENDUE_PIB";
	
	//TAXES 9%
	public static final String CLE_MONTANT_TAXES_9 = "montant_taxe_9";
	
	
	//NUM POLICE
//	public static final String CLE_NUM_POLICE_COMPLET = "num_police_complet";
//	public static final String CLE_LETTRE_PJ_NUM_POLICE = "lettre_pj_num_police";
	public static final String VALEUR_LETTRE_NUM_POLICE_PJPROETENDUE = "T";
	public static final String VALEUR_LETTRE_NUM_POLICE_PJPRO = "J";
	
	public static final String VALEUR_FALSE = "false";
	public static final String VALEUR_TRUE = "true";
	
	
	private int idSousDonnee;
	private String jsonData;
	private TaDossierRcd taDossierRcd;

	
	public TaSousDonnee() {
		JSONObject jsonData = new JSONObject();
		jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_0_A_2_ANS, "false");
		jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_2_A_5_ANS,	"false");
		jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_5_A_7_ANS, "false");
		jsonData.put(TaSousDonnee.CLE_INTERRUP_ASSUR_PLUS_7_ANS, "false");
		jsonData.put(TaSousDonnee.CLE_PJ_CHOISIE, "");
		this.setJsonData(jsonData.toString());
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sous_donnee", unique = true, nullable = false)
	public int getIdSousDonnee() {
		return idSousDonnee;
	}

	public void setIdSousDonnee(int idSousDonnee) {
		this.idSousDonnee = idSousDonnee;
	}

	@Column(name = "json_data")
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}



	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dossier")
	public TaDossierRcd getTaDossierRcd() {
		return taDossierRcd;
	}

	public void setTaDossierRcd(TaDossierRcd taDossierRcd) {
		this.taDossierRcd = taDossierRcd;
	}

	
}
