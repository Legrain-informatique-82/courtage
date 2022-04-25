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
import fr.ylyade.courtage.dao.ITaTAdresseDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTAdresseDTO;
import fr.ylyade.courtage.model.TaTAdresse;
import fr.ylyade.courtage.model.mapper.TaTAdresseMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTAdresseServiceRemote;


/**
 * Session Bean implementation class TaTAdresseBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTAdresseService extends AbstractApplicationDAOServer<TaTAdresse, TaTAdresseDTO> implements ITaTAdresseServiceRemote {

	static Logger logger = Logger.getLogger(TaTAdresseService.class);

	@Inject private ITaTAdresseDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTAdresseService() {
		super(TaTAdresse.class,TaTAdresseDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTAdresse a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTAdresse transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTAdresse transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTAdresse persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTAdresse()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTAdresse merge(TaTAdresse detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTAdresse merge(TaTAdresse detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTAdresse findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTAdresse findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTAdresse> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTAdresseDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTAdresseDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTAdresse> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTAdresseDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTAdresseDTO entityToDTO(TaTAdresse entity) {
//		TaTAdresseDTO dto = new TaTAdresseDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTAdresseMapper mapper = new TaTAdresseMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTAdresseDTO> listEntityToListDTO(List<TaTAdresse> entity) {
		List<TaTAdresseDTO> l = new ArrayList<TaTAdresseDTO>();

		for (TaTAdresse taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTAdresseDTO> selectAllDTO() {
		System.out.println("List of TaTAdresseDTO EJB :");
		ArrayList<TaTAdresseDTO> liste = new ArrayList<TaTAdresseDTO>();

		List<TaTAdresse> projects = selectAll();
		for(TaTAdresse project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTAdresseDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTAdresseDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTAdresseDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTAdresseDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTAdresseDTO dto, String validationContext) throws EJBException {
		try {
			TaTAdresseMapper mapper = new TaTAdresseMapper();
			TaTAdresse entity = null;
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
	
	public void persistDTO(TaTAdresseDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTAdresseDTO dto, String validationContext) throws CreateException {
		try {
			TaTAdresseMapper mapper = new TaTAdresseMapper();
			TaTAdresse entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTAdresseDTO dto) throws RemoveException {
		try {
			TaTAdresseMapper mapper = new TaTAdresseMapper();
			TaTAdresse entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTAdresse refresh(TaTAdresse persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTAdresse value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTAdresse value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTAdresseDTO dto, String validationContext) {
		try {
			TaTAdresseMapper mapper = new TaTAdresseMapper();
			TaTAdresse entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTAdresseDTO> validator = new BeanValidator<TaTAdresseDTO>(TaTAdresseDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTAdresseDTO dto, String propertyName, String validationContext) {
		try {
			TaTAdresseMapper mapper = new TaTAdresseMapper();
			TaTAdresse entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTAdresseDTO> validator = new BeanValidator<TaTAdresseDTO>(TaTAdresseDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTAdresseDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTAdresseDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTAdresse value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTAdresse value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
