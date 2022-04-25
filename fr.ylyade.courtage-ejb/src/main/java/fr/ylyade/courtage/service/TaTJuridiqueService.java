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
import fr.ylyade.courtage.dao.ITaTJuridiqueDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaTJuridiqueDTO;
import fr.ylyade.courtage.model.TaTJuridique;
import fr.ylyade.courtage.model.mapper.TaTJuridiqueMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaTJuridiqueServiceRemote;


/**
 * Session Bean implementation class TaTJuridiqueBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaTJuridiqueService extends AbstractApplicationDAOServer<TaTJuridique, TaTJuridiqueDTO> implements ITaTJuridiqueServiceRemote {

	static Logger logger = Logger.getLogger(TaTJuridiqueService.class);

	@Inject private ITaTJuridiqueDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaTJuridiqueService() {
		super(TaTJuridique.class,TaTJuridiqueDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaTJuridique a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaTJuridique transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaTJuridique transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaTJuridique persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdTJuridique()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaTJuridique merge(TaTJuridique detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaTJuridique merge(TaTJuridique detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaTJuridique findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaTJuridique findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaTJuridique> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaTJuridiqueDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaTJuridiqueDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaTJuridique> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaTJuridiqueDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaTJuridiqueDTO entityToDTO(TaTJuridique entity) {
//		TaTJuridiqueDTO dto = new TaTJuridiqueDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaTJuridiqueDTO> listEntityToListDTO(List<TaTJuridique> entity) {
		List<TaTJuridiqueDTO> l = new ArrayList<TaTJuridiqueDTO>();

		for (TaTJuridique taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaTJuridiqueDTO> selectAllDTO() {
		System.out.println("List of TaTJuridiqueDTO EJB :");
		ArrayList<TaTJuridiqueDTO> liste = new ArrayList<TaTJuridiqueDTO>();

		List<TaTJuridique> projects = selectAll();
		for(TaTJuridique project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaTJuridiqueDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaTJuridiqueDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaTJuridiqueDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaTJuridiqueDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaTJuridiqueDTO dto, String validationContext) throws EJBException {
		try {
			TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
			TaTJuridique entity = null;
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
	
	public void persistDTO(TaTJuridiqueDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaTJuridiqueDTO dto, String validationContext) throws CreateException {
		try {
			TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
			TaTJuridique entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaTJuridiqueDTO dto) throws RemoveException {
		try {
			TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
			TaTJuridique entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaTJuridique refresh(TaTJuridique persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaTJuridique value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaTJuridique value, String propertyName, String validationContext) {
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
	public void validateDTO(TaTJuridiqueDTO dto, String validationContext) {
		try {
			TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
			TaTJuridique entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTJuridiqueDTO> validator = new BeanValidator<TaTJuridiqueDTO>(TaTJuridiqueDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaTJuridiqueDTO dto, String propertyName, String validationContext) {
		try {
			TaTJuridiqueMapper mapper = new TaTJuridiqueMapper();
			TaTJuridique entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaTJuridiqueDTO> validator = new BeanValidator<TaTJuridiqueDTO>(TaTJuridiqueDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaTJuridiqueDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaTJuridiqueDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaTJuridique value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaTJuridique value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
