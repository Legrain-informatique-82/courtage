package fr.ylyade.courtage.model;

import java.util.Date;
import java.util.List;

public interface IDonneStatut {
	
	public List<TaTStatut> getTaTStatut();
	public Boolean getRefusDefinitifSuperAssureur();
	public Boolean getRefusDefinitifYlyade();
	public Boolean getContrat();
	public Boolean getSoumisEtude();
	public Boolean getSoumisEtudeAssureur();
	public Boolean getValidationAssureurApresetude();
	public Boolean getValidationApresetudeYlyade();
	public Boolean getValidationGlobaleGedCourtier();
	public Boolean getValidationGlobaleGedYlyade();
	public Boolean getSoumisSouscription();
	public Boolean getPremierPaiementEffectue();
	public Boolean getValidationYlyade();
	public Boolean getValidationSuperAssureur();
	public Integer getNumeroAvenant();
//	public String getCodeTReglement();
//	public Date getDateReceptionCheque();
//	public Date getDateDepotCheque();
//	public Date getDateEncaissementCheque();
//	public Date getDateVirementEffectue();
//	public Date getDateVirementRecu();
	public Boolean getContratEnCours();
	public Boolean getTraite();
	public Date getResilieAmiableContrat();
	public Date getResilieCessationActiviteContrat();
	public Date getResilieEcheanceContrat();
	public Date getResilieFausseDeclarationContrat();
	public Date getResilieNonPaiementContrat();
	public Date getResilieSansEffetContrat();
	public Date getMisEnDemeure();
	public Date getSuspenduNonPaiement();
	public Boolean getSuspenduAvenant();

}
