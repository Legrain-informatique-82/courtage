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
import fr.ylyade.courtage.dao.ITaContratGfaDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaContratGfaDTO;
import fr.ylyade.courtage.model.TaContratGfa;
import fr.ylyade.courtage.model.mapper.TaContratGfaMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaContratGfaServiceRemote;


/**
 * Session Bean implementation class TaContratGfaBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaContratGfaService extends AbstractApplicationDAOServer<TaContratGfa, TaContratGfaDTO> implements ITaContratGfaServiceRemote {

	static Logger logger = Logger.getLogger(TaContratGfaService.class);

	@Inject private ITaContratGfaDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaContratGfaService() {
		super(TaContratGfa.class,TaContratGfaDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaContratGfa a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Integer countActiveByIdCourtier(int idCourtier) {
		return dao.countActiveByIdCourtier(idCourtier);
	}
	
	public void persist(TaContratGfa transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaContratGfa transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaContratGfa persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdContratGfa()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaContratGfa merge(TaContratGfa detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaContratGfa merge(TaContratGfa detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaContratGfa findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaContratGfa findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaContratGfa> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaContratGfaDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaContratGfaDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaContratGfa> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaContratGfaDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaContratGfaDTO entityToDTO(TaContratGfa entity) {
//		TaContratGfaDTO dto = new TaContratGfaDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaContratGfaMapper mapper = new TaContratGfaMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaContratGfaDTO> listEntityToListDTO(List<TaContratGfa> entity) {
		List<TaContratGfaDTO> l = new ArrayList<TaContratGfaDTO>();

		for (TaContratGfa taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaContratGfaDTO> selectAllDTO() {
		System.out.println("List of TaContratGfaDTO EJB :");
		ArrayList<TaContratGfaDTO> liste = new ArrayList<TaContratGfaDTO>();

		List<TaContratGfa> projects = selectAll();
		for(TaContratGfa project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaContratGfaDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaContratGfaDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaContratGfaDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaContratGfaDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaContratGfaDTO dto, String validationContext) throws EJBException {
		try {
			TaContratGfaMapper mapper = new TaContratGfaMapper();
			TaContratGfa entity = null;
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
	
	public void persistDTO(TaContratGfaDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaContratGfaDTO dto, String validationContext) throws CreateException {
		try {
			TaContratGfaMapper mapper = new TaContratGfaMapper();
			TaContratGfa entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaContratGfaDTO dto) throws RemoveException {
		try {
			TaContratGfaMapper mapper = new TaContratGfaMapper();
			TaContratGfa entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaContratGfa refresh(TaContratGfa persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaContratGfa value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaContratGfa value, String propertyName, String validationContext) {
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
	public void validateDTO(TaContratGfaDTO dto, String validationContext) {
		try {
			TaContratGfaMapper mapper = new TaContratGfaMapper();
			TaContratGfa entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContratGfaDTO> validator = new BeanValidator<TaContratGfaDTO>(TaContratGfaDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaContratGfaDTO dto, String propertyName, String validationContext) {
		try {
			TaContratGfaMapper mapper = new TaContratGfaMapper();
			TaContratGfa entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContratGfaDTO> validator = new BeanValidator<TaContratGfaDTO>(TaContratGfaDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaContratGfaDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaContratGfaDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaContratGfa value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaContratGfa value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
