package fr.ylyade.courtage.dto;

public class TaAttestationCompetencesDTO extends ModelObject implements java.io.Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8650545892850443894L;

	private Integer id;
	
	private String exEmployeurNom;
	private String exEmployeurAdresse;
	private String exEmployeurSiren;
	private String exEmployeurDateDebut;
	private String exEmployeurDateFin;
	private String exEmploiQualite;
	private String exEmploiActiviteExercee;

	
	private Integer versionObj;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getExEmployeurNom() {
		return exEmployeurNom;
	}

	public void setExEmployeurNom(String exEmployeurNom) {
		this.exEmployeurNom = exEmployeurNom;
	}

	public String getExEmployeurAdresse() {
		return exEmployeurAdresse;
	}

	public void setExEmployeurAdresse(String exEmployeurAdresse) {
		this.exEmployeurAdresse = exEmployeurAdresse;
	}

	public String getExEmployeurSiren() {
		return exEmployeurSiren;
	}

	public void setExEmployeurSiren(String exEmployeurSiren) {
		this.exEmployeurSiren = exEmployeurSiren;
	}

	public String getExEmployeurDateDebut() {
		return exEmployeurDateDebut;
	}

	public void setExEmployeurDateDebut(String exEmployeurDateDebut) {
		this.exEmployeurDateDebut = exEmployeurDateDebut;
	}

	public String getExEmployeurDateFin() {
		return exEmployeurDateFin;
	}

	public void setExEmployeurDateFin(String exEmployeurDateFin) {
		this.exEmployeurDateFin = exEmployeurDateFin;
	}

	public String getExEmploiQualite() {
		return exEmploiQualite;
	}

	public void setExEmploiQualite(String exEmploiQualite) {
		this.exEmploiQualite = exEmploiQualite;
	}

	public String getExEmploiActiviteExercee() {
		return exEmploiActiviteExercee;
	}

	public void setExEmploiActiviteExercee(String exEmploiActiviteExercee) {
		this.exEmploiActiviteExercee = exEmploiActiviteExercee;
	}

	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
}
