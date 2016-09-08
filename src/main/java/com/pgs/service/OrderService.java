package com.pgs.service;

import com.pgs.schema.order.OrderInquiryResponseType;
import com.pgs.schema.order.OrderInquiryType;

public interface OrderService {

	OrderInquiryResponseType processOrder(OrderInquiryType inquiry);
}
