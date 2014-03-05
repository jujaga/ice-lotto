package com.jrfom.icelotto.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

public class ImageDownloader {
  private static final Logger log = LoggerFactory.getLogger(ImageDownloader.class);

  private RestTemplate restTemplate;
  private String destinationDir;

  public ImageDownloader(String destinationDir) {
    this.destinationDir = destinationDir;
    this.setRestTemplate(new RestTemplate());
  }

  /**
   * <p>Downloads an image from the specified {@code url} and saves it to the
   * directory specified by the {@link com.jrfom.icelotto.util.ImageDownloader}
   * constructor.</p>
   *
   * @param url A URL to a PNG image.
   * @param name A name for the file. Either "foo" or "foo.png" format is accepted.
   */
  public void downloadImageAtUrlAs(String url, String name) {
    log.debug("Downloading image `{}`", url);

    BufferedImage image = this.restTemplate.getForObject(url, BufferedImage.class);
    this.writeImage(image, (name.endsWith(".png")) ? name : name + ".png");
  }

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    List<HttpMessageConverter<?>> converters = new ArrayList<>(0);
    converters.addAll(this.restTemplate.getMessageConverters());
    converters.add(new BufferedImageConverter());
    this.restTemplate.setMessageConverters(converters);
  }

  public String getDestinationDir() {
    return this.destinationDir;
  }

  public void setDestinationDir(String destinationDir) {
    this.destinationDir = destinationDir;
  }

  private void writeImage(BufferedImage image, String name) {
    log.debug("Writing image data to `{}/{}`", this.destinationDir, name);
    File file = new File(this.destinationDir + "/" + name);
    if (file.exists()) {
      file.delete();
    }

    try {
      file.mkdirs();
      file.createNewFile();
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      log.error("Could not create file: `{}`", e.getMessage());
      log.debug(e.toString());
    }
  }

  private class BufferedImageConverter implements HttpMessageConverter<BufferedImage> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
      boolean result = false;

      if (clazz != null) {
        result = clazz.getSimpleName().equals("BufferedImage");
      }

      if (mediaType != null) {
        result = mediaType.includes(MediaType.IMAGE_PNG);
      }

      return result;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
      return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
      return Collections.singletonList(new MediaType("image", "png"));
    }

    @Override
    public BufferedImage read(Class<? extends BufferedImage> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
      return ImageIO.read(inputMessage.getBody());
    }

    @Override
    public void write(BufferedImage bufferedImage, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
      throw new UnsupportedOperationException("Not implemented");
    }
  }

}