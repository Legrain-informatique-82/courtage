--AJOUT DE COLONNE PRIME NETTE ASSUREUR
ALTER TABLE public.ta_dossier_rcd ADD COLUMN prime_nette_assureur did9facult;
--INSERTION DANS TA LISTEREFDOC DE 3 CHAMPS AUTRE
INSERT INTO public.ta_liste_ref_doc VALUES (30, 'Autre (3)', NULL, 1, NULL, '2018-05-24 12:25:34.308863+02', NULL, '2018-05-24 12:25:34.308863+02', NULL, 0, 'PieceRcPro15', true, true);
INSERT INTO public.ta_liste_ref_doc VALUES (31, 'Autre (4)', NULL, 1, NULL, '2018-05-24 12:25:55.016795+02', NULL, '2018-05-24 12:25:55.016795+02', NULL, 0, 'PieceRcPro16', true, true);
INSERT INTO public.ta_liste_ref_doc VALUES (32, 'Autre (5)', NULL, 1, NULL, '2018-05-24 12:26:15.142834+02', NULL, '2018-05-24 12:26:15.142834+02', NULL, 0, 'PieceRcPro17', true, true);
--AJOUT DE 6 COLONNES RESILIATION DANS TA DOSSIER
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_non_paiement_contrat date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_fausse_declaration_contrat date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_echeance_contrat date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_amiable_contrat date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_cessation_activite_contrat date_heure_lgr DEFAULT NULL;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN resilie_sans_effet_contrat date_heure_lgr DEFAULT NULL;


--SI JAMAIS A LA CREATION LES 6 COLONNES NE SONT PAS VIDES :
--UPDATE public.ta_dossier_rcd SET resilie_non_paiement_contrat = NULL,
--resilie_fausse_declaration_contrat = NULL,
--resilie_echeance_contrat = NULL,
--resilie_amiable_contrat = NULL,
--resilie_cessation_activite_contrat = NULL,
--resilie_sans_effet_contrat = NULL;




