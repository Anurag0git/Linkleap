package com.linkleap.api.repository;

import com.linkleap.api.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // this tells spring that this is a bean for db operations
public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {

//    we will leave this empty for now. why?
//    because we extended JpaRepository<UrlMapping, Long>
//    Spring Data JPA reads that and says,
//    okay u need a repo for url-mapping entity,
//    whose id is long. I will automatically generate all standard code to
//    c,r,u,d these entities

//   JPARepository gives us methods like .save(), .findById(), .findAll() for free
//    we can add custom methods later, if we need


    Optional<UrlMapping> findByShortKey(String shortKey);
}
