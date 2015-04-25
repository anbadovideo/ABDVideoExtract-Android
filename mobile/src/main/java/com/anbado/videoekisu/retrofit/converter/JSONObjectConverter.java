package com.anbado.videoekisu.retrofit.converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * @author by SeunOh on 15. 4. 20..
 */
public class JSONObjectConverter implements Converter {
    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(fromStream(body.in()));
        } catch (IOException | JSONException ignored) {/*NOP*/ }
        return jsonObject;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}
