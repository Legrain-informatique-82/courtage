package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;


import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaListeRefDocDAO;
import fr.ylyade.courtage.dto.TaListeRefDocDTO;
import fr.ylyade.courtage.model.TaListeRefDoc;


public class TaListeRefDocDAO implements ITaListeRefDocDAO {

	private static final Logger logger = Logger.getLogger(TaListeRefDocDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaListeRefDoc p";
	
	public TaListeRefDocDAO(){
//		this(null);
	}

//	public TaTaListeRefDocDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaListeRefDoc.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaListeRefDoc());
//	}


	public void persist(TaListeRefDoc transientInstance) {
		logger.debug("persisting TaListeRefDoc instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaListeRefDoc persistentInstance) {
		logger.debug("removing TaListeRefDoc instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdListeRefDoc()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaListeRefDoc merge(TaListeRefDoc detachedInstance) {
		logger.debug("merging TaListeRefDoc instance");
		try {
			TaListeRefDoc result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaListeRefDoc findById(int id) {
		logger.debug("getting TaListeRefDoc instance with id: " + id);
		try {
			TaListeRefDoc instance = entityManager.find(TaListeRefDoc.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaListeRefDoc> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaListeRefDoc> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaListeRefDoc entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaListeRefDoc> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaListeRefDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaListeRefDoc> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaListeRefDoc> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaListeRefDoc value) throws Exception {
		BeanValidator<TaListeRefDoc> validator = new BeanValidator<TaListeRefDoc>(TaListeRefDoc.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaListeRefDoc value, String propertyName) throws Exception {
		BeanValidator<TaListeRefDoc> validator = new BeanValidator<TaListeRefDoc>(TaListeRefDoc.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaListeRefDoc transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaListeRefDoc findByCode(String code) {
		logger.debug("getting TaListeRefDoc instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaListeRefDoc f where f.identifiant='"+code+"'");
				TaListeRefDoc instance = (TaListeRefDoc)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
//	public List<TaListeRefDoc> findByType(int idTListeRefDoc) {
//		logger.debug("getting TaListeRefDoc instance with type: " + idTListeRefDoc);
//		try {
//			
//				Query query = entityManager.createQuery("select f from TaListeRefDoc f where f.taTListeRefDoc.idTListeRefDoc='"+idTListeRefDoc+"'");
//				List<TaListeRefDoc> instance = (List<TaListeRefDoc>)query.getResultList();
//				logger.debug("get successful");
//				return instance;
//			
//			
//		} catch (RuntimeException re) {
//			//logger.error("get failed", re);
//			return null;
//		}
//	}
	
	@Override
	public List<TaListeRefDocDTO> findByType(int idTListeRefDoc) {
		try {
			Query query = entityManager.createNamedQuery(TaListeRefDoc.QN.FIND_BY_TYPE);
			query.setParameter("idTListeRefDoc", idTListeRefDoc);
			List<TaListeRefDocDTO> instance = query.getResultList();
			
			logger.debug("get successful");
			return instance;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaListeRefDocDTO> findByTypeNotInGedUtilisateur(int idDossierRcd, int idTListeRefDoc) {
		try {
			Query query = entityManager.createNamedQuery(TaListeRefDoc.QN.FIND_BY_TYPE_NOT_IN_GED_UTILISATEUR);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			query.setParameter("idTListeRefDoc", idTListeRefDoc);
			List<TaListeRefDocDTO> instance = query.getResultList();
			
			logger.debug("get successful");
			return instance;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public List<TaListeRefDocDTO> findByTypeInGedUtilisateur(int idDossierRcd, int idTListeRefDoc) {
		try {
			Query query = entityManager.createNamedQuery(TaListeRefDoc.QN.FIND_BY_TYPE_IN_GED_UTILISATEUR);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			query.setParameter("idTListeRefDoc", idTListeRefDoc);
			List<TaListeRefDocDTO> instance = query.getResultList();
			
			logger.debug("get successful");
			return instance;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}


	
	
	
}

