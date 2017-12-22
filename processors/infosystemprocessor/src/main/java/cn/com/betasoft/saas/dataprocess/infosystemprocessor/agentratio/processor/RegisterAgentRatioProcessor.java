package cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.agentratio.model.AgentRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegisterAgentRatioProcessor implements ItemProcessor<AgentRatioModel, AgentRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(RegisterAgentRatioProcessor.class);

    public AgentRatioModel process(AgentRatioModel agentRatioModel) throws Exception {
        agentRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        agentRatioModel.setCreateTime(now);
        agentRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        agentRatioModel.setType(AgentRatioModel.TYPE_AGENT);
        log.info("Converting agentRatioModel :"+agentRatioModel);
        return agentRatioModel;
    }
}
