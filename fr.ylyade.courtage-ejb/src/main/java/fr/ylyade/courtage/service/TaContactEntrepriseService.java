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
import fr.ylyade.courtage.dao.ITaContactEntrepriseDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaContactEntrepriseDTO;
import fr.ylyade.courtage.model.TaContactEntreprise;
import fr.ylyade.courtage.model.mapper.TaContactEntrepriseMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaContactEntrepriseServiceRemote;


/**
 * Session Bean implementation class TaContactEntrepriseBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaContactEntrepriseService extends AbstractApplicationDAOServer<TaContactEntreprise, TaContactEntrepriseDTO> implements ITaContactEntrepriseServiceRemote {

	static Logger logger = Logger.getLogger(TaContactEntrepriseService.class);

	@Inject private ITaContactEntrepriseDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaContactEntrepriseService() {
		super(TaContactEntreprise.class,TaContactEntrepriseDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaContactEntreprise a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaContactEntreprise transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaContactEntreprise transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaContactEntreprise persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdContactEntreprise()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaContactEntreprise merge(TaContactEntreprise detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaContactEntreprise merge(TaContactEntreprise detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaContactEntreprise findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaContactEntreprise findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaContactEntreprise> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaContactEntrepriseDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaContactEntrepriseDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaContactEntreprise> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaContactEntrepriseDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaContactEntrepriseDTO entityToDTO(TaContactEntreprise entity) {
//		TaContactEntrepriseDTO dto = new TaContactEntrepriseDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaContactEntrepriseDTO> listEntityToListDTO(List<TaContactEntreprise> entity) {
		List<TaContactEntrepriseDTO> l = new ArrayList<TaContactEntrepriseDTO>();

		for (TaContactEntreprise taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaContactEntrepriseDTO> selectAllDTO() {
		System.out.println("List of TaContactEntrepriseDTO EJB :");
		ArrayList<TaContactEntrepriseDTO> liste = new ArrayList<TaContactEntrepriseDTO>();

		List<TaContactEntreprise> projects = selectAll();
		for(TaContactEntreprise project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaContactEntrepriseDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaContactEntrepriseDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaContactEntrepriseDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaContactEntrepriseDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaContactEntrepriseDTO dto, String validationContext) throws EJBException {
		try {
			TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
			TaContactEntreprise entity = null;
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
	
	public void persistDTO(TaContactEntrepriseDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaContactEntrepriseDTO dto, String validationContext) throws CreateException {
		try {
			TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
			TaContactEntreprise entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaContactEntrepriseDTO dto) throws RemoveException {
		try {
			TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
			TaContactEntreprise entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaContactEntreprise refresh(TaContactEntreprise persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaContactEntreprise value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaContactEntreprise value, String propertyName, String validationContext) {
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
	public void validateDTO(TaContactEntrepriseDTO dto, String validationContext) {
		try {
			TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
			TaContactEntreprise entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContactEntrepriseDTO> validator = new BeanValidator<TaContactEntrepriseDTO>(TaContactEntrepriseDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaContactEntrepriseDTO dto, String propertyName, String validationContext) {
		try {
			TaContactEntrepriseMapper mapper = new TaContactEntrepriseMapper();
			TaContactEntreprise entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaContactEntrepriseDTO> validator = new BeanValidator<TaContactEntrepriseDTO>(TaContactEntrepriseDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaContactEntrepriseDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaContactEntrepriseDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaContactEntreprise value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaContactEntreprise value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
