package br.com.amarques.notasfiscais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.amarques.notasfiscais.domain.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("SELECT e FROM Empresa e WHERE e.cnpj.cnpj = :cnpj")
    Optional<Empresa> findByCnpj(String cnpj);

    @Query("SELECT CASE WHEN COUNT(nf) > 0 THEN true ELSE false END "
            + "FROM NotaFiscal nf "
            + "WHERE nf.tomador = :empresa OR nf.prestador = :empresa")
    boolean canBeRemoved(@Param("empresa") Empresa empresa);
}
