package com.demo.bs.demoapp2.constants;

/**
 * Created by wangjun on 3/18/17.
 */

public final class AppConstant {

    public static final class ActivityRequestCode {
        public static final int ACCOUNT_KIT_LOGIN = 101;
        public static final int REQUEST_ALBUM_IMG = 105;
        public static final int REQUEST_CAMERA_IMG = 106;
        public static final int SETTING_PERMISSION = 109;
    }

    public static final class Common {
        public static final int TEXT_CHAT_INT_TO_MAIN_ACTIVITY = 1;
        public static final int LOGOUT_TINTEN_KEY = 2;
    }


    public static final class PermissionRequestCode {
        private static final int BASE_VALUE_PERMISSION = 0X0001;
        public static final int RECORD_AUDIO = BASE_VALUE_PERMISSION + 1;
        public static final int CAMERA = BASE_VALUE_PERMISSION + 2;
        public static final int WRITE_EXTERNAL_STORAGE = BASE_VALUE_PERMISSION + 3;
        public static final int READ_CONTACTS = BASE_VALUE_PERMISSION + 4;
        public static final int READ_PHONE_STATE = BASE_VALUE_PERMISSION + 5;
        public static final int ACCESS_FINE_LOCATION = BASE_VALUE_PERMISSION + 6;
    }

    public static final class HttpResponseCode {
        public static final int SUCCESS = 200;
        public static final int INVALID_TOKEN = 3;
        public static final int NOT_ENOUGH_GEM = 7;
        public static final int ALREADY_FRIEND = 19;
        public static final int PAYMENT_REPEAT_CODE = 26;
        public static final int LOG_OUT = 401;
        public static final int NAME_INVALID = 400;
    }

    public static final class SharedPreferenceKey {
        public static final String CURRENT_UID = "current_uid";
        public static final String CURRENT_TOKEN = "current_token";
        public static final String ACCOUNTKIT_ACTION = "Accountkit_action";//这个是判断用户是老用户还是新注册用户

        public static final boolean MATCH_1ST_REQUEST = false;//新用户24小时内是否打过这个点MATCH_1ST_REQUEST
        public static final boolean MATCH_1ST_RECEIVED = false;//新用户24小时内是否打过这个点MATCH_1ST_RECEIVED
        public static final boolean MATCH_1ST_SUCCESS = false;//新用户24小时内是否打过这个点MATCH_1ST_SUCCESS
        public static final boolean MATCH_1ST_ADDTIME = false;//新用户24小时内是否打过这个点MATCH_1ST_ADDTIME
        public static final boolean MATCH_1ST_ADDFRIEND = false;//新用户24小时内是否打过这个点MATCH_1ST_ADDFRIEND

        public static final String CAMERA_PERMISSION_NEVER_ASK = "CAMERA_PERMISSION_NEVER_ASK";
        public static final String HAVE_REJECT_LOCATION_PERMISSION = "HAVE_REJECT_LOCATION_PERMISSION";
        public static final String START_USER_APP_TIME = "START_USER_APP_TIME";
        public static final String APP_USER_ALL_TIME = "APP_USER_ALL_TIME";
    }

    public static final class IntentKey {
        public static final String INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_RELATION_USER = "INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_RELATION_USER";
        public static final String INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_USER_ID = "INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_USER_ID";
        public static final String INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_FRIEND_SHIP_ID = "INTENT_TO_TEXT_CHAT_ACTIVITY_WITH_FRIEND_SHIP_ID";
        public static final String INTENT_TO_MAIN_ACTIVITY_WHEN_TEXT_CHAT_FINISH = "INTENT_TO_MAIN_ACTIVITY_WHEN_TEXT_CHAT_FINISH";
        public static final String INTENT_TO_PROFILE_ACTIVITY_WITH_CURRENT_USER_INFO = "INTENT_TO_PROFILE_ACTIVITY_WITH_CURRENT_USER_INFO";
        public static final String INTENT_TO_WEBVIEW_ACTIVITY_WITH_URL = "INTENT_TO_WEBVIEW_ACTIVITY_WITH_URL";
        public static final String INTENT_TO_LOGIN_ACTIVITY_WITH_INTEN_KEY = "INTENT_TO_LOGIN_ACTIVITY_WITH_INTEN_KEY";
    }

    public static final class ReceiveMessageType {
        public static final String CHAT = "chat";
        public static final String MATCHED_USER = "matched_user";
        public static final String JSON_API_DATA = "json_api_data";
        public static final String PAUSECAMERA = "pause_camera";
        public static final String RESUMEAMERA = "resume_camera";
        public static final String LOG_OUT_RESPONSE_MESSAGE = "Unauthorized";
        public static final String ADD_FRIEND_SUCCESS = "addFriendSuccess";
        public static final String SCREENSHOTSUCCESS = "screenShotSuccess";
    }

    public static final class MatchRateEventType {
        public static final String POUTING = "bad";
        public static final String NEUTRAL = "neutral";
        public static final String SMILING = "good";
    }

    public static final class MatchRateType {
        public static final int POUTING = -1;
        public static final int NEUTRAL = 0;
        public static final int SMILING = 1;
    }

    public static final class ActivityBundle {
        public static final String LOAD_URL = "LOAD_URL";
        public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
        public static final String TITLE_TEXT = "TITLE_TEXT";
        public static final String CHAT_MESSAGE = "CHAT_MESSAGE";
        public static final String STORE_CHANNEL = "STORE_CHANNEL";
        public static final String NOTIFICATION_LAUNCH_TO_WELCOME = "NOTIFICATION_LAUNCH_TO_WELCOME";

    }


}