--AJOUT DE 2 CHAMPS DANS TATSTATUT
INSERT INTO public.ta_t_statut VALUES (32, 'resilie_amiable', 'Résilié à l''amiable', '2018-05-25 15:26:37.747188+02', NULL, '2018-05-25 15:26:37.747188+02', NULL, '2018-05-25 15:26:37.747188+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (33, 'resilie_fausse_declaration', 'Résilié fausse déclaration', '2018-05-25 15:27:26.439364+02', NULL, '2018-05-25 15:27:26.439364+02', NULL, '2018-05-25 15:27:26.439364+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (34, 'suspendu_avenant', 'Suspendu pour avenant', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);

-- ajout de 2 colonnes dans le TaDossierRcd (mis en demeure et suspendu_non_paiement)
ALTER TABLE public.ta_dossier_rcd ADD COLUMN mis_en_demeure date_heure_lgr DEFAULT NULL;
--ALTER TABLE public.ta_dossier_rcd ALTER COLUMN mis_en_demeure SET DEFAULT NULL::timestamp with time zone;
ALTER TABLE public.ta_dossier_rcd ADD COLUMN suspendu_non_paiement date_heure_lgr DEFAULT NULL;
--ALTER TABLE public.ta_dossier_rcd ALTER COLUMN suspendu_non_paiement SET DEFAULT NULL::timestamp with time zone;

-- SI JAMAIS A LA CREATION LES 2 COLONNES NE SONT PAS VIDES :
--UPDATE public.ta_dossier_rcd  SET mis_en_demeure = NULL,
--suspendu_non_paiement = NULL;

-- CREATION DE LA TABLE FRAIS IMPAYE
CREATE TABLE public.ta_frais_impaye
(
  id_frais_impaye SERIAL,
  date_frais date_heure_lgr DEFAULT NULL,
  montant_frais did9facult,
  id_t_frais_impaye_ta_t_frais_impaye did_facultatif,
  id_echeance_ta_echeance did_facultatif,
  qui_cree dlib50,
  quand_cree date_heure_lgr,
  qui_modif dlib50,
  quand_modif date_heure_lgr,
  ip_acces dlib50,
  version_obj did_version_obj,
  CONSTRAINT prk_constraint_ta_frais_impaye PRIMARY KEY (id_frais_impaye),
  CONSTRAINT fk_ta_frais_impaye_id_t_frais_impaye_ta_t_frais_impaye FOREIGN KEY (id_t_frais_impaye_ta_t_frais_impaye)
      REFERENCES public.ta_t_frais_impaye (id_t_frais_impaye) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ta_frais_impaye_id_echeance_ta_echeance FOREIGN KEY (id_echeance_ta_echeance)
      REFERENCES public.ta_echeance (id_echeance) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ta_frais_impaye
  OWNER TO postgres;

  
--CHANGER VALEUR LIBL FRAIS IMPAYE DANS LA TABLE TATFRAISIMPAYE PAR :
--Frais d'impayé par
Frais mis en demeure
-- Frais de résiliation amiable par
Frais de résiliation (Hors contexte légal)
--et toujours dans cette table changer les code par :
Frais_annulation
Frais_ylyade_affaire_nouvelle
Frais_resiliation
Frais_avenant_contractuel
Frais_mis_demeure
Frais_pib
Frais_compagnie_affaire_nouvelle
-- et toujours dans cette table changer le montant frais_avenant (150 au lieu de 75)
150


--AJOUT D'UNE COLONNE DANS TAECHEANCE 
ALTER TABLE public.ta_echeance ADD COLUMN montant_echeance_plus_frais did9facult;

-- AJOUT D'UNE COLONNE DANS TADOSSIERRCD :
ALTER TABLE public.ta_dossier_rcd ADD COLUMN suspendu_avenant boolean;

-- AJOUT D'UNE COLONNE id_tarif_prestation dans ta_attestation_nominative
ALTER TABLE public.ta_attestation_nominative ADD COLUMN id_tarif_prestation_ta_tarif_prestation did_facultatif; 
-- Ajout de la foreign key
ALTER TABLE public.ta_attestation_nominative
  ADD CONSTRAINT fk_ta_attestation_nominative_id_tarif_prestation_ta_tarif_prest FOREIGN KEY (id_tarif_prestation_ta_tarif_prestation)
      REFERENCES public.ta_tarif_prestation (id_tarif_prestation) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
-- renommage de la table montant_prestation de ta_attestation_nominative par montant_paye
ALTER TABLE public.ta_attestation_nominative RENAME montant_prestation  TO montant_paye;
-- drop de la colonne code_tarif_prestation
ALTER TABLE ta_attestation_nominative 
DROP COLUMN code_tarif_prestation;

-- ajout de la colonne id_reglement_prestation_ta_reglement_prestation
ALTER TABLE public.ta_attestation_nominative ADD COLUMN id_reglement_prestation_ta_reglement_prestation did_facultatif;
-- Ajout de la foreign key
ALTER TABLE public.ta_attestation_nominative
  ADD CONSTRAINT fk_ta_attestation_nominative_id_reg_presta_ta_reg_presta FOREIGN KEY (id_reglement_prestation_ta_reglement_prestation)
      REFERENCES public.ta_reglement_prestation (id_reglement_prestation) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
-- Ajout de la contrainte foreign key de taAttestationNominative pour la clé id_dossier
ALTER TABLE public.ta_attestation_nominative
  ADD CONSTRAINT fk_ta_attestation_nominative_id_dossier_rcd_ta_dossier FOREIGN KEY (id_dossier_rcd_ta_dossier_rcd)
      REFERENCES public.ta_dossier_rcd (id_devis_rc_pro_detail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
-- Ajout d'une colonne ref_ext_reglement :
ALTER TABLE public.ta_reglement_prestation ADD COLUMN ref_ext_reglement dlib100;
