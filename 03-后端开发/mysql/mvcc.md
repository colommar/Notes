+++
date = '2024-01-21T21:29:49+08:00'
title = 'MVCC'
categories = [
    "mysql",
]
tags = [
    "MVCC"
]
+++


MVCC
====

MVCC (Multi-Version Concurrency Control, 多版本并发控制) 是数据库并发控制机制的一种，用于**无锁**实现事务隔离级别的一种方法。

隔离级别
----

MySQL默认的隔离级别是可重复读（REPEATABLE READ）。而 Oracle 则是提交读（READ COMMITTED）。

表中从上到下，隔离等级依次上升，隔离性越强，并发性越低，也就是效率越低。

| 隔离级别 | 脏读(Dirty Read) | 不可重复读(Non-Repeatable Read) | 幻读(Phantom Read) |
| --- | --- | --- | --- |
| 未提交读(Read Uncommitted) | 可能 | 可能 | 可能 |
| 提交读(Read Committed) | 不可能 | 可能 | 可能 |
| 可重复读(Repeatable Read) | 不可能 | 不可能 | 可能 |
| 串行化(Serializable) | 不可能 | 不可能 | 不可能 |

*   **脏读**：一个事务读到了另一个事务未提交的数据。比如事务a写，事务b读到了事务a的写，然后事务a回滚，这时候事务b读到的就是脏数据。因为要保证串行执行的外显要求，事务b应该读到原先的结果，因为事务a的修改并没有生效。
    
*   **不可重复读**：一个事务在同一行记录上读取两次，第二次读取的结果和第一次读取的结果不同。比如事务a读，事务b写，事务a再读，事务a的两次读取结果不同。而按照串行执行的要求，一个事务独立地两次读取应该结果是一致才对。
    
*   **幻读**：一个事务在同一范围内读取到其他事务插入的数据。事务a在范围内查询，事务b在范围内插入新的数据，事务a再次查询时，会发现多了一些新增的数据。比如事务a进行全表的操作，事务b在范围内插入新的数据，事务a再次查询时，会发现多了一些新增的数据。
    

### 疑问

1.  如何避免脏读？
    
    这个可以修改数据库的隔离级别为 READ COMMITTED，使得事务被提交后才能被其他事务读取。
    
    如果要使用 MVCC 进行实现，那么需要每次查询都在 ReadView 中创建读取数据。
    
2.  如何避免不可重复读？
    
    这个需要加对某条记录或某几条记录或某行记录的锁，具体为读取时的对象，防止其他事务修改或删除。也就是调到 REPEATABLE READ 隔离级别。
    
    MVCC 可以实现，但在同样的查询中，仅有第一次会创建 ReadView。
    
3.  如何避免幻读？
    
    由于幻读是全表操作，所以需要对表加表级别的锁，防止其他事务对表进行插入、删除、更新。也就是调到 SERIALIZABLE 隔离级别。
    
    MVCC 无法实现，只能通过表锁。READ UNCOMMITTED 隔离级别 MVCC 也无法实现，因为但凡加了任何锁，脏读都不会出现了，也就最起码进入了 READ COMMITTED 隔离级别。
    

原理和实现
-----

MVCC 实现原理是基于快照（Snapshot）的，每一个事务都有自己的快照，快照记录了该事务在开始时刻所看到的数据。所以，MVCC 并不是真正的锁机制，而是通过一种类似**快照**和**版本**的机制来实现隔离性。

其实每条记录都被维护了额外的字段，一个用来存储该记录的版本，一个指向上个版本的指针。也就是说利用了类似**链表**的结构，对每条记录的修改都维护了多个版本。那么除了最新的那条数据，以整张表为单位作为快照保存在数据库中，其他的历史版本都被放在了 **undo log** 中。

![alt text](https://z4r1tsu.github.io/_astro/mvcc-undolog.DhJbr071_Z1MV8vS.webp)

### 疑问

那我们说了上面这么多，都是在讲 MVCC 怎么怎么可以回溯到历史版本，重点的事务隔离性呢？以及，为什么不直接上锁来保证事务隔离性呢？

首先对于第二个问题的回答，上互斥锁会导致事务的阻塞，而串行执行，最终导致性能的降低。而MVCC则是一种类似乐观锁的无锁实现，通过对记录的版本进行控制，以此在保证事务隔离性的同时，提高了并发性和效率。

对于第一个问题来说，我举不可重复读那个场景为例子来讲。

按照不可重复读的场景，本来事务a先后两次读的结果应该是相同的，却因为事务b在两次读之间进行了一次修改。

再代入MVCC的版本号机制，事务a的两次读取版本号都是相同的，而事务b的修改版本号，使得事务a第二次读到的版本其实跟事务b修改后的版本一致，跟第一次读得到的版本号不同。

所以我们的策略是开始从 undo log 中找出该记录的历史版本，也就是第一次读的那个版本，因为这两次读是同一次事务发生的，所以事务b的修改对事务a来说是不可见的。我们按照记录的上个版本指针开始回推，直到找到一个版本号比事务b修改版本号小的版本，然后返回该版本号对应的记录。

### ReadView

ReadView 就是实现上述回溯的过程和原理的一个数据结构。

在事务开始时，会创建一个 ReadView，ReadView 记录了该事务在开始时刻所看到的数据。ReadView 包含了当前事务的最新版本号，以及一个指向 undo log 中最新版本的指针。

当事务要读取某个记录时，会先判断该记录的最新版本号是否在 ReadView 中，如果在，则直接返回该记录；如果不在，则需要回溯到该记录的历史版本。

回溯的过程就是从 undo log 中找出该记录的历史版本，直到找到一个版本号比 ReadView 中最新版本号小的版本，然后返回该版本号对应的记录。

  