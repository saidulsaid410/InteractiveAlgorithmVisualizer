
package com.project.interactivealgorithmvisualizer;

public class SessionManager {

    private static int currentUserId = -1;
    private static String currentUserEmail = null;

    public static void setCurrentUser(int userId, String email) {
        currentUserId = userId;
        currentUserEmail = email;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }

    public static void logout() {
        currentUserId = -1;
        currentUserEmail = null;
    }
}

