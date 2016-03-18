package com.digiwes.frameworx.odata.common.impl.transfer;

import com.digiwes.frameworx.common.api.IResourceTransfer;
import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.DefaultIndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
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
 * Created by nisx on 16-3-18.
 */
public abstract class TransferForODataImpl implements IResourceTransfer<Entity> {

    protected <T>Set<T> createBeanCollection(Class<T> tClass, List<ComplexValue> complexValues) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Set<T> outDatas = new HashSet();
        Iterator it = complexValues.iterator();

        while(it.hasNext()){
            Object obj = it.next();
            ComplexValue complexValue = (ComplexValue)obj;
            try {
                T newBean = (T)tClass.newInstance();
                outDatas.add(createBeanProperty(complexValue, newBean));
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return outDatas;
    }

    protected <T> T createBeanProperty(ComplexValue complexValue,T bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
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
    public <T> Entity transOut(T resource) throws Exception {
        if (null == resource) {
            return null;
        }
        Class type = resource.getClass();
        Entity entity = new Entity();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Property property = null;
            System.out.println(descriptor.getPropertyType());
            if(!"class".equals(propertyName)){
                Object value = getPropertyValue(resource, propertyName);
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
                }else if (value != null ){
                    property = createComplexProperty(propertyName, value);
                }
            }
            entity.addProperty(property);
        }
        return entity;
    }
    protected Property createComplexCollection(String propertyName, Set value) throws IntrospectionException {
        List outDatas = new ArrayList();
        Iterator it = value.iterator();

        while(it.hasNext()){
            Object obj = it.next();
            ComplexValue propertyTemp = createComplexValue(obj);
            outDatas.add(propertyTemp);
        }
        return new Property(null,propertyName,ValueType.COLLECTION_COMPLEX, outDatas);
    }

    protected Property createComplexProperty(String name, Object bean) throws IntrospectionException {
        ComplexValue complexValue = createComplexValue(bean);
        return  new Property(null,name, ValueType.COMPLEX, complexValue);
    }

    protected ComplexValue createComplexValue(Object bean) throws IntrospectionException {
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
        return complexValue;
    }

    protected Property createPrimitive(final String name, final Object value) {
        return new Property(null, name, ValueType.PRIMITIVE, value);
    }
    protected Object getPropertyValue(Object bean, String propertyName) {
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
