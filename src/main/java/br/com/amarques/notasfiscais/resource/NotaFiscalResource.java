package br.com.amarques.notasfiscais.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;
import br.com.amarques.notasfiscais.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notas-fiscais")
public class NotaFiscalResource {

    private final NotaFiscalService service;

    @GetMapping
    public ResponseEntity<List<NotaFiscalDTO>> getAll() {
        log.debug("REST request to get all NotaFiscal");

        List<NotaFiscalDTO> notasFiscais = service.getAll();

        return new ResponseEntity<>(notasFiscais, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get an NotaFiscal [id: {0}]", id);

        NotaFiscalDTO notaFiscal = service.getById(id);

        return ResponseEntity.ok(notaFiscal);
    }

    @PostMapping
    public ResponseEntity<SimpleEntityDTO> create(@Valid @RequestBody CreateUpdateNotaFiscalDTO dto) {
        log.debug("REST request to create an new NotaFiscal: {}", dto);

        SimpleEntityDTO simpleEntityDTO = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(simpleEntityDTO);
    }

    @GetMapping("/empresas/{id}")
    public ResponseEntity<List<NotaFiscalDTO>> getAllByEmpresaId(@PathVariable Long id) {
        log.debug("REST request to get an NotaFiscal [id: {0}]", id);

        List<NotaFiscalDTO> notasFiscais = service.getAllByEmpresa(id);

        return ResponseEntity.ok(notasFiscais);
    }
}
