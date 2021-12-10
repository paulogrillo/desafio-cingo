package com.cingo.logstore.repostory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.cingo.logstore.entity.Log;

public class LogRepository extends Repository {

	public List<Log> findAllOrdened() {
		Query query = this.getManager().createQuery("SELECT e FROM Log e ORDER BY occurrences desc");
		return query.getResultList();
		//return new ArrayList<Log>();
	}
	
	public void addLog(Log log) {
		if (this.alreadyExists(log.getContent())) {
			Log firstLogFound = this.getLogs(log.getContent()).get(0);
			firstLogFound.newOcurrence(log.getOccurrences());
			this.update(firstLogFound);
		} else {
			super.add(log);
		}
	}
	
	private boolean alreadyExists(String content){
	    return !this.getLogs(content).isEmpty();
	}
	
	private List<Log> getLogs(String content){
	    Query query = this.getManager().createQuery("SELECT e FROM Log e WHERE content = :content");
	    query.setParameter("content", content);
	    return query.getResultList();
		//return new ArrayList<Log>();
	}
	
	public void updateLog(Log log) {
		this.update(log);
	}
	
	public void removeLog(Log log){
		this.delete(log);
	    
	}
	
	public Log find(int id){
	    Query query = this.getManager().createQuery("SELECT e FROM Log e WHERE id = :id");
	    query.setParameter("id", id);
	    
	    return (Log) query.getResultList().get(0);
	
	}
	
	
}
