package com.example.demo.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.model.Ocv;
import com.example.demo.model.Student;
import com.example.demo.toolkits.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.val;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/Index")
    public String Index() {
        String cell = "04QCE83H24600JD3S0005485";// cell
        String cell_1 = "";
        System.out.println(cell.substring(7));
        System.out.println(cell.substring(0, 7));

        List<Long> list = new ArrayList<>();
        for (long i = 1; i < 50; i++) {
            list.add(i);
            long num = convertA(list);
            System.out.println("sum_" + i + ":" + num);
        }


        List<Student> studentList = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            Student student = new Student();
            student.setId(i);
            student.setName("老王" + i);
            student.setSex(true);
            student.setState((int) i);
            studentList.add(student);
        }
        List<Integer> test1 = find1Cursor(3);
        List<Integer> test2 = find1Cursor(7);
        List<Integer> test3 = find1Cursor(15);
        List<Integer> test4 = find1Cursor(31);
        List<Integer> test5 = find1Cursor(131071);
        List<Integer> test6 = find1Cursor(65535);
        List<Integer> test7 = find1Cursor(54);
        studentList = studentList.stream().filter(s -> test7.contains(s.getState())).collect(Collectors.toList());

        System.out.println("max:" + Integer.MAX_VALUE);


        return "Home->Index";
    }

    public static List<Integer> find1Cursor(int number) {
        if (number < 0)
            return new ArrayList<>();

        List<Integer> cursorList = new ArrayList<>();

        int cursor = 0;

        if (number == 0) {
            cursorList.add(cursor);
            return cursorList;
        }

        while (true) {

            if (number == 0)
                break;

            //移动坐标
            ++cursor;
            //如果低位二进制有 1 值，则将坐标保存到数组中
            if ((number & 1) == 1) {
                cursorList.add(cursor);
            }

            number >>= 1;
        }
        return cursorList;
    }

    private long convertA(List<Long> numberList) {
        long number = 0;
        for (Long cursor : numberList) {
            if (cursor == 0) {
                number += 0;
            }
            number += 1 << (cursor - 1);
        }

        return number;
    }

    @RequestMapping("/GetBMWToken")
    public String GetBMWToken() throws Exception {
        String client_key = "WCrXK7osgDygQ2h7A5Bj0c82XS3VH6XduP/FokGjnDU=";
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String password = "{\"clientKey\":\"" + client_key + "\",\"account\":\"1\",\"timestamp\":\"" + timestamp + "\"}";

        String rsaPassword = AESUtil.encrypt(password, client_secret);

        return "Home->Test:password-->" + rsaPassword;
    }

    @RequestMapping("/GetBMWData/{num}")
    // public String GetBMWData(int num) throws Exception {
    public String GetBMWData(@PathVariable("num") Long num) throws Exception {
        String client_key = "WCrXK7osgDygQ2h7A5Bj0c82XS3VH6XduP/FokGjnDU=";
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";

        String data = "";
//        String currentDir = System.getProperty("user.dir");
//        File file = new File(currentDir + "\\src\\main\\resources\\ocvdata\\ocvdata_" + num + ".json");
        String resourcePath = "ocvdata/ocvdata_" + num + ".json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("资源文件未找到: " + resourcePath);
        }

        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Ocv.class);
//        List<Ocv> List = mapper.readValue(file, javaType);
        List<Ocv> List = mapper.readValue(inputStream, javaType);
        List = List.stream().distinct().collect(Collectors.toList());

        String result = mapper.writeValueAsString(List);
        String data_new = AESUtil.encrypt(result, client_secret);

        long timestamp1 = System.currentTimeMillis();
        // System.out.println("当前时间戳: " + timestamp1);

        return "Home->GetBMWData:" + timestamp1 + "," +
//                "文件名:" + file.getName() + "," +
                "文件名:" + resourcePath + "," +
                "Json长度:" + List.size() + "," +
                "加密-->\r\n" + data_new;
    }

    @RequestMapping("/JieMa/{num}")
    public String JieMa(@PathVariable("num") Long num) throws Exception {
        String client_secret = "MzsAJvgneLJh5T4hcEsfWr+S5Xdtj3nx2eHOcPaQmgs=";

//        String currentDir = System.getProperty("user.dir");
//        File file = new File(currentDir + "\\src\\main\\resources\\data\\data_" + num + ".txt");
        String resourcePath = "data/data_" + num + ".txt";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("资源文件未找到: " + resourcePath);
        }

        StringBuffer sb = new StringBuffer();
