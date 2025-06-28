https://doc.rust-lang.org/std/string/struct.String.html#method.trim
# string 
```rust
// strings4.rs

//

// Ok, here are a bunch of values-- some are `String`s, some are `&str`s. Your

// task is to call one of these two functions on each value depending on what

// you think each value is. That is, add either `string_slice` or `string`

// before the parentheses on each line. If you're right, it will compile!

//

// No hints this time!

  
  

fn string_slice(arg: &str) {

    println!("{}", arg);

}

fn string(arg: String) {

    println!("{}", arg);

}
  

fn main() {

    string_slice("blue");

    string("red".to_string());

    string(String::from("hi"));

    string("rust is fun!".to_owned());
	
    string("nice weather".into());

    string(format!("Interpolation {}", "Station"));

    string_slice(&String::from("abc")[0..1]);

    string_slice("  hello there ".trim());

    string("Happy Monday!".to_string().replace("Mon", "Tues"));

    string("mY sHiFt KeY iS sTiCkY".to_lowercase());

}
```

# option
```rust
// options2.rs

//

// Execute `rustlings hint options2` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

#[cfg(test)]

mod tests {

    #[test]

    fn simple_option() {

        let target = "rustlings";

        let optional_target = Some(target);

  

        // TODO: Make this an if let statement whose value is "Some" type

        if let Some(word) = optional_target {

            assert_eq!(word, target);

        }

    }

  

    #[test]

    fn layered_option() {

        let range = 10;

        let mut optional_integers: Vec<Option<i8>> = vec![None];

  

        for i in 1..(range + 1) {

            optional_integers.push(Some(i));

        }

  

        let mut cursor = range;

  

        // TODO: make this a while let statement - remember that vector.pop also

        // adds another layer of Option<T>. You can stack `Option<T>`s into

        // while let and if let.

        while let Some(Some(integer)) = optional_integers.pop() {

            assert_eq!(integer, cursor);

            cursor -= 1;

        }

  

        assert_eq!(cursor, 0);

    }

}
```
* 我们同时处理了由`pop`方法返回的`Option`和向量中元素的`Option`。这样就能直接获取到向量中的`Some(i)`值，并与`cursor`进行比较。当`pop`返回`None`时，表示向量已空，循环结束。这段代码应该能通过测试。
```rust
// options3.rs

//

// Execute `rustlings hint options3` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

struct Point {

    x: i32,

    y: i32,

}

  

fn main() {

    let y: Option<Point> = Some(Point { x: 100, y: 200 });

  

    match &y {

        Some(p) => println!("Co-ordinates are {},{} ", p.x, p.y),

        _ => panic!("no match!"),

    }

    y; // Fix without deleting this line.

}
```
* 这个是`&y`
```rust
// options3.rs

//

// Execute `rustlings hint options3` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

struct Point {

    x: i32,

    y: i32,

}

  

fn main() {

    let y: Option<Point> = Some(Point { x: 100, y: 200 });

  

    match &y {

        Some(p) => println!("Co-ordinates are {},{} ", p.x, p.y),

        _ => panic!("no match!"),

    }

    y; // Fix without deleting this line.

}
```
* 这个是`ref p`
#  泛型
```rust
// traits4.rs

//

// Your task is to replace the '??' sections so the code compiles.

//

// Don't change any line other than the marked one.

//

// Execute `rustlings hint traits4` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

pub trait Licensed {

    fn licensing_info(&self) -> String {

        "some information".to_string()

    }

}

  

struct SomeSoftware {}

  

struct OtherSoftware {}

  

impl Licensed for SomeSoftware {}

impl Licensed for OtherSoftware {}

  

// YOU MAY ONLY CHANGE THE NEXT LINE

fn compare_license_types<T:Licensed, U: Licensed>(software: T, software_two: U) -> bool {

    software.licensing_info() == software_two.licensing_info()

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn compare_license_information() {

        let some_software = SomeSoftware {};

        let other_software = OtherSoftware {};

  

        assert!(compare_license_types(some_software, other_software));

    }

  

    #[test]

    fn compare_license_information_backwards() {

        let some_software = SomeSoftware {};

        let other_software = OtherSoftware {};

  

        assert!(compare_license_types(other_software, some_software));

    }

}
```

> 这个修改让`compare_license_types`函数能够接收任何实现了`Licensed` trait的类型作为参数，无论它们是`SomeSoftware`还是`OtherSoftware`，或者是任何其他实现了该trait的类型。这提高了函数的灵活性和可重用性。

```rust
// traits5.rs

//

// Your task is to replace the '??' sections so the code compiles.

//

// Don't change any line other than the marked one.

//

// Execute `rustlings hint traits5` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

pub trait SomeTrait {

    fn some_function(&self) -> bool {

        true

    }

}

  

pub trait OtherTrait {

    fn other_function(&self) -> bool {

        true

    }

}

  

struct SomeStruct {}

struct OtherStruct {}

  

impl SomeTrait for SomeStruct {}

impl OtherTrait for SomeStruct {}

impl SomeTrait for OtherStruct {}

impl OtherTrait for OtherStruct {}

  

// YOU MAY ONLY CHANGE THE NEXT LINE

fn some_func<T: OtherTrait + SomeTrait>(item: T) -> bool {

    item.some_function() && item.other_function()

}

  

fn main() {

    some_func(SomeStruct {});

    some_func(OtherStruct {});

}
```
```bash
error[E0599]: no method named `some_function` found for type parameter `T` in the current scope
  --> exercises/traits/traits5.rs:34:10
   |
33 | fn some_func<T: OtherTrait>(item: T) -> bool {
   |              - method `some_function` not found for this type parameter
34 |     item.some_function() && item.other_function()
   |          ^^^^^^^^^^^^^
   |
   = help: items from traits can only be used if the type parameter is bounded by the trait
help: the following trait defines an item `some_function`, perhaps you need to restrict type parameter `T` with it:
   |
33 | fn some_func<T: OtherTrait + SomeTrait>(item: T) -> bool {
   |                            +++++++++++
help: there is a method with a similar name
   |
34 |     item.other_function() && item.other_function()
   |          ~~~~~~~~~~~~~~

error: aborting due to 1 previous error

For more information about this error, try `rustc --explain E0599`.
```
> fn some_func<T: OtherTrait + SomeTrait> 
> '+'支持更多泛型

