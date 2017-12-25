package cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.processor;

import cn.com.betasoft.saas.dataprocess.baselib.parameters.SaaSJobParameters;
import cn.com.betasoft.saas.dataprocess.baselib.util.DateUtil;
import cn.com.betasoft.saas.dataprocess.infosystemprocessor.producttyperatio.model.ProductTypeRatioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Component
public class ProductTypeRatioProcessor implements ItemProcessor<ProductTypeRatioModel, ProductTypeRatioModel> {

    private static final Logger log = LoggerFactory.getLogger(ProductTypeRatioProcessor.class);

    @Autowired
    @Qualifier("defaultSaaSJobParameters")
    private SaaSJobParameters defaultSaaSJobParameters;

    public ProductTypeRatioModel process(ProductTypeRatioModel productTypeRatioModel) throws Exception {
        productTypeRatioModel.setId(UUID.randomUUID().toString());
//        Date now = new Date();
//        productTypeRatioModel.setCreateTime(now);
        productTypeRatioModel.setDate(defaultSaaSJobParameters.getString(SaaSJobParameters.KEY_PROCESS_DATE_STR));
        String productTypeCode = productTypeRatioModel.getProductType();
        if(StringUtils.isEmpty(productTypeCode)){
            productTypeRatioModel.setProductType("未填写");
        }else{
            switch (productTypeCode){
                case "1":
                    productTypeRatioModel.setProductType("正式机");
                    break;
                case "2":
                    productTypeRatioModel.setProductType("租赁机");
                    break;
                case "3":
                    productTypeRatioModel.setProductType("测试机");
                    break;
                default:
                    break;
            }
        }
        log.info("Converting productTypeRatioModel :"+productTypeRatioModel);
        return productTypeRatioModel;
    }
}
