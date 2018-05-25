package com.xuxue.dapp.btc.schedule;


import com.google.gson.Gson;
import com.xuxue.dapp.btc.Kline;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnnouncingAnswerSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(AnnouncingAnswerSchedule.class);
    private static final Gson G = new Gson();

    @Scheduled(fixedDelay = 1000*60*2)
    public void start(){
        try{
            Kline kline = requestKline();
            HttpPost post = new HttpPost("http://localhost:9003/announcing");
            post.setEntity(new StringEntity(G.toJson(kline)));
            try(CloseableHttpClient client = HttpClients.createDefault()){
                try(CloseableHttpResponse response = client.execute(post)){
                    LOG.info(EntityUtils.toString(response.getEntity()));
                }
            }
        }catch (Exception ex){
            LOG.warn("Failed request!",ex);
        }
    }

    public static Kline requestKline()throws Exception{
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet get = new HttpGet("https://www.binance.cloud/api/v1/klines?symbol=BTCUSDT&interval=1h");
            try(CloseableHttpResponse response = client.execute(get)){
                String json = EntityUtils.toString(response.getEntity());
                List<List<Object>> data =   G.fromJson(json, ArrayList.class);
                List<Object> d = data.get(data.size()-2);
                Kline kline = new Kline();
                kline.setTime(((Number)d.get(0)).longValue());
                kline.setOpenPrice(Double.parseDouble(d.get(1).toString()));
                kline.setClosePrice(Double.parseDouble(d.get(4).toString()));
                return kline;
            }
        }
    }


}
