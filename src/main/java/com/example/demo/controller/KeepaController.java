package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.ProductsInfoMapper;
import com.example.demo.entity.ProductsInfo;
import com.example.demo.service.ServiceA;
import com.fasterxml.jackson.databind.JsonNode;



@Controller
public class KeepaController {

	@Autowired
	ProductsInfoMapper productsInfoMapper;

	@Autowired
	ServiceA ser;

	
	@GetMapping("/keepa")
	public String input() {
		return "imput/imput";

	}


	@PostMapping("/api")
	public String output(@RequestParam("text1")String str, Model model) {

		//各ProductsInfoにセットしたListを代入
		List<List<ProductsInfo>> ProductsIntoPut = new ArrayList<>();

		//受け取ったASINを分割
		String[] sp = str.split("[\r\n]+");
		for(int i =0 ; i < sp.length; i++) {
			JsonNode a = ser.e(sp[i]);


			//timeStanp
			int ts = 0;
			try {
				ts = a.get("timestamp").asInt();
			}catch(Exception e) {

			}
			model.addAttribute("ts" , ts);

			//lastUpdate
			int las = 0;
			try {
				las = a.get("products").get(0).get("lastUpdate").asInt();
			}catch(Exception e) {
			}
			model.addAttribute("las" , las);

			//イメージファイル
			String b = a.get("products").get(0).get("imagesCSV").asText();
			String[] picture = b.split(",");	

			String url = "https://images-na.ssl-images-amazon.com/images/I/" + picture[0];
			model.addAttribute("picture" , url);



			//④String型でかつ、「a.get("products").get(0)」の配下にある値
			List<String> csvUnderString = new ArrayList<>();
			csvUnderString.add("asin");
			csvUnderString.add("title");
			//この間に「currentSell,avgSell,avgSell30」を入れる
			csvUnderString.add("manufacturer");
			csvUnderString.add("brand");
			csvUnderString.add("partNumber");
			csvUnderString.add("model");
			csvUnderString.add("productGroup");
			csvUnderString.add("color");

			model.addAttribute("csvUnder" , csvUnderString);

			List<String> csvUnderPut = new ArrayList<>();

			for(String cu :csvUnderString) {
				String check = a.get("products").get(0).get(cu).asText();

				if(check != null) {
					csvUnderPut.add(check);
				}
			}
			model.addAttribute("csvUnderPut" , csvUnderPut);



			//④int型でかつ、「a.get("products").get(0)」の配下にある値
			List<String> csvUnderinteger = new ArrayList<>();

			csvUnderinteger.add("size");
			csvUnderinteger.add("packageHeight");
			csvUnderinteger.add("packageLength");
			csvUnderinteger.add("packageWidth");
			csvUnderinteger.add("packageWeight");

			model.addAttribute("csvUnderinteger" , csvUnderinteger);

			List<Integer> csvUnderintegerPut = new ArrayList<>();

			for(String cui :csvUnderinteger) {

				int check = 0;
				try {
					check = a.get("products").get(0).get(cui).asInt();
				}catch(Exception e) {

				}	
				csvUnderintegerPut.add(check);
			}

			model.addAttribute("csvUnderintegerPut" , csvUnderintegerPut);	


			//eanListはJson配列・もしくは配列じゃないので、Try/catchで受け取りエラー回避
			String eanList = null;

			try {
				eanList =a.get("products").get(0).get("eanList").get(0).asText();
			}catch(Exception e){
				eanList = a.get("products").get(0).get("eanList").asText();	
			}
			model.addAttribute("eanList" , eanList );


			//upcListはJson配列・もしくは配列じゃないので、Try/catchで受け取りエラー回避
			String upcList = null;

			try {
				upcList =a.get("products").get(0).get("upcList").get(0).asText();
			}catch(Exception e){
				upcList = a.get("products").get(0).get("upcList").asText();	
			}
			model.addAttribute("upcList" , upcList);


			//stats配下のファイル　売れた個数
			List<String> statsUnder = new ArrayList<>();
			statsUnder.add("salesRankDrops30");
			statsUnder.add("salesRankDrops90");
			statsUnder.add("salesRankDrops180");

			model.addAttribute("statsUnder" , statsUnder);

			List<Integer> statsUnderPut = new ArrayList<>();

			for(String su :statsUnder) {
				statsUnderPut.add(a.get("products").get(0).get("stats").get(su).asInt());
			}

			model.addAttribute("statsUnderPut" , statsUnderPut);


			//current 現在(カート)価格の取得
			int currentSell = a.get("products").get(0).get("stats").get("current").get(1).asInt();
			model.addAttribute("currentSell" , currentSell);

			//current 現在ランクの取得
			int currentRank = a.get("products").get(0).get("stats").get("current").get(3).asInt();
			model.addAttribute("currentRank" , currentRank);


			//avg 平均価格の取得
			int avgSell  = a.get("products").get(0).get("stats").get("avg").get(1).asInt();
			model.addAttribute("avgSell" , avgSell);

			//avg30 平均価格の取得
			int avgSell30  = a.get("products").get(0).get("stats").get("avg30").get(1).asInt();
			model.addAttribute("avgSell30" , avgSell30);


			//Mapperクラス(Insert)に渡すためのデータ
			List<ProductsInfo> ProductsInto = new ArrayList<>();

			//ProductsInfoへセットする
			ProductsInto.add(makeDate(ts,las,url,csvUnderPut.get(0),csvUnderPut.get(1),
					currentSell,avgSell,avgSell30,statsUnderPut.get(0),
					statsUnderPut.get(1),statsUnderPut.get(2),csvUnderPut.get(2),
					csvUnderPut.get(3),csvUnderPut.get(4),csvUnderPut.get(5),
					upcList,eanList,csvUnderPut.get(6),csvUnderPut.get(7),
					csvUnderintegerPut.get(0),csvUnderintegerPut.get(1),csvUnderintegerPut.get(2),
					csvUnderintegerPut.get(3),csvUnderintegerPut.get(4)));

			ProductsIntoPut.add(ProductsInto);

			productsInfoMapper.insertSet(ProductsInto);

		}

		//responseへ取得データを渡す
		model.addAttribute("ProductsInto" , ProductsIntoPut);

		return "request/response";


	}


