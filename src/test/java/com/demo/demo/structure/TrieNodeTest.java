package com.demo.demo.structure;

import com.demo.demo.common.structure.TrieNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuyang on 2018/12/2
 */
public class TrieNodeTest {

    @Test
    public void test1() {
        TrieNode node = TrieNode.createRoot(20);
        List<String> list = Arrays.asList("abc", "abc", "abcd", "abcdefg", "def", "ghijk", "bbq");
        list.forEach(node::add);
        System.out.println(node.check("abc"));
//        node.delete("abc");
//        System.out.println(node.check("abc"));
    }

    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        list.add(4);
        System.out.println(list);
    }
}
