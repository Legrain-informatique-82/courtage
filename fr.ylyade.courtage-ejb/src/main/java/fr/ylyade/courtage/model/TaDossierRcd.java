package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.json.JSONObject;

@Entity
@Table(name = "ta_dossier_rcd")
@NamedQueries(value = { 
	    					
//		@NamedQuery(name=TaDevisRcProDetail.QN.FIND_ACTIVE_BY_ID_ASSURE,
//		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(" + 
//				" f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier," + 
//				" f.commentaireCourtier,f.validationYlyade, f.commentaireYlyade, f.dateControleCourtier," + 
//				" f.dateDepot, f.taDevisRcPro.idDevisRcPro, f.taDevisRcPro.numDevis, " + 
//				" f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " +
//				" from TaGedUtilisateur f " + 
//				" where f.taDevisRcPro.idDevisRcPro = :idDossierRcd and f.taListeRefDoc.idListeRefDoc = :idDoc")
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ACTIVE_BY_ID_ASSURE,
		query = "select new fr.ylyade.courtage.dto.TaDevisRcProDetailDTO(" +
		        " d.idDossierRcd, d.dateEcheance)" +
		        " from TaDossierRcd d left join d.taAssure a" +
		        " where a.idAssure = :idAssure and  d.dateEcheance < :now "),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_NON_ACTIVE_BY_ID_ASSURE,
		query = "select new fr.ylyade.courtage.dto.TaDevisRcProDetailDTO(" +
		        " d.idDossierRcd, d.dateEcheance)" +
		        " from TaDossierRcd d left join d.taAssure a" +
		        " where a.idAssure = :idAssure and d.dateEcheance > :now "),
		
		@NamedQuery(name=TaDossierRcd.QN.COUNT_ACTIVE_BY_ID_COURTIER,
		query = "select count (d.idDossierRcd)" +
		        " from TaDossierRcd d left join d.taAssure a left join a.taCourtier c" +
		        " where c.idCourtier = :idCourtier and d.dateEcheance < :now "),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_LIGHT_ACTIF, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom, d.idDossierRcd, "
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_BY_CODE_LIGHT, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "where d.numDossierPolice like :numDevis order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_LIGHT_PLUS, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant,c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, c.codeCourtier) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_BY_ID_ASSURE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where a.idAssure = :idAssure order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_REFUSE_ASSUREUR, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationSuperAssureur=false "
			+ "	and d.numeroAvenant is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateDevis DESC"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_REFUSE_ASSUREUR_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and d.numeroAvenant is null"
			+ " and d.validationSuperAssureur=false "
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateDevis DESC"),
		
//		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVEC_PIECE_A_VALIDER_BY_ID_COURTIER, 
//		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
//				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
//				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
//				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
//			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c inner join d.taGedUtilisateur g"
//			+ " where c.idCourtier = :idCourtier"
//			+ " and g.validationCourtier = false"
//			+ " or g.validationCourtier IS NULL"
//			+ " or g.validationYlyade =false"
//			+ " or g.validationYlyade IS NULL"
//			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and d.numeroAvenant is null"
			+ " and d.contrat=false"
			+ " and (d.validationGlobaleGedCourtier=false"
			+ " or d.validationGlobaleGedCourtier is null)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_A_VALIDER_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationGlobaleGedCourtier=true"
			+ " and d.soumisSouscription=true"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationYlyade is null"
			+ " and d.contrat=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_A_VALIDER_EN_ATTENTE_PAIEMENT_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationGlobaleGedCourtier=true"
			+ " and d.soumisSouscription=true"
			+ " and d.premierPaiementEffectue=false"
			+ " and d.validationYlyade is null"
			+ " and d.contrat=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVEC_PIECES_INVALIDES, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaGedUtilisateur ged join ged.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationGlobaleGedCourtier=true"
			+ " and d.soumisSouscription=true"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationYlyade is null"
			+ " and d.contrat=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " and ged.validationYlyade = false"
			+ " order by d.numDossierPolice"),
		
	///////////////////////////PAIEMENT
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_PAIEMENT_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationGlobaleGedCourtier=true"
			+ "	and d.numeroAvenant is null"
			+ " and d.soumisSouscription=true"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and d.numeroAvenant is null"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		////////////////////////PAIEMENT CHEQUE
		///COURTIER
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE_BY_ID_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is null"
			+ " and r.dateReceptionCheque is null"
			+ " and r.dateDepotCheque is null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE_BY_ID_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is not null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),

		
		///YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is null"
			+ " and r.dateReceptionCheque is null"
			+ " and r.dateDepotCheque is null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_CHEQUE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is not null"
			+ " and r.dateReceptionCheque is null"
			+ " and r.dateDepotCheque is null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_DEPOT_CHEQUE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is not null"
			+ " and r.dateReceptionCheque is not null"
			+ " and r.dateDepotCheque is null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateEnvoiChequeParCourtier is not null"
			+ " and r.dateReceptionCheque is not null"
			+ " and r.dateDepotCheque is not null"
			+ " and r.dateEncaissementCheque is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		///////////////////////PAIEMENT VIREMENT
		///COURTIER
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE_BY_ID_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateVirementEffectue is null"
			+ " and r.dateVirementRecu is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT_BY_ID_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateVirementEffectue is not null"
			+ " and r.dateVirementRecu is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		
		///YLYADE
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateVirementEffectue is null"
			+ " and r.dateVirementRecu is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taEcheances e left join e.taReglementAssurance r"
			+ " where d.contrat = false"
			+ " and d.taTReglement.codeTReglement = :codeTReglement"
			+ " and r.dateVirementEffectue is not null"
			+ " and r.dateVirementRecu is null"
			+ "	and d.numeroAvenant is null"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateEffet"),
		
		///////////////////////
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_VALIDATION_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationYlyade=true"
			+ "	and d.numeroAvenant is null"
			+ " and d.contrat=false"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and d.numeroAvenant is null"
			+ " and d.soumisSouscription=true"
			+ " and d.contrat=false"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and d.numeroAvenant is null"
			+ " and d.soumisSouscription=true"
			+ " and d.contrat=false"
			+ " and d.validationYlyade=true"
			+ " and d.validationSuperAssureur is null"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		
		///////////////////////////////////////// AVENANT
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.numeroAvenant is not null"
			+ " and (d.validationGlobaleGedCourtier=false"
			+ " or d.validationGlobaleGedCourtier is null)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.numeroAvenant is not null"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.soumisSouscription=true"
			+ " and (d.contrat=false or d.contrat is null)"
			+ " and d.numeroAvenant is not null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.numeroAvenant is not null"
			+ " and d.soumisSouscription=true"
			+ " and (d.contrat=false or d.contrat is null)"
			+ " and d.validationYlyade=true"
			+ " and d.validationSuperAssureur is null"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_AVENANT_REFUSE_ASSUREUR_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.numeroAvenant is not null"
			+ " and d.validationSuperAssureur=false "
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateDevis DESC"),
		
		//avenants YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_AVENANT_REFUSE_ASSUREUR, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationSuperAssureur=false "
			+ "	and d.numeroAvenant is not null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.dateDevis DESC"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationGlobaleGedCourtier=true"
			+ "	and d.numeroAvenant is not null"
			+ " and d.soumisSouscription=true"
			+ " and d.premierPaiementEffectue=false"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_VALIDATION_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.validationYlyade=true"
			+ "	and d.numeroAvenant is not null"
			+ " and (d.contrat=false or d.contrat is null)"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where  d.soumisSouscription=true"
			+ " and (d.contrat=false or d.contrat is null)"
			+ " and d.numeroAvenant is not null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and d.premierPaiementEffectue=true"
			+ " and d.validationGlobaleGedCourtier=true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		////////////////////////////////////// FIN AVENANT
		
		//////////////////////////////SOUMIS A EXPERTISE
		//////////COURTIER
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and (d.soumisEtudeAssureur=false or d.soumisEtudeAssureur is null )"
			+ " and d.validationApresetudeYlyade=null "
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.soumisEtudeAssureur=true"
			+ " and d.soumisSouscription=false"
			+ " and d.validationAssureurApresetude is null"
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_VALIDE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and ((d.validationApresetudeYlyade is null and d.validationAssureurApresetude= true) or d.validationApresetudeYlyade = true)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and (d.validationApresetudeYlyade = false or d.validationAssureurApresetude = false)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ "	and  d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and (d.refusDefinitifYlyade = true or d.refusDefinitifSuperAssureur = true)"
			+ " order by d.numDossierPolice"),


		
		//////////YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.soumisEtude=true"
			+ " and (d.soumisEtudeAssureur=false or d.soumisEtudeAssureur is null )"
			+ " and d.soumisSouscription=false"
			+ " and d.validationApresetudeYlyade=null "
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.soumisEtudeAssureur=true"
			+ " and d.soumisSouscription=false"
			+ " and d.validationAssureurApresetude is null"
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and d.validationSuperAssureur is null"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_VALIDE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where  d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and d.numeroAvenant is null"
			+ " and d.validationYlyade is null"
			+ " and ((d.validationApresetudeYlyade is null and d.validationAssureurApresetude= true) or d.validationApresetudeYlyade = true)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where  d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and (d.validationApresetudeYlyade = false or d.validationAssureurApresetude = false)"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where  d.soumisSouscription=false"
			+ " and d.soumisEtude=true"
			+ " and d.contrat=false"
			+ " and (d.refusDefinitifYlyade = true or d.refusDefinitifSuperAssureur = true)"
			+ " order by d.numDossierPolice"),
		// a voir si refusé definitif ou autre
		

		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_ACTIF, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "where d.contrat = true "
				+ "order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_CONTRAT_BY_CODE_LIGHT, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "where d.numDossierPolice like :numDevis and d.contrat = true order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_PLUS, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
				+ "from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "where d.contrat = true"
				+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_LIGHT_PLUS_EXTRACTION, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd,"
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier,"
				+ " c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren,"
				+ " ech.liblTEcheance, d.dateEcheance, d.montantCommissionCourtier, d.montantPrimeAnnuelleTTC) "
				+ "from TaDossierRcd d left join d.taTEcheance ech left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c "
				+ "where d.contrat = true"
				+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier and d.contrat = true order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_ID_ASSURE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where a.idAssure = :idAssure and d.contrat = true order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_AVENANT_BY_NUM_POLICE, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.numDossierPolice = :numDossierPolice and d.numeroAvenant is not null order by d.numDossierPolice, d.lettrePjNumPolice, d.numeroAvenant"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_A_TRAITER_BY_ID_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = true"
			+ " and d.traite = false"
			+ " and d.suspenduAvenant = false"
			+ " and d.validationSuperAssureur = true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_A_TRAITER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c"
			+ " where d.contrat = true"
			+ " and d.traite = false"
			+ " and d.suspenduAvenant = false"
			+ " and d.validationSuperAssureur = true"
			+ " and (d.refusDefinitifYlyade = false or d.refusDefinitifYlyade is null)"
			+ " and (d.refusDefinitifSuperAssureur = false or d.refusDefinitifSuperAssureur is null)"
			+ " order by d.numDossierPolice"),
		
		
		
		
		///////////////ESPACE PAIEMENT CONTRATS
		////YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayPlus30"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayPlus30"
			+ " and e.dateDebutEcheance > current_date"
			+ " order by d.dateEffet"),
		
	////YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_DANS_EXACTEMENT_X_JOURS, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and date(e.dateDebutEcheance) = date(:todayPlus30)"
			+ " order by d.dateEffet"),
			
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSE_DEPUIS_EXACTEMENT_X_JOURS, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and date(e.dateDebutEcheance) = date(:todayMoinsX)"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSEE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayMoins10"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c	"
			+ " where d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and d.dateEcheance < :todayPlus30"
			+ " order by d.dateEffet"),
		//ENTITE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_MIS_EN_DEMEURE, 
		query="select distinct d "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayMoins20"
			+ " order by d.dateEffet"),
		
		//ENTITE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT, 
		query="select distinct d "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayMoins30"
			+ " order by d.dateEffet"),
		//ENTITE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT_41_JOURS, 
		query="select distinct d "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.contratEnCours = false"
			+ " and s.codeTStatut = :codeTStatut"
			+ " and e.dateDebutEcheance < :todayMoins41"
			+ " order by d.dateEffet"),

		
		////COURTIER
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_BY_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and r.dateReglement is null"
			+ " and e.dateDebutEcheance < :todayPlus30"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE_BY_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where r.dateReglement is null"
			+ " and c.idCourtier = :idCourtier"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayPlus30"
			+ " and e.dateDebutEcheance > current_date"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_TERMES_DEPASSEE_BY_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaEcheance e left join e.taReglementAssurance r left join e.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where c.idCourtier = :idCourtier"
			+ " and r.dateReglement is null"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and e.dateDebutEcheance < :todayMoins10"
			+ " order by d.dateEffet"),
		
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE_BY_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c	"
			+ " where c.idCourtier = :idCourtier"
			+ " and d.contrat = true"
			+ " and d.suspenduAvenant = false"
			+ " and d.contratEnCours = true"
			+ " and d.dateEcheance < :todayPlus30"
			+ " order by d.dateEffet"),
		
		////////////////RESILIATION
		////YLYADE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_STATUT, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where s.codeTStatut = :codeTStatut"
			+ " order by d.dateEffet"),
		////COURTIER
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_BY_STATUT_AND_BY_COURTIER, 
		query="select new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where c.idCourtier = :idCourtier "
			+ " and s.codeTStatut = :codeTStatut"
			+ " order by d.dateEffet"),
		
		
		/////METHODE POUR SCHEDULE (TIMERSERVICE)
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_EN_COURS, 
		query="select d from TaDossierRcd d "
			+ " where d.contrat = true"
			+ " and d.contratEnCours = true"
			+ " order by d.dateEffet"),
		
		
		/////////ATTESTATION NOMINATIVE
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE_BY_COURTIER, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale,"
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren ) "
			+ " from TaAttestationNominative attest left join attest.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s"
			+ " where c.idCourtier = :idCourtier "
			+ " and attest.montantPaye is null"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale," 
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren )" 
			+ " from TaAttestationNominative attest left join attest.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s" 
			+ " where attest.montantPaye is null "
			+ " and d.suspenduAvenant = false"
			+ " order by d.dateEffet"),
		
		@NamedQuery(name=TaDossierRcd.QN.FIND_ALL_CONTRAT_ATTENTE_VALIDATION_ATTESTATION_NOMINATIVE, 
		query="select distinct new fr.ylyade.courtage.dto.TaDossierRcdDTO(d.idDossierRcd,"
				+ "a.idAssure,a.codeAssure,a.raisonSociale," 
				+ "tce.idContactEntreprise,tce.nom,tce.prenom,d.idDossierRcd," 
				+ "d.numDossierPolice, d.lettrePjNumPolice, d.contrat, d.numeroAvenant, c.idCourtier, c.codeCourtier, c.nomDenominationSociale, t.idTAssure, t.codeTAssure, d.dateEffet, a.codeSiren )" 
			+ " from TaAttestationNominative attest left join attest.taDossierRcd d left join d.taAssure a left join a.taTAssure t left join a.taContactEntreprise tce left join a.taCourtier c left join d.taTStatut s" 
			+ " where attest.montantPaye is null "
			+ " and attest.validationCourtier = true "
			+ " and attest.validationYlyade = false "
			+ " and d.suspenduAvenant = false "
			+ " order by d.dateEffet")

})
//Requete pour dossier avec pièce invalide (validation courtier et ylyade nulle ou false)

//select DISTINCT id_devis_rc_pro_detail from ta_dossier_rcd d
//INNER JOIN ta_ged_utilisateur g ON d.id_devis_rc_pro_detail = g.id_devis_rc_pro_ta_devis_rc_pro
//where g.validation_courtier = 'false'
// or g.validation_courtier IS NULL
// or g.validation_ylyade ='false'
// or g.validation_ylyade IS NULL;


public class TaDossierRcd implements Serializable, IDonneStatut {

	private static final long serialVersionUID = 7137444485563896767L;
	
	public static class QN {
		public static final String FIND_ACTIVE_BY_ID_ASSURE = "TaDossierRcd.findActiveByIdAssureDTO";
		public static final String FIND_NON_ACTIVE_BY_ID_ASSURE = "TaDossierRcd.findNonActiveByIdAssureDTO";
		public static final String COUNT_ACTIVE_BY_ID_COURTIER = "TaDossierRcd.countActiveByIdCourtier";
		
