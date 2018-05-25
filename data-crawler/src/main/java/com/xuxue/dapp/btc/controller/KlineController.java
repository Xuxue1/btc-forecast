package com.xuxue.dapp.btc.controller;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/btc")
@RestController
public class KlineController {
    private static final Logger LOG = LoggerFactory.getLogger(KlineController.class);
    private CloseableHttpClient client = HttpClients.createDefault();


    @GetMapping(value="kline")
    public String kline(){
        try{
            HttpGet get = new HttpGet("https://www.binance.cloud/api/v1/klines?symbol=BTCUSDT&interval=1h&limit=5");
            try(CloseableHttpResponse response = client.execute(get)){
                return EntityUtils.toString(response.getEntity());
            }
        }catch (Exception ex){
            LOG.warn("Failed request!");
        }
        return "";
    }

}
