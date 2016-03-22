package com.android.base.demo.network.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gagandeep on 22/3/16.
 */
public interface SampleApiEndpointInterface {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<User> createUser(@Body User user);

    /*@Path	variable substitution for the API endpoint (i.e. username will be swapped for {username} in the URL endpoint).
    @Query	specifies the query key name with the value of the annotated parameter.
    @Body	payload for the POST call (serialized from a Java object to a JSON string)
    @Header	specifies the header with the value of the annotated parameter*/



    /*Adding headers

    Notice that there is a @Headers and @Header annotation. The Headers can be used to provide pre-defined ones:

    @Headers({"Cache-Control: max-age=640000", "User-Agent: My-App-Name"})
    @GET("/some/endpoint")
    We can also add headers as a parameter to the endpoint:

    @Multipart
    @POST("/some/endpoint")
    Call<SomeResponse> someEndpoint(@Header("Cache-Control") int maxAge)*/




    /*Multipart forms

    If we need to upload images or files, we need to send by using Multipart forms. We will to mark the endpoint with @Multipart, and label at least one parameter with @Part.

    @Multipart
    @POST("some/endpoint")
            Call<Response> uploadImage(@Part("description") String description, @Part("image") RequestBody image)

    Assuming we have a reference to the file, we can create a RequestBody object:

    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    file = new File("/storage/emulated/0/Pictures/MyApp/test.png");
    RequestBody requestBody = RequestBody.create(MEDIA_TYPE_PNG, file);

    Call<Response> call = apiService.uploadImage("test", requestBody);
    If you need to specify a unique filename for your multipart upload, there is currently an issue in Retrofit 2 tracked in this ticket. Alternatively, you can create a multi-part RequestBody according to this OkHttp recipe guide and pass it along as one of the @Part annotated parameters:

    RequestBody requestBody = new MultipartBuilder()
            .type(MultipartBuilder.FORM)
            .addPart(
                    Headers.of("Content-Disposition", "form-data; name=\"mycustomfile.png\""),
                    RequestBody.create(MEDIA_TYPE_PNG, file))
            .build();*/





    /*Form URL encoding

    If we wish to POST form-encoded name/value pairs, we can use the @FormUrlEncoded and @FieldMap annotations:

    @FormUrlEncoded
    @POST("some/endpoint")
    Call<SomeResponse> someEndpoint(@FieldMap Map<String, String> names);*/






    /*POSTing JSON data

    Retrofit 2 will use the converter library chosen to handle the deserialization of data from a Java object. If you annotate the parameter with a @Body parameter, this work will be done automatically. If you are using the Gson library for instance, any field belonging to the class will be serialized for you. You can change this name using the @SerializedName decorator:

    Our endpoint would look like the following:

    @POST("/users/new")
    Call<User> createUser(@Body User user);*/
}