```rust
// quiz3.rs

//

// This quiz tests:

// - Generics

// - Traits

//

// An imaginary magical school has a new report card generation system written

// in Rust! Currently the system only supports creating report cards where the

// student's grade is represented numerically (e.g. 1.0 -> 5.5). However, the

// school also issues alphabetical grades (A+ -> F-) and needs to be able to

// print both types of report card!

//

// Make the necessary code changes in the struct ReportCard and the impl block

// to support alphabetical report cards. Change the Grade in the second test to

// "A+" to show that your changes allow alphabetical grades.

//

// Execute `rustlings hint quiz3` or use the `hint` watch subcommand for a hint.

  
  

pub struct ReportCard <T>{

    pub grade: T,

    pub student_name: String,

    pub student_age: u8,

}

  

impl <T: std::fmt::Display> ReportCard <T>{

    pub fn print(&self) -> String {

        format!("{} ({}) - achieved a grade of {}",

            &self.student_name, &self.student_age, &self.grade)

    }

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn generate_numeric_report_card() {

        let report_card = ReportCard {

            grade: 2.1,

            student_name: "Tom Wriggle".to_string(),

            student_age: 12,

        };

        assert_eq!(

            report_card.print(),

            "Tom Wriggle (12) - achieved a grade of 2.1"

        );

    }

  

    #[test]

    fn generate_alphabetic_report_card() {

        // TODO: Make sure to change the grade here after you finish the exercise.

        let report_card = ReportCard {

            grade: "A+",

            student_name: "Gary Plotter".to_string(),

            student_age: 11,

        };

        assert_eq!(

            report_card.print(),

            "Gary Plotter (11) - achieved a grade of A+"

        );

    }

}
```

>  在 Rust 中，`impl <T: std::fmt::Display> ReportCard <T> {` 这行代码是在为 `ReportCard<T>` 结构体实现方法的部分，其中 `T` 是一个泛型类型参数。这里的 `<T: std::fmt::Display>` 是对泛型类型 `T` 的一个约束（也称作“trait bound”），指定 `T` 必须实现了 `std::fmt::Display` 这个 trait。`std::fmt::Display` 是标准库中定义的一个 trait，它要求类型实现一个返回格式化后字符串的 `fmt` 方法，这个方法通常用于更友好的显示输出。
> 这种做法的好处在于它为 `ReportCard` 结构体提供了灵活性，使得它可以接受任何实现了 `std::fmt::Display` 的类型作为其 `grade` 字段的值。`std::fmt::Display` trait 确保了无论 `grade` 字段的具体类型是什么，它都可以被转化成一个字符串，从而能够通过 `print` 方法生成一个格式化的报告字符串。
> 简而言之，这里的 `impl <T: std::fmt::Display> ReportCard <T>` 表示：
> `impl` 关键字开始了一个 impl 块，用于定义 `ReportCard<T>` 的方法。
> `<T: std::fmt::Display>` 在这个 impl 块中引入了一个泛型类型 `T`，并对 `T` 施加了一个约束，即它必须实现 `std::fmt::Display` trait。
> 这样一来，当 `ReportCard` 的 `print` 方法被调用时，无论 `grade` 是数字类型还是字符串类型（或者任何其他实现了 `std::fmt::Display` 的类型），都可以将其格式化为字符串并嵌入到返回的报告字符串中。
> 通过使用泛型和 trait 约束，这段代码有效地增加了 `ReportCard` 结构体的通用性和灵活性，能够支持不同类型的成绩表示方法。

# 生命周期
```rust
// lifetimes1.rs

//

// The Rust compiler needs to know how to check whether supplied references are

// valid, so that it can let the programmer know if a reference is at risk of

// going out of scope before it is used. Remember, references are borrows and do

// not own their own data. What if their owner goes out of scope?

//

// Execute `rustlings hint lifetimes1` or use the `hint` watch subcommand for a

// hint.

  
  

fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {

    if x.len() > y.len() {

        x

    } else {

        y

    }

}

  

fn main() {

    let string1 = String::from("abcd");

    let string2 = "xyz";

  

    let result = longest(string1.as_str(), string2);

    println!("The longest string is '{}'", result);

}
```

>   
在 Rust 中，生命周期（lifetimes）是一种编译时检查，确保引用在它们被使用的整个时间内都是有效的。这是为了防止悬垂引用（dangling references），即引用指向的数据已经被释放或者不再有效的情况。当你有函数返回引用或者使用多个引用的时候，Rust 需要知道这些引用的生命周期关系，以确保它们指向的数据在使用期间不会失效。
在 `fn longest<'a>(x: &'a str, y: &'a str) -> &'a str` 函数签名中，生命周期注解 `'a` 的作用是告诉 Rust 编译器：返回的引用将至少和传入的两个字符串引用中较短的那个一样长生存。这意味着：
> 两个参数 `x` 和 `y` 都有相同的生命周期 `'a`，这表示它们必须至少同时有效。
> 返回值也有相同的生命周期 `'a`，这意味着返回的引用将和 `x`、`y` 中较短的生命周期一样长。
没有生命周期注解的情况下，编译器不知道返回的引用与传入的引用之间的生命周期关系，因此它不能保证返回的引用是有效的。这就是为什么编译器会要求在某些情况下明确指定生命周期注解。
例如，如果 `x` 和 `y` 引用的是在 `main` 函数中定义的两个字符串，那么它们的生命周期是从它们被创建到 `main` 函数结束。如果 `longest` 函数返回 `x` 或 `y` 中的一个，返回的引用必须不比 `x` 和 `y` 的生命周期短，否则在 `main` 函数中使用返回的引用时可能会引用到已经释放的内存。
通过在 `longest` 函数定义中使用生命周期 `'a`，你明确了函数的行为，使得编译器可以静态地保证引用的有效性，这就避免了悬垂引用和其它相关的内存安全问题。

```rust
// lifetimes2.rs

//

// So if the compiler is just validating the references passed to the annotated

// parameters and the return type, what do we need to change?

//

// Execute `rustlings hint lifetimes2` or use the `hint` watch subcommand for a

// hint.

  
  

fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {

    if x.len() > y.len() {

        x

    } else {

        y

    }

}

  

fn main() {

    let string1 = String::from("long string is long");

    {

        let string2 = String::from("xyz");

        let result = longest(string1.as_str(), string2.as_str());

        println!("The longest string is '{}'", result);

    }

}
```


