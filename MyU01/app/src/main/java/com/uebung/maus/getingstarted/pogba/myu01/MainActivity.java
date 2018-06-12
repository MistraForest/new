package com.uebung.maus.getingstarted.pogba.myu01;

import android.os.Bundle;

import java.io.PrintWriter;

public class MainActivity extends AbstractMessageSender {

    private PushClient.IOHandler ioHandler;


    @Override
    protected void processMessage(String message) {
        ioHandler.sendOutput(message);
        //ioHandler.displayInput(message);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.conversation);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.ioHandler = new PushClient.IOHandler() {

            private PrintWriter printWriter;

            @Override
            public void displayInput(final String input) {
                //updateConversation(input);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConversation(input);
                    }
                });
            }

            @Override
            public void sendOutput(String output) {
                printWriter.write(output);
            }

            @Override
            public void setOutputWriter(PrintWriter writer) {
                this.printWriter = writer;
            }
        };

        new PushClient(this.ioHandler).connect();


    }
}
