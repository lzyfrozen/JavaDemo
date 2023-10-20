package com.example.demo.controller;

import com.example.demo.model.Ocv;
import com.example.demo.toolkits.AESUtil;
import com.example.demo.toolkits.RsaUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class HomeController {

    @RequestMapping("/Index")
    public String Index() {
        return "Home->Index";
    }

    @RequestMapping("/GetBMWToken")
    public String GetBMWToken() throws Exception {
        String client_key = "WCrXK7osgDygQ2h7A5Bj0c82XS3VH6XduP/FokGjnDU=";
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";
        String password = "{\"clientKey\":\"" + client_key + "\",\"account\":\"1\",\"timestamp\":\"1697618076253\"}";

        String rsaPassword = AESUtil.encrypt(password, client_secret);

        return "Home->Test:password-->" + rsaPassword;
    }

    @RequestMapping("/GetBMWData")
    public String GetBMWData(int num) throws Exception {
        String client_key = "WCrXK7osgDygQ2h7A5Bj0c82XS3VH6XduP/FokGjnDU=";
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";

        String data = "";
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "\\src\\main\\resources\\ocvdata\\ocvdata_" + num + ".json");

        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Ocv.class);
        List<Ocv> List = mapper.readValue(file, javaType);

        String result = mapper.writeValueAsString(List);
        String data_new = AESUtil.encrypt(result, client_secret);

        long timestamp1 = System.currentTimeMillis();
//        System.out.println("当前时间戳: " + timestamp1);

        return "Home->GetBMWData:" + timestamp1 + "," +
                "文件名:" + file.getName() + "," +
                "Json长度:" + List.size() + "," +
                "加密-->" + data_new;
    }

    @RequestMapping("/Test3_2")
    public String Test3_2() throws Exception {
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";

        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "\\src\\main\\resources\\data\\data_" + 1 + ".txt");

        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
//            System.out.println(line);
        }

        String data_new = AESUtil.decrypt(sb.toString(), client_secret);

        return "Home->Test:解密-->" + data_new;
    }

    @RequestMapping("/GetDate")
    public String GetDate() throws Exception {

        long timestamp1 = System.currentTimeMillis();
        System.out.println("当前时间戳: " + timestamp1);

        Instant instant = Instant.now();
        long timestamp2 = instant.toEpochMilli();
        System.out.println("当前时间戳: " + timestamp2);

        Date date = new Date();
        long timestamp3 = date.getTime();
        System.out.println("当前时间戳: " + timestamp3);

        return "Home->GetDate:-->" + timestamp1 + ":" + timestamp2 + ":" + timestamp3;
    }

    @RequestMapping("/Test")
    public String Test() throws Exception {
        String rsaPublickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwnNvt+crmV2iC2bGs9cAhxVmjtPgYttba+LVkAbMTZCpjSWiE1gpisaPwGpRqx29oV4X6pDp44hzxWoK3NFmu9NzSXgn9fHr8dGtWAzj8j7hxfkHB4YYXivGlU8jXvzi9Rd4NYk7zMEjMGuU65uJBws+hNhV9O7dQtaXKsrw5meK9KPMSca7kvIy5+QdzLNg/u5q5LkijqB57m7qTSJ6SlBDjUhuVP5PKDIYyDzFZfpGbDXCKJhHT/zeM9DNSi54V9vKyySoWlMWDTWKXtqjCuUGk/RLruN4C82OSaGelbfLTLS9pJT5otu3LgypGWLNAZDrbHyXphxx4i8QRxQA1wIDAQAB";
        String password = "p123456";

        String rsaPassword = RsaUtil.encryptPasswordRSA(password, rsaPublickey);
        return "Home->Test:password-->" + rsaPassword;
    }
}
