package com.example.grpcdemo;


import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;


import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
import caribou.tasks.service.*;
import caribou.tasks.service.TasksApiServiceGrpc;
import io.grpc.ManagedChannelBuilder;



public class ListTaskTest {

    private final TasksApiServiceGrpc.TasksApiServiceBlockingStub taskStub;
    Context mcontext;

    public ListTaskTest(Context context) {
        mcontext = context;
        //获取账号
        if (checkSelfPermission(mcontext,Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions((Activity) mcontext,new String[]{Manifest.permission.GET_ACCOUNTS}, 1);
        }
        AccountManager manager = AccountManager.get(mcontext);
        Account[] accounts = manager.getAccountsByType("com.google");
//        Account[] accounts = manager.getAccounts();

        Account account = accounts[0];
        // 处理每个账号，例如打印账号名
        Toast.makeText(mcontext, "Account name: " + account.name, Toast.LENGTH_SHORT).show();

        final String[] token = {""};
        manager.getAuthToken(account, "oauth2:https://www.googleapis.com/auth/reminders", (Bundle) null, (Activity) mcontext, new AccountManagerCallback<Bundle>(){
            @Override
            public void run(AccountManagerFuture<Bundle> result) {
                try {
                    token[0] = result.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                    Toast.makeText(mcontext, token[0],Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, null);


        HeaderClientInterceptor interceptor = new HeaderClientInterceptor(token[0],null);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("reminders-pa.googleapis.com", 443)
                .intercept(interceptor)
                .useTransportSecurity()
                .build();
        taskStub = TasksApiServiceGrpc.newBlockingStub(channel);
    }

    public void greet() {

        dher request = dher.newBuilder().
                setB(dhfs.newBuilder().setC("Mozilla 5.0 (Linux; U; Android 12; zh_CN_#Hans; Pixel 4; Build/SQ3A.220705.003.A1); com.google.android.gms/232316044; FastParser/1.1; Reminders-Android; (gzip)").build())
                .setG(true)
                .setD(dhfg.newBuilder().setB(2).build())
                .build();

        dhes response;
        try {
            response = taskStub.listTasks(request);
        } catch (Exception e) {
            return;
        }
//        System.out.println("Greeting: " + response.getMessage());
    }
}

