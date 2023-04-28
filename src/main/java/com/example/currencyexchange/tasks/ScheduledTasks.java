package com.example.currencyexchange.tasks;

import com.alibaba.fastjson2.JSONObject;
import com.example.currencyexchange.helper.Constant;
import com.example.currencyexchange.model.entity.Currency;
import com.example.currencyexchange.model.entity.RatesSchedule;
import com.example.currencyexchange.model.entity.RatesSource;
import com.example.currencyexchange.service.impl.CurrencyService;
import com.example.currencyexchange.service.impl.RatesScheduleService;
import com.example.currencyexchange.service.impl.RatesSourceService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledTasks {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    OkHttpClient client = new OkHttpClient();

    @Value("${python.path}")
    public String pythonPath;
    @Value("${python.ratebot}")
    public String pythonRateBot;

    @Setter(onMethod_ = {@Autowired})
    public RatesScheduleService ratesScheduleService;
    @Setter(onMethod_ = {@Autowired})
    public CurrencyService currencyService;
    @Setter(onMethod_ = {@Autowired})
    public RatesSourceService ratesSourceService;
    @Setter(onMethod_ = {@Autowired})
    StringRedisTemplate redisTemplate;

    @Scheduled(cron = "*/60 * * * * *")//0 0 16 * * *
    public void getDailyExchangeRates() {
        //請求來源是否開啟
        Optional<RatesSource> ratesSource = ratesSourceService.findByIdAndIsOpen(RatesSource.EXCHANGERATEAPI);
        if (ratesSource.isPresent()) {

            List<Currency> allCurrency = currencyService.findAll();
            allCurrency.forEach(e -> {
                String url = ratesSource.get().getAddress().replace(RatesSource.REPLACECODE, e.getCurCode());
                logger.info(url);
                Request request = new Request.Builder().url(url).build();
                Response execute;
                try {
                    execute = client.newCall(request).execute();
                    String data = execute.body().string();
                    logger.info(data);
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    RatesSchedule ratesSchedule = ratesScheduleService.findByCurCodeAndRatesSourceId(e.getCurCode(), RatesSource.EXCHANGERATEAPI);
                    ratesSchedule.setRatesSourceId(RatesSource.EXCHANGERATEAPI);
                    ratesSchedule.setCurCode(e.getCurCode());
                    ratesSchedule.setData(jsonObject.toString());
                    ratesScheduleService.save(ratesSchedule);
//                ratesSchedule.setProvider(jsonObject.getString("provider"));
//                ratesSchedule.setWarningUpgradeToV6(jsonObject.getString("WARNING_UPGRADE_TO_V6"));
//                ratesSchedule.setTerms(jsonObject.getString("terms"));
//                ratesSchedule.setBase(jsonObject.getString("base"));
//                ratesSchedule.setDate(jsonObject.getDate("date"));
//                ratesSchedule.setRates(jsonObject.getString("rates"));
//                ratesSchedule.setTimeLastUpdated(jsonObject.getInteger("time_last_updated"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    @Scheduled(cron = "*/180 * * * * *")
    public void getTaiwanBankRates() {

        //請求來源是否開啟
        Optional<RatesSource> ratesSource = ratesSourceService.findByIdAndIsOpen(RatesSource.BANKOFTAIWAN);
        if (ratesSource.isPresent()) {
            String[] args1 = new String[]{pythonPath, pythonRateBot};
            Process python_;
            try {
                python_ = Runtime.getRuntime().exec(args1);
                BufferedReader in = new BufferedReader(new InputStreamReader(python_.getInputStream()));
                String line = in.readLine();
                redisTemplate.opsForValue().set(Constant.REDIS_KEY.BANKOFTAIWAN,line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
