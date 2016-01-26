/*
 * Copyright (C) 2015 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.prjdagger2.global.datasource.api;

import java.util.ArrayList;
import java.util.List;

import es.voghdev.prjdagger2.global.datasource.GetUsers;
import es.voghdev.prjdagger2.global.datasource.api.rest.model.UserApiEntry;
import es.voghdev.prjdagger2.global.datasource.api.rest.GetUsersRetrofitRequest;
import es.voghdev.prjdagger2.global.model.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetUsersApiImpl extends GetUsers implements Callback<GetUsersResponse> {
    private static final String ENDPOINT = "http://api.randomuser.me";
    private int pageSize = 0;
    private int pageNumber = 0;

    public GetUsersApiImpl(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public void getUsers() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
        GetUsersRetrofitRequest request = restAdapter.create(GetUsersRetrofitRequest.class);
        request.getRandomUsers(pageSize, pageNumber, this);
    }

    @Override
    public void success(GetUsersResponse getUsersResponse, Response response) {
        List<User> users = new ArrayList<User>();

        for(UserApiEntry entry :  getUsersResponse.getResults()){
            users.add( entry.parseUser() );
        }

        listener.onUsersListReceived(users);
    }

    @Override
    public void failure(RetrofitError error) {

    }
}