/*
 * Copyright (c) 2005, Christian Bauer <christian@hibernate.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the original author nor the names of contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES of MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT MANAGE_ACL OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT of
 * SUBSTITUTE GOODS OR SERVICES; LOSS of USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY of LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT of THE USE of THIS SOFTWARE, EVEN IF ADVISED of
 * THE POSSIBILITY of SUCH DAMAGE.
 */
package jdf.jest.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

/**
 * From http://www.hibernate.org/328.html.
 * 
 * @param <T> transfer object type
 * @param <ID> the type of the Hibernate id - usually {@link Long}
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements IDAO<T, ID> {

	private final Class<T> persistentClass;
	private Session session;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final Criterion... criterion) {
		final Criteria crit = getSession().createCriteria(getPersistentClass());
		for (final Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T exampleInstance,
			final String... excludeProperty) {
		final Criteria crit = getSession().createCriteria(getPersistentClass());
		final Example example = Example.create(exampleInstance);
		for (final String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(final ID id, final boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().load(getPersistentClass(), id,
					LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().load(getPersistentClass(), id);
		}
		return entity;
	}

	@Override
	public void lock(final T entity) {
		if (entity == null) {
			return;
		}
		getSession().buildLockRequest(LockOptions.UPGRADE).lock(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final ID id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	protected Session getSession() {
		if (session == null) {
			throw new IllegalStateException(
					"Session has not been set on DAO before usage");
		}
		return session;
	}

	@Override
	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	// @Override
	// public void update(final T entity) {
	// getSession().update(entity);
	// }

	@Override
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	public void setSession(final Session s) {
		this.session = s;
	}

	@Override
	public void setReadOnly(final T entity, boolean readOnly) {
		session.setReadOnly(entity, readOnly);
	}

	@Override
	public void evict(T entity) {
		session.evict(entity);
	}

	@Override
	public void forceVersIncrPes(T entity) {
		session.buildLockRequest(
				new LockOptions(LockMode.PESSIMISTIC_FORCE_INCREMENT))
				.lock(entity);
	}

	@Override
	public ScrollableResults findAllScroll() {
		return getSession()
				.createCriteria(getPersistentClass())
				.scroll(ScrollMode.FORWARD_ONLY);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findByNaturalId(Class<T> entityClass, String naturalId) {
		SimpleNaturalIdLoadAccess la =
				getSession().bySimpleNaturalId(entityClass);
		return (T) la.load(naturalId);
	}

}
