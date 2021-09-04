# 问题
## 介绍
此页表示在编码过程中遇到的问题和解决方案
## 主要问题
### 通过驼峰命名法命名的变量在转换过程中会出错
FastJson中自带，Mybatis中需要进行配置
### 前端在引入css和js样式时失败
目录的选择错误，应在根目录下引用文件  
引用的顺序
### Mybatis的Mapper无效
没有正确使用Mybatis的包
### 热部署失败
### 配置拦截器的时候出现No Mapping的错误
```sql
 private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }

    }

```
或者去掉@EnableWebMvc
### UserMapper注入失败
mybatis.type-aliases-package没有配置好
### 出现id为空无法写入数据库的问题
updateByExampleSelective：选择性更新数据<br>
updateByExample：全部更新
### 出现类型不匹配的设计问题
重新设计并修改数据库
### 数据库不同步问题
修改为a = a + b
### 事务问题
@Transaction