package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaActiviteDTO extends ModelObject implements java.io.Serializable {

	
	private static final long serialVersionUID = -5661187164450838879L;

	private Integer id;
	
	
	private String liblActivite;
	private String codeActivite;
	private String descriptionActivite;
	private Integer position;

	private Boolean actif = false;
	private BigDecimal franchise;
	
	private Integer idFamilleActivite;
	private String codeFamilleActivite;
	private String liblFamilleActivite;
	
	private Integer idClasse;
	private String codeClasse;
	private String liblClasse;
	
	private BigDecimal minCa;
	private BigDecimal maxCa;
	private BigDecimal primeBase;
	
	private String commentaire;
	private BigDecimal pourcentExerceEntreprise;
	private BigDecimal pourcentSousTraite;

	private BigDecimal pourcentTotalRepartition;
	
	private Boolean commentaireObligatoire = false;

	private Integer versionObj;
	
	
	public Integer getIdFamilleActivite() {
		return idFamilleActivite;
	}

	public void setIdFamilleActivite(Integer idFamilleActivite) {
		this.idFamilleActivite = idFamilleActivite;
	}

	public String getCodeFamilleActivite() {
		return codeFamilleActivite;
	}

	public void setCodeFamilleActivite(String codeFamilleActivite) {
		this.codeFamilleActivite = codeFamilleActivite;
	}

	public String getLiblFamilleActivite() {
		return liblFamilleActivite;
	}

	public void setLiblFamilleActivite(String liblFamilleActivite) {
		this.liblFamilleActivite = liblFamilleActivite;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public String getLiblActivite() {
		return liblActivite;
	}

	public void setLiblActivite(String liblActivite) {
		this.liblActivite = liblActivite;
	}

	public String getCodeActivite() {
		return codeActivite;
	}

	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	public Integer getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Integer idClasse) {
		this.idClasse = idClasse;
	}

	public String getCodeClasse() {
		return codeClasse;
	}

	public void setCodeClasse(String codeClasse) {
		this.codeClasse = codeClasse;
	}

	public String getLiblClasse() {
		return liblClasse;
	}

	public void setLiblClasse(String liblClasse) {
		this.liblClasse = liblClasse;
	}
	public BigDecimal getMinCa() {
		return minCa;
	}

	public void setMinCa(BigDecimal minCa) {
		this.minCa = minCa;
	}

	public BigDecimal getMaxCa() {
		return maxCa;
	}

	public void setMaxCa(BigDecimal maxCa) {
		this.maxCa = maxCa;
	}

	public BigDecimal getPrimeBase() {
		return primeBase;
	}

	public void setPrimeBase(BigDecimal primeBase) {
		this.primeBase = primeBase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeActivite == null) ? 0 : codeActivite.hashCode());
		result = prime * result + ((codeClasse == null) ? 0 : codeClasse.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idClasse == null) ? 0 : idClasse.hashCode());
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
		TaActiviteDTO other = (TaActiviteDTO) obj;
		if (codeActivite == null) {
			if (other.codeActivite != null)
				return false;
		} else if (!codeActivite.equals(other.codeActivite))
			return false;
		if (codeClasse == null) {
			if (other.codeClasse != null)
				return false;
		} else if (!codeClasse.equals(other.codeClasse))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idClasse == null) {
			if (other.idClasse != null)
				return false;
		} else if (!idClasse.equals(other.idClasse))
			return false;
		return true;
	}

	public String getDescriptionActivite() {
		return descriptionActivite;
	}

	public void setDescriptionActivite(String descriptionActivite) {
		this.descriptionActivite = descriptionActivite;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public BigDecimal getPourcentExerceEntreprise() {
		return pourcentExerceEntreprise;
	}

	public void setPourcentExerceEntreprise(BigDecimal pourcentExerceEntreprise) {
		this.pourcentExerceEntreprise = pourcentExerceEntreprise;
	}

	public BigDecimal getPourcentSousTraite() {
		return pourcentSousTraite;
	}

	public void setPourcentSousTraite(BigDecimal pourcentSousTraite) {
		this.pourcentSousTraite = pourcentSousTraite;
	}

	public BigDecimal getPourcentTotalRepartition() {
		return pourcentTotalRepartition;
	}

	public void setPourcentTotalRepartition(BigDecimal pourcentTotalRepartition) {
		this.pourcentTotalRepartition = pourcentTotalRepartition;
	}

	public Boolean getCommentaireObligatoire() {
		return commentaireObligatoire;
	}

	public void setCommentaireObligatoire(Boolean commentaireObligatoire) {
		this.commentaireObligatoire = commentaireObligatoire;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	

	
}
