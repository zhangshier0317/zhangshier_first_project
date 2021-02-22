package com.eastrobot.robotdev.com.eastrobot.robotdev.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;



@PropertySource(value = {"classpath:app.properties"})
@Component
public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

	@Autowired
	private RobotUtils robotUtils;

	@Value("${queryInfoURL}")
	private String queryInfoURL;

	@Value("${callResultURL}")
	private String callResultURL;

	@Value("${uploadURL}")
	private String uploadURL;



	public String sendPost(String url, Map<String, String> map) {
		String result = null;
		PostMethod postMethod = null;
		LocalDateTime startTime = LocalDateTime.now();
		try {
			//1.创建Httpclient对象
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);

			postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type", "application/json");

			//2.封装参数
			RequestEntity requestParam = new StringRequestEntity(map.toString());
			postMethod.setRequestEntity(requestParam);

			//3.发送请求
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = Optional.ofNullable(new String(postMethod.getResponseBody(), "utf-8"))
						.orElseGet(String::new);
			} else {
				result = "";
			}
		} catch (Exception e) {
			result = "-1";
			LOG.error("xxxxxxxxxx send post error ", e);
		}

		LOG.info("<--------- 调用 {} 耗时 {} ms", url, robotUtils.DurationSecond(startTime, LocalDateTime.now()));
		return result;
	}

	public String sendGet(String url) {
		String result = null;
		LocalDateTime startTime = LocalDateTime.now();
		try {
			//1.创建Httpclient对象
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);

			//2.创建请求对象
			HttpMethod httpGet = new GetMethod(url);
			int statusCode = httpClient.executeMethod(httpGet);

			//3.判断返回值
			if (statusCode == HttpStatus.SC_OK) {
				result = Optional.ofNullable(new String(httpGet.getResponseBody(), "utf-8"))
						.orElseGet(String::new);
			}
		} catch (Exception e) {
			result = "-1";
			LOG.error(" xxxxxxx send get error ", e);
		}
		LOG.info("<--------- 调用 {} 耗时 {} ms", url,robotUtils.DurationSecond(startTime, LocalDateTime.now()));
		return result;
	}
}
