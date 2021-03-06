-- DROP d'abord tte les lignes de tatstatut puis appliqué ce qui suit 

INSERT INTO public.ta_t_statut VALUES (25, 'avenant', 'Avenant', '2018-05-11 09:35:17.712648+02', NULL, '2018-05-11 09:35:17.712648+02', NULL, '2018-05-11 09:35:17.712648+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (2, 'attente_verif_paiement', 'Attente vérification paiement', NULL, NULL, NULL, NULL, NULL, NULL, 0, 15);
INSERT INTO public.ta_t_statut VALUES (3, 'attente_piece', 'En attente de pièce(s)', NULL, NULL, NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO public.ta_t_statut VALUES (4, 'resilie_echeance', 'Résilié à échéance', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (5, 'resilie_cessation_activite', 'Résilié cessation d''activité', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (6, 'sans_effet', 'Sans effet', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (8, 'suspendu', 'Suspendu', NULL, NULL, NULL, NULL, NULL, NULL, 0, 11);
INSERT INTO public.ta_t_statut VALUES (9, 'resilie_non_paiement', 'Résilié pour non paiement', NULL, NULL, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (1, 'en_cours', 'Contrat en cours', NULL, NULL, NULL, NULL, NULL, NULL, 3, 0);
INSERT INTO public.ta_t_statut VALUES (7, 'mise_en_demeure', 'Mis en demeure', NULL, NULL, NULL, NULL, NULL, NULL, 0, 31);
INSERT INTO public.ta_t_statut VALUES (10, 'attente_premier_paiement', 'Attente 1er paiement', '2018-05-07 17:02:35.407902+02', NULL, '2018-05-07 17:02:35.407902+02', NULL, '2018-05-07 17:02:35.407902+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (13, 'soumis_etude_assureur', 'Soumis à étude assureur', '2018-05-09 09:46:33.854517+02', NULL, '2018-05-09 09:46:33.854517+02', NULL, '2018-05-09 09:46:33.854517+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (26, 'attente_envoi_cheque', 'Attente d''envoi du chèque par le courtier', '2018-05-11 09:36:04.684059+02', NULL, '2018-05-11 09:36:04.684059+02', NULL, '2018-05-11 09:36:04.684059+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (12, 'soumis_etude_ylyade', 'Soumis à étude Ylyade', '2018-05-09 09:45:57.878965+02', NULL, '2018-05-09 09:45:57.878965+02', NULL, '2018-05-09 09:45:57.878965+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (14, 'validation_apres_etude_ylyade', 'Validé par Ylyade suite à étude', '2018-05-09 09:56:21.025051+02', NULL, '2018-05-09 09:56:21.025051+02', NULL, '2018-05-09 09:56:21.025051+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (15, 'validation_apres_etude_assureur', 'Validé par l''assureur suite à étude', '2018-05-09 09:57:13.341923+02', NULL, '2018-05-09 09:57:13.341923+02', NULL, '2018-05-09 09:57:13.341923+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (16, 'soumis_souscription', 'Soumis à souscription', '2018-05-09 10:05:43.489353+02', NULL, '2018-05-09 10:05:43.489353+02', NULL, '2018-05-09 10:05:43.489353+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (27, 'attente_reception_cheque', 'Attente de reception du chèque par Ylyade', '2018-05-11 09:36:47.923983+02', NULL, '2018-05-11 09:36:47.923983+02', NULL, '2018-05-11 09:36:47.923983+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (28, 'attente_depot_cheque', 'Attente de dépôt du chèque par Ylyade', '2018-05-11 09:37:34.337472+02', NULL, '2018-05-11 09:37:34.337472+02', NULL, '2018-05-11 09:37:34.337472+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (17, 'refus_definitif', 'Refusé définitivement', '2018-05-09 10:11:45.657873+02', NULL, '2018-05-09 10:11:45.657873+02', NULL, '2018-05-09 10:11:45.657873+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (31, 'attente_encaissement_cheque', 'Attente d''encaissement du chèque par Ylyade', '2018-05-11 09:39:34.650638+02', NULL, '2018-05-11 09:39:34.650638+02', NULL, '2018-05-11 09:39:34.650638+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (29, 'attente_virement', 'Attente de virement par le courtier', '2018-05-11 09:38:20.923282+02', NULL, '2018-05-11 09:38:20.923282+02', NULL, '2018-05-11 09:38:20.923282+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (30, 'attente_validation_virement', 'Attente de validation du virement par Ylyade', '2018-05-11 09:38:45.085194+02', NULL, '2018-05-11 09:38:45.085194+02', NULL, '2018-05-11 09:38:45.085194+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (11, 'a_traiter', 'A traiter par le courtier', '2018-05-09 09:23:20.748931+02', NULL, '2018-05-09 09:23:20.748931+02', NULL, '2018-05-09 09:23:20.748931+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (18, 'ged_a_valider_ylyade', 'Ged à valider par Ylyade', '2018-05-09 11:54:56.926723+02', NULL, '2018-05-09 11:54:56.926723+02', NULL, '2018-05-09 11:54:56.926723+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (19, 'ged_a_valider_courtier', 'Ged à valider par le Courtier', '2018-05-09 11:55:07.980644+02', NULL, '2018-05-09 11:55:07.980644+02', NULL, '2018-05-09 11:55:07.980644+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (20, 'attente_validation_ylyade', 'Attente validation Ylyade', '2018-05-09 14:12:23.280294+02', NULL, '2018-05-09 14:12:23.280294+02', NULL, '2018-05-09 14:12:23.280294+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (21, 'attente_validation_assureur', 'Attente validation Assureur', '2018-05-09 14:12:52.406043+02', NULL, '2018-05-09 14:12:52.406043+02', NULL, '2018-05-09 14:12:52.406043+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (22, 'refuse_assureur', 'Refusé par l''assureur', '2018-05-09 16:48:58.864992+02', NULL, '2018-05-09 16:48:58.864992+02', NULL, '2018-05-09 16:48:58.864992+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (23, 'refus_apres_etude_ylyade', 'Refusé par Ylyade suite à étude', '2018-05-09 17:01:24.303121+02', NULL, '2018-05-09 17:01:24.303121+02', NULL, '2018-05-09 17:01:24.303121+02', NULL, 0, 0);
INSERT INTO public.ta_t_statut VALUES (24, 'refus_apres_etude_assureur', 'Refusé par l''assureur suite à étude', '2018-05-09 17:01:53.15497+02', NULL, '2018-05-09 17:01:53.15497+02', NULL, '2018-05-09 17:01:53.15497+02', NULL, 0, 0);