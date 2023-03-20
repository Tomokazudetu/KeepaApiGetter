package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.ProductsInfo;



@Mapper
public interface ProductsInfoMapper {


	//Mapperに定義されたselectAll(全返し)を実行
	List<ProductsInfo> selectAll();
	

	//Mapperに定義されたinsertSet(データの代入)を実行
	void insertSet(@Param("Products")List<ProductsInfo> ProductsInto);
	
}
