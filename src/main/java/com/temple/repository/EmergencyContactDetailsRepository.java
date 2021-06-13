package com.temple.repository;

import com.temple.domain.EmergencyContactDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmergencyContactDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergencyContactDetailsRepository extends JpaRepository<EmergencyContactDetails, Long> {}
