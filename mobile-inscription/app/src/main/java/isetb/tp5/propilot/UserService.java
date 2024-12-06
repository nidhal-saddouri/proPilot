package isetb.tp5.propilot;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/users/AddUser")
    Call<Users> registerUser(@Body Users user);
}
