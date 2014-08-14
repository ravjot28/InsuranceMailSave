package com.rav.dao;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.rav.bean.MessageInfo;

public class SaveMailDAO {

	public void saveMessages(List<MessageInfo> messages) {
		Session session = null;
		try {

			session = DatabaseConfig.getSessionFactory().openSession();

			Transaction tx = (Transaction) session.beginTransaction();
			int i = 0;
			for (MessageInfo mi : messages) {

				session.save(mi);
				if (i % 50 == 0) { 
					session.flush();
					session.clear();
				}
				i++;
			}

			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

	}

}
