package fr.ylyade.courtage.model.mapper;

import java.math.BigDecimal;

import fr.ylyade.courtage.dto.TaCourtierDTO;
import fr.ylyade.courtage.model.TaCourtier;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaCourtierMapper implements ILgrMapper<TaCourtierDTO, TaCourtier> {

	@Override
	public TaCourtier mapDtoToEntity(TaCourtierDTO dto, TaCourtier entity) {
		if(entity==null)
			entity = new TaCourtier();

		entity.setIdCourtier(dto.getId()!=null?dto.getId():0);
		entity.setCodeCourtier(dto.getCodeCourtier());
//		entity.setTitreFonction(dto.getTitreFonction());
		entity.setCodeSiret(dto.getCodeSiret());
		entity.setCodeSiren(dto.getCodeSiren());
		entity.setCodeApe(dto.getCodeApe());
		entity.setTvaIntraComm(dto.getTvaIntraComm());
		entity.setLogo(dto.getLogo());
		entity.setQualite(dto.getQualite());
		entity.setNumOrias(dto.getNumOrias());
		entity.setNomDenominationSociale(dto.getNomDenominationSociale());
		entity.setNumRcs(dto.getNumRcs());
		entity.setNumRcPro(dto.getNumRcPro());
		entity.setNumGreffe(dto.getNumGreffe());
		entity.setCapitalSocial(dto.getCapitalSocial());
		entity.setActif(dto.getActif());
		entity.setSuspendu(dto.getSuspendu());
		entity.setEffectifTotal(dto.getEffectifTotal());
		entity.setDateAccordTarif(dto.getDateAccordTarif());
		entity.setComBrutes(dto.getComBrutes());
		entity.setComBrutesIard(dto.getComBrutesIard());
		entity.setConnuYlyade(dto.getConnuYlyade());
		entity.setCaIardPourcentEntreprise(dto.getCaIardPourcentEntreprise());
		entity.setCaIardPourcentParticulier(dto.getCaIardPourcentParticulier());
		
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());

		if(entity.getTaTGroupeTarif()!=null) {
			entity.getTaTGroupeTarif().setIdTGroupeTarif(dto.getIdTGroupeTarif());
			entity.getTaTGroupeTarif().setCodeGroupe(dto.getCodeGroupe());
			entity.getTaTGroupeTarif().setNomGroupe(dto.getNomGroupe());
			entity.getTaTGroupeTarif().setTauxGroupe(dto.getTauxGroupe());
		}

		
		if(entity.getTaUtilisateur()!=null) entity.getTaUtilisateur().setIdentifiant(dto.getIdentifiant());
		
//		if(entity.getTaTCivilite()!=null) entity.getTaTCivilite().setIdTCivilite(dto.getIdTCivilite());
//		if(entity.getTaTCivilite()!=null) entity.getTaTCivilite().setCodeTCivilite(dto.getCodeTCivilite());
//		if(entity.getTaTCivilite()!=null) entity.getTaTCivilite().setLiblTCivilite(dto.getLiblTCivilite());
		
		if(entity.getTaTCourtier()!=null) entity.getTaTCourtier().setIdTCourtier(dto.getIdTCourtier());
		if(entity.getTaTCourtier()!=null) entity.getTaTCourtier().setCodeTCourtier(dto.getCodeTCourtier());
		if(entity.getTaTCourtier()!=null) entity.getTaTCourtier().setLiblTCourtier(dto.getLiblTCourtier());
		
		if(entity.getTaTJuridique()!=null) entity.getTaTJuridique().setIdTJuridique(dto.getIdTJuridique());
		if(entity.getTaTJuridique()!=null) entity.getTaTJuridique().setCodeTJuridique(dto.getCodeTJuridique());
		if(entity.getTaTJuridique()!=null) entity.getTaTJuridique().setLiblTJuridique(dto.getLiblTJuridique());

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaCourtierDTO mapEntityToDto(TaCourtier entity, TaCourtierDTO dto) {
		if(dto==null)
			dto = new TaCourtierDTO();

		dto.setId(entity.getIdCourtier());
		dto.setCodeCourtier(entity.getCodeCourtier());
//		dto.setTitreFonction(entity.getTitreFonction());
		dto.setCodeSiret(entity.getCodeSiret());
		dto.setCodeSiren(entity.getCodeSiren());
		dto.setCodeApe(entity.getCodeApe());
		dto.setTvaIntraComm(entity.getTvaIntraComm());
		dto.setLogo(entity.getLogo());
		dto.setQualite(entity.getQualite());
		dto.setNumOrias(entity.getNumOrias());
		dto.setNumGreffe(entity.getNumGreffe());
		dto.setNomDenominationSociale(entity.getNomDenominationSociale());
		dto.setNumRcs(entity.getNumRcs());
		dto.setNumRcPro(entity.getNumRcPro());
		dto.setCapitalSocial(entity.getCapitalSocial());
		dto.setActif(entity.getActif());
		dto.setSuspendu(entity.getSuspendu());
		dto.setEffectifTotal(entity.getEffectifTotal());
		dto.setDateAccordTarif(entity.getDateAccordTarif());
		dto.setComBrutes(entity.getComBrutes());
		dto.setComBrutesIard(entity.getComBrutesIard());
		dto.setConnuYlyade(entity.getConnuYlyade());
		dto.setCaIardPourcentEntreprise(entity.getCaIardPourcentEntreprise());
		dto.setCaIardPourcentParticulier(entity.getCaIardPourcentParticulier());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		if(entity.getTaTGroupeTarif()!=null) dto.setIdTGroupeTarif(entity.getTaTGroupeTarif().getIdTGroupeTarif());
		if(entity.getTaTGroupeTarif()!=null) dto.setCodeGroupe(entity.getTaTGroupeTarif().getCodeGroupe());
		if(entity.getTaTGroupeTarif()!=null) dto.setNomGroupe(entity.getTaTGroupeTarif().getNomGroupe());
		if(entity.getTaTGroupeTarif()!=null) dto.setTauxGroupe(entity.getTaTGroupeTarif().getTauxGroupe());
		
		if(entity.getTaUtilisateur()!=null) dto.setIdentifiant(entity.getTaUtilisateur().getIdentifiant());
		
//		if(entity.getTaTCivilite()!=null) dto.setIdTCivilite(entity.getTaTCivilite().getIdTCivilite());
//		if(entity.getTaTCivilite()!=null) dto.setCodeTCivilite(entity.getTaTCivilite().getCodeTCivilite());
//		if(entity.getTaTCivilite()!=null) dto.setLiblTCivilite(entity.getTaTCivilite().getLiblTCivilite());
		
		if(entity.getTaTCourtier()!=null) dto.setIdTCourtier(entity.getTaTCourtier().getIdTCourtier());
		if(entity.getTaTCourtier()!=null) dto.setCodeTCourtier(entity.getTaTCourtier().getCodeTCourtier());
		if(entity.getTaTCourtier()!=null) dto.setLiblTCourtier(entity.getTaTCourtier().getLiblTCourtier());
		
		if(entity.getTaTJuridique()!=null) dto.setIdTJuridique(entity.getTaTJuridique().getIdTJuridique());
		if(entity.getTaTJuridique()!=null) dto.setCodeTJuridique(entity.getTaTJuridique().getCodeTJuridique());
		if(entity.getTaTJuridique()!=null) dto.setLiblTJuridique(entity.getTaTJuridique().getLiblTJuridique());
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
