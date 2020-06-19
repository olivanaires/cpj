//package br.com.ota.cpjbackend.repository.impl;
//
//import br.com.ota.cpjbackend.repository.BaseRepository;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//
//import javax.persistence.EntityManager;
//
//public class BaseRepositoryImpl<T> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {
//
//
//    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//    }
//
//}
