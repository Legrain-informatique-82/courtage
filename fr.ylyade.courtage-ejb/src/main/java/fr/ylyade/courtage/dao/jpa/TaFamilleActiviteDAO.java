package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaFamilleActiviteDAO;
import fr.ylyade.courtage.model.TaFamilleActivite;


public class TaFamilleActiviteDAO implements ITaFamilleActiviteDAO {

	private static final Logger logger = Logger.getLogger(TaFamilleActiviteDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaFamilleActivite p";
	
	public TaFamilleActiviteDAO(){
//		this(null);
	}

//	public TaTaFamilleActiviteDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaFamilleActivite.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaFamilleActivite());
//	}


	public void persist(TaFamilleActivite transientInstance) {
		logger.debug("persisting TaFamilleActivite instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaFamilleActivite persistentInstance) {
		logger.debug("removing TaFamilleActivite instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdFamilleActivite()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaFamilleActivite merge(TaFamilleActivite detachedInstance) {
		logger.debug("merging TaFamilleActivite instance");
		try {
			TaFamilleActivite result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaFamilleActivite findById(int id) {
		logger.debug("getting TaFamilleActivite instance with id: " + id);
		try {
			TaFamilleActivite instance = entityManager.find(TaFamilleActivite.class, id);
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
	public List<TaFamilleActivite> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaFamilleActivite> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaFamilleActivite entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaFamilleActivite> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaFamilleActivite> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaFamilleActivite> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaFamilleActivite> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaFamilleActivite value) throws Exception {
		BeanValidator<TaFamilleActivite> validator = new BeanValidator<TaFamilleActivite>(TaFamilleActivite.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaFamilleActivite value, String propertyName) throws Exception {
		BeanValidator<TaFamilleActivite> validator = new BeanValidator<TaFamilleActivite>(TaFamilleActivite.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaFamilleActivite transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaFamilleActivite findByCode(String code) {
		logger.debug("getting TaFamilleActivite instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaFamilleActivite f where f.codeFamilleActivite='"+code+"'");
				TaFamilleActivite instance = (TaFamilleActivite)query.getSingleResult();
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

