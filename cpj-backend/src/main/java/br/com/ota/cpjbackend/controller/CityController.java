package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.model.City;
import br.com.ota.cpjbackend.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/city")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        City city = cityRepository.findOne(Example.of(new City(name))).orElse(null);
        return ResponseEntity.ok(city);
    }

}
