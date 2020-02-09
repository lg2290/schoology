package com.schoology.test.autocomplete.dataprovider.repository;

import com.schoology.test.autocomplete.dataprovider.entity.UserDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDbEntity, Long> {
}
