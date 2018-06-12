package com.uebung.maus.getingstarted.pogba.mydemou01.ChatApp;

import android.util.Log;

import java.io.PrintWriter;

public class PushClientTest {

    public static void main(String[] args) {
        PushClient client = new PushClient(new PushClient.IOHandler(){

            private PrintWriter printer;

            @Override
            public void displayInput(String input) {
                Log.i(PushClient.logger, input);
            }

            @Override
            public void sendOutput(String output) {
                Log.i(PushClient.logger, "sending output...");
                printer.write(output);
            }

            public void setOutputWriter(PrintWriter writer) {
                printer = writer;
            }

        });
        client.connect();
    }
}
