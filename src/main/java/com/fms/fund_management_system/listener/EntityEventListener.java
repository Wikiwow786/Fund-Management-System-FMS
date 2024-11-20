package com.fms.fund_management_system.listener;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Waqar Khan
 * To change this template use File | Settings | File Templates.
 */

@Component
@RequiredArgsConstructor
public class EntityEventListener implements PostInsertEventListener, PostUpdateEventListener,PostCommitDeleteEventListener
{
    @Value("${spring.vyzor.listener.path:''}")
    private String filePath;

    private final EntityManagerFactory entityManagerFactory;
    @PostConstruct
    private void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
        registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);

    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent)
    {
        process(postInsertEvent.getEntity(), false,"INSERT");
    }


    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent)
    {
        process(postUpdateEvent.getEntity(), true, postUpdateEvent.getPersister().getPropertyNames(), postUpdateEvent.getDirtyProperties(), postUpdateEvent.getOldState(),"UPDATE");
    }

    private void process(Object entity, boolean update,String operation)
    {
       process(entity, update, null, null, null,operation);
    }

    private void process(Object entity, boolean update, String[] propertyName, int[] dirtyProperties, Object[] oldState,String operation)
    {
        ChangeListenerManager.getInstance(filePath).processListener(entity, propertyName, dirtyProperties, oldState,update,operation);
    }

    @Override
    public void onPostDeleteCommitFailed(PostDeleteEvent postDeleteEvent) {
        process(postDeleteEvent.getEntity(), false,"DELETE");
    }

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        process(postDeleteEvent.getEntity(), false,"DELETE");
    }
}
