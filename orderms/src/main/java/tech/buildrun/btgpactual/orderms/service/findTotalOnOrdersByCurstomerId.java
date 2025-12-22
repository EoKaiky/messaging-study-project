package tech.buildrun.btgpactual.orderms.service;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import tech.buildrun.btgpactual.orderms.controller.dto.OrderResponse;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@AllArgsConstructor
public class findTotalOnOrdersByCurstomerId {

    private final MongoTemplate mongoTemplate;

    public BigDecimal findTotalOnOrdersByCurstomerId(Long customerId){
        var aggregations = newAggregation(
          match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);
        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

}
