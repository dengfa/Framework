package com.hyena.framework.network.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.zip.GZIPInputStream;


/**
 * HttpEntity数据获取工具类
 * @author yangzc
 *
 */
public class EntityUtil {

	/**
	 * 获取HttpEntity中的内容字符串
	 * @param entity HttpEntity对象
	 * @return data
	 * @throws ParseException exception
	 * @throws IOException exception
	 */
	public static String toString(HttpEntity entity) throws ParseException,
            IOException {
		if (entity == null)
			return null;
		if (isGZIPed(entity))
			return gzipedToString(entity);
		return org.apache.http.util.EntityUtils.toString(entity);

	}

	/**
	 * 获取以GZIP格式压缩后的HttpEntity中的内容
	 * @param entity HttpEntity对象
	 * @return data
	 * @throws IllegalStateException exception
	 * @throws IOException exception
	 */
	public static String gzipedToString(HttpEntity entity)
			throws IllegalStateException, IOException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		GZIPInputStream is = new GZIPInputStream(entity.getContent());
		StringWriter writer = new StringWriter();

		char[] buffer = new char[1024];
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		return writer.toString();
	}

	/**
	 * 检查HttpEntity对象是否通过gzip压缩
	 * @param entity HttpEntity对象
	 * @return result
	 * @throws IllegalStateException exception
	 * @throws IOException exception
	 */
	public static boolean isGZIPed(HttpEntity entity)
			throws IllegalStateException, IOException {
		if (entity == null)
			return false;
		Header header = entity.getContentEncoding();
		if (header == null)
			return false;
		String contentEncoding = header.getValue();
		if (contentEncoding == null)
			return false;
		return contentEncoding.contains("gzip");
	}
}
