package com.pms.repository;

import com.pms.modal.SubScription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubScriptionRepository extends JpaRepository<SubScription,Long> {
    SubScription findByUserId(Long userId);

}
