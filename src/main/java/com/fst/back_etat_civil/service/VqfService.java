package com.fst.back_etat_civil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.fst.back_etat_civil.dto.VqfDto;
import com.fst.back_etat_civil.model.Commune;
import com.fst.back_etat_civil.model.Vqf;
import com.fst.back_etat_civil.repository.CommuneRepository;
import com.fst.back_etat_civil.repository.VqfRepository;

import lombok.Data;

@Data
@Service
public class VqfService {
    @Autowired
    private VqfRepository vqfRepository;
    
    @Autowired
    private CommuneRepository communeRepository;

    public List<VqfDto> getAllVqfs() {
        List<Vqf> vqfs = vqfRepository.findAll();
        return mapToDtoList(vqfs);
    }
    
    public List<VqfDto> getVqfsByCom(long id) {
    	Commune commune=communeRepository.findById(id).get();
        List<Vqf> vqfs = vqfRepository.findByCommune(commune);
        List<VqfDto> vqfDtos = new ArrayList<>();

        for (Vqf vqf : vqfs) {
            VqfDto vqfDto = new VqfDto();
            vqfDto.setId(vqf.getId());
            vqfDto.setAutre(vqf.getAutre());
            vqfDto.setNom(vqf.getNom());
            vqfDto.setCode(vqf.getCode());

            

            vqfDtos.add(vqfDto);
        }

        return vqfDtos;
    }

    public VqfDto getVqfById(long id) {
        Optional<Vqf> vqfOptional = vqfRepository.findById(id);
        if (vqfOptional.isPresent()) {
            Vqf vqf = vqfOptional.get();
            return mapToDto(vqf);
        } else {
            throw new NotFoundException("Vqf not found with id: " + id);
        }
    }

    public VqfDto createVqf(VqfDto vqfDto) {
        Vqf vqf = mapToModel(vqfDto);
        vqf.setCommune(communeRepository.findById(vqfDto.getCommune()).get());
        vqf = vqfRepository.save(vqf);
        return mapToDto(vqf);
    }

    public VqfDto updateVqf(long id, VqfDto vqfDto) {
        Optional<Vqf> vqfOptional = vqfRepository.findById(id);
        if (vqfOptional.isPresent()) {
            Vqf vqf = vqfOptional.get();
            vqf.setNom(vqfDto.getNom());
            vqf.setCode(vqfDto.getCode());
            vqf.setAutre(vqfDto.getAutre());
            vqf = vqfRepository.save(vqf);
            return mapToDto(vqf);
        } else {
            throw new NotFoundException("Vqf not found with id: " + id);
        }
    }

    public void deleteVqf(long id) {
        vqfRepository.deleteById(id);
    }

    // Méthodes utilitaires de mappage entre les entités et les DTO

    private VqfDto mapToDto(Vqf vqf) {
        VqfDto vqfDto = new VqfDto();
        vqfDto.setId(vqf.getId());
        vqfDto.setNom(vqf.getNom());
        vqfDto.setCode(vqf.getCode());
        vqfDto.setAutre(vqf.getAutre());
        return vqfDto;
    }

    private List<VqfDto> mapToDtoList(List<Vqf> vqfs) {
        return vqfs.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Vqf mapToModel(VqfDto vqfDto) {
        Vqf vqf = new Vqf();
        vqf.setNom(vqfDto.getNom());
        vqf.setCode(vqfDto.getCode());
        vqf.setAutre(vqfDto.getAutre());
        return vqf;
    }
}
