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
import fr.ylyade.courtage.dao.ITaProjetGfaDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaProjetGfaDTO;
import fr.ylyade.courtage.model.TaProjetGfa;
import fr.ylyade.courtage.model.mapper.TaProjetGfaMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaProjetGfaServiceRemote;


/**
 * Session Bean implementation class TaProjetGfaBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaProjetGfaService extends AbstractApplicationDAOServer<TaProjetGfa, TaProjetGfaDTO> implements ITaProjetGfaServiceRemote {

	static Logger logger = Logger.getLogger(TaProjetGfaService.class);

	@Inject private ITaProjetGfaDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaProjetGfaService() {
		super(TaProjetGfa.class,TaProjetGfaDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaProjetGfa a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		return dao.countActiveByIdCourtier(idCourtier);
	}
	
	public void persist(TaProjetGfa transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaProjetGfa transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaProjetGfa persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdProjetGfa()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaProjetGfa merge(TaProjetGfa detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaProjetGfa merge(TaProjetGfa detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaProjetGfa findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaProjetGfa findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaProjetGfa> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaProjetGfaDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaProjetGfaDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaProjetGfa> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaProjetGfaDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaProjetGfaDTO entityToDTO(TaProjetGfa entity) {
//		TaProjetGfaDTO dto = new TaProjetGfaDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaProjetGfaMapper mapper = new TaProjetGfaMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaProjetGfaDTO> listEntityToListDTO(List<TaProjetGfa> entity) {
		List<TaProjetGfaDTO> l = new ArrayList<TaProjetGfaDTO>();

		for (TaProjetGfa taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaProjetGfaDTO> selectAllDTO() {
		System.out.println("List of TaProjetGfaDTO EJB :");
		ArrayList<TaProjetGfaDTO> liste = new ArrayList<TaProjetGfaDTO>();

		List<TaProjetGfa> projects = selectAll();
		for(TaProjetGfa project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaProjetGfaDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaProjetGfaDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaProjetGfaDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaProjetGfaDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaProjetGfaDTO dto, String validationContext) throws EJBException {
		try {
			TaProjetGfaMapper mapper = new TaProjetGfaMapper();
			TaProjetGfa entity = null;
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
	
	public void persistDTO(TaProjetGfaDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaProjetGfaDTO dto, String validationContext) throws CreateException {
		try {
			TaProjetGfaMapper mapper = new TaProjetGfaMapper();
			TaProjetGfa entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaProjetGfaDTO dto) throws RemoveException {
		try {
			TaProjetGfaMapper mapper = new TaProjetGfaMapper();
			TaProjetGfa entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaProjetGfa refresh(TaProjetGfa persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaProjetGfa value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaProjetGfa value, String propertyName, String validationContext) {
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
	public void validateDTO(TaProjetGfaDTO dto, String validationContext) {
		try {
			TaProjetGfaMapper mapper = new TaProjetGfaMapper();
			TaProjetGfa entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaProjetGfaDTO> validator = new BeanValidator<TaProjetGfaDTO>(TaProjetGfaDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaProjetGfaDTO dto, String propertyName, String validationContext) {
		try {
			TaProjetGfaMapper mapper = new TaProjetGfaMapper();
			TaProjetGfa entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaProjetGfaDTO> validator = new BeanValidator<TaProjetGfaDTO>(TaProjetGfaDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaProjetGfaDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaProjetGfaDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaProjetGfa value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaProjetGfa value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
