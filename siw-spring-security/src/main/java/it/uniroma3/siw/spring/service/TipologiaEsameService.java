package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.model.Medico;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.repository.IndicatoreRepository;
import it.uniroma3.siw.spring.repository.MedicoRepository;
import it.uniroma3.siw.spring.repository.ProdottoRepository;
import it.uniroma3.siw.spring.repository.TipologiaEsameRepository;

@Service
public class TipologiaEsameService {
	
	@Autowired
	private TipologiaEsameRepository tipologiaesameRepository; 
	
	@Transactional
	public TipologiaEsame inserisci(TipologiaEsame tipologiaesame) {
		return tipologiaesameRepository.save(tipologiaesame);
	}

	@Transactional
	public List<TipologiaEsame> tutti() {
		return (List<TipologiaEsame>) tipologiaesameRepository.findAll();
	}

	@Transactional
	public TipologiaEsame tipologiaesamiPerId(Long id) {
		Optional<TipologiaEsame> optional = tipologiaesameRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(TipologiaEsame tipologiaesame) {
		List<TipologiaEsame> medici = this.tipologiaesameRepository.findByNome(tipologiaesame.getNome());
		if (medici.size() > 0)
			return true;
		else 
			return false;
	}


	
}
