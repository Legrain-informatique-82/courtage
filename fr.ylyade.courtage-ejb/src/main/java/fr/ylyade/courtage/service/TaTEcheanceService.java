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
import fr.ylyade.courtage.dao.ITaTEcheanceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTEcheanceDTO;
import fr.ylyade.courtage.model.TaTEcheance;
import fr.ylyade.courtage.model.mapper.TaTEcheanceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTEcheanceServiceRemote;


/**
 * Session Bean implementation class TaTEcheanceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTEcheanceService extends AbstractApplicationDAOServer<TaTEcheance, TaTEcheanceDTO> implements ITaTEcheanceServiceRemote {

	static Logger logger = Logger.getLogger(TaTEcheanceService.class);

	@Inject private ITaTEcheanceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTEcheanceService() {
		super(TaTEcheance.class,TaTEcheanceDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTEcheance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTEcheance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTEcheance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTEcheance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTEcheance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTEcheance merge(TaTEcheance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTEcheance merge(TaTEcheance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTEcheance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTEcheance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTEcheance> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTEcheanceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTEcheanceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTEcheance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTEcheanceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTEcheanceDTO entityToDTO(TaTEcheance entity) {
//		TaTEcheanceDTO dto = new TaTEcheanceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTEcheanceMapper mapper = new TaTEcheanceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTEcheanceDTO> listEntityToListDTO(List<TaTEcheance> entity) {
		List<TaTEcheanceDTO> l = new ArrayList<TaTEcheanceDTO>();

		for (TaTEcheance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTEcheanceDTO> selectAllDTO() {
		System.out.println("List of TaTEcheanceDTO EJB :");
		ArrayList<TaTEcheanceDTO> liste = new ArrayList<TaTEcheanceDTO>();

		List<TaTEcheance> projects = selectAll();
		for(TaTEcheance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTEcheanceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTEcheanceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTEcheanceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTEcheanceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTEcheanceDTO dto, String validationContext) throws EJBException {
		try {
			TaTEcheanceMapper mapper = new TaTEcheanceMapper();
			TaTEcheance entity = null;
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
	
	public void persistDTO(TaTEcheanceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTEcheanceDTO dto, String validationContext) throws CreateException {
		try {
			TaTEcheanceMapper mapper = new TaTEcheanceMapper();
			TaTEcheance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTEcheanceDTO dto) throws RemoveException {
		try {
			TaTEcheanceMapper mapper = new TaTEcheanceMapper();
			TaTEcheance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTEcheance refresh(TaTEcheance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTEcheance value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTEcheance value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTEcheanceDTO dto, String validationContext) {
		try {
			TaTEcheanceMapper mapper = new TaTEcheanceMapper();
			TaTEcheance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTEcheanceDTO> validator = new BeanValidator<TaTEcheanceDTO>(TaTEcheanceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTEcheanceDTO dto, String propertyName, String validationContext) {
		try {
			TaTEcheanceMapper mapper = new TaTEcheanceMapper();
			TaTEcheance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTEcheanceDTO> validator = new BeanValidator<TaTEcheanceDTO>(TaTEcheanceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTEcheanceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTEcheanceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTEcheance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTEcheance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
