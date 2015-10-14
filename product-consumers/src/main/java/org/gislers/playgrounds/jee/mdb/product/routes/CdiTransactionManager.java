package org.gislers.playgrounds.jee.mdb.product.routes;

import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Created by:   jgisle
 * Created date: 10/14/15
 */
@Named("txManager")
public class CdiTransactionManager extends JtaTransactionManager {

    @Resource(mappedName = "java:/TransactionManager")
    private TransactionManager transactionManager;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void initTransactionManager() {
        setTransactionManager(transactionManager);
        setUserTransaction(userTransaction);
    }
}
