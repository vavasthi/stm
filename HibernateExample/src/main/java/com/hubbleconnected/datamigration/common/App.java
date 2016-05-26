package com.hubbleconnected.datamigration.common;

import com.hubbleconnected.datamigration.persistence.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

public class App
{
  public static void main( String[] args )
  {
    System.out.println("Maven + Hibernate + MySQL");
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();
    SQLQuery query = session.createSQLQuery("select count(*) from users");
    List results = query.list();
    System.out.println(query.getQueryString() + "Count = " + results.size() + " Output = " + results.get(0));
    session.getTransaction().commit();
  }
}
