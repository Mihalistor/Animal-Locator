package com.animal.locator.animalLocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@SpringBootApplication
@EnableScheduling
public class AnimalLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimalLocatorApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver r = new CookieLocaleResolver();
		r.setDefaultLocale(Locale.ENGLISH);
		r.setCookieName("localeInfo");
		r.setCookieMaxAge(24 * 60 * 60);
		return r;
	}

	@Bean
	public WebMvcConfigurerAdapter configurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				LocaleChangeInterceptor l = new LocaleChangeInterceptor();
				l.setParamName("localeCode");
				registry.addInterceptor(l);
			}
		};
	}

}
