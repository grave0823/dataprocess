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
public class PartnerAreaRatioProcessor implements ItemProcessor<AreaRatioModel, AreaRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(PartnerAreaRatioProcessor.class);

    public AreaRatioModel process(AreaRatioModel areaRatioModel) throws Exception {
        areaRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        areaRatioModel.setCreateTime(now);
        areaRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        areaRatioModel.setType(AreaRatioModel.TYPE_PARTNER);
        log.info("Converting areaRatioModel :"+areaRatioModel);
        return areaRatioModel;
    }
}
