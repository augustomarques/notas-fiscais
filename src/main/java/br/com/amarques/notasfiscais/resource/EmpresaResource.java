package br.com.amarques.notasfiscais.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import br.com.amarques.notasfiscais.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

    private final EmpresaService service;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAll() {
        log.debug("REST request to get all Empresa");

        List<EmpresaDTO> empresas = service.getAll();

        return new ResponseEntity<>(empresas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get an Empresa [id: {0}]", id);

        EmpresaDTO empresa = service.getById(id);

        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<SimpleEntityDTO> create(@Valid @RequestBody CreateUpdateEmpresaDTO dto) {
        log.debug("REST request to create an new Empresa: {}", dto);

        SimpleEntityDTO simpleEntityDTO = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CreateUpdateEmpresaDTO dto) {
        log.debug("REST request to update an Empresa [id: {0}] [dto: {1}]", id, dto);

        service.update(id, dto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete an Empresa [id: {0}]", id);

        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