```rust
// lifetimes3.rs

//

// Lifetimes are also needed when structs hold references.

//

// Execute `rustlings hint lifetimes3` or use the `hint` watch subcommand for a

// hint.

  

struct Book<'a> {

    author: &'a str,

    title: &'a str,

}

  

fn main() {

    let name = String::from("Jill Smith");

    let title = String::from("Fish Flying");

    let book = Book { author: &name, title: &title };

  

    println!("{} by {}", book.title, book.author);

}
```


# 测试程序是否pannic
```rust
// tests4.rs

//

// Make sure that we're testing for the correct conditions!

//

// Execute `rustlings hint tests4` or use the `hint` watch subcommand for a

// hint.

  
  

struct Rectangle {

    width: i32,

    height: i32

}

  

impl Rectangle {

    // Only change the test functions themselves

    pub fn new(width: i32, height: i32) -> Self {

        if width <= 0 || height <= 0 {

            panic!("Rectangle width and height cannot be negative!")

        }

        Rectangle {width, height}

    }

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn correct_width_and_height() {

        // This test should check if the rectangle is the size that we pass into its constructor

        let rect = Rectangle::new(10, 20);

        assert_eq!(rect.width, 10); // check width

        assert_eq!(rect.height, 20); // check height

    }

  

    #[test]

    #[should_panic(expected = "Rectangle width and height cannot be negative!")]

    fn negative_width() {

        // This test should check if program panics when we try to create rectangle with negative width

        let _rect = Rectangle::new(-10, 10);

    }

  

    #[test]

    #[should_panic(expected = "Rectangle width and height cannot be negative!")]

    fn negative_height() {

        // This test should check if program panics when we try to create rectangle with negative height

        let _rect = Rectangle::new(10, -10);

  

    }

}
```

# 迭代器
```rust
// iterators5.rs

//

// Let's define a simple model to track Rustlings exercise progress. Progress

// will be modelled using a hash map. The name of the exercise is the key and

// the progress is the value. Two counting functions were created to count the

// number of exercises with a given progress. Recreate this counting

// functionality using iterators. Try not to use imperative loops (for, while).

// Only the two iterator methods (count_iterator and count_collection_iterator)

// need to be modified.

//

// Execute `rustlings hint iterators5` or use the `hint` watch subcommand for a

// hint.

  
  

use std::collections::HashMap;

  

#[derive(Clone, Copy, PartialEq, Eq)]

enum Progress {

    None,

    Some,

    Complete,

}

  

fn count_for(map: &HashMap<String, Progress>, value: Progress) -> usize {

    let mut count = 0;

    for val in map.values() {

        if val == &value {

            count += 1;

        }

    }

    count

}

  

fn count_iterator(map: &HashMap<String, Progress>, value: Progress) -> usize {

    map.values()

        .filter(|&v| v == &value)

        .count()

}

  
  

fn count_collection_for(collection: &[HashMap<String, Progress>], value: Progress) -> usize {

    let mut count = 0;

    for map in collection {

        for val in map.values() {

            if val == &value {

                count += 1;

            }

        }

    }

    count

}

  

fn count_collection_iterator(collection: &[HashMap<String, Progress>], value: Progress) -> usize {

    collection.iter()

        .flat_map(|map| map.values())

        .filter(|&v| v == &value)

        .count()

}

  
  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn count_complete() {

        let map = get_map();

        assert_eq!(3, count_iterator(&map, Progress::Complete));

    }

  

    #[test]

    fn count_some() {

        let map = get_map();

        assert_eq!(1, count_iterator(&map, Progress::Some));

    }

  

    #[test]

    fn count_none() {

        let map = get_map();

        assert_eq!(2, count_iterator(&map, Progress::None));

    }

  

    #[test]

    fn count_complete_equals_for() {

        let map = get_map();

        let progress_states = vec![Progress::Complete, Progress::Some, Progress::None];

        for progress_state in progress_states {

            assert_eq!(

                count_for(&map, progress_state),

                count_iterator(&map, progress_state)

            );

        }

    }

  

    #[test]

    fn count_collection_complete() {

        let collection = get_vec_map();

        assert_eq!(

            6,

            count_collection_iterator(&collection, Progress::Complete)

        );

    }

  

    #[test]

    fn count_collection_some() {

        let collection = get_vec_map();

        assert_eq!(1, count_collection_iterator(&collection, Progress::Some));

    }

  

    #[test]

    fn count_collection_none() {

        let collection = get_vec_map();

        assert_eq!(4, count_collection_iterator(&collection, Progress::None));

    }

  

    #[test]

    fn count_collection_equals_for() {

        let progress_states = vec![Progress::Complete, Progress::Some, Progress::None];

        let collection = get_vec_map();

  

        for progress_state in progress_states {

            assert_eq!(

                count_collection_for(&collection, progress_state),

                count_collection_iterator(&collection, progress_state)

            );

        }

    }

  

    fn get_map() -> HashMap<String, Progress> {

        use Progress::*;

  

        let mut map = HashMap::new();

        map.insert(String::from("variables1"), Complete);

        map.insert(String::from("functions1"), Complete);

        map.insert(String::from("hashmap1"), Complete);

        map.insert(String::from("arc1"), Some);

        map.insert(String::from("as_ref_mut"), None);

        map.insert(String::from("from_str"), None);

  

        map

    }

  

    fn get_vec_map() -> Vec<HashMap<String, Progress>> {

        use Progress::*;

  

        let map = get_map();

  

        let mut other = HashMap::new();

        other.insert(String::from("variables2"), Complete);

        other.insert(String::from("functions2"), Complete);

        other.insert(String::from("if1"), Complete);

        other.insert(String::from("from_into"), None);

        other.insert(String::from("try_from_into"), None);

  

        vec![map, other]

    }

}
```

这两个函数，`count_iterator`和`count_collection_iterator`，利用 Rust 的迭代器和相关方法来处理数据集合。下面是它们各自使用的核心内容及其作用：

### count_iterator

这个函数计算一个给定的哈希表（`HashMap`）中，有多少值匹配给定的`Progress`值。它使用以下迭代器方法：

