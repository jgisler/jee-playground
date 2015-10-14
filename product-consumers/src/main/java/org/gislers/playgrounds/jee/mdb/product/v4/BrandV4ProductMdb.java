package org.gislers.playgrounds.jee.mdb.product.v4;

import org.gislers.playgrounds.jee.mdb.product.AbstractProductMdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.logging.Logger;

/**
 * Created by:   jgisle
 * Created date: 10/14/15
 */
@MessageDriven(name = "BrandV4ProductMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup"        , propertyValue = "java:/jms/topic/OutboundV4ProductTopic"),
        @ActivationConfigProperty(propertyName = "destinationType"          , propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode"          , propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "subscriptionName"         , propertyValue = "BrandV4ProductMdb"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability"   , propertyValue = "Durable")
})
public class BrandV4ProductMdb extends AbstractProductMdb {

    private static final Logger logger = Logger.getLogger( BrandV4ProductMdb.class.getSimpleName() );

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
