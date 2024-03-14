package org.redhat.demo.crazytrain.consumer;

import java.util.Base64;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Produces;


import org.jboss.logging.Logger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class Consumer {
  private static final Logger LOGGER = Logger.getLogger(Consumer.class);
    
  @Incoming("train-monitoring")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public Object  process(String result) {
    System.out.println("Consumer kafka recived  : "+result);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode;
    try {
      jsonNode = mapper.readTree(result);
          JsonNode data = jsonNode.get("data");
          String id = data.get("id").asText();
          String imageBytesBase64  = data.get("image").asText();
          byte[] imageBytes = Base64.getDecoder().decode(imageBytesBase64.substring(imageBytesBase64.indexOf(",")+1));
          Mat image = new Mat(480, 640, CvType.CV_8UC3);
          image.put(0, 0, imageBytes);
          image = addSquareToimage(image, data.get("detections"));
          long timestamp = System.currentTimeMillis();
          String filename = timestamp+".jpg";
          try {
              if (!Imgcodecs.imwrite(filename, image)) {
                  LOGGER.error("Failed to save image");
              }
          } catch (Exception e) {
              // TODO: handle exception
              LOGGER.error("Failed to save image", e);
          }
          //LOGGER.infof("Received dans consumer kafka: Id '%s' Image '%s'",id, imageBytes);
          // System.out.println("Received dans consumer kafka: Id "+id+" Image "+new String(imageBytes));
          return null;
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
      return null;
  }  
  private Mat addSquareToimage(Mat image, JsonNode detections){
    if(detections == null || detections.size()==0 || !detections.isArray())
      return image;
    for(JsonNode detection : detections){
      double x = detection.get("box").get(0).asDouble();
      double y = detection.get("box").get(1).asDouble();
      double width = detection.get("box").get(2).asDouble();
      double height = detection.get("box").get(3).asDouble();
      // Create a rectangle from the detected box coordinates
      Rect rect = new Rect(new Point(x, y), new Size(width, height));
      // Draw the rectangle on the image
      Scalar color = new Scalar(0, 0, 255);  // Red color
      int thickness = 2;  // Thickness of the rectangle border
      Imgproc.rectangle(image, rect, color, thickness);
      // Add a label
      String label = detection.get("class_name").asText();  // Replace with your actual label
      int fontFace = Imgproc.FONT_ITALIC;
      double fontScale = 0.5;
      Scalar textColor = new Scalar(255, 255, 255);  // White color
      int textThickness = 2;
      Imgproc.putText(image, label, new Point(x, y - 20), fontFace, fontScale, textColor, textThickness);
      String confidence = "Confidence: "+detection.get("confidence").asText();
      Imgproc.putText(image, confidence, new Point(x, y - 5), fontFace, fontScale, textColor, textThickness);
    }
    return image;
  }
}
