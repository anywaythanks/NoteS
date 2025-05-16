package com.notes.repository.inner;

import com.notes.models.entity.NoteContent;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Внутренний репозиторий для работы с эластиком (В основном запись).
 */
@Profile("elastic")
public interface NoteRepositoryElastic extends ElasticsearchRepository<NoteContent, String> {
}
