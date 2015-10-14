package org.gislers.playgrounds.jee.mdb.product.routes;

import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by:   jgisle
 * Created date: 10/14/15
 */
@Named("PROPAGATION_REQUIRED")
public class CdiRequiredPolicy extends SpringTransactionPolicy {

    @Inject
    public CdiRequiredPolicy( CdiTransactionManager cdiTransactionManager ) {
        super( new TransactionTemplate(cdiTransactionManager,
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED))
        );
    }
}
