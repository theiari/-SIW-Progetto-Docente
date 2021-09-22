package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.TipologiaEsame;

public interface ProdottoRepository extends CrudRepository<TipologiaEsame, Long> {

	public List<TipologiaEsame> findByNome(String nome);
}