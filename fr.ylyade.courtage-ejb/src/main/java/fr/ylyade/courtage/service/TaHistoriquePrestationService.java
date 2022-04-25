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
import fr.ylyade.courtage.dao.ITaHistoriquePrestationDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaHistoriquePrestationDTO;
import fr.ylyade.courtage.model.TaHistoriquePrestation;
import fr.ylyade.courtage.model.mapper.TaHistoriquePrestationMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaHistoriquePrestationServiceRemote;


/**
 * Session Bean implementation class TaHistoriquePrestationBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaHistoriquePrestationService extends AbstractApplicationDAOServer<TaHistoriquePrestation, TaHistoriquePrestationDTO> implements ITaHistoriquePrestationServiceRemote {

	static Logger logger = Logger.getLogger(TaHistoriquePrestationService.class);

	@Inject private ITaHistoriquePrestationDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaHistoriquePrestationService() {
		super(TaHistoriquePrestation.class,TaHistoriquePrestationDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaHistoriquePrestation a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaHistoriquePrestation transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaHistoriquePrestation transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaHistoriquePrestation persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdHistoriquePrestation()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaHistoriquePrestation merge(TaHistoriquePrestation detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaHistoriquePrestation merge(TaHistoriquePrestation detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaHistoriquePrestation findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaHistoriquePrestation findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaHistoriquePrestation> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaHistoriquePrestationDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaHistoriquePrestationDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaHistoriquePrestation> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaHistoriquePrestationDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaHistoriquePrestationDTO entityToDTO(TaHistoriquePrestation entity) {
//		TaHistoriquePrestationDTO dto = new TaHistoriquePrestationDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaHistoriquePrestationDTO> listEntityToListDTO(List<TaHistoriquePrestation> entity) {
		List<TaHistoriquePrestationDTO> l = new ArrayList<TaHistoriquePrestationDTO>();

		for (TaHistoriquePrestation taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaHistoriquePrestationDTO> selectAllDTO() {
		System.out.println("List of TaHistoriquePrestationDTO EJB :");
		ArrayList<TaHistoriquePrestationDTO> liste = new ArrayList<TaHistoriquePrestationDTO>();

		List<TaHistoriquePrestation> projects = selectAll();
		for(TaHistoriquePrestation project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaHistoriquePrestationDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaHistoriquePrestationDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaHistoriquePrestationDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaHistoriquePrestationDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaHistoriquePrestationDTO dto, String validationContext) throws EJBException {
		try {
			TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
			TaHistoriquePrestation entity = null;
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
	
	public void persistDTO(TaHistoriquePrestationDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaHistoriquePrestationDTO dto, String validationContext) throws CreateException {
		try {
			TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
			TaHistoriquePrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaHistoriquePrestationDTO dto) throws RemoveException {
		try {
			TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
			TaHistoriquePrestation entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaHistoriquePrestation refresh(TaHistoriquePrestation persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaHistoriquePrestation value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaHistoriquePrestation value, String propertyName, String validationContext) {
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
	public void validateDTO(TaHistoriquePrestationDTO dto, String validationContext) {
		try {
			TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
			TaHistoriquePrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaHistoriquePrestationDTO> validator = new BeanValidator<TaHistoriquePrestationDTO>(TaHistoriquePrestationDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaHistoriquePrestationDTO dto, String propertyName, String validationContext) {
		try {
			TaHistoriquePrestationMapper mapper = new TaHistoriquePrestationMapper();
			TaHistoriquePrestation entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaHistoriquePrestationDTO> validator = new BeanValidator<TaHistoriquePrestationDTO>(TaHistoriquePrestationDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaHistoriquePrestationDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaHistoriquePrestationDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaHistoriquePrestation value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaHistoriquePrestation value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
