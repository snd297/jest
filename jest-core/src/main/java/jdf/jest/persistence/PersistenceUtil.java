package jdf.jest.persistence;

import javax.annotation.Nullable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PersistenceUtil {

	private static Logger logger =
			LoggerFactory.getLogger(PersistenceUtil.class);

	public static void commit(@Nullable Session sess) {
		if (sess != null && sess.isOpen()) {
			try {
				sess.getTransaction().commit();
			} catch (Exception e) {
				logger.error("exception committing trx", e);
			}
		}
	}

	public static void commit(@Nullable Transaction trx) {
		if (trx != null && trx.isActive()) {
			try {
				trx.commit();
			} catch (Exception e) {
				logger.error("exception committing trx", e);
			}
		}
	}

	public static void close(@Nullable Session sess) {
		if (sess != null && sess.isOpen()) {
			try {
				sess.close();
			} catch (Exception e) {
				logger.error("exception closing session", e);
			}
		}
	}

	public static void rollback(@Nullable Transaction trx) {
		if (trx != null && trx.isActive()) {
			try {
				trx.rollback();
			} catch (Exception e) {
				logger.error("exception rolling back trx", e);
			}
		}
	}

	private PersistenceUtil() {}
}
