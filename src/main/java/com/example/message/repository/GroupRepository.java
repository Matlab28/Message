package com.example.message.repository;

import com.example.message.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    boolean existsByNumber(String number);

    Optional<GroupEntity> findByUserName(String userName);

    Optional<GroupEntity> findByNumber(String number);
}
