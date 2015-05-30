package listner;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class Marshal implements org.ksoap2.serialization.Marshal {

    @Override
    public Object readInstance(XmlPullParser parser, String namespace, String name,
                               PropertyInfo expected) throws IOException, XmlPullParserException {
        // TODO Auto-generated method stub
        return Double.parseDouble(parser.nextText());
    }

    @Override
    public void register(SoapSerializationEnvelope cm) {
        // TODO Auto-generated method stub
        cm.addMapping(cm.xsd, "double", Double.class, this);
    }

    @Override
    public void writeInstance(XmlSerializer writer, Object obj)
            throws IOException {
        // TODO Auto-generated method stub
        writer.text(obj.toString());
    }

}
