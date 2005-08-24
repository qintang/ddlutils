package org.apache.ddlutils.builder;

/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import org.apache.ddlutils.PlatformInfo;
import org.apache.ddlutils.model.Table;

/**
 * The SQL Builder for the HsqlDb database.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @author <a href="mailto:tomdz@apache.org">Thomas Dudziak</a>
 * @version $Revision$
 */
public class HsqlDbBuilder extends SqlBuilder
{
    /**
     * Creates a new builder instance.
     * 
     * @param info The platform info
     */
    public HsqlDbBuilder(PlatformInfo info)
    {
        super(info);
    }

    /* (non-Javadoc)
     * @see org.apache.ddlutils.builder.SqlBuilder#dropTable(Table)
     */
    public void dropTable(Table table) throws IOException
    { 
        print("DROP TABLE ");
        print(getTableName(table));
        print(" IF EXISTS");
        printEndOfStatement();
    }
}