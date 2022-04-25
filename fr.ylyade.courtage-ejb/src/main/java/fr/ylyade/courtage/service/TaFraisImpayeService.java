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
import fr.ylyade.courtage.dao.ITaFraisImpayeDAO;
import fr.ylyade.courtage.droits.service.interfaces.remote.SessionInfo;
import fr.ylyade.courtage.droits.service.interfaces.remote.TenantInfo;
import fr.ylyade.courtage.dto.TaFraisImpayeDTO;

import fr.ylyade.courtage.model.TaFraisImpaye;
import fr.ylyade.courtage.model.mapper.TaFraisImpayeMapper;
import fr.ylyade.courtage.service.interfaces.remote.ITaFraisImpayeServiceRemote;


/**
 * Session Bean implementation class TaFraisImpayeBean
 */
@Stateless
//@Interceptors(ServerTenantInterceptor.class)
public class TaFraisImpayeService extends AbstractApplicationDAOServer<TaFraisImpaye, TaFraisImpayeDTO> implements ITaFraisImpayeServiceRemote {

	static Logger logger = Logger.getLogger(TaFraisImpayeService.class);

	@Inject private ITaFraisImpayeDAO dao;
	
	@Inject private	SessionInfo sessionInfo;
	
	@Inject private	TenantInfo tenantInfo;
	@EJB private ITaGenCodeExServiceRemote gencode;

	/**
	 * Default constructor. 
	 */
	public TaFraisImpayeService() {
		super(TaFraisImpaye.class,TaFraisImpayeDTO.class);
	}
	
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDDTO() {
		return dao.findAllFraisImpayeRCDDTO();
	}
	
	public List<TaFraisImpayeDTO> findAllFraisImpayeRCDIdDTO(Integer idRCD) {
		return dao.findAllFraisImpayeRCDIdDTO(idRCD);
	}
	
	public TaFraisImpayeDTO findFraisImpayeReglementRCDDTO(Integer idFraisImpayeRcd) {
		return dao.findFraisImpayeReglementRCDDTO(idFraisImpayeRcd);
	}
	
