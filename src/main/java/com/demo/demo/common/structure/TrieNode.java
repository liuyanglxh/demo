package com.demo.demo.common.structure;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyang on 2018/12/1
 * 思路：
 * 1.把单词拆分成字符数组
 * 2.从跟节点开始，判断第一个字符是否已经存在：
 * 如果存在，则将第二个字符插入这个子节点；以此类推；
 * 如果不存在，那么新建一个子节点放进去；
 * 3.插入的过程中，如果遇到了最后一个字符，那么就将这个节点的isData设置为true，
 * 表示以这个节点结束的字符串是一个完整的单词；
 * 4.根节点不存储任何数据，只是作为一个统一的入口
 */
public class TrieNode {

    private int num;//通过这个节点的总数
    private int duplicate;//字符串的重复次数
    private TrieNode parentNode;//父节点
    private Map<Character, TrieNode> subNodes;//子节点
    private char value;//当前节点存储的值
    private boolean isData;//是否有以这个词结尾的单词
    private int maxCapacity;//check时，返回的最大数量

    private TrieNode() {
    }

    /**
     * 获取一个根节点
     */
    public static TrieNode createRoot(int maxCapacity) {
        TrieNode node = new TrieNode();
        node.maxCapacity = maxCapacity;
        return node;
    }

    /**
     * 查找
     */
    public TrieNodeResult check(String str) {
        TrieNodeResult result = new TrieNodeResult();
        List<String> list = new ArrayList<>();
        result.setStrs(list);
        if (StringUtils.isEmpty(str)) {
            return result;
        }

        char c = str.toCharArray()[0];
        TrieNode node = subNodes.get(c);
        if (node != null) {
            TrieNode check = node.getNode(str.toCharArray(), 0);
            if (check != null) {
                list.add(str);
                check.subNodes.values().forEach(subNode -> subNode.addStr(list, subNode, str, maxCapacity));
                result.setNum(check.num);
            }
        }

        return result;
    }

    /**
     * 把一个node下的所有可能出现的组合返回去
     *
     * @param maxCapacity 最大容量，如果list超过这个值则不再继续返回
     */
    private void addStr(List<String> list, TrieNode node, String prefix, int maxCapacity) {
        if (list.size() >= maxCapacity) {
            return;
        }
        prefix = prefix + value;
        if (isData) {
            list.add(prefix);
        }
        if (subNodes != null) {
            for (TrieNode subNode : subNodes.values()) {
                subNode.addStr(list, subNode, prefix, maxCapacity);
            }
        }
    }

    /**
     * 找到对应的节点
     */
    private TrieNode getNode(char[] chars, int index) {
        boolean isEnd = index == chars.length - 1;
        if (isEnd) {
            return this;
        }
        char c = chars[++index];
        TrieNode node = subNodes.get(c);
        if (node == null) {
            return null;
        }
        return node.getNode(chars, index);
    }

    /**
     * 添加
     */
    public boolean add(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        if (subNodes == null) {
            subNodes = new HashMap<>();
        }
        char[] chars = str.toCharArray();
        subNodes.putIfAbsent(chars[0], new TrieNode());
        return subNodes.get(chars[0]).add(chars, 0);
    }

    /**
     * 添加
     *
     * @param index 添加的位置
     */
    private boolean add(char[] chars, int index) {
        //设置当前节点的值
        value = chars[index];

        //当前是否已经到达单词的结尾处
        boolean isEnd = index == chars.length - 1;
        //设置当前节点的值
        if (isEnd) {
            duplicate++;
            if (isData) {
                return false;
            }
            isData = true;
            num++;
            return true;
        }

        //当前不是字符串的尾巴，并且当前节点不包含子节点
        if (subNodes == null) {
            subNodes = new HashMap<>();
        }
        //下一个字符
        char c = chars[++index];
        boolean addNewString;
        if (subNodes.containsKey(c)) {
            addNewString = subNodes.get(c).add(chars, index);
        } else {
            TrieNode node = new TrieNode();
            node.parentNode = this;
            subNodes.put(c, node);
            addNewString = node.add(chars, index);
        }
        if (addNewString) {
            num++;
        }
        return addNewString;
    }

    /**
     * 删除
     * 1.将重复数字减一，
     * 2.如果减少之后为0
     * 如果它还有字节点，那么就将isData设置为false
     * 如果没有子节点，就删除
     */
    public void delete(String str) {
//        if (StringUtils.isEmpty(str)) {
//            return;
//        }
//        char[] chars = str.toCharArray();
//        char c = chars[0];
//        if (!subNodes.containsKey(c)) {
//            return;
//        }
//        TrieNode node = subNodes.get(c);
//        TrieNode check = node.check(chars, 0);
//        if (check != null && check.isData) {
//            check.duplicate--;
//            if (check.duplicate == 0) {
//                check.isData = false;
//                TrieNode parent = check;
//                while (parent.parentNode != null) {
//                    parent.num--;
//                    parent = parent.parentNode;
//                }
//            }
//        }
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "value=" + value +
                '}';
    }
}
