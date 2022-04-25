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
import fr.ylyade.courtage.dao.ITaTTravauxDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTTravauxDTO;
import fr.ylyade.courtage.model.TaTTravaux;
import fr.ylyade.courtage.model.mapper.TaTTravauxMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTTravauxServiceRemote;


/**
 * Session Bean implementation class TaTTravauxBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTTravauxService extends AbstractApplicationDAOServer<TaTTravaux, TaTTravauxDTO> implements ITaTTravauxServiceRemote {

	static Logger logger = Logger.getLogger(TaTTravauxService.class);

	@Inject private ITaTTravauxDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTTravauxService() {
		super(TaTTravaux.class,TaTTravauxDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTTravaux a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTTravaux transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTTravaux transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTTravaux persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTTravaux()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTTravaux merge(TaTTravaux detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTTravaux merge(TaTTravaux detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTTravaux findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTTravaux findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTTravaux> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTTravauxDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTTravauxDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTTravaux> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTTravauxDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTTravauxDTO entityToDTO(TaTTravaux entity) {
//		TaTTravauxDTO dto = new TaTTravauxDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTTravauxMapper mapper = new TaTTravauxMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTTravauxDTO> listEntityToListDTO(List<TaTTravaux> entity) {
		List<TaTTravauxDTO> l = new ArrayList<TaTTravauxDTO>();

		for (TaTTravaux taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTTravauxDTO> selectAllDTO() {
		System.out.println("List of TaTTravauxDTO EJB :");
		ArrayList<TaTTravauxDTO> liste = new ArrayList<TaTTravauxDTO>();

		List<TaTTravaux> projects = selectAll();
		for(TaTTravaux project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTTravauxDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTTravauxDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTTravauxDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTTravauxDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTTravauxDTO dto, String validationContext) throws EJBException {
		try {
			TaTTravauxMapper mapper = new TaTTravauxMapper();
			TaTTravaux entity = null;
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
	
	public void persistDTO(TaTTravauxDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTTravauxDTO dto, String validationContext) throws CreateException {
		try {
			TaTTravauxMapper mapper = new TaTTravauxMapper();
			TaTTravaux entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTTravauxDTO dto) throws RemoveException {
		try {
			TaTTravauxMapper mapper = new TaTTravauxMapper();
			TaTTravaux entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTTravaux refresh(TaTTravaux persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTTravaux value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTTravaux value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTTravauxDTO dto, String validationContext) {
		try {
			TaTTravauxMapper mapper = new TaTTravauxMapper();
			TaTTravaux entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTTravauxDTO> validator = new BeanValidator<TaTTravauxDTO>(TaTTravauxDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTTravauxDTO dto, String propertyName, String validationContext) {
		try {
			TaTTravauxMapper mapper = new TaTTravauxMapper();
			TaTTravaux entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTTravauxDTO> validator = new BeanValidator<TaTTravauxDTO>(TaTTravauxDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTTravauxDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTTravauxDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTTravaux value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTTravaux value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
