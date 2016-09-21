package org.apache.ddlutils.platform.h2;

import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.*;
import org.apache.ddlutils.platform.DatabaseMetaDataWrapper;
import org.apache.ddlutils.platform.JdbcModelReader;
import org.apache.ddlutils.platform.MetaDataColumnDescriptor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by maoren on 16-9-21.
 */
public class H2ModelReader extends JdbcModelReader {

    /*
     * Creates a new model reader for H2 databases.
     *
     * @param platform The platform that this model reader belongs to
     */
    public H2ModelReader(Platform platform) {
        super(platform);
        setDefaultCatalogPattern(null);
        setDefaultSchemaPattern(null);
    }

    @Override
    protected Column readColumn(DatabaseMetaDataWrapper metaData, Map values)
            throws SQLException {
        Column column = super.readColumn(metaData, values);
        if (values.get("CHARACTER_MAXIMUM_LENGTH") != null) {
            column.setSize(values.get("CHARACTER_MAXIMUM_LENGTH").toString());
        }
        if (values.get("COLUMN_DEFAULT") != null) {
            column.setDefaultValue(values.get("COLUMN_DEFAULT").toString());
        }
        if (values.get("NUMERIC_SCALE") != null) {
            column.setScale((Integer) values.get("NUMERIC_SCALE"));
        }
        if (TypeMap.isTextType(column.getTypeCode()) && (column.getDefaultValue() != null)) {
            column.setDefaultValue(unescape(column.getDefaultValue(), "'", "''"));
        }

        String autoIncrement = (String)values.get("IS_AUTOINCREMENT");
        if (autoIncrement != null) {
            column.setAutoIncrement("YES".equalsIgnoreCase(autoIncrement.trim()));
        }
        return column;
    }

    @Override
    protected List<MetaDataColumnDescriptor> initColumnsForColumn() {
        List<MetaDataColumnDescriptor> result = super.initColumnsForColumn();
        result.add(new MetaDataColumnDescriptor("COLUMN_DEFAULT", 12));
        result.add(new MetaDataColumnDescriptor("NUMERIC_SCALE", 4, new Integer(0)));
        result.add(new MetaDataColumnDescriptor("CHARACTER_MAXIMUM_LENGTH", 12));
        return result;
    }

    @Override
    protected boolean isInternalForeignKeyIndex(DatabaseMetaDataWrapper metaData, Table table,
                                                ForeignKey fk, Index index) {
        String name = index.getName();
        return name != null && name.startsWith("CONSTRAINT_INDEX_");
    }

    @Override
    protected boolean isInternalPrimaryKeyIndex(DatabaseMetaDataWrapper metaData, Table table,Index index) {
        String name = index.getName();
        return name != null && name.startsWith("PRIMARY_KEY_");
    }

}
