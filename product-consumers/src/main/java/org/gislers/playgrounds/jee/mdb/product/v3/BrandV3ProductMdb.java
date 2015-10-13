package org.gislers.playgrounds.jee.mdb.product.v3;

import org.gislers.playgrounds.jee.common.properties.MessageProperties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/13/15
 */
@MessageDriven(name = "BrandV3ProductMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV3ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "BrandV3ProductMdb"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class BrandV3ProductMdb implements MessageListener {

    private static final Logger logger = Logger.getLogger( BrandV3ProductMdb.class.getSimpleName() );

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;

            String txId = textMessage.getStringProperty(MessageProperties.TRANSACTION_ID);
            String envName = textMessage.getStringProperty(MessageProperties.ENV_NAME);
            String messageVersion = textMessage.getStringProperty(MessageProperties.MESSAGE_VERSION);
            String payload = textMessage.getText();

            String logMsg = "Recieved message: txId='%s', envName='%s', msgVer='%s', payload='%s'";
            logger.info( String.format(logMsg, txId, envName, messageVersion, payload) );
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
