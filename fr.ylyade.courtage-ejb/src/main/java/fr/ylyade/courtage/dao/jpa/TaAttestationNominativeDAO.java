package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaAttestationNominativeDAO;
import fr.ylyade.courtage.model.TaAttestationNominative;
import fr.ylyade.courtage.model.TaEcheance;


public class TaAttestationNominativeDAO implements ITaAttestationNominativeDAO {

	private static final Logger logger = Logger.getLogger(TaAttestationNominativeDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaAttestationNominative p";
	
	public TaAttestationNominativeDAO(){
//		this(null);
	}

//	public TaTaAttestationNominativeDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaAttestationNominative.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaAttestationNominative());
//	}


	public void persist(TaAttestationNominative transientInstance) {
		logger.debug("persisting TaAttestationNominative instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaAttestationNominative persistentInstance) {
		logger.debug("removing TaAttestationNominative instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdAttestationNominative()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaAttestationNominative merge(TaAttestationNominative detachedInstance) {
		logger.debug("merging TaAttestationNominative instance");
		try {
			TaAttestationNominative result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaAttestationNominative findById(int id) {
		logger.debug("getting TaAttestationNominative instance with id: " + id);
		try {
			TaAttestationNominative instance = entityManager.find(TaAttestationNominative.class, id);
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
	public List<TaAttestationNominative> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaAttestationNominative> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaAttestationNominative entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<TaAttestationNominative> findAllByIdDossier(Integer idDossierRcd) {
		try {
			Query query = entityManager.createNamedQuery(TaAttestationNominative.QN.FIND_ALL_BY_ID_DOSSIER);
			
			query.setParameter("idDossierRcd", idDossierRcd);
			
			@SuppressWarnings("unchecked")
			List<TaAttestationNominative> l = query.getResultList();
			logger.debug("get successful");
			return l;

		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	@Override
	public List<TaAttestationNominative> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaAttestationNominative> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaAttestationNominative> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaAttestationNominative> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaAttestationNominative value) throws Exception {
		BeanValidator<TaAttestationNominative> validator = new BeanValidator<TaAttestationNominative>(TaAttestationNominative.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaAttestationNominative value, String propertyName) throws Exception {
		BeanValidator<TaAttestationNominative> validator = new BeanValidator<TaAttestationNominative>(TaAttestationNominative.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaAttestationNominative transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaAttestationNominative findByCode(String code) {
		logger.debug("getting TaAttestationNominative instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaAttestationNominative f where f.codeAttestationNominative='"+code+"'");
				TaAttestationNominative instance = (TaAttestationNominative)query.getSingleResult();
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

