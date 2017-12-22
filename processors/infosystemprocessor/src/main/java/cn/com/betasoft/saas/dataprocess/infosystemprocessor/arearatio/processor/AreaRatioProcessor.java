package cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.arearatio.model.AreaRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class AreaRatioProcessor implements ItemProcessor<AreaRatioModel, AreaRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(AreaRatioProcessor.class);

    public AreaRatioModel process(AreaRatioModel provinceRatioModel) throws Exception {
        provinceRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        provinceRatioModel.setCreateTime(now);
        provinceRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        log.info("Converting provinceRatioModel :"+provinceRatioModel);
        return provinceRatioModel;
    }
}
