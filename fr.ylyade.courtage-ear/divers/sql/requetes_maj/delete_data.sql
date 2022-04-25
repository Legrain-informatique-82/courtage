--mdp util '1234'
update ta_utilisateur set password='A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=' where (identifiant<>'admin' and identifiant<>'adminlgr' and identifiant<>'assumarisk') ;

--reglement
update ta_reglement_assurance set id_echeance_ta_echeance=null;
update ta_reglement_assurance set id_quittance_ta_quittance=null;
update ta_reglement_assurance set id_talon_comptable_ta_talon_comptable=null;
update ta_echeance set id_reglement_assurance_ta_reglement_assurance=null;
update ta_quittance set id_reglement_assurance_ta_reglement_assurance=null;
update ta_talon_comptable set id_reglement_assurance_ta_reglement_assurance=null;
delete from ta_reglement_assurance;
delete from ta_quittance;
delete from ta_talon_comptable;
-- taSousDonnee des devis/contrats
delete from ta_sous_donnee;
-- Attestation nominative
delete from ta_attestation_nominative_activite;
delete from ta_attestation_nominative;
--contrat
delete from ta_frais_impaye;

delete from ta_echeance;
update ta_dossier_rcd set contrat=false;
update ta_dossier_rcd set soumis_souscription=false;

--devis
delete from ta_r_t_statut_dossier_rcd;
delete from ta_sinistre_antecedent;
delete from ta_dossier_rcd_activite;
delete from ta_dossier_rcd_taux_pib;
delete from ta_dossier_rcd;
delete from ta_ged_utilisateur;

--assure courtier utilisateur
update ta_assure set id_ta_contact_entreprise_ta_contact_entreprise=null;
update ta_courtier set id_contact_entreprise_ta_contact_entreprise=null;
delete from ta_adresse;
delete from ta_tel;
delete from ta_email;
delete from ta_contact_entreprise;
delete from ta_assure;
delete from ta_courtier;

delete from ta_utilisateur where (identifiant<>'admin' and identifiant<>'adminlgr' and identifiant<>'assumarisk') or identifiant is NULL ;