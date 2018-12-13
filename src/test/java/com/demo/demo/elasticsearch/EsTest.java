package com.demo.demo.elasticsearch;

import com.demo.demo.Application;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by liuyang on 2018/11/9
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EsTest {

    private String text = "吃葡萄不塌不吐葡萄皮";

    @Test
    public void testBase(){
        Result result = BaseAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            System.out.println(term);
        }
    }

    @Test
    public void testTo(){
        Result result = ToAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            System.out.println(term);
        }
    }

    @Test
    public void testDic(){
        Result result = DicAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            System.out.println(term);
        }
    }

    @Test
    public void testIndex(){
        Result result = IndexAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            System.out.println(term);
        }
    }

    @Test
    public void testNlp(){
        Result result = NlpAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (Term term : terms) {
            System.out.println(term);
        }
    }

}
