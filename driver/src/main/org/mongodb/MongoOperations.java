/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb;

import org.bson.types.Document;
import org.mongodb.operation.GetMore;
import org.mongodb.operation.MongoCommand;
import org.mongodb.operation.MongoFind;
import org.mongodb.operation.MongoInsert;
import org.mongodb.operation.MongoKillCursor;
import org.mongodb.operation.MongoRemove;
import org.mongodb.operation.MongoReplace;
import org.mongodb.operation.MongoUpdate;
import org.mongodb.result.CommandResult;
import org.mongodb.result.QueryResult;
import org.mongodb.result.WriteResult;
import org.mongodb.serialization.Serializer;

public interface MongoOperations {

    // TODO: should this really be a separate call from query?
    CommandResult executeCommand(String database, MongoCommand commandOperation,
                                 Serializer<Document> serializer);

    <T> QueryResult<T> query(final MongoNamespace namespace, MongoFind find, Serializer<Document> baseSerializer,
                             Serializer<T> serializer);

    <T> QueryResult<T> getMore(final MongoNamespace namespace, GetMore getMore, Serializer<T> serializer);

    void killCursors(MongoKillCursor killCursor);

    <T> WriteResult insert(MongoNamespace namespace, MongoInsert<T> insert, Serializer<T> serializer,
                           final Serializer<Document> baseSerializer);

    WriteResult update(final MongoNamespace namespace, MongoUpdate update, Serializer<Document> serializer);

    <T> WriteResult replace(MongoNamespace namespace, MongoReplace<T> replace, Serializer<Document> baseSerializer,
                            Serializer<T> serializer);

    WriteResult remove(final MongoNamespace namespace, MongoRemove remove, Serializer<Document> serializer);
}
