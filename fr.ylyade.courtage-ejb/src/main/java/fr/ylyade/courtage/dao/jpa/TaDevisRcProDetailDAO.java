package fr.ylyade.courtage.dao.jpa;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaDevisRcProDetailDAO;
import fr.ylyade.courtage.dto.TaDevisRcProDetailDTO;
import fr.ylyade.courtage.model.TaAssure;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaDossierRcd;
import fr.ylyade.courtage.model.TaGedUtilisateur;


public class TaDevisRcProDetailDAO implements ITaDevisRcProDetailDAO {

	private static final Logger logger = Logger.getLogger(TaDevisRcProDetailDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaDevisRcProDetail p";
	
	public TaDevisRcProDetailDAO(){
//		this(null);
	}

//	public TaTaDevisRcProDetailDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaDevisRcProDetail.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaDevisRcProDetail());
//	}


	public void persist(TaDossierRcd transientInstance) {
		logger.debug("persisting TaDevisRcProDetail instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaDossierRcd persistentInstance) {
		logger.debug("removing TaDevisRcProDetail instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdDossierRcd()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaDossierRcd merge(TaDossierRcd detachedInstance) {
		logger.debug("merging TaDevisRcProDetail instance");
		try {
			TaDossierRcd result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaDossierRcd findById(int id) {
		logger.debug("getting TaDevisRcProDetail instance with id: " + id);
		try {
			TaDossierRcd instance = entityManager.find(TaDossierRcd.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List<TaDossierRcd> findActiveByIdAssure(int idAssure, Boolean active) {
		Date now = new Date();
		
		if(active != null) {
			if (active==true) { /*Renvoi une liste des devis rcpro actif (donc qui n'ont PAS générés de contrat ou n'ont PAS dépassés la date d'échéance)*/
				try {
						Query query = entityManager.createQuery("select f from TaDevisRcProDetail f left join f.taDevisRcPro d left join d.taAssure a"
								+ " where a.idAssure="+idAssure+" and"
										+ " (f.taContratRcProDetail IS NULL or f.dateEcheance < :now  )" );
						query.setParameter("now" , now);
						List<TaDossierRcd> l = query.getResultList();
						logger.debug("get successful");
						return l;
				} catch (RuntimeException re) {
					return null;
				}
				
			} else {  /*Renvoi une liste des devis rcpro PAS actif (donc qui ont générés un contrat ou ont dépassés la date d'échéance)*/
				try {
					Query query = entityManager.createQuery("select f from TaDevisRcProDetail f left join f.taDevisRcPro d left join d.taAssure a"
							+ " where a.idAssure="+idAssure+" and"
							+ " (f.taContratRcProDetail IS NOT NULL or f.dateEcheance > :now )" );
					query.setParameter("now" , now);
					List<TaDossierRcd> l = query.getResultList();
					logger.debug("get successful");
					return l;
				} catch (RuntimeException re) {
					return null;
				}
	
			}
		}
		return null;
		
	}
	
	
	
	public List<TaDevisRcProDetailDTO> findActiveByIdAssureDTO(int idAssure, Boolean active) {
		Date now = new Date();
		
		if(active != null) {
			if (active==true) { /*Renvoi une liste des devis rcpro actif (donc qui n'ont PAS générés de contrat ou n'ont PAS dépassés la date d'échéance)*/
				try {
						Query query =entityManager.createNamedQuery(TaDossierRcd.QN.FIND_ACTIVE_BY_ID_ASSURE);
						
						query.setParameter("idAssure" , idAssure);
						query.setParameter("now" , now);
						List<TaDevisRcProDetailDTO> l = query.getResultList();
						logger.debug("get successful");
						return l;
				} catch (RuntimeException re) {
					return null;
				}
				
			} else {  /*Renvoi une liste des devis rcpro PAS actif (donc qui ont générés un contrat ou ont dépassés la date d'échéance)*/
				try {
					Query query = entityManager.createQuery(TaDossierRcd.QN.FIND_NON_ACTIVE_BY_ID_ASSURE);
					query.setParameter("idAssure" , idAssure);
					query.setParameter("now" , now);
					List<TaDevisRcProDetailDTO> l = query.getResultList();
					logger.debug("get successful");
					return l;
				} catch (RuntimeException re) {
					return null;
				}
	
			}
		}
		return null;
		
	}
	
	
	
	public Integer countActiveByIdCourtier(int idCourtier) {
		try {
			Query query = entityManager.createNamedQuery(TaDossierRcd.QN.COUNT_ACTIVE_BY_ID_COURTIER);/*Compte le nombre de DevisRcPro actifs par courtier*/
			
			query.setParameter("idCourtier", idCourtier);
			
			Integer instance = (Integer) query.getSingleResult();
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			return 0;
		}
	}
	
	
	

	
	
	
	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<TaDossierRcd> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaDossierRcd entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaDossierRcd> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaDossierRcd> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaDossierRcd> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaDossierRcd value) throws Exception {
		BeanValidator<TaDossierRcd> validator = new BeanValidator<TaDossierRcd>(TaDossierRcd.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaDossierRcd value, String propertyName) throws Exception {
		BeanValidator<TaDossierRcd> validator = new BeanValidator<TaDossierRcd>(TaDossierRcd.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaDossierRcd transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaDossierRcd findByCode(String code) {
		logger.debug("getting TaDevisRcProDetail instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaDevisRcProDetail f where f.codeDevisRcProDetail='"+code+"'");
				TaDossierRcd instance = (TaDossierRcd)query.getSingleResult();
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

