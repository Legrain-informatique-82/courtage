ALTER TABLE public.ta_reglement_assurance ADD COLUMN date_virement_effectue date_heure_lgr;
ALTER TABLE public.ta_reglement_assurance ADD COLUMN date_virement_recu date_heure_lgr;
-- DROP VIEW public.vue_prime_light_dossier_rcd_;
CREATE OR REPLACE VIEW public.vue_prime_light_dossier_rcd_ AS 
 SELECT ta_dossier_rcd.id_devis_rc_pro_detail,
    ta_dossier_rcd.id_courtier_ta_courtier,
    ta_dossier_rcd.num_police,
    ta_dossier_rcd.contrat,
    ta_dossier_rcd.premier_paiement_effectue,
    ta_dossier_rcd.franchise,
    ta_dossier_rcd.code_franchise,
    ta_dossier_rcd.montant_prime,
    ta_dossier_rcd.chiffre_affaire_exercice_previsionnel,
    ta_dossier_rcd.prime_nette,
    ta_dossier_rcd.montant_prime_annuelle_ht,
    ta_dossier_rcd.montant_prime_annuelle_ttc,
    ta_dossier_rcd.montant_prime_echeance_ht,
    ta_dossier_rcd.montant_prime_echeance_ttc
   FROM ta_dossier_rcd;

ALTER TABLE public.vue_prime_light_dossier_rcd_
  OWNER TO postgres;

  
  DROP INDEX public.ta_dossier_rcd_num_police_dossier_uindex;

CREATE UNIQUE INDEX ta_dossier_rcd_num_police_dossier_uindex
  ON public.ta_dossier_rcd
  USING btree
  (num_police, numero_avenant)
  
 DROP VIEW public.vue_etat_dossier_rcd_; 
  CREATE OR REPLACE VIEW public.vue_etat_dossier_rcd_ AS 
 SELECT ta_dossier_rcd.id_devis_rc_pro_detail,
    ta_dossier_rcd.id_courtier_ta_courtier,
    ta_dossier_rcd.num_police,
    ta_dossier_rcd.contrat,
    ta_dossier_rcd.soumis_souscription,
    ta_dossier_rcd.validation_globale_ged_ylyade,
    ta_dossier_rcd.premier_paiement_effectue,
    ta_dossier_rcd.traite,
    ta_dossier_rcd.soumis_etude,
    ta_dossier_rcd.soumis_etude_assureur,
    ta_dossier_rcd.validation_apres_etude_ylyade,
    ta_dossier_rcd.validation_assureur_apres_etude,
    ta_dossier_rcd.validation_super_assureur,
    ta_dossier_rcd.validation_ylyade,
    ta_dossier_rcd.validation_courtier,
    ta_dossier_rcd.refus_definitif_ylyade,
    ta_dossier_rcd.refus_definitif_super_assureur,
    ta_dossier_rcd.numero_avenant,
    ta_dossier_rcd.validation_globale_ged_courtier
   FROM ta_dossier_rcd;

ALTER TABLE public.vue_etat_dossier_rcd_
  OWNER TO postgres;