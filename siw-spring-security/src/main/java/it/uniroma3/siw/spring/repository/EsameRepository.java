package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Esame;

public interface EsameRepository extends CrudRepository<Esame, Long> {

	public List<Esame> findByPrenotato(String prenotato);
}