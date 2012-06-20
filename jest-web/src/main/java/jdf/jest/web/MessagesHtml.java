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

import javax.transaction.Transaction;

import jdf.jest.model.Messages;

import org.hibernate.Session;

import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author Sam Donnelly
 * 
 */
@At("/messages")
public class MessagesHtml {

	private Messages messages = new Messages("Hello sailor!");

	@Post
	public void postEntry() {
		Session sess = null;
		Transaction trx = null;
		try {

		} finally {

		}
	}

	@Get
	public void listBlogs() {
		// this.blogs = .. ; //fetch from store
	}
	// get + set methods
}
