package com.notes.repository.inner;

import com.notes.models.entity.Entry;

import java.util.List;
import java.util.UUID;


interface EntryUuidsRepositoryDb {
   List<Entry> findByElasticUuids(List<UUID> uuids);
}