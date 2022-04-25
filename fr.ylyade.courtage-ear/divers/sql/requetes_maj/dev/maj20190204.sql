-- rajout de la colonne prime_min dans la table ta_taux_rcpro_pib (activités PIB)
ALTER TABLE ta_taux_rcpro_pib
ADD COLUMN prime_min did9facult;
-- UPDATE des activités PIB
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '1', libl_taux_rcpro_pib = 'Architecte', taux_rcpro_pib = 5.00, description_activite = 'Mission complète ou partielle (conception et/ou direction de l''exécution des contrats de travaux) pour l''aménagement intérieur et l''agencement des ouvrages avec intervention sur la structure de la construction.
 Limité aux missions suivantes :
 - ESQ (Esquisse)
 - AVP (Avant-Projet)
 - APS (avant-projet sommaire)
 - APD (avant-projet détaillé ou définitif)
 - DPC (Dossier de permis de construire)
 - DCE (Dossier de consultation des entreprises)', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 1;

UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '1.1', libl_taux_rcpro_pib = 'Architecte d''intérieur', taux_rcpro_pib = 3.00, description_activite = 'Mission complète ou partielle (conception et/ou direction de l’exécution des contrats de travaux) pour l’aménagement intérieur et l’agencement des ouvrages sans intervention sur la structure de la construction.
 Limité aux missions suivantes :
 - ESQ (Esquisse)
 - AVP (Avant-Projet)
 - APS (avant-projet sommaire)
 - APD (avant-projet détaillé ou définitif)
 - DCE (Dossier de consultation des entreprises) A l''exclusion de : Travaux de bâtiment.
 Tous travaux portant atteinte à l''infra et la super structure du bâtiment.', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 2;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '2', libl_taux_rcpro_pib = 'Assitant Maître d''oeuvre', taux_rcpro_pib = 3.50, description_activite = 'L''assistant Maître d''Œuvre est mandaté par et se substitue au Maître d''œuvre. Il l''assiste et en aucun cas il ne peut réceptionner ou éditer tout document engageant le maitre d''œuvre.
 Ces missions sont :
 - Assister la Maître d''œuvre notamment dans la définition de l''objectif du projet,
 - Définir et concevoir le projet
 - Participer à la sélection de solutions adéquates
 - Suivre et vérifier l''avancement du projet et le respect du cahier des charges
 - Participer à la définition du plan de recettes - Participer à la livraison du projet
 - Communiquer sur l''avancée du projet
 A l''exclusion de : Toute mission de maîtrise d’ouvrage directe.
', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 3;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '2.2', libl_taux_rcpro_pib = 'Assitant Maître d''ouvrage', taux_rcpro_pib = 4.00, description_activite = 'Mission d’assistance et de conseil au maître de l’ouvrage pour l’évaluation, la programmation, la budgétisation, la passation des marchés, les choix techniques et/ou architecturaux d’une opération de construction, au stade de la conception et/ou de la réalisation.
 Evaluation, programmation, budgétisation. A l''exclusion de : Toute mission de maîtrise d''ouvrage déléguée (MOD) ou de mandataire du maître de l''ouvrage et de toute autre prestation y compris toutes activités de conception ou de réalisation.
 ', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 4;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3', libl_taux_rcpro_pib = 'BET TCE (Tous corps d''etat)', taux_rcpro_pib = 7.00, description_activite = 'Un BET Tous Corps d''Etat (TCE), en collaboration avec l''architecte il réalise toutes les études pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l''exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 BET Couverts :
 - BET Acoustique
 - BET Assainissement / VRD
 - BET Charpente / Couverture (Bois et fer) 
 - BET Chauffage / Plomberie
 - BET Climatisation / Ventilation - BET Construction Bois
 - BET Electricité
 - BET Etanchéité
 - BET Fluides
 - BET Géotechnique
 - BET Infrastructure
 - BET Structure
 - BET Thermique
 ', prime_min = 7000.00
