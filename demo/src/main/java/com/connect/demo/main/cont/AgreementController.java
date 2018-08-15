package com.connect.demo.main.cont;

import com.connect.demo.main.dao.AgreementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zt/connect2")
public class AgreementController  {
    @Autowired
    private AgreementDao agreementDao;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(){
        System.out.println(agreementDao.findList());

    }

}

