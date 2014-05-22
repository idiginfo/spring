package org.idiginfo.docsvc.factories;

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nonnull;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Config {

	@Nonnull
	private final AtomicReference<ApplicationContext> storageContext = new AtomicReference<ApplicationContext>();

	@Bean
	public CitagoraFactory storage(@Nonnull ApplicationContext baseContext) {
		ApplicationContext context = getStorageContext(baseContext);
		if (Boolean.getBoolean("use.nosql.storage")) {
			return (CitagoraFactory) context.getBean("factoryImpl");
		} else {
			return (CitagoraFactory) context.getBean("testFactoryImpl");
		}
	}

	@Nonnull
	private ApplicationContext getStorageContext(
			@Nonnull final ApplicationContext baseContext) {
		ApplicationContext result = storageContext.get();
		if (result == null) {
			final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.getEnvironment().setActiveProfiles("storage");
			context.register(StorageConfig.class);
			context.setParent(baseContext);
			context.refresh();
			storageContext.compareAndSet(null, context);
			return storageContext.get();
		} else {
			return result;
		}
	}

	@Configuration
	//@ComponentScan(basePackages = "my.storage")
	@Profile("storage")
	public static class StorageConfig {
	}
}