- **`.values()`**: 这个方法从哈希表中提取一个迭代器，包含所有的值。在这个场景中，它提取了所有的`Progress`值。
- **`.filter(|&v| v == &value)`**: `filter`方法接收一个闭包作为参数，这个闭包决定了哪些元素应该被包含在最终的迭代器中。在这里，它筛选出与`value`参数相匹配的所有`Progress`值。
- **`.count()`**: 这个方法消费迭代器并返回它包含的元素数量。在应用了`filter`筛选之后，`count`方法计算有多少`Progress`值匹配给定的条件。

### count_collection_iterator

这个函数计算一个哈希表的集合（一个向量中的多个`HashMap`）中，有多少值匹配给定的`Progress`值。它使用以下迭代器方法：

- **`.iter()`**: 从集合中提取一个迭代器，这里它遍历向量中的每个`HashMap`。
- **`.flat_map(|map| map.values())`**: `flat_map`方法与`map`类似，但它"平铺"了嵌套结构，将多层迭代器转换成一个单层迭代器。这里，它将向量中的每个`HashMap`的值迭代器转换成一个单一的、包含所有哈希表值的迭代器。
- **`.filter(|&v| v == &value)`**: 与`count_iterator`函数中的使用相同，`filter`方法在这里筛选出匹配给定`Progress`值的元素。
- **`.count()`**: 计算筛选后剩余元素的数量，即所有哈希表中匹配给定`Progress`值的元素总数。

通过这些方法，这两个函数展示了如何使用 Rust 的迭代器来执行集合上的查询和聚合操作，而无需显式循环。这种方法利用了迭代器的惰性求值特性，以及 Rust 强大的函数式编程能力，使代码更加简洁和高效。

------------------------------------------------------------------

要使用迭代器来重新实现这些计数功能，我们可以利用迭代器的`filter`和`count`方法。`filter`方法允许我们基于某个条件筛选出迭代器中的元素，而`count`方法则用于计算迭代器中元素的数量。这里是如何用迭代器方法重写`count_iterator`和`count_collection_iterator`函数的具体做法：

### count_iterator 函数

`fn count_iterator(map: &HashMap<String, Progress>, value: Progress) -> usize {     map.values()         .filter(|&v| v == &value)         .count() }`

这个函数首先通过调用`values`方法获取哈希表中所有值的迭代器。然后，它使用`filter`方法选择那些与指定`value`相等的值。最后，`count`方法计算并返回满足条件的值的数量。

### count_collection_iterator 函数


`fn count_collection_iterator(collection: &[HashMap<String, Progress>], value: Progress) -> usize {     collection.iter()         .flat_map(|map| map.values())         .filter(|&v| v == &value)         .count() }`

对于`count_collection_iterator`函数，我们需要处理一个哈希表集合。首先，我们通过`iter`方法获取集合的迭代器。然后，我们使用`flat_map`方法将每个哈希表中的值转换成一个平铺（flattened）的迭代器，这样我们就可以直接迭代集合中所有哈希表的值。之后，我们同样使用`filter`和`count`方法来筛选出满足条件的值并计算它们的数量。

这种方式不仅简洁，而且充分利用了 Rust 迭代器的强大功能，包括链式调用和惰性求值，使代码更加高效和易于理解。

--------------------------------------------------------------------------

