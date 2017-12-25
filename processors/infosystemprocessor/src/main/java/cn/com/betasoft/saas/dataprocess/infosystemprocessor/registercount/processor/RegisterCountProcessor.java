package cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.processor;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.registercount.model.RegisterCountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegisterCountProcessor implements ItemProcessor<RegisterCountModel, RegisterCountModel> {

    private static final Logger log = LoggerFactory.getLogger(RegisterCountProcessor.class);

    @Autowired
    @Qualifier("defaultSaaSJobParameters")
    private SaaSJobParameters defaultSaaSJobParameters;

    public RegisterCountModel process(RegisterCountModel registerCountModel) throws Exception {
        registerCountModel.setId(UUID.randomUUID().toString());
//        Date now = new Date();
//        registerCountModel.setCreateTime(now);
        registerCountModel.setDate(defaultSaaSJobParameters.getString(SaaSJobParameters.KEY_PROCESS_DATE_STR));
        log.info("Converting registerCountModel :"+registerCountModel);
        return registerCountModel;
    }
}
