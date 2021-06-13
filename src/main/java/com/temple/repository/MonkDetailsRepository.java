package com.temple.repository;

import com.temple.domain.MonkDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MonkDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonkDetailsRepository extends JpaRepository<MonkDetails, Long> {}