		public static final String FIND_ALL_LIGHT_ACTIF = "TaDossierRcd.findAllLightActif";
		public static final String FIND_BY_CODE_LIGHT = "TaDossierRcd.findByCodeLight";
		public static final String FIND_ALL_LIGHT_PLUS = "TaDossierRcd.findAllLightPlus";
		public static final String FIND_ALL_BY_ID_COURTIER = "TaDossierRcd.findAllByIdCourtier";
		public static final String FIND_ALL_BY_ID_ASSURE = "TaDossierRcd.findAllByIdAssure";
		
		public static final String FIND_REFUSE_ASSUREUR = "TaDossierRcd.findRefuseAssureur";
		public static final String FIND_REFUSE_ASSUREUR_BY_ID_COURTIER="TaDossierRcd.findRefuseAssureurByIdCourtier";
		public static final String FIND_ALL_AVEC_PIECE_A_VALIDER_BY_ID_COURTIER="TaDossierRcd.findAllAvecPieceAValiderByIdCourtier";
		public static final String FIND_ALL_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER="TaDossierRcd.findAllPieceManquanteOuRefuseByIdCourtier";
		public static final String FIND_ALL_VALIDATION_YLYADE= "TaDossierRcd.findAllValidationYlyade";
		
				
		/////AVENANTS
		//courtier
		public static final String FIND_ALL_AVENANT_PIECE_MANQUANTE_OU_REFUSE_BY_ID_COURTIER = "TaDossierRcd.findAllAvenantPieceManquanteOuRefuseByIdCourtier";
		public static final String FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER="TaDossierRcd.findAllAvenantEnAttentePaiementByIdCourtier";
		public static final String FIND_ALL_AVENANT_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER="TaDossierRcd.findAllAvenantAttenteValidationAssureurByIdCourtier";
		public static final String FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER="TaDossierRcd.findAllAvenantAttenteValidationYlyadeByIdCourtier";
		public static final String FIND_AVENANT_REFUSE_ASSUREUR_BY_ID_COURTIER="TaDossierRcd.findAvenantRefuseAssureurByIdCourtier";
		//ylyade
		public static final String FIND_AVENANT_REFUSE_ASSUREUR="TaDossierRcd.findAvenantRefuseAssureur";
		public static final String FIND_ALL_AVENANT_EN_ATTENTE_PAIEMENT_YLYADE="TaDossierRcd.findAllAvenantEnAttentePaiementYlyade";
		public static final String FIND_ALL_AVENANT_VALIDATION_YLYADE="TaDossierRcd.findAllAvenantValidationYlyade";
		public static final String FIND_ALL_AVENANT_ATTENTE_VALIDATION_YLYADE = "TaDossierRcd.findAllAvenantEnAttenteValidationYlyade";
		
		
		/////SOUMIS ETUDE
		//courtier
		public static final String FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER = "TaDossierRcd.findAllSoumisEtudeAttenteValidationYlyadeByIdCourtier";
		public static final String FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER= "TaDossierRcd.findAllSoumisEtudeAttenteValidationAssureurByIdCourtier";
		public static final String FIND_ALL_SOUMIS_ETUDE_VALIDE_BY_ID_COURTIER= "TaDossierRcd.findAllSoumisEtudeValideByIdCourtier";
		public static final String FIND_ALL_SOUMIS_ETUDE_REFUSE_BY_ID_COURTIER= "TaDossierRcd.findAllSoumisEtudeRefuseByIdCourtier";
		public static final String FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF_BY_ID_COURTIER="TaDossierRcd.findAllSoumisEtudeRefuseDefinitifByIdCourtier";
		//ylyade
		public static final String FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_YLYADE ="TaDossierRcd.findAllSoumisEtudeAttenteValidationYlyade";
		public static final String FIND_ALL_SOUMIS_ETUDE_ATTENTE_VALIDATION_ASSUREUR="TaDossierRcd.findAllSoumisEtudeAttenteValidationAssureur";
		public static final String FIND_ALL_SOUMIS_ETUDE_VALIDE="TaDossierRcd.findAllSoumisEtudeValide";
		public static final String FIND_ALL_SOUMIS_ETUDE_REFUSE="TaDossierRcd.findAllSoumisEtudeRefuse";
		public static final String FIND_ALL_SOUMIS_ETUDE_REFUSE_DEFINITIF="TaDossierRcd.findAllSoumisEtudeRefuseDefinitif";
		public static final String FIND_ALL_AVEC_PIECES_INVALIDES="TaDossierRcd.findAllAvecPiecesInvalides";
		
		public static final String FIND_ALL_ATTENTE_VALIDATION_YLYADE_BY_ID_COURTIER= "TaDossierRcd.findAllAttenteValidationYlyadeByIdCourtier";
		public static final String FIND_ALL_ATTENTE_VALIDATION_ASSUREUR_BY_ID_COURTIER="TaDossierRcd.findAllAttenteValidationAssureurByIdCourtier";
		
		public static final String FIND_ALL_A_VALIDER_YLYADE="TaDossierRcd.findAllAValiderYlyade";
		public static final String FIND_ALL_A_VALIDER_EN_ATTENTE_PAIEMENT_YLYADE = "TaDossierRcd.findAllAValiderEnAttentePaiementYlyade";
		///////PAIEMENT
		
		public static final String FIND_ALL_EN_ATTENTE_PAIEMENT_YLYADE="TaDossierRcd.findAllEnAttentePaiementYlyade";
		public static final String FIND_ALL_EN_ATTENTE_PAIEMENT_BY_ID_COURTIER="TaDossierRcd.findAllEnAttentePaiementByIdCourtier";
		////CHEQUE
		public static final String FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE_BY_ID_COURTIER="TaDossierRcd.findAllEnAttenteEnvoiChequeByIdCourtier";
		public static final String FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE_BY_ID_COURTIER="TaDossierRcd.findAllEnAttenteEncaissementChequeByIdCourtier";
		public static final String FIND_ALL_EN_ATTENTE_ENVOI_CHEQUE="TaDossierRcd.findAllEnAttenteEnvoiCheque";
		public static final String FIND_ALL_EN_ATTENTE_RECEPTION_CHEQUE="TaDossierRcd.findAllEnAttenteReceptionCheque";
		public static final String FIND_ALL_EN_ATTENTE_DEPOT_CHEQUE="TaDossierRcd.findAllEnAttenteDepotCheque";
		public static final String FIND_ALL_EN_ATTENTE_ENCAISSEMENT_CHEQUE="TaDossierRcd.findAllEnAttenteEncaissementCheque";
		//VIREMENT
		public static final String FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE_BY_ID_COURTIER ="TaDossierRcd.findAllEnAttenteVirementEffectueByIdCourtier";
		public static final String FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT_BY_ID_COURTIER="TaDossierRcd.findAllEnAttenteReceptionVirementByIdCourtier";
		public static final String FIND_ALL_EN_ATTENTE_VIREMENT_EFFECTUE ="TaDossierRcd.findAllEnAttenteVirementEffectue";
		public static final String FIND_ALL_EN_ATTENTE_RECEPTION_VIREMENT="TaDossierRcd.findAllEnAttenteReceptionVirement";
		
		
		public static final String CONTRAT_FIND_ACTIVE_BY_ID_ASSURE = "TaContratRcPro.findActiveByIdAssureDTO";
		public static final String CONTRAT_COUNT_ACTIVE_BY_ID_COURTIER = "TaContratRcPro.countActiveByIdCourtier";
		public static final String CONTRAT_COUNT_PRESQUE_FIN_BY_ID_COURTIER = "TaContratRcPro.countPresqueFinByIdCourtier";
		public static final String CONTRAT_COUNT_FINI_BY_ID_COURTIER = "TaContratRcPro.countFiniByIdCourtier";
		
		public static final String FIND_ALL_CONTRAT_LIGHT_ACTIF = "TaDossierRcd.findAllContratLightActif";
		public static final String FIND_CONTRAT_BY_CODE_LIGHT = "TaDossierRcd.findContratByCodeLight";
		public static final String FIND_ALL_CONTRAT_LIGHT_PLUS = "TaDossierRcd.findAllContratLightPlus";
		public static final String FIND_ALL_CONTRAT_LIGHT_PLUS_EXTRACTION= "TaDossierRcd.findAllContratLightPlusExtraction";
		public static final String FIND_ALL_CONTRAT_BY_ID_COURTIER = "TaDossierRcd.findAllContratByIdCourtier";
		
		public static final String FIND_ALL_CONTRAT_A_TRAITER_BY_ID_COURTIER = "TaDossierRcd.findAllContratATraiterByIdCourtier";
		public static final String FIND_ALL_CONTRAT_A_TRAITER = "TaDossierRcd.findAllContratATraiter";
		
		
		public static final String FIND_ALL_CONTRAT_BY_ID_ASSURE = "TaDossierRcd.findAllContratByIdAssure";
		
		public static final String FIND_ALL_AVENANT_BY_NUM_POLICE = "TaDossierRcd.findAllAvenantByNumPolice";
		
		
		public static final String FIND_ALL_CONTRAT_EN_COURS= "TaDossierRcd.findAllContratEnCours";
		public static final String FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT_41_JOURS = "TaDossierRcd.findAllContratSuspendusNonPaiement41Jours";
		
		
		//ESPACE PAIEMENT
		public static final String FIND_ALL_CONTRAT_ARRIVANT_TERMES ="TaDossierRcd.findAllContratArrivantTermes" ;
		public static final String FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE="TaDossierRcd.findAllContratArrivantAndPasDepasse";
		public static final String FIND_ALL_CONTRAT_ARRIVANT_TERMES_DANS_EXACTEMENT_X_JOURS = "TaDossierRcd.findAllContratArrivantTermesDansExactementXjours";
		public static final String FIND_ALL_CONTRAT_TERMES_DEPASSE_DEPUIS_EXACTEMENT_X_JOURS =  "TaDossierRcd.findAllContratTermesDepasseDepuisExactementXjours";
		public static final String FIND_ALL_CONTRAT_ARRIVANT_TERMES_BY_COURTIER ="TaDossierRcd.findAllContratArrivantTermesByCourtier" ;
		public static final String FIND_ALL_CONTRAT_ARRIVANT_TERMES_AND_PAS_DEPASSE_BY_COURTIER="TaDossierRcd.findAllContratArrivantTermesAndPasDepasseByCourtier";
		public static final String FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE="TaDossierRcd.findAllContratArrivantEcheanceAnnuelle";
		public static final String FIND_ALL_CONTRAT_ARRIVANT_ECHEANCE_ANNUELLE_BY_COURTIER="TaDossierRcd.findAllContratArrivantEcheanceAnnuelleByCourtier";
		public static final String FIND_ALL_CONTRAT_TERMES_DEPASSEE="TaDossierRcd.findAllContratTermesDepassee";
		public static final String FIND_ALL_CONTRAT_TERMES_DEPASSEE_BY_COURTIER="TaDossierRcd.findAllContratTermesDepasseeByCourtier";
		public static final String FIND_ALL_CONTRAT_MIS_EN_DEMEURE="TaDossierRcd.findAllContratMisEnDemeure";
		public static final String FIND_ALL_CONTRAT_SUSPENDUS_NON_PAIEMENT="TaDossierRcd.findAllContratSuspendusNonPaiement";
		
		//RESILIATION 
		public static final String FIND_ALL_CONTRAT_BY_STATUT = "TaDossierRcd.findAllContratByStatut";
		public static final String FIND_ALL_CONTRAT_BY_STATUT_AND_BY_COURTIER= "TaDossierRcd.findAllContratByStatutAndByCourtier";
		
		//ATTESTATION
		public static final String FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE_BY_COURTIER="TaDossierRcd.findAllContratAttentePaiementAttestationNominativeByCourtier";
		public static final String FIND_ALL_CONTRAT_ATTENTE_PAIEMENT_ATTESTATION_NOMINATIVE="TaDossierRcd.findAllContratAttentePaiementAttestationNominative";
		public static final String FIND_ALL_CONTRAT_ATTENTE_VALIDATION_ATTESTATION_NOMINATIVE = "TaDossierRcd.findAllContratAttenteValidationAttestationNominative";
		
	}

	private Integer idDossierRcd;
	
	private TaAssure taAssure;
	private TaCourtier taCourtier;
	private List<TaSinistreAntecedent> taSinistreAntecedent = new ArrayList<TaSinistreAntecedent>();	
	
	private List<TaGedUtilisateur> taGedUtilisateur = new ArrayList<TaGedUtilisateur>();
	
	private Boolean contrat = false;
	private String numDossierPolice;
	private Integer version;
	private Integer numeroAvenant;
	
	private List<TaTStatut> taTStatut = new ArrayList<TaTStatut>();
	
	private Date dateEffet;
	private Date dateEcheance;
	private Date dateSouscription;
	private Date dateDevis;
	
	private Boolean resiliation = false;
	private String motifResiliation;
	private Date dateResiliation;
	private Date dateResiliationContrat;
	
	private Boolean retractation = false;
	private Date dateRetractation;
	
	private BigDecimal primeNette;
	private BigDecimal montantPrime; 
	
	private BigDecimal montantPremierReglement;
	private Date dateDebutPremierePeriode;
	private Date dateFinPremierePeriode;
	private BigDecimal montantTaxesDiversesAssurance;
	
	private TaDossierRcd contratOrigine;
	private TaDossierRcd contratParentDirect;
	
	private BigDecimal chiffreAffaireExercicePrevisionnel;
	private Integer effectifTotalExercice;
	
	private Boolean resilieNonPaiement = false;
	private Boolean resilieFausseDeclaration = false;
	
	private Date resilieNonPaiementContrat = null;
	private Date resilieFausseDeclarationContrat  = null;
	private Date resilieEcheanceContrat  = null;
	private Date resilieAmiableContrat  = null;
	private Date resilieCessationActiviteContrat  = null;
	private Date resilieSansEffetContrat  = null;
	
	

	private Boolean interventionChantierCoutMax = false;
	private Boolean dejaAssurer = false; //deja assure ailleur
	private Boolean contratEnCours = false;
	private Boolean reprisePasse = false;
	
//	private TaDossierRcd devisDetailOrigine;
	
	private String codeSousTraitance;
	private BigDecimal pourcentSoustraitance;
	
	private BigDecimal fraisRcPro; //frais du courtier/frais de dossier
	private BigDecimal tauxCommission;
	private BigDecimal montantCommissionCourtierUneEcheance;
	private BigDecimal montantCommissionCourtier;
	private BigDecimal tauxSurCommissionCourtier;
	private BigDecimal montantSurCommissionCourtier;
	private BigDecimal montantProtectionJuridique;
	private BigDecimal montantTaxeFiscale;
	private BigDecimal tauxTaxeFiscale;
	private BigDecimal montantFraisDeGestion;
	private BigDecimal primeAnnuelle;
	private BigDecimal primeAnnuelleComplete;
	private BigDecimal montantFraisFractionnement;
	
	private String logDetailCalculPrime;
	private String logDetailCalculPremierReglement;
	
	private TaTReglement taTReglement;
	private TaTEcheance taTEcheance;
	private TaTSousTraitance taTSousTraitance;
	
	private String codeFranchise;
	private BigDecimal franchise;
	private String codeEcheance; //trimestriel/semestriel
	
//	private byte[] imgDevisRcd;
	private String nomFichier;
	private Integer taille;
	private String typeMime;
	
	private byte[] imgContratRcd;
	private String nomFichierContrat;
	private Integer tailleContrat;
	private String typeMimeContrat;
	
	private String assureurPrecedentRcd;
	private Date dateEffetContratPrecendentRcd;
	private String policeContratPrecedentRcd;
	private BigDecimal coutGlobalSinistreRcd;
	private Integer nbSinistre5ans;	   
	private Integer nbSinistreTotal;
	private BigDecimal montantSinistre;
	private Date dateResiliationContratPrecedent;
	private String motifResiliationContratPrecedent;
			
	private List<TaDossierRcdActivite> taDevisRcProActivites;
	private List<TaDossierRcdTauxPib> taDevisRcProTauxPibs = new ArrayList<TaDossierRcdTauxPib>();
	private List<TaEcheance> taEcheances = new ArrayList<TaEcheance>();
	private TaTauxAssurance taTauxAssurance;
	private BigDecimal tauxTauxAssurance;
	
