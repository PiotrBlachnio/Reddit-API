package com.piotrblachnio.reddit.constants;

public class ApiRoutes {
    private static final String _prefix = "/api/v1";

    public static class Auth {
        public static final String REGISTER = _prefix + "/auth/register";
        public static final String LOGIN = _prefix + "/auth/login";
        public static final String LOGOUT = _prefix + "/auth/logout";
        public static final String REFRESH_TOKEN = _prefix + "/auth/refresh-token";
        public static final String CONFIRM_EMAIL = _prefix + "/auth/confirm-email/{token}";
    }
}
