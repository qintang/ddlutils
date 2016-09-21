# Apache DdlUtils 
  forked from [apache/ddlutils][1] 添加h2支持

##ddlutils [doc][2]

  
##用途,单元测试 
 * 单元测试使用h2作为底层数据库,将生产环境的shecme和data放到h2中,实现插件[ddlutils-maven-plugin][7]
 * 数据备份 
 * 不同数据库迁移数据

# 参考
* [symmetric-ds][3] 数据同步工具,参考symmetric-ddl(未找到源码库)[mvn仓库][4]
* [google play ddlutils supports][6] Google
* 相同fork [rubenqba/ddlutils][5]

  
[1]: https://github.com/apache/ddlutils
[2]: http://db.apache.org/ddlutils/api-usage.html#Inserting+data+into+a+database
[3]: https://github.com/JumpMind/symmetric-ds
[4]: http://www.mvnrepository.com/artifact/org.jumpmind.symmetric/symmetric-ddl
[5]: https://github.com/rubenqba/ddlutils
[6]: http://www.mvnrepository.com/artifact/com.google.code.maven-play-plugin.org.apache.ddlutils/ddlutils
[7]:https://github.com/qintang/ddlutils-maven-plugin
