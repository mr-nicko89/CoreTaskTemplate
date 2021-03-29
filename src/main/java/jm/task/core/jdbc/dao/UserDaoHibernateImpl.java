package jm.task.core.jdbc.dao;

import org.hibernate.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    void createSQLQueryExecuteUpdate(String sql) throws Exception {
        Session session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
        session.close();

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
            createSQLQueryExecuteUpdate(INSERT_NEW);
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
            createSQLQueryExecuteUpdate(INSERT_NEW);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();

            User user;
            user = (User) session.load(User.class, id);
            session.delete(user);

            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try {
            Session session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            users = (List<User>) query.list();

            transaction.commit();
            session.close();
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
            createSQLQueryExecuteUpdate(INSERT_NEW);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}