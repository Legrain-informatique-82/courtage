package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaDocumentPdfDAO;
import fr.ylyade.courtage.model.TaDocumentPdf;


public class TaDocumentPdfDAO implements ITaDocumentPdfDAO {

	private static final Logger logger = Logger.getLogger(TaDocumentPdfDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaDocumentPdf p";
	
	public TaDocumentPdfDAO(){
//		this(null);
	}

//	public TaTaDocumentPdfDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaDocumentPdf.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaDocumentPdf());
//	}


	public void persist(TaDocumentPdf transientInstance) {
		logger.debug("persisting TaDocumentPdf instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDocumentPdf persistentInstance) {
		logger.debug("removing TaDocumentPdf instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdDocumentPdf()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDocumentPdf merge(TaDocumentPdf detachedInstance) {
		logger.debug("merging TaDocumentPdf instance");
		try {
			TaDocumentPdf result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDocumentPdf findById(int id) {
		logger.debug("getting TaDocumentPdf instance with id: " + id);
		try {
			TaDocumentPdf instance = entityManager.find(TaDocumentPdf.class, id);
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
	public List<TaDocumentPdf> selectAll() {
		logger.debug("selectAll TaDocumentPdf");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaDocumentPdf> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaDocumentPdf entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaDocumentPdf> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaDocumentPdf> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaDocumentPdf> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaDocumentPdf> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaDocumentPdf value) throws Exception {
		BeanValidator<TaDocumentPdf> validator = new BeanValidator<TaDocumentPdf>(TaDocumentPdf.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaDocumentPdf value, String propertyName) throws Exception {
		BeanValidator<TaDocumentPdf> validator = new BeanValidator<TaDocumentPdf>(TaDocumentPdf.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaDocumentPdf transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaDocumentPdf findByCode(String code) {
		logger.debug("getting TaDocumentPdf instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaDocumentPdf f where f.codeDoc='"+code+"'");
				TaDocumentPdf instance = (TaDocumentPdf)query.getSingleResult();
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

