package com.sun.springbootdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.springbootdemo.config.BeanDefinitionConfig;
import com.sun.springbootdemo.config.CommconProperty;
import com.sun.springbootdemo.mapper.EntitiesConfigMapper;
import com.sun.springbootdemo.mybatis.config.DataSourceConfig;
import com.sun.springbootdemo.service.CommonBusiniessService;
import com.sun.springbootdemo.service.TestService;
import com.sun.springbootdemo.service.entities.Animals;
import com.sun.springbootdemo.service.entities.Eagle;
import com.sun.springbootdemo.service.entities.Mailbox;
import com.sun.springbootdemo.service.entities.Person;
import com.sun.springbootdemo.service.entities.Pet;
import com.sun.springbootdemo.service.entities.Record;
import com.sun.springbootdemo.service.entities.Student;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Log4j2
@MapperScan("com.sun.springbootdemo.mapper")
public class SpringbootdemoApplicationTests extends BaseJnuit5Test {

    protected long endTime;

    protected long startTime;

    @Autowired
    private Person person;

    @Autowired
    private Mailbox mailbox;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Spring框架中事务的几种方式：
     * 1、编程式事务  -->> 数据库的连接 connection
     * 2、声明式事务  -->> PlatformTransactionManager  -->> AOP
     * 3、注解式事务 @Transational  -->> AOP
     */
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Autowired
    private CommconProperty commconProperty;

    @Autowired
    private Pet pet;

    @Autowired
    private Animals animals;

    @Autowired
    @Qualifier("dataSourceMaster")
    private DataSource dataSource;

    @Autowired
    private EntitiesConfigMapper entitiesConfigMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Record record;

    @Autowired
    private CommonBusiniessService commonBusiniessService;

    @Autowired
    private TestService testService;

    @Autowired
    private Student student;

    /**
     * @redisTemplate 默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的
     */
    /*@Autowired
    private RedisTemplate<String, Object> redisTemplate;*/

    /**
     * @stringRedisTemplate 默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的
     */
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Test
    public void contextLoads() {
        //Assert.assertNotNull(personService);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanDefinitionConfig.class);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        System.out.println("studentService的bean->" + context.getBeanFactory());
    }

    @Test
    public void serviceTest() throws Exception {
        pet.setName("鹦鹉");
        pet.setAge(1);
        String jsonPerson = "{\\\"name\\\":\\\"夏侯惇\\\",\\\"Age\\\":25,\\\"sex\\\":\\\"m\\\",\\\"birthDay\\\":\\\"1992-09-08\\\",\\\"pet\\\":{\\\"name\\\":\\\"鹦鹉\\\",\\\"age\\\":1,\\\"sex\\\":null},\\\"boss\\\":true}";
        Person p = new Person("夏侯惇", 25, "m", true, LocalDate.of(1992, 9, 8), pet);
        String s = objectMapper.writeValueAsString(p);

        Map<String, Object> map = objectMapper.readValue(s, Map.class);
        System.out.println(map);
        /*String persongJson = objectMapper.writeValueAsString(p);
        objectMapper.readValue(jsonPerson, new TypeReference<Person>() {
        });
        JavaType javaType = objectMapper.getTypeFactory().constructType(Person.class);
        Person p2 = objectMapper.readValue(persongJson, javaType);
        JsonNode jsonNode = objectMapper.readTree(persongJson);
        String name = jsonNode.get("name").asText();
        jsonNode.get("pet");
        ObjectNode rootNode = objectMapper.getNodeFactory().objectNode();
        rootNode.put("Root", "root");
        ObjectNode petNode = objectMapper.getNodeFactory().objectNode();
        petNode.put("name", "鹦鹉");
        petNode.put("age", 1);
        rootNode.set("pet", petNode);
        String str = objectMapper.writeValueAsString(rootNode); //{"Root":"root","pet":{"name":"鹦鹉","age":1}}
        System.out.println(str);*/

    }

    @Test
    public void RecordTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(com.sun.springbootdemo.SpringbootdemoApplication.class);
        Map<String, Record> beanMap = context.getBeansOfType(Record.class);
        System.out.println(environment);
    }

    @Test
    public void transactionTest() {
        TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        platformTransactionManager.commit(status);
        platformTransactionManager.rollback(status);

    }

    @Test
    public void configrableApplicationContextTest() {
       /* Annotation annotation = configurableApplicationContext.findAnnotationOnBean("mailbox", Service.class);
        System.out.println(annotation.annotationType().getName());*/
        Student student = (Student) configurableApplicationContext.getBean(Student.class, "sundz", 28);
        System.out.println(student);
    }

    @Test
    public void BinderTest() {
        Binder binder = Binder.get(environment);
        //将配置文件中的属性注入指定的对象
        CommconProperty commcomProperty = binder.bind("common.property", Bindable.of(CommconProperty.class)).get();
        System.out.println(commcomProperty);
    }

    @Test
    public void commconPropertyTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        Object o = context.getBean("sessionFactorySlave");
    }

    @Test
    public void RestTemplateTest() {
        /*RestTemplate restTemplate = new RestTemplate();*/
        Cache<Object, Object> cache = Caffeine.newBuilder().build(new CacheLoader<Object, Object>() {
            @Nullable
            @Override
            public Object load(@NonNull Object key) throws Exception {
                return Integer.parseInt(String.valueOf(key)) + 1;
            }
        });
        cache.put("1", "1");
        System.out.println(cache.asMap());
        CaffeineCache caffeineCache = new CaffeineCache("sundz", cache);
    }

    @Test
    public void selectAllTest() {
        PageHelper.startPage(1, 2, true);
        List<Map<String, Object>> data = entitiesConfigMapper.findAll();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(data);
        List<Map<String, Object>> pageData = pageInfo.getList();
        System.out.println(pageData);
    }

    @Test
    public void selectOneTest() {
        String userId = "21";
        String city = entitiesConfigMapper.selectOne(userId);
        log.info("city:" + city);
    }

    @Test
    public void BeanCopierTest() {
        Pet p = new Pet("Tom", 2);
        p.setSex("m");
        Animals eagle = new Eagle();
        BeanCopier.create(Pet.class, Eagle.class, false).copy(p, eagle, null);
        System.out.println("eagle ----------->> " + eagle);
    }

    @Test
    public void cacheTest() {
        testService.test();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();


    }


}
