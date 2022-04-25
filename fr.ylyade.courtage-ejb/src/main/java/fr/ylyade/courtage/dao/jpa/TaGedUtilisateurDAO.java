package fr.ylyade.courtage.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.validators.BeanValidator;
import fr.ylyade.courtage.dao.ITaGedUtilisateurDAO;
import fr.ylyade.courtage.dto.TaGedUtilisateurDTO;
import fr.ylyade.courtage.model.TaGedUtilisateur;
import fr.ylyade.courtage.model.TaListeRefDoc;


public class TaGedUtilisateurDAO implements ITaGedUtilisateurDAO {

	private static final Logger logger = Logger.getLogger(TaGedUtilisateurDAO.class);
	
	@PersistenceContext(unitName = "ylyade")
	private EntityManager entityManager;

	private String defaultJPQLQuery = "select p from TaGedUtilisateur p";
	
	public TaGedUtilisateurDAO(){
//		this(null);
	}

//	public TaTaGedUtilisateurDAO(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		initChampId(TaGedUtilisateur.class);
//		setJPQLQuery(defaultJPQLQuery);
//		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
//		initNomTable(new TaGedUtilisateur());
//	}


	public void persist(TaGedUtilisateur transientInstance) {
		logger.debug("persisting TaGedUtilisateur instance");
		try {
			entityManager.persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TaGedUtilisateur persistentInstance) {
		logger.debug("removing TaGedUtilisateur instance");
		//boolean estRefPrix=false;
		try {
			entityManager.remove(findById(persistentInstance.getIdGedUtilisateur()));

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public TaGedUtilisateur merge(TaGedUtilisateur detachedInstance) {
		logger.debug("merging TaGedUtilisateur instance");
		try {
			TaGedUtilisateur result = entityManager.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaGedUtilisateur findById(int id) {
		logger.debug("getting TaGedUtilisateur instance with id: " + id);
		try {
			TaGedUtilisateur instance = entityManager.find(TaGedUtilisateur.class, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public long countNbDocAttenduPourCourtier(String codeTListeRefDoc) {
		try {
			Query query = entityManager.createQuery("select count (l) from TaListeRefDoc l join l.taTListeRefDoc r"
					+ " where r.codeTListeRefDoc ='court'");
			long resultat = (long)query.getSingleResult();
			return resultat;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
//	﻿SELECT COUNT(*) FROM ta_t_liste_ref_doc 
//	INNER JOIN ta_liste_ref_doc ON ta_liste_ref_doc.id_t_liste_ref_doc_ta_t_liste_ref_doc = ta_t_liste_ref_doc.id_t_liste_ref_doc
//	Where code_t_liste_ref_doc = 'court'
	
	public long countNbDocUploderPourCourtier(Integer idCourtier) {
		try {

			Query query = entityManager.createQuery("select count (l) from TaGedUtilisateur l"
					+" where l.taCourtier='"+idCourtier+"'");
			long resultat = (long)query.getSingleResult();
			return resultat;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	//SELECT COUNT(*) FROM ta_liste_ref_doc 
	//INNER JOIN ta_ged_utilisateur ON ta_liste_ref_doc.id_liste_ref_doc = ta_ged_utilisateur.id_liste_ref_doc_ta_liste_ref_doc
	//Where id_courtier_ta_courtier = 5
	
	public long countNbDocValiderPourCourtier(Integer idCourtier) {
		try {

			Query query = entityManager.createQuery("select count (l) from TaGedUtilisateur l"
					+" where l.taCourtier='"+idCourtier+"' and l.validationYlyade= 'TRUE'");
			long resultat = (long)query.getSingleResult();
			return resultat;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	//SELECT COUNT(*) FROM ta_liste_ref_doc 
	//INNER JOIN ta_ged_utilisateur ON ta_liste_ref_doc.id_liste_ref_doc = ta_ged_utilisateur.id_liste_ref_doc_ta_liste_ref_doc
	//Where id_courtier_ta_courtier = 5 AND validation_ylyade = true
	
	public TaGedUtilisateur findByIdDocAndByIdCourtier(Integer idDoc, Integer idCourtier) {
		try {
			Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.taCourtier='"+idCourtier+"' and f.taListeRefDoc='"+idDoc+"'");
			//select * from ta_ged_utilisateur  where id_courtier_ta_courtier= 3 and id_liste_ref_doc_ta_liste_ref_doc=1

			TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
			return instance ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	} 
	
	public List<TaGedUtilisateur> findAllByIdDossier(Integer idDossier) {
		try {
			Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.taDossierRcd.id='"+idDossier+"'");
			
			List<TaGedUtilisateur> l = query.getResultList();
			return l ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	}
	
	public TaGedUtilisateurDTO findByIdDocAndByIdCourtierDTO(Integer idDoc, Integer idCourtier) {
	try {
		Query query =entityManager.createNamedQuery(TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_COURTIER);

//		Query query = entityManager.createQuery("select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO("
//				+ " f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier,"
//				+ " f.commentaireCourtier, f.validationYlyade, f.commentaireYlyade, f.dateControleCourtier,"
//				+ " f.dateControleYlyade, f.dateDepot, f.taCourtier.idCourtier, f.taCourtier.codeCourtier, "
//				+ " 0, '', f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " //si document courtier, pas d'id et de code assuré
//				+ " from TaGedUtilisateur f"
//				+ " where f.taCourtier.idCourtier = :idCourtier and f.taListeRefDoc.idListeRefDoc = :idDoc");

		query.setParameter("idDoc", idDoc);
		query.setParameter("idCourtier", idCourtier);

		TaGedUtilisateurDTO instance = (TaGedUtilisateurDTO) query.getSingleResult();
		return instance ;

	} catch (RuntimeException re) {
		//logger.error("get failed", re);
		return null;
	}
} 
	
//	public TaGedUtilisateur findByIdDocAndByIdAssure(Integer idDoc, Integer idAssure) {
//		try {
//			Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.taAssure='"+idAssure+"' and f.taListeRefDoc='"+idDoc+"'");
//			//select * from ta_ged_utilisateur  where id_courtier_ta_courtier= 3 and id_liste_ref_doc_ta_liste_ref_doc=1
//
//			TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
//			return instance ;
//
//		} catch (RuntimeException re) {
//			//logger.error("get failed", re);
//			return null;
//		}
//	} 
	
//	public TaGedUtilisateurDTO findByIdDocAndByIdAssureDTO(Integer idDoc, Integer idAssure) {
//		try {
//			Query query =entityManager.createNamedQuery(TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_ASSURE);
//
////			Query query = entityManager.createQuery("select new fr.ylyade.courtage.dto.TaGedUtilisateurDTO("
////					+ " f.idGedUtilisateur, f.nomFichier, f.taille, f.typeMime, f.fichierDoc, f.validationCourtier,"
////					+ " f.commentaireCourtier, f.validationYlyade, f.commentaireYlyade, f.dateControleCourtier,"
////					+ " f.dateControleYlyade, f.dateDepot, " //si document assure, pas d'id et de code courtier
////					+ " f.taAssure.idAssure, f.taAssure.codeAssure, f.taListeRefDoc.idListeRefDoc, f.taListeRefDoc.codeListeRefDoc, f.taListeRefDoc.liblDoc ) " 
////					+ " from TaGedUtilisateur f"
////					+ " where f.taCourtier.idCourtier = :idAssure and f.taListeRefDoc.idListeRefDoc = :idDoc");
//
//			query.setParameter("idDoc", idDoc);
//			query.setParameter("idAssure", idAssure);
//
//			TaGedUtilisateurDTO instance = (TaGedUtilisateurDTO) query.getSingleResult();
//			return instance ;
//
//		} catch (RuntimeException re) {
//			//logger.error("get failed", re);
//			return null;
//		}
//	} 

	
	public TaGedUtilisateur findByIdDocAndByIdDevisRcPro(Integer idDoc, Integer idDossierRcd) {
		//logger.error("findByIdDocAndByIdRcPro A FINIR, modifier la table ou les ralations pour avoir un lien entre RC Pro et la GED");
		try {
			Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.taDossierRcd='"+idDossierRcd+"' and f.taListeRefDoc='"+idDoc+"'");
			//select * from ta_ged_utilisateur  where id_courtier_ta_courtier= 3 and id_liste_ref_doc_ta_liste_ref_doc=1

			TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
			return instance ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			re.printStackTrace();
			return null;
		}
	} 
	
	public TaGedUtilisateur findByCodeListeRefAndByIdDossier(String codeDoc, Integer idDossierRcd) {
		try {
			Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.taDossierRcd.idDossierRcd= :idDossierRcd and f.taListeRefDoc.codeListeRefDoc = :codeDoc");
			query.setParameter("codeDoc", codeDoc);
			query.setParameter("idDossierRcd", idDossierRcd);

			TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
			return instance ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			re.printStackTrace();
			return null;
		}
	} 
	
	public TaGedUtilisateurDTO findByIdDocAndByIdDevisRcProDTO(Integer idDoc, Integer idDossierRcd) {
		//logger.error("findByIdDocAndByIdDevisRcPro A FINIR, modifier la table ou les ralations pour avoir un lien entre RC Pro et la GED");
		try {
			Query query =entityManager.createNamedQuery(TaGedUtilisateur.QN.FIND_BY_ID_DOC_AND_BY_ID_DOSSIER_RCD);

			query.setParameter("idDoc", idDoc);
			query.setParameter("idDossierRcd", idDossierRcd);

			TaGedUtilisateurDTO instance = (TaGedUtilisateurDTO) query.getSingleResult();
			return instance ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	} 
	
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	// Jean marc Compter le nombre de document attendu pour un courtier
	
	public TaGedUtilisateur findByIdListeDocAndByIdCourtier(Integer idTlisteRefDoc) {
		try {
			Query query = entityManager.createQuery("select count f.idTlistRefDoc from taListeRefDoc where f.idTlistRefDoc='"+idTlisteRefDoc+"'");
			//select * from ta_ged_utilisateur  where id_courtier_ta_courtier= 3 and id_liste_ref_doc_ta_liste_ref_doc=1

			TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
			return instance ;

		} catch (RuntimeException re) {
			//logger.error("get failed", re);
			return null;
		}
	} 
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public List<TaGedUtilisateur> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = entityManager.createQuery(defaultJPQLQuery);
			List<TaGedUtilisateur> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaGedUtilisateur entity,String field) throws Exception {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<TaGedUtilisateur> findWithNamedQuery(String queryName) {
		try {
			Query ejbQuery = entityManager.createNamedQuery(queryName);
			List<TaGedUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public List<TaGedUtilisateur> findWithJPQLQuery(String JPQLquery) {
		try {
			Query ejbQuery = entityManager.createQuery(JPQLquery);
			List<TaGedUtilisateur> l = ejbQuery.getResultList();
			System.out.println("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			System.out.println("selectAll failed");
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public boolean validate(TaGedUtilisateur value) throws Exception {
		BeanValidator<TaGedUtilisateur> validator = new BeanValidator<TaGedUtilisateur>(TaGedUtilisateur.class);
		return validator.validate(value);
	}

	@Override
	public boolean validateField(TaGedUtilisateur value, String propertyName) throws Exception {
		BeanValidator<TaGedUtilisateur> validator = new BeanValidator<TaGedUtilisateur>(TaGedUtilisateur.class);
		return validator.validateField(value,propertyName);
	}

	@Override
	public void detach(TaGedUtilisateur transientInstance) {
		entityManager.detach(transientInstance);
	}
	
	public TaGedUtilisateur findByCode(String code) {
		logger.debug("getting TaGedUtilisateur instance with username: " + code);
		try {
			if(!code.equals("")){
				Query query = entityManager.createQuery("select f from TaGedUtilisateur f where f.codeGedUtilisateur='"+code+"'");
				TaGedUtilisateur instance = (TaGedUtilisateur)query.getSingleResult();
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

