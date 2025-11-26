package com.example.appfood.controller;

import com.example.appfood.model.User;
import com.example.appfood.repository.UserRepository;
import com.example.appfood.repository.UserRepositoryImpl;

import java.util.List;

/**
 * Controller class for User operations
 * Acts as a bridge between View (Activity/Fragment) and Model (Repository)
 * Handles business logic for User-related operations
 * This will be fully implemented when user functionality is developed
 */
public class UserController {

    private final UserRepository userRepository;
    private static UserController instance;

    /**
     * Private constructor for singleton pattern
     */
    private UserController() {
        this.userRepository = UserRepositoryImpl.getInstance();
    }

    /**
     * Get singleton instance
     */
    public static synchronized UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    /**
     * Get all users
     * @param callback Callback to handle the result
     */
    public void getAllUsers(UserRepository.OnDataLoadedCallback<List<User>> callback) {
        userRepository.getAllUsers(callback);
    }

    /**
     * Get user by ID
     * @param userId The user ID
     * @param callback Callback to handle the result
     */
    public void getUserById(String userId, UserRepository.OnDataLoadedCallback<User> callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onFailure("User ID cannot be empty");
            return;
        }
        userRepository.getUserById(userId, callback);
    }

    /**
     * Get user by email
     * @param email The user email
     * @param callback Callback to handle the result
     */
    public void getUserByEmail(String email, UserRepository.OnDataLoadedCallback<User> callback) {
        if (email == null || email.trim().isEmpty()) {
            callback.onFailure("Email cannot be empty");
            return;
        }
        userRepository.getUserByEmail(email, callback);
    }

    /**
     * Create a new user with validation
     * @param user The user to create
     * @param callback Callback to handle success/failure
     */
    public void createUser(User user, UserRepository.OnOperationCallback callback) {
        // Validate user data
        String validationError = validateUser(user);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        // Check if username already exists
        userRepository.isUsernameExists(user.getUsername(), new UserRepository.OnDataLoadedCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean exists) {
                if (exists) {
                    callback.onFailure("Tên đăng nhập đã tồn tại");
                } else {
                    // Check if email already exists
                    userRepository.isEmailExists(user.getEmail(), new UserRepository.OnDataLoadedCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean exists) {
                            if (exists) {
                                callback.onFailure("Email đã được sử dụng");
                            } else {
                                userRepository.createUser(user, callback);
                            }
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            callback.onFailure(errorMessage);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    /**
     * Update an existing user
     * @param user The user with updated data
     * @param callback Callback to handle success/failure
     */
    public void updateUser(User user, UserRepository.OnOperationCallback callback) {
        // Validate user data
        String validationError = validateUser(user);
        if (validationError != null) {
            callback.onFailure(validationError);
            return;
        }

        if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
            callback.onFailure("User ID is required for update");
            return;
        }

        userRepository.updateUser(user, callback);
    }

    /**
     * Delete a user
     * @param userId The user ID to delete
     * @param callback Callback to handle success/failure
     */
    public void deleteUser(String userId, UserRepository.OnOperationCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onFailure("User ID cannot be empty");
            return;
        }
        userRepository.deleteUser(userId, callback);
    }

    /**
     * Validate user data
     * @param user The user to validate
     * @return Error message if validation fails, null if valid
     */
    private String validateUser(User user) {
        if (user == null) {
            return "User data cannot be null";
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }

        if (user.getUsername().length() < 3) {
            return "Tên đăng nhập phải có ít nhất 3 ký tự";
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email không được để trống";
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            return "Email không hợp lệ";
        }

        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            return "Họ tên không được để trống";
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }

        if (!user.getPhoneNumber().matches("^[0-9]{10,11}$")) {
            return "Số điện thoại không hợp lệ";
        }

        return null;
    }

    /**
     * Validate username format
     * @param username The username to validate
     * @return Error message if validation fails, null if valid
     */
    public String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }

        if (username.length() < 3) {
            return "Tên đăng nhập phải có ít nhất 3 ký tự";
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới";
        }

        return null;
    }

    /**
     * Validate password
     * @param password The password to validate
     * @return Error message if validation fails, null if valid
     */
    public String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Mật khẩu không được để trống";
        }

        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }

        return null;
    }
}

