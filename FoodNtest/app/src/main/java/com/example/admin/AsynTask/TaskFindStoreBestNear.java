package com.example.admin.AsynTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public TaskFindStoreBestNear() {
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_GET_LIST_POSITION);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
            httpTransportSE.call(Configuaration.SOAP_ACTION_GET_LIST_POSITION, envelope);
            SoapObject listSoapObject= (SoapObject) envelope.getResponse();
            SoapObject so = null;
            for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                so = (SoapObject) listSoapObject.getProperty(i);

                Position pos = new Position(Double.parseDouble(so.getPropertyAsString("HoanhDo")), Double.parseDouble(so.getPropertyAsString("TungDo")));
                //so sánh khoảng cách cửa hàng với vị trí theo bán kính cho phép


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
        for(int i=0; i<GlobalVariable.listStore.size();i++)
        {
//            Direction direction = new Direction(new LatLng(GlobalVariable.myLocationDevide.getLatitude(),GlobalVariable.myLocationDevide.getLongitude()),
//                    new LatLng(GlobalVariable.listStore.get(i).getListPoint().get(0).getV(),
//                            GlobalVariable.listStore.get(i).getListPoint().get(0).getV1()));
//            direction.GetRequestData();
//            while (direction.distance==-1);

            GlobalVariable.listStore.get(i).setMarker(GlobalVariable.mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(GlobalVariable.listStore.get(i).getListPoint().get(0).getV(),
                            GlobalVariable.listStore.get(i).getListPoint().get(0).getV1()))
                    .title(GlobalVariable.listStore.get(i).getTenCuaHang())
                    .snippet(GlobalVariable.listStore.get(i).getDiaChi())));
        }
    }

    Boolean InRadius(LatLng tam, LatLng store){
        return true;

    }

    @Override
    protected void onProgressUpdate(Store... values) {
        super.onProgressUpdate(values);
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
