package org.opentosca.toscana.core.integration.upload;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TOSCAnaUploadInterface {
    @GET("api/status/health")
    Call<ResponseBody> getStatus();

    @Multipart
    @POST("api/csars/{checkStateNoPropsSet}")
    Call<ResponseBody> upload(
        @Part MultipartBody.Part body,
        @Path("checkStateNoPropsSet") String name
    );
}