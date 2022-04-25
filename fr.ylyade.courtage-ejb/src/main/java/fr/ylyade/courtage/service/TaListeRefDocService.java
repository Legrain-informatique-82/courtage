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
import fr.ylyade.courtage.dao.ITaListeRefDocDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaListeRefDoc;
import fr.ylyade.courtage.model.mapper.TaListeRefDocMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaListeRefDocServiceRemote;


/**
 * Session Bean implementation class TaListeRefDocBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaListeRefDocService extends AbstractApplicationDAOServer<TaListeRefDoc, TaListeRefDocDTO> implements ITaListeRefDocServiceRemote {

	static Logger logger = Logger.getLogger(TaListeRefDocService.class);

	@Inject private ITaListeRefDocDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaListeRefDocService() {
		super(TaListeRefDoc.class,TaListeRefDocDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaListeRefDoc a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<TaListeRefDocDTO> findByType(int idTListeRefDoc) {
		return dao.findByType(idTListeRefDoc);
	}
	public List<TaListeRefDocDTO> findByTypeNotInGedUtilisateur(int idDossierRcd, int idTListeRefDoc) {
		return dao.findByTypeNotInGedUtilisateur(idDossierRcd, idTListeRefDoc);
	}
	public List<TaListeRefDocDTO> findByTypeInGedUtilisateur(int idDossierRcd, int idTListeRefDoc) {
		return dao.findByTypeInGedUtilisateur(idDossierRcd, idTListeRefDoc);
	}
	
	public void persist(TaListeRefDoc transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaListeRefDoc transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaListeRefDoc persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdListeRefDoc()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaListeRefDoc merge(TaListeRefDoc detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaListeRefDoc merge(TaListeRefDoc detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaListeRefDoc findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaListeRefDoc findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaListeRefDoc> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaListeRefDocDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaListeRefDocDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaListeRefDoc> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaListeRefDocDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaListeRefDocDTO entityToDTO(TaListeRefDoc entity) {
//		TaListeRefDocDTO dto = new TaListeRefDocDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaListeRefDocMapper mapper = new TaListeRefDocMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaListeRefDocDTO> listEntityToListDTO(List<TaListeRefDoc> entity) {
		List<TaListeRefDocDTO> l = new ArrayList<TaListeRefDocDTO>();

		for (TaListeRefDoc taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaListeRefDocDTO> selectAllDTO() {
		System.out.println("List of TaListeRefDocDTO EJB :");
		ArrayList<TaListeRefDocDTO> liste = new ArrayList<TaListeRefDocDTO>();

		List<TaListeRefDoc> projects = selectAll();
		for(TaListeRefDoc project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaListeRefDocDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaListeRefDocDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaListeRefDocDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaListeRefDocDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaListeRefDocDTO dto, String validationContext) throws EJBException {
		try {
			TaListeRefDocMapper mapper = new TaListeRefDocMapper();
			TaListeRefDoc entity = null;
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
	
	public void persistDTO(TaListeRefDocDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaListeRefDocDTO dto, String validationContext) throws CreateException {
		try {
			TaListeRefDocMapper mapper = new TaListeRefDocMapper();
			TaListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaListeRefDocDTO dto) throws RemoveException {
		try {
			TaListeRefDocMapper mapper = new TaListeRefDocMapper();
			TaListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaListeRefDoc refresh(TaListeRefDoc persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaListeRefDoc value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaListeRefDoc value, String propertyName, String validationContext) {
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
	public void validateDTO(TaListeRefDocDTO dto, String validationContext) {
		try {
			TaListeRefDocMapper mapper = new TaListeRefDocMapper();
			TaListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaListeRefDocDTO> validator = new BeanValidator<TaListeRefDocDTO>(TaListeRefDocDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaListeRefDocDTO dto, String propertyName, String validationContext) {
		try {
			TaListeRefDocMapper mapper = new TaListeRefDocMapper();
			TaListeRefDoc entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaListeRefDocDTO> validator = new BeanValidator<TaListeRefDocDTO>(TaListeRefDocDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaListeRefDocDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaListeRefDocDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaListeRefDoc value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaListeRefDoc value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
