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

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository medicoRepository; 
	
	@Transactional
	public Medico inserisci(Medico medico) {
		return medicoRepository.save(medico);
	}

	@Transactional
	public List<Medico> tutti() {
		return (List<Medico>) medicoRepository.findAll();
	}

	@Transactional
	public Medico medicoPerId(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Medico medico) {
		List<Medico> medici = this.medicoRepository.findByNome(medico.getNome());
		if (medici.size() > 0)
			return true;
		else 
			return false;
	}

	
}
