package com.connect.demo.main2.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AgreementDao2 {


   List<Map<String,Object>> findList();

}
