package com.connect.demo.main2.cont;

import com.connect.demo.main.dao.AgreementDao;
import com.connect.demo.main2.dao.AgreementDao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zt/connect")
public class AgreementController2 {
    @Autowired
    private AgreementDao2 agreementDao;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(){
        System.out.println(agreementDao.findList());

    }

}

