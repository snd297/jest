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
import java.util.List;

import javax.annotation.Nullable;

import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;

/**
 * A DAO interface.
 * 
 * @param <T> type of the transfer object.
 * @param <ID> the type of the edu.upenn.cis.braintrust.persistence id.
 */
public interface IDAO<T, ID extends Serializable> {

	/**
	 * Retrieve all <code>T</code>s.
	 * 
	 * @return all persisted <code>T</code>s
	 */
	List<T> findAll();

	/**
	 * Do a find by example with the given example instance and properties to
	 * exclude.
	 * 
	 * @param exampleInstance the example instance
	 * @param excludeProperty properties to exclude in the find by example
	 * @return the results of the search
	 */
	List<T> findByExample(T exampleInstance, String... excludeProperty);

	/**
	 * Given a edu.upenn.cis.braintrust.persistence id <code>id</code>, retrieve
	 * the corresponding <code>T</code>.
	 * 
	 * @param id see description
	 * @param lock use an upgrade lock. Objects loaded in this lock mode are
	 *            materialized using an SQL <tt>select ... for update</tt>.
	 * @return the retrieved object
	 * 
	 * @throws HibernateException if there is no entity with the given id
	 */
	T findById(ID id, boolean lock);

	void flush();

	/**
	 * Make <code>entity</code> persistent.
	 * 
	 * @param entity the entity object
	 */
	void saveOrUpdate(T entity);

	/**
	 * Make the given entity transient. That is, delete <code>entity</code>.
	 * 
	 * @param entity to be made transient
	 */
	void delete(T entity);

	/**
	 * Returns {@code null} if there is no entity with the given id.
	 * 
	 * @param id the id
	 * @return
	 */
	T get(final ID id);

	void setReadOnly(final T entity, boolean readOnly);

	void evict(T entity);

	void forceVersIncrPes(T entity);

	// void update(T entity);

	ScrollableResults findAllScroll();

	void clear();

	void lock(@Nullable final T entity);

	T findByNaturalId(Class<T> entityClass, String naturalId);
}
