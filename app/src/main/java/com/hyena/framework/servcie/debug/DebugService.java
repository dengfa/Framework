/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.servcie.debug;

public interface DebugService {

	String SERVICE_NAME = "debug_service";
	
	void enableDebug(boolean debugMode);
	
	void showDebugMsg(String msg);
	
	void clearMsg();
	
	DebugServerObserver getObserver();
	
	boolean isDebug();
	
}
