package com.cingo.logstore.entity.factory.impl;

import com.cingo.logstore.entity.Log;
import com.cingo.logstore.entity.factory.LogFactory;

public class DefaultLogFactory implements LogFactory {

	@Override
	public Log build(String content, int occurrences) {
		return new Log(content, occurrences);
	}

}
