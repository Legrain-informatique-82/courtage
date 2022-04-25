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
import fr.ylyade.courtage.dao.ITaTSousTraitanceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTSousTraitanceDTO;
import fr.ylyade.courtage.model.TaTSousTraitance;
import fr.ylyade.courtage.model.mapper.TaTSousTraitanceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTSousTraitanceServiceRemote;



/**
 * Session Bean implementation class TaTSousTraitanceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTSousTraitanceService extends AbstractApplicationDAOServer<TaTSousTraitance, TaTSousTraitanceDTO> implements ITaTSousTraitanceServiceRemote {

	static Logger logger = Logger.getLogger(TaTSousTraitanceService.class);

	@Inject private ITaTSousTraitanceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTSousTraitanceService() {
		super(TaTSousTraitance.class,TaTSousTraitanceDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTSousTraitance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTSousTraitance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTSousTraitance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTSousTraitance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTSousTraitance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTSousTraitance merge(TaTSousTraitance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTSousTraitance merge(TaTSousTraitance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTSousTraitance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTSousTraitance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTSousTraitance> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTSousTraitanceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTSousTraitanceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTSousTraitance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTSousTraitanceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTSousTraitanceDTO entityToDTO(TaTSousTraitance entity) {
//		TaTSousTraitanceDTO dto = new TaTSousTraitanceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTSousTraitanceDTO> listEntityToListDTO(List<TaTSousTraitance> entity) {
		List<TaTSousTraitanceDTO> l = new ArrayList<TaTSousTraitanceDTO>();

		for (TaTSousTraitance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTSousTraitanceDTO> selectAllDTO() {
		System.out.println("List of TaTSousTraitanceDTO EJB :");
		ArrayList<TaTSousTraitanceDTO> liste = new ArrayList<TaTSousTraitanceDTO>();

		List<TaTSousTraitance> projects = selectAll();
		for(TaTSousTraitance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTSousTraitanceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTSousTraitanceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTSousTraitanceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTSousTraitanceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTSousTraitanceDTO dto, String validationContext) throws EJBException {
		try {
			TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
			TaTSousTraitance entity = null;
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
	
	public void persistDTO(TaTSousTraitanceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTSousTraitanceDTO dto, String validationContext) throws CreateException {
		try {
			TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
			TaTSousTraitance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTSousTraitanceDTO dto) throws RemoveException {
		try {
			TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
			TaTSousTraitance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTSousTraitance refresh(TaTSousTraitance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTSousTraitance value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTSousTraitance value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTSousTraitanceDTO dto, String validationContext) {
		try {
			TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
			TaTSousTraitance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTSousTraitanceDTO> validator = new BeanValidator<TaTSousTraitanceDTO>(TaTSousTraitanceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTSousTraitanceDTO dto, String propertyName, String validationContext) {
		try {
			TaTSousTraitanceMapper mapper = new TaTSousTraitanceMapper();
			TaTSousTraitance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTSousTraitanceDTO> validator = new BeanValidator<TaTSousTraitanceDTO>(TaTSousTraitanceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTSousTraitanceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTSousTraitanceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTSousTraitance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTSousTraitance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
