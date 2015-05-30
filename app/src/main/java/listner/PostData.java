package listner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import utils.ProgressHUD;

/**
 * Created by ratnav on 11-05-2015.
 */
public class PostData extends AsyncTask implements OnCancelListener {
    ProgressHUD dialog;
    String request;
    //    String URL = "http://122.160.137.122/emallservice/eMallService.asmx";
//    String URL = "http://192.168.1.13/emallservice/eMallService.asmx";

    String URL="http://182.50.142.154:59/eMallService.asmx";
    String MethodName;
    Listener_service mListener_service;
    int Method;
    SoapPrimitive response;
    Context m_activity;

    public PostData(int Method, String request, String MethodName, Activity mCintext) {
        this.request = request;
        this.MethodName = MethodName;
        this.Method = Method;

        mListener_service = (Listener_service) mCintext;
        m_activity = mCintext;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressHUD.show(m_activity, "Processing...", true, true, PostData.this);

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.encodingStyle = SoapEnvelope.XSD;
        PropertyInfo mPropertyInfo = new PropertyInfo();
        mPropertyInfo.setName("data");
        mPropertyInfo.setValue(request);


        envelope.setOutputSoapObject(new SoapObject("http://tempuri.org/", MethodName).addProperty(mPropertyInfo));
        System.out.println(new SoapObject().addProperty(mPropertyInfo));
        Marshal md = new Marshal();
        md.register(envelope);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {


            httpTransport.debug = true;
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport = new HttpTransportSE(URL);


            httpTransport.call("http://tempuri.org/" + this.MethodName, envelope, headerPropertyArrayList);

            Log.e("Service Called", this.MethodName);


            response = (SoapPrimitive) envelope.getResponse();

            Log.e("request ", response.toString());
            Log.e("response ", response.toString());


        } catch (HttpResponseException ex) {

            mListener_service.onStatus404();
            ex.printStackTrace();
            return "";
        } catch (XmlPullParserException e) {

            e.printStackTrace();
            mListener_service.onRequestFail(Method, "Xml Pull Parser Exception");
            return "";
        } catch (IOException io) {
            io.printStackTrace();
            mListener_service.onRequestFail(Method, "Io Exception");
        }
        if (response != null) {
            mListener_service.onRequestSuccess(Method, response.toString());
        } else {
            mListener_service.onRequestFail(Method, "Xml Pull Parser Exception");

        }
        return null;
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }
}
