//package br.com.ota.cpjbackend.repository.impl;
//
//import br.com.ota.cpjbackend.model.Role;
//import br.com.ota.cpjbackend.model.enums.RoleName;
//import br.com.ota.cpjbackend.repository.RoleRepository;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//public class RoleRepositoryImpl extends BaseRepositoryImpl<Role> implements RoleRepository {
//
//
//    public RoleRepositoryImpl(JpaEntityInformation<Role, ?> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//    }
//
//    @Override
//    public Role findByName(RoleName roleName) {
//        return null;
//    }
//
//    @Override
//    public List<Role> findManyByNameIn(List<RoleName> rolesName) {
//        return null;
//    }
//
//}
