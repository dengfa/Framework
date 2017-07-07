/*
 * Copyright (c) 2013 Baidu Inc.
 */
package com.hyena.framework.network;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpProvider下载监听器
 * 
 * 调用顺序如下：<br>
 * <p>onReady-onStart-onAdvance-onComplete-onRelease</p>
 * 状态码不正确 <br>
 * <p>onReady-onError-onRelease</p>
 *
 * @author yangzc
 */
public interface HttpListener {

	/*
	 * 整个下载任务开始
	 */
	boolean onReady(String url);

	/*
	 * 网络响应
     */
	boolean onResponse(InputStream is, OutputStream os, int statusCode,
                       String mimeType, String encoding, long contentLength,
                       boolean repeatable, boolean isTrunk);
	
	/*
	 * 开始下载
	 * 发生这个回调代表已经连接成功
	 */
	boolean onStart(long startPos, long contentLength) throws Throwable;
	
	/*
	 * 开始读取网络数据
	 */
	boolean onAdvance(byte buffer[], int offset, int len) throws Throwable;
	
	/*
	 * 下载完成
	 * 没有任何意外
	 */
	boolean onCompleted() throws Throwable;
	
	/*
	 * 由于网络返回码
	 * 或者异常导致的下载失败
	 */
	void onError(int statusCode) throws Throwable;
	
	/*
	 * 整个任务结束
	 */
	boolean onRelease() throws Throwable;
}
