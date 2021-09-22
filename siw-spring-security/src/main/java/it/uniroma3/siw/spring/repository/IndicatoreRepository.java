package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;

public interface IndicatoreRepository extends CrudRepository<Indicatore, Long> {

	public List<Indicatore> findByNome(String descrizione);

	
}