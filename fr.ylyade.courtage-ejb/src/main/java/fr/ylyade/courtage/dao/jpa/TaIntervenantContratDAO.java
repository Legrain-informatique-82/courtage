package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaIntervenantContratDAO;
import fr.ylyade.courtage.model.TaIntervenantContrat;


public class TaIntervenantContratDAO implements ITaIntervenantContratDAO {

	private static final Logger logger = Logger.getLogger(TaIntervenantContratDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaIntervenantContrat p";
	
	public TaIntervenantContratDAO(){
//		this(null);
	}

//	public TaTaIntervenantContratDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaIntervenantContrat.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaIntervenantContrat());
//	}


	public void persist(TaIntervenantContrat transientInstance) {
		logger.debug("persisting TaIntervenantContrat instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaIntervenantContrat persistentInstance) {
		logger.debug("removing TaIntervenantContrat instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdIntervenantContrat()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaIntervenantContrat merge(TaIntervenantContrat detachedInstance) {
		logger.debug("merging TaIntervenantContrat instance");
		try {
			TaIntervenantContrat result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaIntervenantContrat findById(int id) {
		logger.debug("getting TaIntervenantContrat instance with id: " + id);
		try {
			TaIntervenantContrat instance = entityManager.find(TaIntervenantContrat.class, id);
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
	public List<TaIntervenantContrat> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaIntervenantContrat> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaIntervenantContrat entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaIntervenantContrat> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaIntervenantContrat> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaIntervenantContrat> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaIntervenantContrat> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaIntervenantContrat value) throws Exception {
		BeanValidator<TaIntervenantContrat> validator = new BeanValidator<TaIntervenantContrat>(TaIntervenantContrat.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaIntervenantContrat value, String propertyName) throws Exception {
		BeanValidator<TaIntervenantContrat> validator = new BeanValidator<TaIntervenantContrat>(TaIntervenantContrat.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaIntervenantContrat transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaIntervenantContrat findByCode(String code) {
		logger.debug("getting TaIntervenantContrat instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaIntervenantContrat f where f.identifiant='"+code+"'");
				TaIntervenantContrat instance = (TaIntervenantContrat)query.getSingleResult();
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

