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
import fr.ylyade.courtage.dao.ITaTGarantieSinistreDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTGarantieSinistreDTO;
import fr.ylyade.courtage.model.TaTGarantieSinistre;
import fr.ylyade.courtage.model.TaTGarantieSinistre;
import fr.ylyade.courtage.model.mapper.TaTGarantieSinistreMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTGarantieSinistreServiceRemote;


/**
 * Session Bean implementation class TaTGarantieSinistreBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTGarantieSinistreService extends AbstractApplicationDAOServer<TaTGarantieSinistre, TaTGarantieSinistreDTO> implements ITaTGarantieSinistreServiceRemote {

	static Logger logger = Logger.getLogger(TaTGarantieSinistreService.class);

	@Inject private ITaTGarantieSinistreDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTGarantieSinistreService() {
		super(TaTGarantieSinistre.class,TaTGarantieSinistreDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTGarantieSinistre a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTGarantieSinistre transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTGarantieSinistre transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTGarantieSinistre persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTGarantieSinistre()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTGarantieSinistre merge(TaTGarantieSinistre detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTGarantieSinistre merge(TaTGarantieSinistre detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTGarantieSinistre findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTGarantieSinistre findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTGarantieSinistre> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTGarantieSinistreDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTGarantieSinistreDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTGarantieSinistre> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTGarantieSinistreDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTGarantieSinistreDTO entityToDTO(TaTGarantieSinistre entity) {
//		TaTGarantieSinistreDTO dto = new TaTGarantieSinistreDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTGarantieSinistreDTO> listEntityToListDTO(List<TaTGarantieSinistre> entity) {
		List<TaTGarantieSinistreDTO> l = new ArrayList<TaTGarantieSinistreDTO>();

		for (TaTGarantieSinistre taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTGarantieSinistreDTO> selectAllDTO() {
		System.out.println("List of TaTGarantieSinistreDTO EJB :");
		ArrayList<TaTGarantieSinistreDTO> liste = new ArrayList<TaTGarantieSinistreDTO>();

		List<TaTGarantieSinistre> projects = selectAll();
		for(TaTGarantieSinistre project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTGarantieSinistreDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTGarantieSinistreDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTGarantieSinistreDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTGarantieSinistreDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTGarantieSinistreDTO dto, String validationContext) throws EJBException {
		try {
			TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
			TaTGarantieSinistre entity = null;
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
	
	public void persistDTO(TaTGarantieSinistreDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTGarantieSinistreDTO dto, String validationContext) throws CreateException {
		try {
			TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
			TaTGarantieSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTGarantieSinistreDTO dto) throws RemoveException {
		try {
			TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
			TaTGarantieSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTGarantieSinistre refresh(TaTGarantieSinistre persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTGarantieSinistre value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTGarantieSinistre value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTGarantieSinistreDTO dto, String validationContext) {
		try {
			TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
			TaTGarantieSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGarantieSinistreDTO> validator = new BeanValidator<TaTGarantieSinistreDTO>(TaTGarantieSinistreDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTGarantieSinistreDTO dto, String propertyName, String validationContext) {
		try {
			TaTGarantieSinistreMapper mapper = new TaTGarantieSinistreMapper();
			TaTGarantieSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTGarantieSinistreDTO> validator = new BeanValidator<TaTGarantieSinistreDTO>(TaTGarantieSinistreDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTGarantieSinistreDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTGarantieSinistreDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTGarantieSinistre value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTGarantieSinistre value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
