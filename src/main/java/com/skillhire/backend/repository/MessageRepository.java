package com.skillhire.backend.repository;

import com.skillhire.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
           select m from Message m
           where (m.sender.id = :userA and m.receiver.id = :userB)
              or (m.sender.id = :userB and m.receiver.id = :userA)
           order by m.sentAt asc
           """)
    List<Message> findConversation(@Param("userA") Long userA, @Param("userB") Long userB);
}
