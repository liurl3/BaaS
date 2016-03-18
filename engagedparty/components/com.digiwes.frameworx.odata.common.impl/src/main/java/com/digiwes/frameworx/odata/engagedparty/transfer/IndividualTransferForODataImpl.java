package com.digiwes.frameworx.odata.engagedparty.transfer;

import com.digiwes.frameworx.common.api.IResourceTransfer;
import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.DefaultIndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;
import com.digiwes.frameworx.engagedparty.party.bean.OptionalIndividualName;
import com.digiwes.frameworx.odata.common.impl.transfer.TransferForODataImpl;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.olingo.commons.api.data.ComplexValue;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by nisx on 16-3-18.
 */
@Component(name = "IndividualTransfer",immediate = true)
@Service(value = {IResourceTransfer.class})
public class IndividualTransferForODataImpl extends TransferForODataImpl {

    @Override
    public <T> T transIn(Entity inParam, Class<T> tClass) throws Exception {
        T innerBean = (T)tClass.newInstance();
        List<Property> properties = inParam.getProperties();
        for( Property property : properties){
            if(null != property){
                String propertyName = property.getName();
                Object propertyValue = property.getValue();
                PropertyDescriptor descriptor = new PropertyDescriptor(propertyName,tClass);
                if("validFor".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    TimePeriod obj = createBeanProperty(complexValue, new TimePeriod());
                    descriptor.getWriteMethod().invoke(innerBean,obj);
                }else if("_languageAbilitys".equals(propertyName)){
                    List<ComplexValue> complexValue = (List<ComplexValue>)propertyValue;
                    Set<LanguageAbility> languageAbilities = createBeanCollection(LanguageAbility.class,complexValue);
                    descriptor.getWriteMethod().invoke(innerBean, languageAbilities);
                }else if("_defaultIndividualName".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    DefaultIndividualName obj = createBeanProperty(complexValue, new DefaultIndividualName());
                    descriptor.getWriteMethod().invoke(innerBean, obj);
                }else if("_optionalIndividualName".equals(propertyName)){
                    List<ComplexValue> complexValue = (List<ComplexValue>)propertyValue;
                    Set<OptionalIndividualName> optionalIndividualNames = createBeanCollection(OptionalIndividualName.class,complexValue);
                    descriptor.getWriteMethod().invoke(innerBean, optionalIndividualNames);
                }else if("aliveDuring".equals(propertyName)){
                    ComplexValue complexValue = (ComplexValue)propertyValue;
                    TimePeriod obj = createBeanProperty(complexValue, new TimePeriod());
                    descriptor.getWriteMethod().invoke(innerBean,obj);
                }else{
                    if (propertyValue instanceof String) {
                        descriptor.getWriteMethod().invoke(innerBean, (String) propertyValue);
                    } else if(propertyValue instanceof Date){
                        descriptor.getWriteMethod().invoke(innerBean, (Date) propertyValue);
                    }
                }
            }
        }
        return innerBean;
    }
}
