package cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.model.IndustryRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class IndustryRatioProcessor implements ItemProcessor<IndustryRatioModel, IndustryRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(IndustryRatioProcessor.class);

    @Autowired
    @Qualifier("defaultSaaSJobParameters")
    private SaaSJobParameters defaultSaaSJobParameters;

    public IndustryRatioModel process(IndustryRatioModel industryRatioModel) throws Exception {
        industryRatioModel.setId(UUID.randomUUID().toString());
//        Date now = new Date();
//        industryRatioModel.setCreateTime(now);
        industryRatioModel.setDate(defaultSaaSJobParameters.getString(SaaSJobParameters.KEY_PROCESS_DATE_STR));
        log.info("Converting industryRatioModel :"+industryRatioModel);
        return industryRatioModel;
    }
}
