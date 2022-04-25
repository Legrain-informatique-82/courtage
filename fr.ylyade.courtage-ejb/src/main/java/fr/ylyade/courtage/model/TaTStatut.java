package fr.ylyade.courtage.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ta_t_statut")
@NamedQueries(value = { 
		@NamedQuery(name=TaTStatut.QN.FIND_ALL_STATUT_BY_ID_DOSSIER, 
		query="select s "
			+ " from TaDossierRcd d  left join d.taTStatut s"
			+ " where d.idDossierRcd = :idDossierRcd")
		

})
public class TaTStatut implements Serializable {
	
	public static class QN {
		public static final String FIND_ALL_STATUT_BY_ID_DOSSIER = "TaTStatut.findAllStatutByIdDossier";
		
	}

	private static final long serialVersionUID = 4762807175657089914L;
	
	public static final String enCours = "en_cours";
	public static final String attenteVerifPaiement = "attente_verif_paiement";
	public static final String attentePremierPaiement = "attente_premier_paiement";
	public static final String enAttentePiece = "attente_piece";
	
	public static final String resilieEcheance = "resilie_echeance";
	public static final String resilieCessationActivite = "resilie_cessation_activite";
	public static final String resilieNonPaiement = "resilie_non_paiement";
	public static final String sansEffet = "sans_effet";
	public static final String resilieAmiable = "resilie_amiable";
	public static final String resilieFausseDeclaration = "resilie_fausse_declaration";
	
	public static final String miseDemeure = "mise_en_demeure";
	public static final String suspendu = "suspendu";
	public static final String aTraiter = "a_traiter";
	public static final String soumisEtudeYlyade = "soumis_etude_ylyade";
	public static final String soumisEtudeAssureur = "soumis_etude_assureur";
	public static final String validationApresEtudeYlyade = "validation_apres_etude_ylyade";
	public static final String validationApresEtudeAssureur = "validation_apres_etude_assureur";
	public static final String refusApresEtudeYlyade = "refus_apres_etude_ylyade";
	public static final String refusApresEtudeAssureur = "refus_apres_etude_assureur";
	public static final String soumisSouscription = "soumis_souscription";
	public static final String refusDefinitif = "refus_definitif";
	public static final String gedAValiderYlyade = "ged_a_valider_ylyade";
	public static final String gedAValiderCourtier = "ged_a_valider_courtier";
	public static final String attenteValidationYlyade = "attente_validation_ylyade";
	public static final String attenteValidationAssureur = "attente_validation_assureur";
	public static final String refuseAssureur = "refuse_assureur";
	public static final String avenant = "avenant";
	
	public static final String attenteEnvoiCheque = "attente_envoi_cheque";
	public static final String attenteReceptionCheque = "attente_reception_cheque";
	public static final String attenteDepotCheque = "attente_depot_cheque";
	public static final String attenteEncaissementCheque = "attente_encaissement_cheque";
	public static final String attenteVirement = "attente_virement";
	public static final String attenteValidationVirement = "attente_validation_virement";
	
	public static final String suspenduAvenant= "suspendu_avenant";
	
	private Integer idTStatut;
	private String codeTStatut;
	private String liblTStatut;
	private Date dureeStatut;
	private int dureeNbJoursStatut;
	
	private Integer versionObj;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	
	@Override
	public String toString() {
	    return String.format("Statut :"+liblTStatut);
	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TaTStatut other = (TaTStatut) obj;
//		if (idTStatut != other.idTStatut)
//			return false;
//		return true;
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_statut", unique = true, nullable = false)
	public Integer getIdTStatut() {
		return idTStatut;
	}

	public void setIdTStatut(Integer id) {
		this.idTStatut = id;
	}

	@Column(name = "code_statut")
	public String getCodeTStatut() {
		return codeTStatut;
	}

	public void setCodeTStatut(String codeStatut) {
		this.codeTStatut = codeStatut;
	}

	@Column(name = "libl_statut")
	public String getLiblTStatut() {
		return liblTStatut;
	}

	public void setLiblTStatut(String liblStatut) {
		this.liblTStatut = liblStatut;
	}

	@Column(name = "duree_statut")
	public Date getDureeStatut() {
		return dureeStatut;
	}

	public void setDureeStatut(Date dureeStatut) {
		this.dureeStatut = dureeStatut;
	}
	
	@Column(name = "duree_nb_jours_statut")
	public int getDureeNbJoursStatut() {
		return dureeNbJoursStatut;
	}

	public void setDureeNbJoursStatut(int dureeNbJoursStatut) {
		this.dureeNbJoursStatut = dureeNbJoursStatut;
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
}
