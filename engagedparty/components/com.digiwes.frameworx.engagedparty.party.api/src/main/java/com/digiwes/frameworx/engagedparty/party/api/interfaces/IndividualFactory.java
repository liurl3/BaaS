package com.digiwes.frameworx.engagedparty.party.api.interfaces;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import org.apache.olingo.commons.api.data.Entity;

import java.beans.IntrospectionException;

public interface IndividualFactory{
	
	public Individual convertEntity(Entity entity);
	
	public Entity convertBean(Individual individual) throws IntrospectionException;
	
}