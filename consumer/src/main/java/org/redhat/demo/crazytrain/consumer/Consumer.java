package org.redhat.demo.crazytrain.consumer;


import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.cloudevents.CloudEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.ce.IncomingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;


import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;



@ApplicationScoped
public class Consumer {
    private static final Logger LOGGER = Logger.getLogger(Consumer.class);
    
    // @Incoming("result")
    // @Produces(MediaType.SERVER_SENT_EVENTS)
    // public CompletionStage<Void>  process(Message<String> result) {
    //     IncomingCloudEventMetadata<Integer> cloudEventMetadata = result.getMetadata(IncomingCloudEventMetadata.class)
    //       .orElseThrow(() -> new IllegalArgumentException("Expected a Cloud Event"));
    //     LOGGER.infof("Received Cloud Events (spec-version: %s): source:  '%s', type: '%s', subject: '%s' , data: '%s'",
    //       cloudEventMetadata.getSpecVersion(),
    //       cloudEventMetadata.getSource(),
    //       cloudEventMetadata.getType(),
    //       cloudEventMetadata.getSubject().orElse("no subject"),
    //       cloudEventMetadata.getData());
    //     return result.ack(); 
    //   }

      @Incoming("train-monitoring")
      @Produces(MediaType.SERVER_SENT_EVENTS)
      public CompletionStage<Void>  process(Message<String> result) {
          LOGGER.infof("Received : '%s'",result.getPayload().toString());
          return result.ack();
      }
          
}
