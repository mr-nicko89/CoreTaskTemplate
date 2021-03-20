package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.hql.internal.antlr.SqlTokenTypes.MAX;
import static org.hibernate.hql.internal.antlr.SqlTokenTypes.MIN;

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
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
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
        // start the transaction
        transaction = session.beginTransaction();

        User user = new User(name, lastName, age);
        // save student object
        session.save(user);

        // commit transction
        transaction.commit();

    }

    @Override
    public void removeUserById(long id) {
        transaction = session.beginTransaction();
        User user;

        user = (User) session.load(User.class, id);
        session.delete(user);

        //This makes the pending delete to be done
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try {
            // start the transaction

            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            users = query.list();

            // commit transction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        final String INSERT_NEW = "DELETE FROM users";
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
}