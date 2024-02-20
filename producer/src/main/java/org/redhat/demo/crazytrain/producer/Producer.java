import java.net.URI;
import java.time.Duration;
import java.util.UUID;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;


import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Message;


import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.Record;
import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class Producer {

    private static final Logger LOGGER = Logger.getLogger(Producer.class);

    // @Outgoing("result")
    // public Message<String> generate() {
    //     Message<String> in = Message.of("message");
    //     return in.addMetadata(OutgoingCloudEventMetadata.builder()
    //     .withId("id-" + in.getPayload())
    //     .withType("result")
    //     .withSource(URI.create("http://example.com"))
    //     .withSubject("result-message").build());
    // }

    String message =  "{\"uid\":\"47904c5c-9acf-4898-96ef-ee20b8425fbd\",\"detections\":[{\"class_id\":3,\"class_name\":\"PedestiranCrossingAhead\",\"confidence\":\"0.94\",\"box\":[\"355.50\",\"117.75\",\"73.98\",\"65.91\"]},{\"class_id\":2,\"class_name\":\"TrafficSignalsAhead\",\"confidence\":\"0.89\",\"box\":[\"256.94\",\"117.60\",\"71.51\",\"64.38\"]},{\"class_id\":0,\"class_name\":\"SpeedLimit_30\",\"confidence\":\"0.79\",\"box\":[\"25.72\",\"89.84\",\"69.53\",\"69.14\"]},{\"class_id\":3,\"class_name\":\"PedestiranCrossingAhead\",\"confidence\":\"0.77\",\"box\":[\"159.45\",\"120.55\",\"72.17\",\"63.97\"]},{\"class_id\":1,\"class_name\":\"SpeedLimit_50\",\"confidence\":\"0.73\",\"box\":[\"120.03\",\"89.87\",\"68.10\",\"67.18\"]}],\"pre-process\":\"0.01ms\",\"inference\":\"0.09ms\",\"post-process\":\"0.02ms\",\"total\":\"0.13ms\",\"scale\":1.09375,\"image\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=\"}";
    @Outgoing("topic-train-model-result")
    public Multi<String> generateMqtt() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(60))
                .onOverflow().drop()
                .map(tick -> {
                    System.out.println(message);
                    return message;
                });
    }
}
