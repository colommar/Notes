# Lombok
## Lombok 和 Jackson 矛盾错误
- @getter，@setter在遇到形如aName的时候，jakson无法将其正确反序列化
- 当 RESTful 接口接收到客户端发送的 JSON 请求体 时，Spring Boot 使用 Jackson 将 JSON 数据反序列化为 Java 对象。
- 得出结论Lombok在转换的时候将其转换为AName，符合直觉，但是jakson无法识别。
- 示范如下：
   - 用Lombok实现getter，setter等配置的自动注入：
```java
@Data
public class User {
    private String name;
    private String aName;
    private String aaName;
}
```
发现get，set方法正确实现，符合直觉
```java
    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setAName(final String aName) {
        this.aName = aName;
    }

    @Generated
    public void setAaName(final String aaName) {
        this.aaName = aaName;
    }
```
json请求字段
```json
{ 
    "name" : "name",
    "aname" : "aname",
    "aaName" : "aaName",
    "aName" : "aName"
}
```
发现传过来的只有`aname: aname`， 却被识别为`aName`的字段，但其实是`aname`的字段。
这个显然是jakson传入时候的问题
```
User(name=name, aName=aname, aaName=aaName)
```
![alt text](<Spring MVC JSON to User Mapping Debug.png>)
![alt text](<Spring MVC 的工作流程图.png>)

改成这个就好了
```java
@Data
public class User {
    private String name;
    @JsonProperty("aName") // 明确指定 JSON 键名
    private String aName;
    private String aaName;
}
```

### debug流程图展示
dispatherservlet.dodispatch -> 


## 日志级别
- TRACE < DEBUG < INFO < WARN < ERROR < FATAL
- 日志常用格式
```yaml
```
