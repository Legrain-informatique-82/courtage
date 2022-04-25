package fr.ylyade.courtage.model.mapper;

import java.util.Date;

import fr.ylyade.courtage.dto.TaDossierRcdDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaDossierRcdMapper implements ILgrMapper<TaDossierRcdDTO, TaDossierRcd> {

	@Override
	public TaDossierRcd mapDtoToEntity(TaDossierRcdDTO dto, TaDossierRcd entity) {
		if(entity==null)
			entity = new TaDossierRcd();

		entity.setIdDossierRcd(dto.getId()!=null?dto.getId():0);
		entity.setNumDossierPolice(dto.getNumDossierPolice());
		entity.setLettrePjNumPolice(dto.getLettrePjNumPolice());
		entity.setVersion(dto.getVersion());
		entity.setContrat(dto.getContrat());
//		entity.setTaSinistreAntecedent(dto.getTaSinistreAntecedent());
//		entity.setJsonDATA(dto.getJsonDATA());

		if(entity.getTaAssure()!=null) {
			entity.getTaAssure().setIdAssure(dto.getIdAssure());
			entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
//			entity.getTaAssure().setRaisonSociale(dto.getRaisonSociale());
			
			if(entity.getTaAssure().getTaContactEntreprise()!=null) {
//				entity.getTaAssure().getTaContactEntreprise().setIdContactEntreprise(dto.getIdContactEntreprise());
//				entity.getTaAssure().getTaContactEntreprise().setNom(dto.getNom());
//				entity.getTaAssure().getTaContactEntreprise().setPrenom(dto.getPrenom());
			}
		}
//		if(entity.getTaContratRcPro()!=null) {
//			entity.getTaContratRcPro().setIdContratRcPro(dto.getIdContratRcPro());
//			entity.getTaContratRcPro().setNumPolice(dto.getNumPolice());
//		}
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaDossierRcdDTO mapEntityToDto(TaDossierRcd entity, TaDossierRcdDTO dto) {
		if(dto==null)
			dto = new TaDossierRcdDTO();

		dto.setId(entity.getIdDossierRcd());
		dto.setNumDossierPolice(entity.getNumDossierPolice());
		dto.setLettrePjNumPolice(entity.getLettrePjNumPolice());
		dto.setVersion(entity.getVersion());
		dto.setContrat(entity.getContrat());
		dto.setNumeroAvenant(entity.getNumeroAvenant());
//		dto.setTaSinistreAntecedent(entity.getTaSinistreAntecedent());
//		dto.setJsonDATA(entity.getJsonDATA());
		
		if(entity.getTaAssure()!=null) {
			dto.setIdAssure(entity.getTaAssure().getIdAssure());
			dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
			dto.setRaisonSociale(entity.getTaAssure().getRaisonSociale());
			
			if(entity.getTaAssure().getTaContactEntreprise()!=null) {
//				dto.setIdContactEntreprise(entity.getTaAssure().getTaContactEntreprise().getIdContactEntreprise());
//				dto.setNom(entity.getTaAssure().getTaContactEntreprise().getNom());
//				dto.setPrenom(entity.getTaAssure().getTaContactEntreprise().getPrenom());
			}
		}
		
		
		if(entity.getTaEcheances()!=null && !entity.getTaEcheances().isEmpty()) {
			if(entity.getTaEcheances().get(0) != null) {
				if(entity.getTaEcheances().get(0).getTaReglementAssurance()!=null) {
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateEnvoiChequeParCourtier()!=null) {
						dto.setDateEnvoiChequeParCourtier(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateEnvoiChequeParCourtier());
					}
					
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateReceptionCheque()!=null) {
						dto.setDateReceptionCheque(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateReceptionCheque());
					}
					
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateDepotCheque()!=null) {
						dto.setDateDepotCheque(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateDepotCheque());
					}
					
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateEncaissementCheque()!=null) {
						dto.setDateEncaissementCheque(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateEncaissementCheque());
					}
					
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateVirementEffectue()!=null) {
						dto.setDateVirementEffectue(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateVirementEffectue());
					}
					
					if(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateVirementRecu()!=null) {
						dto.setDateVirementRecu(entity.getTaEcheances().get(0).getTaReglementAssurance().getDateVirementRecu());
					}
				}
			}
		}
//		if(entity.getTaContratRcPro()!=null) {
//			dto.setIdContratRcPro(entity.getTaContratRcPro().getIdContratRcPro());
//			dto.setNumPolice(entity.getTaContratRcPro().getNumPolice());
//		}
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
