package cn.com.betasoft.saas.dataprocess.baselib.parameters.impl;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("defaultSaaSJobParameters")
public class DefaultSaaSJobParameters implements SaaSJobParameters {

    private Map<String,Object> parameters = null;

    @Override
    public void init() {
        parameters = new HashMap<>();
        Date yesterday = DateUtil.adjDataByDay(null,-1);
        parameters.put(SaaSJobParameters.KEY_PROCESS_DATE_STR, DateUtil.formatDate(yesterday,"yyyy-MM-dd"));
    }

    @Override
    public void init(Map<String, Object> parameters) {
        parameters = parameters;
    }

    @Override
    public String getString(String key) {
        String value = null;
        Object v = getValue(key);
        if(v!=null){
            value = v.toString();
        }
        return value;
    }

    @Override
    public Long getLong(String key) {
        Long value = null;
        Object v = getValue(key);
        if(v!=null){
            value = (Long)v;
        }
        return value;
    }

    @Override
    public Integer getInteger(String key) {
        Integer value = null;
        Object v = getValue(key);
        if(v!=null){
            value = (Integer)v;
        }
        return value;
    }

    @Override
    public Double getDouble(String key) {
        Double value = null;
        Object v = getValue(key);
        if(v!=null){
            value = (Double)v;
        }
        return value;
    }

    @Override
    public Date getDate(String key) {
        Date value = null;
        Object v = getValue(key);
        if(v!=null){
            value = (Date)v;
        }
        return value;
    }

    @Override
    public Object getValue(String key) {
        if(CollectionUtils.isEmpty(parameters)){
            return null;
        }
        return parameters.get(key);
    }
}
