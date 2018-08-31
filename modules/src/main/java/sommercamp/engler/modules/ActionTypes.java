package sommercamp.engler.modules;

public enum ActionTypes {
    // Client
    REGISTER,
    LOGIN,
    SEND_MESSAGE,


    // Server
    SHOW_ACCESS_KEY,
    ADD_USER,
    REMOVE_USER,
    ADD_MESSAGE,
    ACCESS_KEY_IS_NOT_VALID,
    USERNAME_ALREADY_IN_USE,
    LOGIN_SUCCESS
}
