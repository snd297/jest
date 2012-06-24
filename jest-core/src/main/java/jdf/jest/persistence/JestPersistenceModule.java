package jdf.jest.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

public class JestPersistenceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SessionFactory.class)
				.toProvider(SessionFactoryProvider.class)
				.asEagerSingleton();
	}

	@Provides
	@RequestScoped
	Session provideSession(final SessionFactory sf) {
		return sf.openSession();
	}

}
