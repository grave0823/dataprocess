package cn.com.betasoft.saas.dataprocess.baselib.parameters;

import java.util.Date;
import java.util.Map;

public interface SaaSJobParameters{

    public final static String KEY_PROCESS_DATE_STR = "processDate";

    public void init();

    public void init(Map<String,Object> parameters);

    public String getString(String key);

    public Long getLong(String key);

    public Integer getInteger(String key);

    public Double getDouble(String key);

    public Date getDate(String key);

    public Object getValue(String key);
}
