package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaProjetGfaDAO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaProjetGfa;


public class TaProjetGfaDAO implements ITaProjetGfaDAO {

	private static final Logger logger = Logger.getLogger(TaProjetGfaDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaProjetGfa p";
	
	public TaProjetGfaDAO(){
//		this(null);
	}

//	public TaTaProjetGfaDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaProjetGfa.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaProjetGfa());
//	}


	public void persist(TaProjetGfa transientInstance) {
		logger.debug("persisting TaProjetGfa instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaProjetGfa persistentInstance) {
		logger.debug("removing TaProjetGfa instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdProjetGfa()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaProjetGfa merge(TaProjetGfa detachedInstance) {
		logger.debug("merging TaProjetGfa instance");
		try {
			TaProjetGfa result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaProjetGfa.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre de ProjetGfa actifs par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}

	public TaProjetGfa findById(int id) {
		logger.debug("getting TaProjetGfa instance with id: " + id);
		try {
			TaProjetGfa instance = entityManager.find(TaProjetGfa.class, id);
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
	public List<TaProjetGfa> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaProjetGfa> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaProjetGfa entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaProjetGfa> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaProjetGfa> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaProjetGfa> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaProjetGfa> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaProjetGfa value) throws Exception {
		BeanValidator<TaProjetGfa> validator = new BeanValidator<TaProjetGfa>(TaProjetGfa.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaProjetGfa value, String propertyName) throws Exception {
		BeanValidator<TaProjetGfa> validator = new BeanValidator<TaProjetGfa>(TaProjetGfa.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaProjetGfa transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaProjetGfa findByCode(String code) {
		logger.debug("getting TaProjetGfa instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaProjetGfa f where f.numProjetGfa='"+code+"'");
				TaProjetGfa instance = (TaProjetGfa)query.getSingleResult();
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

