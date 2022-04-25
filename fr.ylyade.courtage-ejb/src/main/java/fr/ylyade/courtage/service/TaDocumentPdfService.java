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
import fr.ylyade.courtage.dao.ITaDocumentPdfDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaDocumentPdfDTO;
import fr.ylyade.courtage.model.TaDocumentPdf;
import fr.ylyade.courtage.model.mapper.TaDocumentPdfMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaDocumentPdfServiceRemote;


/**
 * Session Bean implementation class TaDocumentPdfBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaDocumentPdfService extends AbstractApplicationDAOServer<TaDocumentPdf, TaDocumentPdfDTO> implements ITaDocumentPdfServiceRemote {

	static Logger logger = Logger.getLogger(TaDocumentPdfService.class);

	@Inject private ITaDocumentPdfDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;

	/**
	 * Default constructor. 
	 */
	public TaDocumentPdfService() {
		super(TaDocumentPdf.class,TaDocumentPdfDTO.class);
	}
	
	//	private String defaultJPQLQuery = "select a from TaDocumentPdf a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaDocumentPdf transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaDocumentPdf transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaDocumentPdf persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdDocumentPdf()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaDocumentPdf merge(TaDocumentPdf detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaDocumentPdf merge(TaDocumentPdf detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaDocumentPdf findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaDocumentPdf findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaDocumentPdf> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaDocumentPdfDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaDocumentPdfDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaDocumentPdf> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaDocumentPdfDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaDocumentPdfDTO entityToDTO(TaDocumentPdf entity) {
//		TaDocumentPdfDTO dto = new TaDocumentPdfDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaDocumentPdfDTO> listEntityToListDTO(List<TaDocumentPdf> entity) {
		List<TaDocumentPdfDTO> l = new ArrayList<TaDocumentPdfDTO>();

		for (TaDocumentPdf taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaDocumentPdfDTO> selectAllDTO() {
		System.out.println("List of TaDocumentPdfDTO EJB :");
		ArrayList<TaDocumentPdfDTO> liste = new ArrayList<TaDocumentPdfDTO>();

		List<TaDocumentPdf> projects = selectAll();
		for(TaDocumentPdf project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaDocumentPdfDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaDocumentPdfDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaDocumentPdfDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaDocumentPdfDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaDocumentPdfDTO dto, String validationContext) throws EJBException {
		try {
			TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
			TaDocumentPdf entity = null;
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
	
	public void persistDTO(TaDocumentPdfDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaDocumentPdfDTO dto, String validationContext) throws CreateException {
		try {
			TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
			TaDocumentPdf entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaDocumentPdfDTO dto) throws RemoveException {
		try {
			TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
			TaDocumentPdf entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaDocumentPdf refresh(TaDocumentPdf persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaDocumentPdf value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaDocumentPdf value, String propertyName, String validationContext) {
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
	public void validateDTO(TaDocumentPdfDTO dto, String validationContext) {
		try {
			TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
			TaDocumentPdf entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaDocumentPdfDTO> validator = new BeanValidator<TaDocumentPdfDTO>(TaDocumentPdfDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaDocumentPdfDTO dto, String propertyName, String validationContext) {
		try {
			TaDocumentPdfMapper mapper = new TaDocumentPdfMapper();
			TaDocumentPdf entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaDocumentPdfDTO> validator = new BeanValidator<TaDocumentPdfDTO>(TaDocumentPdfDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaDocumentPdfDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaDocumentPdfDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaDocumentPdf value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaDocumentPdf value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
