package fr.epita.quiz.tests.jpa;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.QuestionDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestJPAEntityManager {

    @PersistenceContext
    EntityManager em;

    @Inject
    QuestionDAO qd;

    @Test
    @Transactional
    public  void testEM() {
        String questionContent = "How to define a property in a maven pom file?";
        Question qu = new Question(questionContent);
        em.persist(qu);

        Question question = em.find(Question.class, qu.getId());

        Assert.assertNotNull(question);
        Assert.assertEquals(questionContent, question.getContent());
    }

    @Test
    @Transactional
    public void testInsert() {
        String questionContent = "How to define a property in a maven pom file?";
        Question qu = new Question(questionContent);

        qd.insert(qu);

        Question retrievedQuestion = qd.getById(qu.getId(), Question.class);
        Assert.assertNotNull(retrievedQuestion);
        Assert.assertEquals(questionContent, retrievedQuestion.getContent());
    }

    @Test
    @Transactional
    public void testUpdate() {
        String questionContent = "How to define a property in a maven pom file?";
        Question qu = new Question(questionContent);
        em.persist(qu);

        String updatedContent = "What is dependency injection?";
        qu.setContent(updatedContent);
        qd.update(qu);

        Question retrievedQuestion = qd.getById(qu.getId(), Question.class);
        Assert.assertEquals(updatedContent, retrievedQuestion.getContent());
    }

    @Test
    @Transactional
    public void testDelete() {
        String questionContent = "How to define a property in a maven pom file?";
        Question qu = new Question(questionContent);
        em.persist(qu);

        qd.delete(qu);

        Question retrievedQuestion = qd.getById(qu.getId(), Question.class);
        Assert.assertNull(retrievedQuestion);
    }

    @Test
    @Transactional
    public void testSearch() {
        String questionContent = "How to define a property in a maven pom file?";
        Question qu = new Question(questionContent);
        em.persist(qu);

        String searchCriteria = "%maven%";
        Question criteria = new Question(searchCriteria);

        List<Question> results = qd.search(criteria);

        Assert.assertTrue(results.size() > 0);
    }
}
