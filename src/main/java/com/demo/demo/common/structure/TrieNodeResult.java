package com.demo.demo.common.structure;

import java.util.List;

/**
 * Created by liuyang on 2018/12/2
 * trie树搜索结果
 */
public class TrieNodeResult {
    private int num;
    private List<String> strs;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }

    @Override
    public String toString() {
        return "TrieNodeResult{" +
                "num=" + num +
                ", strs=" + strs +
                '}';
    }
}
