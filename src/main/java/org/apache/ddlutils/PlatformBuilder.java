package org.apache.ddlutils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by maoren on 16-9-21.
 */
public class PlatformBuilder {
    private static PlatformBuilder builder;

    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    private PlatformBuilder() {

    }

    public static PlatformBuilder builder() {
        if (builder == null)
            builder = new PlatformBuilder();
        return builder;
    }

    public PlatformBuilder jdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        return this;
    }

    public PlatformBuilder jdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public PlatformBuilder jdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
        return this;
    }

    public PlatformBuilder jdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
        return this;
    }

    public Platform build() {
        DruidDataSource dataSource =new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriver);//驱动程序
        dataSource.setUrl(jdbcUrl);//访问地址
        dataSource.setUsername(jdbcUser);//用户名
        dataSource.setPassword(jdbcPassword);//密码
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        return platform;
    }
}
