package jdf.jest.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;

public class JestConfig extends GuiceServletContextListener {

	@Override
	public Injector getInjector() {
		return Guice.createInjector(new SitebricksModule() {
			@Override
			protected void configureSitebricks() {
				// scan class Example's package and all descendants
				scan(Example.class.getPackage());
			}
		});
	}
}
