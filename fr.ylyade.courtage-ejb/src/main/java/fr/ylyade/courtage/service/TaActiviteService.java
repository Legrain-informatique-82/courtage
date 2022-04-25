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
import fr.ylyade.courtage.dao.ITaActiviteDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaActiviteDTO;
import fr.ylyade.courtage.model.TaActivite;
import fr.ylyade.courtage.model.mapper.TaActiviteMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaActiviteServiceRemote;


/**
 * Session Bean implementation class TaActiviteBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaActiviteService extends AbstractApplicationDAOServer<TaActivite, TaActiviteDTO> implements ITaActiviteServiceRemote {

	static Logger logger = Logger.getLogger(TaActiviteService.class);

	@Inject private ITaActiviteDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaActiviteService() {
		super(TaActivite.class,TaActiviteDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaActivite a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaActivite transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaActivite transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaActivite persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdActivite()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaActivite merge(TaActivite detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaActivite merge(TaActivite detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaActivite findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaActivite findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaActivite> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaActiviteDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaActiviteDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaActivite> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaActiviteDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaActiviteDTO entityToDTO(TaActivite entity) {
//		TaActiviteDTO dto = new TaActiviteDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaActiviteMapper mapper = new TaActiviteMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaActiviteDTO> listEntityToListDTO(List<TaActivite> entity) {
		List<TaActiviteDTO> l = new ArrayList<TaActiviteDTO>();

		for (TaActivite taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaActiviteDTO> selectAllDTO() {
		System.out.println("List of TaActiviteDTO EJB :");
		ArrayList<TaActiviteDTO> liste = new ArrayList<TaActiviteDTO>();

		List<TaActivite> projects = selectAll();
		for(TaActivite project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaActiviteDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaActiviteDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaActiviteDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaActiviteDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaActiviteDTO dto, String validationContext) throws EJBException {
		try {
			TaActiviteMapper mapper = new TaActiviteMapper();
			TaActivite entity = null;
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
	
	public void persistDTO(TaActiviteDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaActiviteDTO dto, String validationContext) throws CreateException {
		try {
			TaActiviteMapper mapper = new TaActiviteMapper();
			TaActivite entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaActiviteDTO dto) throws RemoveException {
		try {
			TaActiviteMapper mapper = new TaActiviteMapper();
			TaActivite entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaActivite refresh(TaActivite persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaActivite value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaActivite value, String propertyName, String validationContext) {
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
	public void validateDTO(TaActiviteDTO dto, String validationContext) {
		try {
			TaActiviteMapper mapper = new TaActiviteMapper();
			TaActivite entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaActiviteDTO> validator = new BeanValidator<TaActiviteDTO>(TaActiviteDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaActiviteDTO dto, String propertyName, String validationContext) {
		try {
			TaActiviteMapper mapper = new TaActiviteMapper();
			TaActivite entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaActiviteDTO> validator = new BeanValidator<TaActiviteDTO>(TaActiviteDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaActiviteDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaActiviteDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaActivite value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaActivite value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
