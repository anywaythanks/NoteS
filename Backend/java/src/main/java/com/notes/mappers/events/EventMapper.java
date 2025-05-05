package com.notes.mappers.events;

import com.notes.configs.MapstructConfig;
import com.notes.events.AckEvent;
import com.notes.events.CompensateEvent;
import com.notes.events.FailedSync;
import com.notes.events.ScheduleEvent;
import com.notes.events.SimpleAcknowledge;
import com.notes.events.SuccessSync;
import com.notes.events.SyncCreateEvent;
import com.notes.events.SyncDeleteEvent;
import com.notes.events.SyncEditEvent;
import com.notes.models.entity.SagaEvent;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface EventMapper {
   SyncCreateEvent ofCreate(ScheduleEvent event, SimpleAcknowledge acknowledgment);

   SyncEditEvent ofEdit(ScheduleEvent event, SimpleAcknowledge acknowledgment);

   SyncDeleteEvent ofDelete(ScheduleEvent event, SimpleAcknowledge acknowledgment);

   CompensateEvent ofCompensate(ScheduleEvent event, SimpleAcknowledge acknowledgment);

   SuccessSync ofSuccess(AckEvent event, SagaEvent eventType);

   FailedSync ofFailed(AckEvent event, SagaEvent eventType);
}
