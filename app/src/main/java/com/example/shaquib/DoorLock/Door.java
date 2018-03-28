    package com.example.shaquib.DoorLock;

    import android.content.Intent;
    import android.os.AsyncTask;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;


    import java.io.DataOutputStream;
    import java.io.IOException;
    import java.net.InetAddress;
    import java.net.Socket;
    import java.net.UnknownHostException;

    public class Door extends AppCompatActivity{

        private Button buttonSignout;
        private Button buttonLock;
        private Button buttonUnlock;
        private EditText ipaddress;
        private EditText portno;
        private static String ip = "";
        private static int port ;
        private static String DATA="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_door);
            buttonSignout = (Button) findViewById(R.id.buttonsignout);
            buttonLock = (Button) findViewById(R.id.buttonlock);
            buttonUnlock = (Button) findViewById(R.id.buttonunlock);
            ipaddress = (EditText) findViewById(R.id.ipaddress);
            portno = (EditText) findViewById(R.id.port);


            buttonLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   getInfo();
                    DATA = "1";
                    Socket_AsyncTask increase = new Socket_AsyncTask();
                    increase.execute();


                }
            });

            buttonUnlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getInfo();
                    DATA = "0";
                    Socket_AsyncTask decrease = new Socket_AsyncTask();
                    decrease.execute();

                }
            });


            buttonSignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginMain.class));
                }
            });
        }



        void getInfo() throws NumberFormatException
        {
            try {
                ipaddress = (EditText) findViewById(R.id.ipaddress);
                portno = (EditText) findViewById(R.id.port);
                ip = ipaddress.getText().toString();
                port = Integer.parseInt(portno.getText().toString());
            }

            catch (NumberFormatException e)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,"Something wrong!",Toast.LENGTH_SHORT).show();
            }
        }



        public class Socket_AsyncTask extends AsyncTask<Void,Void,Void> {
            Socket socket;

            @Override
            protected Void doInBackground(Void... params){
                try{
                    InetAddress inetAddress = InetAddress.getByName(Door.ip);
                    socket = new java.net.Socket(inetAddress,Door.port);
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeBytes(DATA);
                    dataOutputStream.close();
                    socket.close();
                }catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
                return null;
            }
        }
    }
