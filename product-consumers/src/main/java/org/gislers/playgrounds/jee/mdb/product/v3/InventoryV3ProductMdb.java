package org.gislers.playgrounds.jee.mdb.product.v3;

import org.gislers.playgrounds.jee.mdb.product.AbstractProductMdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/13/15
 */
@MessageDriven(name = "InventoryV3ProductMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV3ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "InventoryV3ProductMdb"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class InventoryV3ProductMdb extends AbstractProductMdb {

    private static final Logger logger = Logger.getLogger( InventoryV3ProductMdb.class.getSimpleName() );

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
