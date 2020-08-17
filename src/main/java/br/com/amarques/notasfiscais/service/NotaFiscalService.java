package br.com.amarques.notasfiscais.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.domain.NotaFiscal;
import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;
import br.com.amarques.notasfiscais.enums.TipoEmpresaEnum;
import br.com.amarques.notasfiscais.exception.InvalidNotaFiscalEmpresaTipoException;
import br.com.amarques.notasfiscais.exception.NotFoundException;
import br.com.amarques.notasfiscais.mapper.NotaFiscalMapper;
import br.com.amarques.notasfiscais.repository.NotaFiscalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaFiscalService {

    private final NotaFiscalRepository repository;
    private final EmpresaService empresaService;

    public NotaFiscalDTO getById(Long id) {
        NotaFiscal notaFiscal = findById(id);
        return NotaFiscalMapper.toDTO(notaFiscal);
    }

    private NotaFiscal findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(
                "NotaFiscal [id: {0}] not found", id)));
    }

    public List<NotaFiscalDTO> getAll() {
        List<NotaFiscal> notasFiscais = repository.findAll();
        return toDTOList(notasFiscais);
    }

    private List<NotaFiscalDTO> toDTOList(List<NotaFiscal> notasFiscais) {
        if (CollectionUtils.isEmpty(notasFiscais)) {
            return List.of();
        }
        return notasFiscais.stream().map(NotaFiscalMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public SimpleEntityDTO create(CreateUpdateNotaFiscalDTO dto) {
        if (dto.prestadorId.equals(dto.tomadorId)) {
            throw new InvalidNotaFiscalEmpresaTipoException(
                    "The [PRESTADORA] and the [TOMADORA] cannot be the same");
        }
        Empresa prestador = getEmpresaAndValidateTipo(dto.prestadorId, TipoEmpresaEnum.PRESTADOR);
        Empresa tomador = getEmpresaAndValidateTipo(dto.tomadorId, TipoEmpresaEnum.TOMADOR);

        NotaFiscal notaFiscal = NotaFiscalMapper.toEntity(dto, tomador, prestador);

        repository.save(notaFiscal);

        return new SimpleEntityDTO(notaFiscal.getId());
    }

    private Empresa getEmpresaAndValidateTipo(Long id, TipoEmpresaEnum tipo) {
        Empresa empresa = empresaService.findById(id);
        if (!tipo.equals(empresa.getTipo())) {
            throw new InvalidNotaFiscalEmpresaTipoException(MessageFormat.format(
                    "The Empresa [id: {0}] should be [{1}] but is [{2}]", id, tipo.name(), empresa.getTipo().name()));
        }

        return empresa;
    }

    public List<NotaFiscalDTO> getAllByEmpresa(Long idEmpresa) {
        Empresa empresa = empresaService.findById(idEmpresa);
        List<NotaFiscal> notasFiscais = repository.findAllByEmpresa(empresa);
        return toDTOList(notasFiscais);
    }
}
