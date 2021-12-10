package com.cingo.logstore.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LogStoreEntityManagerFactory {

	private static EntityManagerFactory instance;
	
	public static EntityManagerFactory getInstance(){
		if (instance == null){
			instance = Persistence.createEntityManagerFactory("logstore");
		}
		return instance;
		//return null;
	}

}
