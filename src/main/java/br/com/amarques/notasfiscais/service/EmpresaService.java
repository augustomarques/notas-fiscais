package br.com.amarques.notasfiscais.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.amarques.notasfiscais.domain.Empresa;
import br.com.amarques.notasfiscais.dto.EmpresaDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateEmpresaDTO;
import br.com.amarques.notasfiscais.exception.DeleteEmpresaException;
import br.com.amarques.notasfiscais.exception.EntityAlreadyRegisteredException;
import br.com.amarques.notasfiscais.exception.NotFoundException;
import br.com.amarques.notasfiscais.mapper.EmpresaMapper;
import br.com.amarques.notasfiscais.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;

    public List<EmpresaDTO> getAll() {
        List<Empresa> empresas = repository.findAll();

        if (CollectionUtils.isEmpty(empresas)) {
            return Collections.emptyList();
        }

        return empresas.stream().map(EmpresaMapper::toDTO).collect(Collectors.toList());
    }

    public EmpresaDTO getById(Long id) {
        Empresa empresa = findById(id);
        return EmpresaMapper.toDTO(empresa);
    }

    @Transactional
    public SimpleEntityDTO create(CreateUpdateEmpresaDTO dto) {
        Optional<Empresa> empresaCadastrada = repository.findByCnpj(dto.cnpj);

        if (!empresaCadastrada.isEmpty()) {
            throw new EntityAlreadyRegisteredException(MessageFormat.format(
                    "There is already an Empresa registered with CNPJ [{0}]", dto.cnpj));
        }

        Empresa empresa = EmpresaMapper.toEntity(dto);
        repository.save(empresa);

        return new SimpleEntityDTO(empresa.getId());
    }

    @Transactional
    public void update(Long id, CreateUpdateEmpresaDTO dto) {
        Optional<Empresa> empresaCadastradaComCNPJ = repository.findByCnpj(dto.cnpj);

        if (!empresaCadastradaComCNPJ.isEmpty() && !empresaCadastradaComCNPJ.get().getId().equals(id)) {
            throw new EntityAlreadyRegisteredException(MessageFormat.format(
                    "There is already an Empresa registered with CNPJ [{0}]", dto.cnpj));
        }

        Empresa empresa = findById(id);
        empresa.setCnpj(dto.cnpj);
        empresa.setFantasia(dto.fantasia);
        empresa.setRazaoSocial(dto.razaoSocial);
        empresa.setTipo(dto.tipo);

        repository.save(empresa);
    }

    protected Empresa findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(
                "Empresa [id: {0}] not found", id)));
    }

    @Transactional
    public void delete(Long id) {
        Empresa empresa = findById(id);
        boolean canBeRemoved = repository.canBeRemoved(empresa);
        if (!canBeRemoved) {
            throw new DeleteEmpresaException("The Empresa has Notas Fiscais. It is not possible to delete");
        }

        repository.delete(empresa);
    }
}
