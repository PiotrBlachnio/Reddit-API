package com.piotrblachnio.reddit.constants;

public class ApiRoutes {
    private static final String _versionPrefix = "/api/v1";

    public static class Auth {
        private static final String _prefix = _versionPrefix + "/auth";

        public static final String REGISTER = _prefix + "/register";
        public static final String LOGIN = _prefix + "/login";
        public static final String LOGOUT = _prefix + "/logout";
        public static final String REFRESH_TOKEN = _prefix + "/refresh-token";
        public static final String CONFIRM_EMAIL = _prefix + "/confirm-email/{token}";
    }

    public static class Comment {
        private static final String _prefix = _versionPrefix + "/comment";

        public static final String CREATE = _prefix;
        public static final String GET_BY_POST = _prefix + "/by-post/{postId}";
        public static final String GET_BY_USER = _prefix + "/by-user/{username}";
    }
}
