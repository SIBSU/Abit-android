package ha.rigfox.appforfanzil;

import java.util.ArrayList;

class NetResponseParent {
    Boolean status;
    String error;
}

class NetLoginRequest {
    String ID;
    String password;
}

class NetLoginResponse extends NetResponseParent {
    String token;
}

class NetDataRequest {
    String token;
}

class NetDataSubject {
    String name;
    int score;
    int rating;
    int ratingInterOriginals;
    int countBudget;
    int countAbit;
    boolean isOriginal;
}

class NetDataResponse extends NetResponseParent {
    ArrayList<NetDataSubject> subjectsList;
    int updateTime;
}

class NetChatRequest {
    String token;
    String message;
}

class NetChatResponse extends NetResponseParent {

}

class NetChatUpdateRequest {
    String token;
}

class NetDataChatMessage {
    String message;
    int time;
    String userName;
}

class  NetChatUpdateResponse extends NetResponseParent {
    ArrayList<NetDataChatMessage> messagesList;
}