package fr.legrain.data;




public interface IGenCode {

	public String genereCodeJPA(String section, int rajoutCompteur)
			throws Exception;

//	public String genereCodeExJPA(String section,  String exo)
//			throws Exception;
//	
//	public String genereCodeExJPA(TaGenCodeEx genCodeEx, String section,String exo) 
//			throws Exception;
	
	public String genereCodeJPA(String section, int rajoutCompteur, String exo)
			throws Exception;

	public String genereCode(String section, int rajoutCompteur, String exo)
			throws Exception;

	public String genereCode(String section, int rajoutCompteur)
			throws Exception;

	public void setListeGestCode(String fileName) throws Exception;

	public void reinitialiseListeGestCode();

	public String getCodeFixe();

	public void setCodeFixe(String codeFixe);

	public Integer getCompteur();

	public void setCompteur(Integer compteur);

}