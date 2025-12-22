package tech.buildrun.btgpactual.orderms.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.btgpactual.orderms.controller.dto.ApiResponse;
import tech.buildrun.btgpactual.orderms.controller.dto.OrderResponse;
import tech.buildrun.btgpactual.orderms.controller.dto.PaginationResponse;
import tech.buildrun.btgpactual.orderms.service.findAllByCustomerId;
import tech.buildrun.btgpactual.orderms.service.findTotalOnOrdersByCurstomerId;

import java.util.Map;

@RestController
@AllArgsConstructor
public class OrderController {

    private final findAllByCustomerId findAllByCustomerId;
    private final findTotalOnOrdersByCurstomerId findTotalOnOrders;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        var body = findAllByCustomerId.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnOrders = findTotalOnOrders.findTotalOnOrdersByCurstomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                body.getContent(),
                PaginationResponse.fromPage(body)
        ));
    }

}
