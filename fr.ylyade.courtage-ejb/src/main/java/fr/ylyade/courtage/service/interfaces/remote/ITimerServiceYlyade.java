package fr.ylyade.courtage.service.interfaces.remote;


import javax.ejb.EJBException;



public interface ITimerServiceYlyade {

	public void actEnvoiMailContratTermeDepasse1Semaine();
	public void actEnvoiMailPourPiecesInavlides();
	public void actEnvoiMailCourtierInactif();

}