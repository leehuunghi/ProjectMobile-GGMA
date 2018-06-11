package com.example.admin.foodn_test;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.admin.config.Configuaration;
import com.example.admin.model.Product;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class TwoFragment extends Fragment {
    int IDStore;
    List<Product> productList =  new ArrayList<>();
    public void setIDStore(int IDStore) {
        this.IDStore = IDStore;
    }
    CustomBaseAdapter adapter;
    DATABASE database_records;
    private List sections = new ArrayList();
    private static int TYPE_SECTION_HEADER = 1;
    StoreDetailActivity storeDetailActivity;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDetailActivity = (StoreDetailActivity) getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_two, container, false);
        ListView listview = (ListView) layout.findViewById(R.id.lvMenu);
        adapter = new CustomBaseAdapter(storeDetailActivity, productList, R.layout.list_gui_row);
        listview.setAdapter(adapter);
        GetInfoProduct getInfoProduct = new GetInfoProduct();
        getInfoProduct.execute();

        return layout;
    }


    class GetInfoProduct extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                SoapObject request = new SoapObject(Configuaration.NAME_SPACE,Configuaration.METHOD_GET_LIST_FOOD_STORE);
                request.addProperty(Configuaration.PARAMETER_ID, IDStore);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE= new HttpTransportSE(Configuaration.SERVER_URL);
                httpTransportSE.call(Configuaration.SOAP_ACTION_GET_FOOD_BY_IDSTORE, envelope);
                SoapObject listSoapObject= (SoapObject) envelope.getResponse();
                SoapObject so = null;
                for (int i = 0; i < listSoapObject.getPropertyCount(); i++) {
                    so = (SoapObject) listSoapObject.getProperty(i);
                    Product product = new Product();
                    product.setId(Integer.parseInt(so.getPropertyAsString("ID_MonAn")));
                    product.setName(so.getPropertyAsString("TenMon"));
                    product.setPrice(so.getPropertyAsString("Gia"));
                    product.setImage(StringToBitMap(so.getPropertyAsString("HinhAnhMonAn")));
                    productList.add(product);
                }

            }
            catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return null;
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
}
