--DPRSA a 5€ TTC
UPDATE ta_taux_assurance
SET taux_taux_assurance = 5.00, taux_ht_taux_assurance = 4.55
WHERE code_taux_assurance = 'DPRSA';

--Activite avec un minimum de prime
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '3.1';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '5';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 4550.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '6';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '7';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 350000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '8';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 4550.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '9';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 4550.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '11';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 300000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '15';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 4550.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '16';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 350000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '17';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 350000.00, prime_base = 2775.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '18.1';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 450000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '20';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '28.1';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 450000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '30.1';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 400000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '36';
UPDATE ta_activite 
SET min_ca = 0.00, max_ca = 300000.00, prime_base = 3185.00, id_t_franchise_ta_t_franchise = 4
WHERE code_activite = '43';
--Ajout des 3 nouvelles activité
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, id_t_franchise_ta_t_franchise, id_famille_activite_ta_famille_activite, code_activite, min_ca, max_ca, prime_base, position) 
VALUES ('Piscine',3, 4, 5, '37', 0.00, 250000, 3185.00, 57 );
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, id_t_franchise_ta_t_franchise, id_famille_activite_ta_famille_activite, code_activite, min_ca, max_ca, prime_base, position) 
VALUES ('Maison à ossature bois',3, 4, 5, '38', 0.00, 400000, 4550.00, 58 );
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, id_t_franchise_ta_t_franchise, id_famille_activite_ta_famille_activite, code_activite, min_ca, max_ca, prime_base, position) 
VALUES ('Installation à énergie solaire par capteur photovoltaïque',3, 4, 5, '40', 0.00, 400000, 3185.00, 59 );

--Nouvelle prime pour les palier de classe d'activité
--Palier classe 1 

UPDATE ta_palier_classe
SET montant_prime_base = 1920.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1920.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2100.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2520
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2940.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3462.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3840.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4260.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5100.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5771.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6378.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7560.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9480.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 13200.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 15120.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 17520.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 20280.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 23400.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 25920.00
WHERE code_palier_classe = 'Pcl1'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

-- Palier 2
UPDATE ta_palier_classe
SET montant_prime_base = 1728.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1728.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1889.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2268
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2645.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3077.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3456.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3833.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4589.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5129.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5669.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6804.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 8532.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11880.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 13608.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 15768.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 18252.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 21060.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 23328.00
WHERE code_palier_classe = 'Pcl2'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;


--Palier classe 3

UPDATE ta_palier_classe
SET montant_prime_base = 1664.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1664.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1820.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2184.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2548.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2964.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3328.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3692.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4420.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4940.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5460.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6552.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 8216.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11440.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 13104.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 15184.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 17576.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 20280.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 22464.00
WHERE code_palier_classe = 'Pcl3'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 4
UPDATE ta_palier_classe
SET montant_prime_base = 1536.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1536.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2016.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2352.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2736.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3072.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3408.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4080.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4560.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5040.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6048.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7584.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 10560.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 12096.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 14016.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 16224.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 18720.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 20736.00
WHERE code_palier_classe = 'Pcl4'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;
--Palier classe 5

UPDATE ta_palier_classe
SET montant_prime_base = 1408.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1408.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1540.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1848.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2156.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2508.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2816.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3124.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3740.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4180.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4620.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5544.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6952.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9680.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11088.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 12848.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 14872.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 17160.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 19008.00
WHERE code_palier_classe = 'Pcl5'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 6

UPDATE ta_palier_classe
SET montant_prime_base = 1120.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1280.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1400.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1960.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2280.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2560.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2840.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3400.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3800.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4200.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5040.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6320.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 8800.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 10080.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11680.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 13520.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 15600.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 17280.00
WHERE code_palier_classe = 'Pcl6'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 7

UPDATE ta_palier_classe
SET montant_prime_base = 1248.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1536.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2016.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2352.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2736.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3072.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3408.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3744.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4080.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4464.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4800.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6480.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6912.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 8400.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9504.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 10896.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 12288.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 13680.00
WHERE code_palier_classe = 'Pcl7'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 8

UPDATE ta_palier_classe
SET montant_prime_base = 1040.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1280.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1400.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1960.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2280.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2560.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2840.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3120.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3400.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3720.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4000.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5400.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5760.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7000.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7920.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9080.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 10240.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11400.00
WHERE code_palier_classe = 'Pcl8'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 9

UPDATE ta_palier_classe
SET montant_prime_base = 1056.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1248.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1536.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1536.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2016.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2352.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2736.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3072.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3408.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3744.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4080.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5280.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6048.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7200.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7776.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9504.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 11232.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 12960.00
WHERE code_palier_classe = 'Pcl9'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 10

UPDATE ta_palier_classe
SET montant_prime_base = 880.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1040.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1280.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1280.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1400.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1960.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2280.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2560.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2840.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3120.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3400.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4400.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5040.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6000.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6480.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7920.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 9360.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 10800.00
WHERE code_palier_classe = 'Pcl10'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 11

UPDATE ta_palier_classe
SET montant_prime_base = 560.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 720.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 880.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1000.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1120.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1400.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1680.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1960.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2280.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2560.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2840.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3120.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3400.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4320.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5200.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5760.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6480.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7200.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 7920.00
WHERE code_palier_classe = 'Pcl11'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 12

UPDATE ta_palier_classe
SET montant_prime_base = 460.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 561.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 708.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 804.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 901.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1126.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1352.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1577.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1835.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2060.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2285.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2511.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2736.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3477.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4185.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4636.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5216.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5796.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 6375.00
WHERE code_palier_classe = 'Pcl12'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;

--Palier classe 13

UPDATE ta_palier_classe
SET montant_prime_base = 400.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 000.00
AND palier_montant_max = 32800.00;

UPDATE ta_palier_classe
SET montant_prime_base = 488.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 32800.00
AND palier_montant_max = 80000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 616.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 80000.00
AND palier_montant_max = 150000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 700.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 150000.00
AND palier_montant_max = 200000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 784.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 200000.00
AND palier_montant_max = 250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 980.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 250000.00
AND palier_montant_max = 300000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1176.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 300000.00
AND palier_montant_max = 350000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1372.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 350000.00
AND palier_montant_max = 400000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1596.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 400000.00
AND palier_montant_max = 450000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1792.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 450000.00
AND palier_montant_max = 500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 1988.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 500000.00
AND palier_montant_max = 550000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2184.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 550000.00
AND palier_montant_max = 750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 2380.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 750000.00
AND palier_montant_max = 1000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3024.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 1000000.00
AND palier_montant_max = 1250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 3640.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 1250000.00
AND palier_montant_max = 1500000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4032.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 1500000.00
AND palier_montant_max = 1750000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 4536.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 1750000.00
AND palier_montant_max = 2000000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5040.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 2000000.00
AND palier_montant_max = 2250000.00;

UPDATE ta_palier_classe
SET montant_prime_base = 5544.00
WHERE code_palier_classe = 'Pcl13'
AND palier_montant_min = 2250000.00
AND palier_montant_max = 2500000.00;




-----------------------------------------------------------------------------------------------------