	/*
	 * Nouveaux champs 
	 */
	//ecran 1
	private Boolean experienceActivite3ans = false;
	private Boolean experienceActivte5ans = false;
	private Boolean exerceActiviteNomenclature = false;
	private Boolean coutOuvrageInferieur15k = false;
	private Boolean montantTravauxHtMax50k = false;
	private Boolean montantTravauxHtMax100k = false;
	private Boolean montantTravauxHtMax250k = false;
	private Boolean montantTravauxHtMax500k = false;
	private Boolean montantTravauxHtMax1m = false;
	private Boolean interventionConstructeurMaisonIndiv = false;
	private Boolean interventionContractantGeneral = false;
	private Boolean interventionBatiment = false;
	private Boolean interventionMaitreOeuvre = false;
	private Boolean interventionImmobilier = false;
	private Boolean interventionFabricantMatConstruction = false;
	private Boolean activitePrincNegoceFabrMatConstructionNonPose = false;
	private Boolean travauxTechniqueNonCourant = false;
	private Boolean interventionMonumentHistorique = false;
	private Boolean avisTechnique = false;
	private Boolean documentUnique = false;
	private Boolean respectRegles = false;
	private Boolean respectDispositionSousTraitance = null;
	
	//ecran 2
	private Integer anneesExperienceActivite;
	private Integer effectifSurChantier;
	private Integer effectifApprentis;
	private Integer effectifCommerciauxAdministratifs;
	private Integer effectifEncadrement;
	private Boolean qualibat = false;
	private BigDecimal pourcentCaSousTraitDernierExercice;
	private BigDecimal pourcentCaSousTraitExerciceNMoins1;
	private BigDecimal pourcentCaSousTraitExerciceNMoins2;
	private BigDecimal caTotalHtExerciceNMoins1;
	private BigDecimal caTotalHtExerciceNMoins2;

	//ecran 3
	private Boolean ancienneteMoins12mois = false;
	private Boolean anciennete12a36mois = false;
	private Boolean anciennetePlus36mois = false;
	private Boolean assureMemeRisque = false;
	private Boolean assureMemeRisqueRcd = false;
	private Boolean assureMemeRisqueRce = false;
	private Boolean antecedentRcdContratEnCours = false;
	private Boolean antecedentRcdContratResilie = false;
	private Boolean antecedentRceContratEnCours = false;
	private Boolean antecedentRceContratResilie = false;
	private Date antecedentRcdDateResiliation;
	private Date antecedentRceDateResiliation;
	private Boolean antecedentRcdResilieAssure = false;
	private Boolean antecedentRceResilieAssure = false;
	private Boolean antecedentRcdResilieEcheance = false;
	private Boolean antecedentRceResilieEcheance = false;
	private Boolean antecedentRcdHausseTarif = false;
	private Boolean antecedentRceHausseTarif = false;
	private Boolean antecedentRcdChangementActivite = false;
	private Boolean antecedentRceChangementActivite = false;
	private Boolean antecedentRcdResilieAmiable = false;
	private Boolean antecedentRceResilieAmiable = false;
	private Boolean antecedentRcdAssureur = false;
	private Boolean antecedentRceAssureur = false;
	private Boolean antecedentRcdNonPaiementPrime = false;
	private Boolean antecedentRceNonPaiementPrime = false;
	private Boolean antecedentRcdSinistre = false;
	private Boolean antecedentRceSinistre = false;
	private Boolean antecedentRcdModifActivite = false;
	private Boolean antecedentRceModifActivite = false;
	private Boolean antecedentRcdAutre = false;
	private Boolean antecedentRceAutre = false;
	private Boolean interruptionAssuranceMoins6mois = false;
	private Boolean interruptionAssurance6a12mois = false;
	private Boolean interruptionAssurance12a24mois = false;
	private Boolean interruptionAssurance24a36mois = false;
	private Boolean interruptionAssurance36a60mois = false;
	private Boolean interruptionAssurance60a84mois = false;
	private Boolean interruptionAssurancePlusDe84mois = false;
	
	private String antecedentRcdPoliceAssureur;
	private String antecedentRcePoliceAssureur;
	private String antecedentRcdNomAssureur;
	private String antecedentRceNomAssureur;
	
	//ecran 4
	private Boolean sinistraliteLiquidationSocieteDemandeuse = false;
	private Boolean sinistraliteLiquidationAutreSociete = false;
	private Boolean sinistraliteRisqueRefusAssurance = false;
	private Boolean sinistraliteMiseEnCause = false;
	private Boolean sinistraliteEvenementEngageantResp = false;
	private String sinistraliteEvenementFaits;
	
	//ecran 6
	private Boolean reprisePasseMoinsDe3mois = false;
	private Boolean reprisePasseDe3a6mois = false;
	private Boolean reprisePasseDe6a12mois = false;
	private Boolean territorialiteLieuFranceMetrop = false;
	private Boolean territorialiteLieuCorse = false;
	private Boolean territorialiteLieuDomtom = false;
	
	//ecran 7
	private Boolean informerCaractereObligatoire = false;
	private Boolean informationPropositionPartieIntegranteContrat = false;
	private Boolean autoriseAssureurCommuniquerReponses = false;
	private Boolean opposeUtilisationDonneesFinCommerciale = false;
	
	
	
	private Date dateRepriseDuPasse;
	private BigDecimal tauxRisqueParFamilleActivite;
	private BigDecimal tauxNombreActivite;
	private String codeInterruptionAssurance;
	private BigDecimal tauxInterruptionAssurance;
	private String codeReprisePasse;
	private BigDecimal tauxReprisePasse;
	private BigDecimal tauxResiliationNonPaiement;
	private BigDecimal tauxAntecedentSinistre;
	private Integer nbAntecedentSinistre;
	private BigDecimal coutMaxPourNbAntecedentSinistre;

	 /*
	  * Nouveaux champs 
	  */
	private Boolean validationCourtier = null;
	private String commentaireCourtier;
	private Boolean soumisEtude = null;
	private Boolean soumisEtudeAssureur = null;
	private Boolean validationApresetudeYlyade = null;
	private Boolean validationAssureurApresetude = null;
	private Boolean validationSuperAssureur = null;
	private Boolean validationYlyade = null;
	private Boolean traite = false;
	private Boolean soumisSouscription = false;
	private Boolean validationGlobaleGedCourtier = null;
	private Boolean premierPaiementEffectue = false;
	
	private BigDecimal txRegulCaHt;
	private String commentaireValidationYlyade;
	private String commentaireApresExpertise;
	private String commentaireValidationAssureur;
	private Boolean inclusionFraisDossier;
	private BigDecimal montantDprsa;
	private BigDecimal montantFraisCompagnieSuperAssureur;
	
	private BigDecimal montantPrimeAnnuelleHT;
	private BigDecimal montantPrimeAnnuelleTTC;
	private BigDecimal montantPrimeEcheanceHT;
	private BigDecimal montantPrimeEcheanceTTC;
	private BigDecimal montantTaxeTotal;
	
	private BigDecimal montantTotalTaxes9pc;
	private BigDecimal montantTotalTaxes134pc;
	private BigDecimal tarifAnnuelTtcPlusFraisFractionnement;
	private BigDecimal pourcentSousTraitanceCalcul;
	
	private BigDecimal tarifAnnuelTotalHT;
	private BigDecimal tarifAnnuelTotalTTC;
	private Boolean validationGlobaleGedYlyade;
	private Boolean refusDefinitifYlyade;
	private Boolean refusDefinitifSuperAssureur;
	private String commentaireRefusDefinitifYlyade;
	private String commentaireRefusDefinitifSuperAssureur;
	
	private BigDecimal primeNetteYlyade;
	private BigDecimal primeNetteAssureur;
	
	private Date misEnDemeure = null;
	private Date suspenduNonPaiement = null;
	
	private Boolean suspenduAvenant = false;
	
	private String lettrePjNumPolice;
	
//	private String jsonDATA;
	
//	private List<TaAttestationNominative> taAttestationNominative = new ArrayList<TaAttestationNominative>();
	  
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_devis_rc_pro_detail", unique = true, nullable = false)
	public Integer getIdDossierRcd() {
		return idDossierRcd;
	}

	public void setIdDossierRcd(Integer idDevisRcPro) {
		this.idDossierRcd = idDevisRcPro;
	}

	@Column(name = "resilie_non_paiement")
	public Boolean getResilieNonPaiement() {
		return resilieNonPaiement;
	}

	public void setResilieNonPaiement(Boolean resilieNonPaiement) {
		this.resilieNonPaiement = resilieNonPaiement;
	}

	@Column(name = "resilie_fausse_declaration")
	public Boolean getResilieFausseDeclaration() {
		return resilieFausseDeclaration;
	}

	public void setResilieFausseDeclaration(Boolean resilieFausseDeclaration) {
		this.resilieFausseDeclaration = resilieFausseDeclaration;
	}

	@Column(name = "date_effet")
	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateRealisation) {
		this.dateEffet = dateRealisation;
	}

	@Column(name = "date_resiliation")
	public Date getDateResiliation() {
		return dateResiliation;
	}

	public void setDateResiliation(Date dateResiliation) {
		this.dateResiliation = dateResiliation;
	}

//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "id_devis_detail_origine")
//	public TaDossierRcd getDevisDetailOrigine() {
//		return devisDetailOrigine;
//	}
//
//	public void setDevisDetailOrigine(TaDossierRcd devisDetailOrigine) {
//		this.devisDetailOrigine = devisDetailOrigine;
//	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

//	@Column(name = "img_devis_rc_pro")
//	public byte[] getImgDevisRcd() {
//		return imgDevisRcd;
//	}
//
//	public void setImgDevisRcd(byte[] imgDevisRcPro) {
//		this.imgDevisRcd = imgDevisRcPro;
//	}

	@Column(name = "montant_prime")
	public BigDecimal getMontantPrime() {
		return montantPrime;
	}

	public void setMontantPrime(BigDecimal montantPrime) {
		this.montantPrime = montantPrime;
	}

	@Column(name = "code_sous_traitance")
	public String getCodeSousTraitance() {
		return codeSousTraitance;
	}

	public void setCodeSousTraitance(String codeSousTraitance) {
		this.codeSousTraitance = codeSousTraitance;
	}

	@Column(name = "pourcent_soustraitance")
	public BigDecimal getPourcentSoustraitance() {
		return pourcentSoustraitance;
	}

	public void setPourcentSoustraitance(BigDecimal pourcentSoustraitance) {
		this.pourcentSoustraitance = pourcentSoustraitance;
	}

	@Column(name = "frais_rc_pro")
	public BigDecimal getFraisRcPro() {
		return fraisRcPro;
	}

	public void setFraisRcPro(BigDecimal fraisRcPro) {
		this.fraisRcPro = fraisRcPro;
	}

	@Column(name = "taux_commission")
	public BigDecimal getTauxCommission() {
		return tauxCommission;
	}

	public void setTauxCommission(BigDecimal tauxCommission) {
		this.tauxCommission = tauxCommission;
	}

	@Column(name = "deja_assurer")
	public Boolean getDejaAssurer() {
		return dejaAssurer;
	}

	public void setDejaAssurer(Boolean dejaAssurer) {
		this.dejaAssurer = dejaAssurer;
	}

	@Column(name = "contrat_en_cours")
	public Boolean getContratEnCours() {
		return contratEnCours;
	}

	public void setContratEnCours(Boolean contratEnCours) {
		this.contratEnCours = contratEnCours;
	}

	@Column(name = "nb_sinistre_5ans")
	public Integer getNbSinistre5ans() {
		return nbSinistre5ans;
	}

	public void setNbSinistre5ans(Integer nbSinistre5ans) {
		this.nbSinistre5ans = nbSinistre5ans;
	}

	@Column(name = "nb_sinistre_total")
	public Integer getNbSinistreTotal() {
		return nbSinistreTotal;
	}

	public void setNbSinistreTotal(Integer nbSinistreTotal) {
		this.nbSinistreTotal = nbSinistreTotal;
	}

	@Column(name = "montant_sinistre")
	public BigDecimal getMontantSinistre() {
		return montantSinistre;
	}

	public void setMontantSinistre(BigDecimal montantSinistre) {
		this.montantSinistre = montantSinistre;
	}

	@Column(name = "code_franchise")
	public String getCodeFranchise() {
		return codeFranchise;
	}

	public void setCodeFranchise(String codeFranchise) {
		this.codeFranchise = codeFranchise;
	}

	@Column(name = "franchise")
	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	@Column(name = "code_echeance")
	public String getCodeEcheance() {
		return codeEcheance;
	}

	public void setCodeEcheance(String codeEcheance) {
		this.codeEcheance = codeEcheance;
	}

//	@Column(name = "assureur_precedent_rcp")
//	public String getAssureurPrecedentRcp() {
//		return assureurPrecedentRcp;
//	}
//
//	public void setAssureurPrecedentRcp(String assureurPrecedentRcp) {
//		this.assureurPrecedentRcp = assureurPrecedentRcp;
//	}

	@Column(name = "assureur_precedent_rcd")
	public String getAssureurPrecedentRcd() {
		return assureurPrecedentRcd;
	}

	public void setAssureurPrecedentRcd(String assureurPrecedentRcd) {
		this.assureurPrecedentRcd = assureurPrecedentRcd;
	}

//	@Column(name = "date_effet_contrat_precedent_rcp")
//	public Date getDateEffetContratPrecendentRcp() {
//		return dateEffetContratPrecendentRcp;
//	}
//
//	public void setDateEffetContratPrecendentRcp(Date dateEffetContratPrecendentRcp) {
//		this.dateEffetContratPrecendentRcp = dateEffetContratPrecendentRcp;
//	}

	@Column(name = "date_effet_contrat_precedent_rcd")
	public Date getDateEffetContratPrecendentRcd() {
		return dateEffetContratPrecendentRcd;
	}

	public void setDateEffetContratPrecendentRcd(Date dateEffetContratPrecendentRcd) {
		this.dateEffetContratPrecendentRcd = dateEffetContratPrecendentRcd;
	}

//	@Column(name = "police_contrat_precedent_rcp")
//	public String getPoliceContratPrecedentRcp() {
//		return policeContratPrecedentRcp;
//	}
//
//	public void setPoliceContratPrecedentRcp(String policeContratPrecedentRcp) {
//		this.policeContratPrecedentRcp = policeContratPrecedentRcp;
//	}

	@Column(name = "police_contrat_precedent_rcd")
	public String getPoliceContratPrecedentRcd() {
		return policeContratPrecedentRcd;
	}

	public void setPoliceContratPrecedentRcd(String policeContratPrecedentRcd) {
		this.policeContratPrecedentRcd = policeContratPrecedentRcd;
	}

//	@Column(name = "montant_chantier_plus_eleve")
//	public BigDecimal getMontantChantierPlusEleve() {
//		return montantChantierPlusEleve;
//	}
//
//	public void setMontantChantierPlusEleve(BigDecimal montantChantierPlusEleve) {
//		this.montantChantierPlusEleve = montantChantierPlusEleve;
//	}
//
//	@Column(name = "part_pourcent_ca_sous_traitance")
//	public BigDecimal getParPourcentCaSousTraitance() {
//		return parPourcentCaSousTraitance;
//	}
//
//	public void setParPourcentCaSousTraitance(BigDecimal parPourcentCaSousTraitance) {
//		this.parPourcentCaSousTraitance = parPourcentCaSousTraitance;
//	}
//
//	@Column(name = "part_pourcent_ca_renovation")
//	public BigDecimal getParPourcentCaRenovation() {
//		return parPourcentCaRenovation;
//	}
//
//	public void setParPourcentCaRenovation(BigDecimal parPourcentCaRenovation) {
//		this.parPourcentCaRenovation = parPourcentCaRenovation;
//	}
//
//	@Column(name = "part_pourcent_client_particulier")
//	public BigDecimal getParPourcentClientParticulier() {
//		return parPourcentClientParticulier;
//	}
//
//	public void setParPourcentClientParticulier(BigDecimal parPourcentClientParticulier) {
//		this.parPourcentClientParticulier = parPourcentClientParticulier;
//	}
//
//	@Column(name = "part_pourcent_ca_pris_sous_traitance")
//	public BigDecimal getParPourcentCaPrisSousTraitance() {
//		return parPourcentCaPrisSousTraitance;
//	}
//
//	public void setParPourcentCaPrisSousTraitance(BigDecimal parPourcentCaPrisSousTraitance) {
//		this.parPourcentCaPrisSousTraitance = parPourcentCaPrisSousTraitance;
//	}
//
//	@Column(name = "part_pourcent_ca_neuf")
//	public BigDecimal getParPourcentCaNeuf() {
//		return parPourcentCaNeuf;
//	}
//
//	public void setParPourcentCaNeuf(BigDecimal parPourcentCaNeuf) {
//		this.parPourcentCaNeuf = parPourcentCaNeuf;
//	}
//
//	@Column(name = "part_pourcent_client_entreprise")
//	public BigDecimal getParPourcentClientEntreprise() {
//		return parPourcentClientEntreprise;
//	}
//
//	public void setParPourcentClientEntreprise(BigDecimal parPourcentClientEntreprise) {
//		this.parPourcentClientEntreprise = parPourcentClientEntreprise;
//	}

	@Column(name = "cout_global_sinistre_rcd")
	public BigDecimal getCoutGlobalSinistreRcd() {
		return coutGlobalSinistreRcd;
	}

	public void setCoutGlobalSinistreRcd(BigDecimal coutGlobalSinistreRcd) {
		this.coutGlobalSinistreRcd = coutGlobalSinistreRcd;
	}