	private ProductsInfo makeDate(int timestamp, int lastUpdate , String url,
			String asin, String title, int currentSell, int avgSell , 
			int avgSell30 , int salesRankDrops30, int salesRankDrops90,
			int salesRankDrops180 , String manufacturer , String brand , 
			String partNumber , String upcList , String eanList , String model,
			String productGroup , String color , int size ,int packageHeight , 
			int packageLength , int packageWidth , int packageWeight)
	{
		ProductsInfo productsInfo = new ProductsInfo();


		productsInfo.setTimestamp(timestamp);

		productsInfo.setLastUpdate(lastUpdate);

		productsInfo.setUrl(url);

		productsInfo.setAsin(asin);

		productsInfo.setTitle(title);

		productsInfo.setCurrentSell(currentSell);

		productsInfo.setAvgSell(avgSell);

		productsInfo.setAvgSell30(avgSell30);

		productsInfo.setSalesRankDrops30(salesRankDrops30);

		productsInfo.setSalesRankDrops90(salesRankDrops90);

		productsInfo.setSalesRankDrops180(salesRankDrops180);

		productsInfo.setManufacturer(manufacturer);

		productsInfo.setBrand(brand);

		productsInfo.setPartNumber(partNumber);

		productsInfo.setModel(upcList);

		productsInfo.setUpcList(eanList);

		productsInfo.setEanList(model);

		productsInfo.setProductGroup(productGroup);

		productsInfo.setColor(color);

		productsInfo.setSize(size);

		productsInfo.setPackageHeight(packageHeight);

		productsInfo.setPackageLength(packageLength);

		productsInfo.setPackageWidth(packageWidth);

		productsInfo.setPackageWeight(packageWeight);



		return productsInfo;
	}

	//全データ表示のメソッド
	@PostMapping("/all")
	public String index(Model model) {
		List<ProductsInfo> list = productsInfoMapper.selectAll();
		model.addAttribute("ProductsInfo", list);
		return "index";
	}

}
