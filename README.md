# 这是一个使用maven集成SSh框架的大体过程，梳理一下思路，也希望对你有用:-1::-1::-1::-1:
## poem.xml 在这里锁定了ssh的版本信息
```
<!-- 属性 -->
	<properties>
		<spring.version>4.2.4.RELEASE</spring.version>
		<hibernate.version>5.0.7.Final</hibernate.version>
		<struts.version>2.3.24</struts.version>
	</properties>
	<!-- 锁定版本，struts2-2.3.24、spring4.2.4、hibernate5.0.7 -->
```
后面的plugin 信息，根据自己的电脑设置自行配置
## 搭建struts2环境
### 1创建Struts2配置文件 ：struts.xml
```
<!-- 配置Struts核心过滤器 -->
	<filter>
		<filter-name>struts</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>struts</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
## 搭建spring环境
1. 创建spring配置文件applicationContext.xml  
2. 在web.xml中配置监听器：ContextLoaderListener  
```
<!-- 配置监听器：默认加载WEB-INF/applicationContext.xml -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!-- 通过上下文参数指定spring配置文件路径 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>
```
## 搭建Hibernate环境
创建hibernate核心配置文件
```
<hibernate-configuration>
	<!-- 会话工厂 -->
	<session-factory>
		<!-- 数据库方言，根据数据库选择 -->
	    <property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>

		<!--为了方便调试是否在运行hibernate时在日志中输出sql语句 -->
		<property name="hibernate.show_sql">true</property>
		<!-- 是否对日志中输出的sql语句进行格式化 -->
		<property name="hibernate.format_sql">true</property>
	
		<property name="hibernate.hbm2ddl.auto">update</property>
		
		<!-- 加载映射文件 -->
		<mapping resource="com/coderlong/domain/Customer.hbm.xml"/>
	</session-factory>
</hibernate-configuration>
```
## Struts2跟spring整合
1. 创建action类
2. 将action对象配置到spring配置文件中
3. 在struts.xml中在action节点中class属性配置为spring工厂中action对象bean的id
## Spring跟Hibernate框架整合
1. 数据源dataSource交给spring
2. SessionFactory对象创建交给spring创建
3. 事务管理
配置DataSource
```
<!--加载属性文件  -->	
		<context:property-placeholder location="classpath:db.properties" />

		<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
			<property name="driverClass" value="${jdbc.driverClass}"></property>
			<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
			<property name="user" value="${jdbc.user}"></property>
			<property name="password" value="${jdbc.password}"></property>
		</bean>
```
配置sessionfactory
```
<bean id="sessionFactory"
			class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
			<property name="dataSource" ref="dataSource"></property>
			<property name="configLocations" value="classpath:hibernate.cfg.xml"></property>
		</bean>
```
事务管理
```
<!-- 配置事物管理器 -->
		<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
			<property name="sessionFactory" ref="sessionFactory"></property>
		</bean>
```
管理事务 有两种方法 基于xml 基于注解
```
<!-- xml方式管理事物  -->
		<tx:advice id="txAdvice">
			<tx:attributes>
				<!-- 匹配业务方法中的名称 什么样的方法会开启通知  -->		
				<tx:method name="save*"/>
				<tx:method name="update*"/>
				<tx:method name="delete*"/>
				<tx:method name="find*" read-only="true"/>
				<tx:method name="*"/>
			</tx:attributes>
		</tx:advice>
```
```
<aop:config>
		<!-- 配置切点：具体哪些方法要增强（真正被增强的方法）-->
		<aop:pointcut expression="execution(* com.coderlong.service.*.*(..))" id="cut"/>
		<!-- 配置切面：将增强逻辑作用到切点  （通知+切入点） -->
		<aop:advisor advice-ref="txAdvice" pointcut-ref="cut"/>
	</aop:config>
		<!-- xml方式管理事物  -->
		
		
		<!--注解方式管理事务  -->
		<!--1 开启注解驱动  2 咋service 类上或者service方法上面使用注解 @Transactional-->
		
		<tx:annotation-driven transaction-manager="transactionManager"/>
		<!--注解方式管理事务  -->
```
最后就是根据自己的事务需求，mvc架构配置到spring就行了 
```
	<!--配置action对象   -->
		<!-- 配置dao对象 -->
	<bean id="customerDao" class="com.coderlong.dao.impl.CustomerDaoImpl">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	<!-- 配置service对象 -->
	<bean id="customerService" class="com.coderlong.service.impl.CustomerServiceImpl">
		<property name="customerDao" ref="customerDao"></property>
	</bean>
	
	<!-- 配置action对象 -->
	<bean id="customerAction" class="ocm.coderlong.web.action.CustomerAction" scope="prototype">
		<property name="customerService" ref="customerService"></property>
	</bean>
		
```
欢迎star我:smile::wink:










