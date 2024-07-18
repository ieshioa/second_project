package com.green.glampick.repository;

import com.green.glampick.entity.GlampingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlampingRepository extends JpaRepository<GlampingEntity, Long> {

    GlampingEntity findByGlampId(long glampId);

}
