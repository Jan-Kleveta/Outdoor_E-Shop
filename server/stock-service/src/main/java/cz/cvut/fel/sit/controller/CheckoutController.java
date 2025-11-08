package cz.cvut.fel.sit.controller;

import com.stripe.model.checkout.Session;
import cz.cvut.fel.sit.domain.ReservedItem;
import cz.cvut.fel.sit.dto.response.ProductResponse;
import cz.cvut.fel.sit.dto.response.StripeSessionResponse;
import cz.cvut.fel.sit.mapper.ItemMapper;
import cz.cvut.fel.sit.dto.request.StripeSessionRequest;
import cz.cvut.fel.sit.service.CheckoutService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Orders", description = "API for order operations")
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    private final ItemMapper itemMapper;

    @PreAuthorize("permitAll()")
    @PostMapping("/public/checkout")
    public ResponseEntity<StripeSessionResponse> createCheckoutSession(@Valid @RequestBody StripeSessionRequest sessionRequest,
                                                                 @AuthenticationPrincipal @Nullable Jwt principal) throws Exception {
        List<ReservedItem> items = itemMapper.toReservedItemList(sessionRequest.getItems());
        StripeSessionResponse sessionResponse = checkoutService.createStripeSession(items, principal);
        return ResponseEntity.ok(sessionResponse);
    }

}