WHERE id_taux_rcpro_pib = 5;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.1', libl_taux_rcpro_pib = 'BET Acoustique', taux_rcpro_pib = 3.00, description_activite = 'Un BET acoustique, en collaboration avec l''architecte il réalise des études acoustiques (épaisseur des cloisons, résilients acoustiques dans le sol, faux-plafonds, etc.) pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l''exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants ', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 6;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.2', libl_taux_rcpro_pib = 'BET Assainissement / VRD', taux_rcpro_pib = 5.00, description_activite = 'Le BET (V.R.D.) assure la conception des espaces extérieurs d''un point de vue technique.
 En collaboration avec l''architecte et, ou le paysagiste de la maitrise d''œuvre, il réalise les documents graphiques et les descriptifs techniques correspondant aux prestations de surface (d''espaces verts et V.R.D.) Élabore tout type de plans, de la conception à l''exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 ', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 7;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.3', libl_taux_rcpro_pib = 'BET Charpente / Couverture (Bois et fer)', taux_rcpro_pib = 5.00, description_activite = 'Le BET charpente / couverture (Bois ou Fer), en collaboration avec l''architecte réalise des études de faisabilité ou de portance pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement / portance conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants ', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 8;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.4', libl_taux_rcpro_pib = 'BET Chauffage / Plomberie', taux_rcpro_pib = 3.00, description_activite = 'Le BET Chauffage / Plomberie en collaboration avec l''architecte réalise des études de faisabilité et/ou calorifique pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 ', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 9;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.5', libl_taux_rcpro_pib = 'BET Climatisation / Ventilation', taux_rcpro_pib = 3.00, description_activite = 'Le BET Climatisation / Ventilation en collaboration avec l''architecte réalise des études de faisabilité et/ou calorifique pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 ', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 10;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.6', libl_taux_rcpro_pib = 'BET Construction Bois', taux_rcpro_pib = 5.00, description_activite = 'Un Bureau d''Etudes bois s''occupera des structures en bois, dont les charpentes et couvertures, réalisant des études et prestations de conseils techniques dans le cadre d''une construction bois.
 Il sera le principal acteur des lots structures / charpente / couverture en phase préparatoire.
 L’intervention du BET Bois :
 - L’étude de projets (en phase PRO) 
 - L’étude d’exécutions (en phase EXE) 
 - La direction de l''exécution du ou des contrats de travaux (en phase DET)
 - L’étude de diagnostic (DIA)
 - Expertise
 - Missions conseils
 ', prime_min = 4000.00
WHERE id_taux_rcpro_pib = 11;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.7', libl_taux_rcpro_pib = 'BET Electricité', taux_rcpro_pib = 3.00, description_activite = 'Le BET Electricité en collaboration avec l''architecte réalise des études pour des ouvrages neufs ou réhabilité.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants. A l''exclusion de : 
- Tous travaux de bâtiment,
- Courant fort (Ouvrage Public)
- Génie civile', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 12;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.8', libl_taux_rcpro_pib = 'BET Etanchéité', taux_rcpro_pib = 6.00, description_activite = 'Le BET Etanchéité en collaboration avec l''architecte réalise des études pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 ', prime_min = 5000.00
WHERE id_taux_rcpro_pib = 13;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.9', libl_taux_rcpro_pib = 'BET Fluides', taux_rcpro_pib = 3.00, description_activite = 'Un BET fluides, en collaboration avec l''architecte réalise des études pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 14;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.10', libl_taux_rcpro_pib = 'BET Géotechniques', taux_rcpro_pib = 5.00, description_activite = 'Le BET Géotechnique en collaboration avec l''architecte réalise des études pour des ouvrages neufs, rénovation, extension / surélévation et ou changement de destination.
 Missions couvertes :
 - Surveiller la résistance des sols avant un projet de construction.
 - Mesurer la teneur en eau d''un sol et sa résistance.
 - Recueillir des informations relatives aux travaux menés sur le site en question.
 - Rédiger un rapport sur l''existence de dangers.
 - Effectuer des mesures pendant et après les travaux.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 Missions géotechniques :
 - Etude géotechnique préalable G1
 - Etude Géotechnique de conception G2
 - Etude et suivi géotechnique d''exécution G3 - Supervision géotechnique d''exécution G4
 - Diagnostic géotechnique G5, selon la norme NFP 94-500
 - Etudes d''hydrologie : études et conception de systèmes de gestion des eaux usées, eaux pluviales, ainsi que des investigations géotechniques nécessaires, sans suivi ni direction des travaux ', prime_min = 5000.00
