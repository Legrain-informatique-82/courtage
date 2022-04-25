--suppréssion de l'activités 48 et de ses liaisons (traitement curatif insectes)
delete from ta_dossier_rcd_activite where ta_dossier_rcd_activite.activite = '48';
delete from ta_activite where ta_activite.code_activite = '48';



--reprise passé 
update ta_taux_assurance set 
taux_taux_assurance = 20.00
where code_taux_assurance = 'Rdp';

--taxe 9%
insert into ta_taux_assurance(taux_taux_assurance, code_taux_assurance, libelle_taux_assurance)
values (9.00, 'Tax', 'Taxes');