# 智能指针
## box
```rust
// box1.rs

//

// At compile time, Rust needs to know how much space a type takes up. This

// becomes problematic for recursive types, where a value can have as part of

// itself another value of the same type. To get around the issue, we can use a

// `Box` - a smart pointer used to store data on the heap, which also allows us

// to wrap a recursive type.

//

// The recursive type we're implementing in this exercise is the `cons list` - a

// data structure frequently found in functional programming languages. Each

// item in a cons list contains two elements: the value of the current item and

// the next item. The last item is a value called `Nil`.

//

// Step 1: use a `Box` in the enum definition to make the code compile

// Step 2: create both empty and non-empty cons lists by replacing `todo!()`

//

// Note: the tests should not be changed

//

// Execute `rustlings hint box1` or use the `hint` watch subcommand for a hint.

  
  

#[derive(PartialEq, Debug)]

pub enum List {

    Cons(i32, Box<List>),

    Nil,

}

  

fn main() {

    println!("This is an empty cons list: {:?}", create_empty_list());

    println!(

        "This is a non-empty cons list: {:?}",

        create_non_empty_list()

    );

}

  

pub fn create_empty_list() -> List {

    List::Nil

}

  

pub fn create_non_empty_list() -> List {

    List::Cons(1, Box::new(List::Nil))

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn test_create_empty_list() {

        assert_eq!(List::Nil, create_empty_list())

    }

  

    #[test]

    fn test_create_non_empty_list() {

        assert_ne!(create_empty_list(), create_non_empty_list())

    }

}
```
## rc
```rust
// rc1.rs

//

// In this exercise, we want to express the concept of multiple owners via the

// Rc<T> type. This is a model of our solar system - there is a Sun type and

// multiple Planets. The Planets take ownership of the sun, indicating that they

// revolve around the sun.

//

// Make this code compile by using the proper Rc primitives to express that the

// sun has multiple owners.

//

// Execute `rustlings hint rc1` or use the `hint` watch subcommand for a hint.

  
  

use std::rc::Rc;

  

#[derive(Debug)]

struct Sun {}

  

#[derive(Debug)]

enum Planet {

    Mercury(Rc<Sun>),

    Venus(Rc<Sun>),

    Earth(Rc<Sun>),

    Mars(Rc<Sun>),

    Jupiter(Rc<Sun>),

    Saturn(Rc<Sun>),

    Uranus(Rc<Sun>),

    Neptune(Rc<Sun>),

}

  

impl Planet {

    fn details(&self) {

        println!("Hi from {:?}!", self)

    }

}

  

#[test]

fn main() {

    let sun = Rc::new(Sun {});

    println!("reference count = {}", Rc::strong_count(&sun)); // 1 reference

  

    let mercury = Planet::Mercury(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 2 references

    mercury.details();

  

    let venus = Planet::Venus(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 3 references

    venus.details();

  

    let earth = Planet::Earth(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 4 references

    earth.details();

  

    let mars = Planet::Mars(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 5 references

    mars.details();

  

    let jupiter = Planet::Jupiter(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 6 references

    jupiter.details();

  

    // TODO

    let saturn = Planet::Saturn(Rc::clone(&sun ));

    println!("reference count = {}", Rc::strong_count(&sun)); // 7 references

    saturn.details();

  

    // TODO

    let uranus = Planet::Uranus(Rc::clone(&sun));

    println!("reference count = {}", Rc::strong_count(&sun)); // 8 references

    uranus.details();

  

    // TODO

    let neptune = Planet::Neptune(Rc::clone(&sun ));

    println!("reference count = {}", Rc::strong_count(&sun)); // 9 references

    neptune.details();

  

    assert_eq!(Rc::strong_count(&sun), 9);

  

    drop(neptune);

    println!("reference count = {}", Rc::strong_count(&sun)); // 8 references

  

    drop(uranus);

    println!("reference count = {}", Rc::strong_count(&sun)); // 7 references

  

    drop(saturn);

    println!("reference count = {}", Rc::strong_count(&sun)); // 6 references

  

    drop(jupiter);

    println!("reference count = {}", Rc::strong_count(&sun)); // 5 references

  

    drop(mars);

    println!("reference count = {}", Rc::strong_count(&sun)); // 4 references

  

    drop(earth);

    println!("reference count = {}", Rc::strong_count(&sun)); // 3 references

  

    drop(venus);

    println!("reference count = {}", Rc::strong_count(&sun)); // 2 references

  

    drop(mercury);

    println!("reference count = {}", Rc::strong_count(&sun)); // 1 reference

  

    assert_eq!(Rc::strong_count(&sun), 1);

}
```
## arch
```rust
// arc1.rs

//

// In this exercise, we are given a Vec of u32 called "numbers" with values

// ranging from 0 to 99 -- [ 0, 1, 2, ..., 98, 99 ] We would like to use this

// set of numbers within 8 different threads simultaneously. Each thread is

// going to get the sum of every eighth value, with an offset.

//

// The first thread (offset 0), will sum 0, 8, 16, ...

// The second thread (offset 1), will sum 1, 9, 17, ...

// The third thread (offset 2), will sum 2, 10, 18, ...

// ...

// The eighth thread (offset 7), will sum 7, 15, 23, ...

//

// Because we are using threads, our values need to be thread-safe.  Therefore,

// we are using Arc.  We need to make a change in each of the two TODOs.

//

// Make this code compile by filling in a value for `shared_numbers` where the

// first TODO comment is, and create an initial binding for `child_numbers`

// where the second TODO comment is. Try not to create any copies of the

// `numbers` Vec!

//

// Execute `rustlings hint arc1` or use the `hint` watch subcommand for a hint.

  

// I AM NOT DONE

  

#![forbid(unused_imports)] // Do not change this, (or the next) line.

use std::sync::Arc;

use std::thread;

  

fn main() {

    let numbers: Vec<_> = (0..100u32).collect();

    let shared_numbers = Arc::new(numbers);

    let mut joinhandles = Vec::new();

  

    for offset in 0..8 {

        let child_numbers = Arc::clone(&shared_numbers);

        joinhandles.push(thread::spawn(move || {

            let sum: u32 = child_numbers.iter().filter(|&&n| n % 8 == offset).sum();

            println!("Sum of offset {} is {}", offset, sum);

        }));

    }

    for handle in joinhandles.into_iter() {

        handle.join().unwrap();

    }

}

```
## cow
```rust
// cow1.rs

//

// This exercise explores the Cow, or Clone-On-Write type. Cow is a

// clone-on-write smart pointer. It can enclose and provide immutable access to

// borrowed data, and clone the data lazily when mutation or ownership is

// required. The type is designed to work with general borrowed data via the

// Borrow trait.

//

// This exercise is meant to show you what to expect when passing data to Cow.

// Fix the unit tests by checking for Cow::Owned(_) and Cow::Borrowed(_) at the

// TODO markers.

//

// Execute `rustlings hint cow1` or use the `hint` watch subcommand for a hint.

  

// I AM NOT DONE

  

use std::borrow::Cow;

  

fn abs_all<'a, 'b>(input: &'a mut Cow<'b, [i32]>) -> &'a mut Cow<'b, [i32]> {

    for i in 0..input.len() {

        let v = input[i];

        if v < 0 {

            // Clones into a vector if not already owned.

            input.to_mut()[i] = -v;

        }

    }

    input

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn reference_mutation() -> Result<(), &'static str> {

        // Clone occurs because `input` needs to be mutated.

        let slice = [-1, 0, 1];

        let mut input = Cow::from(&slice[..]);

        match abs_all(&mut input) {

            Cow::Owned(_) => Ok(()),

            _ => Err("Expected owned value"),

        }

    }

  

    #[test]

    fn reference_no_mutation() -> Result<(), &'static str> {

        // No clone occurs because `input` doesn't need to be mutated.

        let slice = [0, 1, 2];

        let mut input = Cow::from(&slice[..]);

        match abs_all(&mut input) {

            Cow::Borrowed(_) => Ok(()),

            _ => Err("Expected borrowed value"),

  

        }

    }

  

    #[test]

    fn owned_no_mutation() -> Result<(), &'static str> {

        // We can also pass `slice` without `&` so Cow owns it directly. In this

        // case no mutation occurs and thus also no clone, but the result is

        // still owned because it was never borrowed or mutated.

        let slice = vec![0, 1, 2];

        let mut input = Cow::from(slice);

        match abs_all(&mut input) {

            Cow::Owned(_) => Ok(()),

            _ => Err("Expected owned value"),

        }

    }

  

    #[test]

    fn owned_mutation() -> Result<(), &'static str> {

        // Of course this is also the case if a mutation does occur. In this

        // case the call to `to_mut()` in the abs_all() function returns a

        // reference to the same data as before.

        let slice = vec![-1, 0, 1];

        let mut input = Cow::from(slice);

        match abs_all(&mut input) {

            Cow::Owned(_) => Ok(()),

            _ => Err("Expected owned value"),

        }

    }

}
```

