package com.whsremote.api.repository;

import com.whsremote.api.entity.QuickLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickLinkRepository extends CrudRepository<QuickLink, String> {
    QuickLink findByUserId(String userId);
}
