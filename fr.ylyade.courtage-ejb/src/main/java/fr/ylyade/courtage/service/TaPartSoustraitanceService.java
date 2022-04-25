//package fr.ylyade.courtage.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.CreateException;
//import javax.ejb.EJBException;
//import javax.ejb.FinderException;
//import javax.ejb.RemoveException;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.jws.WebMethod;
//
//import org.apache.log4j.Logger;
//import org.hibernate.OptimisticLockException;
//
//import fr.legrain.data.AbstractApplicationDAOServer;
//import fr.ylyade.courtage.dao.ITaPartSoustraitanceDAO;
//import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
//import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
//import fr.ylyade.courtage.dto.TaPartSoustraitanceDTO;
//import fr.ylyade.courtage.model.TaPartSoustraitance;
//import fr.ylyade.courtage.model.mapper.TaPartSoustraitanceMapper;
//import fr.ylyade.courtage.service.interfaces.remote.ITaPartSoustraitanceServiceRemote;
//
//
///**
// * Session Bean implementation class TaPartSoustraitanceBean
// */
//@Stateless
////@Interceptors(ServerTenantInterceptor.class)
//public class TaPartSoustraitanceService extends AbstractApplicationDAOServer<TaPartSoustraitance, TaPartSoustraitanceDTO> implements ITaPartSoustraitanceServiceRemote {
//
//	static Logger logger = Logger.getLogger(TaPartSoustraitanceService.class);
//
//	@Inject private ITaPartSoustraitanceDAO dao;
//	
//	@Inject private	SessionInfo sessionInfo;
//	
//	@Inject private	TenantInfo tenantInfo;
//
//	/**
//	 * Default constructor. 
//	 */
//	public TaPartSoustraitanceService() {
//		super(TaPartSoustraitance.class,TaPartSoustraitanceDTO.class);
//	}
//	
//	//	private String defaultJPQLQuery = "select a from TaPartSoustraitance a";
//
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	// 										ENTITY
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	public void persist(TaPartSoustraitance transientInstance) throws CreateException {
//		persist(transientInstance, null);
//	}
//
//	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	@WebMethod(operationName = "persistValidationContext")
//	public void persist(TaPartSoustraitance transientInstance, String validationContext) throws CreateException {
//
//		validateEntity(transientInstance, validationContext);
//
//		dao.persist(transientInstance);
//	}
//
//	public void remove(TaPartSoustraitance persistentInstance) throws RemoveException {
//		try {
//			dao.remove(findById(persistentInstance.getIdPartSousTraitance()));
//		} catch (FinderException e) {
//			logger.error("", e);
//		}
//	}
//	
//	public TaPartSoustraitance merge(TaPartSoustraitance detachedInstance) {
//		return merge(detachedInstance, null);
//	}
//
//	@Override
//	@WebMethod(operationName = "mergeValidationContext")
//	public TaPartSoustraitance merge(TaPartSoustraitance detachedInstance, String validationContext) {
//		validateEntity(detachedInstance, validationContext);
//
//		return dao.merge(detachedInstance);
//	}
//
//	public TaPartSoustraitance findById(int id) throws FinderException {
//		return dao.findById(id);
//	}
//
//	public TaPartSoustraitance findByCode(String code) throws FinderException {
//		return dao.findByCode(code);
//	}
//
////	@RolesAllowed("admin")
//	public List<TaPartSoustraitance> selectAll() {
//		return dao.selectAll();
//	}
//
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	// 										DTO
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	public List<TaPartSoustraitanceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
//		return null;
//	}
//
//	@Override
//	public List<TaPartSoustraitanceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
//		List<TaPartSoustraitance> entityList = dao.findWithJPQLQuery(JPQLquery);
//		List<TaPartSoustraitanceDTO> l = null;
//		if(entityList!=null) {
//			l = listEntityToListDTO(entityList);
//		}
//		return l;
//	}
//
//	public TaPartSoustraitanceDTO entityToDTO(TaPartSoustraitance entity) {
////		TaPartSoustraitanceDTO dto = new TaPartSoustraitanceDTO();
////		dto.setId(entity.getIdTCivilite());
////		dto.setCodeTCivilite(entity.getCodeTCivilite());
////		return dto;
//		TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//		return mapper.mapEntityToDto(entity, null);
//	}
//
//	public List<TaPartSoustraitanceDTO> listEntityToListDTO(List<TaPartSoustraitance> entity) {
//		List<TaPartSoustraitanceDTO> l = new ArrayList<TaPartSoustraitanceDTO>();
//
//		for (TaPartSoustraitance taTCivilite : entity) {
//			l.add(entityToDTO(taTCivilite));
//		}
//
//		return l;
//	}
//
////	@RolesAllowed("admin")
//	public List<TaPartSoustraitanceDTO> selectAllDTO() {
//		System.out.println("List of TaPartSoustraitanceDTO EJB :");
//		ArrayList<TaPartSoustraitanceDTO> liste = new ArrayList<TaPartSoustraitanceDTO>();
//
//		List<TaPartSoustraitance> projects = selectAll();
//		for(TaPartSoustraitance project : projects) {
//			liste.add(entityToDTO(project));
//		}
//
//		return liste;
//	}
//
//	public TaPartSoustraitanceDTO findByIdDTO(int id) throws FinderException {
//		return entityToDTO(findById(id));
//	}
//
//	public TaPartSoustraitanceDTO findByCodeDTO(String code) throws FinderException {
//		return entityToDTO(findByCode(code));
//	}
//
//	@Override
//	public void error(TaPartSoustraitanceDTO dto) {
//		throw new EJBException("Test erreur EJB");
//	}
//
//	@Override
//	public int selectCount() {
//		return dao.selectAll().size();
//		//return 0;
//	}
//	
//	public void mergeDTO(TaPartSoustraitanceDTO dto) throws EJBException {
//		mergeDTO(dto, null);
//	}
//
//	@Override
//	@WebMethod(operationName = "mergeDTOValidationContext")
//	public void mergeDTO(TaPartSoustraitanceDTO dto, String validationContext) throws EJBException {
//		try {
//			TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//			TaPartSoustraitance entity = null;
//			if(dto.getId()!=null) {
//				entity = dao.findById(dto.getId());
//				if(dto.getVersionObj()!=entity.getVersionObj()) {
//					throw new OptimisticLockException(entity,
//							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getId()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
//				} else {
//					 entity = mapper.mapDtoToEntity(dto,entity);
//				}
//			}
//			
//			//dao.merge(entity);
//			dao.detach(entity); //pour passer les controles
//			enregistrerMerge(entity, validationContext);
//		} catch (Exception e) {
//			e.printStackTrace();
//			//throw new CreateException(e.getMessage());
//			throw new EJBException(e.getMessage());
//		}
//	}
//	
//	public void persistDTO(TaPartSoustraitanceDTO dto) throws CreateException {
//		persistDTO(dto, null);
//	}
//
//	@Override
//	@WebMethod(operationName = "persistDTOValidationContext")
//	public void persistDTO(TaPartSoustraitanceDTO dto, String validationContext) throws CreateException {
//		try {
//			TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//			TaPartSoustraitance entity = mapper.mapDtoToEntity(dto,null);
//			//dao.persist(entity);
//			enregistrerPersist(entity, validationContext);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new CreateException(e.getMessage());
//		}
//	}
//
//	@Override
//	public void removeDTO(TaPartSoustraitanceDTO dto) throws RemoveException {
//		try {
//			TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//			TaPartSoustraitance entity = mapper.mapDtoToEntity(dto,null);
//			//dao.remove(entity);
//			supprimer(entity);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RemoveException(e.getMessage());
//		}
//	}
//
//	@Override
//	protected TaPartSoustraitance refresh(TaPartSoustraitance persistentInstance) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	@WebMethod(operationName = "validateEntityValidationContext")
//	public void validateEntity(TaPartSoustraitance value, String validationContext) /*throws ExceptLgr*/ {
//		try {
//			if(validationContext==null) validationContext="";
//			validateAll(value,validationContext,false); //ancienne validation, extraction de l'annotation et appel
//			//dao.validate(value); //validation automatique via la JSR bean validation
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
//	}
//
//	@Override
//	@WebMethod(operationName = "validateEntityPropertyValidationContext")
//	public void validateEntityProperty(TaPartSoustraitance value, String propertyName, String validationContext) {
//		try {
//			if(validationContext==null) validationContext="";
//			validate(value, propertyName, validationContext);
//			//dao.validateField(value,propertyName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
//	}
//
//	@Override
//	@WebMethod(operationName = "validateDTOValidationContext")
//	public void validateDTO(TaPartSoustraitanceDTO dto, String validationContext) {
//		try {
//			TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//			TaPartSoustraitance entity = mapper.mapDtoToEntity(dto,null);
//			validateEntity(entity,validationContext);
//			
//			//validation automatique via la JSR bean validation
////			BeanValidator<TaPartSoustraitanceDTO> validator = new BeanValidator<TaPartSoustraitanceDTO>(TaPartSoustraitanceDTO.class);
////			validator.validate(dto);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
//	}
//
//	@Override
//	@WebMethod(operationName = "validateDTOPropertyValidationContext")
//	public void validateDTOProperty(TaPartSoustraitanceDTO dto, String propertyName, String validationContext) {
//		try {
//			TaPartSoustraitanceMapper mapper = new TaPartSoustraitanceMapper();
//			TaPartSoustraitance entity = mapper.mapDtoToEntity(dto,null);
//			validateEntityProperty(entity,propertyName,validationContext);
//			
//			//validation automatique via la JSR bean validation
////			BeanValidator<TaPartSoustraitanceDTO> validator = new BeanValidator<TaPartSoustraitanceDTO>(TaPartSoustraitanceDTO.class);
////			validator.validateField(dto,propertyName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new EJBException(e.getMessage());
//		}
//
//	}
//	
//	@Override
//	@WebMethod(operationName = "validateDTO")
//	public void validateDTO(TaPartSoustraitanceDTO dto) {
//		validateDTO(dto,null);
//		
//	}
//
//	@Override
//	@WebMethod(operationName = "validateDTOProperty")
//	public void validateDTOProperty(TaPartSoustraitanceDTO dto, String propertyName) {
//		validateDTOProperty(dto,propertyName,null);
//		
//	}
//
//	@Override
//	@WebMethod(operationName = "validateEntity")
//	public void validateEntity(TaPartSoustraitance value) {
//		validateEntity(value,null);
//	}
//
//	@Override
//	@WebMethod(operationName = "validateEntityProperty")
//	public void validateEntityProperty(TaPartSoustraitance value, String propertyName) {
//		validateEntityProperty(value,propertyName,null);
//	}
//
//}
