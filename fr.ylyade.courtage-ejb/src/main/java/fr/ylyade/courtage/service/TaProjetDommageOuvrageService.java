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
import fr.ylyade.courtage.dao.ITaProjetDommageOuvrageDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaProjetDommageOuvrageDTO;
import fr.ylyade.courtage.model.TaProjetDommageOuvrage;
import fr.ylyade.courtage.model.mapper.TaProjetDommageOuvrageMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaProjetDommageOuvrageServiceRemote;


/**
 * Session Bean implementation class TaProjetDommageOuvrageBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaProjetDommageOuvrageService extends AbstractApplicationDAOServer<TaProjetDommageOuvrage, TaProjetDommageOuvrageDTO> implements ITaProjetDommageOuvrageServiceRemote {

	static Logger logger = Logger.getLogger(TaProjetDommageOuvrageService.class);

	@Inject private ITaProjetDommageOuvrageDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaProjetDommageOuvrageService() {
		super(TaProjetDommageOuvrage.class,TaProjetDommageOuvrageDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaProjetDommageOuvrage a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		return dao.countActiveByIdCourtier(idCourtier);
	}
	
	public void persist(TaProjetDommageOuvrage transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaProjetDommageOuvrage transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaProjetDommageOuvrage persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdProjetDommageOuvrage()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaProjetDommageOuvrage merge(TaProjetDommageOuvrage detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaProjetDommageOuvrage merge(TaProjetDommageOuvrage detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaProjetDommageOuvrage findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaProjetDommageOuvrage findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaProjetDommageOuvrage> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaProjetDommageOuvrageDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaProjetDommageOuvrageDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaProjetDommageOuvrage> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaProjetDommageOuvrageDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaProjetDommageOuvrageDTO entityToDTO(TaProjetDommageOuvrage entity) {
//		TaProjetDommageOuvrageDTO dto = new TaProjetDommageOuvrageDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaProjetDommageOuvrageDTO> listEntityToListDTO(List<TaProjetDommageOuvrage> entity) {
		List<TaProjetDommageOuvrageDTO> l = new ArrayList<TaProjetDommageOuvrageDTO>();

		for (TaProjetDommageOuvrage taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaProjetDommageOuvrageDTO> selectAllDTO() {
		System.out.println("List of TaProjetDommageOuvrageDTO EJB :");
		ArrayList<TaProjetDommageOuvrageDTO> liste = new ArrayList<TaProjetDommageOuvrageDTO>();

		List<TaProjetDommageOuvrage> projects = selectAll();
		for(TaProjetDommageOuvrage project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaProjetDommageOuvrageDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaProjetDommageOuvrageDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaProjetDommageOuvrageDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaProjetDommageOuvrageDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaProjetDommageOuvrageDTO dto, String validationContext) throws EJBException {
		try {
			TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
			TaProjetDommageOuvrage entity = null;
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
	
	public void persistDTO(TaProjetDommageOuvrageDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaProjetDommageOuvrageDTO dto, String validationContext) throws CreateException {
		try {
			TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
			TaProjetDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaProjetDommageOuvrageDTO dto) throws RemoveException {
		try {
			TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
			TaProjetDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaProjetDommageOuvrage refresh(TaProjetDommageOuvrage persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaProjetDommageOuvrage value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaProjetDommageOuvrage value, String propertyName, String validationContext) {
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
	public void validateDTO(TaProjetDommageOuvrageDTO dto, String validationContext) {
		try {
			TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
			TaProjetDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaProjetDommageOuvrageDTO> validator = new BeanValidator<TaProjetDommageOuvrageDTO>(TaProjetDommageOuvrageDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaProjetDommageOuvrageDTO dto, String propertyName, String validationContext) {
		try {
			TaProjetDommageOuvrageMapper mapper = new TaProjetDommageOuvrageMapper();
			TaProjetDommageOuvrage entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaProjetDommageOuvrageDTO> validator = new BeanValidator<TaProjetDommageOuvrageDTO>(TaProjetDommageOuvrageDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaProjetDommageOuvrageDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaProjetDommageOuvrageDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaProjetDommageOuvrage value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaProjetDommageOuvrage value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
