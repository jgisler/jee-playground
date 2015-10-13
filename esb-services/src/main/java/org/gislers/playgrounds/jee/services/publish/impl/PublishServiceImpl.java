package org.gislers.playgrounds.jee.services.publish.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.jee.common.properties.MessageProperties;
import org.gislers.playgrounds.jee.services.publish.PublishService;
import org.gislers.playgrounds.jee.services.publish.dto.ProductDto;
import org.gislers.playgrounds.jee.services.publish.exception.PublishException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/12/15
 */
@Stateless
public class PublishServiceImpl implements PublishService {

    private static final Logger logger = Logger.getLogger( PublishServiceImpl.class.getSimpleName() );

    @Resource(lookup="java:jboss/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="java:/jms/queue/InboundProductQueue")
    private Queue inboundProductQueue;

    @Override
    public void publish( final ProductDto productDto) throws PublishException {

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            MessageProducer messageProducer = session.createProducer(inboundProductQueue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage(productDto.getPayload());
            textMessage.setStringProperty(MessageProperties.ENV_NAME, productDto.getEnvironmentName());
            textMessage.setStringProperty(MessageProperties.TRANSACTION_ID, productDto.getTxId());
            textMessage.setStringProperty(MessageProperties.MESSAGE_VERSION, productDto.getMessageVersion());

            messageProducer.send(textMessage);
            logger.info(String.format("Sent message; txId: '%s'", productDto.getTxId()));
        }
        catch ( JMSException e ) {
            throw new PublishException(e);
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.info(getExceptionRootCause(e));
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.info(getExceptionRootCause(e));
                }
            }
        }
    }

    String getExceptionRootCause( Exception e ) {
        return ExceptionUtils.getRootCauseMessage( e );
    }
}
