package com.example.admin.AsynTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.config.Configuaration;
import com.example.admin.foodn_test.Direction;
import com.example.admin.foodn_test.GlobalVariable;
import com.example.admin.model.Position;
import com.example.admin.model.Store;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Created by Windows 10 on 09/06/2018.
 */

public class TaskFindStoreBestNear extends AsyncTask<Void, Store, Void>
{

    String methodName;
    String textSearch;
    String soapMethod;

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    public TaskFindStoreBestNear() {
        methodName = Configuaration.METHOD_GET_LIST_POSITION;
        soapMethod =Configuaration.SOAP_ACTION_GET_LIST_POSITION;
        switch (GlobalVariable.typeSearch)
        {
            case 1:
                methodName = Configuaration.METHOD_SEARCH_NAME;
                soapMethod =Configuaration.SOAP_ACTION_SEARCH_BY_NAME;
                break;
            case 2:
                methodName = Configuaration.METHOD_SEARCH_NAME;
                soapMethod =Configuaration.SOAP_ACTION_SEARCH_BY_NAME;
                break;
            case 0:
                methodName = Configuaration.METHOD_GET_LIST_POSITION;
                soapMethod =Configuaration.SOAP_ACTION_GET_LIST_POSITION;
                break;
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(Configuaration.NAME_SPACE, this.methodName);
            if(GlobalVariable.typeSearch!= 0 )
                request.addProperty("text", textSearch);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
            httpTransportSE.call(soapMethod, envelope);
            SoapObject listSoapObject= (SoapObject) envelope.getResponse();
            SoapObject so = null;
            for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                so = (SoapObject) listSoapObject.getProperty(i);

                Position pos = new Position(Double.parseDouble(so.getPropertyAsString("HoanhDo")), Double.parseDouble(so.getPropertyAsString("TungDo")));
                Store store = new Store();
                store.getListPoint().add(pos);
                so = (SoapObject) so.getProperty("CuaHang");
                store.setID(Integer.parseInt(so.getPropertyAsString("ID_CuaHang")));
                store.setTenCuaHang(so.getPropertyAsString("TenCuaHang"));
                store.setDiaChi(so.getPropertyAsString("DiaChi"));
                store.setHinhAnh(StringToBitMap(so.getPropertyAsString("HinhAnh")));
                publishProgress(store);
            }
        }
        catch (Exception ex)
        {
            Log.e("Lỗi", ex.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        GlobalVariable.mMap.clear();
        for(int i=0; i<GlobalVariable.listStore.size();i++)
        {
            GlobalVariable.listStore.get(i).setMarker(GlobalVariable.mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(GlobalVariable.listStore.get(i).getListPoint().get(0).getV(),
                            GlobalVariable.listStore.get(i).getListPoint().get(0).getV1()))
                    .title(GlobalVariable.listStore.get(i).getTenCuaHang())
                    .snippet(GlobalVariable.listStore.get(i).getDiaChi())));
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double meter = valueResult*1000;

        return meter;
    }


    @Override
    protected void onProgressUpdate(Store... values) {
        super.onProgressUpdate(values);
        double distance = CalculationByDistance(new LatLng(GlobalVariable.myLocationDevide.getLatitude(),GlobalVariable.myLocationDevide.getLongitude()),
                new LatLng(values[0].getListPoint().get(0).getV(),
                        values[0].getListPoint().get(0).getV1()));
        if( distance > GlobalVariable.radius && GlobalVariable.typeSearch ==0) return;
        GlobalVariable.listStore.add(values[0]);
    }


    private Bitmap StringToBitMap(String hinhAnh) {
        try {
            byte[] encodeByte = Base64.decode(hinhAnh);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
