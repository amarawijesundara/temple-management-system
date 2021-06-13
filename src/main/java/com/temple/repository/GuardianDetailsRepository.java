package com.temple.repository;

import com.temple.domain.GuardianDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GuardianDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuardianDetailsRepository extends JpaRepository<GuardianDetails, Long> {}
