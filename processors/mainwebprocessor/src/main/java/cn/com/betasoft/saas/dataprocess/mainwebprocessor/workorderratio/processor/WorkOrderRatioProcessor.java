package cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.enums.WorkOrderModuleTypeEnum;
import cn.com.betasoft.saas.dataprocess.mainwebprocessor.workorderratio.model.WorkOrderRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Component
public class WorkOrderRatioProcessor implements ItemProcessor<WorkOrderRatioModel, WorkOrderRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderRatioProcessor.class);

    public WorkOrderRatioModel process(WorkOrderRatioModel workOrderRatioModel) throws Exception {
        workOrderRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        workOrderRatioModel.setCreateTime(now);
        workOrderRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        String moduleNameCode = workOrderRatioModel.getModuleName();
        if(StringUtils.isEmpty(moduleNameCode)){
            workOrderRatioModel.setModuleName("未填写");
        }else{
            int moduleNameCodeInt = Integer.parseInt(moduleNameCode);
            String moduleNameCh = WorkOrderModuleTypeEnum.getType(moduleNameCodeInt);
            workOrderRatioModel.setModuleName(moduleNameCh);
        }
        log.info("Converting workOrderRatioModel :"+workOrderRatioModel);
        return workOrderRatioModel;
    }
}
