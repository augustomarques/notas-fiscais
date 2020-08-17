package br.com.amarques.notasfiscais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;

@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {

    @Query("SELECT nf FROM NotaFiscal nf WHERE nf.tomador = :empresa OR nf.prestador = :empresa")
    List<NotaFiscal> findAllByEmpresa(@Param("empresa") Empresa empresa);
}
