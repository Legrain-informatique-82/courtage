-- ajout d'une colonne id_t_reglement dans l'attestation nominative
ALTER TABLE public.ta_attestation_nominative ADD COLUMN id_t_reglement_ta_t_reglement did_facultatif;
-- fk
ALTER TABLE public.ta_attestation_nominative
  ADD CONSTRAINT fk_ta_attestation_nominative_id_t_reglement_ta_t_reglement FOREIGN KEY (id_t_reglement_ta_t_reglement)
      REFERENCES public.ta_t_reglement (id_t_reglement) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- ajout d'une FK manquante sur la table echéance pour tatreglement :
ALTER TABLE public.ta_echeance
  ADD CONSTRAINT fk_ta_echeance_id_t_reglement__ta_t_reglement FOREIGN KEY (id_t_reglement_ta_t_reglement)
      REFERENCES public.ta_t_reglement (id_t_reglement) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
-- ajout de 7 colonnes dans ta_reglement_prestation concernant les paiements cheque et virement
ALTER TABLE public.ta_reglement_prestation ADD COLUMN validation_automatique_paiement boolean;
ALTER TABLE public.ta_reglement_prestation ALTER COLUMN validation_automatique_paiement SET DEFAULT false;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_reception_cheque date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_encaissement_cheque date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_envoi_cheque_par_courtier date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_depot_cheque date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_virement_effectue date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_reglement_prestation ADD COLUMN date_virement_recu date_heure_lgr DEFAULT NULL;