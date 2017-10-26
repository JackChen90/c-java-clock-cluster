package indi.jackie.clock.cluster;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@MapperScan({"cn.migu.athena.dao"})
public class MyBatisConfig implements TransactionManagementConfigurer {
    @Autowired
    DataSource dataSource;

    public MyBatisConfig() {
    }

    @Bean(
        name = {"sqlSessionFactory"}
    )
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.dataSource);
        bean.setTypeAliasesPackage("cn.migu.athena.domain");
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            bean.setMapperLocations(resolver.getResources("classpath:cn/migu/athena/mapper/*.xml"));
            return bean.getObject();
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new RuntimeException(var6);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }
}
