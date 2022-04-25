package fr.ylyade.courtage.dto;

import java.math.BigDecimal;

public class TaTauxRcproPibDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = 9060988248165963111L;

	private Integer id;
	
	private String codeTauxRcproPib;
	private String liblTauxRcproPib;
	private BigDecimal tauxRcproPib;
	private String descriptionActivite;
    private BigDecimal primeMin;
	
	private Integer versionObj;
	
	
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

	public String getCodeTauxRcproPib() {
		return codeTauxRcproPib;
	}

	public void setCodeTauxRcproPib(String codeTauxRcproPib) {
		this.codeTauxRcproPib = codeTauxRcproPib;
	}

	public String getLiblTauxRcproPib() {
		return liblTauxRcproPib;
	}

	public void setLiblTauxRcproPib(String liblTauxRcproPib) {
		this.liblTauxRcproPib = liblTauxRcproPib;
	}

	public BigDecimal getTauxRcproPib() {
		return tauxRcproPib;
	}

	public void setTauxRcproPib(BigDecimal tauxRcproPib) {
		this.tauxRcproPib = tauxRcproPib;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeTauxRcproPib == null) ? 0 : codeTauxRcproPib.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((liblTauxRcproPib == null) ? 0 : liblTauxRcproPib.hashCode());
		result = prime * result + ((tauxRcproPib == null) ? 0 : tauxRcproPib.hashCode());
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
		TaTauxRcproPibDTO other = (TaTauxRcproPibDTO) obj;
		if (codeTauxRcproPib == null) {
			if (other.codeTauxRcproPib != null)
				return false;
		} else if (!codeTauxRcproPib.equals(other.codeTauxRcproPib))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liblTauxRcproPib == null) {
			if (other.liblTauxRcproPib != null)
				return false;
		} else if (!liblTauxRcproPib.equals(other.liblTauxRcproPib))
			return false;
		if (tauxRcproPib == null) {
			if (other.tauxRcproPib != null)
				return false;
		} else if (!tauxRcproPib.equals(other.tauxRcproPib))
			return false;
		return true;
	}

	public String getDescriptionActivite() {
		return descriptionActivite;
	}

	public void setDescriptionActivite(String descriptionActivite) {
		this.descriptionActivite = descriptionActivite;
	}

	public BigDecimal getPrimeMin() {
		return primeMin;
	}

	public void setPrimeMin(BigDecimal primeMin) {
		this.primeMin = primeMin;
	}
	
}
