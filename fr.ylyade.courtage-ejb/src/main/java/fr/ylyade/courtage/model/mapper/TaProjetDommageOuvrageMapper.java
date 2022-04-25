package fr.ylyade.courtage.model.mapper;

import fr.ylyade.courtage.dto.TaProjetDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;
import fr.ylyade.courtage.model.mapping.ILgrMapper;


public class TaProjetDommageOuvrageMapper implements ILgrMapper<TaProjetDommageOuvrageDTO, TaProjetDommageOuvrage> {

	@Override
	public TaProjetDommageOuvrage mapDtoToEntity(TaProjetDommageOuvrageDTO dto, TaProjetDommageOuvrage entity) {
		if(entity==null)
			entity = new TaProjetDommageOuvrage();

		entity.setIdProjetDommageOuvrage(dto.getId()!=null?dto.getId():0);
		entity.setNumProjetDommageOuvrage(dto.getNumProjetDommageOuvrage());
		entity.setTauxCommissionCourtier(dto.getTauxCommissionCourtier());
		entity.setMontantCommissionCourtier(dto.getMontantCommissionCourtier());
		entity.setFraisCourtier(dto.getFraisCourtier());
		entity.setTauxDommageOuvrage(dto.getTauxDommageOuvrage());
		entity.setPrimeassuranceNue(dto.getPrimeassuranceNue());
		entity.setTauxTaxeFiscale(dto.getTauxTaxeFiscale());
		entity.setValeurTaxeAttentat(dto.getValeurTaxeAttentat());
		entity.setImgProjetDommageOuvrage(dto.getImgProjetDommageOuvrage());
		entity.setNumClient(dto.getNumClient());
		entity.setNomProposant(dto.getNomProposant());
		entity.setPrenomProposant(dto.getPrenomProposant());
		entity.setQualitePreposant(dto.getQualitePreposant());
		entity.setAdresseProposant(dto.getAdresseProposant());
		entity.setNomMaitreOuvrage(dto.getNomMaitreOuvrage());
		entity.setPrenomMaitreOuvrage(dto.getPrenomMaitreOuvrage());
		entity.setAdresseOperationConstruction(dto.getAdresseOperationConstruction());
		entity.setAdresseMaitreOuvrage(dto.getAdresseMaitreOuvrage());
		entity.setNbBatiments(dto.getNbBatiments());
		entity.setNbLogements(dto.getNbLogements());
		entity.setNbSousSols(dto.getNbSousSols());
		entity.setNbEtages(dto.getNbEtages());
		entity.setCnr(dto.getCnr());
		entity.setDateReceptionTravaux(dto.getDateReceptionTravaux());
		entity.setDateOuvertureChantier(dto.getDateOuvertureChantier());
		entity.setCoutTotalConstruction(dto.getCoutTotalConstruction());
		entity.setNumPermisConstruire(dto.getNumPermisConstruire());
		entity.setDescriptionOperation(dto.getDescriptionOperation());
		
		entity.setDateFinValiditeProjet(dto.getDateFinValiditeProjet());
		
		
		
		if(entity.getTaAssure()!=null) {
			entity.getTaAssure().setIdAssure(dto.getIdAssure());
			entity.getTaAssure().setCodeAssure(dto.getCodeAssure());
		}
			
		
		entity.setTaContratDommageOuvrage(dto.getTaContratDommageOuvrage());
		entity.setTaTAssurance(dto.getTaTAssurance());
		entity.setTaTConstruction(dto.getTaTConstruction());
		entity.setTaTTravaux(dto.getTaTTravaux());
		entity.setTaTConstruction(dto.getTaTConstruction());
		entity.setTaTDestinationUsage(dto.getTaTDestinationUsage());
		entity.setTaTMaitriseOeuvre(dto.getTaTMaitriseOeuvre());
		
		
		entity.setNomFichier(dto.getNomFichier());
		entity.setTaille(dto.getTaille());
		entity.setTypeMime(dto.getTypeMime());
		

		entity.setVersionObj(dto.getVersionObj());

		return entity;
	}

	@Override
	public TaProjetDommageOuvrageDTO mapEntityToDto(TaProjetDommageOuvrage entity, TaProjetDommageOuvrageDTO dto) {
		if(dto==null)
			dto = new TaProjetDommageOuvrageDTO();

		dto.setId(entity.getIdProjetDommageOuvrage());
		
		dto.setNumProjetDommageOuvrage(entity.getNumProjetDommageOuvrage());
		dto.setTauxCommissionCourtier(entity.getTauxCommissionCourtier());
		dto.setMontantCommissionCourtier(entity.getMontantCommissionCourtier());
		dto.setFraisCourtier(entity.getFraisCourtier());
		dto.setTauxDommageOuvrage(entity.getTauxDommageOuvrage());
		dto.setPrimeassuranceNue(entity.getPrimeassuranceNue());
		dto.setTauxTaxeFiscale(entity.getTauxTaxeFiscale());
		dto.setValeurTaxeAttentat(entity.getValeurTaxeAttentat());
		dto.setImgProjetDommageOuvrage(entity.getImgProjetDommageOuvrage());
		dto.setNumClient(entity.getNumClient());
		dto.setNomProposant(entity.getNomProposant());
		dto.setPrenomProposant(entity.getPrenomProposant());
		dto.setQualitePreposant(entity.getQualitePreposant());
		dto.setAdresseProposant(entity.getAdresseProposant());
		dto.setNomMaitreOuvrage(entity.getNomMaitreOuvrage());
		dto.setPrenomMaitreOuvrage(entity.getPrenomMaitreOuvrage());
		dto.setAdresseOperationConstruction(entity.getAdresseOperationConstruction());
		dto.setAdresseMaitreOuvrage(entity.getAdresseMaitreOuvrage());
		dto.setNbBatiments(entity.getNbBatiments());
		dto.setNbLogements(entity.getNbLogements());
		dto.setNbSousSols(entity.getNbSousSols());
		dto.setNbEtages(entity.getNbEtages());
		dto.setCnr(entity.getCnr());
		dto.setDateReceptionTravaux(entity.getDateReceptionTravaux());
		dto.setDateOuvertureChantier(entity.getDateOuvertureChantier());
		dto.setCoutTotalConstruction(entity.getCoutTotalConstruction());
		dto.setNumPermisConstruire(entity.getNumPermisConstruire());
		dto.setDescriptionOperation(entity.getDescriptionOperation());
		
		dto.setDateFinValiditeProjet(entity.getDateFinValiditeProjet());
		
		if(entity.getTaAssure()!=null) {
			dto.setIdAssure(entity.getTaAssure().getIdAssure());
			dto.setCodeAssure(entity.getTaAssure().getCodeAssure());
		}
		
		dto.setTaContratDommageOuvrage(entity.getTaContratDommageOuvrage());
		dto.setTaTAssurance(entity.getTaTAssurance());
		dto.setTaTConstruction(entity.getTaTConstruction());
		dto.setTaTTravaux(entity.getTaTTravaux());
		dto.setTaTConstruction(entity.getTaTConstruction());
		dto.setTaTDestinationUsage(entity.getTaTDestinationUsage());
		dto.setTaTMaitriseOeuvre(entity.getTaTMaitriseOeuvre());
		
		dto.setNomFichier(entity.getNomFichier());
		dto.setTaille(entity.getTaille());
		dto.setTypeMime(entity.getTypeMime());
		
		
		
		dto.setVersionObj(entity.getVersionObj());

		return dto;
	}

}