//        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            // System.out.println(line);
        }

        String data_new = AESUtil.decrypt(sb.toString(), client_secret);

        return "Home->Test:解密-->" + data_new;
    }

    @RequestMapping("/GetDate")
    public String GetDate() throws Exception {

        long timestamp1 = System.currentTimeMillis();
        System.out.println("当前时间戳: " + timestamp1);

        long timestamp4 = System.currentTimeMillis();
        System.out.println("当前时间戳-1: " + timestamp4 / 1000);

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

    @RequestMapping("/GetSHA512/{msg}")
    public String GetSHA512(@PathVariable("msg") String msg) throws NoSuchAlgorithmException {
        String input = msg;//"要加密的字符串"; // 需要加密的输入字符串

        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedBytes = digest.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
//            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            sb.append(String.format("%02x", b));
        }

        System.out.println("SHA-512加密结果为：" + sb.toString());
        return sb.toString();
    }

    @RequestMapping("/GetSHA512N/{msg}")
    public String GetSHA512N(@PathVariable("msg") String msg) throws NoSuchAlgorithmException {
//        String input = msg;//"要加密的字符串"; // 需要加密的输入字符串
//
//        MessageDigest digest = MessageDigest.getInstance("SHA-512");
//        digest.update(input.getBytes(StandardCharsets.UTF_8));
//        byte[] hash = digest.digest();
//        String encrypted = DatatypeConverter.printHexBinary(hash);
//
//        System.out.println("SHA-512 NEW加密结果为：" + encrypted);
//        System.out.println("uuid为：" + UUID.randomUUID());
//        return  encrypted;
        String encrypted = ShaUtils.getSha512(msg, false);
        String encrypted2 = ShaUtils.getSha512(msg, true);
        System.out.println("SHA-512 NEW加密结果为x：" + encrypted);
        System.out.println("SHA-512 NEW加密结果为d：" + encrypted2);
        return encrypted;
    }

    @RequestMapping("/TestWebService")
    public String TestWebService() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "dbtype=wms&type=SupToWms&json=[{\"electron_sn\":\"03HCB0160000AUD140202809\",\"lot_number\":\"AUD14\",\"djybyl\":390.1,\"ems\":\"gotion\",\"spec\":\"102Ah\",\"k_value\":0.482,\"ocv_value1\":3239.09,\"ocv_time1\":\"2023-01-11 22:01:35\",\"nz_acr\":0.3027,\"businesslotnumber\":\"0924060534-0021T23120AAM\",\"ocv_value2\":3235.66,\"ocv_time2\":\"2023-01-19 00:01:44\",\"dxhd\":49.707,\"create_time\":\"2023-01-19 15:04:28\",\"dxkd\":160.114,\"zyhdxzl\":1937.1,\"dx_produce_date\":\"2023-01-19 00:00:00\",\"zjjg\":116.478,\"box_sn\":\"LAZ02AL42303AA0720\",\"fjjg\":116.514,\"volume\":106731.5},{\"electron_sn\":\"03HCB0160000AUD140301514\",\"lot_number\":\"AUD14\",\"djybyl\":390.1,\"ems\":\"gotion\",\"spec\":\"102Ah\",\"k_value\":0.432,\"ocv_value1\":3237.42,\"ocv_time1\":\"2023-01-11 23:01:25\",\"nz_acr\":0.2963,\"businesslotnumber\":\"0924060534-0021T23120AAM\",\"ocv_value2\":3234.39,\"ocv_time2\":\"2023-01-19 00:01:09\",\"dxhd\":49.665,\"create_time\":\"2023-01-19 15:04:28\",\"dxkd\":160.152,\"zyhdxzl\":1933.05,\"dx_produce_date\":\"2023-01-19 00:00:00\",\"zjjg\":116.45,\"box_sn\":\"LAZ02AL42303AA0720\",\"fjjg\":116.427,\"volume\":105513.6}]");
        Request request = new Request.Builder()
                .url("http://dataapi.scud.cn:8092/MesToOneService.asmx/WebInvoke")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();


        return "WebService-->" + response.body().string();
    }

    //    @RequestMapping("/TestWebService2")
    @PostMapping("/TestWebService2")
    public String TestWebService2(@RequestBody JSONObject req) throws Exception {
        String url = "http://dataapi.scud.cn:8092/MesToOneService.asmx/WebInvoke";
        String param = "";

        JSONArray data = EveJsonUtil.getJSONArray(req, "data");

        Map<String, String> headParams = new HashMap<>();
        headParams.put("Content-Type", "application/x-www-form-urlencoded");

//        param = "dbtype=wms&type=SupToWms&json=[{\"electron_sn\":\"03HCB0160000AUD140202809\",\"lot_number\":\"AUD14\",\"djybyl\":390.1,\"ems\":\"gotion\",\"spec\":\"102Ah\",\"k_value\":0.482,\"ocv_value1\":3239.09,\"ocv_time1\":\"2023-01-11 22:01:35\",\"nz_acr\":0.3027,\"businesslotnumber\":\"0924060534-0021T23120AAM\",\"ocv_value2\":3235.66,\"ocv_time2\":\"2023-01-19 00:01:44\",\"dxhd\":49.707,\"create_time\":\"2023-01-19 15:04:28\",\"dxkd\":160.114,\"zyhdxzl\":1937.1,\"dx_produce_date\":\"2023-01-19 00:00:00\",\"zjjg\":116.478,\"box_sn\":\"LAZ02AL42303AA0720\",\"fjjg\":116.514,\"volume\":106731.5},{\"electron_sn\":\"03HCB0160000AUD140301514\",\"lot_number\":\"AUD14\",\"djybyl\":390.1,\"ems\":\"gotion\",\"spec\":\"102Ah\",\"k_value\":0.432,\"ocv_value1\":3237.42,\"ocv_time1\":\"2023-01-11 23:01:25\",\"nz_acr\":0.2963,\"businesslotnumber\":\"0924060534-0021T23120AAM\",\"ocv_value2\":3234.39,\"ocv_time2\":\"2023-01-19 00:01:09\",\"dxhd\":49.665,\"create_time\":\"2023-01-19 15:04:28\",\"dxkd\":160.152,\"zyhdxzl\":1933.05,\"dx_produce_date\":\"2023-01-19 00:00:00\",\"zjjg\":116.45,\"box_sn\":\"LAZ02AL42303AA0720\",\"fjjg\":116.427,\"volume\":105513.6}]";

        Map<String, String> params = new HashMap<>();
        params.put("dbtype", "wms");
        params.put("type", "SupToWms");
        params.put("json", data.toJSONString());

        for (String key : params.keySet()) {
            param = param + key + "=" + params.get(key) + "&";
        }
        if (StringUtils.isNotEmpty(param)) {
            param = param.substring(0, param.length() - 1);
        }

        param = StringUtils.isNotBlank(param) ? param : "";

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            log.info("sendPost - {}", url);

            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/json");
            if (StringUtils.isNotEmpty(headParams)) {
                headParams.forEach((key, value) -> {
                    conn.setRequestProperty(key, value);
                });
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
//            result = new StringBuilder(EveJsonUtil.error("请求失败，请检查网络是否正常！").toJSONString());
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
//            result = new StringBuilder(EveJsonUtil.error(e.getMessage()).toJSONString());
        } catch (IOException e) {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
//            result = new StringBuilder(EveJsonUtil.error(e.getMessage()).toJSONString());
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
//            result = new StringBuilder(EveJsonUtil.error(e.getMessage()).toJSONString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
                result = new StringBuilder(EveJsonUtil.error(ex.getMessage()).toJSONString());
            }
        }
        return "TestWebService2--->" + result.toString();
    }

    @RequestMapping("/TransXML1")
    public String TransXML1() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">{  \"data\": null,  \"msg\": \"传输成功!\",  \"status\": 0}</string>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
//        XPathExpression expr = xPath.compile("/root/tag/@attribute");
        XPathExpression expr = xPath.compile("/string");
        String attributeValue = (String) expr.evaluate(document, XPathConstants.STRING);

        System.out.println(attributeValue);

        return "TransXML1--" + attributeValue;
    }

    @RequestMapping("/TransXML2")
    public String TransXML2() throws ParserConfigurationException, IOException, SAXException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">{  \"data\": null,  \"msg\": \"传输成功!\",  \"status\": 0}</string>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(xmlString));
        Document document = builder.parse(inputSource);
        document.getDocumentElement().normalize();

        String value = "";

//        Element root = document.getDocumentElement();
        NodeList nodeList = document.getElementsByTagName("string");
        if (nodeList != null && nodeList.getLength() > 0) {
            org.w3c.dom.Element element = (org.w3c.dom.Element) nodeList.item(0);
            value = element.getTextContent();
            System.out.println("Tag value: " + value);
        }

        return "TransXML2--" + value;
    }
}
