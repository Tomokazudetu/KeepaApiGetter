package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceA {


	public JsonNode e(String text1) {
		String  result = "";
		JsonNode root = null;
		GZIPInputStream in = null;
		try {
			//アクセスキーを入力ください。
			String getUrl = "https://api.keepa.com/product?key=　&domain=5&asin=" + text1 + "&stats=90";
			URL url1 = new URL(getUrl);
			HttpURLConnection conect = (HttpURLConnection) url1.openConnection();
			conect.setRequestMethod("GET");
			conect.setRequestProperty("Accept-Encoding", "gzip");
			conect.setRequestProperty("Accept", "gzip, deflate");
			conect.setRequestProperty("Content-Encoding", "gzip");
			conect.setRequestProperty("Connection", "Keep-Alive");
			conect.setRequestProperty("Accept-Language", "jp");
			conect.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X; ja-JP-mac; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");

			conect.connect();

			in = new GZIPInputStream(conect.getInputStream() , 100);
			BufferedReader out = new BufferedReader(new InputStreamReader(in));


			String tmp = "";

			while((tmp = out.readLine()) != null){
				result += tmp;

			}

			ObjectMapper map = new ObjectMapper();
			root = map.readTree(result);


			in.close();
			conect.disconnect();

		}catch(Exception e) {
			e.printStackTrace();

		}
		return root;






	}
}