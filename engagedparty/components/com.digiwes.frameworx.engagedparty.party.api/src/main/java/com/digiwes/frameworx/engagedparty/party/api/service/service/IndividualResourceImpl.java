package com.digiwes.frameworx.engagedparty.party.api.service.service;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.api.service.interfaces.IndividualFactory;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.olingo.commons.api.data.ComplexValue;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by liurl3 on 2016/3/9.
 */
@Component(name = "IndividualResource" , immediate = true)
@Service(value = IndividualFactory.class)
public class IndividualResourceImpl implements IndividualFactory {
    @Override
    public Individual convertEntity(Entity entity) {
        List<Property> properties = entity.getProperties();
        for(int i = 0 ;i < properties.size() ; i++){

        }
        return null;
    }
    private PropertyDescriptor[] getPropertyDescriptor(Individual bean) throws IntrospectionException {
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        return propertyDescriptors;
    }
    @Override
    public Entity convertBean(Individual bean) throws IntrospectionException {
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Object value = getPropertyValue(bean, propertyName);
            Property property = null;
            /*if (value instanceof TimePeriod) {
                property = createComplexProperty(propertyName, value);
            } else if (value instanceof String) {
                property = createPrimitive(propertyName,value);
            } else if (value instanceof IndividualName) {
                property = createComplexProperty(propertyName, value);
            } else if (value instanceof List) {
                //property =
            }*/
            if (value instanceof String) {
                property = createPrimitive(propertyName,value);
            } else if (value instanceof Integer) {
                property = createPrimitive(propertyName, value);
            }else if (value instanceof Boolean){
                property = createPrimitive(propertyName,value);
            }else if(value instanceof Float){
                property = createPrimitive(propertyName,value);
            }else if(value instanceof Double){
                property = createPrimitive(propertyName,value);
            }else if(value instanceof Long){
                property = createPrimitive(propertyName,value);
            }else{
                property = createComplexProperty(propertyName, value);
            }
            entity.addProperty(property);
        }
        return entity;
    }
    private Property createComplexProperty(String name, Object bean) throws IntrospectionException {
        ComplexValue complexValue=new ComplexValue();
        List<Property> addressProperties = complexValue.getValue();
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Object value = getPropertyValue(bean, propertyName);
            complexValue.getValue().add(createPrimitive(propertyName, value));
        }
        return  new Property(null,name, ValueType.COMPLEX, complexValue);
    }
    private Property createPrimitive(final String name, final Object value) {
        return new Property(null, name, ValueType.PRIMITIVE, value);
    }
    private Object getPropertyValue(Object bean, String propertyName) {
        String firstLetter = propertyName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + propertyName.substring(1);

        try {
            Method method = bean.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(bean, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
