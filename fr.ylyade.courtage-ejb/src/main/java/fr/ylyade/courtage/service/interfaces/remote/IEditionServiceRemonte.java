package fr.ylyade.courtage.service.interfaces.remote;

import java.util.List;
import java.util.Map;

import fr.ylyade.courtage.model.TaDossierRcd;

public interface IEditionServiceRemonte {
	
	public String generePropositionDevisPDF(int idDossierRcd, String modeleEdition);
	public String generePDF(String modeleEdition, String localPath, List<String> listResourcesPath, Map<String,Object> mapParametreEdition);
	
	public String genereConditionParticulierePDF(int idDossierRcd, String modeleEdition);
	public String genereAttestationAssurancePDF(int idDossierRcd, String modeleEdition);
	public String genereAvisEcheancePDF(int idEcheance, String modeleEdition);
	public String genereAvisMiseEnDemeurePDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisSuspensionGarantiePDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationImpayePDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationEcheancePDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationFausseDeclaPDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationSansEffetPDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationCessationPDF(int idDossier, int idEcheance, String modeleEdition);
	public String genereAvisResiliationAmiablePDF(int idDossier, int idEcheance, String modeleEdition);
	
	
}
