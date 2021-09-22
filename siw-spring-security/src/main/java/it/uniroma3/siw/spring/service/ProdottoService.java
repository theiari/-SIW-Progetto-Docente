package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.repository.ProdottoRepository;

@Service
public class ProdottoService {
	
	@Autowired
	private ProdottoRepository prodottoRepository; 
	
	@Transactional
	public TipologiaEsame inserisci(TipologiaEsame prodotto) {
		return prodottoRepository.save(prodotto);
	}

	@Transactional
	public List<TipologiaEsame> tutti() {
		return (List<TipologiaEsame>) prodottoRepository.findAll();
	}

	@Transactional
	public TipologiaEsame prodottoPerId(Long id) {
		Optional<TipologiaEsame> optional = prodottoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(TipologiaEsame prodotto) {
		List<TipologiaEsame> prodotti = this.prodottoRepository.findByNome(prodotto.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
}
