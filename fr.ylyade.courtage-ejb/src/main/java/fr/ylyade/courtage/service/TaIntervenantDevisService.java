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
import fr.ylyade.courtage.dao.ITaIntervenantDevisDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaIntervenantDevisDTO;
import fr.ylyade.courtage.model.TaIntervenantDevis;
import fr.ylyade.courtage.model.mapper.TaIntervenantDevisMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaIntervenantDevisServiceRemote;


/**
 * Session Bean implementation class TaIntervenantDevisBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaIntervenantDevisService extends AbstractApplicationDAOServer<TaIntervenantDevis, TaIntervenantDevisDTO> implements ITaIntervenantDevisServiceRemote {

	static Logger logger = Logger.getLogger(TaIntervenantDevisService.class);

	@Inject private ITaIntervenantDevisDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaIntervenantDevisService() {
		super(TaIntervenantDevis.class,TaIntervenantDevisDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaIntervenantDevis a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaIntervenantDevis transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaIntervenantDevis transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaIntervenantDevis persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdIntervenantDevis()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaIntervenantDevis merge(TaIntervenantDevis detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaIntervenantDevis merge(TaIntervenantDevis detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaIntervenantDevis findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaIntervenantDevis findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaIntervenantDevis> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaIntervenantDevisDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaIntervenantDevisDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaIntervenantDevis> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaIntervenantDevisDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaIntervenantDevisDTO entityToDTO(TaIntervenantDevis entity) {
//		TaIntervenantDevisDTO dto = new TaIntervenantDevisDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaIntervenantDevisDTO> listEntityToListDTO(List<TaIntervenantDevis> entity) {
		List<TaIntervenantDevisDTO> l = new ArrayList<TaIntervenantDevisDTO>();

		for (TaIntervenantDevis taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaIntervenantDevisDTO> selectAllDTO() {
		System.out.println("List of TaIntervenantDevisDTO EJB :");
		ArrayList<TaIntervenantDevisDTO> liste = new ArrayList<TaIntervenantDevisDTO>();

		List<TaIntervenantDevis> projects = selectAll();
		for(TaIntervenantDevis project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaIntervenantDevisDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaIntervenantDevisDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaIntervenantDevisDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaIntervenantDevisDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaIntervenantDevisDTO dto, String validationContext) throws EJBException {
		try {
			TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
			TaIntervenantDevis entity = null;
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
	
	public void persistDTO(TaIntervenantDevisDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaIntervenantDevisDTO dto, String validationContext) throws CreateException {
		try {
			TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
			TaIntervenantDevis entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaIntervenantDevisDTO dto) throws RemoveException {
		try {
			TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
			TaIntervenantDevis entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaIntervenantDevis refresh(TaIntervenantDevis persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaIntervenantDevis value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaIntervenantDevis value, String propertyName, String validationContext) {
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
	public void validateDTO(TaIntervenantDevisDTO dto, String validationContext) {
		try {
			TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
			TaIntervenantDevis entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaIntervenantDevisDTO> validator = new BeanValidator<TaIntervenantDevisDTO>(TaIntervenantDevisDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaIntervenantDevisDTO dto, String propertyName, String validationContext) {
		try {
			TaIntervenantDevisMapper mapper = new TaIntervenantDevisMapper();
			TaIntervenantDevis entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaIntervenantDevisDTO> validator = new BeanValidator<TaIntervenantDevisDTO>(TaIntervenantDevisDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaIntervenantDevisDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaIntervenantDevisDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaIntervenantDevis value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaIntervenantDevis value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
