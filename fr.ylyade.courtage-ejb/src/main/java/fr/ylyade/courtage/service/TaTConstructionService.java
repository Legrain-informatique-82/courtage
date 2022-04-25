package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaTConstructionDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTConstructionDTO;
import fr.ylyade.courtage.model.TaTConstruction;
import fr.ylyade.courtage.model.mapper.TaTConstructionMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTConstructionServiceRemote;


/**
 * Session Bean implementation class TaTConstructionBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTConstructionService extends AbstractApplicationDAOServer<TaTConstruction, TaTConstructionDTO> implements ITaTConstructionServiceRemote {

	static Logger logger = Logger.getLogger(TaTConstructionService.class);

	@Inject private ITaTConstructionDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTConstructionService() {
		super(TaTConstruction.class,TaTConstructionDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTConstruction a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTConstruction transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTConstruction transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTConstruction persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTConstruction()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTConstruction merge(TaTConstruction detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTConstruction merge(TaTConstruction detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTConstruction findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTConstruction findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTConstruction> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTConstructionDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTConstructionDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTConstruction> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTConstructionDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTConstructionDTO entityToDTO(TaTConstruction entity) {
//		TaTConstructionDTO dto = new TaTConstructionDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTConstructionMapper mapper = new TaTConstructionMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTConstructionDTO> listEntityToListDTO(List<TaTConstruction> entity) {
		List<TaTConstructionDTO> l = new ArrayList<TaTConstructionDTO>();

		for (TaTConstruction taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTConstructionDTO> selectAllDTO() {
		System.out.println("List of TaTConstructionDTO EJB :");
		ArrayList<TaTConstructionDTO> liste = new ArrayList<TaTConstructionDTO>();

		List<TaTConstruction> projects = selectAll();
		for(TaTConstruction project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTConstructionDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTConstructionDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTConstructionDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTConstructionDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTConstructionDTO dto, String validationContext) throws EJBException {
		try {
			TaTConstructionMapper mapper = new TaTConstructionMapper();
			TaTConstruction entity = null;
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
	
	public void persistDTO(TaTConstructionDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTConstructionDTO dto, String validationContext) throws CreateException {
		try {
			TaTConstructionMapper mapper = new TaTConstructionMapper();
			TaTConstruction entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTConstructionDTO dto) throws RemoveException {
		try {
			TaTConstructionMapper mapper = new TaTConstructionMapper();
			TaTConstruction entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTConstruction refresh(TaTConstruction persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTConstruction value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTConstruction value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTConstructionDTO dto, String validationContext) {
		try {
			TaTConstructionMapper mapper = new TaTConstructionMapper();
			TaTConstruction entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTConstructionDTO> validator = new BeanValidator<TaTConstructionDTO>(TaTConstructionDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTConstructionDTO dto, String propertyName, String validationContext) {
		try {
			TaTConstructionMapper mapper = new TaTConstructionMapper();
			TaTConstruction entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTConstructionDTO> validator = new BeanValidator<TaTConstructionDTO>(TaTConstructionDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTConstructionDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTConstructionDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTConstruction value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTConstruction value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
