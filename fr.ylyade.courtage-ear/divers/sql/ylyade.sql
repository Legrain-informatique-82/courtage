------------------------------------------------------------
-- Table: ta_assure
------------------------------------------------------------
CREATE TABLE ta_assure(
    id_assure                             SERIAL NOT NULL ,
    prenom                                dlib100,
    nom                                   dlib100,
    titre_fonction                        dlib100,
    code_assure                           dlib100,
    forme_juridique                       dlib100,
    code_siret                            dlib100,
    code_siren                            dlib100,
    code_ape                              dlib100,
    tva_intracom                          dlib100,
    qualifications                        dlib100,
    diplome                               dlib100,
    raison_sociale                        dlib100,
    chiffre_affaire                       did9facult,
    date_creation                         date_heure_lgr,
    date_cloture_bilan                    date_heure_lgr,
    type_artisan_pib                      dlib100,
    identifiant_assure                    dlib100,
    password_assure                       dlib100,
    actif                                 boolean DEFAULT false,
    activite_principale                   dlib100,
    chiffre_affaire_exercice_anterieur    did9facult,
    chiffre_affaire_exercice_previsionnel did9facult,
    effectif_total_exercice_anterieur     did4,
    effectif_total_exercice               did4,
    id_courtier_ta_courtier               did_facultatif,
    id_t_civilite_ta_t_civilite           did_facultatif,
    id_utilisateur_ta_utilisateur         did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_assure PRIMARY KEY (id_assure)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_courtier
------------------------------------------------------------
CREATE TABLE ta_courtier(
    id_courtier                         SERIAL NOT NULL ,
    prenom                              dlib100,
    nom                                 dlib100,
    titre_fonction                      dlib100,
    code_courtier                       dlib100 NOT NULL ,
    forme_juridique                     dlib100 NOT NULL ,
    code_siret                          dlib100 NOT NULL ,
    code_siren                          dlib100 NOT NULL ,
    code_ape                            dlib100 NOT NULL ,
    tva_intracom                        dlib100 NOT NULL ,
    logo                                bytea,
    qualite                             dlib100,
    num_orias                           dlib100,
    nom_denomination_sociale            dlib100,
    type_agent_courtier                 dlib100,
    num_rcs                             dlib100,
    num_rc_pro                          dlib100,
    num_greffe                          dlib100,
    capital_social                      did9facult,
    identifiant_courtier                dlib100,
    password_courtier                   dlib100,
    actif                               boolean DEFAULT false,
    suspendu                            boolean DEFAULT false,
    effectif_total                      did4,
    date_accord_tarif_accorde_tarif     date_heure_lgr,
    id_t_groupe_tarif_ta_t_groupe_tarif did_facultatif,
    id_ylyade_ta_ylyade                 did_facultatif,
    id_utilisateur_ta_utilisateur       did_facultatif,
    id_t_civilite_ta_t_civilite         did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_courtier PRIMARY KEY (id_courtier)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_adresse
------------------------------------------------------------
CREATE TABLE ta_adresse(
    id_adresse                                  SERIAL NOT NULL ,
    adresse_ligne_1                             dlib255,
    adresse_ligne_2                             dlib255,
    adresse_ligne_3                             dlib255,
    code_postal                                 dlib100,
    ville                                       dlib100,
    pays                                        dlib100,
    id_t_adresse_ta_t_adresse                   did_facultatif,
    id_contact_entreprise_ta_contact_entreprise did_facultatif,
    id_courtier_ta_courtier                     did_facultatif,
    id_assure_ta_assure                         did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_adresse PRIMARY KEY (id_adresse)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_email
------------------------------------------------------------
CREATE TABLE ta_email(
    id_email                                    SERIAL NOT NULL ,
    adresse_email                               dlib100,
    id_t_email                                  did_facultatif,
    id_contact_entreprise_ta_contact_entreprise did_facultatif,
    id_t_email_ta_t_email                       did_facultatif,
    id_assure_ta_assure                         did_facultatif,
    id_courtier_ta_courtier                     did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_email PRIMARY KEY (id_email)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_tel
------------------------------------------------------------
CREATE TABLE ta_tel(
    id_tel                                      SERIAL NOT NULL ,
    numero_tel                                  dlib100,
    poste_tel                                   dlib100,
    id_contact_entreprise_ta_contact_entreprise did_facultatif,
    id_t_tel_ta_t_tel                           did_facultatif,
    id_assure_ta_assure                         did_facultatif,
    id_courtier_ta_courtier                     did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_tel PRIMARY KEY (id_tel)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_email
------------------------------------------------------------
CREATE TABLE ta_t_email(
    id_t_email   SERIAL NOT NULL ,
    code_t_email dlib100,
    libl_t_email dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_email PRIMARY KEY (id_t_email)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_tel
------------------------------------------------------------
CREATE TABLE ta_t_tel(
    id_t_tel   SERIAL NOT NULL ,
    code_t_tel dlib100,
    libl_t_tel dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_tel PRIMARY KEY (id_t_tel)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_adresse
------------------------------------------------------------
CREATE TABLE ta_t_adresse(
    id_t_adresse   SERIAL NOT NULL ,
    code_t_adresse dlib100,
    libl_t_adresse dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_adresse PRIMARY KEY (id_t_adresse)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_contact_entreprise
------------------------------------------------------------
CREATE TABLE ta_contact_entreprise(
    id_contact_entreprise       SERIAL NOT NULL ,
    nom                         dlib100,
    prenom                      dlib100,
    fonction                    dlib100,
    id_courtier_ta_courtier     did_facultatif,
    id_assure_ta_assure         did_facultatif,
    id_t_civilite_ta_t_civilite did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_contact_entreprise PRIMARY KEY (id_contact_entreprise)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_civilite
------------------------------------------------------------
CREATE TABLE ta_t_civilite(
    id_t_civilite   SERIAL NOT NULL ,
    code_t_civilite dlib100,
    libl_t_civilite dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_civilite PRIMARY KEY (id_t_civilite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_roles
------------------------------------------------------------
CREATE TABLE ta_roles(
    id_roles            SERIAL NOT NULL ,
    libl_role           dlib100,
    id_t_role_ta_t_role did4,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_roles PRIMARY KEY (id_roles)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_ylyade
------------------------------------------------------------
CREATE TABLE ta_ylyade(
    id_ylyade                     SERIAL NOT NULL ,
    nom                           dlib100,
    prenom                        dlib100,
    fonction                      dlib100,
    actif                         boolean DEFAULT false,
    id_utilisateur_ta_utilisateur did_facultatif,
    id_t_civilite_ta_t_civilite   did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_ylyade PRIMARY KEY (id_ylyade)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_groupe_tarif
------------------------------------------------------------
CREATE TABLE ta_t_groupe_tarif(
    id_t_groupe_tarif SERIAL NOT NULL ,
    code_groupe       dlib100,
    nom_groupe        dlib100,
    tarif             did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_groupe_tarif PRIMARY KEY (id_t_groupe_tarif)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_devis_rc_pro
------------------------------------------------------------
CREATE TABLE ta_devis_rc_pro(
    id_devis_rc_pro                     SERIAL NOT NULL ,
    id_devis_pere                       INTEGER,
    dernier_devis                       boolean DEFAULT false,
    version                             did4,
    code_assure                         dlib100,
    frais_rc_pro                        did9facult,
    taux_commission                     did9facult,
    prime_assurance                     did9facult,
    deja_assurer                        boolean DEFAULT false,
    contrat_en_cours                    boolean DEFAULT false,
    date_resiliation                    date_heure_lgr,
    resilie_non_paiement                boolean DEFAULT false,
    resilie_fausse_declaration          boolean DEFAULT false,
    nb_sinistre                         did4,
    montant_sinistre                    did9facult,
    reprise_passe                       boolean DEFAULT false,
    franchise                           NUMERIC (15,2),
    echeance_libl                       dlib100,
    pourcent_soustraitance              NUMERIC (15,2),
    img_devis_rc_pro                    bytea,
    date_realisation                    date_heure_lgr,
    montant_prime                       did9facult,
    id_assure_ta_assure                 did_facultatif,
    id_contrat_rc_pro_ta_contrat_rc_pro did_facultatif,
    id_taux_assurance_ta_taux_assurance did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_devis_rc_pro PRIMARY KEY (id_devis_rc_pro)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_historique_activite
------------------------------------------------------------
CREATE TABLE ta_historique_activite(
    id_historique_activite                    SERIAL NOT NULL ,
    activite                                  dlib100,
    classe_associe                            did4,
    franchise                                 did9facult,
    id_historique_classe_ta_historique_classe did_facultatif,
    id_devis_rc_pro_ta_devis_rc_pro           did_facultatif,
    id_activite_ta_activite                   did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_historique_activite PRIMARY KEY (id_historique_activite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_historique_classe
------------------------------------------------------------
CREATE TABLE ta_historique_classe(
    id_historique_classe                                    SERIAL NOT NULL ,
    classe                                                  did4 ,
    id_historique_palier_classe_ta_historique_palier_classe did_facultatif ,
    id_classe_ta_classe                                     did_facultatif ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_historique_classe PRIMARY KEY (id_historique_classe)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_historique_palier_classe
------------------------------------------------------------
CREATE TABLE ta_historique_palier_classe(
    id_historique_palier_classe SERIAL NOT NULL ,
    palier_montant_min          did9facult,
    palier_montant_max          did9facult,
    montant_prime_base          did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_historique_palier_classe PRIMARY KEY (id_historique_palier_classe)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_contrat_rc_pro
------------------------------------------------------------
CREATE TABLE ta_contrat_rc_pro(
    id_contrat_rc_pro               SERIAL NOT NULL ,
    version                         did4,
    resiliation                     boolean DEFAULT false,
    date_resiliation                date_heure_lgr,
    motif_resiliation               dlib100,
    id_pere                         INTEGER,
    contrat_actif                   boolean DEFAULT false,
    police                          dlib100,
    date_retractation               date_heure_lgr,
    retractation                    boolean DEFAULT false,
    img_contrat_rc_pro              bytea,
    montant_avenant                 did9facult,
    date_souscription               date_heure_lgr,
    date_activation_donne_statut    date_heure_lgr,
    id_statut_ta_statut             did_facultatif,
    id_devis_rc_pro_ta_devis_rc_pro did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_contrat_rc_pro PRIMARY KEY (id_contrat_rc_pro)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_projet_dommage_ouvrage
------------------------------------------------------------
CREATE TABLE ta_projet_dommage_ouvrage(
    id_projet_dommage_ouvrage                             SERIAL NOT NULL ,
    code_assure                                           dlib100,
    frais_souscription                                    did9facult ,
    taux_commission                                       did9facult ,
    prime_assurance                                       did9facult ,
    img_projet_dommage_ouvrage                            bytea ,
    pourcent_dommage_ouvrage                              did9facult ,
    montant_chantier                                      did9facult ,
    date_realisation                                      date_heure_lgr,
    id_assure_ta_assure                                   did_facultatif,
    id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_projet_dommage_ouvrage PRIMARY KEY (id_projet_dommage_ouvrage)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_contrat_dommage_ouvrage
------------------------------------------------------------
CREATE TABLE ta_contrat_dommage_ouvrage(
    id_contrat_dommage_ouvrage                          SERIAL NOT NULL ,
    contrat_actif                                       boolean DEFAULT false  NOT NULL ,
    police                                              dlib100,
    date_retractation                                   date_heure_lgr,
    retractation                                        boolean DEFAULT false,
    img_contrat_dommage_ouvrage                         bytea,
    date_souscription                                   date_heure_lgr,
    id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage did_facultatif,
    id_echeance_ta_echeance                             did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_contrat_dommage_ouvrage PRIMARY KEY (id_contrat_dommage_ouvrage)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_projet_gfa
------------------------------------------------------------
CREATE TABLE ta_projet_gfa(
    id_projet_gfa                 SERIAL NOT NULL ,
    code_assure                   dlib100,
    frais_souscription            did9facult,
    taux_commission               did9facult,
    prime_assurance               did9facult,
    img_projet_gfa                bytea,
    pourcent_gfa                  did9facult,
    montant_chantier_gfa          did9facult,
    date_realisation              date_heure_lgr,
    id_assure_ta_assure           did_facultatif,
    id_contrat_gfa_ta_contrat_gfa did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_projet_gfa PRIMARY KEY (id_projet_gfa)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_contrat_gfa
------------------------------------------------------------
CREATE TABLE ta_contrat_gfa(
    id_contrat_gfa              SERIAL NOT NULL ,
    contrat_actif               boolean DEFAULT false  NOT NULL ,
    police                      dlib100,
    date_retractation           date_heure_lgr,
    retractation                boolean DEFAULT false,
    img_contrat_gfa             bytea,
    date_souscription           date_heure_lgr,
    id_projet_gfa_ta_projet_gfa did_facultatif,
    id_echeance_ta_echeance     did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_contrat_gfa PRIMARY KEY (id_contrat_gfa)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_quittance
------------------------------------------------------------
CREATE TABLE ta_quittance(
    id_quittance                                    SERIAL NOT NULL ,
    code_prestation                                 dlib100 NOT NULL ,
    libl_prestation                                 dlib100 NOT NULL ,
    montant                                         did9facult  NOT NULL ,
    img_quittance                                   bytea,
    id_reglement_assurance_ta_reglement_assurance   did_facultatif,
    id_reglement_prestation_ta_reglement_prestation did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_quittance PRIMARY KEY (id_quittance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_reglement_assurance
------------------------------------------------------------
CREATE TABLE ta_reglement_assurance(
    id_reglement_assurance        SERIAL NOT NULL ,
    ref_reglement                 dlib100,
    validation_paiement           boolean DEFAULT false,
    defaut_paiement               boolean DEFAULT false,
    type_reglement                dlib100,
    date_reglement                date_heure_lgr,
    montant                       did9facult,
    id_quittance_ta_quittance     did_facultatif,
    id_t_reglement_ta_t_reglement did_facultatif,
    id_echeance_ta_echeance       did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_reglement_assurance PRIMARY KEY (id_reglement_assurance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_taux_assurance
------------------------------------------------------------
CREATE TABLE ta_taux_assurance(
    id_taux_assurance                     SERIAL NOT NULL ,
    antecedent_resilie_non_paiement       did9facult,
    antecedent_resilie_fausse_declaration did9facult,
    antecedent_reprise_passe              did9facult,
    dommage_ouvrage_taux_pourcent         did9facult,
    g_f_a_taux_pourcent                   did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_taux_assurance PRIMARY KEY (id_taux_assurance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_activite
------------------------------------------------------------
CREATE TABLE ta_activite(
    id_activite                             SERIAL NOT NULL ,
    activite                                dlib100,
    classe_associe                          did4,
    franchise                               did9facult,
    code_famille                            dlib100,
    actif                                   boolean DEFAULT false,
    id_famille_activite_ta_famille_activite did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_activite PRIMARY KEY (id_activite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_classe
------------------------------------------------------------
CREATE TABLE ta_classe(
    id_classe                         SERIAL NOT NULL ,
    classe                            did4,
    id_palier_classe_ta_palier_classe did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_classe PRIMARY KEY (id_classe)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_famille_activite
------------------------------------------------------------
CREATE TABLE ta_famille_activite(
    id_famille_activite SERIAL NOT NULL ,
    code_famille        dlib100,
    libl_famille        dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_famille_activite PRIMARY KEY (id_famille_activite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_palier_classe
------------------------------------------------------------
CREATE TABLE ta_palier_classe(
    id_palier_classe   SERIAL NOT NULL ,
    palier_montant_min did9facult,
    palier_montant_max did9facult,
    montant_prime_base did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_palier_classe PRIMARY KEY (id_palier_classe)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_franchise
------------------------------------------------------------
CREATE TABLE ta_t_franchise(
    id_t_franchise SERIAL NOT NULL ,
    montant        did9facult,
    taux_montant   did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_franchise PRIMARY KEY (id_t_franchise)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_echeance
------------------------------------------------------------
CREATE TABLE ta_t_echeance(
    id_t_echeance   SERIAL NOT NULL ,
    taux_echeance   did9facult,
    libl_t_echeance dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_echeance PRIMARY KEY (id_t_echeance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_sous_traitance
------------------------------------------------------------
CREATE TABLE ta_t_sous_traitance(
    id_t_sous_traitance SERIAL NOT NULL ,
    base_min            did4,
    base_max            did4,
    taux                did9facult,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_sous_traitance PRIMARY KEY (id_t_sous_traitance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_facturation
------------------------------------------------------------
CREATE TABLE ta_t_facturation(
    id_t_facturation   SERIAL NOT NULL ,
    montant_prestation did9facult,
    libl_facture       dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_facturation PRIMARY KEY (id_t_facturation)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_document_pdf
------------------------------------------------------------
CREATE TABLE ta_document_pdf(
    id_document_pdf   SERIAL NOT NULL ,
    numero_version    did4,
    img_doc           bytea,
    code_doc          dlib100,
    libl_doc          dlib100,
    description_doc   dlib100,
    id_t_doc_ta_t_doc did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint__ta_document_pdf PRIMARY KEY (id_document_pdf)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_ged_sinistre
------------------------------------------------------------
CREATE TABLE ta_ged_sinistre(
    id_ged_sinistre                     SERIAL NOT NULL ,
    img_doc_sinistre                    bytea,
    ref_sinistre                        dlib100,
    commentaire                         dlib100,
    id_t_ged_sinistre_ta_t_ged_sinistre did_facultatif,
    id_sinistre_ta_sinistre             did_facultatif,
    CONSTRAINT prk_constraint_ta_ged_sinistre PRIMARY KEY (id_ged_sinistre)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_ged_sinistre
------------------------------------------------------------
CREATE TABLE ta_t_ged_sinistre(
    id_t_ged_sinistre SERIAL NOT NULL ,
    code_doc          dlib100,
    libl_sinistre     dlib100,
    description       dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_ged_sinistre PRIMARY KEY (id_t_ged_sinistre)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_ged_utilisateur
------------------------------------------------------------
CREATE TABLE ta_ged_utilisateur(
    id_ged_utilisateur                SERIAL NOT NULL ,
    libl_doc                          bytea,
    validation_courtier               boolean DEFAULT false,
    rejet_courtier                    boolean DEFAULT false,
    motif_rejet_courtier              dlib100,
    validation_ylyade                 boolean DEFAULT false,
    rejet_ylyade                      boolean DEFAULT false,
    motif_rejet_ylyade                dlib100,
    commentaire                       dlib100,
    date_depot                        date_heure_lgr,
    id_courtier_ta_courtier           did_facultatif,
    id_assure_ta_assure               did_facultatif,
    id_liste_ref_doc_ta_liste_ref_doc did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_ged_utilisateur PRIMARY KEY (id_ged_utilisateur)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_sinistre
------------------------------------------------------------
CREATE TABLE ta_sinistre(
    id_sinistre         SERIAL NOT NULL ,
    ref_sinistre        dlib100,
    date_sinistre       date_heure_lgr  NOT NULL ,
    date_declaration    date_heure_lgr  NOT NULL ,
    code_assure         dlib100 NOT NULL ,
    code_assurance      dlib100,
    code_contrat        dlib100,
    libl_sinistre       dlib100,
    description         dlib100,
    cloture_sinistre    boolean DEFAULT false,
    id_assure_ta_assure did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_sinistre PRIMARY KEY (id_sinistre)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_statut
------------------------------------------------------------
CREATE TABLE ta_statut(
    id_statut               SERIAL NOT NULL ,
    statut                  dlib100,
    id_t_statut_ta_t_statut did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_statut PRIMARY KEY (id_statut)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_statut
------------------------------------------------------------
CREATE TABLE ta_t_statut(
    id_t_statut  SERIAL NOT NULL ,
    code_statut  dlib100,
    libl_statut  dlib100,
    duree_statut date_heure_lgr,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_statut PRIMARY KEY (id_t_statut)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_action_doc
------------------------------------------------------------
CREATE TABLE ta_t_action_doc(
    id_t_action_doc SERIAL NOT NULL ,
    libl_action     dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_action_doc PRIMARY KEY (id_t_action_doc)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_liste_ref_doc
------------------------------------------------------------
CREATE TABLE ta_liste_ref_doc(
    id_liste_ref_doc                      SERIAL NOT NULL ,
    libl_doc                              dlib100,
    description                           dlib100,
    id_t_liste_ref_doc_ta_t_liste_ref_doc did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_liste_ref_doc PRIMARY KEY (id_liste_ref_doc)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_doc
------------------------------------------------------------
CREATE TABLE ta_t_doc(
    id_t_doc                      SERIAL NOT NULL ,
    libl_doc                      dlib100,
    code_assurance                dlib100,
    description_doc               dlib100,
    id_t_assurance_ta_t_assurance did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_doc PRIMARY KEY (id_t_doc)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_echeance
------------------------------------------------------------
CREATE TABLE ta_echeance(
    id_echeance                                           SERIAL NOT NULL ,
    date_echeance                                         date_heure_lgr,
    montant_echeance                                      did9facult ,
    majoration_frais                                      did9facult ,
    avoir                                                 did9facult ,
    id_contrat_rc_pro_ta_contrat_rc_pro                   did_facultatif,
    id_reglement_assurance_ta_reglement_assurance         did_facultatif,
    id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage did_facultatif,
    id_contrat_gfa_ta_contrat_gfa                         did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_echeance PRIMARY KEY (id_echeance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_assurance
------------------------------------------------------------
CREATE TABLE ta_t_assurance(
    id_t_assurance   SERIAL NOT NULL ,
    libl_t_assurance dlib100,
    code_assurance   dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_assurance PRIMARY KEY (id_t_assurance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_reglement
------------------------------------------------------------
CREATE TABLE ta_t_reglement(
    id_t_reglement   SERIAL NOT NULL ,
    code_t_reglement dlib100,
    libl_t_reglement dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_reglement PRIMARY KEY (id_t_reglement)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_liste_ref_doc
------------------------------------------------------------
CREATE TABLE ta_t_liste_ref_doc(
    id_t_liste_ref_doc SERIAL NOT NULL ,
    libl_utilisateur   dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_liste_ref_doc PRIMARY KEY (id_t_liste_ref_doc)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_attestation_nominative
------------------------------------------------------------
CREATE TABLE ta_attestation_nominative(
    id_attestation_nominative           SERIAL NOT NULL ,
    date_effet                          date_heure_lgr,
    police_assurance                    dlib100,
    date_debut                          date_heure_lgr,
    date_fin                            date_heure_lgr,
    adresse_chantier                    dlib100,
    nom_maitre_ouvrage                  dlib100,
    date_ouverture_chantier             date_heure_lgr,
    nature_ouvrage                      dlib100,
    coup_total_construction_ttc         did9facult,
    date_reception_definitive           date_heure_lgr,
    date_intervention_assure            date_heure_lgr,
    qualite_assure                      dlib100,
    travaux_effectue                    dlib100,
    montant_marche_ht                   did9facult,
    effectif_chantier                   did4,
    img_nominative                      bytea,
    montant                             did9facult,
    id_contrat_rc_pro_ta_contrat_rc_pro did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_attestation_nominative PRIMARY KEY (id_attestation_nominative)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_courrier
------------------------------------------------------------
CREATE TABLE ta_courrier(
    id_courrier                     SERIAL NOT NULL ,
    libl_courrier                   dlib100,
    date_envoi                      date_heure_lgr,
    courrier                        bytea,
    id_t_action_doc_ta_t_action_doc did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_courrier PRIMARY KEY (id_courrier)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_reglement_prestation
------------------------------------------------------------
CREATE TABLE ta_reglement_prestation(
    id_reglement_prestation                           SERIAL NOT NULL ,
    ref_reglement                                     dlib100,
    validation_paiement                               boolean DEFAULT false,
    defaut_paiement                                   boolean DEFAULT false,
    type_reglement                                    dlib100,
    date_prestation                                   date_heure_lgr,
    montant                                           did9facult,
    id_quittance_ta_quittance                         did_facultatif,
    id_historique_prestation_ta_historique_prestation did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_reglement_prestation PRIMARY KEY (id_reglement_prestation)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_frais_impaye
------------------------------------------------------------
CREATE TABLE ta_t_frais_impaye(
    id_t_frais_impaye         SERIAL NOT NULL ,
    montant_frais_impaye      did9facult,
    libl_facture_frais_impaye dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_frais_impaye PRIMARY KEY (id_t_frais_impaye)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_historique_prestation
------------------------------------------------------------
CREATE TABLE ta_historique_prestation(
    id_historique_prestation                        SERIAL NOT NULL ,
    libl_prestation                                 dlib100 ,
    date_prestation                                 date_heure_lgr,
    id_contrat_rc_pro_ta_contrat_rc_pro             did_facultatif,
    id_reglement_prestation_ta_reglement_prestation did_facultatif,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_historique_prestation PRIMARY KEY (id_historique_prestation)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_utilisateur
------------------------------------------------------------
CREATE TABLE ta_utilisateur(
    id_utilisateur       SERIAL NOT NULL ,
    identifiant          dlib100,
    password             dlib100,
    date_derniere_visite date_heure_lgr,
    date_creation        date_heure_lgr,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_utilisateur PRIMARY KEY (id_utilisateur)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ta_t_role
------------------------------------------------------------
CREATE TABLE ta_t_role(
    id_t_role   SERIAL NOT NULL ,
    libl_t_role dlib100,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_ta_t_role PRIMARY KEY (id_t_role)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: donne_role
------------------------------------------------------------
CREATE TABLE donne_role(
    id_roles_ta_roles             did_facultatif  NOT NULL ,
    id_utilisateur_ta_utilisateur did_facultatif  NOT NULL ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_donne_role PRIMARY KEY (id_roles_ta_roles,id_utilisateur_ta_utilisateur)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: appliquer_taux
------------------------------------------------------------
CREATE TABLE appliquer_taux(
    id_taux_assurance_ta_taux_assurance                 did_facultatif  NOT NULL ,
    id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage did_facultatif  NOT NULL ,
    id_projet_gfa_ta_projet_gfa                         did_facultatif  NOT NULL ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_appliquer_taux PRIMARY KEY (id_taux_assurance_ta_taux_assurance,id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage,id_projet_gfa_ta_projet_gfa)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: possede_action_selon
------------------------------------------------------------
CREATE TABLE possede_action_selon(
    id_t_action_doc_ta_t_action_doc did4  NOT NULL ,
    id_t_assurance_ta_t_assurance   did4  NOT NULL ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_possede_action_selon PRIMARY KEY (id_t_action_doc_ta_t_action_doc,id_t_assurance_ta_t_assurance)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: possede_type_assurance
------------------------------------------------------------
CREATE TABLE possede_type_assurance(
    id_t_assurance_ta_t_assurance                       did_facultatif  NOT NULL ,
    id_devis_rc_pro_ta_devis_rc_pro                     did_facultatif  NOT NULL ,
    id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage did_facultatif  NOT NULL ,
    id_projet_gfa_ta_projet_gfa                         did_facultatif  NOT NULL ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_possede_type_assurance PRIMARY KEY (id_t_assurance_ta_t_assurance,id_devis_rc_pro_ta_devis_rc_pro,id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage,id_projet_gfa_ta_projet_gfa)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: possede
------------------------------------------------------------
CREATE TABLE possede(
    id_t_action_doc_ta_t_action_doc did_facultatif  NOT NULL ,
    id_t_statut_ta_t_statut         did_facultatif  NOT NULL ,
    qui_cree 		dlib50,
  	quand_cree 		date_heure_lgr,
  	qui_modif 		dlib50,
  	quand_modif 	date_heure_lgr,
  	ip_acces 		dlib50,
  	version_obj 	did_version_obj,
    CONSTRAINT prk_constraint_possede PRIMARY KEY (id_t_action_doc_ta_t_action_doc,id_t_statut_ta_t_statut)
)WITHOUT OIDS;



ALTER TABLE ta_assure ADD CONSTRAINT FK_ta_assure_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_assure ADD CONSTRAINT FK_ta_assure_id_t_civilite_ta_t_civilite FOREIGN KEY (id_t_civilite_ta_t_civilite) REFERENCES ta_t_civilite(id_t_civilite);
ALTER TABLE ta_assure ADD CONSTRAINT FK_ta_assure_id_utilisateur_ta_utilisateur FOREIGN KEY (id_utilisateur_ta_utilisateur) REFERENCES ta_utilisateur(id_utilisateur);
ALTER TABLE ta_courtier ADD CONSTRAINT FK_ta_courtier_id_t_groupe_tarif_ta_t_groupe_tarif FOREIGN KEY (id_t_groupe_tarif_ta_t_groupe_tarif) REFERENCES ta_t_groupe_tarif(id_t_groupe_tarif);
ALTER TABLE ta_courtier ADD CONSTRAINT FK_ta_courtier_id_ylyade_ta_ylyade FOREIGN KEY (id_ylyade_ta_ylyade) REFERENCES ta_ylyade(id_ylyade);
ALTER TABLE ta_courtier ADD CONSTRAINT FK_ta_courtier_id_utilisateur_ta_utilisateur FOREIGN KEY (id_utilisateur_ta_utilisateur) REFERENCES ta_utilisateur(id_utilisateur);
ALTER TABLE ta_courtier ADD CONSTRAINT FK_ta_courtier_id_t_civilite_ta_t_civilite FOREIGN KEY (id_t_civilite_ta_t_civilite) REFERENCES ta_t_civilite(id_t_civilite);
ALTER TABLE ta_adresse ADD CONSTRAINT FK_ta_adresse_id_t_adresse_ta_t_adresse FOREIGN KEY (id_t_adresse_ta_t_adresse) REFERENCES ta_t_adresse(id_t_adresse);
ALTER TABLE ta_adresse ADD CONSTRAINT FK_ta_adresse_id_contact_entreprise_ta_contact_entreprise FOREIGN KEY (id_contact_entreprise_ta_contact_entreprise) REFERENCES ta_contact_entreprise(id_contact_entreprise);
ALTER TABLE ta_adresse ADD CONSTRAINT FK_ta_adresse_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_adresse ADD CONSTRAINT FK_ta_adresse_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_email ADD CONSTRAINT FK_ta_email_id_contact_entreprise_ta_contact_entreprise FOREIGN KEY (id_contact_entreprise_ta_contact_entreprise) REFERENCES ta_contact_entreprise(id_contact_entreprise);
ALTER TABLE ta_email ADD CONSTRAINT FK_ta_email_id_t_email_ta_t_email FOREIGN KEY (id_t_email_ta_t_email) REFERENCES ta_t_email(id_t_email);
ALTER TABLE ta_email ADD CONSTRAINT FK_ta_email_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_email ADD CONSTRAINT FK_ta_email_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_tel ADD CONSTRAINT FK_ta_tel_id_contact_entreprise_ta_contact_entreprise FOREIGN KEY (id_contact_entreprise_ta_contact_entreprise) REFERENCES ta_contact_entreprise(id_contact_entreprise);
ALTER TABLE ta_tel ADD CONSTRAINT FK_ta_tel_id_t_tel_ta_t_tel FOREIGN KEY (id_t_tel_ta_t_tel) REFERENCES ta_t_tel(id_t_tel);
ALTER TABLE ta_tel ADD CONSTRAINT FK_ta_tel_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_tel ADD CONSTRAINT FK_ta_tel_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_contact_entreprise ADD CONSTRAINT FK_ta_contact_entreprise_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_contact_entreprise ADD CONSTRAINT FK_ta_contact_entreprise_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_contact_entreprise ADD CONSTRAINT FK_ta_contact_entreprise_id_t_civilite_ta_t_civilite FOREIGN KEY (id_t_civilite_ta_t_civilite) REFERENCES ta_t_civilite(id_t_civilite);
ALTER TABLE ta_roles ADD CONSTRAINT FK_ta_roles_id_t_role_ta_t_role FOREIGN KEY (id_t_role_ta_t_role) REFERENCES ta_t_role(id_t_role);
ALTER TABLE ta_ylyade ADD CONSTRAINT FK_ta_ylyade_id_utilisateur_ta_utilisateur FOREIGN KEY (id_utilisateur_ta_utilisateur) REFERENCES ta_utilisateur(id_utilisateur);
ALTER TABLE ta_ylyade ADD CONSTRAINT FK_ta_ylyade_id_t_civilite_ta_t_civilite FOREIGN KEY (id_t_civilite_ta_t_civilite) REFERENCES ta_t_civilite(id_t_civilite);
ALTER TABLE ta_devis_rc_pro ADD CONSTRAINT FK_ta_devis_rc_pro_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_devis_rc_pro ADD CONSTRAINT FK_ta_devis_rc_pro_id_contrat_rc_pro_ta_contrat_rc_pro FOREIGN KEY (id_contrat_rc_pro_ta_contrat_rc_pro) REFERENCES ta_contrat_rc_pro(id_contrat_rc_pro);
ALTER TABLE ta_devis_rc_pro ADD CONSTRAINT FK_ta_devis_rc_pro_id_taux_assurance_ta_taux_assurance FOREIGN KEY (id_taux_assurance_ta_taux_assurance) REFERENCES ta_taux_assurance(id_taux_assurance);
ALTER TABLE ta_historique_activite ADD CONSTRAINT FK_ta_historique_activite_id_historique_classe_ta_historique_classe FOREIGN KEY (id_historique_classe_ta_historique_classe) REFERENCES ta_historique_classe(id_historique_classe);
ALTER TABLE ta_historique_activite ADD CONSTRAINT FK_ta_historique_activite_id_devis_rc_pro_ta_devis_rc_pro FOREIGN KEY (id_devis_rc_pro_ta_devis_rc_pro) REFERENCES ta_devis_rc_pro(id_devis_rc_pro);
ALTER TABLE ta_historique_activite ADD CONSTRAINT FK_ta_historique_activite_id_activite_ta_activite FOREIGN KEY (id_activite_ta_activite) REFERENCES ta_activite(id_activite);
ALTER TABLE ta_historique_classe ADD CONSTRAINT FK_ta_historique_classe_id_historique_palier_classe_ta_historique_palier_classe FOREIGN KEY (id_historique_palier_classe_ta_historique_palier_classe) REFERENCES ta_historique_palier_classe(id_historique_palier_classe);
ALTER TABLE ta_historique_classe ADD CONSTRAINT FK_ta_historique_classe_id_classe_ta_classe FOREIGN KEY (id_classe_ta_classe) REFERENCES ta_classe(id_classe);
ALTER TABLE ta_contrat_rc_pro ADD CONSTRAINT FK_ta_contrat_rc_pro_id_statut_ta_statut FOREIGN KEY (id_statut_ta_statut) REFERENCES ta_statut(id_statut);
ALTER TABLE ta_contrat_rc_pro ADD CONSTRAINT FK_ta_contrat_rc_pro_id_devis_rc_pro_ta_devis_rc_pro FOREIGN KEY (id_devis_rc_pro_ta_devis_rc_pro) REFERENCES ta_devis_rc_pro(id_devis_rc_pro);
ALTER TABLE ta_projet_dommage_ouvrage ADD CONSTRAINT FK_ta_projet_dommage_ouvrage_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_projet_dommage_ouvrage ADD CONSTRAINT FK_ta_projet_dommage_ouvrage_id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage FOREIGN KEY (id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage) REFERENCES ta_contrat_dommage_ouvrage(id_contrat_dommage_ouvrage);
ALTER TABLE ta_contrat_dommage_ouvrage ADD CONSTRAINT FK_ta_contrat_dommage_ouvrage_id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage FOREIGN KEY (id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage) REFERENCES ta_projet_dommage_ouvrage(id_projet_dommage_ouvrage);
ALTER TABLE ta_contrat_dommage_ouvrage ADD CONSTRAINT FK_ta_contrat_dommage_ouvrage_id_echeance_ta_echeance FOREIGN KEY (id_echeance_ta_echeance) REFERENCES ta_echeance(id_echeance);
ALTER TABLE ta_projet_gfa ADD CONSTRAINT FK_ta_projet_gfa_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_projet_gfa ADD CONSTRAINT FK_ta_projet_gfa_id_contrat_gfa_ta_contrat_gfa FOREIGN KEY (id_contrat_gfa_ta_contrat_gfa) REFERENCES ta_contrat_gfa(id_contrat_gfa);
ALTER TABLE ta_contrat_gfa ADD CONSTRAINT FK_ta_contrat_gfa_id_projet_gfa_ta_projet_gfa FOREIGN KEY (id_projet_gfa_ta_projet_gfa) REFERENCES ta_projet_gfa(id_projet_gfa);
ALTER TABLE ta_contrat_gfa ADD CONSTRAINT FK_ta_contrat_gfa_id_echeance_ta_echeance FOREIGN KEY (id_echeance_ta_echeance) REFERENCES ta_echeance(id_echeance);
ALTER TABLE ta_quittance ADD CONSTRAINT FK_ta_quittance_id_reglement_assurance_ta_reglement_assurance FOREIGN KEY (id_reglement_assurance_ta_reglement_assurance) REFERENCES ta_reglement_assurance(id_reglement_assurance);
ALTER TABLE ta_quittance ADD CONSTRAINT FK_ta_quittance_id_reglement_prestation_ta_reglement_prestation FOREIGN KEY (id_reglement_prestation_ta_reglement_prestation) REFERENCES ta_reglement_prestation(id_reglement_prestation);
ALTER TABLE ta_reglement_assurance ADD CONSTRAINT FK_ta_reglement_assurance_id_quittance_ta_quittance FOREIGN KEY (id_quittance_ta_quittance) REFERENCES ta_quittance(id_quittance);
ALTER TABLE ta_reglement_assurance ADD CONSTRAINT FK_ta_reglement_assurance_id_t_reglement_ta_t_reglement FOREIGN KEY (id_t_reglement_ta_t_reglement) REFERENCES ta_t_reglement(id_t_reglement);
ALTER TABLE ta_reglement_assurance ADD CONSTRAINT FK_ta_reglement_assurance_id_echeance_ta_echeance FOREIGN KEY (id_echeance_ta_echeance) REFERENCES ta_echeance(id_echeance);
ALTER TABLE ta_activite ADD CONSTRAINT FK_ta_activite_id_famille_activite_ta_famille_activite FOREIGN KEY (id_famille_activite_ta_famille_activite) REFERENCES ta_famille_activite(id_famille_activite);
ALTER TABLE ta_classe ADD CONSTRAINT FK_ta_classe_id_palier_classe_ta_palier_classe FOREIGN KEY (id_palier_classe_ta_palier_classe) REFERENCES ta_palier_classe(id_palier_classe);
ALTER TABLE ta_document_pdf ADD CONSTRAINT FK__ta_document_pdf_id_t_doc_ta_t_doc FOREIGN KEY (id_t_doc_ta_t_doc) REFERENCES ta_t_doc(id_t_doc);
ALTER TABLE ta_ged_sinistre ADD CONSTRAINT FK_ta_ged_sinistre_id_t_ged_sinistre_ta_t_ged_sinistre FOREIGN KEY (id_t_ged_sinistre_ta_t_ged_sinistre) REFERENCES ta_t_ged_sinistre(id_t_ged_sinistre);
ALTER TABLE ta_ged_sinistre ADD CONSTRAINT FK_ta_ged_sinistre_id_sinistre_ta_sinistre FOREIGN KEY (id_sinistre_ta_sinistre) REFERENCES ta_sinistre(id_sinistre);
ALTER TABLE ta_ged_utilisateur ADD CONSTRAINT FK_ta_ged_utilisateur_id_courtier_ta_courtier FOREIGN KEY (id_courtier_ta_courtier) REFERENCES ta_courtier(id_courtier);
ALTER TABLE ta_ged_utilisateur ADD CONSTRAINT FK_ta_ged_utilisateur_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_ged_utilisateur ADD CONSTRAINT FK_ta_ged_utilisateur_id_liste_ref_doc_ta_liste_ref_doc FOREIGN KEY (id_liste_ref_doc_ta_liste_ref_doc) REFERENCES ta_liste_ref_doc(id_liste_ref_doc);
ALTER TABLE ta_sinistre ADD CONSTRAINT FK_ta_sinistre_id_assure_ta_assure FOREIGN KEY (id_assure_ta_assure) REFERENCES ta_assure(id_assure);
ALTER TABLE ta_statut ADD CONSTRAINT FK_ta_statut_id_t_statut_ta_t_statut FOREIGN KEY (id_t_statut_ta_t_statut) REFERENCES ta_t_statut(id_t_statut);
ALTER TABLE ta_liste_ref_doc ADD CONSTRAINT FK_ta_liste_ref_doc_id_t_liste_ref_doc_ta_t_liste_ref_doc FOREIGN KEY (id_t_liste_ref_doc_ta_t_liste_ref_doc) REFERENCES ta_t_liste_ref_doc(id_t_liste_ref_doc);
ALTER TABLE ta_t_doc ADD CONSTRAINT FK_ta_t_doc_id_t_assurance_ta_t_assurance FOREIGN KEY (id_t_assurance_ta_t_assurance) REFERENCES ta_t_assurance(id_t_assurance);
ALTER TABLE ta_echeance ADD CONSTRAINT FK_ta_echeance_id_contrat_rc_pro_ta_contrat_rc_pro FOREIGN KEY (id_contrat_rc_pro_ta_contrat_rc_pro) REFERENCES ta_contrat_rc_pro(id_contrat_rc_pro);
ALTER TABLE ta_echeance ADD CONSTRAINT FK_ta_echeance_id_reglement_assurance_ta_reglement_assurance FOREIGN KEY (id_reglement_assurance_ta_reglement_assurance) REFERENCES ta_reglement_assurance(id_reglement_assurance);
ALTER TABLE ta_echeance ADD CONSTRAINT FK_ta_echeance_id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage FOREIGN KEY (id_contrat_dommage_ouvrage_ta_contrat_dommage_ouvrage) REFERENCES ta_contrat_dommage_ouvrage(id_contrat_dommage_ouvrage);
ALTER TABLE ta_echeance ADD CONSTRAINT FK_ta_echeance_id_contrat_gfa_ta_contrat_gfa FOREIGN KEY (id_contrat_gfa_ta_contrat_gfa) REFERENCES ta_contrat_gfa(id_contrat_gfa);
ALTER TABLE ta_attestation_nominative ADD CONSTRAINT FK_ta_attestation_nominative_id_contrat_rc_pro_ta_contrat_rc_pro FOREIGN KEY (id_contrat_rc_pro_ta_contrat_rc_pro) REFERENCES ta_contrat_rc_pro(id_contrat_rc_pro);
ALTER TABLE ta_courrier ADD CONSTRAINT FK_ta_courrier_id_t_action_doc_ta_t_action_doc FOREIGN KEY (id_t_action_doc_ta_t_action_doc) REFERENCES ta_t_action_doc(id_t_action_doc);
ALTER TABLE ta_reglement_prestation ADD CONSTRAINT FK_ta_reglement_prestation_id_quittance_ta_quittance FOREIGN KEY (id_quittance_ta_quittance) REFERENCES ta_quittance(id_quittance);
ALTER TABLE ta_reglement_prestation ADD CONSTRAINT FK_ta_reglement_prestation_id_historique_prestation_ta_historique_prestation FOREIGN KEY (id_historique_prestation_ta_historique_prestation) REFERENCES ta_historique_prestation(id_historique_prestation);
ALTER TABLE ta_historique_prestation ADD CONSTRAINT FK_ta_historique_prestation_id_contrat_rc_pro_ta_contrat_rc_pro FOREIGN KEY (id_contrat_rc_pro_ta_contrat_rc_pro) REFERENCES ta_contrat_rc_pro(id_contrat_rc_pro);
ALTER TABLE ta_historique_prestation ADD CONSTRAINT FK_ta_historique_prestation_id_reglement_prestation_ta_reglement_prestation FOREIGN KEY (id_reglement_prestation_ta_reglement_prestation) REFERENCES ta_reglement_prestation(id_reglement_prestation);
ALTER TABLE donne_role ADD CONSTRAINT FK_donne_role_id_roles_ta_roles FOREIGN KEY (id_roles_ta_roles) REFERENCES ta_roles(id_roles);
ALTER TABLE donne_role ADD CONSTRAINT FK_donne_role_id_utilisateur_ta_utilisateur FOREIGN KEY (id_utilisateur_ta_utilisateur) REFERENCES ta_utilisateur(id_utilisateur);
ALTER TABLE appliquer_taux ADD CONSTRAINT FK_appliquer_taux_id_taux_assurance_ta_taux_assurance FOREIGN KEY (id_taux_assurance_ta_taux_assurance) REFERENCES ta_taux_assurance(id_taux_assurance);
ALTER TABLE appliquer_taux ADD CONSTRAINT FK_appliquer_taux_id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage FOREIGN KEY (id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage) REFERENCES ta_projet_dommage_ouvrage(id_projet_dommage_ouvrage);
ALTER TABLE appliquer_taux ADD CONSTRAINT FK_appliquer_taux_id_projet_gfa_ta_projet_gfa FOREIGN KEY (id_projet_gfa_ta_projet_gfa) REFERENCES ta_projet_gfa(id_projet_gfa);
ALTER TABLE possede_action_selon ADD CONSTRAINT FK_possede_action_selon_id_t_action_doc_ta_t_action_doc FOREIGN KEY (id_t_action_doc_ta_t_action_doc) REFERENCES ta_t_action_doc(id_t_action_doc);
ALTER TABLE possede_action_selon ADD CONSTRAINT FK_possede_action_selon_id_t_assurance_ta_t_assurance FOREIGN KEY (id_t_assurance_ta_t_assurance) REFERENCES ta_t_assurance(id_t_assurance);
ALTER TABLE possede_type_assurance ADD CONSTRAINT FK_possede_type_assurance_id_t_assurance_ta_t_assurance FOREIGN KEY (id_t_assurance_ta_t_assurance) REFERENCES ta_t_assurance(id_t_assurance);
ALTER TABLE possede_type_assurance ADD CONSTRAINT FK_possede_type_assurance_id_devis_rc_pro_ta_devis_rc_pro FOREIGN KEY (id_devis_rc_pro_ta_devis_rc_pro) REFERENCES ta_devis_rc_pro(id_devis_rc_pro);
ALTER TABLE possede_type_assurance ADD CONSTRAINT FK_possede_type_assurance_id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage FOREIGN KEY (id_projet_dommage_ouvrage_ta_projet_dommage_ouvrage) REFERENCES ta_projet_dommage_ouvrage(id_projet_dommage_ouvrage);
ALTER TABLE possede_type_assurance ADD CONSTRAINT FK_possede_type_assurance_id_projet_gfa_ta_projet_gfa FOREIGN KEY (id_projet_gfa_ta_projet_gfa) REFERENCES ta_projet_gfa(id_projet_gfa);
ALTER TABLE possede ADD CONSTRAINT FK_possede_id_t_action_doc_ta_t_action_doc FOREIGN KEY (id_t_action_doc_ta_t_action_doc) REFERENCES ta_t_action_doc(id_t_action_doc);
ALTER TABLE possede ADD CONSTRAINT FK_possede_id_t_statut_ta_t_statut FOREIGN KEY (id_t_statut_ta_t_statut) REFERENCES ta_t_statut(id_t_statut);