	@Override
	public String genereCode( Map<String, String> params) {
		try {
			return gencode.genereCodeJPA(TaFraisImpaye.class.getSimpleName(),params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOUVEAU CODE";
	}

	public void annuleCode(String code) {
		try {

			gencode.annulerCodeGenere(gencode.findByCode(TaFraisImpaye.class.getSimpleName()),code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verrouilleCode(String code) {
		try {
			gencode.rentreCodeGenere(gencode.findByCode(TaFraisImpaye.class.getSimpleName()),code, sessionInfo.getSessionID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//	private String defaultJPQLQuery = "select a from TaFraisImpaye a";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										ENTITY
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void persist(TaFraisImpaye transientInstance) throws CreateException {
		persist(transientInstance, null);
	}

	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@WebMethod(operationName = "persistValidationContext")
	public void persist(TaFraisImpaye transientInstance, String validationContext) throws CreateException {

		validateEntity(transientInstance, validationContext);

		dao.persist(transientInstance);
	}

	public void remove(TaFraisImpaye persistentInstance) throws RemoveException {
		try {
			dao.remove(findById(persistentInstance.getIdFraisImpaye()));
		} catch (FinderException e) {
			logger.error("", e);
		}
	}
	
	public TaFraisImpaye merge(TaFraisImpaye detachedInstance) {
		return merge(detachedInstance, null);
	}

	@Override
	@WebMethod(operationName = "mergeValidationContext")
	public TaFraisImpaye merge(TaFraisImpaye detachedInstance, String validationContext) {
		validateEntity(detachedInstance, validationContext);

		return dao.merge(detachedInstance);
	}

	public TaFraisImpaye findById(int id) throws FinderException {
		return dao.findById(id);
	}

	public TaFraisImpaye findByCode(String code) throws FinderException {
		return dao.findByCode(code);
	}

//	@RolesAllowed("admin")
	public List<TaFraisImpaye> selectAll() {
		return dao.selectAll();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 										DTO
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<TaFraisImpayeDTO> findWithNamedQueryDTO(String queryName) throws FinderException {
		return null;
	}

	@Override
	public List<TaFraisImpayeDTO> findWithJPQLQueryDTO(String JPQLquery) throws FinderException {
		List<TaFraisImpaye> entityList = dao.findWithJPQLQuery(JPQLquery);
		List<TaFraisImpayeDTO> l = null;
		if(entityList!=null) {
			l = listEntityToListDTO(entityList);
		}
		return l;
	}

	public TaFraisImpayeDTO entityToDTO(TaFraisImpaye entity) {
//		TaFraisImpayeDTO dto = new TaFraisImpayeDTO();
//		dto.setId(entity.getIdTCivilite());
//		dto.setCodeTCivilite(entity.getCodeTCivilite());
//		return dto;
		TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
		return mapper.mapEntityToDto(entity, null);
	}

	public List<TaFraisImpayeDTO> listEntityToListDTO(List<TaFraisImpaye> entity) {
		List<TaFraisImpayeDTO> l = new ArrayList<TaFraisImpayeDTO>();

		for (TaFraisImpaye taTCivilite : entity) {
			l.add(entityToDTO(taTCivilite));
		}

		return l;
	}

//	@RolesAllowed("admin")
	public List<TaFraisImpayeDTO> selectAllDTO() {
		System.out.println("List of TaFraisImpayeDTO EJB :");
		ArrayList<TaFraisImpayeDTO> liste = new ArrayList<TaFraisImpayeDTO>();

		List<TaFraisImpaye> projects = selectAll();
		for(TaFraisImpaye project : projects) {
			liste.add(entityToDTO(project));
		}

		return liste;
	}

	public TaFraisImpayeDTO findByIdDTO(int id) throws FinderException {
		return entityToDTO(findById(id));
	}

	public TaFraisImpayeDTO findByCodeDTO(String code) throws FinderException {
		return entityToDTO(findByCode(code));
	}

	@Override
	public void error(TaFraisImpayeDTO dto) {
		throw new EJBException("Test erreur EJB");
	}

	@Override
	public int selectCount() {
		return dao.selectAll().size();
		//return 0;
	}
	
	public void mergeDTO(TaFraisImpayeDTO dto) throws EJBException {
		mergeDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "mergeDTOValidationContext")
	public void mergeDTO(TaFraisImpayeDTO dto, String validationContext) throws EJBException {
		try {
			TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
			TaFraisImpaye entity = null;
			if(dto.getIdFraisImpaye()!=null) {
				entity = dao.findById(dto.getIdFraisImpaye());
				if(dto.getVersionObj()!=entity.getVersionObj()) {
					throw new OptimisticLockException(entity,
							"L'objet à été modifié depuis le dernier accés. Client ID : "+dto.getIdFraisImpaye()+" - Client Version objet : "+dto.getVersionObj()+" -Serveur Version Objet : "+entity.getVersionObj());
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
	
	public void persistDTO(TaFraisImpayeDTO dto) throws CreateException {
		persistDTO(dto, null);
	}

	@Override
	@WebMethod(operationName = "persistDTOValidationContext")
	public void persistDTO(TaFraisImpayeDTO dto, String validationContext) throws CreateException {
		try {
			TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
			TaFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			//dao.persist(entity);
			enregistrerPersist(entity, validationContext);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException(e.getMessage());
		}
	}

	@Override
	public void removeDTO(TaFraisImpayeDTO dto) throws RemoveException {
		try {
			TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
			TaFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			//dao.remove(entity);
			supprimer(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	protected TaFraisImpaye refresh(TaFraisImpaye persistentInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(operationName = "validateEntityValidationContext")
	public void validateEntity(TaFraisImpaye value, String validationContext) /*throws ExceptLgr*/ {
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
	public void validateEntityProperty(TaFraisImpaye value, String propertyName, String validationContext) {
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
	public void validateDTO(TaFraisImpayeDTO dto, String validationContext) {
		try {
			TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
			TaFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			validateEntity(entity,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaFraisImpayeDTO> validator = new BeanValidator<TaFraisImpayeDTO>(TaFraisImpayeDTO.class);
//			validator.validate(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	@WebMethod(operationName = "validateDTOPropertyValidationContext")
	public void validateDTOProperty(TaFraisImpayeDTO dto, String propertyName, String validationContext) {
		try {
			TaFraisImpayeMapper mapper = new TaFraisImpayeMapper();
			TaFraisImpaye entity = mapper.mapDtoToEntity(dto,null);
			validateEntityProperty(entity,propertyName,validationContext);
			
			//validation automatique via la JSR bean validation
//			BeanValidator<TaFraisImpayeDTO> validator = new BeanValidator<TaFraisImpayeDTO>(TaFraisImpayeDTO.class);
//			validator.validateField(dto,propertyName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}

	}
	
	@Override
	@WebMethod(operationName = "validateDTO")
	public void validateDTO(TaFraisImpayeDTO dto) {
		validateDTO(dto,null);
		
	}

	@Override
	@WebMethod(operationName = "validateDTOProperty")
	public void validateDTOProperty(TaFraisImpayeDTO dto, String propertyName) {
		validateDTOProperty(dto,propertyName,null);
		
	}

	@Override
	@WebMethod(operationName = "validateEntity")
	public void validateEntity(TaFraisImpaye value) {
		validateEntity(value,null);
	}

	@Override
	@WebMethod(operationName = "validateEntityProperty")
	public void validateEntityProperty(TaFraisImpaye value, String propertyName) {
		validateEntityProperty(value,propertyName,null);
	}

}
