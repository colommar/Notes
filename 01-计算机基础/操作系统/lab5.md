# **实验5. 请求页式存储管理的模拟**

## **实验内容:**

熟悉虚拟存储器管理的各种页面置换算法，并编写模拟程序实现请求页式存储管理的页面置换算法----最近最少使用算法（**LRU**），要求在每次产生置换时显示页面分配状态和缺页率。

## **实验要求：**

### 1、运行给出的实验程序，查看执行情况，进而分析算法的执行过程，在理解FIFO页面置换算法在程序中的实现后，给出最近最久未使用算法（LRU）置换算法的模拟程序实现，并集成到参考程序中。

> LRU其实很简单，就是一个最近最久未使用算法，没什么花头，实现起来也很方便的，每次使用过的页，他的`struct`都会被刷新，每次刷新的时候，就刷新所有存在的页的最小的那个数字即可。实现难点在于时间复杂度的要求，如果时间复杂度无所谓的话，思路对就能写出来。
> 而，FIFO: 按先进先出顺序替换页面。
> LRU: 根据最近最久未使用算法进行页面替换
 
LRU要想在内存中能够使用，他对于时间复杂度的要求一定是非常高的，要求copy from leetcode
如果get put操作都要求O(1)的话，那么我的struct每次刷新，每次遍历的笨办法显然是不行的。而且leetcode给出的内容是很繁琐的，他还要更新key-value对，于实验要求更新的单纯的key并不相同。
思考了如下的数据结构时间复杂度的实现以辅助我的LRU的O(1)的时间复杂度
- get，put都需要O(1)，我能想到的只有链表，其他数据结构都无法保证put的O(1)
- hashmap用于记录当前页的访问次序，保证访问的时候也是O(1)的
```
请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。  
 实现 LRUCache 类：  
 LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存 int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。 void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。   
 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。   
示例： 输入  
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]  
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]  
输出  
[null, null, null, 1, null, -1, null, -1, 3, 4]  
  
解释  
LRUCache lRUCache = new LRUCache(2);  
lRUCache.put(1, 1); // 缓存是 {1=1}lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}lRUCache.get(1);    // 返回 1lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}lRUCache.get(2);    // 返回 -1 (未找到)  
lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}lRUCache.get(1);    // 返回 -1 (未找到)  
lRUCache.get(3);    // 返回 3lRUCache.get(4);    // 返回 4 提示：  1 <= capacity <= 3000   
 0 <= key <= 10000   
 0 <= value <= 10⁵   
最多调用 2 * 10⁵ 次 get 和 putRelated Topics 设计 哈希表 链表 双向链表 👍 3334 👎 0
```

```cpp
class Node {

public:

    int key;

    int value;

    Node* prev;

    Node* next;

  

    Node(int k = 0, int v = 0) : key(k), value(v) {}

};

  

class LRUCache {

public:

    int capacity;

    Node* dummy;

    unordered_map<int, Node*> key2Node;

    void remove(Node* x) {

        x -> prev -> next = x -> next;

        x -> next -> prev = x -> prev;

    }

  

    void pushFront(Node* x) {

        x->prev = dummy;

        x->next = dummy->next;

        x->prev->next = x;

        x->next->prev = x;

    }

    Node* getNode(int key) {

        auto it = key2Node.find(key);

        if (it == key2Node.end()) {

            return nullptr;

        }

        Node* node = it->second;

        remove(node);

        pushFront(node);

        return node;

    }
    LRUCache(int capacity): capacity(capacity), dummy(new Node()){

        dummy -> prev = dummy;

        dummy -> next = dummy;

    }

    int get(int key) {

        Node* node = getNode(key);

        return node ? node -> value : -1;

    }

    void put(int key, int value) {

        Node* node = getNode(key);

        if(node) {

            node -> value = value;

            return;

        }

        node = new Node(key, value);

        key2Node[key] = node;

        pushFront(node);

        if(key2Node.size() > capacity) {

            node = dummy -> prev;

            key2Node.erase(node -> key);

            remove(node);

            delete node;
        }
    }
};
```

![[resouces/Pasted image 20241219145119.png]]

再回到给出的代码本身，也就是要求我在每次产生置换时显示页面分配状态和缺页率。put，get总数为res，每次换页的时候，计算cnt，cnt/res即为缺页率。

本次实验则更显然，与leetcode的不同，只有put(key)，写起来很简单，我只要将上面的缺页率计算方法加上即可。
FIFO
![[resouces/Pasted image 20241219153359.png]]


### 2、执行2个页面置换模拟程序，分析缺页率的情况。程序运行时，页框数和访问序列长度可调节，在使用同一组访问序列数据的情况下，改变页框数并执行2个页面置换模拟算法的程序，查看缺页率的变化（参考实验4，这里应该包括产生地址访问序列和2个算法选项）。

以FIFO为例


![[resouces/Pasted image 20241219155043.png]]

LRU
![[resouces/Pasted image 20241219161412.png]]

这样修改是没有意义的，并不能看出什么，只能看出一些随机值。
LRU是最近最少使用，FIFO是最早入的，相同的访问序列这里看起来是FIFO更少点。


### 3、在每次产生置换时要求显示分配状态和缺页率。程序的地址访问序列通过随机数产生，且具有足够的长度。页框数和访问序列长度可调节。

也就是说只需要随机生成`capacity`(3, 6), 序列队列的长度(30, 50)，程序的地址也由随机数访问(1, 20)，即可
可以看出有不同

![[resouces/Pasted image 20241219162126.png]]
![[resouces/Pasted image 20241219162201.png]]


## 总结
	这次给的实验还是比较简单的，实现一个lru，队列随机数。leetcode的lru难点在于数据结构的思考，知道采用双向链表和hash之后，问题迎刃而解，我写了好几遍，一直tle，最后看了题解才知道要用双向链表和hash，虽然写起来还是有很多错误，但我相信，现在再让我写lru，我应该会写的很快，对于lru的理解也会很深刻。
	不过，我上网搜了下，Windows似乎用的不是lru，给出的理由是lru过于严格，消耗过高，用的是一个类似于lru的算法。
