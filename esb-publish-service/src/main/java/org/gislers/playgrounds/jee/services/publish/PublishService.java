package org.gislers.playgrounds.jee.services.publish;

import org.gislers.playgrounds.jee.services.publish.dto.ProductDto;
import org.gislers.playgrounds.jee.services.publish.exception.PublishException;

import javax.ejb.Local;

/**
 * Created by:   jgisle
 * Created date: 10/12/15
 */
@Local
public interface PublishService {

    void publish(ProductDto productDto) throws PublishException;
}
