package com.connect.demo.main.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AgreementDao {


   List<Map<String,Object>>  findList();

}
