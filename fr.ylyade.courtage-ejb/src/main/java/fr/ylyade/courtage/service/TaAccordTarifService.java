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
import fr.ylyade.courtage.dao.ITaAccordTarifDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaAccordTarifDTO;
import fr.ylyade.courtage.model.TaAccordTarif;
import fr.ylyade.courtage.model.mapper.TaAccordTarifMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaAccordTarifServiceRemote;


/**
 * Session Bean implementation class TaAccordTarifBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaAccordTarifService extends AbstractApplicationDAOServer<TaAccordTarif, TaAccordTarifDTO> implements ITaAccordTarifServiceRemote {

	static Logger logger = Logger.getLogger(TaAccordTarifService.class);

	@Inject private ITaAccordTarifDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaAccordTarifService() {
		super(TaAccordTarif.class,TaAccordTarifDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaAccordTarif a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaAccordTarif transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaAccordTarif transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaAccordTarif persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdAccordeTarif()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaAccordTarif merge(TaAccordTarif detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaAccordTarif merge(TaAccordTarif detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaAccordTarif findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaAccordTarif findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaAccordTarif> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaAccordTarifDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaAccordTarifDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaAccordTarif> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaAccordTarifDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaAccordTarifDTO entityToDTO(TaAccordTarif entity) {
//		TaAccordTarifDTO dto = new TaAccordTarifDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaAccordTarifMapper mapper = new TaAccordTarifMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaAccordTarifDTO> listEntityToListDTO(List<TaAccordTarif> entity) {
		List<TaAccordTarifDTO> l = new ArrayList<TaAccordTarifDTO>();

		for (TaAccordTarif taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaAccordTarifDTO> selectAllDTO() {
		System.out.println("List of TaAccordTarifDTO EJB :");
		ArrayList<TaAccordTarifDTO> liste = new ArrayList<TaAccordTarifDTO>();

		List<TaAccordTarif> projects = selectAll();
		for(TaAccordTarif project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaAccordTarifDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaAccordTarifDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaAccordTarifDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaAccordTarifDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaAccordTarifDTO dto, String validationContext) throws EJBException {
		try {
			TaAccordTarifMapper mapper = new TaAccordTarifMapper();
			TaAccordTarif entity = null;
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
	
	public void persistDTO(TaAccordTarifDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaAccordTarifDTO dto, String validationContext) throws CreateException {
		try {
			TaAccordTarifMapper mapper = new TaAccordTarifMapper();
			TaAccordTarif entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaAccordTarifDTO dto) throws RemoveException {
		try {
			TaAccordTarifMapper mapper = new TaAccordTarifMapper();
			TaAccordTarif entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaAccordTarif refresh(TaAccordTarif persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaAccordTarif value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaAccordTarif value, String propertyName, String validationContext) {
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
	public void validateDTO(TaAccordTarifDTO dto, String validationContext) {
		try {
			TaAccordTarifMapper mapper = new TaAccordTarifMapper();
			TaAccordTarif entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAccordTarifDTO> validator = new BeanValidator<TaAccordTarifDTO>(TaAccordTarifDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaAccordTarifDTO dto, String propertyName, String validationContext) {
		try {
			TaAccordTarifMapper mapper = new TaAccordTarifMapper();
			TaAccordTarif entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaAccordTarifDTO> validator = new BeanValidator<TaAccordTarifDTO>(TaAccordTarifDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaAccordTarifDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaAccordTarifDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaAccordTarif value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaAccordTarif value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
