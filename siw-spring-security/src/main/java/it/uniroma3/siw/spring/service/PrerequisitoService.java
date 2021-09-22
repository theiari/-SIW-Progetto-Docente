package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.repository.PrerequisitoRepository;
import it.uniroma3.siw.spring.repository.ProdottoRepository;

@Service
public class PrerequisitoService {
	
	@Autowired
	private PrerequisitoRepository prerequisitoRepository; 
	
	@Transactional
	public Prerequisito inserisci(Prerequisito prerequisito) {
		return prerequisitoRepository.save(prerequisito);
	}

	@Transactional
	public List<Prerequisito> tutti() {
		return (List<Prerequisito>) prerequisitoRepository.findAll();
	}

	@Transactional
	public Prerequisito prerequisitoPerId(Long id) {
		Optional<Prerequisito> optional = prerequisitoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Prerequisito prerequisito) {
		List<Prerequisito> prerequisiti = this.prerequisitoRepository.findByDescrizione(prerequisito.getDescrizione());
		if (prerequisiti.size() > 0)
			return true;
		else 
			return false;
	}
}
