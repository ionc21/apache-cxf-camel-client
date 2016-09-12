package com.pgs.interceptor;

import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

@Component("isPhaseOkInterceptor")
public class IsPhaseOkInterceptor extends AbstractPhaseInterceptor<Message> {

	public IsPhaseOkInterceptor() {
		super(Phase.MARSHAL_ENDING);
		addBefore(SoapPreProtocolOutInterceptor.class.getName());
	}

	@Override
	public void handleMessage(Message message) {
		System.out.println("handleMessage Ok");
	}

	@Override
	public void handleFault(Message message) {
		System.out.println("handleFault Ok");
	}
}
