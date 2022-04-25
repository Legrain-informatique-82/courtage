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
import fr.ylyade.courtage.dao.ITaIntervenantContratDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaIntervenantContratDTO;
import fr.ylyade.courtage.model.TaIntervenantContrat;
import fr.ylyade.courtage.model.mapper.TaIntervenantContratMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaIntervenantContratServiceRemote;


/**
 * Session Bean implementation class TaIntervenantContratBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaIntervenantContratService extends AbstractApplicationDAOServer<TaIntervenantContrat, TaIntervenantContratDTO> implements ITaIntervenantContratServiceRemote {

	static Logger logger = Logger.getLogger(TaIntervenantContratService.class);

	@Inject private ITaIntervenantContratDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaIntervenantContratService() {
		super(TaIntervenantContrat.class,TaIntervenantContratDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaIntervenantContrat a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaIntervenantContrat transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaIntervenantContrat transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaIntervenantContrat persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdIntervenantContrat()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaIntervenantContrat merge(TaIntervenantContrat detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaIntervenantContrat merge(TaIntervenantContrat detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaIntervenantContrat findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaIntervenantContrat findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaIntervenantContrat> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaIntervenantContratDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaIntervenantContratDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaIntervenantContrat> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaIntervenantContratDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaIntervenantContratDTO entityToDTO(TaIntervenantContrat entity) {
//		TaIntervenantContratDTO dto = new TaIntervenantContratDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaIntervenantContratDTO> listEntityToListDTO(List<TaIntervenantContrat> entity) {
		List<TaIntervenantContratDTO> l = new ArrayList<TaIntervenantContratDTO>();

		for (TaIntervenantContrat taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaIntervenantContratDTO> selectAllDTO() {
		System.out.println("List of TaIntervenantContratDTO EJB :");
		ArrayList<TaIntervenantContratDTO> liste = new ArrayList<TaIntervenantContratDTO>();

		List<TaIntervenantContrat> projects = selectAll();
		for(TaIntervenantContrat project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaIntervenantContratDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaIntervenantContratDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaIntervenantContratDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaIntervenantContratDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaIntervenantContratDTO dto, String validationContext) throws EJBException {
		try {
			TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
			TaIntervenantContrat entity = null;
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
	
	public void persistDTO(TaIntervenantContratDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaIntervenantContratDTO dto, String validationContext) throws CreateException {
		try {
			TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
			TaIntervenantContrat entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaIntervenantContratDTO dto) throws RemoveException {
		try {
			TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
			TaIntervenantContrat entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaIntervenantContrat refresh(TaIntervenantContrat persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaIntervenantContrat value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaIntervenantContrat value, String propertyName, String validationContext) {
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
	public void validateDTO(TaIntervenantContratDTO dto, String validationContext) {
		try {
			TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
			TaIntervenantContrat entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaIntervenantContratDTO> validator = new BeanValidator<TaIntervenantContratDTO>(TaIntervenantContratDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaIntervenantContratDTO dto, String propertyName, String validationContext) {
		try {
			TaIntervenantContratMapper mapper = new TaIntervenantContratMapper();
			TaIntervenantContrat entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaIntervenantContratDTO> validator = new BeanValidator<TaIntervenantContratDTO>(TaIntervenantContratDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaIntervenantContratDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaIntervenantContratDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaIntervenantContrat value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaIntervenantContrat value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
