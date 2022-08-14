package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.services.TicketService;
import com.ratepay.client.bugtracker.models.TicketModel;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketAPI extends BaseRestSqlModeImpl<TicketModel, Long> {

    public TicketAPI(TicketService ticketService) {
        super(ticketService);
    }
}