WHERE id_taux_rcpro_pib = 15;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.11', libl_taux_rcpro_pib = 'BET Infrastructure', taux_rcpro_pib = 5.00, description_activite = 'Un BET Infrastructure réalise la conception d''ouvrages Infrastructurels, qu''il s''agisse d''ouvrages :
 - En béton armé,
 - En structure métallique,
 - En structure mixte.
 De manière restrictive, un BET Infrastructure peut être spécialisé dans l''un ou l''autre de ces types de structures.
 Il dessine des plans d''Infrastructure du dossier de consultation des entreprises (DCE) et rédige le CCTP (cahier des clauses techniques particulières).
 Les Missions couvertes :
 PROJET-DCE :
 - DCE / CCTP / BPU
 Études d’exécution (EXE),
 VISA des études d’exécution (VISA) :
 - Notes de calculs
 - Plans de coffrage
 - Plans de précontrainte
 - Plans de ferraillage et nomenclatures ', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 16;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.12', libl_taux_rcpro_pib = 'BET Structure', taux_rcpro_pib = 5.00, description_activite = 'Un BET structure réalise la conception d''ouvrages structurels, qu''il s''agisse d''ouvrages :
 - En béton armé,
 - En structure métallique,
 - En structure mixte.
 De manière restrictive, un BET structure peut être spécialisé dans l''un ou l''autre de ces types de structures.
 Un BET béton assistera l''architecte sur le dimensionnement des ouvrages en béton.
 Il dessine des plans bétons du dossier de consultation des entreprises (DCE) et rédige le CCTP (cahier des clauses techniques particulières) du lot gros œuvre (GO).', prime_min = 3000.00
WHERE id_taux_rcpro_pib = 17;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '3.14', libl_taux_rcpro_pib = 'BET Thermique', taux_rcpro_pib = 3.00, description_activite = 'Un BET Thermique, en collaboration avec l''architecte réalise des études de faisabilité pour des ouvrages neufs ou réhabilités.
 Élabore tout type de plans, de la conception à l’exécution.
 Édite toute note de calcul de dimensionnement conformément aux réglementations en vigueur.
 La consultation des entreprises et écrit le ou les CCTP correspondants.
 ', prime_min = 2000.00
WHERE id_taux_rcpro_pib = 18;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '4', libl_taux_rcpro_pib = 'Contôler technique', taux_rcpro_pib = 5.00, description_activite = 'Missions de contrôle technique :
 L : Solidité des ouvrages constitutifs ou indissociables au bâtiment LP : Solidité des ouvrages indissociables et dissociables LE : Solidité des existants
 AV : vérification des Avoisinants
 PV : Récolement des procès-verbaux COPREC des installations techniques
 PS : Sécurité des personnes dans les constructions en cas de Séisme SH : Sécurité incendie des personnes dans les bâtiments d''habitation STI : Sécurité incendie des personnes dans les bâtiments relevant du code du travail uniquement
 SEI : Sécurité incendie des personnes dans les établissements recevant du public
 HAND : Vérification des exigences d''accessibilité des personnes handicapées
 BRD : Passage du brancard
 TH : Vérification des exigences d''isolation thermique des bâtiments PHh : Vérification des exigences d''isolation acoustique dans les bâtiments d''habitation
 PHa : Vérification des exigences d''isolation acoustique dans les bâtiments autre qu''habitation
 F : Fonctionnement des installations
 ', prime_min = 4000.00
WHERE id_taux_rcpro_pib = 19;
UPDATE ta_taux_rcpro_pib 
SET code_taux_rcpro_pib = '5', libl_taux_rcpro_pib = 'Contractant général', taux_rcpro_pib = 4.00, description_activite = 'Contractant général pour la construction, réhabilitation ou la mise en conformité de bâtiments :
 - Assumant la maitrise d''œuvre d''exécution totale ou partielle.
 - Donnant en sous-traitance automatiquement la mission de maitrise d''œuvre de conception ainsi que les études techniques spécialisées.
 - Donnant en sous-traitance à des entreprises du bâtiment la réalisation de l''intégralité des travaux tous corps d''état.
 A l''exclusion de :
 Une activité de constructeur de maisons individuelles avec ou sans fourniture de plans telle que visée dans la loi n° 90-1129 du 19 décembre 1990 et son décret d''application du 27 novembre 1991.
 Une activité exclusive de vendeur de produits de construction visée à l''article 1792-4 du code civil.
 Une activité de conception, de direction et/ou de surveillance de travaux en qualité de sous-traitant dans le cadre de son activité de contractant général.
 
 ', prime_min = 5000.00
