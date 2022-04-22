package org.example.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Account;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountDAO {
    public static Set<Account> getAllAccounts() {
        Set<Account> res = new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> query = builder.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            query.select(root);
            List<Account> accounts = session.createQuery(query).getResultList();

            res.addAll(accounts);
        }
        return res;
    }

    public static Account getAccountById(int id) {
        Account res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> query = builder.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("id").as(Integer.class), id);
            query.where(p1);

            List<Account> accounts = session.createQuery(query).getResultList();

            if (accounts.size() == 1)
                res = accounts.get(0);
        }
        return res;
    }

    public static Account getAccountByUsername(String username) {
        Account res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> query = builder.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("username").as(String.class), username);
            query.where(p1);

            List<Account> accounts = session.createQuery(query).getResultList();

            if (accounts.size() == 1)
                res = accounts.get(0);
        }
        return res;
    }

    public static void createAccount(String username, String password, boolean isStudent) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray()); // Hash password
            Account account = new Account(username, hashedPassword, isStudent);

            session.persist(account);

            session.getTransaction().commit();
        }
    }

    // Kiểm tra password có đúng ko
    public static boolean checkPassword(String username, String password) {
        Account account = getAccountByUsername(username);
        if (account != null) {
            String hashedPassword = account.getPassword();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
            return result.verified;
        }
        return false;
    }

    // Kiểm tra 1 tài khoản có tồn tại trong database hay không thông qua username
    public static boolean isAccountExists(String username) {
        Account account = getAccountByUsername(username);
        return account != null;
    }

    // Kiểm tra mật khẩu cũ trước khi gọi hàm
    public static void changePassword(String username, String newPassword) {
        String newHashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray()); // Hash password
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            Account account = getAccountByUsername(username);
            session.evict(account);
            account.setPassword(newHashedPassword);
            session.update(account);

            session.getTransaction().commit();
        }
    }
}
