package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
//    private HibernateUtil worker = new HibernateUtil();

    public UserDaoHibernateImpl() {

    }

    void createSQLQueryExecuteUpdate(String sql) {
        transaction = session.getTransaction();
        transaction.begin();
        session.createSQLQuery(sql).executeUpdate();

    }

    @Override
    public void createUsersTable() {
        final String INSERT_NEW = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `lastName` varchar(255) NOT NULL,\n" +
                "  `age` tinyint NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8";
        try {
            // start the transaction
            createSQLQueryExecuteUpdate(INSERT_NEW);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        final String INSERT_NEW = "DROP TABLE  IF EXISTS users";
        try {
            // start the transaction
            createSQLQueryExecuteUpdate(INSERT_NEW);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        // auto close session object
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();

            // save student object
//            User user = new User(name,lastName,age);
//            session.save(user);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
/*
package net.javaguides.hibernate.dao;

        import org.hibernate.Session;
        import org.hibernate.Transaction;

        import net.javaguides.hibernate.model.Student;
        import net.javaguides.hibernate.util.HibernateUtil;

public class StudentDao {
    public void saveStudent(Student student) {

        Transaction transaction = null;

        // auto close session object
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save student object
            session.save(student);

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}*/
