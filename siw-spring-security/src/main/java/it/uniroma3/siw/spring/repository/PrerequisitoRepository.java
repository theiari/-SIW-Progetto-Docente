package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;

public interface PrerequisitoRepository extends CrudRepository<Prerequisito, Long> {

	public List<Prerequisito> findByDescrizione(String descrizione);
}