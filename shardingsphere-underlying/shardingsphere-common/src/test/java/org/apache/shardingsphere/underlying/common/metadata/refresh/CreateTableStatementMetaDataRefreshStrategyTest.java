/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.underlying.common.metadata.refresh;

import lombok.SneakyThrows;
import org.apache.shardingsphere.sql.parser.binder.metadata.column.ColumnMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.index.IndexMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaData;
import org.apache.shardingsphere.sql.parser.binder.statement.ddl.CreateTableStatementContext;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.table.TableNameSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.CreateTableStatement;
import org.apache.shardingsphere.sql.parser.sql.value.identifier.IdentifierValue;
import org.apache.shardingsphere.underlying.common.metadata.refresh.impl.CreateTableStatementMetaDataRefreshStrategy;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class CreateTableStatementMetaDataRefreshStrategyTest extends AbstractMetaDataRefreshStrategyTest {
    
    @SneakyThrows
    @Test
    public void refreshMetaData() {
        MetaDataRefreshStrategy<CreateTableStatementContext> metaDataRefreshStrategy = new CreateTableStatementMetaDataRefreshStrategy();
        CreateTableStatement createTableStatement = new CreateTableStatement(new SimpleTableSegment(new TableNameSegment(1, 3, new IdentifierValue("t_order_0"))));
        CreateTableStatementContext createTableStatementContext = new CreateTableStatementContext(createTableStatement);
        metaDataRefreshStrategy.refreshMetaData(getMetaData(), createTableStatementContext, tableName -> Optional.of(new TableMetaData(
                Collections.singletonList(new ColumnMetaData("order_id", 1, "String", true, false, false)),
                Collections.singletonList(new IndexMetaData("index")))));
        assertThat(getMetaData().getSchema().getConfiguredSchemaMetaData().containsTable("t_order_0"), is(true));
    }
}
