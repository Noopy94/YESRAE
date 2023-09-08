package com.ssafy.yesrae.domain.cup.repository;

import com.ssafy.yesrae.domain.cup.entity.CupResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupResultRepository extends JpaRepository<CupResult, Long> {

}
