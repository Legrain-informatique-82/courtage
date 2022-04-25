package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaAttestationCompetencesDAO;
import fr.ylyade.courtage.model.TaAttestationCompetences;


public class TaAttestationCompetencesDAO implements ITaAttestationCompetencesDAO {

	private static final Logger logger = Logger.getLogger(TaAttestationCompetencesDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaAttestationCompetences p";
	
	public TaAttestationCompetencesDAO(){
//		this(null);
	}

//	public TaTaAttestationCompetencesDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaAttestationCompetences.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaAttestationCompetences());
//	}


	public void persist(TaAttestationCompetences transientInstance) {
		logger.debug("persisting TaAttestationCompetences instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAttestationCompetences persistentInstance) {
		logger.debug("removing TaAttestationCompetences instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdAttestationCompetences()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAttestationCompetences merge(TaAttestationCompetences detachedInstance) {
		logger.debug("merging TaAttestationCompetences instance");
		try {
			TaAttestationCompetences result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAttestationCompetences findById(int id) {
		logger.debug("getting TaAttestationCompetences instance with id: " + id);
		try {
			TaAttestationCompetences instance = entityManager.find(TaAttestationCompetences.class, id);
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
	public List<TaAttestationCompetences> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaAttestationCompetences> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaAttestationCompetences entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaAttestationCompetences> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaAttestationCompetences> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaAttestationCompetences> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaAttestationCompetences> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaAttestationCompetences value) throws Exception {
		BeanValidator<TaAttestationCompetences> validator = new BeanValidator<TaAttestationCompetences>(TaAttestationCompetences.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaAttestationCompetences value, String propertyName) throws Exception {
		BeanValidator<TaAttestationCompetences> validator = new BeanValidator<TaAttestationCompetences>(TaAttestationCompetences.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaAttestationCompetences transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaAttestationCompetences findByCode(String code) {
		logger.debug("getting TaAttestationCompetences instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaAttestationCompetences f where f.codeAttestationCompetences='"+code+"'");
				TaAttestationCompetences instance = (TaAttestationCompetences)query.getSingleResult();
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

