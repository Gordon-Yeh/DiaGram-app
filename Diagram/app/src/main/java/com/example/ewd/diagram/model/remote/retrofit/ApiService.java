package com.example.ewd.diagram.model.remote.retrofit;



import com.example.ewd.diagram.model.local.AuthResponse;
import com.example.ewd.diagram.model.local.CommentBody;
import com.example.ewd.diagram.model.local.LoginCredentials;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.local.PostBody;
import com.example.ewd.diagram.model.local.SignUpCredentials;
import com.example.ewd.diagram.model.local.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * All API calls
 */
public interface ApiService {

//Login
@POST("/login")
Call<AuthResponse> getLoginResponse(@Body LoginCredentials loginCredentials);

//Sign up
@POST("/signup")
Call<AuthResponse> getSignUpResponse(@Body SignUpCredentials signUpCredentials);

//Get all posts
@GET("/posts")
Call<List<Post>> getAllPosts(@Header("Authorization") String token);

//Get all posts
@GET("/posts/followed")
Call<List<Post>> getFollowingPosts(@Header("Authorization") String token);


//Get single post
@GET("/posts")
Call<List<Post>> getPost(@Header("Authorization") String token, @Query("post_id") String postId);


//Add new post
@POST("/posts")
Call<Post> addNewPost(@Header("Authorization") String token, @Body PostBody postBody);


//Add comment
@POST("/posts/{post_id}/comments")
Call<Post> addComment(@Header("Authorization") String token, @Path("post_id") String postId, @Body CommentBody body);

//Edit profile
@PUT("/users")
Call<User> editProfile(@Header("Authorization") String token, @Body User user);


//Get profile
@GET("/users/{user_id}")
Call<User> getProfile(@Header("Authorization") String token, @Path("user_id") String user_id);







}
