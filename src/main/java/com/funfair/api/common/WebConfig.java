package com.funfair.api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${app.upload.user-image-path}")
	private String userImagePath;
	@Value("${app.upload.event-thumbnail-image-path}")
	private String eventThumbnailImagePath;
	@Value("${app.upload.event-Banner-image-path}")
	private String bannerImagePath;
	@Value("${app.upload.event-gallery-image-path}")
	private String galleryImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user-image/**")
        .addResourceLocations(userImagePath.startsWith("file:") ? userImagePath : "file:" + userImagePath);
        
        registry.addResourceHandler("/thumbnail-image/**")
        .addResourceLocations("file:" + eventThumbnailImagePath);
        
        registry.addResourceHandler("/banner-image/**")
        .addResourceLocations("file:" + bannerImagePath);
        
        registry.addResourceHandler("/gallery-image/**")
        .addResourceLocations("file:" + galleryImagePath);
 }
    
}