# thread
```rust
// threads2.rs

//

// Building on the last exercise, we want all of the threads to complete their

// work but this time the spawned threads need to be in charge of updating a

// shared value: JobStatus.jobs_completed

//

// Execute `rustlings hint threads2` or use the `hint` watch subcommand for a

// hint.

  
  

use std::sync::{Arc,Mutex};

use std::thread;

use std::time::Duration;

  

struct JobStatus {

    jobs_completed: u32,

}

  

fn main() {

    let status = Arc::new(Mutex::new(JobStatus { jobs_completed: 0 }) );

    let mut handles = vec![];

    for _ in 0..10 {

        let status_shared = Arc::clone(&status);

        let handle = thread::spawn(move || {

            thread::sleep(Duration::from_millis(250));

            // TODO: You must take an action before you update a shared value

            status_shared.lock().unwrap().jobs_completed += 1;

        });

        handles.push(handle);

    }

    for handle in handles {    

        handle.join().unwrap();

        // TODO: Print the value of the JobStatus.jobs_completed. Did you notice

        // anything interesting in the output? Do you have to 'join' on all the

        // handles?

        println!("jobs completed {}", status.lock().unwrap().jobs_completed);

    }

}
```
--------
```rust
// threads3.rs

//

// Execute `rustlings hint threads3` or use the `hint` watch subcommand for a

// hint.

  
  

use std::sync::mpsc;

use std::sync::Arc;

use std::thread;

use std::time::Duration;

  

struct Queue {

    length: u32,

    first_half: Vec<u32>,

    second_half: Vec<u32>,

}

  

impl Queue {

    fn new() -> Self {

        Queue {

            length: 10,

            first_half: vec![1, 2, 3, 4, 5],

            second_half: vec![6, 7, 8, 9, 10],

        }

    }

}

  

fn send_tx(q: Queue, tx: mpsc::Sender<u32>) -> () {

    let qc = Arc::new(q);

    let qc1 = Arc::clone(&qc);

    let qc2 = Arc::clone(&qc);

    let txc = tx.clone();

    thread::spawn(move || {

        for val in &qc1.first_half {

            println!("sending {:?}", val);

            tx.send(*val).unwrap();

            thread::sleep(Duration::from_secs(1));

        }

    });

  

    thread::spawn(move || {

        for val in &qc2.second_half {

            println!("sending {:?}", val);

            txc.send(*val).unwrap();

            thread::sleep(Duration::from_secs(1));

        }

    });

}

  

#[test]

fn main() {

    let (tx, rx) = mpsc::channel();

    let queue = Queue::new();

    let queue_length = queue.length;

  

    send_tx(queue, tx);

  

    let mut total_received: u32 = 0;

    for received in rx {

        println!("Got: {}", received);

        total_received += 1;

    }

  

    println!("total numbers received: {}", total_received);

    assert_eq!(total_received, queue_length)

}
```

# macro
```rust
pub mod macros {
    #[macro_export] // 添加这行来导出宏
    macro_rules! my_macro {
        () => {
            println!("Check out my macro!");
        };
    }
}

fn main() {
    my_macro!(); // 注意，这里不再需要 `macros::` 前缀
}

```
或
```rust
#[macro_use] // 将这个属性应用于模块声明，以允许宏在模块外部使用
pub mod macros {
    macro_rules! my_macro {
        () => {
            println!("Check out my macro!");
        };
    }
}

fn main() {
    my_macro!();
}

```
或
```rust
// macros3.rs

//

// Make me compile, without taking the macro out of the module!

//

// Execute `rustlings hint macros3` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

fn main() {

    macros::my_macro!();

}

  

mod macros {

    macro_rules! my_macro {

        () => {

            println!("Check out my macro!");

        };

    }

    pub (crate) use my_macro;

}
```
注意要有；
 ```rust
 // macros4.rs

//

// Execute `rustlings hint macros4` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

#[rustfmt::skip]

macro_rules! my_macro {

    () => {

        println!("Check out my macro!");

    };

    ($val:expr) => {

        println!("Look at this other macro: {}", $val);

    }

}

  

fn main() {

    my_macro!();

    my_macro!(7777);

}
```
# clippy
```rust
// clippy1.rs

//

// The Clippy tool is a collection of lints to analyze your code so you can

// catch common mistakes and improve your Rust code.

//

// For these exercises the code will fail to compile when there are clippy

// warnings check clippy's suggestions from the output to solve the exercise.

//

// Execute `rustlings hint clippy1` or use the `hint` watch subcommand for a

// hint.

  
  

use std::f32;

use f32::consts::PI;

fn main() {

    let pi = f32::consts::PI;

    let radius = 5.00f32;

  

    let area = pi * f32::powi(radius, 2);

    println!(

        "The area of a circle with radius {:.2} is {:.5}!",

        radius, area

    );

    let area = PI * f32::powi(radius, 2);

    println!(

        "The area of a circle with radius {:.2} is {:.5}!",

        radius, area

    )

}
```
# 类型转换
```rust
// using_as.rs

//

// Type casting in Rust is done via the usage of the `as` operator. Please note

// that the `as` operator is not only used when type casting. It also helps with

// renaming imports.

//

// The goal is to make sure that the division does not fail to compile and

// returns the proper type.

//

// Execute `rustlings hint using_as` or use the `hint` watch subcommand for a

// hint.

  

// I AM NOT DONE

  

fn average(values: &[f64]) -> f64 {

    let total = values.iter().sum::<f64>();

    total / (values.len() as f64)

}

  

fn main() {

    let values = [3.5, 0.3, 13.0, 11.7];

    println!("{}", average(&values));

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn returns_proper_type_and_value() {

        assert_eq!(average(&[3.5, 0.3, 13.0, 11.7]), 7.125);

    }

}
```

# conversion 
```rust
// from_into.rs

//

// The From trait is used for value-to-value conversions. If From is implemented

// correctly for a type, the Into trait should work conversely. You can read

// more about it at https://doc.rust-lang.org/std/convert/trait.From.html

//

// Execute `rustlings hint from_into` or use the `hint` watch subcommand for a

// hint.

  

#[derive(Debug)]

struct Person {

    name: String,

    age: usize,

}

  

// We implement the Default trait to use it as a fallback

// when the provided string is not convertible into a Person object

impl Default for Person {

    fn default() -> Person {

        Person {

            name: String::from("John"),

            age: 30,

        }

    }

}

  

// Your task is to complete this implementation in order for the line `let p =

// Person::from("Mark,20")` to compile Please note that you'll need to parse the

// age component into a `usize` with something like `"4".parse::<usize>()`. The

// outcome of this needs to be handled appropriately.

//

// Steps:

// 1. If the length of the provided string is 0, then return the default of

//    Person.

// 2. Split the given string on the commas present in it.

// 3. Extract the first element from the split operation and use it as the name.

// 4. If the name is empty, then return the default of Person.

// 5. Extract the other element from the split operation and parse it into a

//    `usize` as the age.

// If while parsing the age, something goes wrong, then return the default of

// Person Otherwise, then return an instantiated Person object with the results

  
  

