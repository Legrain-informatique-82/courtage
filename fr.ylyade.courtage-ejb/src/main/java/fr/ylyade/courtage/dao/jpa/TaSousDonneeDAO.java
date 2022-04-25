package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaSousDonneeDAO;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaSousDonnee;


public class TaSousDonneeDAO implements ITaSousDonneeDAO {

	private static final Logger logger = Logger.getLogger(TaSousDonneeDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaSousDonnee p";
	
	public TaSousDonneeDAO(){
//		this(null);
	}

//	public TaTaSousDonneeDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaSousDonnee.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaSousDonnee());
//	}


	public void persist(TaSousDonnee transientInstance) {
		logger.debug("persisting TaSousDonnee instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaSousDonnee persistentInstance) {
		logger.debug("removing TaSousDonnee instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdSousDonnee()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaSousDonnee merge(TaSousDonnee detachedInstance) {
		logger.debug("merging TaSousDonnee instance");
		try {
			TaSousDonnee result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaSousDonnee findById(int id) {
		logger.debug("getting TaSousDonnee instance with id: " + id);
		try {
			TaSousDonnee instance = entityManager.find(TaSousDonnee.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public TaSousDonnee findByIdDossierRcd(int idDossier) {
		try {
				Query query = entityManager.createQuery("select sd from TaSousDonnee sd where sd.taDossierRcd.idDossierRcd='"+idDossier+"'");
				TaSousDonnee instance = (TaSousDonnee)query.getSingleResult();
				logger.debug("get successful");
				return instance;
		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaSousDonnee> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaSousDonnee> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaSousDonnee entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaSousDonnee> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaSousDonnee> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaSousDonnee> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaSousDonnee> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaSousDonnee value) throws Exception {
		BeanValidator<TaSousDonnee> validator = new BeanValidator<TaSousDonnee>(TaSousDonnee.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaSousDonnee value, String propertyName) throws Exception {
		BeanValidator<TaSousDonnee> validator = new BeanValidator<TaSousDonnee>(TaSousDonnee.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaSousDonnee transientInstance) {
		entityManager.detach(transientInstance);
	}

	@Override
	public TaSousDonnee findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}

