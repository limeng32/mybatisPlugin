## mybatisPlugin

mybatisPlugin是集成若干个mybatis插件的工具包。

## 数据库映射
mybatisPlugin提供注解式数据库映射，相关注解都在limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation包，可通过注解描述字段匹配，外键关系，乐观锁和"
<>%_”等特殊查询。支持复杂关系映射。

## 分页
mybatisPlugin支持物理分页，并可返回已封装好的Page<Pojo>对象，属性包括每页容量，当前页号，最大页号，总结果数量等。

## 缓存
mybatisPlugin支持自定义缓存相互关系，采用序列化加MD5方式存储海量查询条件，可与redis共同使用。
