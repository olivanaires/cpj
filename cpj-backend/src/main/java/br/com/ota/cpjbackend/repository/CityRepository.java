package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
