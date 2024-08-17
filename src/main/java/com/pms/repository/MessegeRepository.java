package com.pms.repository;

import com.pms.modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessegeRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
