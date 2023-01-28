package com.rabindra.ndhandbook.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.rabindra.ndhandbook.entity.Product;
import com.rabindra.ndhandbook.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig, CorsRegistry cors) {
		HttpMethod [] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
//		
		ExposureConfiguration config = restConfig.getExposureConfiguration();
        config.forDomainType(Product.class)
        .withItemExposure((metadata, httpMethods) ->httpMethods.disable(theUnsupportedActions))
        .withCollectionExposure((metadata, httpMethods) ->httpMethods.disable(theUnsupportedActions));
//		
		//disable method for product repo:put , post and delete 
//		config.getExposureConfiguration()
//			.forDomainType(Product.class)
//			.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
//			.withCollectionExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions));
//		
//		//disable http methods for product category:put , post and delete 
//		config.getExposureConfiguration()
//		.forDomainType(ProductCategory.class)
//		.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
//		.withCollectionExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions));

        exposeIds(restConfig);
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		
		//get the list of all the entities from entitymanager
		
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		//get the list of entity types
		List<Class> entityClasses = new ArrayList<>();
		
		//get the entity types for each entity
		for(EntityType entityType :entities) {
			
			entityClasses.add(entityType.getJavaType());
		}
		
		//expose the ids for domain types or entity types 
		Class [] domainTypes = entityClasses.toArray(new Class[0]);
		
		config.exposeIdsFor(domainTypes);
		
	}
}
