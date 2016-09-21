package org.apache.ddlutils.io;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformBuilder;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Types;
import java.util.Iterator;

/**
 * Tests the database XML reading/writing via the {@link DatabaseIO} class.
 *
 * @version $Revision: 289996 $
 */
public class TestH2DatabaseIO extends TestCase
{
    /**
     * Tests an XML document without a database element.
     */
    public void testgeDomainXml() throws Exception
    {
        final String schema =
                "<?xml version='1.0' encoding='ISO-8859-1'?>\n" +
                        "<database xmlns='" + DatabaseIO.DDLUTILS_NAMESPACE + "' name='datatypetest'>\n" +
                        "  <table name='coltype'>\n" +
                        "    <column name='COL_ARRAY'           type='ARRAY'/>\n" +
                        "    <column name='COL_BIGINT'          type='BIGINT'/>\n" +
                        "    <column name='COL_BINARY'          type='BINARY'/>\n" +
                        "    <column name='COL_BIT'             type='BIT'/>\n" +
                        "    <column name='COL_BLOB'            type='BLOB'/>\n" +
                        "    <column name='COL_BOOLEAN'         type='BOOLEAN'/>\n" +
                        "    <column name='COL_CHAR'            type='CHAR' size='15'/>\n" +
                        "    <column name='COL_CLOB'            type='CLOB'/>\n" +
                        "    <column name='COL_DATALINK'        type='DATALINK'/>\n" +
                        "    <column name='COL_DATE'            type='DATE'/>\n" +
                        "    <column name='COL_DECIMAL'         type='DECIMAL' size='15,3'/>\n" +
                        "    <column name='COL_DECIMAL_NOSCALE' type='DECIMAL' size='15'/>\n" +
                        "    <column name='COL_DISTINCT'        type='DISTINCT'/>\n" +
                        "    <column name='COL_DOUBLE'          type='DOUBLE'/>\n" +
                        "    <column name='COL_FLOAT'           type='FLOAT'/>\n" +
                        "    <column name='COL_INTEGER'         type='INTEGER'/>\n" +
                        "    <column name='COL_JAVA_OBJECT'     type='JAVA_OBJECT'/>\n" +
                        "    <column name='COL_LONGVARBINARY'   type='LONGVARBINARY'/>\n" +
                        "    <column name='COL_LONGVARCHAR'     type='LONGVARCHAR'/>\n" +
                        "    <column name='COL_NULL'            type='NULL'/>\n" +
                        "    <column name='COL_NUMERIC'         type='NUMERIC' size='15' />\n" +
                        "    <column name='COL_OTHER'           type='OTHER'/>\n" +
                        "    <column name='COL_REAL'            type='REAL'/>\n" +
                        "    <column name='COL_REF'             type='REF'/>\n" +
                        "    <column name='COL_SMALLINT'        type='SMALLINT' size='5'/>\n" +
                        "    <column name='COL_STRUCT'          type='STRUCT'/>\n" +
                        "    <column name='COL_TIME'            type='TIME'/>\n" +
                        "    <column name='COL_TIMESTAMP'       type='TIMESTAMP'/>\n" +
                        "    <column name='COL_TINYINT'         type='TINYINT'/>\n" +
                        "    <column name='COL_VARBINARY'       type='VARBINARY' size='15'/>\n" +
                        "    <column name='COL_VARCHAR'         type='VARCHAR' size='15'/>\n" +
                        "  </table>\n" +
                        "</database>";
        DatabaseIO dbIO = new DatabaseIO();

        dbIO.setUseInternalDtd(true);
        dbIO.setValidateXml(true);
        Database db=dbIO.read(new StringReader(schema));
        dbIO.write(db,"/tmp/aaaaaa1.xml");
        dbIO.read("/tmp/aaaaaa1.xml");
    }


    public void testMysql2Xml() throws Exception{
        String jdbcDriver="org.h2.Driver";
        //String jdbcUrl="jdbc:h2:mem:tmp-domain-db;DB_CLOSE_DELAY=-1";
        String jdbcUrl="jdbc:h2:/tmp/tmp-domain-db";
        String jdbcUser="root";
        String jdbcPassword="root";

        String schemaFile="/tmp/domain.xml";
        String dataFile="/tmp/domain.data.xml";

        Platform pf = PlatformBuilder.builder()
                .jdbcDriver(jdbcDriver)
                .jdbcUrl(jdbcUrl)
                .jdbcUser(jdbcUser)
                .jdbcPassword(jdbcPassword)
                .build();

        Database db = new DatabaseIO().read(new File(schemaFile));

        try {
            pf.setDelimitedIdentifierModeOn(true);
            String sql=pf.getCreateTablesSql(db,true,false);
            System.out.println(sql);
            pf.createTables(db,false,false);

            //导入数据
            new DatabaseDataIO().writeDataToDatabase(pf,
                    db,new String[] {dataFile});

        } catch (DatabaseOperationException e) {
            System.err.print("error//");
            e.printStackTrace();
        }
    }
}
