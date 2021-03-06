package org.gislers.playgrounds.jee.mdb.product.v3;

import org.gislers.playgrounds.jee.mdb.product.AbstractProductMdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
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
public class BrandV3ProductMdb extends AbstractProductMdb {

    private static final Logger logger = Logger.getLogger( BrandV3ProductMdb.class.getSimpleName() );

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
