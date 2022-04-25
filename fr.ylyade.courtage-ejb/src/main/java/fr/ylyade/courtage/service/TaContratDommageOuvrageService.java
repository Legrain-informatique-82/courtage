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
import fr.ylyade.courtage.dao.ITaContratDommageOuvrageDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaContratDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaContratDommageOuvrage;
import fr.ylyade.courtage.model.mapper.TaContratDommageOuvrageMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaContratDommageOuvrageServiceRemote;


/**
 * Session Bean implementation class TaContratDommageOuvrageBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaContratDommageOuvrageService extends AbstractApplicationDAOServer<TaContratDommageOuvrage, TaContratDommageOuvrageDTO> implements ITaContratDommageOuvrageServiceRemote {

	static Logger logger = Logger.getLogger(TaContratDommageOuvrageService.class);

	@Inject private ITaContratDommageOuvrageDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaContratDommageOuvrageService() {
		super(TaContratDommageOuvrage.class,TaContratDommageOuvrageDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaContratDommageOuvrage a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		return dao.countActiveByIdCourtier(idCourtier);
	}
	
	public void persist(TaContratDommageOuvrage transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaContratDommageOuvrage transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaContratDommageOuvrage persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdContratDommageOuvrage()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaContratDommageOuvrage merge(TaContratDommageOuvrage detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaContratDommageOuvrage merge(TaContratDommageOuvrage detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaContratDommageOuvrage findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaContratDommageOuvrage findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaContratDommageOuvrage> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaContratDommageOuvrageDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaContratDommageOuvrageDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaContratDommageOuvrage> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaContratDommageOuvrageDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaContratDommageOuvrageDTO entityToDTO(TaContratDommageOuvrage entity) {
//		TaContratDommageOuvrageDTO dto = new TaContratDommageOuvrageDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaContratDommageOuvrageDTO> listEntityToListDTO(List<TaContratDommageOuvrage> entity) {
		List<TaContratDommageOuvrageDTO> l = new ArrayList<TaContratDommageOuvrageDTO>();

		for (TaContratDommageOuvrage taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaContratDommageOuvrageDTO> selectAllDTO() {
		System.out.println("List of TaContratDommageOuvrageDTO EJB :");
		ArrayList<TaContratDommageOuvrageDTO> liste = new ArrayList<TaContratDommageOuvrageDTO>();

		List<TaContratDommageOuvrage> projects = selectAll();
		for(TaContratDommageOuvrage project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaContratDommageOuvrageDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaContratDommageOuvrageDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaContratDommageOuvrageDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaContratDommageOuvrageDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaContratDommageOuvrageDTO dto, String validationContext) throws EJBException {
		try {
			TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
			TaContratDommageOuvrage entity = null;
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
	
	public void persistDTO(TaContratDommageOuvrageDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaContratDommageOuvrageDTO dto, String validationContext) throws CreateException {
		try {
			TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
			TaContratDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaContratDommageOuvrageDTO dto) throws RemoveException {
		try {
			TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
			TaContratDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaContratDommageOuvrage refresh(TaContratDommageOuvrage persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaContratDommageOuvrage value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaContratDommageOuvrage value, String propertyName, String validationContext) {
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
	public void validateDTO(TaContratDommageOuvrageDTO dto, String validationContext) {
		try {
			TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
			TaContratDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContratDommageOuvrageDTO> validator = new BeanValidator<TaContratDommageOuvrageDTO>(TaContratDommageOuvrageDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaContratDommageOuvrageDTO dto, String propertyName, String validationContext) {
		try {
			TaContratDommageOuvrageMapper mapper = new TaContratDommageOuvrageMapper();
			TaContratDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContratDommageOuvrageDTO> validator = new BeanValidator<TaContratDommageOuvrageDTO>(TaContratDommageOuvrageDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaContratDommageOuvrageDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaContratDommageOuvrageDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaContratDommageOuvrage value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaContratDommageOuvrage value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
