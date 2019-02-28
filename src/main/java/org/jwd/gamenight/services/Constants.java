package org.jwd.gamenight.services;


/**
 * Class static constant field
 *
 * @author e.dobreva
 */
public class Constants
{
    public static final String DATE_REGEX_PATTERN = "((2)[0-9]{3})(-)((0[0-9])|(1[0-2]))(-)([0-3][0-9])";
    public static final String EMAIL_REGEX_PATTERN = "([a-zA-z]{1}[A-Za-z0-9!#$%&'*+-/=?^_`{|}~]+)(@)([a-z]+)(.[a-z]{2,5})+";
    public static final String CREATE_TASK_PATH = "/createTask";
    public static final String REGISTER_PATH = "/register";
    public static final String EDIT_USER_PATH = "/editUser";
    public static final String HOME_PATH = "/";
    public static final String EDIT_TASK_PATH = "/editTask";
    public static final String GARBADGE_PATH = "/garbadge";
    public static final String ALL_TASKS_PATH = "/allTasks";
    public static final String ALL_USERS_PATH = "/allUsers";
    public static final String VIEW_PAGE_PATH = "/view";
    public static final String CONFIG_PATH = "/config/";
    public static final String[] PAGE_PATHS_ARR = new String[]{HOME_PATH, REGISTER_PATH, EDIT_USER_PATH, CREATE_TASK_PATH, EDIT_TASK_PATH,
                                                               GARBADGE_PATH, ALL_TASKS_PATH, ALL_USERS_PATH, CONFIG_PATH, VIEW_PAGE_PATH};
}
