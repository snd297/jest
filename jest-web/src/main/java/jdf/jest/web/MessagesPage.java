/*******************************************************************************
 * Copyright 2012 Trustees of the University of Pennsylvania
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jdf.jest.web;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.List;

import jdf.jest.model.Message;
import jdf.jest.model.Messages;
import jdf.jest.persistence.PersistenceUtil;

import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author Sam Donnelly
 * 
 */
@At("/messages")
public class MessagesPage {

	private Messages messages = new Messages("Hello sailor!");
	private Message newMessage = new Message();

	private Session sess;

	@Inject
	MessagesPage(Session sess) {
		this.sess = sess;
	}

	@Post
	public void postEntry() {
		try {
			sess.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Messages> messages =
					sess.createQuery("from Messages m").list();
			Messages ms = null;
			if (messages.size() == 0) {
				ms = new Messages("Hello sailor!");
				sess.save(ms);
			} else {
				ms = getOnlyElement(messages);
			}
			ms.addMessage(newMessage);
			sess.getTransaction().commit();
		} catch (Exception e) {
			PersistenceUtil.rollback(sess.getTransaction());
		} finally {
			PersistenceUtil.close(sess);
		}
	}

	@Get
	public void listBlogs() {
		try {
			sess.beginTransaction();
			List<Messages> messages =
					sess.createQuery("from Messages m").list();
			
			sess.getTransaction().commit();

		} finally {
			PersistenceUtil.commit(sess);
			PersistenceUtil.close(sess);
		}
	}
	// get + set methods
}
