package cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.industryratio.model.IndustryRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegisterIndustryRatioProcessor implements ItemProcessor<IndustryRatioModel, IndustryRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(RegisterIndustryRatioProcessor.class);

    public IndustryRatioModel process(IndustryRatioModel industryRatioModel) throws Exception {
        industryRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        industryRatioModel.setCreateTime(now);
        industryRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        industryRatioModel.setType(IndustryRatioModel.TYPE_REGISTER);
        log.info("Converting industryRatioModel :"+industryRatioModel);
        return industryRatioModel;
    }
}
