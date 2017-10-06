package com.umg.helloworld.interceptor;

import org.apache.logging.log4j.ThreadContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.interceptor.AbstractEnvelopeInterceptor;
import org.mule.management.stats.ProcessingTime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

public class EnterpriseLoggingInterceptor extends AbstractEnvelopeInterceptor{


    /**
     *
     * @param event
     * @return
     * @throws MuleException
     * This method is invoked before the event is processed
     */
    public MuleEvent before(MuleEvent event) throws MuleException {
        ThreadContext.put("message_id", "msg_id"+UUID.randomUUID().toString());
        ThreadContext.put("object_id", String.valueOf(100000 + new Random().nextInt(900000)));
        ThreadContext.put("object", "physicalAssetMessgae");
        ThreadContext.put("message_correlation_id", "corr_id"+UUID.randomUUID().toString());
        ThreadContext.put("action", "publish");
        ThreadContext.put("service_name", "restfacade");
        ThreadContext.put("source_system", "AIC");
        try {
            ThreadContext.put("host_name", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {

        }
        return event;
    }

    /**
     *
     * @param event
     * @return
     * @throws MuleException
     *
     * This method is invoked after the event has been processed, unless an exception was thrown
     */
    public MuleEvent after(MuleEvent event) throws MuleException {
        return event;
    }

    /**
     *
     * @param event
     * @param time
     * @param startTime
     * @param exceptionWasThrown
     * @return
     * @throws MuleException
     *
     *  This method is always invoked after the event has been processed
     */
    public MuleEvent last(MuleEvent event, ProcessingTime time, long startTime, boolean exceptionWasThrown) throws MuleException {
        ThreadContext.clearAll();
        return event;
    }
}