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
import fr.ylyade.courtage.dao.ITaGedSinistreDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaGedSinistreDTO;
import fr.ylyade.courtage.model.TaGedSinistre;
import fr.ylyade.courtage.model.mapper.TaGedSinistreMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaGedSinistreServiceRemote;


/**
 * Session Bean implementation class TaGedSinistreBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaGedSinistreService extends AbstractApplicationDAOServer<TaGedSinistre, TaGedSinistreDTO> implements ITaGedSinistreServiceRemote {

	static Logger logger = Logger.getLogger(TaGedSinistreService.class);

	@Inject private ITaGedSinistreDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaGedSinistreService() {
		super(TaGedSinistre.class,TaGedSinistreDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaGedSinistre a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaGedSinistre transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaGedSinistre transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaGedSinistre persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdGedSinistre()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaGedSinistre merge(TaGedSinistre detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaGedSinistre merge(TaGedSinistre detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaGedSinistre findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaGedSinistre findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaGedSinistre> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaGedSinistreDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaGedSinistreDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaGedSinistre> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaGedSinistreDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaGedSinistreDTO entityToDTO(TaGedSinistre entity) {
//		TaGedSinistreDTO dto = new TaGedSinistreDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaGedSinistreMapper mapper = new TaGedSinistreMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaGedSinistreDTO> listEntityToListDTO(List<TaGedSinistre> entity) {
		List<TaGedSinistreDTO> l = new ArrayList<TaGedSinistreDTO>();

		for (TaGedSinistre taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaGedSinistreDTO> selectAllDTO() {
		System.out.println("List of TaGedSinistreDTO EJB :");
		ArrayList<TaGedSinistreDTO> liste = new ArrayList<TaGedSinistreDTO>();

		List<TaGedSinistre> projects = selectAll();
		for(TaGedSinistre project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaGedSinistreDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaGedSinistreDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaGedSinistreDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaGedSinistreDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaGedSinistreDTO dto, String validationContext) throws EJBException {
		try {
			TaGedSinistreMapper mapper = new TaGedSinistreMapper();
			TaGedSinistre entity = null;
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
	
	public void persistDTO(TaGedSinistreDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaGedSinistreDTO dto, String validationContext) throws CreateException {
		try {
			TaGedSinistreMapper mapper = new TaGedSinistreMapper();
			TaGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaGedSinistreDTO dto) throws RemoveException {
		try {
			TaGedSinistreMapper mapper = new TaGedSinistreMapper();
			TaGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaGedSinistre refresh(TaGedSinistre persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaGedSinistre value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaGedSinistre value, String propertyName, String validationContext) {
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
	public void validateDTO(TaGedSinistreDTO dto, String validationContext) {
		try {
			TaGedSinistreMapper mapper = new TaGedSinistreMapper();
			TaGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaGedSinistreDTO> validator = new BeanValidator<TaGedSinistreDTO>(TaGedSinistreDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaGedSinistreDTO dto, String propertyName, String validationContext) {
		try {
			TaGedSinistreMapper mapper = new TaGedSinistreMapper();
			TaGedSinistre entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaGedSinistreDTO> validator = new BeanValidator<TaGedSinistreDTO>(TaGedSinistreDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaGedSinistreDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaGedSinistreDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaGedSinistre value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaGedSinistre value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
