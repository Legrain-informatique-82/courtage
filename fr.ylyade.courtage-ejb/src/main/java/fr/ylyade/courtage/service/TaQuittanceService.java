package fr.ylyade.courtage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;

import org.apache.log4j.Logger;
import org.hibernate.OptimisticLockException;

import fr.legrain.courtage.controle.service.interfaces.remote.general.ITaGenCodeExServiceRemote;
import fr.legrain.data.AbstractApplicationDAOServer;
import fr.ylyade.courtage.dao.ITaQuittanceDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaQuittanceDTO;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.TaQuittance;
import fr.ylyade.courtage.model.mapper.TaQuittanceMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaQuittanceServiceRemote;
import fr.ylyade.courtage.service.interfaces.remote.ITaQuittanceServiceRemote;


/**
 * Session Bean implementation class TaQuittanceBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaQuittanceService extends AbstractApplicationDAOServer<TaQuittance, TaQuittanceDTO> implements ITaQuittanceServiceRemote {

	static Logger logger = Logger.getLogger(TaQuittanceService.class);

	@Inject private ITaQuittanceDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;
	
	/**
	 * Default constructor. 
	 */
	public TaQuittanceService() {
		super(TaQuittance.class,TaQuittanceDTO.class);
	}

	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaQuittance.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaQuittance.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaQuittance.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//	private String defaultJPQLQuery = "select a from TaQuittance a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaQuittance transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaQuittance transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaQuittance persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdQuittance()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaQuittance merge(TaQuittance detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaQuittance merge(TaQuittance detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaQuittance findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaQuittance findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaQuittance> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaQuittanceDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaQuittanceDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaQuittance> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaQuittanceDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaQuittanceDTO entityToDTO(TaQuittance entity) {
//		TaQuittanceDTO dto = new TaQuittanceDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaQuittanceMapper mapper = new TaQuittanceMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaQuittanceDTO> listEntityToListDTO(List<TaQuittance> entity) {
		List<TaQuittanceDTO> l = new ArrayList<TaQuittanceDTO>();

		for (TaQuittance taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaQuittanceDTO> selectAllDTO() {
		System.out.println("List of TaQuittanceDTO EJB :");
		ArrayList<TaQuittanceDTO> liste = new ArrayList<TaQuittanceDTO>();

		List<TaQuittance> projects = selectAll();
		for(TaQuittance project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaQuittanceDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaQuittanceDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaQuittanceDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaQuittanceDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaQuittanceDTO dto, String validationContext) throws EJBException {
		try {
			TaQuittanceMapper mapper = new TaQuittanceMapper();
			TaQuittance entity = null;
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
	
	public void persistDTO(TaQuittanceDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaQuittanceDTO dto, String validationContext) throws CreateException {
		try {
			TaQuittanceMapper mapper = new TaQuittanceMapper();
			TaQuittance entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaQuittanceDTO dto) throws RemoveException {
		try {
			TaQuittanceMapper mapper = new TaQuittanceMapper();
			TaQuittance entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaQuittance refresh(TaQuittance persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaQuittance value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaQuittance value, String propertyName, String validationContext) {
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
	public void validateDTO(TaQuittanceDTO dto, String validationContext) {
		try {
			TaQuittanceMapper mapper = new TaQuittanceMapper();
			TaQuittance entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaQuittanceDTO> validator = new BeanValidator<TaQuittanceDTO>(TaQuittanceDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaQuittanceDTO dto, String propertyName, String validationContext) {
		try {
			TaQuittanceMapper mapper = new TaQuittanceMapper();
			TaQuittance entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaQuittanceDTO> validator = new BeanValidator<TaQuittanceDTO>(TaQuittanceDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaQuittanceDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaQuittanceDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaQuittance value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaQuittance value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
