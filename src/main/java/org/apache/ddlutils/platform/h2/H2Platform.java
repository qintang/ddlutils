package org.apache.ddlutils.platform.h2;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformInfo;
import org.apache.ddlutils.platform.PlatformImplBase;

import java.sql.Types;

/**
 * Created by maoren on 16-9-21.
 */
/*
 * The platform implementation for the H2 database. From patch at <a
 * href="https://issues.apache.org/jira/browse/DDLUTILS-185"
 * >https://issues.apache.org/jira/browse/DDLUTILS-185</a>
 *
 * @version $Revision: 231306 $
 */
public class H2Platform extends PlatformImplBase implements Platform {

    /* Database name of this platform. */
    public static final String[] DATABASENAMES = {"h2","h21","H2 JDBC Driver"};

    /* The standard H2 driver. */
    public static final String JDBC_DRIVER = "org.h2.Driver";

    /* The sub protocol used by the H2 driver. */
    public static final String JDBC_SUBPROTOCOL = "h2";

    /*
     * Creates a new instance of the H2 platform.
     */
    public H2Platform() {
        PlatformInfo info = getPlatformInfo();

        //info.setNonPKIdentityColumnsSupported(false);
        info.setIdentityOverrideAllowed(false);
        info.setSystemForeignKeyIndicesAlwaysNonUnique(true);
        info.setNullAsDefaultValueRequired(false);
        info.addNativeTypeMapping(Types.ARRAY, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.DISTINCT, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.NULL, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.REF, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.STRUCT, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.DATALINK, "BINARY", Types.BINARY);

        info.addNativeTypeMapping(Types.BIT, "BOOLEAN", Types.BIT);;
        info.addNativeTypeMapping(Types.NUMERIC, "DECIMAL", Types.DECIMAL);
        info.addNativeTypeMapping(Types.BINARY, "BINARY", Types.BINARY);
        info.addNativeTypeMapping(Types.BLOB, "BLOB", Types.BLOB);
        info.addNativeTypeMapping(Types.CLOB, "CLOB", Types.CLOB);
        info.addNativeTypeMapping(Types.LONGVARCHAR, "VARCHAR", Types.VARCHAR);
        info.addNativeTypeMapping(Types.FLOAT, "DOUBLE", Types.DOUBLE);
        info.addNativeTypeMapping(Types.JAVA_OBJECT, "OTHER");

        info.setDefaultSize(Types.CHAR, Integer.MAX_VALUE);
        info.setDefaultSize(Types.VARCHAR, Integer.MAX_VALUE);
        info.setDefaultSize(Types.BINARY, Integer.MAX_VALUE);
        info.setDefaultSize(Types.VARBINARY, Integer.MAX_VALUE);

        //info.setStoresUpperCaseInCatalog(true);

        setSqlBuilder(new H2Builder(this));
        setModelReader(new H2ModelReader(this));
    }

    /*
     * {@inheritDoc}
     */
    public String getName() {
        return DATABASENAMES[0];
    }

    /**
     * h2 必须打开
     * */
    @Override
    public boolean isDelimitedIdentifierModeOn() {
        return true;
    }
}