package org.gislers.playgrounds.jee.mdb.product;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.jee.common.properties.MessageProperties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/13/15
 */
public abstract class AbstractProductMdb implements MessageListener {

    protected abstract Logger getLogger();

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;

            String txId = textMessage.getStringProperty(MessageProperties.TRANSACTION_ID);
            String envName = textMessage.getStringProperty(MessageProperties.ENV_NAME);
            String messageVersion = textMessage.getStringProperty(MessageProperties.MESSAGE_VERSION);
            String payload = textMessage.getText();

            String logMsg = "Recieved message: txId='%s', envName='%s', msgVer='%s', payload='%s'";
            getLogger().info(String.format(logMsg, txId, envName, messageVersion, payload));
        }
        catch (JMSException e) {
           getLogger().warning(ExceptionUtils.getRootCauseMessage(e));
        }
    }
}
