package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaAccordTarifDAO;
import fr.ylyade.courtage.model.TaAccordTarif;


public class TaAccordTarifDAO implements ITaAccordTarifDAO {

	private static final Logger logger = Logger.getLogger(TaAccordTarifDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaAccordTarif p";
	
	public TaAccordTarifDAO(){
//		this(null);
	}

//	public TaTaAccordTarifDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaAccordTarif.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaAccordTarif());
//	}


	public void persist(TaAccordTarif transientInstance) {
		logger.debug("persisting TaAccordTarif instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAccordTarif persistentInstance) {
		logger.debug("removing TaAccordTarif instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdAccordeTarif()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAccordTarif merge(TaAccordTarif detachedInstance) {
		logger.debug("merging TaAccordTarif instance");
		try {
			TaAccordTarif result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAccordTarif findById(int id) {
		logger.debug("getting TaAccordTarif instance with id: " + id);
		try {
			TaAccordTarif instance = entityManager.find(TaAccordTarif.class, id);
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
	public List<TaAccordTarif> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaAccordTarif> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaAccordTarif entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaAccordTarif> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaAccordTarif> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaAccordTarif> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaAccordTarif> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaAccordTarif value) throws Exception {
		BeanValidator<TaAccordTarif> validator = new BeanValidator<TaAccordTarif>(TaAccordTarif.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaAccordTarif value, String propertyName) throws Exception {
		BeanValidator<TaAccordTarif> validator = new BeanValidator<TaAccordTarif>(TaAccordTarif.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaAccordTarif transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaAccordTarif findByCode(String code) {
		logger.debug("getting TaAccordTarif instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaAccordTarif f where f.codeAccordTarif='"+code+"'");
				TaAccordTarif instance = (TaAccordTarif)query.getSingleResult();
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

