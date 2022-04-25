package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaContratDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaContratDommageOuvrageMapper implements ILgrMapper<TaContratDommageOuvrageDTO, TaContratDommageOuvrage> {

	@Override
	public TaContratDommageOuvrage mapDtoToEntity(TaContratDommageOuvrageDTO dto, TaContratDommageOuvrage entity) {
		if(entity==null)
			entity = new TaContratDommageOuvrage();

		entity.setIdContratDommageOuvrage(dto.getId()!=null?dto.getId():0);
		
		entity.setNumPolice(dto.getNumPolice());
		entity.setTauxCommissionCourtier(dto.getTauxCommissionCourtier());
		entity.setMontantCommissionCourtier(dto.getMontantCommissionCourtier());
		entity.setFraisCourtier(dto.getFraisCourtier());
		entity.setTauxDommageOuvrage(dto.getTauxDommageOuvrage());
		entity.setPrimeassuranceNue(dto.getPrimeassuranceNue());
		entity.setTauxTaxeFiscale(dto.getTauxTaxeFiscale());
		entity.setValeurTaxeAttentat(dto.getValeurTaxeAttentat());
		entity.setNumClient(dto.getNumClient());
		entity.setNomProposant(dto.getNomProposant());
		entity.setPrenomProposant(dto.getPrenomProposant());
		entity.setQualiteProposant(dto.getQualiteProposant());
		entity.setAdresseProposant(dto.getAdresseProposant());
		entity.setNomMaitreOuvrage(dto.getNomMaitreOuvrage());
		entity.setPrenomMaitreOuvrage(dto.getPrenomMaitreOuvrage());
		entity.setAdresseMaitreOuvrage(dto.getAdresseMaitreOuvrage());
		entity.setNbBatiments(dto.getNbBatiments());
		entity.setNbLogements(dto.getNbLogements());
		entity.setNbSousSols(dto.getNbSousSols());
		entity.setNbEtages(dto.getNbEtages());
		entity.setCnr(dto.getCnr());
		entity.setDateOuvertureChantier(dto.getDateOuvertureChantier());
		entity.setDateReceptionChantier(dto.getDateReceptionChantier());
		entity.setCoutTotalConstruction(dto.getCoutTotalConstruction());
		entity.setNumPermisConstruire(dto.getNumPermisConstruire());
		entity.setDescriptionOperation(dto.getDescriptionOperation());
		entity.setDateSouscription(dto.getDateSouscription());
		entity.setImgContratDommageOuvrage(dto.getImgContratDommageOuvrage());
		entity.setContratactif(dto.getContratactif());
		entity.setRetractation(dto.getRetractation());
		entity.setDateRetractation(dto.getDateRetractation());
		entity.setTaProjetDommageOuvrage(dto.getTaProjetDommageOuvrage());
		entity.setTaEcheance(dto.getTaEcheance());
		
		entity.setDateFinContrat(dto.getDateFinContrat());
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		
		if(entity.getTaAssure()!=null) {
			entity.getTaAssure().setIdAssure(dto.getIdAssure());
			entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
		}
		
		
		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaContratDommageOuvrageDTO mapEntityToDto(TaContratDommageOuvrage entity, TaContratDommageOuvrageDTO dto) {
		if(dto==null)
			dto = new TaContratDommageOuvrageDTO();

		dto.setId(entity.getIdContratDommageOuvrage());
		
		dto.setNumPolice(entity.getNumPolice());
		dto.setTauxCommissionCourtier(entity.getTauxCommissionCourtier());
		dto.setMontantCommissionCourtier(entity.getMontantCommissionCourtier());
		dto.setFraisCourtier(entity.getFraisCourtier());
		dto.setTauxDommageOuvrage(entity.getTauxDommageOuvrage());
		dto.setPrimeassuranceNue(entity.getPrimeassuranceNue());
		dto.setTauxTaxeFiscale(entity.getTauxTaxeFiscale());
		dto.setValeurTaxeAttentat(entity.getValeurTaxeAttentat());
		dto.setNumClient(entity.getNumClient());
		dto.setNomProposant(entity.getNomProposant());
		dto.setPrenomProposant(entity.getPrenomProposant());
		dto.setQualiteProposant(entity.getQualiteProposant());
		dto.setAdresseProposant(entity.getAdresseProposant());
		dto.setNomMaitreOuvrage(entity.getNomMaitreOuvrage());
		dto.setPrenomMaitreOuvrage(entity.getPrenomMaitreOuvrage());
		dto.setAdresseMaitreOuvrage(entity.getAdresseMaitreOuvrage());
		dto.setNbBatiments(entity.getNbBatiments());
		dto.setNbLogements(entity.getNbLogements());
		dto.setNbSousSols(entity.getNbSousSols());
		dto.setNbEtages(entity.getNbEtages());
		dto.setCnr(entity.getCnr());
		dto.setDateOuvertureChantier(entity.getDateOuvertureChantier());
		dto.setDateReceptionChantier(entity.getDateReceptionChantier());
		dto.setCoutTotalConstruction(entity.getCoutTotalConstruction());
		dto.setNumPermisConstruire(entity.getNumPermisConstruire());
		dto.setDescriptionOperation(entity.getDescriptionOperation());
		dto.setDateSouscription(entity.getDateSouscription());
		dto.setImgContratDommageOuvrage(entity.getImgContratDommageOuvrage());
		dto.setContratactif(entity.getContratactif());
		dto.setRetractation(entity.getRetractation());
		dto.setDateRetractation(entity.getDateRetractation());
		dto.setTaProjetDommageOuvrage(entity.getTaProjetDommageOuvrage());
		dto.setTaEcheance(entity.getTaEcheance());
		
		dto.setDateFinContrat(entity.getDateFinContrat());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		if(entity.getTaAssure()!=null) {
			dto.setIdAssure(entity.getTaAssure().getIdAssure());
			dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
		}
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
