package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Esame;
import it.uniroma3.siw.spring.repository.EsameRepository;



@Service
public class EsameService {
	
	@Autowired
	private EsameRepository esameRepository; 
	
	@Transactional
	public Esame inserisci(Esame esame) {
		return esameRepository.save(esame);
	}

	@Transactional
	public List<Esame> tutti() {
		return (List<Esame>) esameRepository.findAll();
	}

	@Transactional
	public Esame esamePerId(Long id) {
		Optional<Esame> optional = esameRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Esame esame) {
		List<Esame> esami = this.esameRepository.findByPrenotato(esame.getPrenotato());
		if (esami.size() > 0)
			return true; //e' possibile che due o piu esami coincidano con la stessa ora, pertanto la verifica non viene effettuata
	else 
			return false;
	}
}
