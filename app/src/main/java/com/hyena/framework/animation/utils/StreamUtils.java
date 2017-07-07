package com.hyena.framework.animation.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class StreamUtils {

	/*
	 * 读取字节
	 */
	public static byte readByte(DataInputStream is) throws IOException {
		return is.readByte();
	}
	
	/*
	 * 读取Short
	 */
	public static short readShort(DataInputStream is) throws IOException {
		return is.readShort();
	}
	
	
}
