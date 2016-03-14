package com.digiwes.tryout.odata.service;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.DefaultIndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
import com.digiwes.tryout.odata.interfaces.IIndividualFactory;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Created by liurl3 on 2016/3/9.
 */
@Component(name = "IndividualFactory" , immediate = true)
@Service(value = IIndividualFactory.class)
public class IndividualFactoryImpl implements IIndividualFactory {
    @Override
    public Individual convertEntity(Entity entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Individual individual = new Individual();
        List<Property> properties = entity.getProperties();
        for( Property property : properties){
            if(null != property){
                String propertyName = property.getName();
                Object propertyValue = property.getValue();
                PropertyDescriptor descriptor = new PropertyDescriptor(propertyName,individual.getClass());
                if("validFor".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    TimePeriod obj = createBeanProperty(complexValue, new TimePeriod());
                    descriptor.getWriteMethod().invoke(individual,obj);
                }else if("_languageAbilitys".equals(propertyName)){
                    Set<ComplexValue> complexValue = (Set<ComplexValue>)propertyValue;
                    Set<LanguageAbility> languageAbilities = createBeanCollection(complexValue);
                    descriptor.getWriteMethod().invoke(individual, languageAbilities);
                }else if("_defaultIndividualName".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    DefaultIndividualName obj = createBeanProperty(complexValue, new DefaultIndividualName());
                    descriptor.getWriteMethod().invoke(individual, obj);
                }else if("_optionalIndividualName".equals(propertyName)){
                    Set<ComplexValue> complexValue = (Set<ComplexValue>)propertyValue;
                    Set<OptionalIndividualName> optionalIndividualNames = createBeanCollection(complexValue);
                    descriptor.getWriteMethod().invoke(individual, optionalIndividualNames);
                }else if("aliveDuring".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    TimePeriod obj = createBeanProperty(complexValue, new TimePeriod());
                    descriptor.getWriteMethod().invoke(individual,obj);
                }else{
                    if (propertyValue instanceof String) {
                        descriptor.getWriteMethod().invoke(individual, (String) propertyValue);
                    } else if(propertyValue instanceof Date){
                        descriptor.getWriteMethod().invoke(individual, (Date) propertyValue);
                    }
                }
            }
        }
        return individual;
    }
    private <T>Set<T> createBeanCollection(Set<ComplexValue> complexValues) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Set<T> outDatas = new HashSet();
        Iterator it = complexValues.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            Property property = (Property)obj;
            Object bean = null;
            if(null != property) {
                String propertyName = property.getName();
                if ("_languageAbilitys".equals(propertyName)) {
                    bean = new LanguageAbility();
                } else if ("_optionalIndividualName".equals(propertyName)) {
                    bean = new OptionalIndividualName();
                }
            }
            ComplexValue complexValue = (ComplexValue)property.getValue();
            outDatas.add((T) createBeanProperty(complexValue, bean));
        }
        return outDatas;
    }

    private <T> T createBeanProperty(ComplexValue complexValue,T bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if(null != complexValue){
            List<Property> properties = complexValue.getValue();
            for( Property property : properties) {
                if (null != property) {
                    String propertyName = property.getName();
                    Object propertyValue = property.getValue();
                    if(!"class".equals(propertyName)){
                        if(null != bean){
                            PropertyDescriptor descriptor = new PropertyDescriptor(propertyName,bean.getClass());
                            if (propertyValue instanceof String) {
                                descriptor.getWriteMethod().invoke(bean, (String) propertyValue);
                            } else if(propertyValue instanceof Date){
                                descriptor.getWriteMethod().invoke(bean, (Date) propertyValue);
                            }else if(propertyValue instanceof ComplexValue){
                                ComplexValue propertyComlex = (ComplexValue) propertyValue;
                                TimePeriod valid = createBeanProperty(propertyComlex, new TimePeriod());
                                descriptor.getWriteMethod().invoke(bean,valid);
                            }
                        }
                    }
                }
            }
        }
        return bean;
    }
    @Override
    public Entity convertBean(Individual bean) throws IntrospectionException {
        if (null == bean) {
            return null;
        }
        Class type = bean.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Property property = null;
            if(!"class".equals(propertyName)){
                Object value = getPropertyValue(bean, propertyName);
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
                }else if(value instanceof Set){
                    property = createComplexCollection(propertyName, (Set) value);
                }else{
                    property = createComplexProperty(propertyName, value);
                }
            }
            entity.addProperty(property);
        }
        return entity;
    }

    private Property createComplexCollection(String propertyName, Set value) throws IntrospectionException {
        Collection outDatas = new HashSet();
        Iterator it = value.iterator();

        while(it.hasNext()){
            Object obj = it.next();
            Property propertyTemp = createComplexProperty(propertyName, obj);
            outDatas.add(propertyTemp);
        }
        return new Property(null,propertyName,ValueType.COLLECTION_COMPLEX, outDatas);
    }

    private Property createComplexProperty(String name, Object bean) throws IntrospectionException {
        ComplexValue complexValue=new ComplexValue();
        if(null != bean){
            Class type = bean.getClass();
            Entity entity = new Entity();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if(!"class".equals(propertyName)){
                    Object value = getPropertyValue(bean, propertyName);
                    if(value instanceof TimePeriod){
                        complexValue.getValue().add(createComplexProperty(propertyName, value));
                    }else {
                        complexValue.getValue().add(createPrimitive(propertyName, value));
                    }
                }
            }
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
