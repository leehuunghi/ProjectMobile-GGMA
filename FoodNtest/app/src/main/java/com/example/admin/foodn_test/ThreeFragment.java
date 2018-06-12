package com.example.admin.foodn_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.config.Configuaration;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ThreeFragment extends Fragment implements FragmentCallBack {

    int rateIndex;
    int ID;


    public int getID() {return ID;};
    EditText cmtInput;
    ImageButton btnCmt;
    ImageView rate21, rate22, rate23, rate24, rate25;
    List<ImageView> rate=new ArrayList<>();
    public ThreeFragment() {
        // Required empty public constructor
    }

    class CheckRate extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_CHECK_RATE);
                request.addProperty(Configuaration.PARAMETER_ID, GlobalVariable.MyUser.getId());
                request.addProperty(Configuaration.PARAMETER_IDStore, ID);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_CHECK_RATE, envelope);

                SoapObject data= (SoapObject) envelope.bodyIn;
                rateIndex = Integer.parseInt(data.getPropertyAsString("rateIndex"));
                    for (int i = 0; i < rateIndex; i++)
                    {
                        rate.get(i).setImageResource(R.drawable.ratefull);
                    }

            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_three, container, false);
        cmtInput = layout.findViewById(R.id.cmInput);
        btnCmt = layout.findViewById(R.id.btnCmt);
        rate21 = layout.findViewById(R.id.rate21);
        rate22 = layout.findViewById(R.id.rate22);
        rate23 = layout.findViewById(R.id.rate23);
        rate24 = layout.findViewById(R.id.rate24);
        rate25 = layout.findViewById(R.id.rate25);
        rateIndex = 0;
        CheckRate checkRate = new CheckRate();
        checkRate.execute();
        rate.add(rate21);
        rate.add(rate22);
        rate.add(rate23);
        rate.add(rate24);
        rate.add(rate25);

        rate21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 1;
                for (int i = 0; i < n; i++)
                {
                    rate.get(i).setImageResource(R.drawable.ratefull);
                }
                for (int i = n; i < 5; i++)
                {
                    rate.get(i).setImageResource(R.drawable.rateempty);
                }
            }
        });

        rate22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 2;
                for (int i = 0; i < n; i++)
                {
                    rate.get(i).setImageResource(R.drawable.ratefull);
                }
                for (int i = n; i < 5; i++)
                {
                    rate.get(i).setImageResource(R.drawable.rateempty);
                }
            }
        });

        rate23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 3;
                for (int i = 0; i < n; i++)
                {
                    rate.get(i).setImageResource(R.drawable.ratefull);
                }
                for (int i = n; i < 5; i++)
                {
                    rate.get(i).setImageResource(R.drawable.rateempty);
                }
            }
        });

        rate24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 4;
                for (int i = 0; i < n; i++)
                {
                    rate.get(i).setImageResource(R.drawable.ratefull);
                }
                for (int i = n; i < 5; i++)
                {
                    rate.get(i).setImageResource(R.drawable.rateempty);
                }
            }
        });

        rate25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 5;
                for (int i = 0; i < n; i++)
                {
                    rate.get(i).setImageResource(R.drawable.ratefull);
                }
                for (int i = n; i < 5; i++)
                {
                    rate.get(i).setImageResource(R.drawable.rateempty);
                }
            }
        });

        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCmt addCmt = new AddCmt();
                addCmt.execute();
                AddRate addRate = new AddRate();
                addRate.execute();
            }
        });
        return layout;
    }

    @Override
    public void MsgFromMain(String msg) {

    }

    class AddCmt extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_ADD_CMT);
                request.addProperty(Configuaration.PARAMETER_ID, GlobalVariable.MyUser.getId());
                request.addProperty(Configuaration.PARAMETER_IDStore, ID);
                request.addProperty(Configuaration.PARAMETER_CMT, cmtInput);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_ADD_CMT, envelope);

                SoapObject data= (SoapObject) envelope.bodyIn;
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }

    class AddRate extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_ADD_RATE);
                request.addProperty(Configuaration.PARAMETER_ID, GlobalVariable.MyUser.getId());
                request.addProperty(Configuaration.PARAMETER_IDStore, ID);
                request.addProperty(Configuaration.PARAMETER_RATE, rateIndex);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_ADD_RATE, envelope);

                SoapObject data= (SoapObject) envelope.bodyIn;
            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
        }
    }
}
