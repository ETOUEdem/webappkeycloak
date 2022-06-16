/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.models;


import java.util.Map;

import org.keycloak.sessions.CommonClientSessionModel;
import org.keycloak.storage.SearchableModelField;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public interface AuthenticatedClientSessionModel extends CommonClientSessionModel {

    class SearchableFields {
        public static final SearchableModelField<AuthenticatedClientSessionModel> ID      = new SearchableModelField<>("id", String.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> REALM_ID = new SearchableModelField<>("realmId", String.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> CLIENT_ID = new SearchableModelField<>("clientId", String.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> USER_SESSION_ID = new SearchableModelField<>("userSessionId", String.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> IS_OFFLINE = new SearchableModelField<>("isOffline", Boolean.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> TIMESTAMP  = new SearchableModelField<>("timestamp", Long.class);
        public static final SearchableModelField<AuthenticatedClientSessionModel> EXPIRATION  = new SearchableModelField<>("expiration", Long.class);
    }

    String STARTED_AT_NOTE = "startedAt";

    String getId();

    default int getStarted() {
        String started = getNote(STARTED_AT_NOTE);
        // Fallback to 0 if "started" note is not available. This can happen for the offline sessions migrated from old version where "startedAt" note was not yet available
        return started == null ? 0 : Integer.parseInt(started);
    }

    int getTimestamp();
    void setTimestamp(int timestamp);

    /**
     * Detaches the client session from its user session.
     */
    void detachFromUserSession();
    UserSessionModel getUserSession();

    String getCurrentRefreshToken();
    void setCurrentRefreshToken(String currentRefreshToken);

    int getCurrentRefreshTokenUseCount();
    void setCurrentRefreshTokenUseCount(int currentRefreshTokenUseCount);

    String getNote(String name);
    void setNote(String name, String value);
    void removeNote(String name);
    Map<String, String> getNotes();
}