WHERE id_taux_rcpro_pib = 20;

-- INSERT DES ACTIVITES PIB SUPPLEMENTAIRE
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('6', 'Coordinateur SPS', 5.00,'Mission de coordination en matière de sécurité et de protection de la santé définie par la loi n° 93-1418 du 31/12/1993 et le décret n° 94-1159 du 26/12/1994.
', 3000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('7', 'Courtier en travaux', 2.00,'Le courtier en travaux est une entreprise de services et un intermédiaire, généralement neutre et indépendant, entre les particuliers souhaitant faire réaliser des travaux et les entreprises du bâtiment.
 Ses prestations se limitent à cette simple mise en relation, sans endosser aucune responsabilité.
 Etudie de besoins effectifs et établissement des conventions.
 Le courtier aide ainsi le consommateur à finaliser son projet et à formuler précisément sa demande de travaux
', 1500.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('8', 'Dessinateur', 2.00,'Le dessinateur n''est pas un architecte et ne peut se substituer à l''obligation d''un architecte si le projet le nécessite.
 Le dessinateur en bâtiment réalise les documents d''exécution à l''aide de logiciels de CAO ou de DAO. (Généralement pour le compte d''entreprises de bâtiment, cabinets d''architecte, bureaux d''études spécialisés ...).
 Il élabore ainsi les éléments graphiques nécessaires à chaque étape de la construction, les nomenclatures agrémentées de schémas ou de dessins illustrant les procédés d''exécution ou d''assemblage… Le dessinateur en bâtiment est aussi amené à réaliser des plans plus détaillés d''une habitation et de son intérieur 
', 2000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('9', 'Diagnostiqueur', 5.00,'Missions de diagnostic couvertes :
 - Expert diagnostiqueur- Diagnostic Acoustique- Diagnostic Amiante (visuel avant-vente, avant-travaux, démolition, enrobés)- Diagnostic Ascenseur- Diagnostic Assainissement autonome ou collectif- Diagnostic Détection de Fuites- Diagnostic Eco Prêt- Diagnostic Etat de l''Installation Electrique- Diagnostic Etat de l''Installation Gaz- Diagnostic Etat des Lieux- Diagnostic Etat Parasitaire- Diagnostiqueur Examinateur Certifications- Diagnostiqueur Expert auprès de la cour d''appel Diagnostic Handicap (accessibilité)- Diagnostic Humidité- Diagnostic
 Légionellose- Diagnostic Logement Décent- Diagnostic Loi Boutin Diagnostic Loi Carrez- Diagnostic Loi Scellier- Diagnostic Métaux Lourds Diagnostic Millièmes- Diagnostic Monoxyde de Carbone- Diagnostic
 Performance Energétique (DPE)- Diagnostic Plomb (CREP, DRIP, avanttravaux, Plomb dans l''eau)- Diagnostic Pollution des Sols- Diagnostic Prêt Conventionné : normes d''habitabilité- Diagnostic Qualité de l''air intérieurDiagnostic Radon- Diagnostic Risques Naturels, Miniers et
 Technologiques- Diagnostic Sécurité Piscine- Diagnostic Technique SRUDiagnostic Termites- Calcul Réglementaire RT2005, RT2012- Expert
 Conseil en Rénovation Energétique ( ERE )- Expert en Valeur Vénale Mesure d''Empoussièrement Amiante- Mission de Coordination SPSPersonne Compétente en Radioprotection ( PCR )- Tests d''Infiltrométrie : Enveloppe (8711)- Réseaux aérauliques (8721)Thermographie infrarouge 
', 3000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('10','Economiste de la construction', 3.00, 'Etablissement, contrôle des devis quantitatifs, estimations détaillées des coûts ou dépenses, mises à prix, attachements, situations et/ou mémoires des travaux
 Missions dans les phases économiques et financières de la conception et de l''exécution des travaux, accomplies dans le cadre ou non d''une équipe pluridisciplinaire.
 Cette mission comporte également la participation de l''économiste à l''établissement du CCTP et à la rédaction des marchés de travaux.', 3000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('11', 'Géotechnicien', 3.00,'PHASE DE TRAVAUX :
 - Sondage (permettant de connaître la coupe et la nature du sol).
 - Étude de sols (carottage, pression etc.).
 - Résultats d''essais réalisés par les laborantins etc.
 ainsi que des investigations géotechniques nécessaires, sans suivi ni direction des travaux
A l''exclusion de : Bureau d''étude technique
', 2000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('12', 'Maître d''oeuvre TCE', 5.00,'PHASE D''ETUDE :
DIA : Diagnostic
ESQ : Esquisse
APS : Avant-Projet Sommaire
APD : Avant-Projet Définitif
EXE : Etudes d''Exécution
PRO : Etudes de Projet
ACT : Assistance aux Contrats de Travaux
PHASE DE TRAVAUX :
VISA
OPC : Ordonnancement, Pilotage, Coordination
DET : Direction de l''Exécution des Travaux
AOR : Assistance aux Opérations de Réception
Exclusion de : Aucun travaux de bâtiment.
', 3000.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('13', 'OPC TCE', 4.00,'L''ordonnancement, le pilotage et la coordination (OPC) ont pour objet, tout au long du déroulement d''un chantier de bâtiment et travaux publics (BTP), d''organiser et d''harmoniser dans le temps et dans l''espace les tâches élémentaires d''études et de travaux et les actions des différents intervenants.
 A l''exclusion de : - Tous travaux de bâtiment, mission de réalisation d''une synthèse ou toute mission totale ou partielle de maîtrise d''œuvre en phase conception ou suivi/réalisation.
 - Tous travaux portant atteinte à la structure et la super structure du bâtiment.', 3000.00);
 INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('14', 'Paysagiste Décorateur', 2.50,'La conception de jardin et espaces verts.
 Le paysagiste élabore les plans qui répondent à une demande d''aménagement complet ou partiel, selon les souhaits du client et après étude du site à aménager.
 Il assure un rôle de conseil auprès des clients sur le tracé d''ensemble du jardin, dans le choix et l''emplacement des circulations, des constructions décoratives, de l''éclairage et aussi bien entendu dans le choix de la palette végétale.
', 1500.00);
INSERT INTO ta_taux_rcpro_pib (code_taux_rcpro_pib, libl_taux_rcpro_pib, taux_rcpro_pib, description_activite, prime_min)
VALUES ('15', 'Topographe / Métreur', 2.00,'Missions destinées à procéder à des relevés métriques qui permettent d''établir un plan et une carte exacte de tous les détails d''un terrain.
', 2000.00);
-------------------------- LE CODE CI-DESSUS (PIB) A ETE APPLIQUER SUR DEV (local yann), TESTPROD, PROD LE 03/01/19


--REQUETE POUR ENLEVER LE S DE LA TABLE TA_GEN_CODE_EX pour les devis sur les nums police :
UPDATE ta_gen_code_ex
SET valeur_gen_code = '19{@num}'
WHERE code_gen_code = 'numDossierPolice';

--Rajout d'un taux d'assurance Pj_ETENDUE_PIB pour les PIB
INSERT INTO ta_taux_assurance (taux_taux_assurance, code_taux_assurance, libelle_taux_assurance, taux_ht_taux_assurance)
VALUES(193.70,'Pj_ETENDUE_PIB','Protection judiciaire PRO JURIDICA ETENDUE (PIB)',167.75);
--Rajout d'un tarif prestation pour les attestations nominatives des PIB (100e)
INSERT INTO ta_tarif_prestation (montant_prestation, libl_prestation, code_tarif_prestation)
VALUES(100.00,'Attestation nominative PIB','AtestNominPIB');
--Rajout d'un taux d'assurance pour Rnp_PIB
INSERT INTO ta_taux_assurance (taux_taux_assurance, code_taux_assurance, libelle_taux_assurance)
VALUES(35.00,'Rnp_PIB','Résiliation non paiement PIB');
-- Modif montant du TFraisImpaye pour dossier gestion PIB
UPDATE ta_t_frais_impaye
SET montant_frais_impaye = '300.00'
WHERE code_frais_impaye = 'Frais_pib';