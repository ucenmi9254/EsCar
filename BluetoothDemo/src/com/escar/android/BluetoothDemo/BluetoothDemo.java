/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.escar.android.BluetoothDemo;

import com.ertugrul.android.BluetoothDemo.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.util.Log;
//import android.view.KeyEvent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
//import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * This is the main Activity that displays the current chat session.
 */
@SuppressLint("NewApi")
public class BluetoothDemo extends Activity {

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
    private ImageButton SolIleriButton;
    private ImageButton IleriButton;
    private ImageButton SagIleriButton;
    
    private ImageButton SolButton;
    private ImageButton DurButton;
    private ImageButton SagButton;
    
    private ImageButton SolGeriButton;
    private ImageButton GeriButton;
    private ImageButton SagGeriButton;
    
    
    
    private byte []veri = new byte[4]  ;	//1 byte veri gönderme deðiþkeni
    

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothDemoService mChatService = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
		
    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothDemoService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }
 
    private void setupChat() {
    	
    	
    	SolIleriButton = (ImageButton) findViewById(R.id.SolIleriButton);
    	SolIleriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'D'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	IleriButton = (ImageButton) findViewById(R.id.IleriButton);
    	IleriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'A'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	SagIleriButton = (ImageButton) findViewById(R.id.SagIleriButton);
    	SagIleriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'X'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	SolButton = (ImageButton) findViewById(R.id.SolButton);
    	SolButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'F'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	DurButton = (ImageButton) findViewById(R.id.DurButton);
    	DurButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	SagButton = (ImageButton) findViewById(R.id.SagButton);
    	SagButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'Z'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	
    	SolGeriButton = (ImageButton) findViewById(R.id.SolGeriButton);
    	SolGeriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'E'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	GeriButton = (ImageButton) findViewById(R.id.GeriButton);
    	GeriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'B'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	SagGeriButton = (ImageButton) findViewById(R.id.SagGeriButton);
    	SagGeriButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                	veri[0] = 'Y'; 
                	sendData(veri);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	veri[0] = 'C'; 
                	sendData(veri);
                }
                return false;
            }
        });
    	
    	
    	
        
        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothDemoService(this, mHandler);

        // Initialize the buffer for outgoing messages
        //mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        //if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    
  private void sendData(byte[] send){
	  if (mChatService.getState() != BluetoothDemoService.STATE_CONNECTED) {
          Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
          return;
      }
	  
	  Log.d("send", ""+send);
	  if(send.length>0)
		  mChatService.write(send);
  }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothDemoService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

        }
    }


    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                //if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothDemoService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    break;
                case BluetoothDemoService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothDemoService.STATE_LISTEN:
                case BluetoothDemoService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                } 
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                //String writeMessage = new String(readBuf);
                String writeMessage = new String(readBuf,0,1);
               // Toast.makeText(getApplicationContext(), writeMessage,Toast.LENGTH_SHORT).show();
                Log.d("gelen", writeMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

}