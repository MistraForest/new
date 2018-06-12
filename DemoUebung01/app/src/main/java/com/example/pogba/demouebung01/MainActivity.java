package com.example.pogba.demouebung01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.PrintWriter;

public class MainActivity extends AbstractMessageSender {

    private PushClient.IOHandler mIOHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        mIOHandler = new PushClient.IOHandler() {

            private PrintWriter printWriter;

            @Override
            public void displayInput(String input) {
                updateConversation(input);
            }

            @Override
            public void sendOutput(final String output) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        printWriter.println(output);
                    }
                }).start();

            }

            @Override
            public void setOutputWriter(PrintWriter writer) {
                printWriter = writer;
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                new PushClient(mIOHandler).connect();
            }
        }).start();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.conversation);
    }

    @Override
    protected void processMessage(String message) {
        mIOHandler.sendOutput(message);
    }
}
