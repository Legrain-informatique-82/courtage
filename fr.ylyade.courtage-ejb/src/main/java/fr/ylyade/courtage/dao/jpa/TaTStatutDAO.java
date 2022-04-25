package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaTStatutDAO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaEcheance;
import fr.ylyade.courtage.model.TaTStatut;


public class TaTStatutDAO implements ITaTStatutDAO {

	private static final Logger logger = Logger.getLogger(TaTStatutDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaTStatut p";
	
	public TaTStatutDAO(){
//		this(null);
	}

//	public TaTaTStatutDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaTStatut.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaTStatut());
//	}


	public void persist(TaTStatut transientInstance) {
		logger.debug("persisting TaTStatut instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaTStatut persistentInstance) {
		logger.debug("removing TaTStatut instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdTStatut()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaTStatut merge(TaTStatut detachedInstance) {
		logger.debug("merging TaTStatut instance");
		try {
			TaTStatut result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaTStatut findById(int id) {
		logger.debug("getting TaTStatut instance with id: " + id);
		try {
			TaTStatut instance = entityManager.find(TaTStatut.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<TaTStatut> findAllStatutByIdDossier(Integer idDossierRcd) {
		try {
			Query query = entityManager.createNamedQuery(TaTStatut.QN.FIND_ALL_STATUT_BY_ID_DOSSIER);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			
			@SuppressWarnings("unchecked")
			List<TaTStatut> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaTStatut> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaTStatut> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaTStatut entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaTStatut> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaTStatut> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaTStatut> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaTStatut> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaTStatut value) throws Exception {
		BeanValidator<TaTStatut> validator = new BeanValidator<TaTStatut>(TaTStatut.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaTStatut value, String propertyName) throws Exception {
		BeanValidator<TaTStatut> validator = new BeanValidator<TaTStatut>(TaTStatut.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaTStatut transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaTStatut findByCode(String code) {
		logger.debug("getting TaTStatut instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaTStatut f where f.codeTStatut='"+code+"'");
				TaTStatut instance = (TaTStatut)query.getSingleResult();
				logger.debug("get successful");
				return instance;
			}
			return null;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
}

