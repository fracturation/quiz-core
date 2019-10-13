package fr.epita.quiz.tests.jpa;

import fr.epita.quiz.datamodel.Question;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestJPA {

    @Inject
    SessionFactory sf;

    @Test
    public void testHibernateSession() {
        Session session = sf.openSession();

        Assert.assertEquals(true, session.isConnected());
    }

    @Test
    public void testCreate() {
        //given
        Question qu = new Question("How to define a property in a maven pom file?");

        //when
        Session session = sf.openSession();
        session.save(qu);

        //then
        Question retrievedQuestion = session.get(Question.class, qu.getId());
        Assert.assertNotNull(retrievedQuestion);
    }

    @Test
    public void testUpdate() { //unfinished
        //given
        Question qu = new Question("How to define a property in a maven pom file?");
        Session session = sf.openSession();
        session.save(qu);

        //when
        qu.setContent("What are transitive dependencies?");
        session.update(qu);

        //then
        Question retrievedQuestion = session.get(Question.class, qu.getId());
        Assert.assertEquals("What are transitive dependencies?", retrievedQuestion.getContent());
    }

    @Test
    public void testDelete() {
        //given
        Question qu = new Question("How to define a property in a maven pom file?");
        Session session = sf.openSession();
        session.save(qu);

        //when
        session.delete(qu);

        //then
        Assert.assertNull(session.get(Question.class, qu.getId()));
    }

    @Test
    public void testSearch() throws HibernateException {
        //given
        Question qu = new Question("How to define a property in a maven pom file?");
        Question qu2 = new Question("How to search for a question?");
        Session session = sf.openSession();
        session.save(qu);
        session.save(qu2);

        //when
        Query<Question> query = session.createQuery("from Question q where q.questionContent like :pContent", Question.class);
        query.setParameter("pContent", "%maven%");
        List<Question> resultList = query.getResultList();

        //then
        //resultList.stream().forEach(q -> q.getQuestionContent().contains("maven"));

        Assert.assertTrue(resultList.size() > 0);
    }
}