//	@Column(name = "cout_global_sinistre_rcp")
//	public BigDecimal getCoutGlobalSinistreRcp() {
//		return coutGlobalSinistreRcp;
//	}
//
//	public void setCoutGlobalSinistreRcp(BigDecimal coutGlobalSinistreRcp) {
//		this.coutGlobalSinistreRcp = coutGlobalSinistreRcp;
//	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_taux_assurance_ta_taux_assurance")
	public TaTauxAssurance getTaTauxAssurance() {
		return taTauxAssurance;
	}

	public void setTaTauxAssurance(TaTauxAssurance taTauxAssurance) {
		this.taTauxAssurance = taTauxAssurance;
	}

	@Column(name = "taux_taux_assurance")
	public BigDecimal getTauxTauxAssurance() {
		return tauxTauxAssurance;
	}

	public void setTauxTauxAssurance(BigDecimal tauxTauxAssurance) {
		this.tauxTauxAssurance = tauxTauxAssurance;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDossierRcd", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaDossierRcdActivite> getTaDevisRcProActivites() {
		return taDevisRcProActivites;
	}

	public void setTaDevisRcProActivites(List<TaDossierRcdActivite> taDevisRcProActivites) {
		this.taDevisRcProActivites = taDevisRcProActivites;
	}

	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTCivilite) {
		this.quiCree = quiCreeTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTCivilite) {
		this.quandCree = quandCreeTCivilite;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTCivilite) {
		this.quiModif = quiModifTCivilite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTCivilite) {
		this.quandModif = quandModifTCivilite;
	}

	@Column(name = "ip_acces", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	@Column(name = "nom_fichier")
	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	@Column(name = "taille")
	public Integer getTaille() {
		return taille;
	}

	public void setTaille(Integer taille) {
		this.taille = taille;
	}
	@Column(name = "type_mime")
	public String getTypeMime() {
		return typeMime;
	}

	public void setTypeMime(String typeMime) {
		this.typeMime = typeMime;
	}
	
//	@Column(name = "chiffre_affaire_exercice_anterieur")
//	public BigDecimal getChiffreAffaireExerciceAnterieur() {
//		return chiffreAffaireExerciceAnterieur;
//	}
//
//	public void setChiffreAffaireExerciceAnterieur(BigDecimal chiffreAffaireExerciceAnterieur) {
//		this.chiffreAffaireExerciceAnterieur = chiffreAffaireExerciceAnterieur;
//	}

	@Column(name = "chiffre_affaire_exercice_previsionnel")
	public BigDecimal getChiffreAffaireExercicePrevisionnel() {
		return chiffreAffaireExercicePrevisionnel;
	}

	public void setChiffreAffaireExercicePrevisionnel(BigDecimal chiffreAffaireExercicePrevisionnel) {
		this.chiffreAffaireExercicePrevisionnel = chiffreAffaireExercicePrevisionnel;
	}

	@Column(name = "effectif_total_exercice")
	public Integer getEffectifTotalExercice() {
		return effectifTotalExercice;
	}

	public void setEffectifTotalExercice(Integer effectifTotalExercice) {
		this.effectifTotalExercice = effectifTotalExercice;
	}

//	@Column(name = "effectif_total_exercice_anterieur")
//	public Integer getEffectifTotalExerciceAnterieur() {
//		return effectifTotalExerciceAnterieur;
//	}
//
//	public void setEffectifTotalExerciceAnterieur(Integer effectifTotalExerciceAnterieur) {
//		this.effectifTotalExerciceAnterieur = effectifTotalExerciceAnterieur;
//	}
	
//	@Column(name = "activite_principale")
//	public String getActivitePrincipale() {
//		return activitePrincipale;
//	}
//
//	public void setActivitePrincipale(String activitePrincipale) {
//		this.activitePrincipale = activitePrincipale;
//	}
	
	@Column(name = "reprise_passe")
	public Boolean getReprisePasse() {
		return reprisePasse;
	}

	public void setReprisePasse(Boolean reprisePasse) {
		this.reprisePasse = reprisePasse;
	}
	
	@Column(name = "intervention_chantier_cout_max")
	public Boolean getInterventionChantierCoutMax() {
		return interventionChantierCoutMax;
	}

	public void setInterventionChantierCoutMax(Boolean interventionchantierCoutMax) {
		this.interventionChantierCoutMax = interventionchantierCoutMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_resiliation_contrat_precedent")
	public Date getDateResiliationContratPrecedent() {
		return dateResiliationContratPrecedent;
	}

	public void setDateResiliationContratPrecedent(Date dateResiliationContratPrecedent) {
		this.dateResiliationContratPrecedent = dateResiliationContratPrecedent;
	}

	@Column(name = "motif_resiliation_contrat_precedent")
	public String getMotifResiliationContratPrecedent() {
		return motifResiliationContratPrecedent;
	}

	public void setMotifResiliationContratPrecedent(String motifResiliationContratPrecedent) {
		this.motifResiliationContratPrecedent = motifResiliationContratPrecedent;
	}

	@Column(name = "prime_nette")
	public BigDecimal getPrimeNette() {
		return primeNette;
	}

	public void setPrimeNette(BigDecimal primeNette) {
		this.primeNette = primeNette;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_devis")
	public Date getDateDevis() {
		return dateDevis;
	}

	public void setDateDevis(Date dateDevis) {
		this.dateDevis = dateDevis;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_echeance")
	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	@Column(name = "montant_premier_reglement")
	public BigDecimal getMontantPremierReglement() {
		return montantPremierReglement;
	}

	public void setMontantPremierReglement(BigDecimal montantPremierReglement) {
		this.montantPremierReglement = montantPremierReglement;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_debut_premiere_periode")
	public Date getDateDebutPremierePeriode() {
		return dateDebutPremierePeriode;
	}

	public void setDateDebutPremierePeriode(Date dateDebutPremierePeriode) {
		this.dateDebutPremierePeriode = dateDebutPremierePeriode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_fin_premiere_periode")
	public Date getDateFinPremierePeriode() {
		return dateFinPremierePeriode;
	}

	public void setDateFinPremierePeriode(Date dateFinPremierePeriode) {
		this.dateFinPremierePeriode = dateFinPremierePeriode;
	}

	@Column(name = "montant_taxes_diverses_assurance")
	public BigDecimal getMontantTaxesDiversesAssurance() {
		return montantTaxesDiversesAssurance;
	}

	public void setMontantTaxesDiversesAssurance(BigDecimal montantTaxesDiversesAssurance) {
		this.montantTaxesDiversesAssurance = montantTaxesDiversesAssurance;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_reglement_ta_t_reglement")
	public TaTReglement getTaTReglement() {
		return taTReglement;
	}

	public void setTaTReglement(TaTReglement taTReglement) {
		this.taTReglement = taTReglement;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_echeance_ta_t_echeance")
	public TaTEcheance getTaTEcheance() {
		return taTEcheance;
	}

	public void setTaTEcheance(TaTEcheance taTEcheance) {
		this.taTEcheance = taTEcheance;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_t_sous_traitance_ta_t_sous_traitance")
	public TaTSousTraitance getTaTSousTraitance() {
		return taTSousTraitance;
	}

	public void setTaTSousTraitance(TaTSousTraitance taTSousTraitance) {
		this.taTSousTraitance = taTSousTraitance;
	}
	
//	@Column(name = "num_devis")
//	public String getNumDevis() {
//		return numDevis;
//	}
//
//	public void setNumDevis(String numDevis) {
//		this.numDevis = numDevis;
//	}

	//@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "id_assure_ta_assure")
	public TaAssure getTaAssure() {
		return taAssure;
	}

	public void setTaAssure(TaAssure taAssure) {
		this.taAssure = taAssure;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDossierRcd", orphanRemoval=true)/*, orphanRemoval=true*/
	@Fetch(FetchMode.SUBSELECT)
	public List<TaSinistreAntecedent> getTaSinistreAntecedent() {
		return taSinistreAntecedent;
	}

	public void setTaSinistreAntecedent(List<TaSinistreAntecedent> taSinistreAntecedent) {
		this.taSinistreAntecedent = taSinistreAntecedent;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_courtier_ta_courtier")
	public TaCourtier getTaCourtier() {
		return taCourtier;
	}

	public void setTaCourtier(TaCourtier taCourtier) {
		this.taCourtier = taCourtier;
	}
	
	@Column(name = "resiliation")
	public Boolean getResiliation() {
		return resiliation;
	}

	public void setResiliation(Boolean resiliation) {
		this.resiliation = resiliation;
	}

	@Column(name = "date_resiliation_contrat")
	public Date getDateResiliationContrat() {
		return dateResiliationContrat;
	}

	public void setDateResiliationContrat(Date dateResiliationContrat) {
		this.dateResiliationContrat = dateResiliationContrat;
	}

	@Column(name = "motif_resiliation_contrat")
	public String getMotifResiliation() {
		return motifResiliation;
	}

	public void setMotifResiliation(String motifResiliation) {
		this.motifResiliation = motifResiliation;
	}

	@Column(name = "num_police")
	public String getNumDossierPolice() {
			return numDossierPolice;		
	}

	public void setNumDossierPolice(String numPolice) {
		this.numDossierPolice = numPolice;
	}

	@Column(name = "date_souscription")
	public Date getDateSouscription() {
		return dateSouscription;
	}

	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	@Column(name = "date_retractation")
	public Date getDateRetractation() {
		return dateRetractation;
	}

	public void setDateRetractation(Date dateRetractation) {
		this.dateRetractation = dateRetractation;
	}

	@Column(name = "retractation")
	public Boolean getRetractation() {
		return retractation;
	}

	public void setRetractation(Boolean retractation) {
		this.retractation = retractation;
	}

	@Column(name = "img_contrat_rc_pro")
	public byte[] getImgContratRcd() {
		return imgContratRcd;
	}

	public void setImgContratRcd(byte[] imgContratRcPro) {
		this.imgContratRcd = imgContratRcPro;
	}
	
	@Column(name = "nom_fichier_contrat")
	public String getNomFichierContrat() {
		return nomFichierContrat;
	}

	public void setNomFichierContrat(String nomFichierContrat) {
		this.nomFichierContrat = nomFichierContrat;
	}
	
	@Column(name = "taille_contrat")
	public Integer getTailleContrat() {
		return tailleContrat;
	}

	public void setTailleContrat(Integer tailleContrat) {
		this.tailleContrat = tailleContrat;
	}
	@Column(name = "type_mime_contrat")
	public String getTypeMimeContrat() {
		return typeMimeContrat;
	}

	public void setTypeMimeContrat(String typeMimeContrat) {
		this.typeMimeContrat = typeMimeContrat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assureurPrecedentRcd == null) ? 0 : assureurPrecedentRcd.hashCode());
//		result = prime * result + ((assureurPrecedentRcp == null) ? 0 : assureurPrecedentRcp.hashCode());
		result = prime * result + ((codeEcheance == null) ? 0 : codeEcheance.hashCode());
		result = prime * result + ((codeFranchise == null) ? 0 : codeFranchise.hashCode());
		result = prime * result + ((codeSousTraitance == null) ? 0 : codeSousTraitance.hashCode());
		result = prime * result + ((contratEnCours == null) ? 0 : contratEnCours.hashCode());
		result = prime * result + ((coutGlobalSinistreRcd == null) ? 0 : coutGlobalSinistreRcd.hashCode());
//		result = prime * result + ((coutGlobalSinistreRcp == null) ? 0 : coutGlobalSinistreRcp.hashCode());
		result = prime * result
				+ ((dateEffetContratPrecendentRcd == null) ? 0 : dateEffetContratPrecendentRcd.hashCode());
//		result = prime * result
//				+ ((dateEffetContratPrecendentRcp == null) ? 0 : dateEffetContratPrecendentRcp.hashCode());
		result = prime * result + ((dateEffet == null) ? 0 : dateEffet.hashCode());
		result = prime * result + ((dateResiliation == null) ? 0 : dateResiliation.hashCode());
		result = prime * result + ((dejaAssurer == null) ? 0 : dejaAssurer.hashCode());
//		result = prime * result + ((devisDetailOrigine == null) ? 0 : devisDetailOrigine.hashCode());
		result = prime * result + ((fraisRcPro == null) ? 0 : fraisRcPro.hashCode());
		result = prime * result + ((franchise == null) ? 0 : franchise.hashCode());
		result = prime * result + ((idDossierRcd == null) ? 0 : idDossierRcd.hashCode());
//		result = prime * result + Arrays.hashCode(imgDevisRcd);
		result = prime * result + ((interventionChantierCoutMax == null) ? 0 : interventionChantierCoutMax.hashCode());
//		result = prime * result + ((montantChantierPlusEleve == null) ? 0 : montantChantierPlusEleve.hashCode());
		result = prime * result + ((montantPrime == null) ? 0 : montantPrime.hashCode());
		result = prime * result + ((montantSinistre == null) ? 0 : montantSinistre.hashCode());
		result = prime * result + ((nbSinistre5ans == null) ? 0 : nbSinistre5ans.hashCode());
		result = prime * result + ((nbSinistreTotal == null) ? 0 : nbSinistreTotal.hashCode());
		result = prime * result + ((nomFichier == null) ? 0 : nomFichier.hashCode());
//		result = prime * result + ((parPourcentCaNeuf == null) ? 0 : parPourcentCaNeuf.hashCode());
//		result = prime * result
//				+ ((parPourcentCaPrisSousTraitance == null) ? 0 : parPourcentCaPrisSousTraitance.hashCode());
//		result = prime * result + ((parPourcentCaRenovation == null) ? 0 : parPourcentCaRenovation.hashCode());
//		result = prime * result + ((parPourcentCaSousTraitance == null) ? 0 : parPourcentCaSousTraitance.hashCode());
//		result = prime * result + ((parPourcentClientEntreprise == null) ? 0 : parPourcentClientEntreprise.hashCode());
//		result = prime * result
//				+ ((parPourcentClientParticulier == null) ? 0 : parPourcentClientParticulier.hashCode());
		result = prime * result + ((policeContratPrecedentRcd == null) ? 0 : policeContratPrecedentRcd.hashCode());
//		result = prime * result + ((policeContratPrecedentRcp == null) ? 0 : policeContratPrecedentRcp.hashCode());
		result = prime * result + ((pourcentSoustraitance == null) ? 0 : pourcentSoustraitance.hashCode());
		result = prime * result + ((resilieFausseDeclaration == null) ? 0 : resilieFausseDeclaration.hashCode());
		result = prime * result + ((resilieNonPaiement == null) ? 0 : resilieNonPaiement.hashCode());
		result = prime * result + ((taDevisRcProActivites == null) ? 0 : taDevisRcProActivites.hashCode());
		result = prime * result + ((taTauxAssurance == null) ? 0 : taTauxAssurance.hashCode());
		result = prime * result + ((taille == null) ? 0 : taille.hashCode());
		result = prime * result + ((tauxCommission == null) ? 0 : tauxCommission.hashCode());
		result = prime * result + ((tauxTauxAssurance == null) ? 0 : tauxTauxAssurance.hashCode());
		result = prime * result + ((typeMime == null) ? 0 : typeMime.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaDossierRcd other = (TaDossierRcd) obj;
		if (assureurPrecedentRcd == null) {
			if (other.assureurPrecedentRcd != null)
				return false;
		} else if (!assureurPrecedentRcd.equals(other.assureurPrecedentRcd))
			return false;
//		if (assureurPrecedentRcp == null) {
//			if (other.assureurPrecedentRcp != null)
//				return false;
//		} else if (!assureurPrecedentRcp.equals(other.assureurPrecedentRcp))
//			return false;
		if (codeEcheance == null) {
			if (other.codeEcheance != null)
				return false;
		} else if (!codeEcheance.equals(other.codeEcheance))
			return false;
		if (codeFranchise == null) {
			if (other.codeFranchise != null)
				return false;
		} else if (!codeFranchise.equals(other.codeFranchise))
			return false;
		if (codeSousTraitance == null) {
			if (other.codeSousTraitance != null)
				return false;
		} else if (!codeSousTraitance.equals(other.codeSousTraitance))
			return false;
		if (contratEnCours == null) {
			if (other.contratEnCours != null)
				return false;
		} else if (!contratEnCours.equals(other.contratEnCours))
			return false;
		if (coutGlobalSinistreRcd == null) {
			if (other.coutGlobalSinistreRcd != null)
				return false;
		} else if (!coutGlobalSinistreRcd.equals(other.coutGlobalSinistreRcd))
			return false;
//		if (coutGlobalSinistreRcp == null) {
//			if (other.coutGlobalSinistreRcp != null)
//				return false;
//		} else if (!coutGlobalSinistreRcp.equals(other.coutGlobalSinistreRcp))
//			return false;
		if (dateEffetContratPrecendentRcd == null) {
			if (other.dateEffetContratPrecendentRcd != null)
				return false;
		} else if (!dateEffetContratPrecendentRcd.equals(other.dateEffetContratPrecendentRcd))
			return false;
//		if (dateEffetContratPrecendentRcp == null) {
//			if (other.dateEffetContratPrecendentRcp != null)
//				return false;
//		} else if (!dateEffetContratPrecendentRcp.equals(other.dateEffetContratPrecendentRcp))
//			return false;
		if (dateEffet == null) {
			if (other.dateEffet != null)
				return false;
		} else if (!dateEffet.equals(other.dateEffet))
			return false;
		if (dateResiliation == null) {
			if (other.dateResiliation != null)
				return false;
		} else if (!dateResiliation.equals(other.dateResiliation))
			return false;
		if (dejaAssurer == null) {
			if (other.dejaAssurer != null)
				return false;
		} else if (!dejaAssurer.equals(other.dejaAssurer))
			return false;
//		if (devisDetailOrigine == null) {
//			if (other.devisDetailOrigine != null)
//				return false;
//		} else if (!devisDetailOrigine.equals(other.devisDetailOrigine))
//			return false;
		if (fraisRcPro == null) {
			if (other.fraisRcPro != null)
				return false;
		} else if (!fraisRcPro.equals(other.fraisRcPro))
			return false;
		if (franchise == null) {
			if (other.franchise != null)
				return false;
		} else if (!franchise.equals(other.franchise))
			return false;
		if (idDossierRcd == null) {
			if (other.idDossierRcd != null)
				return false;
		} else if (!idDossierRcd.equals(other.idDossierRcd))
			return false;
//		if (!Arrays.equals(imgDevisRcd, other.imgDevisRcd))
//			return false;
		if (interventionChantierCoutMax == null) {
			if (other.interventionChantierCoutMax != null)
				return false;
		} else if (!interventionChantierCoutMax.equals(other.interventionChantierCoutMax))
			return false;
//		if (montantChantierPlusEleve == null) {
//			if (other.montantChantierPlusEleve != null)
//				return false;
//		} else if (!montantChantierPlusEleve.equals(other.montantChantierPlusEleve))
//			return false;
		if (montantPrime == null) {
			if (other.montantPrime != null)
				return false;
		} else if (!montantPrime.equals(other.montantPrime))
			return false;
		if (montantSinistre == null) {
			if (other.montantSinistre != null)
				return false;
		} else if (!montantSinistre.equals(other.montantSinistre))
			return false;
		if (nbSinistre5ans == null) {
			if (other.nbSinistre5ans != null)
				return false;
		} else if (!nbSinistre5ans.equals(other.nbSinistre5ans))
			return false;
		if (nbSinistreTotal == null) {
			if (other.nbSinistreTotal != null)
				return false;
		} else if (!nbSinistreTotal.equals(other.nbSinistreTotal))
			return false;
		if (nomFichier == null) {
			if (other.nomFichier != null)
				return false;
		} else if (!nomFichier.equals(other.nomFichier))
			return false;
//		if (parPourcentCaNeuf == null) {
//			if (other.parPourcentCaNeuf != null)
//				return false;
//		} else if (!parPourcentCaNeuf.equals(other.parPourcentCaNeuf))
//			return false;
//		if (parPourcentCaPrisSousTraitance == null) {
//			if (other.parPourcentCaPrisSousTraitance != null)
//				return false;
//		} else if (!parPourcentCaPrisSousTraitance.equals(other.parPourcentCaPrisSousTraitance))
//			return false;
//		if (parPourcentCaRenovation == null) {
//			if (other.parPourcentCaRenovation != null)
//				return false;
//		} else if (!parPourcentCaRenovation.equals(other.parPourcentCaRenovation))
//			return false;
//		if (parPourcentCaSousTraitance == null) {
//			if (other.parPourcentCaSousTraitance != null)
//				return false;
//		} else if (!parPourcentCaSousTraitance.equals(other.parPourcentCaSousTraitance))
//			return false;
//		if (parPourcentClientEntreprise == null) {
//			if (other.parPourcentClientEntreprise != null)
//				return false;
//		} else if (!parPourcentClientEntreprise.equals(other.parPourcentClientEntreprise))
//			return false;
//		if (parPourcentClientParticulier == null) {
//			if (other.parPourcentClientParticulier != null)
//				return false;
//		} else if (!parPourcentClientParticulier.equals(other.parPourcentClientParticulier))
//			return false;
		if (policeContratPrecedentRcd == null) {
			if (other.policeContratPrecedentRcd != null)
				return false;
		} else if (!policeContratPrecedentRcd.equals(other.policeContratPrecedentRcd))
			return false;
//		if (policeContratPrecedentRcp == null) {
//			if (other.policeContratPrecedentRcp != null)
//				return false;
//		} else if (!policeContratPrecedentRcp.equals(other.policeContratPrecedentRcp))
//			return false;
		if (pourcentSoustraitance == null) {
			if (other.pourcentSoustraitance != null)
				return false;
		} else if (!pourcentSoustraitance.equals(other.pourcentSoustraitance))
			return false;
		if (resilieFausseDeclaration == null) {
			if (other.resilieFausseDeclaration != null)
				return false;
		} else if (!resilieFausseDeclaration.equals(other.resilieFausseDeclaration))
			return false;
		if (resilieNonPaiement == null) {
			if (other.resilieNonPaiement != null)
				return false;
		} else if (!resilieNonPaiement.equals(other.resilieNonPaiement))
			return false;
		if (taDevisRcProActivites == null) {
			if (other.taDevisRcProActivites != null)
				return false;
		} else if (!taDevisRcProActivites.equals(other.taDevisRcProActivites))
			return false;
		if (taTauxAssurance == null) {
			if (other.taTauxAssurance != null)
				return false;
		} else if (!taTauxAssurance.equals(other.taTauxAssurance))
			return false;
		if (taille == null) {
			if (other.taille != null)
				return false;
		} else if (!taille.equals(other.taille))
			return false;
		if (tauxCommission == null) {
			if (other.tauxCommission != null)
				return false;
		} else if (!tauxCommission.equals(other.tauxCommission))
			return false;
		if (tauxTauxAssurance == null) {
			if (other.tauxTauxAssurance != null)
				return false;
		} else if (!tauxTauxAssurance.equals(other.tauxTauxAssurance))
			return false;
		if (typeMime == null) {
			if (other.typeMime != null)
				return false;
		} else if (!typeMime.equals(other.typeMime))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDossierRcd", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaDossierRcdTauxPib> getTaDevisRcProTauxPibs() {
		return taDevisRcProTauxPibs;
	}

	public void setTaDevisRcProTauxPibs(List<TaDossierRcdTauxPib> taDevisRcProTauxPibs) {
		this.taDevisRcProTauxPibs = taDevisRcProTauxPibs;
	}
	
	

	@Column(name = "contrat")
	public Boolean getContrat() {
		return contrat;
	}

	public void setContrat(Boolean contrat) {
		this.contrat = contrat;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contrat_origine")
	public TaDossierRcd getContratOrigine() {
		return contratOrigine;
	}

	public void setContratOrigine(TaDossierRcd contratOrigine) {
		this.contratOrigine = contratOrigine;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contrat_parent_direct")
	public TaDossierRcd getContratParentDirect() {
		return contratParentDirect;
	}

	public void setContratParentDirect(TaDossierRcd contratParentDirect) {
		this.contratParentDirect = contratParentDirect;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDossierRcd", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaEcheance> getTaEcheances() {
		return taEcheances;
	}

	public void setTaEcheances(List<TaEcheance> taEcheances) {
		this.taEcheances = taEcheances;
	}

	@Column(name = "numero_avenant")
	public Integer getNumeroAvenant() {
		return numeroAvenant;
	}

	public void setNumeroAvenant(Integer numeroAvenant) {
		this.numeroAvenant = numeroAvenant;
	}

	@Column(name = "montant_commission_courtier")
	public BigDecimal getMontantCommissionCourtier() {
		return montantCommissionCourtier;
	}

	public void setMontantCommissionCourtier(BigDecimal montant_commission_courtier) {
		this.montantCommissionCourtier = montant_commission_courtier;
	}

	@Column(name = "taux_sur_commission_courtier")
	public BigDecimal getTauxSurCommissionCourtier() {
		return tauxSurCommissionCourtier;
	}

	public void setTauxSurCommissionCourtier(BigDecimal taux_sur_commission_courtier) {
		this.tauxSurCommissionCourtier = taux_sur_commission_courtier;
	}

	@Column(name = "montant_sur_commission_courtier")
	public BigDecimal getMontantSurCommissionCourtier() {
		return montantSurCommissionCourtier;
	}

	public void setMontantSurCommissionCourtier(BigDecimal montant_sur_commission_courtier) {
		this.montantSurCommissionCourtier = montant_sur_commission_courtier;
	}

	@Column(name = "montant_protection_juridique")
	public BigDecimal getMontantProtectionJuridique() {
		return montantProtectionJuridique;
	}

	public void setMontantProtectionJuridique(BigDecimal montant_protection_juridique) {
		this.montantProtectionJuridique = montant_protection_juridique;
	}

	@Column(name = "montant_taxe_fiscale")
	public BigDecimal getMontantTaxeFiscale() {
		return montantTaxeFiscale;
	}

	public void setMontantTaxeFiscale(BigDecimal montant_taxe_fiscale) {
		this.montantTaxeFiscale = montant_taxe_fiscale;
	}

	@Column(name = "taux_taxe_fiscale")
	public BigDecimal getTauxTaxeFiscale() {
		return tauxTaxeFiscale;
	}

	public void setTauxTaxeFiscale(BigDecimal taux_taxe_fiscale) {
		this.tauxTaxeFiscale = taux_taxe_fiscale;
	}

	@Column(name = "montant_frais_de_gestion")
	public BigDecimal getMontantFraisDeGestion() {
		return montantFraisDeGestion;
	}

	public void setMontantFraisDeGestion(BigDecimal montant_frais_de_gestion) {
		this.montantFraisDeGestion = montant_frais_de_gestion;
	}

	@Column(name = "prime_annuelle")
	public BigDecimal getPrimeAnnuelle() {
		return primeAnnuelle;
	}

	public void setPrimeAnnuelle(BigDecimal prime_annuelle) {
		this.primeAnnuelle = prime_annuelle;
	}

	@Column(name = "prime_annuelle_complete")
	public BigDecimal getPrimeAnnuelleComplete() {
		return primeAnnuelleComplete;
	}

	public void setPrimeAnnuelleComplete(BigDecimal prime_annuelle_complete) {
		this.primeAnnuelleComplete = prime_annuelle_complete;
	}

	@Column(name = "montant_frais_fractionnement")
	public BigDecimal getMontantFraisFractionnement() {
		return montantFraisFractionnement;
	}

	public void setMontantFraisFractionnement(BigDecimal montant_frais_fractionnement) {
		this.montantFraisFractionnement = montant_frais_fractionnement;
	}

	@Column(name = "log_detail_calcul_prime")
	public String getLogDetailCalculPrime() {
		return logDetailCalculPrime;
	}

	public void setLogDetailCalculPrime(String log_detail_calcul_prime) {
		this.logDetailCalculPrime = log_detail_calcul_prime;
	}

	@Column(name = "log_detail_calcul_premier_reglement")
	public String getLogDetailCalculPremierReglement() {
		return logDetailCalculPremierReglement;
	}

	public void setLogDetailCalculPremierReglement(String log_detail_calcul_premier_reglement) {
		this.logDetailCalculPremierReglement = log_detail_calcul_premier_reglement;
	}

	@Column(name = "montant_commission_courtier_une_echeance")
	public BigDecimal getMontantCommissionCourtierUneEcheance() {
		return montantCommissionCourtierUneEcheance;
	}

	public void setMontantCommissionCourtierUneEcheance(BigDecimal montantCommissionCourtierUneEcheance) {
		this.montantCommissionCourtierUneEcheance = montantCommissionCourtierUneEcheance;
	}

	@Column(name = "experience_activite_3ans")
	public Boolean getExperienceActivite3ans() {
		return experienceActivite3ans;
	}

	public void setExperienceActivite3ans(Boolean experience_activite_3ans) {
		this.experienceActivite3ans = experience_activite_3ans;
	}

	@Column(name = "experience_activte_5ans")
	public Boolean getExperienceActivte5ans() {
		return experienceActivte5ans;
	}

	public void setExperienceActivte5ans(Boolean experience_activte_5ans) {
		this.experienceActivte5ans = experience_activte_5ans;
	}

	@Column(name = "exerce_activite_nomenclature")
	public Boolean getExerceActiviteNomenclature() {
		return exerceActiviteNomenclature;
	}

	public void setExerceActiviteNomenclature(Boolean exerce_activite_nomenclature) {
		this.exerceActiviteNomenclature = exerce_activite_nomenclature;
	}

	@Column(name = "cout_ouvrage_inferieur_15k")
	public Boolean getCoutOuvrageInferieur15k() {
		return coutOuvrageInferieur15k;
	}

	public void setCoutOuvrageInferieur15k(Boolean cout_ouvrage_inferieur_15k) {
		this.coutOuvrageInferieur15k = cout_ouvrage_inferieur_15k;
	}

	@Column(name = "montant_travaux_ht_max_50k")
	public Boolean getMontantTravauxHtMax50k() {
		return montantTravauxHtMax50k;
	}

	public void setMontantTravauxHtMax50k(Boolean montant_travaux_ht_max_50k) {
		this.montantTravauxHtMax50k = montant_travaux_ht_max_50k;
	}

	@Column(name = "montant_travaux_ht_max_100k")
	public Boolean getMontantTravauxHtMax100k() {
		return montantTravauxHtMax100k;
	}

	public void setMontantTravauxHtMax100k(Boolean montant_travaux_ht_max_100k) {
		this.montantTravauxHtMax100k = montant_travaux_ht_max_100k;
	}

	@Column(name = "montant_travaux_ht_max_250k")
	public Boolean getMontantTravauxHtMax250k() {
		return montantTravauxHtMax250k;
	}

	public void setMontantTravauxHtMax250k(Boolean montant_travaux_ht_max_250k) {
		this.montantTravauxHtMax250k = montant_travaux_ht_max_250k;
	}

	@Column(name = "montant_travaux_ht_max_500k")
	public Boolean getMontantTravauxHtMax500k() {
		return montantTravauxHtMax500k;
	}

	public void setMontantTravauxHtMax500k(Boolean montant_travaux_ht_max_500k) {
		this.montantTravauxHtMax500k = montant_travaux_ht_max_500k;
	}

	@Column(name = "montant_travaux_ht_max_1m")
	public Boolean getMontantTravauxHtMax1m() {
		return montantTravauxHtMax1m;
	}

	public void setMontantTravauxHtMax1m(Boolean montant_travaux_ht_max_1m) {
		this.montantTravauxHtMax1m = montant_travaux_ht_max_1m;
	}

	@Column(name = "intervention_constructeur_maison_indiv")
	public Boolean getInterventionConstructeurMaisonIndiv() {
		return interventionConstructeurMaisonIndiv;
	}

	public void setInterventionConstructeurMaisonIndiv(Boolean intervention_constructeur_maison_indiv) {
		this.interventionConstructeurMaisonIndiv = intervention_constructeur_maison_indiv;
	}

	@Column(name = "intervention_contractant_general")
	public Boolean getInterventionContractantGeneral() {
		return interventionContractantGeneral;
	}

	public void setInterventionContractantGeneral(Boolean intervention_contractant_general) {
		this.interventionContractantGeneral = intervention_contractant_general;
	}

	@Column(name = "intervention_batiment")
	public Boolean getInterventionBatiment() {
		return interventionBatiment;
	}

	public void setInterventionBatiment(Boolean intervention_batiment) {
		this.interventionBatiment = intervention_batiment;
	}

	@Column(name = "intervention_maitre_oeuvre")
	public Boolean getInterventionMaitreOeuvre() {
		return interventionMaitreOeuvre;
	}

	public void setInterventionMaitreOeuvre(Boolean intervention_maitre_oeuvre) {
		this.interventionMaitreOeuvre = intervention_maitre_oeuvre;
	}

	@Column(name = "intervention_immobilier")
	public Boolean getInterventionImmobilier() {
		return interventionImmobilier;
	}

	public void setInterventionImmobilier(Boolean intervention_immobilier) {
		this.interventionImmobilier = intervention_immobilier;
	}

	@Column(name = "intervention_fabricant_mat_construction")
	public Boolean getInterventionFabricantMatConstruction() {
		return interventionFabricantMatConstruction;
	}

	public void setInterventionFabricantMatConstruction(Boolean intervention_fabricant_mat_construction) {
		this.interventionFabricantMatConstruction = intervention_fabricant_mat_construction;
	}

	@Column(name = "activite_princ_negoce_fabr_mat_construction_non_pose")
	public Boolean getActivitePrincNegoceFabrMatConstructionNonPose() {
		return activitePrincNegoceFabrMatConstructionNonPose;
	}

	public void setActivitePrincNegoceFabrMatConstructionNonPose(
			Boolean activite_princ_negoce_fabr_mat_construction_non_pose) {
		this.activitePrincNegoceFabrMatConstructionNonPose = activite_princ_negoce_fabr_mat_construction_non_pose;
	}

	@Column(name = "travaux_technique_non_courant")
	public Boolean getTravauxTechniqueNonCourant() {
		return travauxTechniqueNonCourant;
	}

	public void setTravauxTechniqueNonCourant(Boolean travaux_technique_non_courant) {
		this.travauxTechniqueNonCourant = travaux_technique_non_courant;
	}

	@Column(name = "intervention_monument_historique")
	public Boolean getInterventionMonumentHistorique() {
		return interventionMonumentHistorique;
	}

	public void setInterventionMonumentHistorique(Boolean intervention_monument_historique) {
		this.interventionMonumentHistorique = intervention_monument_historique;
	}

	@Column(name = "avis_technique")
	public Boolean getAvisTechnique() {
		return avisTechnique;
	}

	public void setAvisTechnique(Boolean avis_technique) {
		this.avisTechnique = avis_technique;
	}

	@Column(name = "document_unique")
	public Boolean getDocumentUnique() {
		return documentUnique;
	}

	public void setDocumentUnique(Boolean document_unique) {
		this.documentUnique = document_unique;
	}

	@Column(name = "respect_regles")
	public Boolean getRespectRegles() {
		return respectRegles;
	}

	public void setRespectRegles(Boolean respect_regles) {
		this.respectRegles = respect_regles;
	}

	@Column(name = "respect_disposition_sous_traitance")
	public Boolean getRespectDispositionSousTraitance() {
		return respectDispositionSousTraitance;
	}

	public void setRespectDispositionSousTraitance(Boolean respect_disposition_sous_traitance) {
		this.respectDispositionSousTraitance = respect_disposition_sous_traitance;
	}
	
	@Column(name = "annees_experience_activite")
	public Integer getAnneesExperienceActivite() {
		return anneesExperienceActivite;
	}

	public void setAnneesExperienceActivite(Integer annees_experience_activite) {
		this.anneesExperienceActivite = annees_experience_activite;
	}
	
	@Column(name = "effectif_sur_chantier")
	public Integer getEffectifSurChantier() {
		return effectifSurChantier;
	}

	public void setEffectifSurChantier(Integer effectif_sur_chantier) {
		this.effectifSurChantier = effectif_sur_chantier;
	}
	
	@Column(name = "effectif_apprentis")
	public Integer getEffectifApprentis() {
		return effectifApprentis;
	}

	public void setEffectifApprentis(Integer effectif_apprentis) {
		this.effectifApprentis = effectif_apprentis;
	}
	
	@Column(name = "effectif_commerciaux_administratifs")
	public Integer getEffectifCommerciauxAdministratifs() {
		return effectifCommerciauxAdministratifs;
	}

	public void setEffectifCommerciauxAdministratifs(Integer effectif_commerciaux_administratifs) {
		this.effectifCommerciauxAdministratifs = effectif_commerciaux_administratifs;
	}
	
	@Column(name = "effectif_encadrement")
	public Integer getEffectifEncadrement() {
		return effectifEncadrement;
	}

	public void setEffectifEncadrement(Integer effectif_encadrement) {
		this.effectifEncadrement = effectif_encadrement;
	}
	
	@Column(name = "qualibat")
	public Boolean getQualibat() {
		return qualibat;
	}

	public void setQualibat(Boolean qualibat) {
		this.qualibat = qualibat;
	}
	
	@Column(name = "pourcent_ca_sous_trait_dernier_exercice")
	public BigDecimal getPourcentCaSousTraitDernierExercice() {
		return pourcentCaSousTraitDernierExercice;
	}

	public void setPourcentCaSousTraitDernierExercice(BigDecimal pourcent_ca_sous_trait_dernier_exercice) {
		this.pourcentCaSousTraitDernierExercice = pourcent_ca_sous_trait_dernier_exercice;
	}
	
	@Column(name = "pourcent_ca_sous_trait_exercice_n_moins1")
	public BigDecimal getPourcentCaSousTraitExerciceNMoins1() {
		return pourcentCaSousTraitExerciceNMoins1;
	}

	public void setPourcentCaSousTraitExerciceNMoins1(BigDecimal pourcent_ca_sous_trait_exercice_n_moins1) {
		this.pourcentCaSousTraitExerciceNMoins1 = pourcent_ca_sous_trait_exercice_n_moins1;
	}
	
	@Column(name = "pourcent_ca_sous_trait_exercice_n_moins2")
	public BigDecimal getPourcentCaSousTraitExerciceNMoins2() {
		return pourcentCaSousTraitExerciceNMoins2;
	}

	public void setPourcentCaSousTraitExerciceNMoins2(BigDecimal pourcent_ca_sous_trait_exercice_n_moins2) {
		this.pourcentCaSousTraitExerciceNMoins2 = pourcent_ca_sous_trait_exercice_n_moins2;
	}
	
	@Column(name = "ca_total_ht_exercice_n_moins1")
	public BigDecimal getCaTotalHtExerciceNMoins1() {
		return caTotalHtExerciceNMoins1;
	}

	public void setCaTotalHtExerciceNMoins1(BigDecimal ca_total_ht_exercice_n_moins1) {
		this.caTotalHtExerciceNMoins1 = ca_total_ht_exercice_n_moins1;
	}
	
	@Column(name = "ca_total_ht_exercice_n_moins2")
	public BigDecimal getCaTotalHtExerciceNMoins2() {
		return caTotalHtExerciceNMoins2;
	}

	public void setCaTotalHtExerciceNMoins2(BigDecimal ca_total_ht_exercice_n_moins2) {
		this.caTotalHtExerciceNMoins2 = ca_total_ht_exercice_n_moins2;
	}
	
	@Column(name = "anciennete_moins_12mois")
	public Boolean getAncienneteMoins12mois() {
		return ancienneteMoins12mois;
	}

	public void setAncienneteMoins12mois(Boolean anciennete_moins_12mois) {
		this.ancienneteMoins12mois = anciennete_moins_12mois;
	}
	
	@Column(name = "anciennete_12_a_36mois")
	public Boolean getAnciennete12a36mois() {
		return anciennete12a36mois;
	}

	public void setAnciennete12a36mois(Boolean anciennete_12_a_36mois) {
		this.anciennete12a36mois = anciennete_12_a_36mois;
	}
	
	@Column(name = "anciennete_plus_36mois")
	public Boolean getAnciennetePlus36mois() {
		return anciennetePlus36mois;
	}

	public void setAnciennetePlus36mois(Boolean anciennete_plus_36mois) {
		this.anciennetePlus36mois = anciennete_plus_36mois;
	}
	
	@Column(name = "assure_meme_risque")
	public Boolean getAssureMemeRisque() {
		return assureMemeRisque;
	}

	public void setAssureMemeRisque(Boolean assure_meme_risque) {
		this.assureMemeRisque = assure_meme_risque;
	}
	
	@Column(name = "assure_meme_risque_rcd")
	public Boolean getAssureMemeRisqueRcd() {
		return assureMemeRisqueRcd;
	}

	public void setAssureMemeRisqueRcd(Boolean assure_meme_risque_rcd) {
		this.assureMemeRisqueRcd = assure_meme_risque_rcd;
	}
	
	@Column(name = "assure_meme_risque_rce")
	public Boolean getAssureMemeRisqueRce() {
		return assureMemeRisqueRce;
	}

	public void setAssureMemeRisqueRce(Boolean assure_meme_risque_rce) {
		this.assureMemeRisqueRce = assure_meme_risque_rce;
	}
	
	@Column(name = "antecedent_rcd_contrat_en_cours")
	public Boolean getAntecedentRcdContratEnCours() {
		return antecedentRcdContratEnCours;
	}

	public void setAntecedentRcdContratEnCours(Boolean antecedent_rcd_contrat_en_cours) {
		this.antecedentRcdContratEnCours = antecedent_rcd_contrat_en_cours;
	}
	
	@Column(name = "antecedent_rcd_contrat_resilie")
	public Boolean getAntecedentRcdContratResilie() {
		return antecedentRcdContratResilie;
	}

	public void setAntecedentRcdContratResilie(Boolean antecedent_rcd_contrat_resilie) {
		this.antecedentRcdContratResilie = antecedent_rcd_contrat_resilie;
	}
	
	@Column(name = "antecedent_rce_contrat_en_cours")
	public Boolean getAntecedentRceContratEnCours() {
		return antecedentRceContratEnCours;
	}

	public void setAntecedentRceContratEnCours(Boolean antecedent_rce_contrat_en_cours) {
		this.antecedentRceContratEnCours = antecedent_rce_contrat_en_cours;
	}
	
	@Column(name = "antecedent_rce_contrat_resilie")
	public Boolean getAntecedentRceContratResilie() {
		return antecedentRceContratResilie;
	}

	public void setAntecedentRceContratResilie(Boolean antecedent_rce_contrat_resilie) {
		this.antecedentRceContratResilie = antecedent_rce_contrat_resilie;
	}
	
	@Column(name = "antecedent_rcd_date_resiliation")
	public Date getAntecedentRcdDateResiliation() {
		return antecedentRcdDateResiliation;
	}

	public void setAntecedentRcdDateResiliation(Date antecedent_rcd_date_resiliation) {
		this.antecedentRcdDateResiliation = antecedent_rcd_date_resiliation;
	}
	
	@Column(name = "antecedent_rce_date_resiliation")
	public Date getAntecedentRceDateResiliation() {
		return antecedentRceDateResiliation;
	}

	public void setAntecedentRceDateResiliation(Date antecedent_rce_date_resiliation) {
		this.antecedentRceDateResiliation = antecedent_rce_date_resiliation;
	}
	
	@Column(name = "antecedent_rcd_resilie_assure")
	public Boolean getAntecedentRcdResilieAssure() {
		return antecedentRcdResilieAssure;
	}

	public void setAntecedentRcdResilieAssure(Boolean antecedent_rcd_resilie_assure) {
		this.antecedentRcdResilieAssure = antecedent_rcd_resilie_assure;
	}
	
	@Column(name = "antecedent_rce_resilie_assure")
	public Boolean getAntecedentRceResilieAssure() {
		return antecedentRceResilieAssure;
	}

	public void setAntecedentRceResilieAssure(Boolean antecedent_rce_resilie_assure) {
		this.antecedentRceResilieAssure = antecedent_rce_resilie_assure;
	}
	
	@Column(name = "antecedent_rcd_resilie_echeance")
	public Boolean getAntecedentRcdResilieEcheance() {
		return antecedentRcdResilieEcheance;
	}

	public void setAntecedentRcdResilieEcheance(Boolean antecedent_rcd_resilie_echeance) {
		this.antecedentRcdResilieEcheance = antecedent_rcd_resilie_echeance;
	}
	
	@Column(name = "antecedent_rce_resilie_echeance")
	public Boolean getAntecedentRceResilieEcheance() {
		return antecedentRceResilieEcheance;
	}

	public void setAntecedentRceResilieEcheance(Boolean antecedent_rce_resilie_echeance) {
		this.antecedentRceResilieEcheance = antecedent_rce_resilie_echeance;
	}
	
	@Column(name = "antecedent_rcd_hausse_tarif")
	public Boolean getAntecedentRcdHausseTarif() {
		return antecedentRcdHausseTarif;
	}

	public void setAntecedentRcdHausseTarif(Boolean antecedent_rcd_hausse_tarif) {
		this.antecedentRcdHausseTarif = antecedent_rcd_hausse_tarif;
	}
	
	@Column(name = "antecedent_rce_hausse_tarif")
	public Boolean getAntecedentRceHausseTarif() {
		return antecedentRceHausseTarif;
	}

	public void setAntecedentRceHausseTarif(Boolean antecedent_rce_hausse_tarif) {
		this.antecedentRceHausseTarif = antecedent_rce_hausse_tarif;
	}
	
	@Column(name = "antecedent_rcd_changement_activite")
	public Boolean getAntecedentRcdChangementActivite() {
		return antecedentRcdChangementActivite;
	}

	public void setAntecedentRcdChangementActivite(Boolean antecedent_rcd_changement_activite) {
		this.antecedentRcdChangementActivite = antecedent_rcd_changement_activite;
	}
	
	@Column(name = "antecedent_rce_changement_activite")
	public Boolean getAntecedentRceChangementActivite() {
		return antecedentRceChangementActivite;
	}

	public void setAntecedentRceChangementActivite(Boolean antecedent_rce_changement_activite) {
		this.antecedentRceChangementActivite = antecedent_rce_changement_activite;
	}
	
	@Column(name = "antecedent_rcd_resilie_amiable")
	public Boolean getAntecedentRcdResilieAmiable() {
		return antecedentRcdResilieAmiable;
	}

	public void setAntecedentRcdResilieAmiable(Boolean antecedent_rcd_resilie_amiable) {
		this.antecedentRcdResilieAmiable = antecedent_rcd_resilie_amiable;
	}
	
	@Column(name = "antecedent_rce_resilie_amiable")
	public Boolean getAntecedentRceResilieAmiable() {
		return antecedentRceResilieAmiable;
	}

	public void setAntecedentRceResilieAmiable(Boolean antecedent_rce_resilie_amiable) {
		this.antecedentRceResilieAmiable = antecedent_rce_resilie_amiable;
	}
	
	@Column(name = "antecedent_rcd_assureur")
	public Boolean getAntecedentRcdAssureur() {
		return antecedentRcdAssureur;
	}

	public void setAntecedentRcdAssureur(Boolean antecedent_rcd_assureur) {
		this.antecedentRcdAssureur = antecedent_rcd_assureur;
	}
	
	@Column(name = "antecedent_rce_assureur")
	public Boolean getAntecedentRceAssureur() {
		return antecedentRceAssureur;
	}

	public void setAntecedentRceAssureur(Boolean antecedent_rce_assureur) {
		this.antecedentRceAssureur = antecedent_rce_assureur;
	}
	
	@Column(name = "antecedent_rcd_non_paiement_prime")
	public Boolean getAntecedentRcdNonPaiementPrime() {
		return antecedentRcdNonPaiementPrime;
	}

	public void setAntecedentRcdNonPaiementPrime(Boolean antecedent_rcd_non_paiement_prime) {
		this.antecedentRcdNonPaiementPrime = antecedent_rcd_non_paiement_prime;
	}
	
	@Column(name = "antecedent_rce_non_paiement_prime")
	public Boolean getAntecedentRceNonPaiementPrime() {
		return antecedentRceNonPaiementPrime;
	}

	public void setAntecedentRceNonPaiementPrime(Boolean antecedent_rce_non_paiement_prime) {
		this.antecedentRceNonPaiementPrime = antecedent_rce_non_paiement_prime;
	}
	
	@Column(name = "antecedent_rcd_sinistre")
	public Boolean getAntecedentRcdSinistre() {
		return antecedentRcdSinistre;
	}

	public void setAntecedentRcdSinistre(Boolean antecedent_rcd_sinistre) {
		this.antecedentRcdSinistre = antecedent_rcd_sinistre;
	}
	
	@Column(name = "antecedent_rce_sinistre")
	public Boolean getAntecedentRceSinistre() {
		return antecedentRceSinistre;
	}

	public void setAntecedentRceSinistre(Boolean antecedent_rce_sinistre) {
		this.antecedentRceSinistre = antecedent_rce_sinistre;
	}
	
	@Column(name = "antecedent_rcd_modif_activite")
	public Boolean getAntecedentRcdModifActivite() {
		return antecedentRcdModifActivite;
	}

	public void setAntecedentRcdModifActivite(Boolean antecedent_rcd_modif_activite) {
		this.antecedentRcdModifActivite = antecedent_rcd_modif_activite;
	}
	
	@Column(name = "antecedent_rce_modif_activite")
	public Boolean getAntecedentRceModifActivite() {
		return antecedentRceModifActivite;
	}

	public void setAntecedentRceModifActivite(Boolean antecedent_rce_modif_activite) {
		this.antecedentRceModifActivite = antecedent_rce_modif_activite;
	}
	
	@Column(name = "antecedent_rcd_autre")
	public Boolean getAntecedentRcdAutre() {
		return antecedentRcdAutre;
	}

	public void setAntecedentRcdAutre(Boolean antecedent_rcd_autre) {
		this.antecedentRcdAutre = antecedent_rcd_autre;
	}
	
	@Column(name = "antecedent_rce_autre")
	public Boolean getAntecedentRceAutre() {
		return antecedentRceAutre;
	}

	public void setAntecedentRceAutre(Boolean antecedent_rce_autre) {
		this.antecedentRceAutre = antecedent_rce_autre;
	}
	
	@Column(name = "interruption_assurance_moins_6mois")
	public Boolean getInterruptionAssuranceMoins6mois() {
		return interruptionAssuranceMoins6mois;
	}

	public void setInterruptionAssuranceMoins6mois(Boolean interruption_assurance_moins_6mois) {
		this.interruptionAssuranceMoins6mois = interruption_assurance_moins_6mois;
	}
	
	@Column(name = "interruption_assurance_6_a_12mois")
	public Boolean getInterruptionAssurance6a12mois() {
		return interruptionAssurance6a12mois;
	}

	public void setInterruptionAssurance6a12mois(Boolean interruption_assurance_6_a_12mois) {
		this.interruptionAssurance6a12mois = interruption_assurance_6_a_12mois;
	}
	
	@Column(name = "interruption_assurance_12_a_24mois")
	public Boolean getInterruptionAssurance12a24mois() {
		return interruptionAssurance12a24mois;
	}

	public void setInterruptionAssurance12a24mois(Boolean interruption_assurance_12_a_24mois) {
		this.interruptionAssurance12a24mois = interruption_assurance_12_a_24mois;
	}
	
	@Column(name = "interruption_assurance_24_a_36mois")
	public Boolean getInterruptionAssurance24a36mois() {
		return interruptionAssurance24a36mois;
	}

	public void setInterruptionAssurance24a36mois(Boolean interruption_assurance_24_a_60mois) {
		this.interruptionAssurance24a36mois = interruption_assurance_24_a_60mois;
	}
	
	@Column(name = "interruption_assurance_36_a_60mois")
	public Boolean getInterruptionAssurance36a60mois() {
		return interruptionAssurance36a60mois;
	}

	public void setInterruptionAssurance36a60mois(Boolean interruptionAssurance36a60mois) {
		this.interruptionAssurance36a60mois = interruptionAssurance36a60mois;
	}
	
	@Column(name = "interruption_assurance_60_a_84mois")
	public Boolean getInterruptionAssurance60a84mois() {
		return interruptionAssurance60a84mois;
	}

	public void setInterruptionAssurance60a84mois(Boolean interruption_assurance_60_a_84mois) {
		this.interruptionAssurance60a84mois = interruption_assurance_60_a_84mois;
	}
	
	@Column(name = "interruption_assurance_plus_de_84mois")
	public Boolean getInterruptionAssurancePlusDe84mois() {
		return interruptionAssurancePlusDe84mois;
	}

	public void setInterruptionAssurancePlusDe84mois(Boolean interruption_assurance_plus_de_84mois) {
		this.interruptionAssurancePlusDe84mois = interruption_assurance_plus_de_84mois;
	}
	
	@Column(name = "antecedent_rcd_police_assureur")
	public String getAntecedentRcdPoliceAssureur() {
		return antecedentRcdPoliceAssureur;
	}

	public void setAntecedentRcdPoliceAssureur(String antecedent_rcd_police_assureur) {
		this.antecedentRcdPoliceAssureur = antecedent_rcd_police_assureur;
	}
	
	@Column(name = "antecedent_rce_police_assureur")
	public String getAntecedentRcePoliceAssureur() {
		return antecedentRcePoliceAssureur;
	}

	public void setAntecedentRcePoliceAssureur(String antecedent_rce_police_assureur) {
		this.antecedentRcePoliceAssureur = antecedent_rce_police_assureur;
	}
	
	@Column(name = "antecedent_rcd_nom_assureur")
	public String getAntecedentRcdNomAssureur() {
		return antecedentRcdNomAssureur;
	}

	public void setAntecedentRcdNomAssureur(String antecedent_rcd_nom_assureur) {
		this.antecedentRcdNomAssureur = antecedent_rcd_nom_assureur;
	}
	
	@Column(name = "antecedent_rce_nom_assureur")
	public String getAntecedentRceNomAssureur() {
		return antecedentRceNomAssureur;
	}

	public void setAntecedentRceNomAssureur(String antecedent_rce_nom_assureur) {
		this.antecedentRceNomAssureur = antecedent_rce_nom_assureur;
	}
	
	@Column(name = "sinistralite_liquidation_societe_demandeuse")
	public Boolean getSinistraliteLiquidationSocieteDemandeuse() {
		return sinistraliteLiquidationSocieteDemandeuse;
	}

	public void setSinistraliteLiquidationSocieteDemandeuse(Boolean sinistralite_liquidation_societe_demandeuse) {
		this.sinistraliteLiquidationSocieteDemandeuse = sinistralite_liquidation_societe_demandeuse;
	}
	
	@Column(name = "sinistralite_liquidation_autre_societe")
	public Boolean getSinistraliteLiquidationAutreSociete() {
		return sinistraliteLiquidationAutreSociete;
	}

	public void setSinistraliteLiquidationAutreSociete(Boolean sinistralite_liquidation_autre_societe) {
		this.sinistraliteLiquidationAutreSociete = sinistralite_liquidation_autre_societe;
	}
	
	@Column(name = "sinistralite_risque_refus_assurance")
	public Boolean getSinistraliteRisqueRefusAssurance() {
		return sinistraliteRisqueRefusAssurance;
	}

	public void setSinistraliteRisqueRefusAssurance(Boolean sinistralite_risque_refus_assurance) {
		this.sinistraliteRisqueRefusAssurance = sinistralite_risque_refus_assurance;
	}
	
	@Column(name = "sinistralite_mise_en_cause")
	public Boolean getSinistraliteMiseEnCause() {
		return sinistraliteMiseEnCause;
	}

	public void setSinistraliteMiseEnCause(Boolean sinistralite_mise_en_cause) {
		this.sinistraliteMiseEnCause = sinistralite_mise_en_cause;
	}
	
	@Column(name = "sinistralite_evenement_engageant_resp")
	public Boolean getSinistraliteEvenementEngageantResp() {
		return sinistraliteEvenementEngageantResp;
	}

	public void setSinistraliteEvenementEngageantResp(Boolean sinistralite_evenement_engageant_resp) {
		this.sinistraliteEvenementEngageantResp = sinistralite_evenement_engageant_resp;
	}
	
	@Column(name = "sinistralite_evenement_faits")
	public String getSinistraliteEvenementFaits() {
		return sinistraliteEvenementFaits;
	}

	public void setSinistraliteEvenementFaits(String sinistralite_evenement_faits) {
		this.sinistraliteEvenementFaits = sinistralite_evenement_faits;
	}
	
	@Column(name = "reprise_passe_moins_de_3mois")
	public Boolean getReprisePasseMoinsDe3mois() {
		return reprisePasseMoinsDe3mois;
	}

	public void setReprisePasseMoinsDe3mois(Boolean reprise_passe_moins_de_3mois) {
		this.reprisePasseMoinsDe3mois = reprise_passe_moins_de_3mois;
	}
	
	@Column(name = "reprise_passe_de_3_a_6mois")
	public Boolean getReprisePasseDe3a6mois() {
		return reprisePasseDe3a6mois;
	}

	public void setReprisePasseDe3a6mois(Boolean reprise_passe_de_3_a_6mois) {
		this.reprisePasseDe3a6mois = reprise_passe_de_3_a_6mois;
	}
	
	@Column(name = "reprise_passe_de_6_a_12mois")
	public Boolean getReprisePasseDe6a12mois() {
		return reprisePasseDe6a12mois;
	}

	public void setReprisePasseDe6a12mois(Boolean reprise_passe_de_6_a_12mois) {
		this.reprisePasseDe6a12mois = reprise_passe_de_6_a_12mois;
	}
	
	@Column(name = "territorialite_lieu_france_metrop")
	public Boolean getTerritorialiteLieuFranceMetrop() {
		return territorialiteLieuFranceMetrop;
	}

	public void setTerritorialiteLieuFranceMetrop(Boolean territorialite_lieu_france_metrop) {
		this.territorialiteLieuFranceMetrop = territorialite_lieu_france_metrop;
	}
	
	@Column(name = "territorialite_lieu_corse")
	public Boolean getTerritorialiteLieuCorse() {
		return territorialiteLieuCorse;
	}

	public void setTerritorialiteLieuCorse(Boolean territorialite_lieu_corse) {
		this.territorialiteLieuCorse = territorialite_lieu_corse;
	}
	
	@Column(name = "territorialite_lieu_domtom")
	public Boolean getTerritorialiteLieuDomtom() {
		return territorialiteLieuDomtom;
	}

	public void setTerritorialiteLieuDomtom(Boolean territorialite_lieu_domtom) {
		this.territorialiteLieuDomtom = territorialite_lieu_domtom;
	}
	
	@Column(name = "informer_caractere_obligatoire")
	public Boolean getInformerCaractereObligatoire() {
		return informerCaractereObligatoire;
	}

	public void setInformerCaractereObligatoire(Boolean informer_caractere_obligatoire) {
		this.informerCaractereObligatoire = informer_caractere_obligatoire;
	}
	
	@Column(name = "information_proposition_partie_integrante_contrat")
	public Boolean getInformationPropositionPartieIntegranteContrat() {
		return informationPropositionPartieIntegranteContrat;
	}

	public void setInformationPropositionPartieIntegranteContrat(
			Boolean information_proposition_partie_integrante_contrat) {
		this.informationPropositionPartieIntegranteContrat = information_proposition_partie_integrante_contrat;
	}
	
	@Column(name = "autorise_assureur_communiquer_reponses")
	public Boolean getAutoriseAssureurCommuniquerReponses() {
		return autoriseAssureurCommuniquerReponses;
	}

	public void setAutoriseAssureurCommuniquerReponses(Boolean autorise_assureur_communiquer_reponses) {
		this.autoriseAssureurCommuniquerReponses = autorise_assureur_communiquer_reponses;
	}
	
	@Column(name = "oppose_utilisation_donnees_fin_commerciale")
	public Boolean getOpposeUtilisationDonneesFinCommerciale() {
		return opposeUtilisationDonneesFinCommerciale;
	}

	public void setOpposeUtilisationDonneesFinCommerciale(Boolean oppose_utilisation_donnees_fin_commerciale) {
		this.opposeUtilisationDonneesFinCommerciale = oppose_utilisation_donnees_fin_commerciale;
	}

	@Column(name = "soumis_etude")
	public Boolean getSoumisEtude() {
		return soumisEtude;
	}

	public void setSoumisEtude(Boolean soumis_etude) {
		this.soumisEtude = soumis_etude;
	}

	@Column(name = "validation_apres_etude_ylyade")
	public Boolean getValidationApresetudeYlyade() {
		return validationApresetudeYlyade;
	}

	public void setValidationApresetudeYlyade(Boolean validation_apres_etude) {
		this.validationApresetudeYlyade = validation_apres_etude;
	}

	@Column(name = "validation_super_assureur")
	public Boolean getValidationSuperAssureur() {
		return validationSuperAssureur;
	}

	public void setValidationSuperAssureur(Boolean validation_super_assureur) {
		this.validationSuperAssureur = validation_super_assureur;
	}

	@Column(name = "validation_ylyade")
	public Boolean getValidationYlyade() {
		return validationYlyade;
	}

	public void setValidationYlyade(Boolean validation_ylyade) {
		this.validationYlyade = validation_ylyade;
	}

	@Column(name = "date_reprise_du_passe")
	public Date getDateRepriseDuPasse() {
		return dateRepriseDuPasse;
	}

	public void setDateRepriseDuPasse(Date date_reprise_du_passe) {
		this.dateRepriseDuPasse = date_reprise_du_passe;
	}
	
	@Column(name = "taux_risque_par_famille_activite")
	public BigDecimal getTauxRisqueParFamilleActivite() {
		return tauxRisqueParFamilleActivite;
	}

	public void setTauxRisqueParFamilleActivite(BigDecimal taux_risque_par_famille_activite) {
		this.tauxRisqueParFamilleActivite = taux_risque_par_famille_activite;
	}

	@Column(name = "taux_nombre_activite")
	public BigDecimal getTauxNombreActivite() {
		return tauxNombreActivite;
	}

	public void setTauxNombreActivite(BigDecimal taux_nombre_activite) {
		this.tauxNombreActivite = taux_nombre_activite;
	}

	@Column(name = "code_interruption_assurance")
	public String getCodeInterruptionAssurance() {
		return codeInterruptionAssurance;
	}

	public void setCodeInterruptionAssurance(String code_interruption_assurance) {
		this.codeInterruptionAssurance = code_interruption_assurance;
	}

	@Column(name = "taux_interruption_assurance")
	public BigDecimal getTauxInterruptionAssurance() {
		return tauxInterruptionAssurance;
	}

	public void setTauxInterruptionAssurance(BigDecimal taux_interruption_assurance) {
		this.tauxInterruptionAssurance = taux_interruption_assurance;
	}

	@Column(name = "code_reprise_passe")
	public String getCodeReprisePasse() {
		return codeReprisePasse;
	}

	public void setCodeReprisePasse(String code_reprise_passe) {
		this.codeReprisePasse = code_reprise_passe;
	}

	@Column(name = "taux_reprise_passe")
	public BigDecimal getTauxReprisePasse() {
		return tauxReprisePasse;
	}

	public void setTauxReprisePasse(BigDecimal taux_reprise_passe) {
		this.tauxReprisePasse = taux_reprise_passe;
	}

	@Column(name = "taux_resiliation_non_paiement")
	public BigDecimal getTauxResiliationNonPaiement() {
		return tauxResiliationNonPaiement;
	}

	public void setTauxResiliationNonPaiement(BigDecimal taux_resiliation_non_paiement) {
		this.tauxResiliationNonPaiement = taux_resiliation_non_paiement;
	}

	@Column(name = "taux_antecedent_sinistre")
	public BigDecimal getTauxAntecedentSinistre() {
		return tauxAntecedentSinistre;
	}

	public void setTauxAntecedentSinistre(BigDecimal taux_antecedent_sinistre) {
		this.tauxAntecedentSinistre = taux_antecedent_sinistre;
	}

	@Column(name = "nb_antecedent_sinistre")
	public Integer getNbAntecedentSinistre() {
		return nbAntecedentSinistre;
	}

	public void setNbAntecedentSinistre(Integer nb_antecedent_sinistre) {
		this.nbAntecedentSinistre = nb_antecedent_sinistre;
	}

	@Column(name = "cout_max_pour_nb_antecedent_sinistre")
	public BigDecimal getCoutMaxPourNbAntecedentSinistre() {
		return coutMaxPourNbAntecedentSinistre;
	}

	public void setCoutMaxPourNbAntecedentSinistre(BigDecimal cout_max_pour_nb_antecedent_sinistre) {
		this.coutMaxPourNbAntecedentSinistre = cout_max_pour_nb_antecedent_sinistre;
	}

	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taDossierRcd", orphanRemoval=true)
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "taDossierRcd", orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	public List<TaGedUtilisateur> getTaGedUtilisateur() {
		return taGedUtilisateur;
	}

	public void setTaGedUtilisateur(List<TaGedUtilisateur> taGedUtilisateur) {
		this.taGedUtilisateur = taGedUtilisateur;
	}
	@Column(name = "traite")
	public Boolean getTraite() {
		return traite;
	}

	public void setTraite(Boolean traite) {
		this.traite = traite;
	}
	@Column(name = "validation_courtier")
	public Boolean getValidationCourtier() {
		return validationCourtier;
	}

	public void setValidationCourtier(Boolean validationCourtier) {
		this.validationCourtier = validationCourtier;
	}
	@Column(name = "commentaire_courtier")
	public String getCommentaireCourtier() {
		return commentaireCourtier;
	}

	public void setCommentaireCourtier(String commentaireCourtier) {
		this.commentaireCourtier = commentaireCourtier;
	}

	@Column(name = "tx_regul_ca_ht")
	public BigDecimal getTxRegulCaHt() {
		return txRegulCaHt;
	}

	public void setTxRegulCaHt(BigDecimal tx_regul_ca_ht) {
		this.txRegulCaHt = tx_regul_ca_ht;
	}

	@Column(name = "commentaire_validation_ylyade")
	public String getCommentaireValidationYlyade() {
		return commentaireValidationYlyade;
	}

	public void setCommentaireValidationYlyade(String commentaire_validation_ylyade) {
		this.commentaireValidationYlyade = commentaire_validation_ylyade;
	}

	@Column(name = "commentaire_apres_expertise")
	public String getCommentaireApresExpertise() {
		return commentaireApresExpertise;
	}

	public void setCommentaireApresExpertise(String commentaire_apres_expertise) {
		this.commentaireApresExpertise = commentaire_apres_expertise;
	}

	@Column(name = "commentaire_validation_assureur")
	public String getCommentaireValidationAssureur() {
		return commentaireValidationAssureur;
	}

	public void setCommentaireValidationAssureur(String commentaire_validation_assureur) {
		this.commentaireValidationAssureur = commentaire_validation_assureur;
	}

	@Column(name = "inclusion_frais_dossier")
	public Boolean getInclusionFraisDossier() {
		return inclusionFraisDossier;
	}

	public void setInclusionFraisDossier(Boolean inclusion_frais_dossier) {
		this.inclusionFraisDossier = inclusion_frais_dossier;
	}

	@Column(name = "montant_dprsa")
	public BigDecimal getMontantDprsa() {
		return montantDprsa;
	}

	public void setMontantDprsa(BigDecimal montant_dprsa) {
		this.montantDprsa = montant_dprsa;
	}

	@Column(name = "montant_frais_compagnie_super_assureur")
	public BigDecimal getMontantFraisCompagnieSuperAssureur() {
		return montantFraisCompagnieSuperAssureur;
	}

	public void setMontantFraisCompagnieSuperAssureur(BigDecimal montant_frais_compagnie_super_assureur) {
		this.montantFraisCompagnieSuperAssureur = montant_frais_compagnie_super_assureur;
	}
	@Column(name = "soumis_souscription")
	public Boolean getSoumisSouscription() {
		return soumisSouscription;
	}

	public void setSoumisSouscription(Boolean soumisSouscription) {
		this.soumisSouscription = soumisSouscription;
	}
	
	@Column(name = "validation_globale_ged_courtier")
	public Boolean getValidationGlobaleGedCourtier() {
		return validationGlobaleGedCourtier;
	}

	public void setValidationGlobaleGedCourtier(Boolean validationGlobaleGedCourtier) {
		this.validationGlobaleGedCourtier = validationGlobaleGedCourtier;
	}

	@Column(name = "premier_paiement_effectue")
	public Boolean getPremierPaiementEffectue() {
		return premierPaiementEffectue;
	}

	public void setPremierPaiementEffectue(Boolean premierPaiementEffectue) {
		this.premierPaiementEffectue = premierPaiementEffectue;
	}

	@Column(name = "montant_prime_annuelle_ht")
	public BigDecimal getMontantPrimeAnnuelleHT() {
		return montantPrimeAnnuelleHT;
	}

	public void setMontantPrimeAnnuelleHT(BigDecimal montantPrimeAnnuelleHT) {
		this.montantPrimeAnnuelleHT = montantPrimeAnnuelleHT;
	}

	@Column(name = "montant_prime_annuelle_ttc")
	public BigDecimal getMontantPrimeAnnuelleTTC() {
		return montantPrimeAnnuelleTTC;
	}

	public void setMontantPrimeAnnuelleTTC(BigDecimal montantPrimeAnnuelleTTC) {
		this.montantPrimeAnnuelleTTC = montantPrimeAnnuelleTTC;
	}

	@Column(name = "montant_prime_echeance_ht")
	public BigDecimal getMontantPrimeEcheanceHT() {
		return montantPrimeEcheanceHT;
	}

	public void setMontantPrimeEcheanceHT(BigDecimal montantPrimeEcheanceHT) {
		this.montantPrimeEcheanceHT = montantPrimeEcheanceHT;
	}

	@Column(name = "montant_prime_echeance_ttc")
	public BigDecimal getMontantPrimeEcheanceTTC() {
		return montantPrimeEcheanceTTC;
	}

	public void setMontantPrimeEcheanceTTC(BigDecimal montantPrimeEcheanceTTC) {
		this.montantPrimeEcheanceTTC = montantPrimeEcheanceTTC;
	}

	@Column(name = "tarif_annuel_total_ht")
	public BigDecimal getTarifAnnuelTotalHT() {
		return tarifAnnuelTotalHT;
	}

	public void setTarifAnnuelTotalHT(BigDecimal tarif_annuel_total_ht) {
		this.tarifAnnuelTotalHT = tarif_annuel_total_ht;
	}

	@Column(name = "tarif_annuel_total_ttc")
	public BigDecimal getTarifAnnuelTotalTTC() {
		return tarifAnnuelTotalTTC;
	}

	public void setTarifAnnuelTotalTTC(BigDecimal tarif_annuel_total_ttc) {
		this.tarifAnnuelTotalTTC = tarif_annuel_total_ttc;
	}

	@Column(name = "validation_globale_ged_ylyade")
	public Boolean getValidationGlobaleGedYlyade() {
		return validationGlobaleGedYlyade;
	}

	public void setValidationGlobaleGedYlyade(Boolean validation_globale_ged_ylyade) {
		this.validationGlobaleGedYlyade = validation_globale_ged_ylyade;
	}

	@Column(name = "refus_definitif_ylyade")
	public Boolean getRefusDefinitifYlyade() {
		return refusDefinitifYlyade;
	}

	public void setRefusDefinitifYlyade(Boolean refus_definitif_ylyade) {
		this.refusDefinitifYlyade = refus_definitif_ylyade;
	}

	@Column(name = "refus_definitif_super_assureur")
	public Boolean getRefusDefinitifSuperAssureur() {
		return refusDefinitifSuperAssureur;
	}

	public void setRefusDefinitifSuperAssureur(Boolean refus_definitif_super_assureur) {
		this.refusDefinitifSuperAssureur = refus_definitif_super_assureur;
	}

	@Column(name = "commentaire_refus_definitif_ylyade")
	public String getCommentaireRefusDefinitifYlyade() {
		return commentaireRefusDefinitifYlyade;
	}

	public void setCommentaireRefusDefinitifYlyade(String commentaire_refus_definitif_ylyade) {
		this.commentaireRefusDefinitifYlyade = commentaire_refus_definitif_ylyade;
	}

	@Column(name = "commentaire_refus_definitif_super_assureur")
	public String getCommentaireRefusDefinitifSuperAssureur() {
		return commentaireRefusDefinitifSuperAssureur;
	}

	public void setCommentaireRefusDefinitifSuperAssureur(String commentaire_refus_definitif_super_assureur) {
		this.commentaireRefusDefinitifSuperAssureur = commentaire_refus_definitif_super_assureur;
	}
	
	@Column(name = "validation_assureur_apres_etude")
	public Boolean getValidationAssureurApresetude() {
		return validationAssureurApresetude;
	}

	public void setValidationAssureurApresetude(Boolean validationAssureurApresetude) {
		this.validationAssureurApresetude = validationAssureurApresetude;
	}

	@Column(name = "soumis_etude_assureur")
	public Boolean getSoumisEtudeAssureur() {
		return soumisEtudeAssureur;
	}

	public void setSoumisEtudeAssureur(Boolean soumis_etude_assureur) {
		this.soumisEtudeAssureur = soumis_etude_assureur;
	}

	@Column(name = "montant_taxe_total")
	public BigDecimal getMontantTaxeTotal() {
		return montantTaxeTotal;
	}

	public void setMontantTaxeTotal(BigDecimal montantTaxeTotal) {
		this.montantTaxeTotal = montantTaxeTotal;
	}

	@Column(name = "montant_total_taxes_9pc")
	public BigDecimal getMontantTotalTaxes9pc() {
		return montantTotalTaxes9pc;
	}

	public void setMontantTotalTaxes9pc(BigDecimal montant_total_taxes_9pc) {
		this.montantTotalTaxes9pc = montant_total_taxes_9pc;
	}

	@Column(name = "montant_total_taxes_13_4pc")
	public BigDecimal getMontantTotalTaxes134pc() {
		return montantTotalTaxes134pc;
	}

	public void setMontantTotalTaxes134pc(BigDecimal montant_total_taxex_13_4pc) {
		this.montantTotalTaxes134pc = montant_total_taxex_13_4pc;
	}

	@Column(name = "tarif_annuel_ttc_plus_frais_fractionnement")
	public BigDecimal getTarifAnnuelTtcPlusFraisFractionnement() {
		return tarifAnnuelTtcPlusFraisFractionnement;
	}

	public void setTarifAnnuelTtcPlusFraisFractionnement(BigDecimal tarif_annuel_ttc_plus_frais_fractionnement) {
		this.tarifAnnuelTtcPlusFraisFractionnement = tarif_annuel_ttc_plus_frais_fractionnement;
	}

	@Column(name = "pourcent_sous_traitance_calcul")
	public BigDecimal getPourcentSousTraitanceCalcul() {
		return pourcentSousTraitanceCalcul;
	}

	public void setPourcentSousTraitanceCalcul(BigDecimal pourcent_sous_traitance_calcul) {
		this.pourcentSousTraitanceCalcul = pourcent_sous_traitance_calcul;
	}
	@Column(name = "prime_nette_ylyade")
	public BigDecimal getPrimeNetteYlyade() {
		return primeNetteYlyade;
	}

	public void setPrimeNetteYlyade(BigDecimal primeNetteYlyade) {
		this.primeNetteYlyade = primeNetteYlyade;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ta_r_t_statut_dossier_rcd",
			joinColumns = {@JoinColumn(name = "id_dossier_rcd_ta_dossier_rcd")},inverseJoinColumns = {@JoinColumn(name = "id_t_statut_ta_t_statut")})
	@Fetch(value = FetchMode.SUBSELECT)
	public List<TaTStatut> getTaTStatut() {
		return taTStatut;
	}

	public void setTaTStatut(List<TaTStatut>  taTStatut) {
		this.taTStatut = taTStatut;
	}
	@Column(name = "prime_nette_assureur")
	public BigDecimal getPrimeNetteAssureur() {
		return primeNetteAssureur;
	}

	public void setPrimeNetteAssureur(BigDecimal primeNetteAssureur) {
		this.primeNetteAssureur = primeNetteAssureur;
	}
	
	@Column(name = "resilie_non_paiement_contrat")
	public Date getResilieNonPaiementContrat() {
		return resilieNonPaiementContrat;
	}

	public void setResilieNonPaiementContrat(Date resilieNonPaiementContrat) {
		this.resilieNonPaiementContrat = resilieNonPaiementContrat;
	}
	
	@Column(name = "resilie_fausse_declaration_contrat")
	public Date getResilieFausseDeclarationContrat() {
		return resilieFausseDeclarationContrat;
	}

	public void setResilieFausseDeclarationContrat(Date resilieFausseDeclarationContrat) {
		this.resilieFausseDeclarationContrat = resilieFausseDeclarationContrat;
	}
	@Column(name = "resilie_echeance_contrat")
	public Date getResilieEcheanceContrat() {
		return resilieEcheanceContrat;
	}

	public void setResilieEcheanceContrat(Date resilieEcheanceContrat) {
		this.resilieEcheanceContrat = resilieEcheanceContrat;
	}
	@Column(name = "resilie_amiable_contrat")
	public Date getResilieAmiableContrat() {
		return resilieAmiableContrat;
	}

	public void setResilieAmiableContrat(Date resilieAmiableContrat) {
		this.resilieAmiableContrat = resilieAmiableContrat;
	}
	@Column(name = "resilie_cessation_activite_contrat")
	public Date getResilieCessationActiviteContrat() {
		return resilieCessationActiviteContrat;
	}

	public void setResilieCessationActiviteContrat(Date resilieCessationActiviteContrat) {
		this.resilieCessationActiviteContrat = resilieCessationActiviteContrat;
	}
	@Column(name = "resilie_sans_effet_contrat")
	public Date getResilieSansEffetContrat() {
		return resilieSansEffetContrat;
	}

	public void setResilieSansEffetContrat(Date resilieSansEffetContrat) {
		this.resilieSansEffetContrat = resilieSansEffetContrat;
	}
	@Column(name = "mis_en_demeure")
	public Date getMisEnDemeure() {
		return misEnDemeure;
	}

	public void setMisEnDemeure(Date misEnDemeure) {
		this.misEnDemeure = misEnDemeure;
	}
	@Column(name = "suspendu_non_paiement")
	public Date getSuspenduNonPaiement() {
		return suspenduNonPaiement;
	}

	public void setSuspenduNonPaiement(Date suspenduNonPaiement) {
		this.suspenduNonPaiement = suspenduNonPaiement;
	}
	@Column(name="suspendu_avenant")
	public Boolean getSuspenduAvenant() {
		return suspenduAvenant;
	}

	public void setSuspenduAvenant(Boolean suspenduAvenant) {
		this.suspenduAvenant = suspenduAvenant;
	}
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taDossierRcd")
//	@Fetch(FetchMode.SUBSELECT)
//	public List<TaAttestationNominative> getTaAttestationNominative() {
//		return taAttestationNominative;
//	}
//
//	public void setTaAttestationNominative(List<TaAttestationNominative> taAttestationNominative) {
//		this.taAttestationNominative = taAttestationNominative;
//	}
//	@Column(name="json_data")
//	public String getJsonDATA() {
//		return jsonDATA;
//	}
//
//	public void setJsonDATA(String jsonDATA) {
//		this.jsonDATA = jsonDATA;
//	}
	@Column(name = "lettre_pj_num_police")
	public String getLettrePjNumPolice() {
		return lettrePjNumPolice;
	}

	public void setLettrePjNumPolice(String lettrePjNumPolice) {
		this.lettrePjNumPolice = lettrePjNumPolice;
	}
}
