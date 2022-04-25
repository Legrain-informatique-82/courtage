package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Const {
	public static final String C_AUCUN = "C_AUCUN";
	public static final String C_MESSAGE_CHANGEMENT_CODE = "";

	public static final String C_ID_T_ADR = "idTAdresse";
	public static final String C_CODE_T_ADR = "codeTAdresse";
	public static final String C_LIBL_T_ADR = "liblTAdresse";


	public static final String C_ID_T_ASSURANCE = "idTAssurance";
	public static final String C_CODE_T_ASSURANCE = "codeTAssurance";
	public static final String C_LIBL_T_ASSURANCE = "liblTAssurance";

	public static final String C_ID_T_ASSURE = "idTAssure";
	public static final String C_CODE_T_ASSURE = "codeTAssure";
	public static final String C_LIBL_T_ASSURE = "liblTAssure";

	public static final String C_ID_T_CIVILITE = "idTCivilite";
	public static final String C_CODE_T_CIVILITE = "codeTCivilite";
	public static final String C_LIBL_T_CIVILITE = "liblTCivilite";

	public static final String C_ID_T_COURTIER = "idTCourtier";
	public static final String C_CODE_T_COURTIER = "codeTCourtier";
	public static final String C_LIBL_T_COURTIER = "liblTCourtier";	

	public static final String C_ID_T_TEL = "idTTel";
	public static final String C_CODE_T_TEL = "codeTTel";
	public static final String C_LIBL_T_TEL = "liblTTel";

	public static final String C_ID_T_FRANCHISE = "idTFranchise";
	public static final String C_CODE_T_FRANCHISE = "codeTFranchise";
	public static final String C_LIBL_T_FRANCHISE = "liblTFranchise";
	public static final String C_MONTANT_T_FRANCHISE = "montantTFranchise";
	public static final String C_TAUX_MONTANT_T_FRANCHISE = "tauxMontantTFranchise";

	public static final String C_ID_T_STATUT = "idStatut";
	public static final String C_CODE_T_STATUT = "codeTStatut";
	public static final String C_LIBL_T_STATUT = "liblTStatut";
	public static final String C_DUREE_STATUT = "dureeStatut";
	public static final String C_DUREE_NB_JOURS_STATUT = "dureeNbJoursStatut";

	public static final String C_ID_T_SOUS_TRAITANCE = "idTSousTraitance";
	public static final String C_LIBL_T_SOUS_TRAITANCE = "liblTSousTraitance"; 
	public static final String C_CODE_T_SOUS_TRAITANCE = "codeTSousTraitance";
	public static final String C_T_SOUS_TRAITANCE_TAUX = "taux";
	public static final String C_T_SOUS_TRAITANCE_BASE_MIN = "baseMin";
	public static final String C_T_SOUS_TRAITANCE_BASE_MAX = "baseMax";

	public static final String C_ID_T_GROUPE_TARIF = "idTGroupeTarif";
	public static final String C_CODE_GROUPE = "codeGroupe";
	public static final String C_NOM_GROUPE = "nomGroupe";
	public static final String C_TAUX_GROUPE = "tauxGroupe";

	public static final String C_ID_T_TRAVAUX = "idTTravaux";
	public static final String C_CODE_T_TRAVAUX = "codeTTravaux";
	public static final String C_LIBL_T_TRAVAUX = "liblTTravaux";

	public static final String C_ID_T_ROLE = "idTRole";
	public static final String C_CODE_T_ROLE = "codeTRole"; 
	public static final String C_LIBL_T_ROLE = "liblTRole";

	public static final String C_ID_T_LISTE_REF_DOC = "idTListeRefDoc";
	public static final String C_CODE_T_LISTE_REF_DOC = "codeTListeRefDoc"; 
	public static final String C_LIBL_T_LISTE_REF_DOC = "liblTListeRefDoc";

	public static final String C_ID_T_JURIDIQUE = "idTJuridique";
	public static final String C_CODE_T_JURIDIQUE = "codeTJuridique";
	public static final String C_LIBL_T_JURIDIQUE = "libl";

	public static final String C_ID_T_EMAIL = "idTEmail";
	public static final String C_CODE_T_EMAIL = "codeTEmail";
	public static final String C_LIBL_T_EMAIL = "liblTEmail";

	public static final String C_ID_T_ECHEANCE = "idTEcheance";
	public static final String C_TAUX_ECHEANCE = "tauxEcheance";
	public static final String C_LIBL_T_ECHEANCE = "liblTEcheance";
	public static final String C_CODE_T_ECHEANCE = "codeTEcheance";
	
	public static final String C_ID_PART_SOUS_TRAITANCE = "idPartSousTraitance";
	public static final String C_CODE_PART_SOUS_TRAITANCE = "codePartSousTraitance";
	public static final String C_LIBL_PART_SOUS_TRAITANCE = "liblPartSousTraitance";
	public static final String C_PART_MIN_PART_SOUS_TRAITANCE = "partMin";
	public static final String C_PART_MAX_PART_SOUS_TRAITANCE = "partMax";
	public static final String C_TAUX_PART_SOUS_TRAITANCE = "tauxPartSousTraitance";

	public static final String C_ID_T_ACTION_DOC = "idTActionDoc";
	public static final String C_CODE_T_ACTION_DOC = "codeTActionDoc";
	public static final String C_LIBL_T_ACTION_DOC = "liblTActionDoc";

	public static final String C_ID_T_REGLEMENT = "idTReglement";
	public static final String C_CODE_T_REGLEMENT = "codeTReglement";
	public static final String C_LIBL_T_REGLEMENT = "liblTReglement";

	public static final String C_ID_T_CONSTRUCTION = "idTConstruction";
	public static final String C_CODE_T_CONSTRUCTION = "codeConstruction";
	public static final String C_LIBL_T_CONSTRUCTION = "liblConstruction";

	public static final String C_ID_T_DESTINATION_USAGE = "idTDestinationUsage";
	public static final String C_CODE_T_DESTINATION_USAGE = "codeTDestinationUsage";
	public static final String C_LIBL_T_DESTINATION_USAGE = "liblTDestinationUsage";

	public static final String C_ID_T_DOC = "idTDoc";
	public static final String C_LIBL_T_DOC = "liblTDoc";
	public static final String C_CODE_T_DOC = "codeTDoc"; 
	public static final String C_DESCRIPTION_T_DOC = "descriptionTDoc";

	public static final String C_ID_FRAIS_IMPAYE = "id";
	public static final String C_CODE_T_FRAIS_IMPAYE = "codeTFraisImpaye";
	public static final String C_LIBL_FACTURE_FRAIS_IMPAYE = "liblFactureFraisImpaye";
	public static final String C_MONTANT = "montant";

	public static final String C_ID_T_GED_SINISTRE = "idTGedSinistre";
	public static final String C_CODE_T_GED_SINISTRE = "codeTGedSinistre";
	public static final String C_LIBL_T_GED_SINISTRE = "liblTGedSinistre";
	public static final String C_DESCRIPTION_T_GED_SINISTRE = "decriptionTGedSinistre";

	public static final String C_ID_T_MAITRISE_OEUVRE = "idTMaitriseOeuvre";
	public static final String C_LIBL_TITRE = "liblTitre";
	public static final String C_LIBL_NATURE = "liblNature";
	public static final String C_CODE_T_MAITRISE_OEUVRE = "codeTMaitriseOeuvre";

	public static final String C_ID_T_LOT_REALISE = "idLotRealise";
	public static final String C_CODE_T_LOT_REALISE = "codeLotRealise";
	public static final String C_LIBL_T_LOT_REALISE = "liblLotRealise";

	public static final String C_ID_TARIF_PRESTATION = "idTarifPrestation";
	public static final String C_CODE_TARIF_PRESTATION = "codeTarifPrestation";
	public static final String C_LIBL_PRESTATION = "liblPrestation";
	public static final String C_MONTANT_PRESTATION = "montantPrestation";

	public static final String C_ID_FAMILLE_ACTIVITE = "idFamilleActivite";	
	public static final String C_CODE_FAMILLE_ACTIVITE = "codeFamilleActivite";
	public static final String C_LIBL_FAMILLE_ACTIVITE = "liblFamilleActivite";

	public static final String C_ID_CLASSE = "idClasse";
	public static final String C_LIBL_CLASSE = "liblClasse";
	public static final String C_CODE_CLASSE = "codeClasse";

	//TaUtilisateur
	public static final String C_ID_UTILISATEUR = "idUtilisateur";
	public static final String C_IDENTIFIANT = "identifiant";
	public static final String C_PASSWORD = "password";

	//TaCourtier
	public static final String C_ID_COURTIER = "idCourtier";
	public static final String C_CODE_COURTIER = "codeCourtier";
	public static final String C_TITRE_FONCTION = "titreFonction";
	public static final String C_CODE_SIRET = "codeSiret";
	public static final String C_CODE_SIREN = "codeSiren";
	public static final String C_CODE_APE = "codeApe";
	public static final String C_TVA_INTRA_COMM = "tvaIntraComm";
	public static final String C_LOGO = "logo";
	public static final String C_QUALITE = "qualite";
	public static final String C_NUM_ORIAS = "numOrias";
	public static final String C_NOM_DENOMINATION_SOCIALE = "nomDenominationSociale";
	public static final String C_NUM_RCS = "numRcs";
	public static final String C_NUM_RC_PRO = "numRcPro";
	public static final String C_NUM_GREFFE = "numGreffe";
	public static final String C_CAPITAL_SOCIAL = "capitalSocial";
	public static final String C_ACTIF = "actif";
	public static final String C_SUSPRENDU = "suspendu";
	public static final String C_EFFECTIF_TOTAL = "effectifTotal";
	public static final String C_COM_BRUTES = "comBrutes";
	public static final String C_COM_BRUTES_IARD = "comBrutesIard";
	public static final String C_CONNU_YLYADE = "connuYlyade";
	public static final String C_CA_IARD_POURCENT_ENTREPRISE = "caIardPourcentEntreprise";
	public static final String C_CA_IARD_POURCENT_PARTICULER = "caIardPourcentParticulier";

	//TaContactEntreprise
	public static final String C_NOM = "nom";
	public static final String C_PRENOM = "prenom";
	public static final String C_FONCTION = "fonction";

	//TaAdresse
	public static final String C_ID_ADRESSE = "idAdresse";
	public static final String C_ADRESSE_LIGNE_1 = "adresseLigne1";
	public static final String C_ADRESSE_LIGNE_2 = "adresseLigne2";
	public static final String C_ADRESSE_LIGNE_3 = "adresseLigne3";
	public static final String C_CODE_POSTAL = "codePostal";
	public static final String C_VILLE = "ville";
	public static final String C_PAYS = "pays";

	//TaTel
	public static final String C_ID_TEL = "idTel";
	public static final String C_POSTE_TEL = "posteTel";
	public static final String C_NUMERO_TEL = "numeroTel";

	//TaEmail
	public static final String C_ID_EMAIL = "idEmail";
	public static final String C_ADRESSE_EMAIL = "adresseEmail";
	
	//TaActivite
	public static final String C_ID_ACTIVITE = "idActivite";
	public static final String C_CODE_ACTIVITE = "codeActivite";
	public static final String C_LIBL_ACTIVITE = "liblActivite";
	public static final String C_DESCRIPTION_ACTIVITE = "descriptionActivite";
	public static final String C_MINCA_ACTIVITE = "minCa";
	public static final String C_MAXCA_ACTIVITE = "maxCa";
	public static final String C_PRIMEBASE_ACTIVITE = "primeBase";
	

	//TaListeRefDoc
	public static final String C_ID_LISTE_REF_DOC="idListeRefDoc";
	public static final String C_CODE_LISTE_REF_DOC="codeListeRefDoc";
	public static final String C_LIBL_DOC="liblDoc";
	public static final String C_DESCRIPTION="description";

	//TaPalierClasse
	public static final String C_ID_PALIER_CLASSE="idPalierClasse";
	public static final String C_CODE_PALIER_CLASSE="codePalierClasse";
	public static final String C_MIN_PALIER_CLASSE="palierMontantMin";
	public static final String C_MAX_PALIER_CLASSE="palierMontantMax";
	public static final String C_MONTANT_PALIER_CLASSE="montantPrimeBase";

	//TaTauxAssurance
	public static final String C_ID_TAUX_ASSURANCE = "idTauxAssurance";
	public static final String C_CODE_TAUX_ASSURANCE = "codeTauxAssurance";
	public static final String C_LIBL_TAUX_ASSURANCE = "libelleTauxAssurance";
	public static final String C_LIBL_TAUX__TAUX_ASSURANCE = "tauxTauxAssurance";

	//TaAssure
	public static final String C_ID_ASSURE = "idAssure";
	public static final String C_CODE_ASSURE = "codeAssure";
	public static final String C_CODE_SIRET_ASSURE = "codeSiret";
	public static final String C_CODE_SIREN_ASSURE = "codeSiren";
	public static final String C_CODE_APE_ASSURE = "codeApe";
	public static final String C_TVA_INTRA_COMM_ASSURE = "tvaIntraComm";
	public static final String C_QULIFICATION = "qualifications";
	public static final String C_DIPLOME = "diplome";
	public static final String C_RAISON_SOCIALE = "raisonSociale";
	public static final String C_DATE_CREATION = "dateCreation";
	public static final String C_DATE_CLOTURE_BILAN = "dateClotureBilan";
	public static final String C_ACTIF_ASSURE = "actif";
	public static final String C_ACTIVITE_PRINCIPALE = "activitePrincipale";
	public static final String C_DATE_NAISSANCE = "dateNaissance";
	public static final String C_CAPITAL = "capital";
	public static final String C_IMAT_REGISTRE_COMM_SOCIETE_VILLE = "imatRegistreCommSocieteVille";
	public static final String C_NUM_IMAT = "numImat";
	public static final String C_CHIFFRE_AFFAIRE = "chiffreAffaire";
	public static final String C_CHIFFRE_AFFAIRE_EXERCICE_ANTERIEUR = "chiffreAffaireExerciceAnterieur";
	public static final String C_EFFECTIF_TOTAL_EXERCICE = "effectifTotalExercice";
	public static final String C_EFFECTIF_TOTAL_EXERCICE_ANTERIEUR = "effectifTotalExerciceAnterieur";

	//TaDossierRcd
	public static final String C_ID_DEVIS_RCPRO = "idDevisRcPro";
	public static final String C_NUM_DEVIS_RCPRO = "numDevis";
	public static final String C_VERSION_DEVIS_RCPRO = "version";

	//TaContratRcPro
	public static final String C_ID_CONTRAT_RCPRO = "idContratRcPro";
	public static final String C_VERSION_CONTRAT_RCPRO = "version";
	public static final String C_RESILISATION = "resiliation";
	public static final String C_DATE_RESILIATION = "dateResiliation";
	public static final String C_MOTIF_RESILIATION = "motifResiliation";
	public static final String C_CONTRAT_ACTIF = "contratActif";
	public static final String C_NUM_POLICE = "numPolice";
	public static final String C_DATE_SOUSCRIPTION = "dateSouscription";
	public static final String C_DATE_RETRACTATION = "dateRetractation";
	public static final String C_DATE_ACTIVATION_DONNE_STATUT = "dateActivationDonneStatut";
	public static final String C_RETRACTATION = "retractation";
	public static final String C_IMG_CONTRAT_RC_PRO = "imgContratRcPro";
	public static final String C_MONTANT_AVENANT = "montantAvenant";
	public static final String C_NOM_FICHIER = "nomFichier";
	public static final String C_TAILLE = "taille";
	public static final String C_TYPE_MIME = "typeMime";

	//TaDevisRcProDetail
	public static final String C_ID_DEVIS_RC_PRO_DETAIL = "idDevisRcProDetail";
	public static final String C_RESILIE_NON_PAIEMENT = "resilieNonPaiement";
	public static final String C_RESILIE_FAUSSE_DECLARATION = "resilieFausseDeclaration";
	public static final String C_DATE_REALISATION = "dateRealisation";
//	public static final String C_DATE_RESILIATION = "dateResiliation";
	public static final String C_VERSION = "version";
	public static final String C_IMG_DEVIS_RC_PRO = "imgDevisRcPro";
	public static final String C_MONTANT_PRIME = "montantPrime"; 
	public static final String C_CODE_SOUS_TRAITANCE = "codeSousTraitance";
	public static final String C_POURCENT_SOUS_TRAITANCE = "pourcentSoustraitance";
	public static final String C_FRAIS_RC_PRO = "fraisRcPro";
	public static final String C_TAUX_COMMISSION = "tauxCommission";
	public static final String C_DEJA_ASSURER = "dejaAssurer";
	public static final String C_CONTRAT_EN_COURS = "contratEnCours";
	public static final String C_NB_SISNISTRE_TOTAL_5_ANS = "nbSinistre5ans";	   
	public static final String C_NB_SINISTRE_TOTAL = "nbSinistreTotal";
	public static final String C_MONTANT_SINISTRE = "montantSinistre";
//	public static final String C_NOM_FICHIER = "nomFichier";
//	public static final String C_TAILLE = "taille";
//	public static final String C_TYPE_MIME = "typeMime";
	public static final String C_CODE_FRANCHISE = "codeFranchise";
	public static final String C_FRANCHISE = "franchise";
	public static final String C_CODE_ECHEANCE = "codeEcheance";
	public static final String C_ASSUREUR_PRECEDENT_RCP = "assureurPrecedentRcp";
	public static final String C_ASSUREUR_PRECEDENT_RCD = "assureurPrecedentRcd";
	public static final String C_DATE_EFFET_CONTRAT_PRECEDENT_RCP = "dateEffetContratPrecendentRcp";
	public static final String C_DATE_EFFETCONTRAT_PRECENDENT_RCD = "dateEffetContratPrecendentRcd";
	public static final String C_POLICE_CONTRAT_PRECEDENT_RCP = "policeContratPrecedentRcp";
	public static final String C_POLICE_CONTRAT_PRECEDENT_RCD = "policeContratPrecedentRcd";
	public static final String C_MONTANT_CHANTIER_PLUS_ELEVE = "montantChantierPlusEleve";
	public static final String C_INTERVENTION_CHANTIER_COUT_MAX = "interventionChantierCoutMax";
	public static final String C_PAR_POURCENT_CA_SOUS_TRAITANCE = "parPourcentCaSousTraitance";	
	public static final String C_PAR_POURCENT_CA_RENOVATION = "parPourcentCaRenovation";
	public static final String C_PAR_POURCENT_CLIENT_PARTICULIER = "parPourcentClientParticulier";
	public static final String C_PAR_POURCENT_PRIS_SOUS_TRAITANCE = "parPourcentCaPrisSousTraitance";
	public static final String C_PAR_POURCENT_CA_NEUF = "parPourcentCaNeuf";
	public static final String C_PAR_POURCENT_CLIENT_ENTREPRISE = "parPourcentClientEntreprise";
	public static final String C_COUT_GLOBAL_SINISTRE_RCD = "coutGlobalSinistreRcd";
	public static final String C_COUT_GLOBAL_SINISTRE_RCP = "coutGlobalSinistreRcp";

	//TaContratRcProDetail
	public static final String C_ID_CONTRAT_RC_PRO_DETAIL = "idContratRcProDetail";
//	public static final String C_RESILIE_NON_PAIEMENT = "resilieNonPaiement";
//	public static final String C_RESILIE_FAUSSE_DECLARATION = "resilieFausseDeclaration";
//	public static final String C_DATE_REALISATION = "dateRealisation";
//	public static final String C_DATE_RESILIATION = "dateResiliation";
//	public static final String C_VERSION = "version";
//	public static final String C_IMG_DEVIS_RC_PRO = "imgDevisRcPro";
//	public static final String C_MONTANT_PRIME = "montantPrime"; 
//	public static final String C_CODE_SOUS_TRAITANCE = "codeSousTraitance";
//	public static final String C_POURCENT_SOUS_TRAITANCE = "pourcentSoustraitance";
//	public static final String C_FRAIS_RC_PRO = "fraisRcPro";
//	public static final String C_TAUX_COMMISSION = "tauxCommission";
//	public static final String C_DEJA_ASSURER = "dejaAssurer";
//	public static final String C_CONTRAT_EN_COURS = "contratEnCours";
//	public static final String C_NB_SISNISTRE_TOTAL_5_ANS = "nbSinistre5ans";	   
//	public static final String C_NB_SINISTRE_TOTAL = "nbSinistreTotal";
//	public static final String C_MONTANT_SINISTRE = "montantSinistre";
//	public static final String C_NOM_FICHIER = "nomFichier";
//	public static final String C_TAILLE = "taille";
//	public static final String C_TYPE_MIME = "typeMime";
//	public static final String C_CODE_FRANCHISE = "codeFranchise";
//	public static final String C_FRANCHISE = "franchise";
//	public static final String C_CODE_ECHEANCE = "codeEcheance";
//	public static final String C_ASSUREUR_PRECEDENT_RCP = "assureurPrecedentRcp";
//	public static final String C_ASSUREUR_PRECEDENT_RCD = "assureurPrecedentRcd";
//	public static final String C_DATE_EFFET_CONTRAT_PRECEDENT_RCP = "dateEffetContratPrecendentRcp";
//	public static final String C_DATE_EFFETCONTRAT_PRECENDENT_RCD = "dateEffetContratPrecendentRcd";
//	public static final String C_POLICE_CONTRAT_PRECEDENT_RCP = "policeContratPrecedentRcp";
//	public static final String C_POLICE_CONTRAT_PRECEDENT_RCD = "policeContratPrecedentRcd";
//	public static final String C_MONTANT_CHANTIER_PLUS_ELEVE = "montantChantierPlusEleve";
//	public static final String C_INTERVENTION_CHANTIER_COUT_MAX = "interventionChantierCoutMax";
//	public static final String C_PAR_POURCENT_CA_SOUS_TRAITANCE = "parPourcentCaSousTraitance";	
//	public static final String C_PAR_POURCENT_CA_RENOVATION = "parPourcentCaRenovation";
//	public static final String C_PAR_POURCENT_CLIENT_PARTICULIER = "parPourcentClientParticulier";
//	public static final String C_PAR_POURCENT_PRIS_SOUS_TRAITANCE = "parPourcentCaPrisSousTraitance";
//	public static final String C_PAR_POURCENT_CA_NEUF = "parPourcentCaNeuf";
//	public static final String C_PAR_POURCENT_CLIENT_ENTREPRISE = "parPourcentClientEntreprise";
//	public static final String C_COUT_GLOBAL_SINISTRE_RCD = "coutGlobalSinistreRcd";
//	public static final String C_COUT_GLOBAL_SINISTRE_RCP = "coutGlobalSinistreRcp";
	public static final String C_TAUX_TAUX_ASSURANCE = "tauxTauxAssurance";
	
	
	public static final BigDecimal C_TAUX_MULTIPLY_ENTREPRISE_ETRANGERE = new BigDecimal(3);
	
	
	public static final BigDecimal C_TAUX_MULTIPLY_INTERVENTION_CORSE = new BigDecimal(0);
	public static final BigDecimal C_TAUX_MULTIPLY_INTERVENTION_DOM = new BigDecimal(30);
	public static final BigDecimal C_TAUX_MULTIPLY_DEJA_ASSURE_MEME_RISQUE = new BigDecimal(20);
	
	public static final BigDecimal C_TAUX_RESILIE_NON_PAIEMENT_ASSUREUR = new BigDecimal(30);
	public static final BigDecimal C_TAUX_RESILIE_NON_PAIEMENT_YLYADE = new BigDecimal(5);
	//PIB
	public static final BigDecimal C_TAUX_RESILIE_NON_PAIEMENT_ASSUREUR_PIB = new BigDecimal(30);
	public static final BigDecimal C_TAUX_RESILIE_NON_PAIEMENT_YLYADE_PIB = new BigDecimal(5);
	
	
	public static final BigDecimal C_TAUX_REPRISE_PASSE = new BigDecimal(20);
	public static final BigDecimal C_TAUX_REPRISE_PASSE_YLYADE = new BigDecimal(5);
	public static final BigDecimal C_TAUX_REPRISE_PASSE_ASSUREUR = new BigDecimal(15);
	
	public static final BigDecimal C_MONTANT_PJ_PRO_JURIDICA = new BigDecimal(69.09);//par ans
	public static final BigDecimal C_MONTANT_PJ_PRO_JURIDICA_ETENDUE = new BigDecimal(262.16);//par ans
	public static final BigDecimal C_CA_PREVI_MAX = new BigDecimal(2100000.00);
	public static final BigDecimal C_CA_PREVI_MAX_PIB = new BigDecimal(1000000.00);
	
	public static final BigDecimal C_TAUX_COMMISSION_YLYADE = new BigDecimal(12.00);
	public static final BigDecimal C_TAUX_COMMISSION_YLYADE_PIB = new BigDecimal(20.00);
	
	public static final BigDecimal C_TAUX_COMMISSION_COMPAGNIE = new BigDecimal(2.00);
	public static final BigDecimal C_TAUX_COMMISSION_COMPAGNIE_PIB = new BigDecimal(2.00);
	
	public static final BigDecimal C_TAUX_TAXES_9 = new BigDecimal(9);//taxes 9%
	
	public static final BigDecimal C_MONTANT_FRAIS_SOUSCRIPTION = new BigDecimal(30);
	

}
