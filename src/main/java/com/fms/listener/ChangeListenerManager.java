package com.fms.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.util.BeanUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeComparator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
class ChangeListenerManager implements Cloneable {
    private static final ChangeListenerManager changeListenerManager = new ChangeListenerManager();
    private static final Map<String, List<ChangeListenerType>> map = new HashMap<>();
    private static final Map<String, PropertyChangeListener> listenerMap = new HashMap<>();

    private static String filePath;

    private ChangeListenerManager() {
    }

    public static ChangeListenerManager getInstance(String path) {
        filePath = path;
        return changeListenerManager;
    }

    boolean isConfigured(String className) {
        return map.containsKey(className);
    }

    void processListener(Object entity, String[] propertyName, int[] dirtyProperties, Object[] oldState, boolean update,String operation) {
        configuredListener(filePath);

        if (isConfigured(entity.getClass().getName())) {
            map.get(entity.getClass().getName()).forEach(clt ->
            {
                Object previousValue = update ? getPreviousValueIndex(propertyName, dirtyProperties, oldState, clt.getProperty()) : null;
                Object newValue = getValue(entity, clt.getProperty());
                if (previousValue != null && newValue != null && isChange(previousValue, newValue)) {
                    clt.setOperation(operation);
                    listenerMap.get(clt.getListener()).postChange(entity, previousValue, newValue, update, clt);
                }
                else if(!update && !clt.isChangedOnly() && operation.equalsIgnoreCase("insert")){
                    clt.setOperation(operation);
                    listenerMap.get(clt.getListener()).postChange(entity, previousValue, newValue, update, clt);
                }
                else if(!update && clt.isChangedOnly() && operation.equalsIgnoreCase("delete") && !clt.getProperty().equalsIgnoreCase("userRole")){
                    clt.setOperation(operation);
                    listenerMap.get(clt.getListener()).postChange(entity, previousValue, newValue, update, clt);
                }

            });
        }
    }


    private Object getValue(Object entity, String fieldName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
            return pd.getReadMethod().invoke(entity);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Introspection exception occurred for method " + fieldName);
        }
        return null;
    }

    private Object getOldValue(String[] propertyName, Object[] oldState, String fieldName) {
        if (oldState != null && oldState.length > 0) {
            for (int index = 0; index < oldState.length; index++) {
                if (fieldName.equalsIgnoreCase(propertyName[index])) {
                    return oldState[index];
                }
            }
        }
        return null;
    }

    private Object getPreviousValueIndex(String[] propertyName, int[] dirtyProperties, Object[] oldState, String fieldName) {
        if (dirtyProperties != null && dirtyProperties.length > 0) {
            for (int index = 0; index < dirtyProperties.length; index++) {
                if (fieldName.equalsIgnoreCase(propertyName[dirtyProperties[index]])) {
                    return oldState[dirtyProperties[index]];
                }
            }
        }
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return changeListenerManager;
    }

    protected boolean isChange(Object oldValue, Object newValue) {
        if (ObjectUtils.isEmpty(oldValue) && !ObjectUtils.isEmpty(newValue)) {
            return true;
        } else if (!ObjectUtils.isEmpty(oldValue) && ObjectUtils.isEmpty(newValue)) {
            return true;
        } else if (!ObjectUtils.isEmpty(oldValue) && !ObjectUtils.isEmpty(newValue)) {
            if (oldValue instanceof Date) {
                Date oldDateValue = (Date) oldValue;
                Date newDateValue = (Date) newValue;
                return DateTimeComparator.getDateOnlyInstance().compare(oldDateValue, newDateValue) != 0
                        || DateTimeComparator.getTimeOnlyInstance().compare(oldDateValue, newDateValue) != 0;
            } else if (isEntity(oldValue) && isEntity(newValue)) {
                // Compare the entities by their IDs if they are both JPA entities
                Object oldId = getEntityId(oldValue);
                Object newId = getEntityId(newValue);
                return oldId != null && newId != null && !oldId.equals(newId);
            } else {
                return !oldValue.equals(newValue);
            }
        }
        return false;
    }

    // Helper method to check if an object is a JPA entity
    private boolean isEntity(Object object) {
        return object != null && object.getClass().isAnnotationPresent(Entity.class);
    }

    // Helper method to retrieve the ID of an entity using reflection
    private Object getEntityId(Object entity) {
        try {
            // Check fields for @Id annotation
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class) || field.getName().matches("(?i).*id$")) {
                    field.setAccessible(true);
                    return field.get(entity);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access ID field", e);
        }
        throw new RuntimeException("No identifiable ID field found for entity: " + entity.getClass().getName());
    }

    private static void configuredListener(String filePath) {
        if (!map.isEmpty())
            return;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BeanUtil.getListeners().forEach(lm -> listenerMap.put(getClassName(lm.getClass().getName()), lm));
                ChangeListenerMapper listenerMapper = new ObjectMapper().readValue(new File(filePath), ChangeListenerMapper.class);

                listenerMapper.getListener().forEach(
                        cl ->
                        {
                            if (!map.containsKey(cl.getEntityClass())) {
                                map.put(cl.getEntityClass(), cl.getFields());
                            } else
                                map.get(cl.getEntityClass()).addAll(cl.getFields());

                        }
                );

            }
        } catch (IOException e) {
            log.info("No listener file exits for this microservice....");
        }
    }


    private static String getClassName(String className) {
        if (StringUtils.hasText(className) && className.contains("$")) {
            return Arrays.asList(className.split("\\$")).iterator().next();
        } else
            return className;
    }
}
