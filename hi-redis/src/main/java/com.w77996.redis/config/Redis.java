package com.w77996.redis.config;

public class Redis {
    //    @Bean
//    public RedisTemplate initRedisTemplate(@Qualifier("connectionFactory") JedisConnectionFactory jedisConnectionFactory) {
//
//        RedisSerializer str = new StringRedisSerializer();
//        RedisSerializer json = new GenericJackson2JsonRedisSerializer();
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        redisTemplate.setKeySerializer(str);
//        redisTemplate.setValueSerializer(json);
//        redisTemplate.setHashKeySerializer(str);
//        redisTemplate.setHashValueSerializer(json);
//        return redisTemplate;
//    }
//
//    @Bean(name = "connectionFactory")
//    public JedisConnectionFactory initConnect(){
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
//        redisStandaloneConfiguration.setHostName("localhost");
//        redisStandaloneConfiguration.setPort(6379);
//        redisStandaloneConfiguration.setDatabase(0);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("12345"));
//
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
//        jedisClientConfiguration.connectTimeout(Duration.ofMillis(30000));//  connection timeout
//
//        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
//
//        return factory;
//    }



//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdle);
//        poolConfig.setMinIdle(minIdle);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setDatabase(database);
//        return jedisConnectionFactory;
//    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory2(){
//        JedisPoolConfig poolConfig=new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdl);
//        poolConfig.setMinIdle(minIdl);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
//        jedisConnectionFactory.setHostName(hostName2);
//        if(!passWord.isEmpty()){
//            jedisConnectionFactory.setPassword(passWord);
//        }
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setDatabase(database);
//        return jedisConnectionFactory;
//    }

//    @Bean(name = "redisTemplate1")
//    public RedisTemplate<String, Object> redisTemplateObject() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }

//    @Bean(name = "redisTemplate2")
//    public RedisTemplate<String, Object> redisTemplateObject2() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory2());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }

//
//    private void setSerializer(RedisTemplate<String, Object> template) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(template.getStringSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        //在使用String的数据结构的时候使用这个来更改序列化方式
//        /*RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringSerializer );
//        template.setValueSerializer(stringSerializer );
//        template.setHashKeySerializer(stringSerializer );
//        template.setHashValueSerializer(stringSerializer );*/
//
//    }

//    @Bean("jedisConnFactory0")
//    public JedisConnectionFactory jedisConnectionFactory(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName("localhost");
//        factory.setUsePool(true);
//        factory.setPassword("12345");
//        factory.setDatabase(1);
//        factory.setPort(6379);
//        factory.setPoolConfig(jedisPoolConfig);
//        return factory;
//    }
//
//    @Bean("redisTemplate2")
//    public RedisTemplate redisTemplate(@Qualifier("jedisConnFactory0") JedisConnectionFactory jedisConnectionFactory){
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        RedisConnectionFactory factory = jedisConnectionFactory;
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }

    /**
     * 配置redis连接工厂
     *
     * @return
     */
//    @Bean("jedisConnFactory0")
//    public JedisConnectionFactory jedisConnectionFactory(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName("localhost");
//        factory.setUsePool(true);
//        factory.setPassword("12345");
//        factory.setDatabase(0);
//        factory.setPort(6379);
//        factory.setPoolConfig(jedisPoolConfig);
//        return factory;
//    }
//
//    @Bean("redisTemplate2")
//    public RedisTemplate redisTemplate(@Qualifier("jedisConnFactory0") JedisConnectionFactory jedisConnectionFactory){
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        RedisConnectionFactory factory = jedisConnectionFactory;
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName("localhost");
//        jedisConnectionFactory.setPort(6379);
//        jedisConnectionFactory.setPassword("12345");
//        jedisConnectionFactory.setDatabase(0);
//        jedisConnectionFactory.setUsePool(true);
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }


//    @Bean
//    public RedisConnectionFactory defaultRedisConnectionFactory() {
//        return createJedisConnectionFactory(dbIndex, host, port, password, timeout);
//    }
//
//    /**
//     * 配置redisTemplate 注入方式使用@Resource(name="") 方式注入
//     *
//     * @return
//     */
//    @Bean(name = "defaultRedisTemplate")
//    public RedisTemplate defaultRedisTemplate() {
//        RedisTemplate template = new RedisTemplate();
//        template.setConnectionFactory(defaultRedisConnectionFactory());
//        setSerializer(template);
//        template.afterPropertiesSet();
//        return template;
//    }
}
