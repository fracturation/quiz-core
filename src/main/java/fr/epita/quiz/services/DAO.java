package fr.epita.quiz.services;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DAO<T> {

    /*@Inject
    SessionFactory sf;*/

    @PersistenceContext
    EntityManager em;

    /*protected Session getSession() {
        Session session;
        try {
            session = sf.getCurrentSession();
        } catch (Exception e) {
            session = sf.openSession();
        }
        return session;
    }

    public void insert(T t) {
        Session session = getSession();
        session.save(t);
    }

    public void delete(T t) {
        Session session = getSession();
        session.delete(t);
    }

    public void update(T t) {
        Session session = getSession();
        session.update(t);
    }

    public List<T> search(T criteria) { //need to finish
        Session session = getSession();
        Query<T> query = session.createQuery(getQueryString());
        Map<String, Object> parameters = new LinkedHashMap<>();
        fillParametersMap(parameters, criteria);
        parameters.forEach((k,v) -> query.setParameter(k,v));
        return query.getResultList();
    }*/

    public T getById(Serializable id, Class<T> clazz) {
        return em.find(clazz, id); //or can set a field that stores the class
    }

    @Transactional
    public void insert(T t) {
        em.persist(t);
    }

    @Transactional
    public void update(T t) {
        em.merge(t);
    }

    @Transactional
    public void delete(T t) {
        em.detach(t);
    }

    @Transactional
    public List<T> search(T criteria) {
        Query query = em.createQuery(getQueryString());
        Map<String, Object> parameters = new LinkedHashMap<>();
        fillParametersMap(parameters, criteria);
        parameters.forEach((k, v) -> query.setParameter(k, v));
        return query.getResultList();
    }

    public abstract String getQueryString();
    public abstract void fillParametersMap(Map<String, Object> map, T t);
}
