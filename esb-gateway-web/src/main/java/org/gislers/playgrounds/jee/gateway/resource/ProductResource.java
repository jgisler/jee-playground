package org.gislers.playgrounds.jee.gateway.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.gislers.playgrounds.jee.common.http.ErrorItem;
import org.gislers.playgrounds.jee.common.http.GatewayResponse;
import org.gislers.playgrounds.jee.common.model.Product;
import org.gislers.playgrounds.jee.common.properties.MessageProperties;
import org.gislers.playgrounds.jee.gateway.service.SerializationService;
import org.gislers.playgrounds.jee.gateway.service.ValidationService;
import org.gislers.playgrounds.jee.services.publish.PublishService;
import org.gislers.playgrounds.jee.services.publish.dto.ProductDto;
import org.gislers.playgrounds.jee.services.publish.exception.PublishException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Path("/product")
public class ProductResource {

    private static final Logger logger = Logger.getLogger( ProductResource.class.getSimpleName() );

    private PublishService publishService;
    private SerializationService serializationService;
    private ValidationService validationService;

    @EJB
    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Inject
    public void setSerializationService(SerializationService serializationService) {
        this.serializationService = serializationService;
    }

    @Inject
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishProduct( @HeaderParam(MessageProperties.ENV_NAME) String envName,
                                    @HeaderParam(MessageProperties.MESSAGE_VERSION) String messageVersion,
                                    Product product ) {

        UUID txId = UUID.randomUUID();

        Response response;
        try {
            ProductDto productDto = buildProductDto( txId, envName, messageVersion, product );

            List<String> errors = validationService.validate(productDto);
            if( !errors.isEmpty() ) {
                response = buildErrorResponse( txId, errors, Response.Status.BAD_REQUEST );
            }
            else {
                publishService.publish(productDto);
                response = buildSuccessResponse( productDto );
            }
        }
        catch( JsonProcessingException e ) {
            response = buildErrorResponse( txId, e, Response.Status.BAD_REQUEST );
        }
        catch( PublishException e ) {
            response = buildErrorResponse( txId, e, Response.Status.INTERNAL_SERVER_ERROR );
        }
        return response;
    }

    ProductDto buildProductDto( UUID txId, String envName, String messageVersion, Product product ) throws JsonProcessingException {
        String payload = serializationService.toJson(product);

        ProductDto productDto = new ProductDto();
        productDto.setTxId( txId.toString() );
        productDto.setEnvironmentName( envName );
        productDto.setMessageVersion( messageVersion );
        productDto.setPayload(payload);
        return productDto;
    }

    Response buildSuccessResponse( ProductDto productDto ) {
        GatewayResponse gatewayResponse = new GatewayResponse();
        gatewayResponse.setTxId(productDto.getTxId());
        return Response.accepted(gatewayResponse)
                .build();
    }

    Response buildErrorResponse( UUID txId, Throwable throwable, Response.Status status ) {
        List<String> errors = new ArrayList<>();
        errors.add( ExceptionUtils.getRootCauseMessage(throwable) );
        return buildErrorResponse(txId, errors, status);
    }

    Response buildErrorResponse( UUID txId, List<String> errors, Response.Status status ) {
        GatewayResponse response = new GatewayResponse();
        response.setTxId( txId.toString() );
        for( String error : errors ) {
            response.getErrorItems().add( new ErrorItem(UUID.randomUUID(), error) );
        }
        return Response.status(status)
                .entity( response )
                .build();
    }
}
