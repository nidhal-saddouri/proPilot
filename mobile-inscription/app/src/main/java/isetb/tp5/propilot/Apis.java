package isetb.tp5.propilot;
public class Apis {
    public static final String URL="http://172.16.25.122:8080";
    public static UserService getService(){
        return RetrofitClient.getClient(URL).create(UserService.class);
    }
}