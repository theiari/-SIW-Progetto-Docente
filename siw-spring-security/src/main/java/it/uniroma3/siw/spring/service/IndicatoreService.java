package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.repository.IndicatoreRepository;
import it.uniroma3.siw.spring.repository.ProdottoRepository;

@Service
public class IndicatoreService {
	
	@Autowired
	private IndicatoreRepository indicatoreRepository; 
	
	@Transactional
	public Indicatore inserisci(Indicatore indicatore) {
		return indicatoreRepository.save(indicatore);
	}

	@Transactional
	public List<Indicatore> tutti() {
		return (List<Indicatore>) indicatoreRepository.findAll();
	}

	@Transactional
	public Indicatore indicatorePerId(Long id) {
		Optional<Indicatore> optional = indicatoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Indicatore indicatore) {
		List<Indicatore> indicatori = this.indicatoreRepository.findByNome(indicatore.getNome());
		if (indicatori.size() > 0)
			return true;
		else 
			return false;
	}

	
}
