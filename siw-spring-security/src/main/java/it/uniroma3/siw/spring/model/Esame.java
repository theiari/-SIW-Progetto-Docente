package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
public class Esame {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String prenotato;
	@Column
	private String sostenuto;

	@Column
    private ArrayList<String> risu;
	
	
	//@Column
	//private Map<Indicatore, String> risultati;
	@Column
	@ElementCollection(targetClass=String.class)
	@MapKeyColumn(name="risultati")
	private Map<Indicatore,String> risultati;
	
	public Long getId() {
		return id;
	}
	
	
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public Map<Indicatore, String> getRisultati() {
		return risultati;
	}

	public void setRisultati(Map<Indicatore, String> risultati) {
		this.risultati = risultati;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public TipologiaEsame getTipologiaesami() {
		return tipologiaesami;
	}

	public void setTipologiaesami(TipologiaEsame tipologiaesami) {
		this.tipologiaesami = tipologiaesami;
	}

	public String getPrenotato() {
		return prenotato;
	}

	public void setPrenotato(String prenotato) {
		this.prenotato = prenotato;
	}

	public String getSostenuto() {
		return sostenuto;
	}

	public void setSostenuto(String sostenuto) {
		this.sostenuto = sostenuto;
	}
	
	


	public ArrayList<String> getRisu() {
		return risu;
	}



	public void setRisu(ArrayList<String> risu) {
		this.risu = risu;
	}




	@ManyToOne
	private User user ;
	
	@ManyToOne
	private Medico medico ;
	
	@ManyToOne
	private TipologiaEsame tipologiaesami ;
	
}
