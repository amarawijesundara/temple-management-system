package com.temple.repository;

import com.temple.domain.PersonalDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonalDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {}
