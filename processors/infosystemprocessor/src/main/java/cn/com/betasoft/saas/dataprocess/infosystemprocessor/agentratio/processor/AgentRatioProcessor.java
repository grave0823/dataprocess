package cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.model.AgentRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class AgentRatioProcessor implements ItemProcessor<AgentRatioModel, AgentRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(AgentRatioProcessor.class);

    @Autowired
    @Qualifier("defaultSaaSJobParameters")
    private SaaSJobParameters defaultSaaSJobParameters;

    public AgentRatioModel process(AgentRatioModel agentRatioModel) throws Exception {
        agentRatioModel.setId(UUID.randomUUID().toString());
//        Date now = new Date();
//        agentRatioModel.setCreateTime(now);
        agentRatioModel.setDate(defaultSaaSJobParameters.getString(SaaSJobParameters.KEY_PROCESS_DATE_STR));
        log.info("Converting agentRatioModel :"+agentRatioModel);
        return agentRatioModel;
    }
}