--Maj.sql pour nouvelle assurance Leader underwriting 09/10/18
-- 9 paliers de classe au lieu de 13


--supprime tout les paliers de classe de la table
DELETE FROM  ta_palier_classe;

-- vire les fk des classes qui n'exite plus sur les activités
UPDATE ta_activite a SET id_classe_ta_classe = null WHERE a.id_classe_ta_classe > 12;

--supprime les classes en trop
DELETE FROM  ta_classe c WHERE id_classe > 12;

--PALIER CLASSE--

---AUTO ENTREPRENEUR--
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (2557.61, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (2301.52, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (2217.80, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (2046.25, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (1705.62, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (1473.34, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (1178.67, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (739.54, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, id_classe_ta_classe, code_palier_classe) VALUES (628.61, 12, 'Pcl9');

--Palier classe 1
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2952.42, 000.00,80000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3215.89, 80000.00,150000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3875.00, 150000.00,200000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4533.28, 200000.00,250000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5192.38, 250000.00,300000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5850.66, 300000.00,350000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6509.76, 350000.00,400000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7168.05, 400000.00,450000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7827.15, 450000.00,500000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8485.43, 500000.00,550000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9144.53, 550000.00,750000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10461.78, 750000.00,950000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11779.16, 950000.00,1150000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (13096.55, 1150000.00,1350000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (14413.93, 1350000.00,1550000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (15731.32, 1550000.00,1750000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (17048.70, 1750000.00,2000000.00, 3, 'Pcl1');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (18366.08, 2000000.00,2250000.00, 3, 'Pcl1');
--Palier classe 2
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2657.75, 000.00,80000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2894.96, 80000.00,150000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3487.58, 150000.00,200000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4079.38, 200000.00,250000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4672.81, 250000.00,300000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5266.25, 300000.00,350000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5858.87, 350000.00,400000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6451.49, 400000.00,450000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7044.11, 450000.00,500000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7637.54, 500000.00,550000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8230.16, 550000.00,750000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9416.35, 750000.00,950000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10602.41, 950000.00,1150000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11788.47, 1150000.00,1350000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (12974.52, 1350000.00,1550000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (14160.58, 1550000.00,1750000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (15346.63, 1750000.00,2000000.00, 4, 'Pcl2');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (16532.69, 2000000.00,2250000.00, 4, 'Pcl2');
--Palier classe 3
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2559.25, 000.00,80000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2787.44, 80000.00,150000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3358.71, 150000.00,200000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3929.17, 200000.00,250000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4500.45, 250000.00,300000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5070.90, 300000.00,350000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5642.18, 350000.00,400000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6212.64, 400000.00,450000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6783.91, 450000.00,500000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7354.37, 500000.00,550000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7925.64, 550000.00,750000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9067.24, 750000.00,950000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10208.97, 950000.00,1150000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11350.71, 1150000.00,1350000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (12492.44, 1350000.00,1550000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (13634.17, 1550000.00,1750000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (14775.90, 1750000.00,2000000.00, 5, 'Pcl3');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (15917.64, 2000000.00,2250000.00, 5, 'Pcl3');
--Palier classe 4
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2362.26, 000.00,80000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2573.21, 80000.00,150000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3100.16, 150000.00,200000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3627.12, 200000.00,250000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4154.07, 250000.00,300000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4681.02, 300000.00,350000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5207.98, 350000.00,400000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5734.93, 400000.00,450000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6261.88, 450000.00,500000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6788.84, 500000.00,550000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7315.79, 550000.00,750000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8369.70, 750000.00,950000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9423.60, 950000.00,1150000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10477.51, 1150000.00,1350000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11531.42, 1350000.00,1550000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (12585.33, 1550000.00,1750000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (13639.23, 1750000.00,2000000.00, 6, 'Pcl4');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (14693.14, 2000000.00,2250000.00, 6, 'Pcl4');
--Palier classe 5
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1969.10, 000.00,80000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2144.75, 80000.00,150000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2583.88, 150000.00,200000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3023.01, 200000.00,250000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3462.13, 250000.00,300000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3901.26, 300000.00,350000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4340.39, 350000.00,400000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4779.52, 400000.00,450000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5218.65, 450000.00,500000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5657.77, 500000.00,550000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6096.90, 550000.00,750000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6975.16, 750000.00,950000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7853.41, 950000.00,1150000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8731.67, 1150000.00,1350000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9609.63, 1350000.00,1550000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10488.18, 1550000.00,1750000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11366.44, 1750000.00,2000000.00, 7, 'Pcl5');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (12244.69, 2000000.00,2250000.00, 7, 'Pcl5');
--Palier classe 6
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1802.48, 000.00,80000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2131.62, 80000.00,150000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2460.76, 150000.00,200000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2680.73, 200000.00,250000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3229.85, 250000.00,300000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3778.14, 300000.00,350000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4327.26, 350000.00,400000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4514.40, 400000.00,450000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4760.64, 450000.00,500000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5088.96, 500000.00,550000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5663.52, 550000.00,750000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6525.36, 750000.00,950000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7428.24, 950000.00,1150000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8331.12, 1150000.00,1350000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9234.00, 1350000.00,1550000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10136.88, 1550000.00,1750000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11039.76, 1750000.00,2000000.00, 8, 'Pcl6');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11942.64, 2000000.00,2250000.00, 8, 'Pcl6');
--Palier classe 7
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1442.15, 000.00,80000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1705.62, 80000.00,150000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1969.10, 150000.00,200000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2144.75, 200000.00,250000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2583.88, 250000.00,300000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3023.01, 300000.00,350000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3462.13, 350000.00,400000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3901.26, 400000.00,450000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4340.39, 450000.00,500000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4779.52, 500000.00,550000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5306.47, 550000.00,750000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6257.92, 750000.00,950000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7224.00, 950000.00,1150000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8190.08, 1150000.00,1350000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9156.16, 1350000.00,1550000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10122.24, 1550000.00,1750000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (11088.32, 1750000.00,2000000.00, 9, 'Pcl7');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (12054.41, 2000000.00,2250000.00, 9, 'Pcl7');
--Palier classe 8
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (827.37, 000.00,80000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1003.02, 80000.00,150000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1354.32, 150000.00,200000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1705.62, 200000.00,250000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2144.75, 250000.00,300000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2583.88, 300000.00,350000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3023.01, 350000.00,400000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3462.13, 400000.00,450000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3901.26, 450000.00,500000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4340.39, 500000.00,550000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4779.52, 550000.00,750000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5657.77, 750000.00,950000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6536.03, 950000.00,1150000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7414.29, 1150000.00,1350000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8292.54, 1350000.00,1550000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9170.80, 1550000.00,1750000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10049.05, 1750000.00,2000000.00, 10, 'Pcl8');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (10927.31, 2000000.00,2250000.00, 10, 'Pcl8');
--Palier classe 9
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (703.26, 000.00,80000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (852.56, 80000.00,150000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1151.17, 150000.00,200000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1449.78, 200000.00,250000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (1823.04, 250000.00,300000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2196.30, 300000.00,350000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2569.56, 350000.00,400000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (2942.81, 400000.00,450000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3316.07, 450000.00,500000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (3689.33, 500000.00,550000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4062.59, 550000.00,750000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (4809.11, 750000.00,950000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (5555.63, 950000.00,1150000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (6302.14, 1150000.00,1350000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7048.66, 1350000.00,1550000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (7795.18, 1550000.00,1750000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (8541.70, 1750000.00,2000000.00, 12, 'Pcl9');
INSERT INTO ta_palier_classe (montant_prime_base, palier_montant_min, palier_montant_max, id_classe_ta_classe, code_palier_classe) VALUES (9288.21, 2000000.00,2250000.00, 12, 'Pcl9');

--SUPPRIMER TOUT CONTRAT ET DEVIS, REGLEMENT, ATTESTATION NOMINATIVE
--Attestation nominatvie
delete from ta_attestation_nominative;
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
--contrat
delete from ta_frais_impaye;

delete from ta_echeance;
update ta_dossier_rcd set contrat=false;
update ta_dossier_rcd set soumis_souscription=false;

--devis
delete from ta_r_t_statut_dossier_rcd;

delete from ta_dossier_rcd_activite;
delete from ta_dossier_rcd_taux_pib;
delete from ta_dossier_rcd;
delete from ta_ged_utilisateur;


--SUPPRIMER LES ACTIVITES EN TROP
--Amélioration des sols
DELETE FROM ta_activite a WHERE a.id_activite= 5;
--Sondages et forages
DELETE FROM ta_activite a WHERE a.id_activite= 6;
--Fondations spéciales
DELETE FROM ta_activite a WHERE a.id_activite= 13;
--Enduits Hydrauliques
DELETE FROM ta_activite a WHERE a.id_activite= 15;
--Béton précontraint in situ
DELETE FROM ta_activite a WHERE a.id_activite= 17;
--Zinguerie
DELETE FROM ta_activite a WHERE a.id_activite= 21;
--Etancheité et impérméabilisation de cuvelage, réservoirs ...
DELETE FROM ta_activite a WHERE a.id_activite= 23;
--Isolation thermique et acoustique par l'extérieur
DELETE FROM ta_activite a WHERE a.id_activite= 42;
--Capteurs solaires à eau chaude et à air chaud
DELETE FROM ta_activite a WHERE a.id_activite= 47;
--Four et cheminées industriels
DELETE FROM ta_activite a WHERE a.id_activite= 53;
--Ascenceurs
DELETE FROM ta_activite a WHERE a.id_activite= 54;
--Geothermie
DELETE FROM ta_activite a WHERE a.id_activite= 57;
--Eolien
DELETE FROM ta_activite a WHERE a.id_activite= 59;
--Démolition sans utilisation d'explosifs
DELETE FROM ta_activite a WHERE a.id_activite= 62;
--Démolition avec utilisation d'explosifs
DELETE FROM ta_activite a WHERE a.id_activite= 63;
--Paysagiste avec construction d'ouvrage
DELETE FROM ta_activite a WHERE a.id_activite= 64;
--Activité de génie civil
DELETE FROM ta_activite a WHERE a.id_activite= 67;
--Piscine
DELETE FROM ta_activite a WHERE a.id_activite= 79;
--Maison à ossature bois
DELETE FROM ta_activite a WHERE a.id_activite= 80;
--Installation à énérgie solaire par capteur photovoltaique
DELETE FROM ta_activite a WHERE a.id_activite= 81;

--Si les 3 dernères activités n'ont pas étés supprimer à cause d'id différent :
DELETE FROM ta_activite a WHERE a.libl_activite= 'Piscine';
DELETE FROM ta_activite a WHERE a.libl_activite= 'Maison à ossature bois';
DELETE FROM ta_activite a WHERE a.libl_activite= 'Installation à énergie solaire par capteur photovoltaïque';

--MODIFIER ACTIVITES EXISTANTES
--Terrassement
UPDATE ta_activite a SET id_classe_ta_classe = 7, code_activite = 43, description_activite = 'Défrichement, remise à niveau des terres, réalisation à ciel ouvert, de creusement et
de blindage de fouilles provisoires dans des sols, ainsi que des travaux de
rabattement de nappes nécessaires à l''exécution des travaux de remblai,
d''enrochement non lié et de comblement (sauf pour les carrières) ayant pour objet
soit de constituer par eux-mêmes un ouvrage soit de permettre la réalisation d''un
ouvrage.
Cette activité comprend la réalisation de sondages superficiels. Cette activité ne
comprend pas les forages.' WHERE a.id_activite =  4;
--VRD
UPDATE ta_activite a SET id_classe_ta_classe = 7, code_activite = 44, description_activite = 'Réalisation de canalisations, d''assainissement autonome, de réseaux enterrés, de
voiries, de poteaux et clôtures. Cette activité comprend les travaux accessoires ou
complémentaires de terrassement et de fouilles.
Cette activité ne comprend pas la réalisation d’espace vert.' WHERE a.id_activite =  7;
--Paysagiste (aménagement Paysagé)
UPDATE ta_activite a SET libl_activite = 'Aménagement paysagé', code_activite = 45, id_classe_ta_classe = 10, description_activite = 'Réalisation et entretien d''espaces verts, parcs et jardins, limité aux activités suivantes
:
-Terrassement nécessaire à l’aménagement d’espaces verts,
-Petite maçonnerie paysagère telle que murets d’une hauteur inférieure à 1m,
poteaux, clôtures, allées piétonnières, bordures, chaussées, trottoirs, pavage,
-Systèmes d’arrosage intégrés,
-Brise vue, treillis,
-Pose d’abris de jardins à usage de stockage inférieurs à 20 m²,
-Eclairage extérieur des jardins,
-Installation de récupérateurs d’eau de pluie sans raccordement au réseau sanitaire,
-Plantation et toutes opérations d’entretien de végétaux, Murs végétalisés' WHERE a.id_activite =  8;
--Montage échaffaudage 
UPDATE ta_activite a SET id_classe_ta_classe = 6, code_activite = 46, description_activite = 'Montage pour le compte de tiers de tous étaiements, échafaudages fixes, suspendus
ou élévateurs employés à la construction ou à l''entretien des immeubles,
monuments et édifices ainsi que le montage de structures évènementielles' WHERE a.id_activite =  9;
--Traitement amiante
UPDATE ta_activite a SET id_classe_ta_classe = 3, code_activite = 47, description_activite = 'Retrait et évacuation de l''amiante, des matériaux, et produits en contenant, ou leur
maintien avec confinement, dans tout ouvrage ou partie d''ouvrage.
Cette activité nécessite :
- Une Qualification Amiante (AFNOR ou QUALI-PV)
- Un processus de contrôle
- Un agrément de transport pour les matières dangereuses si transport. Cette activité ne comprend pas : les travaux de démolition' WHERE a.id_activite =  10;
--Traitement curatifs Insectes xylophages...
UPDATE ta_activite a SET id_classe_ta_classe = 4,code_activite = 48, description_activite = 'Traitement curatif des bois en œuvre et des constructions contre les insectes à larves
xylophages, les termites et les champignons dans les charpentes et menuiseries en
bois, mais aussi les sols, fondations, murs, cloisons et planchers.' WHERE a.id_activite =  11;
--Assechement des murs
UPDATE ta_activite a SET id_classe_ta_classe  = 5,code_activite = 49, description_activite = 'Traitement des murs contre les remontées d''humidité par capillarité. Cette activité
comprend les travaux préparatoires et de traitement proprement dits et les travaux
accessoires et complémentaires de remplacement des parements' WHERE a.id_activite =  12;
--Maçonnerie (et béton armé sauf précontraint in situ...)
UPDATE ta_activite a SET libl_activite = 'Maçonnerie',code_activite = 50, description_activite = 'Réalisation de maçonnerie en béton armé préfabriqué ou non, en béton précontraint
préfabriqué (hors précontrainte in situ), en blocs agglomérés de mortier ou de béton
cellulaire, en pierre naturelles ou briques, ceci tant en infrastructure qu’en
superstructure (hors parois de soutènement structurellement autonomes), par
toutes les techniques de maçonnerie de coulage, hourdage (hors revêtement mural
agrafé, attaché ou collé).
Cette activité comprend les travaux de :
- enduits à base de liants hydrauliques ou de synthèse,
- ravalement en maçonnerie,
- de briquetage, pavage,
- dallage, chape,
- fondations superficielles (semelles filantes, isolées, radiers et puits courts).
Ainsi que les travaux accessoires ou complémentaires de :
- complément d’étanchéité des murs enterrés,
- pose de matériaux contribuant à l''isolation intérieure,
- la pose de renforts bois ou métal nécessités par l''ouverture de baies et les reprises
en sous-œuvre,
- pose d''huisseries,
- plâtrerie,
- carrelage, faïence et revêtement en matériaux durs à base minérale,
- calfeutrement de joints.' WHERE a.id_activite =  14;
--Taille de pierre
UPDATE ta_activite a SET id_classe_ta_classe = 8, code_activite = 51, description_activite = 'Taille et façonnage de pierres de construction et de menus ouvrages en pierre.' WHERE a.id_activite =  16;
--Charpente et structure en bois
UPDATE ta_activite a SET id_classe_ta_classe = 8, code_activite = 52, description_activite = 'Réalisation de charpentes, structures et ossatures à base de bois à l''exclusion des
façades-rideaux.
Cette activité comprend les travaux accessoires ou complémentaires de :
- couverture, bardage, châssis divers, lorsque ceux-ci sont fixés directement à
l’ossature
- supports de couverture ou d''étanchéité,
- plafonds, faux plafonds, cloisons en bois et autres matériaux, planchers et
parquets, hors platelage extérieur,
- isolation thermique et acoustique liées à l’ossature et la charpente,
- traitement préventif des bois,
- mise en œuvre de matériaux ou de tous éléments métalliques concourant à
l''édification, au renforcement ou à la stabilité des charpentes et escaliers. Cette activité ne comprend pas le traitement curatif et la réalisation de
constructions à ossature bois.' WHERE a.id_activite =  18;
--Charpente et structure métallique
UPDATE ta_activite a SET id_classe_ta_classe = 8, code_activite = 53, description_activite = 'Réalisation de charpentes, structures et ossatures métalliques.
Cette activité comprend les travaux accessoires ou complémentaires de :
- couverture, bardage, châssis divers, lorsque ceux-ci sont métalliques et fixés
directement à l’ossature.
- supports de couverture ou d''étanchéité,
- protection et traitement contre la corrosion,
- traitement pour la stabilité au feu par peinture ou flocage,
- travaux en sous œuvre par structure métallique,
- isolation thermique et acoustique liées à l’ossature ou à la charpente
Cette activité ne comprend pas les travaux de façades-rideaux.' WHERE a.id_activite =  19;
--Couverture
UPDATE ta_activite a SET id_classe_ta_classe = 7, libl_activite='Couverture', code_activite = 54, description_activite = 'Réalisation de couverture en tous matériaux, y compris par bardeau bitumé.
Cette activité comprend les travaux de :
- zinguerie et éléments accessoires en tous matériaux,
- pose de châssis de toit (y compris exutoires en toiture),
- réalisation d''isolation et d''écran sous toiture,
- ravalement réfection des souches hors combles
- installation de paratonnerre.
Ainsi que les travaux accessoires ou complémentaires de :
- raccords d’étanchéité,
- réalisation de bardages verticaux, vêtage et vêture
Cette activité ne comprend pas : les travaux d’étanchéité de toiture et terrasse, la
pose de capteurs solaires intégrés ou non au bati, les couvertures textiles' WHERE a.id_activite =  20;
--Etanchéité de toiture, terrasse...
UPDATE ta_activite a SET id_classe_ta_classe = 4, code_activite = 55, description_activite = 'Réalisation d''étanchéité de toiture, terrasse et plancher intérieur par mise en œuvre
de matériaux bitumineux ou de synthèse sur des supports horizontaux ou inclinés, y
compris la pose du support d''étanchéité.
Ainsi que la réalisation des travaux de :
- étanchéité de parois enterrées (hors cuvelage)
- zinguerie et éléments accessoires en PVC,
- châssis de toit (y compris exutoires en toiture).
Cette activité comprend dans la limite éventuelle fixée au procédé, la mise en
œuvre de matériaux d''isolation et inclut tous travaux préparant l''application ou
assurant la protection du revêtement étanche, ainsi que ceux complétant
l''étanchéité des ouvrages.
Cette activité ne comprend pas les travaux de couverture' WHERE a.id_activite =  22;
--Calfeutrement , protection, imperméabilité et étanchéité des façades
UPDATE ta_activite a SET id_classe_ta_classe = 6, code_activite = 57, description_activite = 'Réalisation de travaux de protection et de réfection des façades par revêtement
d''imperméabilisation à base de polymères de classe I1, I2, I3, et systèmes
d''étanchéité à base de polymères de classe I4.
Cette activité comprend les travaux de :
- étanchéité des sols d''ouvrage lorsqu''il domine les parties non closes du bâtiment,
- calfeutrement de joints de construction aux fins d’étanchéité à l’eau et à l’air, -
d''isolation thermique par l''extérieur.
Cette activité ne comprend pas la réalisation d’isolation thermique par l’extérieur.' WHERE a.id_activite =  24;
--Menuiserie extérieure
UPDATE ta_activite a SET libl_activite = 'Menuiseries extérieures', id_classe_ta_classe = 9, code_activite = 60, description_activite = 'Réalisation de menuiseries extérieures, y compris leur revêtement de protection,
quel que soit le matériau utilisé.
Cette activité comprend les travaux de :
- mise en œuvre des éléments de remplissage y compris les produits en résine ou en
plastique et les polycarbonates,
- calfeutrement sur chantier des joints de menuiseries,
- mise en œuvre des fermetures et de protections solaires intégrées ou non,
- mise en œuvre des fermetures et de protections solaires intégrées ou non,
- d’habillage et de liaisons intérieures et extérieures.
Ainsi que les travaux accessoires ou complémentaires de :
- vitrerie et de miroiterie,
- alimentations, commandes et branchements électriques éventuels,
- mise en œuvre des matériaux ou produits contribuant à l’isolation thermique,
acoustique, feu et de sécurité,
- traitement préventif des bois.
Cette activité ne comprend pas la réalisation de : façades rideaux et verrières,
vérandas, traitement curatif des bois, platelage extérieur' WHERE a.id_activite =  25;
--Agencement de cuisine
UPDATE ta_activite a SET libl_activite = 'Agencement de cuisines', id_classe_ta_classe = 10, code_activite = 71, id_famille_activite_ta_famille_activite=4, description_activite = 'Réalisation de l’agencement de cuisines, autres que professionnelles et/ou
collectives, comportant la pose de meubles.
Cette activité comprend les travaux accessoires ou complémentaires de :
-Raccord de plomberie, branchements et raccordements électriques, de VMC,
-revêtements de sols et murs, hors étanchéité
- pose des appareils électroménagers'  WHERE a.id_activite =  26;
--Vérandas
UPDATE ta_activite a SET  id_classe_ta_classe = 7, code_activite = 64, description_activite = 'Réalisation de vérandas, y compris leur revêtement de protection, quel que soit le
matériau utilisé.
Cette activité comprend les travaux de :
- mise en œuvre des éléments de remplissage y compris les produits en résine ou en
plastique et les polycarbonates,
- calfeutrement sur chantier des joints de menuiserie,
- mise en œuvre des fermetures et de protections solaires intégrées ou non,
- d''habillage et de liaisons intérieures et extérieures.
Ainsi que les travaux accessoires ou complémentaires de :
- vitrerie et de miroiterie, - alimentations, commandes et branchements électriques
éventuels,
- mise en œuvre des matériaux ou produits contribuant à l''isolation thermique,
acoustique, feu et de sécurité,
- traitement préventif des bois. Cette activité ne comprend pas : la réalisation de serres de jardins ou agricoles, les
verrières, les travaux de façades-rideaux' WHERE a.id_activite =  27;
--Bardage de façade
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 61, description_activite = 'Réalisation de bardages par mise en œuvre de clins ou de panneaux, avec ou sans
incorporation d’isolant. Cette activité comprend les travaux de vêture et de vêtage.
Ainsi que les travaux accessoires ou complémentaires de mises en œuvre des
matériaux ou produits contribuant à l’isolation thermique.
Cette activité ne comprend pas les travaux de façades-rideaux' WHERE a.id_activite =  28;
--Façade rideaux
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 62, description_activite = 'Réalisation des façades-rideaux quel que soit le matériau utilisé, y compris la mise
en place des éléments de remplissage et de façade selon les techniques de vitrage
extérieur colle (VEC) ou de vitrage extérieur attache (VEA).
Cette activité ne comprend pas : la réalisation de verrières' WHERE a.id_activite =  29;
--Structure et couverture textiles
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 63, description_activite = 'Réalisation de superstructures et couvertures à base de membranes textiles
tendues ou gonflées. Cette activité comprend la réalisation des structures
complémentaires en support bois, métal ou autres matières ainsi que tous les
éléments d’évacuation d’eaux nécessaires.' WHERE a.id_activite =  30;
--Menuiserie intérieures
UPDATE ta_activite a SET  id_classe_ta_classe = 10, code_activite = 70, description_activite = 'Réalisation de tout travaux de menuiserie intérieure, y compris leur revêtement de
protection, quel que soit le matériau utilisé, pour les portes, murs, plafonds,
cloisons, planchers y compris surélevés, parquets y compris pour les sols sportifs,
revêtements, escaliers et garde-corps, stands, expositions, fêtes, agencements et
mobiliers.
Cette activité comprend les travaux de :
- mise en œuvre des éléments de remplissage y compris les produits en résine ou en
plastique et les polycarbonates,
- habillage et de liaisons intérieures et extérieures.
Ainsi que les travaux accessoires ou complémentaires de :
- vitrerie et de miroiterie,
- mise en œuvre des matériaux ou produits contribuant à l''isolation thermique,
acoustique, et à la sécurité incendie,
- traitement préventif des bois.
Cette activité ne comprend pas : l’aménagement de cuisines, l’aménagement de
salles de bains, le traitement curatif des bois' WHERE a.id_activite =  31;
--Plâtrerie - staff - stuc - Gypserie
UPDATE ta_activite a SET  id_classe_ta_classe = 10, code_activite = 73, description_activite = 'Réalisation de plâtrerie, cloisonnement et faux plafonds à base de plâtre en
intérieur.
Cette activité comprend la mise en œuvre des matériaux ou produits contribuant à
l''isolation thermique, acoustique et à la sécurité incendie. Cette activité comprend les travaux accessoires ou complémentaires de : -
menuiseries intégrées aux cloisons, - doublage thermique ou acoustique intérieur ' WHERE a.id_activite =  32;
--Serrurerie métallerie
UPDATE ta_activite a SET  id_classe_ta_classe = 12, code_activite = 74, description_activite = 'Réalisation de serrurerie et métallerie à partir de câbles, de profilés de tôle en tous
métaux ou en matériaux de synthèse.
Cette activité comprend les travaux accessoires ou complémentaires de :
- protection contre les risques de corrosion,
- installation et le raccordement des alimentations électriques et automatismes
nécessaires au fonctionnement des équipements,
- mise en œuvre des éléments de remplissage, y compris les produits en résilient ou
en plastique et les polycarbonates,
- mise en œuvre des matériaux ou produits contribuant à l''isolation thermique,
acoustique, et à la sécurité incendie.
Cette activité ne comprend pas la réalisation de : travaux de structure métallique,
façades rideaux, vérandas ' WHERE a.id_activite =  33;
--Vitrerie miroiterie
UPDATE ta_activite a SET  id_classe_ta_classe = 10, code_activite = 75, description_activite = 'Réalisation de tout travaux à partir de produits verriers, y compris les produits en
résine ou en plastique, les polycarbonates à l’exclusion des Façades-rideaux.
Cette activité comprend les travaux accessoires ou complémentaires d''encadrement
des éléments verriers.
Cette activité ne comprend pas la réalisation de : vitrage extérieur colle (VEC) ou
de vitrage extérieur attache (VEA), verrières, vérandas, fenêtres et portes fenêtres' WHERE a.id_activite =  34;
--Peinture intérieure
UPDATE ta_activite a SET  libl_activite = 'Peinture intérieure', id_classe_ta_classe = 12, code_activite = 76, description_activite = 'Réalisation de peinture intérieure, y compris les revêtements plastiques épais ou
semi-épais (RPE et RSE), de pose de revêtements souples, textiles, plastiques ou
assimilés sur surfaces horizontales et verticales.
Cette activité comprend les travaux accessoires ou complémentaires de :
- menuiserie,
- revêtement de faïence,
- nettoyage, sablage, grenaillage, gommage.
Cette activité ne comprend pas les réalisations suivantes : les travaux
d''imperméabilisation et d''étanchéité notamment à base de polymères de classe I2
à I4, les travaux de peinture extérieure, sols coulés à base de résine de synthèse,
les travaux de peinture industrielle' WHERE a.id_activite =  35;
--Nettoyage intérieur
UPDATE ta_activite a SET  libl_activite = 'Nettoyage (intérieur)', id_classe_ta_classe = 12, code_activite = 77, description_activite = 'Nettoyage de locaux exercé à titre accessoire de l''activité de peinture' WHERE a.id_activite =  36;
--Revetements de surfaces en matériaux souples et parquets flottants
UPDATE ta_activite a SET  id_classe_ta_classe = 12, code_activite = 79, description_activite = 'Réalisation de parquets collés ou flottants, de revêtements souples, avec ou sans
support textile, en tout matériaux plastiques, caoutchouc et produits similaires, ou
en bois (feuilles de placage sur kraft ou sur textile, placages collés ou contreplaqués
minces collés) ou tout autre relevant des mêmes techniques de mise en œuvre.
Cette activité ne comprend pas la réalisation des platelages extérieurs.' WHERE a.id_activite =  38;
--Revêtements de surfaces en matériaux durs -Chapes et sols coulés - Marbrerie funéraire
UPDATE ta_activite a SET  id_classe_ta_classe = 12, code_activite = 81, description_activite = 'Réalisation de revêtement de surfaces en carrelage ou en tout autre produit en
matériaux durs, naturels ou artificiels, chapes et sols coules, marbrerie funéraire.
Cette activité comprend les travaux accessoires ou complémentaires de :
- pose de résilient acoustique ou d''isolation sous chape ou formes flottantes, SPEC
(système de protection de l’eau sous carrelage)
- étanchéité sous carrelage non immergé limité aux salles de bains et d’eau
privatives
- protection par imperméabilisation des supports de carrelage et faïence.
Cette activité ne comprend pas la réalisation de revêtements de façades agrafés
ou attachés.' WHERE a.id_activite =  39;
--Revêtements de surfaces à base de résines...
UPDATE ta_activite a SET  id_classe_ta_classe = 6, code_activite = 82, description_activite = 'Réalisation de chapes, revêtements de murs et sols à base de liant synthétique ou
résine, y compris sols sportifs et résine de sols industriels.
Cette activité nécessite : - un avis technique des produits utilisés,
- formation aux techniques de pose.
Cette activité ne comprend pas : les travaux à vocation d’étanchéité, les
techniques d''agrafages et attaches, les chantiers supérieurs à 500m²' WHERE a.id_activite =  40;
--Isolation intérieure thermique - Acoustique 
UPDATE ta_activite a SET  libl_activite = 'Isolation intérieure thermique - Acoustique', id_classe_ta_classe = 10, code_activite = 83, description_activite = 'Réalisation, y compris leurs revêtements et menuiseries, de :
- Isolation thermique de murs, parois, sols, plafonds et toitures de tous ouvrages,
- Isolation et de traitement acoustique par la mise en œuvre de matières ou
matériaux adaptés,
- Calorifugeage des circuits, tuyauteries et appareils' WHERE a.id_activite =  41;
--Isolation thermique frigorifique
UPDATE ta_activite a SET  libl_activite = 'Isolation thermique frigorifique', id_classe_ta_classe = 9, code_activite = 84, description_activite = 'Réalisation de travaux d''isolation frigorifique des locaux d''une capacité maximale de
100m3, et fonctionnant à toutes températures' WHERE a.id_activite =  43;
--Plomberie - Installations sanitaires
UPDATE ta_activite a SET  libl_activite = 'Plomberie - Installations sanitaires', id_classe_ta_classe = 10, code_activite = 90, description_activite = 'Réalisation d''installations d’eau chaude et d’eau froide sanitaires (distribution,
évacuation) et de réseaux de canalisations de fluide basse pression ou de gaz.
Cette activité comprend :
-l’installation de chauffe-eau y compris thermodynamique
-la réalisation de gouttières, descentes eaux pluviales.
Cette activité comprend les travaux accessoires ou complémentaires de :
- platelage, réalisation de socle et support d''appareils et équipements,
- chapes de protection des installations de chauffage,
- tranchées, trous de passage, saignées et raccords,
- calorifugeage, isolation thermique et acoustique, - raccordement électrique du
matériel,
- l’entretien des chaudières à gaz.
Cette activité ne comprend pas : la mise en œuvre de PAC autre que les chauffeeaux
thermodynamiques, les travaux de géothermie, la pose de carrelage ' WHERE a.id_activite =  44;
--Installation de sprinkler - RIA
UPDATE ta_activite a SET  libl_activite = 'Installation de sprinkler - RIA', id_classe_ta_classe = 7, code_activite = 91, description_activite = 'Pose sans conception de réseaux sprinklers et RIA. Cette activité comprend la
réalisation d''installations d''eau (distribution, évacuation).
Cette activité comprend les travaux accessoires ou complémentaires de :
- platelage, réalisation de socle et support d''appareils et équipements,
- chapes de protection des installations de chauffage,
- tranchées, trous de passage, saignées et raccords,
- calorifugeage, isolation thermique et acoustique,
- raccordement électrique du matériel.' WHERE a.id_activite =  45;
--Installation thermique de génie climatique
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 92, description_activite = 'Réalisation d''installations (production, distribution, évacuation) de chauffage, de
rafraîchissement et de climatisation.
Cette activité comprend :
-les installations de production frigorifique
- les installations de capteurs thermiques
-les installations de ventilation mécanique contrôlée (VMC).
Cette activité comprend les travaux accessoires ou complémentaires de :
- platelage, réalisation de socle et support d''appareils et équipements,
- chapes de protection des installations de chauffage,
- tranchées, trous de passage, saignées et raccords,
- raccordement électrique du matériel
- calorifugeage
-installations de technique de gestion centralisée des installations concernées.
Cette activité ne comprend pas : les travaux de géothermie, la pose de capteurs
solaires photovoltaïques, la pose de poêles, les travaux de fumisterie' WHERE a.id_activite =  46;
--Fumisterie
UPDATE ta_activite a SET  libl_activite = 'Fumisterie', id_classe_ta_classe = 7, code_activite = 94, description_activite = 'Réalisation (hors fours et cheminée industriels) de systèmes d''évacuation des
produits de combustion.
Cette activité comprend les travaux de :
-tubage, chemisage et ramonage des conduits
- pose d''insert, poêle à bois, poêle à granulés
-construction et installations d''âtres et foyers
- construction de socles de chaudières,
- pose sur le sol de carreaux réfractaires et céramiques.
Ainsi que les travaux accessoires ou complémentaires de :
- raccords d''enduits divers,
- calorifugeage des conduits,
- revêtements en carreaux et panneaux de faïence-réfection des souches' WHERE a.id_activite =  48;
--Installation d'aéraulique et de conditionnement d'air
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 95, description_activite = 'Réalisation d''installations (production, distribution, évacuation) assurant les
fonctions de chauffage, renouvellement et traitement de l''air, de rafraîchissement,
y compris aérothermie.
Cette activité comprend les travaux accessoires et nécessaires à l''installation et au
fonctionnement de ces installations de :
-platelage, réalisation de socle et support d''appareils et équipements,
-chapes de protection des installations de chauffage,
- tranchées, trous de passage, saignées et raccords,
-calorifugeage, isolation thermique et acoustique,
- raccordement électrique du matériel
-installations de technique de gestion centralisée des installations concernées
Cette activité ne comprend pas : les travaux de géothermie, la pose de capteurs
solaires photovoltaïques' WHERE a.id_activite =  49;
--Electricité
UPDATE ta_activite a SET  id_classe_ta_classe = 12, code_activite = 96, description_activite = 'Réalisation de réseaux de distribution de courant électrique, de chauffage
électrique, ainsi que le raccordement et l''installation d''appareils électriques (hors
pose de capteurs solaires).
Cette activité comprend :
-l''installation de ventilation mécanique contrôlée (VMC)
-la pose de dispositifs de protection contre les effets de la foudre
-les installations de mise à la terre
-les courants faibles : installations de domotique, VDI, télécommunication, fibre
optique
Cette activité comprend les travaux accessoires ou complémentaires de :
- tranchées, trous passage, saignées et raccords,
- chapes de protection des installations de chauffage.
Cette activité ne comprend pas : les travaux de géothermie, la pose ou le raccord
de capteurs solaires photovoltaïques' WHERE a.id_activite =  50;
--Pose d'antennes, paraboles et alarmes (particuliers)
UPDATE ta_activite a SET  id_classe_ta_classe = 12, code_activite = 97, description_activite = 'Réalisation de systèmes destinés à capter les ondes électromagnétiques (antennes
ou paraboles) en vue de recevoir la télévision grand public, ainsi que le raccord et
l''installation d''appareils électriques (hors pose de capteurs solaires).
Cette activité comprend également la réalisation d''installations de systèmes
d''alarmes anti-intrusion, de vidéosurveillance ou incendie à destination des
particuliers, ainsi que l''installation et la pose de dispositifs de protection contre les
effets de la foudre.
Cette activité comprend les travaux accessoires ou complémentaires :
- de tranchées, trous passage, saignées et raccords.
- raccords d''enduits divers,
- zinguerie et éléments accessoires en FYC,
- raccords d''étanchéité.
Cette activité ne comprend pas la réalisation d''antennes ou pylônes relais' WHERE a.id_activite =  51;
--Pose d'antennes, paraboles et alarmes (professionnels)
UPDATE ta_activite a SET  id_classe_ta_classe = 9, code_activite = 98, description_activite = 'Réalisation de systèmes destinés à capter les ondes électromagnétiques (antennes
ou paraboles) en vue de recevoir la télévision grand public, ainsi que le raccord et
l''installation d''appareils électriques (hors pose de capteurs solaires).
Cette activité comprend également la réalisation d''installations de systèmes
d''alarmes anti-intrusion, de vidéosurveillance ou incendie à destination des
particuliers, ainsi que l''installation et la pose de dispositifs de protection contre les
effets de la foudre.
Ainsi que les travaux accessoires ou complémentaires :
- de tranchées, trous passage, saignées et raccords.
- raccords d''enduits divers,
- zinguerie et éléments accessoires en FYC,
- raccords d''étanchéité.
Cette activité ne comprend pas : la réalisation d''antennes ou pylônes relais, la
pose d’alarmes dans des structures dites sensibles (bijouteries, HIFI-vidéo,
banque, bureau de change…)' WHERE a.id_activite =  52;






--AJOUT D'ACTIVITES
--Peinture Extérieure
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Peinture extérieure', 6, 78, 4,'Réalisation de peinture extérieure, de ravalement en peinture ou par nettoyage
(lavage, nettoyage haute pression, décapage) y compris réfection partielle en liants
hydrauliques ou avec des matériaux de synthèse sous toutes leurs formes.
Cette activité comprend les travaux de préparation de surfaces et d’application de
revêtements pour assurer la protection des ouvrages.
Cette activité comprend les travaux accessoires ou complémentaires de :
- nettoyage, sablage, grenaillage, gommage.
Cette activité ne comprend pas les réalisations suivantes : ravalementrevêtements
de façades tels que définis dans l’activité 56, les travaux d''imperméabilisation et d''étanchéité notamment à base de polymères de classe I2
à I4'  );
--Démolition
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Démolition', 4, 42, 1,'Démolition ou déconstruction, totale ou partielle, d’ouvrages par des moyens
manuels ou mécaniques. Cette activité comprend les travaux de sciage et carottage
béton ainsi que les travaux d’ouverture de murs porteurs.
Cette activité ne comprend pas : les travaux de désamiantage, l’utilisation d’explosifs,
le terrassement, les travaux de maçonnerie'  );
--Ravalement - Revêtements de façades par enduits
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Ravalement - Revêtements de façades par enduits', 9, 56, 3,'Réalisation de revêtement de façade par enduits, mince ou épais, à base de liants
hydrauliques ou de synthèse.
Cette activité comprend les travaux de :
- nettoyage, sablage, grenaillage et peinture de façade
- isolation acoustique et thermique par l''intérieur et l''extérieur.
Cette activité ne comprend pas : les travaux d''imperméabilisation et d''étanchéité
ainsi que les travaux d’isolation thermique par l’extérieur.'  );
--Nettoyage (Extérieur)
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Nettoyage (Extérieur)', 10, 58, 3,'Nettoyage haute pressions de sols, façades et toitures (démoussage)'  );
--Isolation Thermique par l’extérieur
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Isolation Thermique par l’extérieur', 9, 59, 3,'Réalisation de travaux d''isolation thermique par l’extérieur associés à un enduit
mince ou des parements collés. Ainsi que les travaux d’intégration des accessoires
contribuant à la ventilation ainsi qu''aux fermetures associées.
Cette activité ne comprend pas : les façades-rideaux, les systèmes avec des
revêtements de façades en pierre attachées ou agrafée, le ravalement, le
calfeutrement protection, imperméabilité et étanchéité des façades.'  );
--Revêtement de façades attachés, agrafés ou collés
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Revêtement de façades attachés, agrafés ou collés', 8, 65, 3,'Réalisation de revêtements en pierre, carrelage, brique ou tout autre matériau dur
assimilé utilisant les techniques d’agrafage, attache ou collage.
Cette activité comprend les travaux accessoires ou complémentaires d’isolation
thermique.'  );

--Agencement de salle de bains
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Agencement de salle de bains', 10, 72, 4,'Réalisation de travaux de plomberie, carrelage, étanchéité, cloisonnement et pose
de meubles, pour l‘agencement de salles de bains d’habitations privées.
Cette activité comprend les travaux accessoires ou complémentaires de :
-raccord de plomberie, branchements et raccordements électriques, de VMC,
-revêtements de sols et murs
- miroiterie'  );
--Platelage
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Platelage', 10, 80, 4,'Réalisation de revêtements de sols extérieurs en bois, bois résine ou matériaux de
synthèse.'  );
--Installation de pompes à chaleur
INSERT INTO ta_activite (libl_activite, id_classe_ta_classe, code_activite,id_famille_activite_ta_famille_activite, description_activite) VALUES ('Installation de pompes à chaleur', 9, 93, 5,'Réalisation d''installations de pompes à chaleur air-air et/ou air-eau comprenant la
réalisation d’installations (production, distribution, évacuation) de chauffage, d’eau,
de rafraîchissement y compris aérothermie et de climatisation.
Cette activité comprend les travaux accessoires ou complémentaires de :
- platelage, réalisation de socle et support d''appareils et équipements,
- chapes de protection des installations de chauffage,
- tranchées, trous de passage, saignées et raccords,
- raccordement électrique du matériel
- calorifugeage
-installations de technique de gestion centralisée des installations concernées
Cette activité ne comprend pas : les travaux de géothermie, la pose de capteurs
solaires photovoltaïques, la pose de poêles, les travaux de fumisterie '  );



-- CREATION D'UNE VUE POUR MIEUX VOIR LES ACTIVITES ET LEUR FAMILLES/CLASSES (optionnel mais très pratique)
CREATE OR REPLACE VIEW public.vue_activite_classe AS 
 SELECT a.id_activite,
    a.code_activite,
    a.libl_activite,
    a.description_activite,
    c.libl_classe,
    f.libl_famille
   
   FROM ta_activite a JOIN ta_classe c on a.id_classe_ta_classe = c.id_classe JOIN ta_famille_activite f on a.id_famille_activite_ta_famille_activite = f.id_famille_activite
   ORDER BY a.code_activite ASC;
 
 --MODIF APPLIQUEE LE 17/10/18 sur TEST PROD--
-------- MODIF FRANCHISES-----------
DELETE FROM ta_t_franchise f WHERE f.code_t_franchise = 'FR5000';
DELETE FROM ta_t_franchise f WHERE f.code_t_franchise = 'FR10000';
UPDATE ta_t_franchise f SET montant = 3000.00, taux_montant = -5.00, code_t_franchise='FR3000', libl_t_franchise='3000€' WHERE f.code_t_franchise='FR3500';
UPDATE ta_t_franchise f SET montant = 2000.00, taux_montant = -3.00, code_t_franchise='FR2000', libl_t_franchise='2000€' WHERE f.code_t_franchise='FR2500';
UPDATE ta_t_franchise f SET montant = 1500.00, taux_montant = 0.00, code_t_franchise='FR1500', libl_t_franchise='1500€' WHERE f.code_t_franchise='FR1500';
--Modif des fk franchises sur les activités (changement provisoire en attendant réponse Ylyade)
UPDATE ta_activite a SET id_t_franchise_ta_t_franchise = 3 WHERE a.id_t_franchise_ta_t_franchise= 4;
---------MODIF FRAIS FRANCTIONNEMENT-------------
UPDATE ta_t_echeance e SET taux_echeance = 3.00 WHERE e.code_t_echeance='SEMESTRIEL';
UPDATE ta_t_echeance e SET taux_echeance = 6.00 WHERE e.code_t_echeance='TRIMESTRIEL';
---------MODIF SOUS TRAITANCE
DELETE FROM ta_t_sous_traitance st WHERE st.code_t_sous_traitance = 'ST_31_50';
DELETE FROM ta_t_sous_traitance st WHERE st.code_t_sous_traitance = 'ST_51_100';
UPDATE ta_t_sous_traitance st SET taux = 30.00 WHERE st.code_t_sous_traitance= 'ST_0_30' ;
INSERT INTO ta_t_sous_traitance (base_min, base_max, code_t_sous_traitance, libl_t_sous_traitance) VALUES (30, 100,'ST_30_100','30 à 100%' );
--------VALIDATION ATTESTATION NOMINATIVE----------
ALTER TABLE ta_attestation_nominative
ADD COLUMN validation_courtier BOOLEAN DEFAULT false, ADD COLUMN validation_ylyade BOOLEAN DEFAULT false;
--------CREATION DE LA TABLE ta_sous_donnee -----------
	CREATE TABLE ta_sous_donnee (
 id_sous_donnee  SERIAL,
 json_data  TEXT,
 id_dossier  integer,
  CONSTRAINT prk_constraint_id_sous_donnee PRIMARY KEY (id_sous_donnee),
  CONSTRAINT ta_sous_donnee_id_dossier_fk FOREIGN KEY (id_dossier)
      REFERENCES public.ta_dossier_rcd (id_devis_rc_pro_detail) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
 --FIN MODIF APPLIQUEE LE 17/10/18 sur TEST PROD--
 -- MODIF APPLIQUE LE 18/10/18 sur TEST PROD--
--AJOUT DE LA PJ PRO JURIDICA ETENDUE ET MODIF MONTANT PJ PRO
INSERT INTO ta_taux_assurance (taux_taux_assurance, code_taux_assurance, libelle_taux_assurance, taux_ht_taux_assurance) VALUES (262.16, 'Pj_ETENDUE', 'Protection judiciaire PRO JURIDICA ETENDUE', 227.04);
UPDATE ta_taux_assurance ta SET taux_taux_assurance = 69.09, taux_ht_taux_assurance = 59.83 WHERE ta.code_taux_assurance = 'Pj';

-- MODIF DU NUMPOLICE POUR LES DOSSIERS--
UPDATE ta_gen_code_ex gen SET valeur_gen_code = '18{@num}S' WHERE gen.code_gen_code = 'numDossierPolice';
ALTER TABLE ta_dossier_rcd 
DROP COLUMN img_devis_rc_pro;	
ALTER TABLE ta_dossier_rcd
ADD COLUMN lettre_pj_num_police dlib50;
 -- FIN MODIF APPLIQUE LE 18/10/18 sur TEST PROD--

--MODIF NICOLAS
--MODIF LISTE ACTIVITE DANS ATTESTATION NOMINATIVE--
CREATE TABLE ta_attestation_nominative_activite
(
  id_attestation_nominative_activite SERIAL NOT NULL,
  activite dlib100,
  classe_associe did4,
  franchise did9facult,
  id_attestation_nominative_ta_attestation_nominative did_facultatif,
  id_activite_ta_activite did_facultatif,
  qui_cree dlib50,
  quand_cree date_heure_lgr,
  qui_modif dlib50,
  quand_modif date_heure_lgr,
  ip_acces dlib50,
  version_obj did_version_obj,
  palier_montant_min did9facult,
  palier_montant_max did9facult,
  montant_prime_base did9facult,
  pourcent_exerce_par_entreprise numeric,
  pourcent_sous_traite numeric,
  commentaire text,
  min_ca did9facult,
  max_ca did9facult,
  prime_base did9facult,
  CONSTRAINT prk_attestation_nominative_activite PRIMARY KEY (id_attestation_nominative_activite),
  CONSTRAINT fk_ta_attestation_nominative_activite_id_activite_ta_activite FOREIGN KEY (id_activite_ta_activite)
      REFERENCES public.ta_activite (id_activite) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ta_attestation_nominative_activite_id_attestation FOREIGN KEY (id_attestation_nominative_ta_attestation_nominative)
      REFERENCES public.ta_attestation_nominative (id_attestation_nominative) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
--FIN MODIF LISTE ACTIVITE DANS ATTESTATION NOMINATIVE--
--FIN MODIF NICOLAS
