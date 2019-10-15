package fr.epita.quiz.tests.jpa;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.QuestionDAO;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestJPAFromDAO {

    @Inject
    QuestionDAO questionDAO;

    @Test
    public void testInsert() {
        Question qu = new Question("What is Dependency Injection?");
        //don't create new DAO; if something is injected into the created instance then it has to be created through spring otherwise the injected fields will not be injected
        questionDAO.insert(qu);
        //need to assert something
        Question retrievedQuestion = questionDAO.getById(qu.getId(), Question.class);
        Assert.assertNotNull(retrievedQuestion);
    }

    @Test
    public void testUpdate() {
        Question qu = new Question("What is Dependency Injection?");
        questionDAO.insert(qu);

        String update = "What does ORM stand for?";
        qu.setContent(update);
        questionDAO.update(qu);
        //need to assert something
        Question retrievedQuestion = questionDAO.getById(qu.getId(), Question.class);
        Assert.assertEquals(update, retrievedQuestion.getContent());
    }

    @Test
    public void testDelete() {
        Question qu = new Question("What is Dependency Injection?");
        questionDAO.insert(qu);

        questionDAO.delete(qu);
        //need to assert something
        Question retrievedQuestion = questionDAO.getById(qu.getId(), Question.class);
        Assert.assertNull(retrievedQuestion);
    }

    @Test
    public void testSearch() throws HibernateException {
        Question qu = new Question("Where are maven dependencies set?");
        questionDAO.insert(qu);

        List<Question> resultList = questionDAO.search(qu);
        Assert.assertTrue(resultList.size() > 0);
    }
}