impl From<&str> for Person {

    fn from(s: &str) -> Person {

        if s.is_empty() {

            return Default::default();

        }

  

        let parts: Vec<&str> = s.splitn(3, ',').collect();

  

        if parts.len() < 2 || parts[0].is_empty() {

            return Default::default();

        }

  

        match parts[1].parse::<usize>() {

            Ok(age) => Person {

                name: parts[0].to_string(),

                age,

            },

            Err(_) => Default::default(),

        }

    }

}

  
  

fn main() {

    // Use the `from` function

    let p1 = Person::from("Mark,20");

    // Since From is implemented for Person, we should be able to use Into

    let p2: Person = "Gerald,70".into();

    println!("{:?}", p1);

    println!("{:?}", p2);

}

  

#[cfg(test)]

mod tests {

    use super::*;

    #[test]

    fn test_default() {

        // Test that the default person is 30 year old John

        let dp = Person::default();

        assert_eq!(dp.name, "John");

        assert_eq!(dp.age, 30);

    }

    #[test]

    fn test_bad_convert() {

        // Test that John is returned when bad string is provided

        let p = Person::from("");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

    #[test]

    fn test_good_convert() {

        // Test that "Mark,20" works

        let p = Person::from("Mark,20");

        assert_eq!(p.name, "Mark");

        assert_eq!(p.age, 20);

    }

    #[test]

    fn test_bad_age() {

        // Test that "Mark,twenty" will return the default person due to an

        // error in parsing age

        let p = Person::from("Mark,twenty");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_missing_comma_and_age() {

        let p: Person = Person::from("Mark");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_missing_age() {

        let p: Person = Person::from("Mark,");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_missing_name() {

        let p: Person = Person::from(",1");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_missing_name_and_age() {

        let p: Person = Person::from(",");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_missing_name_and_invalid_age() {

        let p: Person = Person::from(",one");

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 30);

    }

  

    #[test]

    fn test_trailing_comma() {

        let p: Person = Person::from("Mike,32,");

        assert_eq!(p.name, "Mike");

        assert_eq!(p.age, 32);

    }

  

    #[test]

    fn test_trailing_comma_and_some_string() {

        let p: Person = Person::from("Mike,32,man");

        assert_eq!(p.name, "Mike");

        assert_eq!(p.age, 32);

    }

}
```

------
```
// from_str.rs

//

// This is similar to from_into.rs, but this time we'll implement `FromStr` and

// return errors instead of falling back to a default value. Additionally, upon

// implementing FromStr, you can use the `parse` method on strings to generate

// an object of the implementor type. You can read more about it at

// https://doc.rust-lang.org/std/str/trait.FromStr.html

//

// Execute `rustlings hint from_str` or use the `hint` watch subcommand for a

// hint.

  

use std::num::ParseIntError;

use std::str::FromStr;

  

#[derive(Debug, PartialEq)]

struct Person {

    name: String,

    age: usize,

}

  

// We will use this error type for the `FromStr` implementation.

#[derive(Debug, PartialEq)]

enum ParsePersonError {

    // Empty input string

    Empty,

    // Incorrect number of fields

    BadLen,

    // Empty name field

    NoName,

    // Wrapped error from parse::<usize>()

    ParseInt(ParseIntError),

}

  
  

// Steps:

// 1. If the length of the provided string is 0, an error should be returned

// 2. Split the given string on the commas present in it

// 3. Only 2 elements should be returned from the split, otherwise return an

//    error

// 4. Extract the first element from the split operation and use it as the name

// 5. Extract the other element from the split operation and parse it into a

//    `usize` as the age with something like `"4".parse::<usize>()`

// 6. If while extracting the name and the age something goes wrong, an error

//    should be returned

// If everything goes well, then return a Result of a Person object

//

// As an aside: `Box<dyn Error>` implements `From<&'_ str>`. This means that if

// you want to return a string error message, you can do so via just using

// return `Err("my error message".into())`.

  

impl FromStr for Person {

    type Err = ParsePersonError;

    fn from_str(s: &str) -> Result<Person, Self::Err> {

        if s.is_empty() {

            return Err(ParsePersonError::Empty);

        }

        let s:Vec<&str> = s.split(",").collect();

  

        match &s[..] {

            &[name, _] if name.is_empty() => Err(ParsePersonError::NoName),

            &[name, age_str] => match age_str.parse::<usize>() {

                Ok(age) => Ok(Person{name:name.to_string(), age}),

                Err(e) => Err(ParsePersonError::ParseInt(e)),

            }

            _ => Err(ParsePersonError::BadLen),

        }

    }

}

  

fn main() {

    let p = "Mark,20".parse::<Person>().unwrap();

    println!("{:?}", p);

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn empty_input() {

        assert_eq!("".parse::<Person>(), Err(ParsePersonError::Empty));

    }

    #[test]

    fn good_input() {

        let p = "John,32".parse::<Person>();

        assert!(p.is_ok());

        let p = p.unwrap();

        assert_eq!(p.name, "John");

        assert_eq!(p.age, 32);

    }

    #[test]

    fn missing_age() {

        assert!(matches!(

            "John,".parse::<Person>(),

            Err(ParsePersonError::ParseInt(_))

        ));

    }

  

    #[test]

    fn invalid_age() {

        assert!(matches!(

            "John,twenty".parse::<Person>(),

            Err(ParsePersonError::ParseInt(_))

        ));

    }

  

    #[test]

    fn missing_comma_and_age() {

        assert_eq!("John".parse::<Person>(), Err(ParsePersonError::BadLen));

    }

  

    #[test]

    fn missing_name() {

        assert_eq!(",1".parse::<Person>(), Err(ParsePersonError::NoName));

    }

  

    #[test]

    fn missing_name_and_age() {

        assert!(matches!(

            ",".parse::<Person>(),

            Err(ParsePersonError::NoName | ParsePersonError::ParseInt(_))

        ));

    }

  

    #[test]

    fn missing_name_and_invalid_age() {

        assert!(matches!(

            ",one".parse::<Person>(),

            Err(ParsePersonError::NoName | ParsePersonError::ParseInt(_))

        ));

    }

  

    #[test]

    fn trailing_comma() {

        assert_eq!("John,32,".parse::<Person>(), Err(ParsePersonError::BadLen));

    }

  

    #[test]

    fn trailing_comma_and_some_string() {

        assert_eq!(

            "John,32,man".parse::<Person>(),

            Err(ParsePersonError::BadLen)

        );

    }

}
```
----
https://doc.rust-lang.org/std/convert/trait.TryFrom.html
```rust
// try_from_into.rs

//

// TryFrom is a simple and safe type conversion that may fail in a controlled

// way under some circumstances. Basically, this is the same as From. The main

// difference is that this should return a Result type instead of the target

// type itself. You can read more about it at

// https://doc.rust-lang.org/std/convert/trait.TryFrom.html

//

// Execute `rustlings hint try_from_into` or use the `hint` watch subcommand for

// a hint.

  

use std::convert::{TryFrom, TryInto};

  

#[derive(Debug, PartialEq)]

struct Color {

    red: u8,

    green: u8,

    blue: u8,

}

  

// We will use this error type for these `TryFrom` conversions.

#[derive(Debug, PartialEq)]

enum IntoColorError {

    // Incorrect length of slice

    BadLen,

    // Integer conversion error

    IntConversion,

}

  

// I AM NOT DONE

  

// Your task is to complete this implementation and return an Ok result of inner

// type Color. You need to create an implementation for a tuple of three

// integers, an array of three integers, and a slice of integers.

//

// Note that the implementation for tuple and array will be checked at compile

// time, but the slice implementation needs to check the slice length! Also note

// that correct RGB color values must be integers in the 0..=255 range.

  

// Tuple implementation

impl TryFrom<(i16, i16, i16)> for Color {

    type Error = IntoColorError;

    fn try_from(tuple: (i16, i16, i16)) -> Result<Self, Self::Error> {

        let(r, g, b) = tuple;

        let u8_range = 0..256;

        for elem in &[r, g, b] {

            if !u8_range.contains(elem) {

                return Err(IntoColorError::IntConversion);

            }

        }

        Ok(Color {red: r as u8, green: g as u8, blue: b  as u8})

  

    }

}

  

// Array implementation

impl TryFrom<[i16; 3]> for Color {

    type Error = IntoColorError;

    fn try_from(arr: [i16; 3]) -> Result<Self, Self::Error> {

        let u8_range = 0..256;

        for elem in &arr[..] {

            if !u8_range.contains(elem) {

                return Err(IntoColorError::IntConversion);

            }

        }

        Ok(Color {red: arr[0] as u8, green: arr[1] as u8, blue: arr[2]  as u8})

    }

}

  

// Slice implementation

impl TryFrom<&[i16]> for Color {

    type Error = IntoColorError;

    fn try_from(slice: &[i16]) -> Result<Self, Self::Error> {

        if slice.len() != 3 {return Err(IntoColorError::BadLen);}

        let u8_range = 0..256;

        for elem in &slice[..] {

            if !u8_range.contains(elem) {

                return Err(IntoColorError::IntConversion);

            }

        }

        Ok(Color {red: slice[0] as u8, green: slice[1] as u8, blue: slice[2]  as u8})

    }

}

  

fn main() {

    // Use the `try_from` function

    let c1 = Color::try_from((183, 65, 14));

    println!("{:?}", c1);

  

    // Since TryFrom is implemented for Color, we should be able to use TryInto

    let c2: Result<Color, _> = [183, 65, 14].try_into();

    println!("{:?}", c2);

  

    let v = vec![183, 65, 14];

    // With slice we should use `try_from` function

    let c3 = Color::try_from(&v[..]);

    println!("{:?}", c3);

    // or take slice within round brackets and use TryInto

    let c4: Result<Color, _> = (&v[..]).try_into();

    println!("{:?}", c4);

}

  

#[cfg(test)]

mod tests {

    use super::*;

  

    #[test]

    fn test_tuple_out_of_range_positive() {

        assert_eq!(

            Color::try_from((256, 1000, 10000)),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_tuple_out_of_range_negative() {

        assert_eq!(

            Color::try_from((-1, -10, -256)),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_tuple_sum() {

        assert_eq!(

            Color::try_from((-1, 255, 255)),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_tuple_correct() {

        let c: Result<Color, _> = (183, 65, 14).try_into();

        assert!(c.is_ok());

        assert_eq!(

            c.unwrap(),

            Color {

                red: 183,

                green: 65,

                blue: 14

            }

        );

    }

    #[test]

    fn test_array_out_of_range_positive() {

        let c: Result<Color, _> = [1000, 10000, 256].try_into();

        assert_eq!(c, Err(IntoColorError::IntConversion));

    }

    #[test]

    fn test_array_out_of_range_negative() {

        let c: Result<Color, _> = [-10, -256, -1].try_into();

        assert_eq!(c, Err(IntoColorError::IntConversion));

    }

    #[test]

    fn test_array_sum() {

        let c: Result<Color, _> = [-1, 255, 255].try_into();

        assert_eq!(c, Err(IntoColorError::IntConversion));

    }

    #[test]

    fn test_array_correct() {

        let c: Result<Color, _> = [183, 65, 14].try_into();

        assert!(c.is_ok());

        assert_eq!(

            c.unwrap(),

            Color {

                red: 183,

                green: 65,

                blue: 14

            }

        );

    }

    #[test]

    fn test_slice_out_of_range_positive() {

        let arr = [10000, 256, 1000];

        assert_eq!(

            Color::try_from(&arr[..]),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_slice_out_of_range_negative() {

        let arr = [-256, -1, -10];

        assert_eq!(

            Color::try_from(&arr[..]),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_slice_sum() {

        let arr = [-1, 255, 255];

        assert_eq!(

            Color::try_from(&arr[..]),

            Err(IntoColorError::IntConversion)

        );

    }

    #[test]

    fn test_slice_correct() {

        let v = vec![183, 65, 14];

        let c: Result<Color, _> = Color::try_from(&v[..]);

        assert!(c.is_ok());

        assert_eq!(

            c.unwrap(),

            Color {

                red: 183,

                green: 65,

                blue: 14

            }

        );

    }

    #[test]

    fn test_slice_excess_length() {

        let v = vec![0, 0, 0, 0];

        assert_eq!(Color::try_from(&v[..]), Err(IntoColorError::BadLen));

    }

    #[test]

    fn test_slice_insufficient_length() {

        let v = vec![0, 0];

        assert_eq!(Color::try_from(&v[..]), Err(IntoColorError::BadLen));

    }

} 
```

todo!()