/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.servcie.debug;

public interface DebugModeListener {

	void onDebugModeChange(boolean debugMode);
	
	void onShowDebugMsg(String msg);
	
}
