package com.fst.back_etat_civil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fst.back_etat_civil.dto.CercleDto;
import com.fst.back_etat_civil.model.Cercle;
import com.fst.back_etat_civil.model.Region;
import com.fst.back_etat_civil.model.Vqf;
import com.fst.back_etat_civil.repository.CercleRepository;
import com.fst.back_etat_civil.repository.RegionRepository;
import com.fst.back_etat_civil.service.CercleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cercle")
public class CercleController {
    @Autowired
    private CercleService cercleService;
    @Autowired
    RegionRepository regionRepository;
   
    @Autowired
    private CercleRepository cercleRepository;

    @GetMapping("")
    public ResponseEntity<List<CercleDto>> getAllCercles() {
        List<CercleDto> cercles = cercleService.getAllCercles();
        return new ResponseEntity<>(cercles, HttpStatus.OK);
    }
    
    @GetMapping("/getCerclesByReg/{id}")
    public ResponseEntity<List<CercleDto>> getCerclesByReg(@PathVariable long id) {
        List<CercleDto> cercles = cercleService.getCerclesByReg(id);
        return new ResponseEntity<>(cercles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CercleDto> getCercleById(@PathVariable long id) {
        CercleDto cercle = cercleService.getCercleById(id);
        return new ResponseEntity<>(cercle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CercleDto> createCercle(@RequestBody CercleDto cercle) {
    	if (cercleRepository.existsByCode(cercle.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce code de cercle existe déjà");
        }
    	if (cercleRepository.existsByNomIgnoreCase(cercle.getNom())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce nom de cercle existe déjà");
        }else {
        try {
            Optional<Region> region=regionRepository.findById(cercle.getRegion());
            Cercle cercle1= new Cercle();
            //MAPPING
            cercle1.setAutre(cercle.getAutre());
            cercle1.setNom(cercle.getNom());
            cercle1.setCode(cercle.getCode());

            cercle1.setRegion(region.get());
            //END MAPPING
            if(!region.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"REGION NOT FOUND");

            Cercle _cercle = cercleRepository
                    .save(cercle1);
            cercle.setId(_cercle.getId());

            return new ResponseEntity<>(cercle, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CercleDto> updateCercle(@PathVariable long id, @RequestBody CercleDto cercleDto) {
    	if (cercleRepository.existsByCode(cercleDto.getCode()) 
        		&& !cercleRepository.findById(id).get().getCode().equals(cercleDto.getCode())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce code de cercle existe déjà");
            }
        	if (cercleRepository.existsByNomIgnoreCase(cercleDto.getNom())
        			&& !cercleRepository.findById(id).get().getNom().equals(cercleDto.getNom())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce nom de cercle existe déjà");
            }else {
        CercleDto updatedCercle = cercleService.updateCercle(id, cercleDto);
        return new ResponseEntity<>(updatedCercle, HttpStatus.OK);
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCercle(@PathVariable long id) {
        cercleService.deleteCercle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Cercle>> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<Cercle> cercles = cercleService.searchCercles(keyword, page, size);
        return ResponseEntity.ok(cercles);
    }
}
