package com.pgs.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import com.pgs.schema.order.AccountType;
import com.pgs.schema.order.BookType;
import com.pgs.schema.order.ObjectFactory;
import com.pgs.schema.order.OrderInquiryResponseType;
import com.pgs.schema.order.OrderInquiryType;
import com.pgs.schema.order.OrderItemType;
import com.pgs.schema.order.OrderStatusType;
import com.pgs.schema.order.OrderType;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Override
	public OrderInquiryResponseType processOrder(OrderInquiryType inquiry) {
		ObjectFactory factory = new ObjectFactory();
		OrderInquiryResponseType response = factory.createOrderInquiryResponseType();
		AccountType account = factory.createAccountType();
		account.setAccountId(inquiry.getAccountId());
		response.setAccount(account);

		OrderItemType orderItem = factory.createOrderItemType();
		BookType book = factory.createBookType();
		book.setEan13(inquiry.getEan13());
		book.setTitle("Amintiri din copilarie");
		orderItem.setBook(book);
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date(System.currentTimeMillis()));
			XMLGregorianCalendar estimatedShippingDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			orderItem.setExpectedShipingDate(estimatedShippingDate);
		} catch (Exception localException) {
		}
		orderItem.setLineNumber(Integer.valueOf(1).intValue());
		orderItem.setPrice(new BigDecimal(5));
		orderItem.setQuantityShipped(inquiry.getOrderQuantity());

		OrderType order = factory.createOrderType();
		order.setOrderStatus(OrderStatusType.READY);
		order.getOrderItems().add(orderItem);
		response.setOrder(order);
		return response;
	}

}
