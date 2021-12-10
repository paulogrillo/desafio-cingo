package com.cingo.logstore.entity.factory;

import com.cingo.logstore.entity.Log;

public interface LogFactory {
	
	Log build(String content, int occurrences);

}
