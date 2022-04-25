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
import fr.ylyade.courtage.dao.ITaSousDonneeDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaSousDonneeDTO;
import fr.ylyade.courtage.model.TaSousDonnee;
import fr.ylyade.courtage.model.mapper.TaSousDonneeMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaSousDonneeServiceRemote;


/**
 * Session Bean implementation class TaSousDonneeBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaSousDonneeService extends AbstractApplicationDAOServer<TaSousDonnee, TaSousDonneeDTO> implements ITaSousDonneeServiceRemote {

	static Logger logger = Logger.getLogger(TaSousDonneeService.class);

	@Inject private ITaSousDonneeDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaSousDonneeService() {
		super(TaSousDonnee.class,TaSousDonneeDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaSousDonnee a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaSousDonnee transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaSousDonnee transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaSousDonnee persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdSousDonnee()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaSousDonnee merge(TaSousDonnee detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaSousDonnee merge(TaSousDonnee detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaSousDonnee findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaSousDonnee findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaSousDonnee> selectAll() {
		return dao.selectAll();
	}
	public TaSousDonnee findByIdDossierRcd(int idDossier) {
		return dao.findByIdDossierRcd(idDossier);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaSousDonneeDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaSousDonneeDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaSousDonnee> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaSousDonneeDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaSousDonneeDTO entityToDTO(TaSousDonnee entity) {
//		TaSousDonneeDTO dto = new TaSousDonneeDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaSousDonneeMapper mapper = new TaSousDonneeMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaSousDonneeDTO> listEntityToListDTO(List<TaSousDonnee> entity) {
		List<TaSousDonneeDTO> l = new ArrayList<TaSousDonneeDTO>();

		for (TaSousDonnee taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaSousDonneeDTO> selectAllDTO() {
		System.out.println("List of TaSousDonneeDTO EJB :");
		ArrayList<TaSousDonneeDTO> liste = new ArrayList<TaSousDonneeDTO>();

		List<TaSousDonnee> projects = selectAll();
		for(TaSousDonnee project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaSousDonneeDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaSousDonneeDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaSousDonneeDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaSousDonneeDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaSousDonneeDTO dto, String validationContext) throws EJBException {
		try {
			TaSousDonneeMapper mapper = new TaSousDonneeMapper();
			TaSousDonnee entity = null;
			if(dto.getIdSousDonnee()!=null) {
				entity = dao.findById(dto.getIdSousDonnee());
//				if(dto.getVersionObj()!=entity.getVersionObj()) {
//					throw new OptimisticLockException(entity,
//							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getId()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
//				} else {
					 entity = mapper.mapDtoToEntity(dto,entity);
//				}
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
	
	public void persistDTO(TaSousDonneeDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaSousDonneeDTO dto, String validationContext) throws CreateException {
		try {
			TaSousDonneeMapper mapper = new TaSousDonneeMapper();
			TaSousDonnee entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaSousDonneeDTO dto) throws RemoveException {
		try {
			TaSousDonneeMapper mapper = new TaSousDonneeMapper();
			TaSousDonnee entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaSousDonnee refresh(TaSousDonnee persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaSousDonnee value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaSousDonnee value, String propertyName, String validationContext) {
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
	public void validateDTO(TaSousDonneeDTO dto, String validationContext) {
		try {
			TaSousDonneeMapper mapper = new TaSousDonneeMapper();
			TaSousDonnee entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSousDonneeDTO> validator = new BeanValidator<TaSousDonneeDTO>(TaSousDonneeDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaSousDonneeDTO dto, String propertyName, String validationContext) {
		try {
			TaSousDonneeMapper mapper = new TaSousDonneeMapper();
			TaSousDonnee entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaSousDonneeDTO> validator = new BeanValidator<TaSousDonneeDTO>(TaSousDonneeDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaSousDonneeDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaSousDonneeDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaSousDonnee value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaSousDonnee value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
