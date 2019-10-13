package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Question;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import java.util.List;

public class QuestionDAO {


    @Inject
    SessionFactory sf;

    private Session getSession() {
        Session session;
        try {
            session = sf.getCurrentSession();
        } catch (Exception e) {
            session = sf.openSession();
        }
        return session;
    }

    public void insert(Question question) {
        Session session = getSession();
        session.save(question);
    }


    public void update(Integer id, String content) {
        Session session = getSession();
        Question retrievedQuestion = session.get(Question.class, id);
        retrievedQuestion.setContent(content);
        session.update(retrievedQuestion);
    }


    public void delete(Integer id) {
        Session session = getSession();
        Question retrievedQuestion = session.get(Question.class, id);
        session.delete(retrievedQuestion);
    }


    public List<Question> search(String searchString) throws HibernateException {
        Session session = getSession();
        Query<Question> query = session.createQuery("from Question q where q.questionContent like :pContent", Question.class);
        query.setParameter("pContent", searchString);
        return query.getResultList();
    }

    public Question getQuestion(Integer id) {
        Session session = getSession();
        return session.get(Question.class, id);
    }
}
