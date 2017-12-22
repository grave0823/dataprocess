package cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.itdepartmentratio.model.ItDepartmentRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Component
public class ItDepartmentRatioProcessor implements ItemProcessor<ItDepartmentRatioModel, ItDepartmentRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(ItDepartmentRatioProcessor.class);

    public ItDepartmentRatioModel process(ItDepartmentRatioModel itDepartmentRatioModel) throws Exception {
        itDepartmentRatioModel.setId(UUID.randomUUID().toString());
        Date now = new Date();
        itDepartmentRatioModel.setCreateTime(now);
        itDepartmentRatioModel.setDate(DateUtil.formatDate(now,"yyyy-MM-dd"));
        String itDepartmentCode = itDepartmentRatioModel.getItDepartment();
        if(StringUtils.isEmpty(itDepartmentCode)){
            itDepartmentRatioModel.setItDepartment("未填写");
        }else{
            switch (itDepartmentCode){
                case "itDepartment":
                    itDepartmentRatioModel.setItDepartment("IT部门");
                    break;
                case "administration":
                    itDepartmentRatioModel.setItDepartment("行政类部门");
                    break;
                case "financeDepartment":
                    itDepartmentRatioModel.setItDepartment("财务类部门");
                    break;
                case "totalHandle":
                    itDepartmentRatioModel.setItDepartment("总经办");
                    break;
                case "other":
                    itDepartmentRatioModel.setItDepartment("其他部门");
                    break;
                default:
                    break;
            }
        }
        log.info("Converting itDepartmentRatioModel :"+itDepartmentRatioModel);
        return itDepartmentRatioModel;
    }
}
