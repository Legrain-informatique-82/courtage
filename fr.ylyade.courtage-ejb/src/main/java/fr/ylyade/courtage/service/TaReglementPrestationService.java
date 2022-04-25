package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaReglementPrestationDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaReglementPrestationDTO;
import fr.ylyade.courtage.model.TaReglementAssurance;
import fr.ylyade.courtage.model.TaReglementPrestation;
import fr.ylyade.courtage.model.mapper.TaReglementPrestationMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaReglementPrestationServiceRemote;


/**
 * Session Bean implementation class TaReglementPrestationBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaReglementPrestationService extends AbstractApplicationDAOServer<TaReglementPrestation, TaReglementPrestationDTO> implements ITaReglementPrestationServiceRemote {

	static Logger logger = Logger.getLogger(TaReglementPrestationService.class);

	@Inject private ITaReglementPrestationDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;

	/**
	 * Default constructor. 
	 */
	public TaReglementPrestationService() {
		super(TaReglementPrestation.class,TaReglementPrestationDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaReglementPrestation a";
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaReglementPrestation.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaReglementPrestation transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaReglementPrestation transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaReglementPrestation persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdReglementPrestation()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaReglementPrestation merge(TaReglementPrestation detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaReglementPrestation merge(TaReglementPrestation detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaReglementPrestation findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaReglementPrestation findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaReglementPrestation> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaReglementPrestationDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaReglementPrestationDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaReglementPrestation> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaReglementPrestationDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaReglementPrestationDTO entityToDTO(TaReglementPrestation entity) {
//		TaReglementPrestationDTO dto = new TaReglementPrestationDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaReglementPrestationDTO> listEntityToListDTO(List<TaReglementPrestation> entity) {
		List<TaReglementPrestationDTO> l = new ArrayList<TaReglementPrestationDTO>();

		for (TaReglementPrestation taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaReglementPrestationDTO> selectAllDTO() {
		System.out.println("List of TaReglementPrestationDTO EJB :");
		ArrayList<TaReglementPrestationDTO> liste = new ArrayList<TaReglementPrestationDTO>();

		List<TaReglementPrestation> projects = selectAll();
		for(TaReglementPrestation project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaReglementPrestationDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaReglementPrestationDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaReglementPrestationDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaReglementPrestationDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaReglementPrestationDTO dto, String validationContext) throws EJBException {
		try {
			TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
			TaReglementPrestation entity = null;
			if(dto.getId()!=null) {
				entity = dao.findById(dto.getId());
				if(dto.getVersionObj()!=entity.getVersionObj()) {
					throw new OptimisticLockException(entity,
							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getId()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
				} else {
					 entity = mapper.mapDtoToEntity(dto,entity);
				}
			}
			
			//dao.merge(entity);
			dao.detach(entity); //pour passer les controles
			enregistrerMerge(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new CreateException(e.getMessage());
			throw new EJBException(e.getMessage());
		}
	}
	
	public void persistDTO(TaReglementPrestationDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaReglementPrestationDTO dto, String validationContext) throws CreateException {
		try {
			TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
			TaReglementPrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaReglementPrestationDTO dto) throws RemoveException {
		try {
			TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
			TaReglementPrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaReglementPrestation refresh(TaReglementPrestation persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaReglementPrestation value, String validationContext) /*throws ExceptLgr*/ {
		try {
			if(validationContext==null) validationContext="";
			validateAll(value,validationContext,false); //ancienne validation, extraction de l'annotation et appel
			//dao.validate(value); //validation automatique via la JSR bean validation
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateEntityPropertyValidationContext")
	public void validateEntityProperty(TaReglementPrestation value, String propertyName, String validationContext) {
		try {
			if(validationContext==null) validationContext="";
			validate(value, propertyName, validationContext);
			//dao.validateField(value,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOValidationContext")
	public void validateDTO(TaReglementPrestationDTO dto, String validationContext) {
		try {
			TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
			TaReglementPrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaReglementPrestationDTO> validator = new BeanValidator<TaReglementPrestationDTO>(TaReglementPrestationDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaReglementPrestationDTO dto, String propertyName, String validationContext) {
		try {
			TaReglementPrestationMapper mapper = new TaReglementPrestationMapper();
			TaReglementPrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaReglementPrestationDTO> validator = new BeanValidator<TaReglementPrestationDTO>(TaReglementPrestationDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaReglementPrestationDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaReglementPrestationDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaReglementPrestation value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaReglementPrestation value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
