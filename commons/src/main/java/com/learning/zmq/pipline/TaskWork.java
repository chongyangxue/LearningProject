package com.learning.zmq.pipline;

import org.zeromq.ZMQ;

/**
 * Created by xuechongyang on 17/7/18.
 */
public class TaskWork {

    public static void main (String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive messages on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");

        //  Socket to send messages to
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://localhost:5558");

        //  Process tasks forever
        while (!Thread.currentThread ().isInterrupted ()) {
            String string = new String(receiver.recv(0)).trim();
            long msec = Long.parseLong(string);
            //  Simple progress indicator for the viewer
            System.out.flush();
            System.out.print(string + '.');

            //  Do the work
            Thread.sleep(msec);

            //  Send results to sink
            sender.send("".getBytes(), 0);
        }
        sender.close();
        receiver.close();
        context.term();
    }
}
